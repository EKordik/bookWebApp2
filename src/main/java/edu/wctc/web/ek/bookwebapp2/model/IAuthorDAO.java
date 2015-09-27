package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author emmakordik
 */
public interface IAuthorDAO {

    void deleteAuthorbyId(String authorId) throws ClassNotFoundException, SQLException;

    List<Author> getAllAuthors() throws ClassNotFoundException, SQLException;
    
    public abstract void saveAuthor(Author author) throws ClassNotFoundException, SQLException;
    
    public void updateAuthor(Author author) throws ClassNotFoundException, SQLException;
}
