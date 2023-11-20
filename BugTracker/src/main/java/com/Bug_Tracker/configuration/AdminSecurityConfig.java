package com.Bug_Tracker.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


// the reason I needed to make another configuration class is because there is no way to have two over-ridding configure methods in one class
// then since I had to create another security config class I needed another authentication manager. Since there were two auth managers, I had to use qualifier to specify which one I wanted
@Configuration
@EnableWebSecurity
public class AdminSecurityConfig extends WebSecurityConfigurerAdapter {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserDetailsService userDetailsService2;
    @Autowired
    public AdminSecurityConfig(@Qualifier("adminServiceImpl") UserDetailsService userDetailsService2
            ,BCryptPasswordEncoder bCryptPasswordEncoder) {


        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService2 = userDetailsService2;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService2).passwordEncoder(bCryptPasswordEncoder);
    }

   @Bean
   @Qualifier("auth2")
    public AuthenticationManager authenticationManagerBean2() throws Exception {
        return super.authenticationManagerBean();
    }
}
