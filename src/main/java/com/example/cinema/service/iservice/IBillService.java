package com.example.cinema.service.iservice;

import com.example.cinema.dto.BillDTO;
import com.example.cinema.entity.Bill;

public interface IBillService {
    Bill createBill(BillDTO billDTO) throws Exception;

}
