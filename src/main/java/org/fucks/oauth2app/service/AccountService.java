/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fucks.oauth2app.service;

import java.util.Optional;
import org.fucks.oauth2app.entity.User;
import org.fucks.oauth2app.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author fucks
 */
@Service
public class AccountService implements UserDetailsService {
    
    @Autowired
    private IUserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public User create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        user = this.userRepository.save(user);
        
        return user;
    }
    
    public Long countUsers(){
        return this.userRepository.count();
    }
    
    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        User user = new User();
        user.setUsername(string);
        
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny();
        Example ex = Example.of(user, exampleMatcher);
        
        Optional<User> _user = this.userRepository.findOne(ex);
 
        if(_user.isPresent()){
            return _user.get();
        }
        else {
            throw new UsernameNotFoundException(String.format("User with username %s not found.", string));
        }
        
    }
    
}
