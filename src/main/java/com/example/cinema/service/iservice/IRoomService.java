package com.example.cinema.service.iservice;

import com.example.cinema.dto.CreateRoomRequest;
import com.example.cinema.dto.UpdateRoomRequest;
import com.example.cinema.entity.Room;

public interface IRoomService {
    Room createRoom(CreateRoomRequest createRoomRequest) throws Exception;
    Room updateRoom(UpdateRoomRequest updateRoomRequest) throws Exception;
    public String deleteRoom(int id);
}
