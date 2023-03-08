package br.com.ubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ubs.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{

	Arquivo findByNome(String name);
}
