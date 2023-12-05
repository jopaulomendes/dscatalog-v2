package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("select obj from Movie obj where :genre is null or obj.genre = :genre order by obj.title")
	Page<Movie> findByGenre(Genre genre, Pageable pageable);
	
	@Query("select obj from Movie obj join fetch obj.genre where obj in :movies")
	List<Movie> findMoviesAndGenres(List<Movie> movies);
}
