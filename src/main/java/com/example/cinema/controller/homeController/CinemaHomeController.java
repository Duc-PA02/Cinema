package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CinemaHomeController {
    private final CinemaService cinemaService;
    @GetMapping("cinema/get-by-schedule-movie")
    public ResponseEntity<?> getCinemaByScheduleMovie(){
        try {
            return ResponseEntity.ok().body(cinemaService.getCinemaByMovie());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
