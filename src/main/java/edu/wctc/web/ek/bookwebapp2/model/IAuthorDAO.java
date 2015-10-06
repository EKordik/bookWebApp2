package edu.wctc.web.ek.bookwebapp2.model;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * The interface for author DAO objects that create objects for use.
 * 
 * @author emmakordik
 * @version 1.00
 */
public interface IAuthorDAO {

    public abstract void deleteAuthorbyId(String authorId) throws ClassNotFoundException, SQLException;

    public abstract List<Author> getAllAuthors() throws ClassNotFoundException, SQLException;
    
    public abstract Author getAuthorbyId(String authorId) throws ClassNotFoundException, SQLException;
    
    public abstract void saveAuthor(String authorName, String date)throws ClassNotFoundException, SQLException, ParseException;
    
    public abstract void updateAuthor(String authorId, String authorName, String date) throws ClassNotFoundException, SQLException, ParseException;
}
