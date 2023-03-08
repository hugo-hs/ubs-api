package br.com.ubs.file;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ubs.model.Arquivo;
import br.com.ubs.model.Produto;
import br.com.ubs.service.ArquivoService;
import br.com.ubs.service.ProdutoService;

@Component
public class FileProcessor {

	private static final String JSON_EXTENSION = ".json";
	
	@Autowired
	private ArquivoService arquivoService;
	
	@Autowired
	private ProdutoService produtoService;

	public List<File> collect(String path){
		final File folder = new File(path);
	    final List<File> fileList = Arrays.asList(folder.listFiles(new FileFilter() {
	        public boolean accept(File pathname) {
	            return pathname.isFile() && pathname.getName().endsWith(JSON_EXTENSION);
	        }
	    }));
	    
	    return fileList;
	}
	
	public void processFile(File file) {
		Arquivo arquivo = arquivoService.buscaArquivo(file.getName());
		ProdutoJson produtosJson = null;
		Set<Produto> produtos = null;
		
		if(arquivo != null) {
			if(arquivo.isProcessadoCompletamente()) {
				return;
			}
			produtosJson = readContent(file);
			produtos = produtoService.encontraProdutosNaoSalvos(produtosJson, arquivo);
		} else {
			produtosJson = readContent(file);
			arquivo = new Arquivo(file.getName());
			arquivoService.save(arquivo);
			produtos = produtosJson.toOrm(produtosJson, arquivo);
		}
		
		produtoService.saveAll(produtos);
		arquivoService.atualizaParaArquivoProcessado(arquivo);
	}
	
	private ProdutoJson readContent(File file) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<ProdutoJson> typeReference = new TypeReference<ProdutoJson>() {};
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			return mapper.readValue(inputStream, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
