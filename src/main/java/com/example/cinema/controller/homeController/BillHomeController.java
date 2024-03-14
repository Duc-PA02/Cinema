package com.example.cinema.controller.homeController;

import com.example.cinema.dto.BillDTO;
import com.example.cinema.service.impl.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BillHomeController {
    private final BillService billService;
    @PostMapping("bill/bill-create")
    public ResponseEntity<?> createBill(@RequestBody BillDTO billDTO){
        try {
            return ResponseEntity.ok().body(billService.createBill(billDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
