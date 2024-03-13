package com.example.cinema.service.impl;

import com.example.cinema.dto.BillDTO;
import com.example.cinema.entity.Bill;
import com.example.cinema.repository.BillRepository;
import com.example.cinema.service.iservice.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;

    @Override
    public Bill createBill(BillDTO billDTO) throws Exception {
        return null;
    }
}
