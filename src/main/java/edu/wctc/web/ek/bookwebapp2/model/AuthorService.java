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
    
    public AuthorService(IAuthorDAO authorStrategy){
        this.authorStrategy = authorStrategy;
    }
    
    public List<Author> getAllAuthors() throws ClassNotFoundException, SQLException{
        return authorStrategy.getAllAuthors();
    }
    
    public void deleteAuthor(String authorId) throws ClassNotFoundException, SQLException{
        authorStrategy.deleteAuthorbyId(authorId);
    }
    
    public void saveAuthor(Author author) throws ClassNotFoundException, SQLException{
        authorStrategy.saveAuthor(author);
    }
    
    public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
        authorStrategy.updateAuthor(author);
    }
    
    public static void main(String[] args) {
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
    }
}
