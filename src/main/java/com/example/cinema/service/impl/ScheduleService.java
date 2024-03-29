package com.example.cinema.service.impl;

import com.example.cinema.dto.ScheduleCreateRequest;
import com.example.cinema.dto.ScheduleDTO;
import com.example.cinema.dto.ScheduleUpdateRequest;
import com.example.cinema.entity.*;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.*;
import com.example.cinema.service.iservice.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.SchedulingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.util.*;

@Service
public class ScheduleService implements IScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Override
    public List<ScheduleDTO> getScheduleDTOsByMovieAndCinemaAndRoom(int movieId, int cinemaId, int roomId) throws Exception {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null){
            throw new DataNotFoundException("Khong tim thay movie");
        }
        Cinema cinema = cinemaRepository.findById(cinemaId).orElse(null);
        if (cinema == null){
            throw new DataNotFoundException("Khong tim thay cinema");
        }
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null){
            throw new DataNotFoundException("Khong tim thay room");
        }
        List<Schedule> scheduleList = scheduleRepository.findByMovieIdAndRoomCinemaIdAndRoomId(movieId, cinemaId, roomId);

        if (scheduleList.isEmpty()) {
            throw new DataNotFoundException("Khong co lich trinh cho phim, reap chieu phim va phong da chon");
        }

        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        for (Schedule schedule : scheduleList) {
            ScheduleDTO scheduleDTO = ScheduleDTO.builder()
                    .startAt(schedule.getStartAt())
                    .endAt(schedule.getEndAt())
                    .price(schedule.getPrice())
                    .name(schedule.getName())
                    .build();
            scheduleDTOS.add(scheduleDTO);
        }
        return scheduleDTOS;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Schedule createSchedule(ScheduleCreateRequest scheduleCreateRequest) throws Exception {
        Room room = roomRepository.findById(scheduleCreateRequest.getRoomId()).orElse(null);
        Movie movie = movieRepository.findById(scheduleCreateRequest.getMovieId()).orElse(null);
        if (movie==null){
            throw new DataNotFoundException("Khong tim thay movie");
        }
        if (room==null){
            throw new DataNotFoundException("Khong tim thay room");
        }
        if (scheduleCreateRequest.getStartAt().isAfter(scheduleCreateRequest.getEndAt())){
            throw new DateTimeException("Thoi gian bat dau phai truoc thoi gian ket thuc");
        }
        boolean isRoomScheduleConflict = scheduleRepository
                .countRoomScheduleConflict(scheduleCreateRequest.getRoomId(),
                        scheduleCreateRequest.getStartAt(), scheduleCreateRequest.getEndAt()) > 0;

        if (isRoomScheduleConflict) {
            throw new SchedulingException("Trung lich chieu");
        }
        Schedule schedule = Schedule.builder()
                .code(scheduleCreateRequest.getCode())
                .movieId(movie)
                .roomId(room)
                .isActive(true)
                .startAt(scheduleCreateRequest.getStartAt())
                .endAt(scheduleCreateRequest.getEndAt())
                .name(scheduleCreateRequest.getName())
                .price(scheduleCreateRequest.getPrice())
                .build();
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Override
    public Schedule updateSchedule(ScheduleUpdateRequest scheduleUpdateRequest) throws Exception {
        Schedule schedule = scheduleRepository.findById(scheduleUpdateRequest.getId()).orElse(null);
        Room room = roomRepository.findById(scheduleUpdateRequest.getRoomId()).orElse(null);
        Movie movie = movieRepository.findById(scheduleUpdateRequest.getMovieId()).orElse(null);
        if (schedule == null){
            throw new DataNotFoundException("Khong tim thay schedule");
        }
        if (room == null){
            throw new DataNotFoundException("Khong tim thay room");
        }
        if (movie == null){
            throw new DataNotFoundException("Khong tim thay movie");
        }
        boolean isRoomScheduleConflict = scheduleRepository
                .countRoomScheduleConflict(scheduleUpdateRequest.getRoomId(),
                        scheduleUpdateRequest.getStartAt(), scheduleUpdateRequest.getEndAt()) > 0;

        if (isRoomScheduleConflict) {
            throw new SchedulingException("Trung lich chieu");
        }
        Schedule newSchedule = Schedule.builder()
                .code(scheduleUpdateRequest.getCode())
                .movieId(movie)
                .roomId(room)
                .isActive(true)
                .startAt(scheduleUpdateRequest.getStartAt())
                .endAt(scheduleUpdateRequest.getEndAt())
                .name(scheduleUpdateRequest.getName())
                .price(scheduleUpdateRequest.getPrice())
                .build();
        scheduleRepository.save(newSchedule);
        return newSchedule;
    }

    @Override
    public String deleteSchedule(int id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()){
            return "Khong tim thay schedule";
        }
        for (Ticket ticket : ticketRepository.findAll()){
            if (ticket.getScheduleId().getId() == id){
                ticketRepository.delete(ticket);
            }
        }
        scheduleRepository.delete(schedule.get());
        return "xoa thanh cong schedule";
    }
}
