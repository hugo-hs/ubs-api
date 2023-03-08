package br.com.ubs.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ubs.service.ProdutoService;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping
	public ResponseEntity<List<LojistaDTO>> distribuiProdutos(@RequestParam String produto,
			@RequestParam(required = false) Integer lojas) {
		return ResponseEntity.ok(produtoService.distribuiProdutos(produto, lojas));
	}
}
