package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The author service class deals with all the actions that relate to author 
 * information. This class is set up to get all authors, get a particular author,
 * delete an author, update an author, or add a new author.
 * 
 * @author emmakordik
 * @version 1.00
 */
public class AuthorService {
    private IAuthorDAO authorStrategy;
    
    /**
     * Constructor that initializes an Author DAO as a low level class to do 
     * the dirty work. The author DAO is passed in from which ever class calls
     * the Author Service which opens the door for dependency injection and 
     * flexible code.
     * 
     * @param authorStrategy - an IAuthorDAO object that is used to handle the 
     * low level author work
     */
    public AuthorService(IAuthorDAO authorStrategy){
        this.authorStrategy = authorStrategy;
    }
    
    /**
     * This method gets all the authors and returns them as a list. 
     * 
     * @return - A list of author objects
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException{
        return authorStrategy.getAllAuthors();
    }
    
    /**
     * Gets one author using the authors unique identifier.
     * 
     * @param authorId - A String which is the unique identifier for the author
     * @return - Returns an author object
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public Author getAuthorbyId(String authorId) throws ClassNotFoundException, SQLException{
        return authorStrategy.getAuthorbyId(authorId);
    }
    
    /**
     * Deletes an author using the unique identifier to find the correct author.
     * 
     * @param authorId - A String which is the unique identifier for the author
     * to be deleted
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public void deleteAuthor(String authorId) throws ClassNotFoundException, SQLException{
        authorStrategy.deleteAuthorbyId(authorId);
    }
    
    /**
     * Saves an author into the data source
     * 
     * @param authorName - A String which is the name of the author to be saved
     * @param date - A string which represents the date the author is being added
     * on. If a date is not provided todays date will be used instead.
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public void saveAuthor(String authorName, String date) throws ClassNotFoundException, SQLException, ParseException{
        authorStrategy.saveAuthor(authorName, date);
    }
    
    /**
     * Update an author in the data source. This method using the author's
     * unique identifier to find the author that is being updated.
     * 
     * @param authorId - A String which is the unique identifier for the author.
     * @param authorName - The author's name as a String
     * @param date - The date the author was created as a String
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws ParseException 
     */
    public void updateAuthor(String authorId, String authorName, String date) throws ClassNotFoundException, SQLException, ParseException{
        authorStrategy.updateAuthor(authorId, authorName, date);
    }
    
    //For testing purposes
//    public static void main(String[] args) {
//       // AuthorService service = new AuthorService(new AuthorDAO());
//        List<Author> authors = new ArrayList<>();
//         Author aa = new Author();
//         aa.setAuthorId(12);
//        aa.setAuthorName("Adolf Huxley");
//        aa.setCreationDate(new Date());
//        
//        try{
//           //service.deleteAuthorbyId("author_id", 9);
//          // service.saveAuthor(aa);
//            service.updateAuthor(aa);
//           authors = service.getAllAuthors();
//            
//        }catch(ClassNotFoundException cnfe){
//            System.out.println("Class Not Found");
//        }catch(SQLException sqle){
//            System.out.println("SQL Exception");
//        }
//        
//        for(Author a: authors){
//            System.out.println(a);
//        }
//    }
}
