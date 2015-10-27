package edu.wctc.web.ek.bookwebapp2.repository;

import edu.wctc.web.ek.bookwebapp2.entity.Author;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author emmakordik
 */
public interface AuthorRepository extends JpaRepository<Author, Integer>, Serializable {
    
}
