package com.example.cinema.service.impl;

import com.example.cinema.dto.BillDTO;
import com.example.cinema.entity.Bill;
import com.example.cinema.entity.BillStatus;
import com.example.cinema.entity.Promotion;
import com.example.cinema.entity.User;
import com.example.cinema.exceptions.DataIntegrityViolationException;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.BillRepository;
import com.example.cinema.repository.BillStatusRepository;
import com.example.cinema.repository.PromotionRepository;
import com.example.cinema.repository.UserRepository;
import com.example.cinema.service.iservice.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService implements IBillService {
    @Autowired
    private BillRepository billRepository;
    @Autowired
    private BillStatusRepository billStatusRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Bill createBill(BillDTO billDTO) throws Exception {
        Bill bill = billRepository.findByTradingCode(billDTO.getTradingCode());
        if (bill != null){
            throw new DataIntegrityViolationException("Bill da ton tai");
        }
        if (billDTO == null) {
            throw new IllegalArgumentException("Thông tin hóa đơn không được để trống");
        }
        BillStatus billStatus = billStatusRepository.findById(billDTO.getBillStatusId()).orElse(null);
        Promotion promotion = promotionRepository.findById(billDTO.getPromotionId()).orElse(null);
        User user = userRepository.findById(billDTO.getCustomerId()).orElse(null);
        if (billStatus == null){
            throw new DataNotFoundException("Khong tim thay billStatus");
        }
        if (promotion == null){
            throw new DataNotFoundException("Khong tim thay promotion");
        }
        if (user == null){
            throw new DataNotFoundException("Khong tim thay user");
        }
        Bill createBill = Bill.builder()
                .totalMoney(billDTO.getTotalMoney())
                .name(billDTO.getName())
                .tradingCode(billDTO.getTradingCode())
                .creatTime(billDTO.getCreatTime())
                .updateTime(billDTO.getUpdateTime())
                .billStatusId(billStatus)
                .customerId(user)
                .promotionId(promotion)
                .isActive(true)
                .build();
        billRepository.save(createBill);
        return createBill;
    }
}
