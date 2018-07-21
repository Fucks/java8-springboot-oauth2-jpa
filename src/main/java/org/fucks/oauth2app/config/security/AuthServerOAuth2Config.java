/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fucks.oauth2app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 *
 * @author fucks
 */
@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config
        extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    /**
     * Esse bean é provido pelo proprio spring.
     * Ele é configurado na classe ServerSecurityConfig.java no método authenticationManagerBean.
     * 
     * O autenticador é configurado por um UserDetailsService, localizado na classe AccountService
     * 
     */
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer
                .tokenKeyAccess("permitAll()") 
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        //Cria a lista de clients para autenticação em memoria
        clients
                .inMemory() 
                .withClient("my-client") //nome do client
                .secret("{bcrypt}$2a$10$1tmyeLQqZtRU1WeY/1m5jemeb8E6huG/OTtiJboGAOyWmL1APX02a") //a senha é secret
               
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                
                .scopes("read", "write", "trust")
                .autoApprove(true);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .tokenStore(this.tokenStore) //token store está configurado in memory
                .authenticationManager(this.authenticationManager);
    }
}
