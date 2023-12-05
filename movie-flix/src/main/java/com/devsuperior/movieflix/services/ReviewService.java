package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private AuthService authService;
	
	@Transactional(readOnly = true)
	public Page<ReviewDTO> findAllPage(Pageable pageable){
		Page<Review> page = repository.findAll(pageable);
		return page.map(x -> new ReviewDTO(x));
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findByMovie (Long movieId){
		Movie movie = movieRepository.getOne(movieId);
		List<Review> list = repository.findByMovie(movie);
		return list.stream().map(x -> new ReviewDTO(x)).collect(Collectors.toList());
	}
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		Review entity = new Review();
		entity.setText(dto.getText());
		entity.setMovie(movieRepository.getOne(dto.getMovieId()));
		entity.setUser(authService.authenticated());
		entity = repository.save(entity);
		return new ReviewDTO(entity);
	}
}
