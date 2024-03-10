package com.example.cinema.controller.adminController;

import com.example.cinema.dto.SeatCreateRequest;
import com.example.cinema.dto.SeatUpdateRequest;
import com.example.cinema.service.impl.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;
    @PostMapping("admin/seat/create")
    public ResponseEntity<?> createSeat(@RequestBody SeatCreateRequest seatCreateRequest){
        try {
            return ResponseEntity.ok().body(seatService.creatSeat(seatCreateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("admin/seat/update")
    public ResponseEntity<?> updateSeat(@RequestBody SeatUpdateRequest seatUpdateRequest){
        try {
            return ResponseEntity.ok().body(seatService.updateSeat(seatUpdateRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/seat/delete")
    public ResponseEntity<?> deleteSeat(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(seatService.deleteSeat(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
