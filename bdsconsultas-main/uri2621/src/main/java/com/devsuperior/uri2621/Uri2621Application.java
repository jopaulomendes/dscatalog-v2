package com.devsuperior.uri2621;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2621.dto.ProductDTO;
import com.devsuperior.uri2621.projections.ProductNameProjetion;
import com.devsuperior.uri2621.repositories.ProductRepository;

@SpringBootApplication
public class Uri2621Application implements CommandLineRunner {

	@Autowired
	private ProductRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2621Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<ProductNameProjetion>list = repository.busca1("p", 10, 20);
		List<ProductDTO> result1 = list.stream().map(x -> new ProductDTO(x)).collect(Collectors.toList());
		
		System.out.println("\n*** RESULTADO SQL RAIZ ***");
		for (ProductDTO dto : result1) {
			System.out.println(dto);
		}
		System.out.println("\n");
		
		List<ProductDTO> result2 = repository.busca2("p", 10, 20);
		
		System.out.println("\n*** RESULTADO JPQL ***");
		for (ProductDTO dto : result2) {
			System.out.println(dto);
		}
	}
}
