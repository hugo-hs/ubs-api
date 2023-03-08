package br.com.ubs.api;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO {

	private Integer quantity;
	private BigDecimal price;
	private BigDecimal volume;
}
