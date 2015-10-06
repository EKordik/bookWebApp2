package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

/**
 * A Strategy that allows us to connect to various types of databases.
 * 
 * @author emmakordik
 */
public interface DatabaseStrategy {

    void deleteByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue) throws SQLException;

    List<Map<String, Object>> getAllRecords(String tableName) throws SQLException;
    
    public abstract Map<String,Object> getRecordbyId(String tableName, String idColumnName, Object idValue) 
            throws SQLException;

    void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    
    public abstract void openConnection(DataSource ds) throws ClassNotFoundException, SQLException;
    
     public abstract void updateRecord(String tableName, List<String>colNames, List<Object> colValues, String whereColumn, Object whereValue) throws SQLException;
}
