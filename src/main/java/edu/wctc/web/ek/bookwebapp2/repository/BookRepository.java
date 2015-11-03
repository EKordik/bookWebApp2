package edu.wctc.web.ek.bookwebapp2.repository;

import edu.wctc.web.ek.bookwebapp2.entity.Book;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author emmakordik
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
    @Query("SELECT b from Book b WHERE b.authorId.authorName = (:name)")
    public List<Book> findBooksforAuthor(@Param("name") String authorName);
    
    public List<Book>findBooksByTitle(String title);
    
    public List<Book>findBooksByIsbn(String isbn);
}
