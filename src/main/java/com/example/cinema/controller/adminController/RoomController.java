package com.example.cinema.controller.adminController;

import com.example.cinema.dto.CreateRoomRequest;
import com.example.cinema.dto.UpdateRoomRequest;
import com.example.cinema.service.impl.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    @PostMapping("admin/room/create")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomRequest createRoomRequest){
        try {
            return ResponseEntity.ok().body(roomService.createRoom(createRoomRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("admin/room/update")
    public ResponseEntity<?> updateRoom(@RequestBody UpdateRoomRequest updateRoomRequest){
        try {
            return ResponseEntity.ok().body(roomService.updateRoom(updateRoomRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/room/delete")
    public ResponseEntity<?> deleteRoom(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(roomService.deleteRoom(id));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
