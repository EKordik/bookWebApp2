package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * A Database Strategy to connect to MySql databases. This is a very low level 
 * class that is designed to be flexible to work with many different programs.
 * 
 * @author emmakordik
 * @version 1.00
 */
public class MySqlDb implements DatabaseStrategy {
    private Connection conn;
    
    /**
     * This constructor is the old school method of connecting to the database
     * and requires that you provide the driver class name, the url for the 
     * database, the username and password needed to connect with the database.
     * This is the constructor to use if you do not have a connection pool.
     * 
     * @param driverClass - A String with the name of the driver class for the
     * database being used
     * @param url - A String with the url address of the database
     * @param userName - A String with the username for the database
     * @param password - A String with the password for the database
     * @throws ClassNotFoundException - Gets thrown if the driver class cannot
     * be instantiated. 
     * @throws SQLException - Gets thrown if something is wrong when connecting 
     * to the database.
     */
    @Override
    public void openConnection(String driverClass, String url, String userName, 
            String password) throws ClassNotFoundException, SQLException{
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    /**
     * This constructor uses a data source to connect to the database. This is 
     * often the preferred method of connection to a database as it encapsulates
     * complexity and hides database information from the developer.
     * 
     * @param ds - THe data source to be used
     * @throws SQLException - Gets thrown if something goes wrong when connecting
     * to the database.
     */
    @Override
    public void openConnection(DataSource ds) throws SQLException{
        conn = ds.getConnection();
    }
    
   //Closes the connection
    private void closeConnection() throws SQLException{
        conn.close();
    }
    
    /**
     * Gets all the records from the database. This method returns them as a list
     * of maps. Each map represents one record. The String keys are the column
     * names and the objects are the value for each column.
     * 
     * @param tableName - A String with the table name that the records are to be
     * gotten from.
     * @return - Returns a list of maps. Each map has String keys and Object
     * values.
     * @throws SQLException - Gets thrown if something is wrong with the SQL
     * statement or when connecting to the database.
     */
    
    @Override
    public List<Map<String, Object>> getAllRecords(String tableName) throws SQLException{
        List<Map<String,Object>> records = new ArrayList<>();
        
        String sql = "SELECT * From " + tableName;
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while(rs.next()){
                Map<String,Object> record = new HashMap<>();
                for(int i = 1; i<=columnCount; i++){
                    record.put(metaData.getColumnName(i), rs.getObject(i));
                }
                records.add(record);
            }
        
        }finally{
            closeConnection();
        }
        return records;
    }
    
    /**
     * Gets one record from the database. This uses the primary key to make sure
     * that only one record is returned. You could use a different column but if
     * more than one record is returned you will only get the first returned to
     * you.
     * 
     * @param tableName - A string with the table name to query data from
     * @param idColumnName - The column name for the primary key
     * @param idValue - The value of the primary key for the record you want
     * @return - Returns a map with a string key which is the column name and
     * an object which is the data in the column.
     * @throws SQLException - Gets thrown if something is wrong with the SQL
     * statement or when connecting to the database
     */
     @Override
    public Map<String,Object> getRecordbyId(String tableName, String idColumnName, Object idValue) 
            throws SQLException{
        PreparedStatement getRecord = null;
        String sql = "SELECT * FROM " + tableName + " WHERE " + idColumnName + "=?";
        Map<String,Object> record = new HashMap();
        try{
            getRecord = conn.prepareStatement(sql);
            getRecord.setObject(1, idValue);
            
            ResultSet rs = getRecord.executeQuery();
            ResultSetMetaData metadata = rs.getMetaData();
            int columnCount = metadata.getColumnCount();
            
            if(rs.next()){
                for(int i = 1; i<=columnCount; i++){
                    record.put(metadata.getColumnName(i), rs.getObject(i));
                }
            }   
        }finally{
            getRecord.close();
            closeConnection();
        }
        
        return record;
    }
    
    /**
     * Deletes a record using the primary key to identify the record to be 
     * deleted. It would be possible to use a different column to find the record
     * however if there are more than one record having the same value both
     * records will be deleted.
     * 
     * @param tableName - A string with the table name to query data from
     * @param primaryKeyColumnName -- The column name for the primary key
     * @param primaryKeyValue - The value of the primary key for the record you want
     * @throws SQLException - Gets thrown if something is wrong with the SQL
     * statement or when connecting to the database
     */
    @Override
    public void deleteByPrimaryKey(String tableName, String primaryKeyColumnName, 
            Object primaryKeyValue) throws SQLException{
//        
        PreparedStatement deleteStmt = null;
        String sql = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumnName + "=?";
        
        try{
        deleteStmt = conn.prepareStatement(sql);
        
        deleteStmt.setObject(1, primaryKeyValue);
        
        int columnsDeleted = deleteStmt.executeUpdate();
        
        }finally{
            closeConnection();
        }
    }
    
