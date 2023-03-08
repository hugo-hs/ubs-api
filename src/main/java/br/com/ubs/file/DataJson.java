package br.com.ubs.file;

import java.math.BigDecimal;

import br.com.ubs.model.Arquivo;
import br.com.ubs.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DataJson {

	private String product;
	private Integer quantity;
	private String price;
	private String type;
	private String industry;
	private String origin;
	
	public Produto toOrm(Arquivo arquivo) {
	return Produto.builder()
			.arquivo(arquivo)
			.codigo(product)
			.industria(industry)
			.origem(origin)
			.preco(new BigDecimal(price.replace("$", "")))
			.quantidade(quantity)
			.tipo(type)
			.build();
}
}
