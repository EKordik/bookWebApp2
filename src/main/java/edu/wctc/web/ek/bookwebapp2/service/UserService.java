package edu.wctc.web.ek.bookwebapp2.service;

import edu.wctc.web.ek.bookwebapp2.entity.Authorities;
import edu.wctc.web.ek.bookwebapp2.entity.Users;
import edu.wctc.web.ek.bookwebapp2.repository.AuthoritiesRepository;
import edu.wctc.web.ek.bookwebapp2.repository.UsersRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author emmakordik
 */
@Repository("userService")
@Transactional(readOnly=true)
public class UserService {
    private transient final Logger LOG = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UsersRepository userRepo;
    
    @Autowired
    private AuthoritiesRepository authorityRepo;
   
    public UserService(){
        
    }
    
    public List<Users> findAll(){
        return userRepo.findAll();
    }

    public Users findByUsername(String username){
        return userRepo.findByUserName(username);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void remove(Users user){
        LOG.debug("Deleting user: " + user.getUsername());
        userRepo.delete(user);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Users edit(Users user){
        return userRepo.saveAndFlush(user);
    }
    
    public List<Authorities> findAllRoles(){
        return authorityRepo.findAll();
    }
}
