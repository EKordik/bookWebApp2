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
 *
 * @author emmakordik
 */
public class MySqlDb implements DatabaseStrategy {
    private Connection conn;
    
    @Override
    public void openConnection(String driverClass, String url, String userName, 
            String password) throws ClassNotFoundException, SQLException{
        Class.forName(driverClass);
        conn = DriverManager.getConnection(url, userName, password);
    }
    
    @Override
    public void openConnection(DataSource ds) throws ClassNotFoundException, SQLException{
        conn = ds.getConnection();
    }
    
   
    private void closeConnection() throws SQLException{
        conn.close();
    }
    
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
        System.out.println(sqlStmt);
        return stmt;
    }
    
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
        System.out.println(sqlStmt);
        
        PreparedStatement stmt = conn.prepareStatement(sqlStmt);
        
        return stmt;
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        MySqlDb db = new MySqlDb();
        String driverClass = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/book";
        String userName = "root";
        String password = "admin";
        List<Map<String,Object>>records = new ArrayList<>();
        
        db.openConnection(driverClass, url, userName, password);
        
        List<String> colNames = new ArrayList<>();
        colNames.add("author_name");
        //colNames.add("date_created");
        
        List<Object> colValues = new ArrayList<>();
        colValues.add("John Milton");
        //Date created = new Date();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");

        //colValues.add(stf.format(created));

        try{
            //db.deleteByID("author", "author_id", 6);
           // db.updateRecord("author", colNames, colValues, "author_id", 11);
            //records = db.getAllRecords("author");
            Map<String,Object> record = db.getRecordbyId("author_name", "author_id", 8);
             System.out.println(record);
        }finally{
            //db.closeConnection();
        }
        
  
//        for(Map record: records){
//            System.out.println(record);
//        }
    }
}
