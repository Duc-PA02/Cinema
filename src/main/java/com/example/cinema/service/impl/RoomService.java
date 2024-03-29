package com.example.cinema.service.impl;

import com.example.cinema.dto.CreateRoomRequest;
import com.example.cinema.dto.RoomDTO;
import com.example.cinema.dto.UpdateRoomRequest;
import com.example.cinema.entity.Cinema;
import com.example.cinema.entity.Movie;
import com.example.cinema.entity.Room;
import com.example.cinema.entity.Schedule;
import com.example.cinema.exceptions.DataNotFoundException;
import com.example.cinema.repository.CinemaRepository;
import com.example.cinema.repository.RoomRepository;
import com.example.cinema.service.iservice.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;
    private final CinemaRepository cinemaRepository;
    @Override
    public Room createRoom(CreateRoomRequest createRoomRequest) throws Exception {
        Room room = roomRepository.findByCode(createRoomRequest.getCode());
        if (room != null){
            throw new DataIntegrityViolationException("Room da ton tai");
        }
        if (roomRepository.existsByName(createRoomRequest.getName())){
            throw new DataIntegrityViolationException("Ten phong da ton tai");
        }
        Cinema cinema = cinemaRepository.findById(createRoomRequest.getCinemaId()).orElse(null);
        if (cinema == null){
            throw new DataNotFoundException("Cinema khong ton tai");
        }
        Room newRoom = Room.builder()
                .capacity(createRoomRequest.getCapacity())
                .description(createRoomRequest.getDescription())
                .type(createRoomRequest.getType())
                .name(createRoomRequest.getName())
                .isActive(true)
                .code(createRoomRequest.getCode())
                .cinema(cinema)
                .build();
        roomRepository.save(newRoom);
        return newRoom;
    }

    @Override
    public Room updateRoom(UpdateRoomRequest updateRoomRequest) throws Exception {
        Room room = roomRepository.findById(updateRoomRequest.getId()).orElse(null);
        Cinema cinema = cinemaRepository.findById(updateRoomRequest.getCinemaId()).orElse(null);
        if (room == null){
            throw new DataNotFoundException("Room khong ton tai");
        }
        if (cinema == null){
            throw new DataNotFoundException("cinema khong ton tai");
        }
        if(!roomRepository.findAllByCodeAndIdNot(updateRoomRequest.getCode(),room.getId()).isEmpty()){
            throw new DataIntegrityViolationException("Code da ton tai");
        }
        room.setCinema(cinema);
        room.setCapacity(updateRoomRequest.getCapacity());
        room.setName(updateRoomRequest.getName());
        room.setDescription(updateRoomRequest.getDescription());
        room.setCode(updateRoomRequest.getCode());
        room.setType(updateRoomRequest.getType());
        roomRepository.save(room);
        return room;
    }

    @Override
    public String deleteRoom(int id) {
        Optional<Room> room = roomRepository.findById(id);
        if (room.isEmpty()){
            return "Khong tim thay room";
        }
        roomRepository.delete(room.get());
        return "Xoa thanh cong room";
    }

    @Override
    public List<RoomDTO> getRooms() throws Exception {
        List<Room> rooms = roomRepository.findAll();
        if (rooms.isEmpty()){
            throw new DataNotFoundException("Khong tim thay room");
        }
        List<RoomDTO> roomDTOs = new ArrayList<>();
        for (Room room : rooms){
            Set<Movie> movies = new HashSet<>();
            for (Schedule schedule : room.getRoomschedulelist()){
                movies.add(schedule.getMovieId());
            }
            if (!movies.isEmpty()){
                RoomDTO roomDTO = RoomDTO.builder()
                        .capacity(room.getCapacity())
                        .description(room.getDescription())
                        .name(room.getName())
                        .type(room.getType())
                        .build();
                roomDTOs.add(roomDTO);
            }
        }
        return roomDTOs;
    }
}
