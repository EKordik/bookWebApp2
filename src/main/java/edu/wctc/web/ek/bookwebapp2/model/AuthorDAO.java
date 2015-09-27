package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 *
 * @author emmakordik
 */
public class AuthorDAO implements IAuthorDAO {
    private DatabaseStrategy db;
    private String driverClass;
    private String url;
    private String userName;
    private String password;
    private final static String TABLE_NAME = "author";
    private final static String NAME_COL = "author_name";
    private final static String ID_COL = "author_id";
    private final static String DATE_COL = "date_created";
    
    public AuthorDAO(DatabaseStrategy db, String driverClass, String url, 
            String userName, String password){
        this.db = db;
        this.driverClass = driverClass;
        this.url= url;
        this.userName = userName;
        this.password = password;
    }
    
    @Override
    public List<Author> getAllAuthors() 
            throws ClassNotFoundException, SQLException{
        List<Author> authors = new ArrayList<>();
        
        db.openConnection(driverClass, url, userName, password);
        try{
            List<Map<String,Object>> rawRecords = db.getAllRecords(TABLE_NAME);

            Author a;
            for(Map rawRecord: rawRecords){
                a = new Author();
                Object obj = rawRecord.get("author_id");
                a.setAuthorId(Integer.parseInt(obj.toString()));
                
                String authorName = rawRecord.get("author_name") == null ? "" : rawRecord.get("author_name").toString();
                a.setAuthorName(authorName);
                
                obj = rawRecord.get("date_created");
                Date creationDate = (obj == null) ? new Date() : (Date)rawRecord.get("date_created");
                a.setDateCreated(creationDate);
                authors.add(a);
            }
            
        }finally{
            db.closeConnection();
        }
        return authors;
    }
    
    @Override
    public void deleteAuthorbyId(String author)throws ClassNotFoundException, SQLException, NumberFormatException{
        int authorId = Integer.parseInt(author);
        db.openConnection(driverClass, url, userName, password);
        try{
            db.deleteByPrimaryKey(TABLE_NAME, ID_COL, authorId);
        }finally{
            db.closeConnection();
        }
    }
    
    public void saveAuthor(Author author) throws ClassNotFoundException, SQLException{
        List<String> colNames = new ArrayList<>();
        List<Object> colValues = new ArrayList<>();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
        
        colNames.add(NAME_COL);
        colNames.add(DATE_COL);
        
        colValues.add(author.getAuthorName());
        
        if(author.getDateCreated() != null){
            colValues.add(stf.format(author.getDateCreated()));
        }else{
            colValues.add(stf.format(new Date()));
        }
        
        db.openConnection(driverClass, url, userName, password);
        try{
            db.insertRecord(TABLE_NAME, colNames, colValues);
        }finally{
            db.closeConnection();
        }
    }
    
    public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
        List<String>colNames = new ArrayList<>();
        List<Object>colValues = new ArrayList<>();
        colNames.add(NAME_COL);
        colNames.add(DATE_COL);
        
        colValues.add(author.getAuthorName());
        colValues.add(author.getDateCreated());
        
        db.openConnection(driverClass, url, userName, password);
        try{
        db.updateRecord(TABLE_NAME, colNames, colValues, ID_COL, author.getAuthorId());
        }finally{
            db.closeConnection();
        }
        
    }
    
    public static void main(String[] args) {
        AuthorDAO author = new AuthorDAO(new MySqlDb(), "com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book","root","admin");
            
        List<Author> records = new ArrayList<>();
        Author aa = new Author();
        aa.setAuthorId(10);
        aa.setAuthorName("Hans Christian Anderson");
        aa.setDateCreated(new Date());
        
        try{
            //author.deleteAuthorbyId("author_id", 7);
            author.updateAuthor(aa);
            records = author.getAllAuthors();
        }catch(ClassNotFoundException cnfe){
            System.out.println("Class Not Found");
        }catch(SQLException sqle){
            System.out.println("SQL Exception");
        }
        
        for(Author a: records){
            System.out.println(a);
        }
    }
}
