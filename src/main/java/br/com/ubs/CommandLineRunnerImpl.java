package br.com.ubs;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.ubs.file.FileProcessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

	@Autowired
	private FileProcessor fileProcessor;
	
	@Override
	public void run(String... args) throws Exception {
		if (args.length > 0) {
			List<File> files = fileProcessor.collect(args[0]);

			files.parallelStream().forEach(file -> fileProcessor.processFile(file));
		}
		
		log.info("Fim da carga de dados!");
	}
}
