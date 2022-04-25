package com.jcmj.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.Usuario;

@Repository
public interface Usuarios extends JpaRepository<Usuario, Long> {

		Optional<Usuario> findByLogin(String login);
}
