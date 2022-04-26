package com.jcmj.core;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{	
	     
	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { auth.inMemoryAuthentication() .withUser("admin")
	 * .password(bCryptPasswordEncoder().encode("123")) .roles("ADMIN") .and()
	 * .withUser("junior") .password(bCryptPasswordEncoder().encode("123"))
	 * .roles("ADMIN");
	 * 
	 * }
	 */
   @Bean    
   CorsConfigurationSource corsConfigurationSource() {
	   CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
	   configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
	   final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	   source.registerCorsConfiguration("/**", configuration);
	   return source;
   }
   
   @Bean
   public  BCryptPasswordEncoder bCryptPasswordEncoder() {
	   return new BCryptPasswordEncoder();
   }
    @Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	/*
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() {
	 * 
	 * return super.userDetailsService(); }
	 */
    
}
