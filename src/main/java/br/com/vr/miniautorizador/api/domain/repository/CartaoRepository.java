package br.com.vr.miniautorizador.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vr.miniautorizador.api.domain.model.Cartao;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String>{
	
}
