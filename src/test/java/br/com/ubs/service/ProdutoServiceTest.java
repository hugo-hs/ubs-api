package br.com.ubs.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.ubs.api.LojistaDTO;
import br.com.ubs.file.DataJson;
import br.com.ubs.file.ProdutoJson;
import br.com.ubs.model.Arquivo;
import br.com.ubs.model.Produto;
import br.com.ubs.repository.ProdutoRepository;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

	@InjectMocks
	private ProdutoService produtoService;
	
	@Mock
	private ProdutoRepository produtoRepository;
	
	@Test
	void deveEncontrarApenasProdutosNaoSalvos() {
		Arquivo arquivo = new Arquivo("file.json");
		Set<Produto> produtosJaSalvos = buildProdutos(arquivo);
		when(produtoRepository.findByArquivoNome(anyString())).thenReturn(produtosJaSalvos);
		
		Set<Produto> produtosNaoSalvos = produtoService.encontraProdutosNaoSalvos(buildProdutoJson(), arquivo);
		
		assertTrue(produtosNaoSalvos.size() == 2);
		assertFalse(produtosNaoSalvos.stream().filter(produto -> produto.getQuantidade().equals(10) && produto.getPreco().equals(new BigDecimal("10.50"))).findAny().isPresent());
		assertTrue(produtosNaoSalvos.stream().filter(produto -> produto.getQuantidade().equals(30) && produto.getPreco().equals(new BigDecimal("6.55"))).findAny().isPresent());
		assertFalse(produtosNaoSalvos.stream().filter(produto -> produto.getQuantidade().equals(15) && produto.getPreco().equals(new BigDecimal("10.52"))).findAny().isPresent());
		assertTrue(produtosNaoSalvos.stream().filter(produto -> produto.getQuantidade().equals(20) && produto.getPreco().equals(new BigDecimal("9.99"))).findAny().isPresent());
	}
	
	@Test
	void deveDistribuirProdutosDeFormaJusta() {
		List<Produto> produtos = buildProdutos();
		when(produtoRepository.findByCodigo(anyString())).thenReturn(produtos);
		
		List<LojistaDTO> lojistas = produtoService.distribuiProdutos("ARNC", 2);

		assertTrue(lojistas.stream().allMatch(lojista -> lojista.getQuantidadeTotal().equals(30)));
	}

	private Set<Produto> buildProdutos(Arquivo arquivo) {
		Set<Produto> produtos = new HashSet<>();
		produtos.add(buildProduto(10, new BigDecimal("10.50"), arquivo));
		produtos.add(buildProduto(15, new BigDecimal("10.51"), arquivo));
		produtos.add(buildProduto(15, new BigDecimal("10.52"), arquivo));
		produtos.add(buildProduto(20, new BigDecimal("10.53"), arquivo));
		
		return produtos;
	}
	
	private List<Produto> buildProdutos() {
		List<Produto> produtos = new ArrayList<>();
		produtos.add(buildProduto(10, new BigDecimal("10.50"), null));
		produtos.add(buildProduto(15, new BigDecimal("10.51"), null));
		produtos.add(buildProduto(15, new BigDecimal("10.52"), null));
		produtos.add(buildProduto(20, new BigDecimal("10.53"), null));
		
		return produtos;
	}
	
	private Produto buildProduto(Integer quantidade, BigDecimal preco, Arquivo arquivo) {
		return Produto.builder()
				.arquivo(arquivo)
				.codigo("ARNC")
				.quantidade(quantidade)
				.preco(preco)
				.build();
	}
	
	private ProdutoJson buildProdutoJson() {
		ProdutoJson produtoJson = new ProdutoJson();
		produtoJson.setData(new HashSet<>());
		produtoJson.getData().add(buildDataJson(10, "$10.50"));
		produtoJson.getData().add(buildDataJson(30, "$6.55"));
		produtoJson.getData().add(buildDataJson(15, "$10.52"));
		produtoJson.getData().add(buildDataJson(20, "$9.99"));
		
		return produtoJson;
	}

	private DataJson buildDataJson(Integer quantity, String price) {
		return DataJson.builder()
				.product("ARNC")
				.quantity(quantity)
				.price(price)
				.build();
	}
}
