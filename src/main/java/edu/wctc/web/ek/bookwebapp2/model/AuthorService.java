package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author emmakordik
 */
public class AuthorService {
    private IAuthorDAO authorStrategy;
    
    public AuthorService(){
        authorStrategy = new AuthorDAO("com.mysql.jdbc.Driver",
                "jdbc:mysql://localhost:3306/book","root","admin");
    }
    
    public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException{
        return authorStrategy.getAllAuthors();
    }
    
    public void deleteAuthorbyId(String primaryKeyColumnName, 
            Object primaryKeyValue) throws ClassNotFoundException, SQLException{
        authorStrategy.deleteAuthorbyId(primaryKeyColumnName, primaryKeyValue);
    }
    
    public void saveAuthor(Author author) throws ClassNotFoundException, SQLException{
        authorStrategy.saveAuthor(author);
    }
    
    public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
        authorStrategy.updateAuthor(author);
    }
    
    public static void main(String[] args) {
        AuthorService service = new AuthorService();
        List<Author> authors = new ArrayList<>();
         Author aa = new Author();
         aa.setAuthorId(12);
        aa.setAuthorName("Adolf Huxley");
        aa.setCreationDate(new Date());
        
        try{
           //service.deleteAuthorbyId("author_id", 9);
          // service.saveAuthor(aa);
            service.updateAuthor(aa);
           authors = service.getAllAuthors();
            
        }catch(ClassNotFoundException cnfe){
            System.out.println("Class Not Found");
        }catch(SQLException sqle){
            System.out.println("SQL Exception");
        }
        
        for(Author a: authors){
            System.out.println(a);
        }
    }
}
