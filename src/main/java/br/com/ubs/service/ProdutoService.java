package br.com.ubs.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ubs.api.LojistaDTO;
import br.com.ubs.api.ProdutoDTO;
import br.com.ubs.file.ProdutoJson;
import br.com.ubs.model.Arquivo;
import br.com.ubs.model.Produto;
import br.com.ubs.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public Set<Produto> encontraProdutosNaoSalvos(ProdutoJson produtoJson, Arquivo arquivo){
		
		Set<Produto> produtosJaSalvos = produtoRepository.findByArquivoNome(arquivo.getNome());
		
		Set<Produto> produtosParaSalvar = produtoJson.toOrm(produtoJson, arquivo);
		
		produtosParaSalvar.removeAll(produtosJaSalvos);
		
		return produtosParaSalvar;
	}
	
	public void saveAll(Set<Produto> produtos) {
		produtoRepository.saveAll(produtos);
	}

	public List<LojistaDTO> distribuiProdutos(String codigoProduto, Integer lojas) {

		List<LojistaDTO> lojistasDTO = createLojistas(lojas);
		
		List<Produto> produtos = produtoRepository.findByCodigo(codigoProduto);
		
		ProdutoDTO produtoDTO = null;
		for (Produto produto : produtos) {
			
			int quantidadeRestante = produto.getQuantidade() % lojas;
			
			for (LojistaDTO lojistaDTO : lojistasDTO) {
				produtoDTO = new ProdutoDTO();
				
				produtoDTO.setPrice(produto.getPreco());
				
				if(quantidadeRestante > 0 && lojistaComMenorQuantidade(lojistaDTO, lojistasDTO)) {
					produtoDTO.setQuantity(produto.getQuantidade() / lojas + quantidadeRestante);
					produtoDTO.setVolume(produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade() / lojas + quantidadeRestante)));
					quantidadeRestante--;
				}else {
					produtoDTO.setQuantity(produto.getQuantidade() / lojas);
					produtoDTO.setVolume(produto.getPreco().multiply(BigDecimal.valueOf(produto.getQuantidade())).divide(BigDecimal.valueOf(lojas), 0));
				}
				
				lojistaDTO.getProdutos().add(produtoDTO);
			}
		}
		
		return lojistasDTO;
	}
	
	private boolean lojistaComMenorQuantidade(LojistaDTO lojistaDTO, List<LojistaDTO> lojistasDTO) {
		Integer menorQuantidade = lojistasDTO.stream()
				.map(LojistaDTO::getQuantidadeTotal)
				.min(Comparator.naturalOrder())
				.orElse(0);
		
		return lojistaDTO.getQuantidadeTotal().equals(menorQuantidade);
	}


	private List<LojistaDTO> createLojistas(Integer lojas){
		List<LojistaDTO> lojistasDTO = new ArrayList<>();
		
		LojistaDTO lojistaDTO = null;
		for (int i = 0; i < lojas; i++) {
			lojistaDTO = new LojistaDTO();
			lojistaDTO.setProdutos(new ArrayList<ProdutoDTO>());
			lojistasDTO.add(lojistaDTO);
		}
		
		return lojistasDTO;
	}
}
