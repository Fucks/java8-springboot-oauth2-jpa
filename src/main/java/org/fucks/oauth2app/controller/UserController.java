/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fucks.oauth2app.controller;

import java.util.Optional;
import org.fucks.oauth2app.entity.User;
import org.fucks.oauth2app.repository.IUserRepository;
import org.fucks.oauth2app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author fucks
 */
@RestController
@RequestMapping("api/user")
public class UserController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private IUserRepository userRepository;
    
    @PostMapping(path = "register")
    public User create(@RequestBody User user){
        return this.accountService.create(user);
    }
    
    @GetMapping(path = "{id}")
    public User find(@PathVariable Long id){
        Optional<User> user = this.userRepository.findById(id);
        
        if(user.isPresent())
            return user.get();
        
        return null;
    }
    
    @PutMapping(path = "{id}")
    public User update(@PathVariable Long id, @RequestBody User user){
        
        Optional<User> dbUser = this.userRepository.findById(id);
        User _dbUser = null;
        
        if(dbUser.isPresent())
            _dbUser = dbUser.get();
        else
            throw new IllegalArgumentException("User not found.");
        
        _dbUser.setName(user.getName());
        _dbUser.setEmail(user.getEmail());
        _dbUser.setPassword(user.getPassword());
        _dbUser.setUsername(user.getUsername());
        
        this.userRepository.save(_dbUser);
        
        return _dbUser;
    }

    @DeleteMapping
    public void delete(@PathVariable Long id){
        this.userRepository.deleteById(id);
    }
    
    @GetMapping("find-all")
    public Page<User> findAll(Pageable page){
        return this.userRepository.findAll(page);
    }
}
