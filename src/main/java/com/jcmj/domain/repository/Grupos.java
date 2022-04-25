package com.jcmj.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.Grupo;
import com.jcmj.domain.Usuario;
@Repository
public interface Grupos extends JpaRepository<Grupo, Long> {
	
	List<Grupo> findByUsuarios(Usuario usuario);


	Grupo findByNome(String nome);

}
