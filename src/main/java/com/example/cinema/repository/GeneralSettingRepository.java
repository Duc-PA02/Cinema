package com.example.cinema.repository;

import com.example.cinema.entity.GeneralSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralSettingRepository extends JpaRepository<GeneralSetting, Integer> {
}
