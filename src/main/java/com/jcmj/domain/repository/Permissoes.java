package com.jcmj.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jcmj.domain.Grupo;
import com.jcmj.domain.Permissao;

@Repository
public interface Permissoes extends JpaRepository<Permissao, Long> {
	
	List<Permissao> findByGrupos(Grupo grupo);

}
