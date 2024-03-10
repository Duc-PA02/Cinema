package com.example.cinema.service.iservice;

import com.example.cinema.dto.MovieByTicketCount;
import com.example.cinema.dto.MovieCreateRequest;
import com.example.cinema.dto.MovieUpdateRequest;
import com.example.cinema.entity.Movie;

import java.util.List;

public interface IMovieService {
    Movie createMovie(MovieCreateRequest movieCreateRequest) throws Exception;
    Movie updateMovie(MovieUpdateRequest movieUpdateRequest) throws Exception;
    public String deleteMovie(int id);
    List<MovieByTicketCount> getMoviesOrderByTicketCount();
}
