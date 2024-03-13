package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RoomHomeController {
    private final RoomService roomService;
    @GetMapping("room/get-by-schedule-movie")
    public ResponseEntity<?> getRooms(){
        try {
            return ResponseEntity.ok().body(roomService.getRooms());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
