package com.ketul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/check-it", "/create-TimeTable").hasRole("USER")
                .antMatchers("/", "/save", "/anotherLogin", "/conform/{token}").permitAll()
                .and()
                .rememberMe().userDetailsService(myUserDetailsService)
                .and()
                .formLogin()
                .usernameParameter("email")
                .passwordParameter("pass")
                .permitAll()
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll();
    }

}
