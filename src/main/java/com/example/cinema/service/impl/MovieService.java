package com.example.cinema.service.impl;

import com.example.cinema.dto.MovieByTicketCount;
import com.example.cinema.dto.MovieCreateRequest;
import com.example.cinema.dto.MovieUpdateRequest;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.MovieType;
import com.example.cinema.entity.Rate;
import com.example.cinema.entity.Schedule;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.MovieRepository;
import com.example.cinema.repository.MovieTypeRepository;
import com.example.cinema.repository.RateRepository;
import com.example.cinema.repository.ScheduleRepository;
import com.example.cinema.service.iservice.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService implements IMovieService {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieTypeRepository movieTypeRepository;
    @Autowired
    private RateRepository rateRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    public LocalDateTime formatTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDateTime = dateTime.format(formatter);
        return LocalDateTime.parse(formattedDateTime, formatter);
    }
    public boolean checkEndTimeAfterPremiereDate(LocalDateTime endTime, LocalDateTime premiereDate){
        return endTime.isAfter(premiereDate);
    }
    @Override
    public Movie createMovie(MovieCreateRequest movieCreateRequest) throws Exception {
        Rate rate = rateRepository.findById(movieCreateRequest.getRateId()).orElse(null);
        MovieType movieType = movieTypeRepository.findById(movieCreateRequest.getMovieTypeId()).orElse(null);
        if (!checkEndTimeAfterPremiereDate(movieCreateRequest.getEndTime(),movieCreateRequest.getPremiereDate())){
            throw new InvalidDataAccessApiUsageException("Thoi gian cong chieu phai truoc thoi gian ket thuc");
        }
        if (!rateRepository.existsById(movieCreateRequest.getRateId())){
            throw new DataNotFoundException("rate khong ton tai");
        }
        if (!movieTypeRepository.existsById(movieCreateRequest.getMovieTypeId())){
            throw new DataNotFoundException("movieType khong ton tai");
        }
        Movie movie = Movie.builder()
                .movieDuration(movieCreateRequest.getMovieDuration())
                .endTime(formatTime(movieCreateRequest.getEndTime()))
                .premiereDate(formatTime(movieCreateRequest.getPremiereDate()))
                .description(movieCreateRequest.getDescription())
                .director(movieCreateRequest.getDirector())
                .image(movieCreateRequest.getImage())
                .heroImage(movieCreateRequest.getHeroImage())
                .language(movieCreateRequest.getLanguage())
                .name(movieCreateRequest.getName())
                .trailer(movieCreateRequest.getTrailer())
                .movieTypeId(movieType)
                .rateId(rate)
                .isActive(true)
                .build();
        movieRepository.save(movie);
        return movie;
    }

    @Override
    public Movie updateMovie(MovieUpdateRequest movieUpdateRequest) throws Exception {
        Movie movie = movieRepository.findById(movieUpdateRequest.getId()).orElse(null);
        if (movie == null){
            throw new DataNotFoundException("movie khong ton tai");
        }
        if (!rateRepository.existsById(movieUpdateRequest.getRateId())){
            throw new DataNotFoundException("rate khong ton tai");
        }
        if (!movieTypeRepository.existsById(movieUpdateRequest.getMovieTypeId())){
            throw new DataNotFoundException("movieType khong ton tai");
        }
        Movie newMovie = Movie.builder()
                .movieDuration(movieUpdateRequest.getMovieDuration())
                .endTime(formatTime(movieUpdateRequest.getEndTime()))
                .premiereDate(formatTime(movieUpdateRequest.getPremiereDate()))
                .description(movieUpdateRequest.getDescription())
                .director(movieUpdateRequest.getDirector())
                .image(movieUpdateRequest.getImage())
                .heroImage(movieUpdateRequest.getHeroImage())
                .language(movieUpdateRequest.getLanguage())
                .name(movieUpdateRequest.getName())
                .trailer(movieUpdateRequest.getTrailer())
                .movieTypeId(movieTypeRepository.findById(movieUpdateRequest.getMovieTypeId()).orElse(null))
                .rateId(rateRepository.findById(movieUpdateRequest.getRateId()).orElse(null))
                .isActive(true)
                .build();
        movieRepository.save(newMovie);
        return newMovie;
    }

    @Override
    public String deleteMovie(int id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()){
            return "Khong tim thay movie";
        }
        for (Schedule schedule : scheduleRepository.findAll()){
            if (schedule.getMovieId().getId() == id){
                scheduleRepository.delete(schedule);
            }
        }
        movieRepository.delete(movie.get());
        return "xoa thanh cong movie";
    }

    @Override
    public List<MovieByTicketCount> getMoviesOrderByTicketCount() {
        List<MovieByTicketCount> movieByTicketCountList = movieRepository.findMoviesOrderByTicketCount();
        return movieByTicketCountList;
    }
}
