package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author emmakordik
 */
public interface IAuthorDAO {

    void deleteAuthorbyId(String authorId) throws ClassNotFoundException, SQLException;

    List<Author> getAllAuthors() throws ClassNotFoundException, SQLException;
    
    public abstract Author getAuthorbyId(String authorId) throws ClassNotFoundException, SQLException;
    
    public abstract void saveAuthor(String authorName, String date)throws ClassNotFoundException, SQLException, ParseException;
    
    public void updateAuthor(String authorId, String authorName, String date) throws ClassNotFoundException, SQLException, ParseException;
}
