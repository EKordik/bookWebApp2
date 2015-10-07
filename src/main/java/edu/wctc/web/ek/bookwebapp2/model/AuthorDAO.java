package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * An Author DAO Class to handle database access for the author table of the
 * database. This class implements IAuthorDAO interface to make it flexible.
 * 
 * @author emmakordik
 * @version 1.00
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
    private final static String SQL_DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * Constructor that uses a Database Strategy class and has the driverClass,
     * url, username, and password passed in. This version of the Author DAO 
     * does not support connection pooling.
     * 
     * @param db - The DatabaseStrategy to use to connect to a particular type
     * of database
     * @param driverClass - A String with the name of the driver class for the
     * database being used
     * @param url - A String with the url address of the database
     * @param userName - A String with the username for the database
     * @param password - A String with the password for the database
     */
    public AuthorDAO(DatabaseStrategy db, String driverClass, String url, 
            String userName, String password){
        this.db = db;
        this.driverClass = driverClass;
        this.url= url;
        this.userName = userName;
        this.password = password;
    }
    
    /**
     * This gets all the author in the database. It takes the raw data from the
     * database and converts it into a list of author objects.
     * 
     * @return - A list of author objects
     * @throws ClassNotFoundException - Thrown if something goes wrong with 
     * the driver class
     * @throws SQLException - If something is wrong with the SQL statement or
     * connection to the database
     */
    @Override
    public List<Author> getAllAuthors() 
            throws ClassNotFoundException, SQLException{
        List<Author> authors = new ArrayList<>();
        
        db.openConnection(driverClass, url, userName, password);

        List<Map<String,Object>> rawRecords = db.getAllRecords(TABLE_NAME);

        Author a;
        for(Map rawRecord: rawRecords){
            a = new Author();
            Object obj = rawRecord.get(ID_COL);
            a.setAuthorId(Integer.parseInt(obj.toString()));

            String authorName = rawRecord.get(NAME_COL) == null ? "" : rawRecord.get(NAME_COL).toString();
            a.setAuthorName(authorName);

            obj = rawRecord.get(DATE_COL);
            Date creationDate = (obj == null) ? new Date() : (Date)rawRecord.get(DATE_COL);
            a.setDateCreated(creationDate);
            authors.add(a);
        }

        return authors;
    }
    
    /**
     * Finds one author in the database using the unique identifier and returns
     * it as an Author object. 
     * 
     * @param authorId - A String with the unique identifier of the author. This
     * will be parsed as an integer.
     * @return - An author object representing the author found, will be null
     * if no author is found.
     * @throws ClassNotFoundException - Thrown if something goes wrong with 
     * the driver class
     * @throws SQLException - If something is wrong with the SQL statement or
     * connection to the database
     * @throws NumberFormatException - Gets thrown if the authorId passed in 
     * cannot be parsed as an integer.
     */
    public Author getAuthorbyId(String authorId) throws ClassNotFoundException, 
            SQLException, NumberFormatException{
        Author author = new Author();
        int aId = Integer.parseInt(authorId);
        
        db.openConnection(driverClass, url, userName, password);
        Map<String,Object> rawRec = db.getRecordbyId(TABLE_NAME, ID_COL, aId);
        
        author.setAuthorId(aId);
        
        String authorName = rawRec.get(NAME_COL) == null ? "" : rawRec.get(NAME_COL).toString();
        author.setAuthorName(authorName);
        
        Object obj = rawRec.get(DATE_COL);
        Date dateAdd = (obj == null) ? new Date() : (Date)rawRec.get(DATE_COL);
        author.setDateCreated(dateAdd);
        
        return author;
    }
    
    /**
     * Deletes an author from the database using the author's unique identifier
     * to locate the author.
     * 
     * @param author - String to contain the author Id for the author to be deleted
     * @throws ClassNotFoundException - Thrown if something goes wrong with 
     * the driver class
     * @throws SQLException - If something is wrong with the SQL statement or
     * connection to the database
     * @throws NumberFormatException - Gets thrown if the author id passed in
     * cannot be parsed as a number
     */
    @Override
    public void deleteAuthorbyId(String author)throws ClassNotFoundException, SQLException, NumberFormatException{
        int authorId = Integer.parseInt(author);
        db.openConnection(driverClass, url, userName, password);

        db.deleteByPrimaryKey(TABLE_NAME, ID_COL, authorId);

    }
    
    /**
     * Adds a new author to the database. Since the author's id is automatically
     * added by the database, we only need the name and date passed in. If the
     * date is passed in as null or an empty string today's date is used for the
     * creation date.
     * 
     * @param authorName - A String with the author's name
     * @param date - A String containing the date the author was created
     * @throws ClassNotFoundException - Thrown if something goes wrong with 
     * the driver class
     * @throws SQLException - If something is wrong with the SQL statement or
     * connection to the database
     * @throws ParseException - Gets thrown if the date passed in cannot be 
     * parsed as a date
     */
    public void saveAuthor(String authorName, String date) 
            throws ClassNotFoundException, SQLException, ParseException{
        List<String> colNames = new ArrayList<>();
        List<Object> colValues = new ArrayList<>();
        SimpleDateFormat stf = new SimpleDateFormat(SQL_DATE_FORMAT);
        
        colNames.add(NAME_COL);
        colNames.add(DATE_COL);
        
        colValues.add(authorName);
        
        if(date != null && !date.isEmpty()){
            colValues.add(stf.parse(date));
        }else{
            colValues.add(stf.format(new Date()));
        }
        
        db.openConnection(driverClass, url, userName, password);
    
        db.insertRecord(TABLE_NAME, colNames, colValues);

    }
    
    /**
     * Updates an author in the database using the author id to find the author
     * that needs updated in the database. This class must have an author id 
     * provided. The name or date are optional, however they will not be updated
     * if they are not passed in.
     * 
     * @param authorId - A String containing the author id
     * @param authorName - A String with the author name
     * @param date - A String containing the date the author was added to the
     * database
     * @throws ClassNotFoundException - Thrown if something goes wrong with 
     * the driver class
     * @throws SQLException - If something is wrong with the SQL statement or
     * connection to the database
     * @throws ParseException - Gets thrown if the date passed in cannot be 
     * parsed into a date object
     */
    @Override
    public void updateAuthor(String authorId, String authorName, String date) 
            throws ClassNotFoundException, SQLException, ParseException{
        List<String>colNames = new ArrayList<>();
        List<Object>colValues = new ArrayList<>();
        
        if(authorName != null && !authorName.isEmpty()){
            colNames.add(NAME_COL);
            colValues.add(authorName);
        }
        
        if(date != null && !date.isEmpty()){
             colNames.add(DATE_COL);
             DateFormat format = new SimpleDateFormat(SQL_DATE_FORMAT);     
            colValues.add(format.parse(date));
        }
        
        db.openConnection(driverClass, url, userName, password);
        
        db.updateRecord(TABLE_NAME, colNames, colValues, ID_COL, Integer.parseInt(authorId));
        
    }
    
    //For testing purposes
//    public static void main(String[] args) {
//        AuthorDAO author = new AuthorDAO(new MySqlDb(), "com.mysql.jdbc.Driver",
//                "jdbc:mysql://localhost:3306/book","root","admin");
//            
//        List<Author> records = new ArrayList<>();
//        Author aa = new Author();
//        aa.setAuthorId(10);
//        aa.setAuthorName("Hans Christian Anderson");
//        aa.setDateCreated(new Date());
//        
//        try{
//            //author.deleteAuthorbyId("author_id", 7);
//            author.updateAuthor("10", "Hans Anderson", "");
//            records = author.getAllAuthors();
//        }catch(ClassNotFoundException cnfe){
//            System.out.println("Class Not Found");
//        }catch(SQLException sqle){
//            System.out.println("SQL Exception");
//        }catch(ParseException pe){
//            System.out.println("ParseException");
//        }
//        
//        for(Author a: records){
//            System.out.println(a);
//        }
//    }
}
