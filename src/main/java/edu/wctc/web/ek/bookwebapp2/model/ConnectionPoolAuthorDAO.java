package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author emmakordik
 */
public class ConnectionPoolAuthorDAO implements IAuthorDAO {
    private DatabaseStrategy db;
    private DataSource ds;
    
    private final static String TABLE_NAME = "author";
    private final static String NAME_COL = "author_name";
    private final static String ID_COL = "author_id";
    private final static String DATE_COL = "date_created";
    
    public ConnectionPoolAuthorDAO(DataSource ds, DatabaseStrategy db){
        this.db = db;
        this.ds = ds;
    }
    
    @Override
    public List<Author> getAllAuthors() 
            throws ClassNotFoundException, SQLException{
        List<Author> authors = new ArrayList<>();
        
        db.openConnection(ds);
    
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
       
        return authors;
    }
    
    public Author getAuthorbyId(String authorId) throws ClassNotFoundException, SQLException{
        Author author = new Author();
        int aId = Integer.parseInt(authorId);
        
        db.openConnection(ds);
        Map<String,Object> rawRec = db.getRecordbyId(TABLE_NAME, ID_COL, aId);
        
        author.setAuthorId(aId);
        
        String authorName = rawRec.get(NAME_COL) == null ? "" : rawRec.get(NAME_COL).toString();
        author.setAuthorName(authorName);
        
        Object obj = rawRec.get(DATE_COL);
        Date dateAdd = (obj == null) ? new Date() : (Date)rawRec.get(DATE_COL);
        author.setDateCreated(dateAdd);
        
        return author;
    }
    
    @Override
    public void deleteAuthorbyId(String author)throws ClassNotFoundException, SQLException, NumberFormatException{
        int authorId = Integer.parseInt(author);
        db.openConnection(ds);

        db.deleteByPrimaryKey(TABLE_NAME, ID_COL, authorId);

    }
    
    public void saveAuthor(String authorName, String date) 
            throws ClassNotFoundException, SQLException, ParseException{
        List<String> colNames = new ArrayList<>();
        List<Object> colValues = new ArrayList<>();
        SimpleDateFormat stf = new SimpleDateFormat("yyyy-MM-dd");
        
        colNames.add(NAME_COL);
        colNames.add(DATE_COL);
        
        colValues.add(authorName);
        
        if(date != null){
            colValues.add(stf.parse(date));
        }else{
            colValues.add(stf.format(new Date()));
        }
        
        db.openConnection(ds);
        db.insertRecord(TABLE_NAME, colNames, colValues);
       
    }
    
    public void updateAuthor(String authorId, String authorName, String date) 
            throws ClassNotFoundException, SQLException, ParseException{
        List<String>colNames = new ArrayList<>();
        List<Object>colValues = new ArrayList<>();
        colNames.add(NAME_COL);
        colNames.add(DATE_COL);
        
        colValues.add(authorName);
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");     
        colValues.add(format.parse(date));
        
        db.openConnection(ds);

        db.updateRecord(TABLE_NAME, colNames, colValues, ID_COL, Integer.parseInt(authorId));
        
    }
    
    public static void main(String[] args) throws NamingException {
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("jdbc/book");
        IAuthorDAO author = new ConnectionPoolAuthorDAO(ds, new MySqlDb());
            
        List<Author> records = new ArrayList<>();
        
        try{
            //author.deleteAuthorbyId("author_id", 7);
            //author.updateAuthor(aa);
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
