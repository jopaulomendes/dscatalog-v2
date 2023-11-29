package com.devsuperior.uri2611.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2611.dto.MovieDTO;
import com.devsuperior.uri2611.entities.Movie;
import com.devsuperior.uri2611.projections.MovieNameProjection;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query(nativeQuery = true, value = "select m.id, m.name "
			+ "from  movies m "
			+ "inner join genres g on m.id_genres = g.id "
			+ "where g.description = :genreName")
	List<MovieNameProjection> busca1(String genreName);
	
	@Query("select new com.devsuperior.uri2611.dto.MovieDTO(m.id, m.name) "
			+ "from  Movie m "
			+ "where m.genre.description = :genreName")
	List<MovieDTO> busca2(String genreName);
}
