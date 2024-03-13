package com.example.cinema.controller.adminController;

import com.example.cinema.dto.FoodCreateRequest;
import com.example.cinema.dto.FoodUpdateRequest;
import com.example.cinema.dto.MovieCreateRequest;
import com.example.cinema.dto.MovieUpdateRequest;
import com.example.cinema.service.impl.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    @PostMapping("admin/movie/create")
    public ResponseEntity<?> createMovie(@RequestBody MovieCreateRequest movieCreateRequest){
        try {
            return ResponseEntity.ok().body(movieService.createMovie(movieCreateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("admin/movie/update")
    public ResponseEntity<?> updateMovie(@RequestBody MovieUpdateRequest movieUpdateRequest){
        try {
            return ResponseEntity.ok().body(movieService.updateMovie(movieUpdateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/movie/delete")
    public ResponseEntity<?> deleteMovie(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(movieService.deleteMovie(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("movie/top-movie")
    public ResponseEntity<?> topMovie(){
        try {
            return ResponseEntity.ok().body(movieService.getMoviesOrderByTicketCount());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
