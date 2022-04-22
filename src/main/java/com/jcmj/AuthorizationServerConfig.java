package com.jcmj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService detailsService;
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		clients
			.inMemory()
				.withClient("jcmj")
				.secret(passwordEncoder.encode("abc123"))
				.authorizedGrantTypes("password", "refresh_token")
				.scopes("write", "read")
				.accessTokenValiditySeconds(60 * 60 * 6) //6 horas
				.refreshTokenValiditySeconds(60 * 24 *60 * 60) // 60 dias
				
		     .and()
				.withClient("analitics")
				.secret(passwordEncoder.encode("abc123"))
				.authorizedGrantTypes("authorization_code")
				.scopes("write")
				.redirectUris("http://casanobreweb.com.br")
				
		     .and()
		     	.withClient("consumidor")
				.secret(passwordEncoder.encode("abc123"))
				.authorizedGrantTypes("client_credentials")
				.scopes("read");
					
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//security.checkTokenAccess("isAuthenticated()");
		security.checkTokenAccess("permitAll()");
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		  .authenticationManager(authenticationManager)
		  .userDetailsService(detailsService)
		  .reuseRefreshTokens(false);
	}
}
