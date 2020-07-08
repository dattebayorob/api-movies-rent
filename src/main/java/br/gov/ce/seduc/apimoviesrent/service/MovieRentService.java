package br.gov.ce.seduc.apimoviesrent.service;

public interface MovieRentService {
	public void saveRent( Long movieId, Long userId );
	public void returnRent( Long movieId, Long userId );
	public Integer countRentedByMovie( Long movieId );
	public boolean isMovieRentedByUser( Long movieId, Long userId);
}
