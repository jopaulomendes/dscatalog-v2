package com.devsuperior.uri2990;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2990.dto.EmpregadoDeptDTO;
import com.devsuperior.uri2990.projections.EmpregadoDeptProjection;
import com.devsuperior.uri2990.repositories.EmpregadoRepository;

@SpringBootApplication
public class Uri2990Application implements CommandLineRunner {

	@Autowired
	private EmpregadoRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2990Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		List<EmpregadoDeptProjection> list1 = repository.busca1();
		List<EmpregadoDeptDTO> result1 = list1.stream().map(x -> new EmpregadoDeptDTO(x)).collect(Collectors.toList());
		for (EmpregadoDeptDTO empregadoDeptDTO : result1) {
			System.out.println(empregadoDeptDTO);
		}
		System.out.println("-------------------------------");
		
		List<EmpregadoDeptDTO> result2 = repository.busca2();
		for (EmpregadoDeptDTO empregadoDeptDTO : result2) {
			System.out.println(empregadoDeptDTO);
		}
		System.out.println("-------------------------------");
		
		List<EmpregadoDeptProjection> list3 = repository.busca3();
		List<EmpregadoDeptDTO> result3 = list3.stream().map(x -> new EmpregadoDeptDTO(x)).collect(Collectors.toList());
		for (EmpregadoDeptDTO empregadoDeptDTO : result3) {
			System.out.println(empregadoDeptDTO);
		}
	}
}
