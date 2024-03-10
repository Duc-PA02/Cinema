package com.example.cinema.service.iservice;

import com.example.cinema.dto.SeatCreateRequest;
import com.example.cinema.dto.SeatUpdateRequest;
import com.example.cinema.entity.Seat;

public interface ISeatService {
    Seat creatSeat(SeatCreateRequest seatCreateRequest) throws Exception;
    Seat updateSeat(SeatUpdateRequest seatUpdateRequest) throws Exception;
    public String deleteSeat(int id);
}
