package br.com.ubs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.ubs.model.Arquivo;
import br.com.ubs.repository.ArquivoRepository;

@Service
public class ArquivoService {

	@Autowired
	private ArquivoRepository arquivoRepository;
	
	public Arquivo buscaArquivo(String nomeArquivo) {
		return arquivoRepository.findByNome(nomeArquivo);
	}
	
	public void atualizaParaArquivoProcessado(Arquivo arquivo) {
		arquivo.setProcessadoCompletamente(true);
		arquivoRepository.save(arquivo);
	}

	public void save(Arquivo arquivo) {
		arquivoRepository.save(arquivo);
	}
}
