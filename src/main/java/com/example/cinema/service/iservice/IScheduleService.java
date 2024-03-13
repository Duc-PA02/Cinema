package com.example.cinema.service.iservice;

import com.example.cinema.dto.ScheduleCreateRequest;
import com.example.cinema.dto.ScheduleDTO;
import com.example.cinema.dto.ScheduleUpdateRequest;
import com.example.cinema.entity.Schedule;

import java.util.List;

public interface IScheduleService {
    List<ScheduleDTO> getScheduleDTOs() throws Exception;
    Schedule createSchedule(ScheduleCreateRequest scheduleCreateRequest) throws Exception;
    Schedule updateSchedule(ScheduleUpdateRequest scheduleUpdateRequest) throws Exception;
    public String deleteSchedule(int id);
}
