package com.jcmj.core;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;

import com.jcmj.domain.Usuario;

public class AuthUser extends User{

      public AuthUser(Usuario usuario) {
		super(usuario.getLogin(),usuario.getSenha(),Collections.emptyList());
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
