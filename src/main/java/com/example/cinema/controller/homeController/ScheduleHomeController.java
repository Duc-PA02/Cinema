package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScheduleHomeController {
    private final ScheduleService scheduleService;
    @GetMapping("schedule/get-scheduledto")
    public ResponseEntity<?> gétcheduleDTOs(){
        try {
            return ResponseEntity.ok().body(scheduleService.getScheduleDTOs());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
