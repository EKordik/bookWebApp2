package edu.wctc.web.ek.bookwebapp2.repository;

import edu.wctc.web.ek.bookwebapp2.entity.Authorities;
import edu.wctc.web.ek.bookwebapp2.entity.Users;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author emmakordik
 */
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer>, Serializable {
    
}
