package br.com.ubs.file;

import java.util.Set;
import java.util.stream.Collectors;

import br.com.ubs.model.Arquivo;
import br.com.ubs.model.Produto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoJson {

	private Set<DataJson> data;
	
	public Set<Produto> toOrm(ProdutoJson produtoJson, Arquivo arquivo){
		return produtoJson.getData().stream().map(data -> data.toOrm(arquivo)).collect(Collectors.toSet());
	}
}