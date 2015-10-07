package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * A Strategy that allows the program to connect to various types of databases.
 * This interface has methods for all of the C.R.U.D operations. It also can use
 * a data source or hard code the connect to the database.
 * 
 * @author emmakordik
 * @version 1.00
 */
public interface DatabaseStrategy {

    /**
     * Deletes a record using the primary key to identify it in the database.
     * 
     * @param tableName - THe table from which the record should be deleted
     * @param primaryKeyColumnName - The column name of the primary key
     * @param primaryKeyValue - The primary key for the record to be deleted
     * @throws SQLException 
     */
    void deleteByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue) throws SQLException;

    /**
     * Retrieves all of the records from a table in the database.
     * 
     * @param tableName - A String with the name of the table to be queried
     * @return - The records from the table in a List of map with String keys 
     * which are the column names and Objects which are the data.
     * @throws SQLException 
     */
    List<Map<String, Object>> getAllRecords(String tableName) throws SQLException;
    
    /**
     * Retrieves a single record from the database using the primary key to 
     * identify that record.
     * 
     * @param tableName - A String with the name of the table to be queried
     * @param idColumnName - A String with the name of the primary key column
     * @param idValue - An object with the value of the primary key
     * @return - the record in a Map with String keys which are the column names
     * and Objects which are the values.
     * @throws SQLException 
     */
    public abstract Map<String,Object> getRecordbyId(String tableName, String idColumnName, Object idValue) 
            throws SQLException;

    /**
     * Saves a record to the database. This method takes parallel list which
     * contain the column names and column values to be saved.
     * @param tableName - A String with the table name of the table the record
     * should be inserted into.
     * @param colNames - A List of String column names
     * @param colValues - A list of column values. This is a list of objects to
     * accommodate any datatype
     * @throws SQLException 
     */
    void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;

    /**
     * Opens a connection with the database. This method has the connection 
     * information passed into the class.
     * 
     * @param driverClass - The driver class name/location for the driver to be
     * used
     * @param url - A String url for the location of the database
     * @param userName - The username to connect to the database
     * @param password - The database password
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    
    /**
     * Opens a connection with the database using a Datasource object.
     * 
     * @param ds - The Data source object
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public abstract void openConnection(DataSource ds) throws ClassNotFoundException, SQLException;
    
    /**
     * Updates a record in the database using the primary key (or another column)
     * to locate the record to be updated. This method uses parallel lists for 
     * the column names and values to be updated.
     * 
     * @param tableName - A String table name of the table the record is in. 
     * @param colNames - The list of column names as strings
     * @param colValues - A list of values to be updated in the database. This is
     * a list of objects to accommodate all data types.
     * @param whereColumn - The column name to be used in the Where clause, as 
     * a string.
     * @param whereValue - The value to be used in the where clause.
     * @throws SQLException 
     */
     public abstract void updateRecord(String tableName, List<String>colNames, List<Object> colValues, String whereColumn, Object whereValue) throws SQLException;
}
