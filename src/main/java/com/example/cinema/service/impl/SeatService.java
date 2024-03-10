package com.example.cinema.service.impl;

import com.example.cinema.dto.SeatCreateRequest;
import com.example.cinema.dto.SeatUpdateRequest;
import com.example.cinema.entity.*;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.*;
import com.example.cinema.service.iservice.ISeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService implements ISeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SeatTypeRepository seatTypeRepository;
    @Autowired
    private SeatStatusRepository seatStatusRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Override
    public Seat creatSeat(SeatCreateRequest seatCreateRequest) throws Exception {
        Room room = roomRepository.findById(seatCreateRequest.getRoomId()).orElse(null);
        SeatType seatType = seatTypeRepository.findById(seatCreateRequest.getSeatTypeId()).orElse(null);
        SeatStatus seatStatus = seatStatusRepository.findById(seatCreateRequest.getSeatStatusId()).orElse(null);
        if (room == null){
            throw new DataNotFoundException("room khong ton tai");
        }
        if (seatType == null){
            throw new DataNotFoundException("seatType khong ton tai");
        }
        if (seatStatus == null){
            throw new DataNotFoundException("seatStatus khong ton tai");
        }
        List<Seat> listSeatByNumberAndLine = seatRepository.findAllByNumberAndLine(seatCreateRequest.getNumber(), seatCreateRequest.getLine());
        if (!listSeatByNumberAndLine.isEmpty()){
            throw new DataIntegrityViolationException("seat da ton tai");
        }
        Seat newSeat = Seat.builder()
                .line(seatCreateRequest.getLine())
                .number(seatCreateRequest.getNumber())
                .seatTypeId(seatType)
                .seatStatusId(seatStatus)
                .roomId(room)
                .isActive(true)
                .build();
        seatRepository.save(newSeat);
        return newSeat;
    }

    @Override
    public Seat updateSeat(SeatUpdateRequest seatUpdateRequest) throws Exception {
        Seat seat = seatRepository.findById(seatUpdateRequest.getId()).orElse(null);
        SeatStatus seatStatus = seatStatusRepository.findById(seatUpdateRequest.getSeatStatusId()).orElse(null);
        SeatType seatType = seatTypeRepository.findById(seatUpdateRequest.getSeatTypeId()).orElse(null);
        Room room = roomRepository.findById(seatUpdateRequest.getRoomId()).orElse(null);
        if (seat == null){
            throw new DataNotFoundException("seat khong ton tai");
        }
        if (seatStatus == null){
            throw new DataNotFoundException("seatStatus khong ton tai");
        }
        if (seatType == null){
            throw new DataNotFoundException("seatType khong ton tai");
        }
        if (room == null){
            throw new DataNotFoundException("room khong ton tai");
        }
        if (seatRepository.existsByLineAndNumberAndIdNot(seatUpdateRequest.getLine(),seatUpdateRequest.getNumber(),seatUpdateRequest.getId())){
            throw new DataIntegrityViolationException("seat da ton tai");
        }
        seat.setSeatStatusId(seatStatus);
        seat.setSeatTypeId(seatType);
        seat.setRoomId(room);
        seat.setLine(seatUpdateRequest.getLine());
        seat.setNumber(seatUpdateRequest.getNumber());
        seatRepository.save(seat);
        return seat;
    }

    @Override
    public String deleteSeat(int id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if (seat.isEmpty()){
            return "Khong tim thay seat";
        }
        for (Ticket ticket : ticketRepository.findAll()){
            if (ticket.getSeatId().getId()==id){
                ticketRepository.delete(ticket);
            }
        }
        seatRepository.delete(seat.get());
        return "Xoa thanh cong seat";
    }
}
