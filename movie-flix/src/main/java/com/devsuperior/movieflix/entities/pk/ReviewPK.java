package com.devsuperior.movieflix.entities.pk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.User;

@Embeddable
public class ReviewPK implements Serializable{
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;
	
	public ReviewPK() {
	}

	public ReviewPK(User user, Movie movie) {
		super();
		this.user = user;
		this.movie = movie;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(movie, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewPK other = (ReviewPK) obj;
		return Objects.equals(movie, other.movie) && Objects.equals(user, other.user);
	}
}
