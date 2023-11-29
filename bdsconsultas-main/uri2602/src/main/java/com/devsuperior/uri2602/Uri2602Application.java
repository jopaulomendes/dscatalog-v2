package com.devsuperior.uri2602;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2602.dto.CustomerDTO;
import com.devsuperior.uri2602.projections.CustomerNameProjection;
import com.devsuperior.uri2602.repositories.CustomRepository;

@SpringBootApplication
public class Uri2602Application  implements CommandLineRunner{

	@Autowired
	private CustomRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2602Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<CustomerNameProjection> list = repository.name1("rs");
		List<CustomerDTO> result1 = list.stream().map(x -> new CustomerDTO(x)).collect(Collectors.toList());
		
		System.out.println("\n*** RESUTADO SQL RAIZ");
		for (CustomerDTO customerNameProjection : result1) {
			System.out.println(customerNameProjection);
		}
		System.out.println("\n**");
		
		List<CustomerDTO> result2 = repository.name2("rs");
		
		System.out.println("\n*** RESUTADO SQL SPQL");
		for (CustomerDTO customerNameProjection : result2) {
			System.out.println(customerNameProjection);
		}
		System.out.println("\n**");
	}
}
