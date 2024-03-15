package com.example.cinema.controller.homeController;

import com.example.cinema.service.impl.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class VNPayController {
    private final VNPayService vnPayService;
    @GetMapping("vnpay-payment")
    public ResponseEntity<?> getPay() throws UnsupportedEncodingException {
        try {
            return ResponseEntity.ok().body(vnPayService.getPay());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @PostMapping("payment")
    public String payment(@RequestParam String email,@RequestParam String bank, @RequestParam String name, @RequestParam String stk, @RequestParam Long totalMoney) {
            return vnPayService.postPayment(email, bank, name, stk, totalMoney);
    }
}
