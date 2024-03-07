package com.example.cinema.service.impl;

import com.example.cinema.dto.CinemaDTO;
import com.example.cinema.dto.UpdateCinemaDTO;
import com.example.cinema.entity.Cinema;
import com.example.cinema.entity.Room;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.CinemaRepository;
import com.example.cinema.repository.RoomRepository;
import com.example.cinema.service.iservice.ICinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CinemaService implements ICinemaService {
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private RoomRepository roomRepository;

//    @Override
//    public String CreateCinema(CinemaDTO cinema) {
//        String res = "";
//        for (Cinema cnm : cinemaRepository.findAll()){
//            if (cnm.getNameOfCinema().toLowerCase().compareTo(cinema.getNameOfCinema()) == 0){
//                res+="Ten rap da ton tai";
//                return res;
//            } else if (cnm.getAdress().toLowerCase().compareTo(cinema.getAddress()) == 0){
//                res+="Dia chi da ton tai";
//            }
//        }
//        cinemaRepository.save(cinema);
//        res+="Da them thanh cong cinema: "+cinema.getNameOfCinema();
//        return res;
//    }

    @Override
    public Cinema createCinema(CinemaDTO createCinemaRequest) throws Exception {
        Cinema existingCinema = cinemaRepository.findByCode(createCinemaRequest.getCode());
        if(existingCinema != null){
            throw new DataIntegrityViolationException("Cinema da ton tai");
        }
        if(cinemaRepository.existsByAddress(createCinemaRequest.getAddress())){
            throw new DataIntegrityViolationException("Dia chi da ton tai");
        }
        if(cinemaRepository.existsByNameOfCinema(createCinemaRequest.getNameOfCinema())){
            throw new DataIntegrityViolationException("Ten rap da ton tai");
        }

        Cinema newCinema = Cinema.builder()
                .nameOfCinema(createCinemaRequest.getNameOfCinema())
                .address(createCinemaRequest.getAddress())
                .isActive(true)
                .code(createCinemaRequest.getCode())
                .description(createCinemaRequest.getDescription())
                .build();
        cinemaRepository.save(newCinema);
        return newCinema;
    }
    @Override
    public Cinema updateCinema(UpdateCinemaDTO updateCinemaRequest) throws Exception {
        String address = updateCinemaRequest.getAddress();
        String nameOfCinema = updateCinemaRequest.getNameOfCinema();
        String code = updateCinemaRequest.getCode();
        Cinema cinema = cinemaRepository.findById(updateCinemaRequest.getId()).orElse(null);
        if(cinema == null){
            throw new DataNotFoundException("Cinema khong ton tai");
        }
        if(!cinemaRepository.findAllByAddressAndIdNot(address, updateCinemaRequest.getId()).isEmpty()){
            throw new DataIntegrityViolationException("Dia chi da ton tai");
        }
        if(!cinemaRepository.findAllByNameOfCinemaAndIdNot(nameOfCinema, updateCinemaRequest.getId()).isEmpty()){
            throw new DataIntegrityViolationException("Ten rap da ton tai");
        }
        if(!cinemaRepository.findAllByCodeAndIdNot(code, updateCinemaRequest.getId()).isEmpty()){
            throw new DataIntegrityViolationException("Code da ton tai");
        }
        cinema.setNameOfCinema(updateCinemaRequest.getNameOfCinema());
        cinema.setCode(updateCinemaRequest.getCode());
        cinema.setAddress(updateCinemaRequest.getAddress());
        cinema.setDescription(updateCinemaRequest.getDescription());

        cinemaRepository.save(cinema);

        return cinema;
    }


//    @Override
//    public String UpdateCinema(Cinema cinema) {
//        String res = "";
//        Cinema cnm = cinemaRepository.findById(cinema.getId()).get();
//        if (cnm == null){
//            res += "Cinema khong ton tai";
//            return res;
//        }
//        cinemaRepository.save(cinema);
//        res += "Update thanh cong cinema: " + cinema.getNameOfCinema();
//        return res;
//    }

    @Override
    public String deleteCinema(int id) {
        Optional<Cinema> cinema = cinemaRepository.findById(id);
        if (cinema.isEmpty()){
            return "Khong tim thay cinema";
        }
        for (Room room : roomRepository.findAll()){
            if (room.getCinema().getId()==id){
                roomRepository.delete(room);
            }
        }
        cinemaRepository.delete(cinema.get());
        return "Xoa thanh cong cinema";
    }
}
