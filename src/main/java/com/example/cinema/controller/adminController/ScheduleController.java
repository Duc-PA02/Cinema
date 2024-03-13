package com.example.cinema.controller.adminController;

import com.example.cinema.dto.ScheduleCreateRequest;
import com.example.cinema.dto.ScheduleUpdateRequest;
import com.example.cinema.service.impl.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    @PostMapping("admin/schedule-create")
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleCreateRequest scheduleCreateRequest){
        try {
            return ResponseEntity.ok().body(scheduleService.createSchedule(scheduleCreateRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PutMapping("admin/schedule-update")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleUpdateRequest scheduleUpdateRequest){
        try {
            return ResponseEntity.ok().body(scheduleService.updateSchedule(scheduleUpdateRequest));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("admin/schedule-delete")
    public ResponseEntity<?> deleteSchedule(@RequestParam int id){
        try {
            return ResponseEntity.ok().body(scheduleService.deleteSchedule(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
