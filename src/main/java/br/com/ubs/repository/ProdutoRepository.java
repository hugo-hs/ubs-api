package br.com.ubs.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.ubs.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	Set<Produto> findByArquivoNome(String nomeArquivo);

	List<Produto> findByCodigo(String produto);
}
