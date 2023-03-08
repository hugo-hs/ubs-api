package br.com.ubs.api;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LojistaDTO {
	
	private List<ProdutoDTO> produtos;

	public Integer getQuantidadeTotal() {
		return produtos != null ? produtos.stream().mapToInt(produto -> produto.getQuantity()).sum() : null;
	}
	
	public BigDecimal getFinanceiro() {
		return produtos != null ? produtos.stream().map(produto -> produto.getVolume()).reduce(BigDecimal.ZERO, BigDecimal::add) : null;
	}
	
	public BigDecimal getPrecoMedio() {
		if(getFinanceiro() != null && getQuantidadeTotal() != null) {
			return getFinanceiro().divide(BigDecimal.valueOf(getQuantidadeTotal()), 0);
		}
		return null;
	}
}