    /**
     * Inserts a record into the database. This accepts two list. They should be
     * parallel list. The colName list must have the column names in the same
     * order as the values are in the colValues list otherwise data will be 
     * placed into the wrong columns. This method adds only one record.
     * 
     * @param tableName - A String with the table name for the table the record
     * is to be inserted into.
     * @param colNames - The names of the columns that will have data added to them
     * @param colValues - The values to be added to the database
     * @throws SQLException - Gets thrown if something goes wrong with the SQL
     * statement or connecting the the database
     */
    @Override
    public void insertRecord(String tableName, List<String> colNames, 
            List<Object> colValues)throws SQLException{
        try{
            PreparedStatement stmt = buildInsertStatement(tableName, colNames);

            for(int i = 1; i<=colNames.size(); i++){
                stmt.setObject(i, colValues.get(i-1));
            }
        
            int insertedRows = stmt.executeUpdate();
        }finally{
            closeConnection();
        }
    }
    
    /**
     * Updates a record in the database using the primary key to find the record
     * to be updated. A different field than the primary key can be used but if
     * multiple records have the same value they will all be updated.
     * 
     * @param tableName - A String with the table name for the table the record
     * is to be inserted into.
     * @param colNames - The names of the columns that will have data updated 
     * @param colValues - The values to be updated in the database
     * @param whereColumn - The primary key column name to be searched for
     * @param whereValue - The value of the primary key for the record to be
     * updated
     * @throws SQLException - Gets thrown if something goes wrong with the SQL
     * statement or connecting to the database
     */
    @Override
    public void updateRecord(String tableName, List<String>colNames, List<Object> colValues, 
            String whereColumn, Object whereValue) throws SQLException{
        try{
            PreparedStatement stmt = buildUpdateStatement(tableName, colNames, whereColumn);

            for(int i = 1; i<=colNames.size(); i++){
                stmt.setObject(i, colValues.get(i-1));
            }

            if(whereColumn != null){
                stmt.setObject(colNames.size()+1, whereValue);
            }

            int recordsUpdated = stmt.executeUpdate();
        }finally{
            closeConnection();
        }
            
    }
    
    //builds a prepared statement for updating
    private PreparedStatement buildUpdateStatement(String tableName, List<String>colNames,
            String whereColumn) throws SQLException{
        StringBuffer sql = new StringBuffer();
        sql.append("Update ").append(tableName).append(" SET ");
        
        final Iterator i = colNames.iterator();
        while(i.hasNext()){
            sql.append(i.next()).append(" =? ,");
        }
        sql = new StringBuffer(sql.toString().substring(0, sql.toString().lastIndexOf(",")));
        sql.append(" ");
        
        if(whereColumn != null){
            sql.append(" WHERE ");
            sql.append(whereColumn).append(" =?");
        }
        
        final String sqlStmt = sql.toString();
        PreparedStatement stmt = conn.prepareStatement(sqlStmt);
        return stmt;
    }
    
    //builds a prepared statement for inserting
    private PreparedStatement buildInsertStatement(String tableName, List<String>columnNames) throws SQLException{
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT INTO ").append(tableName).append("(");
        final Iterator i = columnNames.iterator();
        while(i.hasNext()){
            sql.append(i.next());
            sql.append(", ");
        }
        sql = new StringBuffer( (sql.toString()).substring( 0,(sql.toString()).lastIndexOf(", ") ) + ") VALUES (" );
        
        for(int j = 0; j<columnNames.size(); j++){
            sql.append("?, ");
        }
        
        final String sqlStmt = (sql.toString()).substring(0,(sql.toString()).lastIndexOf(",")) + ");";
        
        PreparedStatement stmt = conn.prepareStatement(sqlStmt);
        
        return stmt;
    }
    
    //for testing purposes
//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        MySqlDb db = new MySqlDb();
//        String driverClass = "com.mysql.jdbc.Driver";
//        String url = "jdbc:mysql://localhost:3306/book";
//        String userName = "root";
//        String password = "admin";
//        List<Map<String,Object>>records = new ArrayList<>();
//        
//        db.openConnection(driverClass, url, userName, password);
//        
//        List<String> colNames = new ArrayList<>();
//        colNames.add("author_name");
//        //colNames.add("date_created");
//        
//        List<Object> colValues = new ArrayList<>();
//        colValues.add("John Milton");
//        //Date created = new Date();
//        SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
//
//        //colValues.add(stf.format(created));
//
//        try{
//            //db.deleteByID("author", "author_id", 6);
//           // db.updateRecord("author", colNames, colValues, "author_id", 11);
//            //records = db.getAllRecords("author");
//            Map<String,Object> record = db.getRecordbyId("author_name", "author_id", 8);
//             System.out.println(record);
//        }finally{
//            //db.closeConnection();
//        }
//        
//  
////        for(Map record: records){
////            System.out.println(record);
////        }
//    }
}
