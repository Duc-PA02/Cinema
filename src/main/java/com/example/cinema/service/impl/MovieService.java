package com.example.cinema.service.impl;

import com.example.cinema.dto.*;
import com.example.cinema.entity.*;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.*;
import com.example.cinema.service.iservice.IMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CinemaRepository cinemaRepository;
    public LocalDateTime formatTime(String dateTime) {
        DateTimeFormatter endTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate localDate =LocalDate.parse(dateTime,endTimeFormatter);
        LocalDateTime localDateTimeEndTime = localDate.atStartOfDay();
        return localDateTimeEndTime;
    }
    public boolean checkEndTimeAfterPremiereDate(String endTime, String premiereDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime endTimeDateTime = LocalDateTime.parse(endTime + " 00:00:00", formatter);
        LocalDateTime premiereDateTime = LocalDateTime.parse(premiereDate + " 00:00:00", formatter);
        if (endTimeDateTime.isBefore(premiereDateTime)) {
            return false;
        }
        return true;
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
        if (movieRepository.existsByName(movieCreateRequest.getName())){
            throw new DataIntegrityViolationException("ten phim da ton tai");
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
        if (movieRepository.existsByName(movieUpdateRequest.getName())){
            throw new DataIntegrityViolationException("ten phim da ton tai");
        }
        if (!checkEndTimeAfterPremiereDate(movieUpdateRequest.getEndTime(),movieUpdateRequest.getPremiereDate())){
            throw new InvalidDataAccessApiUsageException("Thoi gian cong chieu phai truoc thoi gian ket thuc");
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

    public List<MovieByCinema> getMoviesByCinema(String nameCinema) throws Exception{
        Cinema cinema = cinemaRepository.findByNameOfCinema(nameCinema);
        if (cinema == null) {
            throw new DataNotFoundException("Khong tim thay rap");
        }
        List<Room> rooms = new ArrayList<>(cinema.getCinemarooms());
        List<MovieByCinema> result = new ArrayList<>();
        for (Room room : rooms) {
            List<Schedule> schedules = scheduleRepository.findByRoomId(room.getId());
            for (Schedule schedule : schedules) {
                Movie movie = schedule.getMovieId();
                result.add(MovieByCinema.builder()
                        .movieDuration(movie.getMovieDuration())
                        .endTime(movie.getEndTime().toString())
                        .premiereDate(movie.getPremiereDate().toString())
                        .description(movie.getDescription())
                        .director(movie.getDirector())
                        .image(movie.getImage())
                        .heroImage(movie.getHeroImage())
                        .language(movie.getLanguage())
                        .name(movie.getName())
                        .trailer(movie.getTrailer())
                        .build());
            }
        }
        return result;
    }

    @Override
    public List<MovieDTO> getMovies() throws Exception {
        List<Movie> movies = movieRepository.findAll();
        if (movies == null){
            throw new DataNotFoundException("Khong tim thay movie");
        }
        List<MovieDTO> movieDTOs = new ArrayList<>();

        for (Movie movie : movies) {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setMovieDuration(movie.getMovieDuration());
            movieDTO.setPremiereDate(movie.getPremiereDate());
            movieDTO.setDescription(movie.getDescription());
            movieDTO.setDirector(movie.getDirector());
            movieDTO.setImage(movie.getImage());
            movieDTO.setHeroImage(movie.getHeroImage());
            movieDTO.setLanguage(movie.getLanguage());
            movieDTO.setName(movie.getName());
            movieDTO.setTrailer(movie.getTrailer());
            movieDTOs.add(movieDTO);
        }

        return movieDTOs;
    }
}
