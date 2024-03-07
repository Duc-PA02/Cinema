package com.example.cinema.service.iservice;

import com.example.cinema.dto.CinemaDTO;
import com.example.cinema.dto.UpdateCinemaDTO;
import com.example.cinema.entity.Cinema;

public interface ICinemaService {
//    public String CreateCinema(CinemaDTO cinema);
    Cinema createCinema(CinemaDTO createCinemaRequest) throws Exception;
//    public String UpdateCinema(Cinema cinema);
    Cinema updateCinema(UpdateCinemaDTO updateCinemaRequest) throws Exception;
    public String deleteCinema(int id);
}
