package com.jcmj.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jcmj.domain.Usuario;
import com.jcmj.domain.repository.Usuarios;
@Service
public class JpaUserDetailsService implements UserDetailsService {
	
	@Autowired
	Usuarios usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByLogin(username)
				.orElseThrow(()-> new UsernameNotFoundException("Usuário não encontrado"));
		
		return new AuthUser(usuario);
	}

}
