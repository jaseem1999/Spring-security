package com.security.jwt.without_jpa.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

import com.security.jwt.without_jpa.MYUserDetailsServices;
import com.security.jwt.without_jpa.jwt.JwtRequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MYUserDetailsServices userDetailsServices; 
	
	@Autowired
	private JwtRequestFilter jwtReqFilter;
	
	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.userDetailsService(userDetailsServices);
     }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return NoOpPasswordEncoder.getInstance();
	 }
	 
	 @Override
	 protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable() // Disable CSRF for simplicity, you can enable it later if needed
	            .authorizeRequests()
	            .antMatchers("/api/auth").permitAll() // Permit access to /authenticate endpoint
	            .anyRequest().authenticated() // All other requests require authentication
	        	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	        http.addFilterBefore(jwtReqFilter, UsernamePasswordAuthenticationFilter.class);
	 }
	 
	 @Autowired
	 @Bean
     public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
     }
}
