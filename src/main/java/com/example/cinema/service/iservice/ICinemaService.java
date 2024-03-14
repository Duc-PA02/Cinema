package com.example.cinema.service.iservice;

import com.example.cinema.dto.CinemaDTO;
import com.example.cinema.dto.CinemaRevenueDTO;
import com.example.cinema.dto.UpdateCinemaDTO;
import com.example.cinema.entity.Cinema;

import java.time.LocalDateTime;
import java.util.List;

public interface ICinemaService {
//    public String CreateCinema(CinemaDTO cinema);
    Cinema createCinema(CinemaDTO createCinemaRequest) throws Exception;
//    public String UpdateCinema(Cinema cinema);
    Cinema updateCinema(UpdateCinemaDTO updateCinemaRequest) throws Exception;
    public String deleteCinema(int id);
    public List<CinemaDTO> getCinemaByMovie() throws Exception;
    List<CinemaRevenueDTO> getCinemaRevenueByTimeRange(LocalDateTime startTime, LocalDateTime endTime);
}
