package com.jcmj;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig  extends AuthorizationServerConfigurerAdapter{

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService detailsService;	
	
	@Autowired
	//private RedisConnectionFactory connectionFactory;
	
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
//http://localhost:98/oauth/authorize?response_type=code&client_id=analitics&redirect_uri=http://casanobreweb.com.br&code_challenge=fsdhfshdjfhsdfhskdhfk&code_challenge_method=plain
				
				.and()
				.withClient("webadmin")
				.secret(passwordEncoder.encode("abc123"))
				.authorizedGrantTypes("implicit")
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
		security.checkTokenAccess("permitAll()")
		.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		  .authenticationManager(authenticationManager)
		  .userDetailsService(detailsService)
		  .reuseRefreshTokens(false)
		  .accessTokenConverter(jwtAccessTokenConverter())
		  .tokenGranter(tokenGranter(endpoints));
		 // .tokenStore(redisTokenStore());
		
	}
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		//jwtAccessTokenConverter.setSigningKey("sustentafogoqueavitoriaenossa/marinhadobrasil");
		 var jksResource = new ClassPathResource("keystores/algafood.jks");
		 var keyStorePass = "123456";
		 var keyPairAlias = "algafood";
		 
		 var keyStoreKeyFactory = new KeyStoreKeyFactory(jksResource,keyStorePass.toCharArray());
		 var keyPair = keyStoreKeyFactory.getKeyPair(keyPairAlias);
		 
		 jwtAccessTokenConverter.setKeyPair(keyPair);
		 
		return jwtAccessTokenConverter;
	}
	//conecção com banco de dados redis 
	/*
	 * private TokenStore redisTokenStore() { return new
	 * RedisTokenStore(connectionFactory); }
	 */
	/*
	 * @Override public void configure(AuthorizationServerEndpointsConfigurer
	 * endpoints) { endpoints.tokenGranter(tokenGranter(endpoints)); }
	 */
	
	private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
		var pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(endpoints.getTokenServices(),
				endpoints.getAuthorizationCodeServices(), endpoints.getClientDetailsService(),
				endpoints.getOAuth2RequestFactory());
		
		var granters = Arrays.asList(
				pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());
		
		return new CompositeTokenGranter(granters);
	}
}
