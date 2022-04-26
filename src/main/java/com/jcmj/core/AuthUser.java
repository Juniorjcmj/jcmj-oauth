package com.jcmj.core;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;

import com.jcmj.domain.Usuario;

import lombok.Getter;
@Getter
public class AuthUser extends User{
	
	private static final long serialVersionUID = 1L;
	
	private Long userId;
	private String fullName;

      public AuthUser(Usuario usuario) {
		super(usuario.getLogin(),usuario.getSenha(),Collections.emptyList());
		
		this.userId = usuario.getId();
		this.fullName = usuario.getNome();
	}

	
}
