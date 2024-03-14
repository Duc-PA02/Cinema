package com.example.cinema.controller.adminController;

import com.example.cinema.dto.CinemaDTO;
import com.example.cinema.dto.UpdateCinemaDTO;
import com.example.cinema.entity.Cinema;
import com.example.cinema.service.impl.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping("admin/cinema/create")
    public ResponseEntity<?> createCinema(@RequestBody CinemaDTO cinema){
        try {
            return ResponseEntity.ok().body(cinemaService.createCinema(cinema));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping ("admin/cinema/update")
    public ResponseEntity<?> updateCinema(@RequestBody UpdateCinemaDTO cinema){
        try {
            return ResponseEntity.ok().body(cinemaService.updateCinema(cinema));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/cinema/delete")
    public ResponseEntity<?> deleteCinema(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(cinemaService.deleteCinema(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("admin/cinema-revenue")
    public ResponseEntity<?> getCinemaRevenueByTime(@RequestParam LocalDateTime startTime, @RequestParam LocalDateTime endTime){
        try {
            return ResponseEntity.ok().body(cinemaService.getCinemaRevenueByTimeRange(startTime,endTime));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
