package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MovieHomeController {
    private final MovieService movieService;
    @GetMapping("movie/cinema-movie")
    public ResponseEntity<?> getMovieByCinema(@RequestParam String nameCinema){
        try {
            return ResponseEntity.ok().body(movieService.getMoviesByCinema(nameCinema));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("movie/all")
    public ResponseEntity<?> getMovies(){
        try {
            return ResponseEntity.ok().body(movieService.getMovies());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
