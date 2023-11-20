package com.Bug_Tracker.configuration;


       import com.Bug_Tracker.filter.JWTAuthorizationFilter;
       import com.Bug_Tracker.filter.JwtAccessDeniedHandler;
       import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Qualifier;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
       import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
       import org.springframework.security.config.annotation.web.builders.HttpSecurity;
        import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
        import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
        import org.springframework.security.core.userdetails.UserDetailsService;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Order(1)
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private JWTAuthorizationFilter JWTAuthorizationFilter;
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    public SecurityConfiguration(JWTAuthorizationFilter jwtAuthorizationFilter,

                                 @Qualifier("UserDetailService") UserDetailsService userDetailsService
            ,BCryptPasswordEncoder bCryptPasswordEncoder,JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        JWTAuthorizationFilter = jwtAuthorizationFilter;

        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and().authorizeRequests().antMatchers("/user/register","/user/login","/adminPortal/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilterBefore(JWTAuthorizationFilter, UsernamePasswordAuthenticationFilter.class); // this filter happens before all other filters
    }

    @Primary
    @Bean
    @Qualifier("auth1")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}