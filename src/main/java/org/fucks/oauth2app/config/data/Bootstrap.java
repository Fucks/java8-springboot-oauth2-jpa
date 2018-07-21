/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fucks.oauth2app.config.data;

import org.fucks.oauth2app.entity.User;
import org.fucks.oauth2app.service.AccountService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author fucks
 */
public class Bootstrap implements InitializingBean{

    @Autowired
    private AccountService accountService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        createDefaultUser();
    }
    
    public void createDefaultUser(){
        
        if(this.accountService.countUsers() == 0) {
            final User user = new User(
                null,
                "Administrador do sistema",
                "admin",
                "admin",
                "admin@admin.com"
            );

            this.accountService.create(user);
        }
    }
    
}
