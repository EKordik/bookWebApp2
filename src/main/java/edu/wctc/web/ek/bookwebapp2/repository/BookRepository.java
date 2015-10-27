package edu.wctc.web.ek.bookwebapp2.repository;

import edu.wctc.web.ek.bookwebapp2.entity.Book;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author emmakordik
 */
public interface BookRepository extends JpaRepository<Book, Integer>, Serializable {
    
}
