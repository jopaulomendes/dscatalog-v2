package com.devsuperior.uri2611;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.devsuperior.uri2611.dto.MovieDTO;
import com.devsuperior.uri2611.projections.MovieNameProjection;
import com.devsuperior.uri2611.repositories.MovieRepository;

@SpringBootApplication
public class Uri2611Application implements CommandLineRunner {
	
	@Autowired
	private MovieRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(Uri2611Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<MovieNameProjection> list = repository.busca1("Action");
		List<MovieDTO> result1 = list.stream().map(x -> new MovieDTO(x)).collect(Collectors.toList());
		
		System.out.println("\n*** RESULTADO SQL RAIZ ***");
		for (MovieDTO movieDTO : result1) {
			System.out.println(movieDTO);
		}
		System.out.println("\n");
		
		List<MovieDTO> reslt2 = repository.busca2("Action");
		
		System.out.println("\n*** RESULTADO JPQL ***");
		for (MovieDTO movieDTO : reslt2) {
			System.out.println(movieDTO);
		}
	}
}
