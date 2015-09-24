package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author emmakordik
 */
public interface DatabaseAccessStrategy {

    void closeConnection() throws SQLException;

    void deleteByPrimaryKey(String tableName, String primaryKeyColumnName, Object primaryKeyValue) throws SQLException;

    List<Map<String, Object>> getAllRecords(String tableName) throws SQLException;

    void insertRecord(String tableName, List<String> colNames, List<Object> colValues) throws SQLException;

    void openConnection(String driverClass, String url, String userName, String password) throws ClassNotFoundException, SQLException;
    
     public abstract void updateRecord(String tableName, List<String>colNames, List<Object> colValues, String whereColumn, Object whereValue) throws SQLException;
}
