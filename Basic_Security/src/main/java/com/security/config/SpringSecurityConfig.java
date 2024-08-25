package com.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
@SuppressWarnings("deprecation")
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("adil").password("123321").roles("ADMIN");
		auth.inMemoryAuthentication().withUser("jaseem").password("1233").roles("User");
	}
	
	
//	for all api
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// TODO Auto-generated method stub
//		http.csrf().disable();
//		http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
//	}

	// specific url based
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	    http.csrf().disable()
//	        .authorizeRequests().antMatchers("/rest/**").fullyAuthenticated() // Requires authentication
//	        .and()
//	        .httpBasic();
//	}


	// security based on ROLE
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.authorizeRequests().antMatchers("/rest/**").hasAnyRole("ADMIN").anyRequest().fullyAuthenticated().and()
					.httpBasic();
		}
	

	
	@Bean
	public static NoOpPasswordEncoder passwordEncript() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}
}
