package edu.wctc.web.ek.bookwebapp2.repository;

import edu.wctc.web.ek.bookwebapp2.entity.Users;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author emmakordik
 */
public interface UsersRepository extends JpaRepository<Users, Integer>, Serializable {
    @Query("SELECT u FROM Users u WHERE u.username = (:username)")
    public Users findByUserName(@Param("username") String username);
}
