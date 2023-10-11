package com.example.accommodation.repository;

import com.example.accommodation.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
public interface HotelRepositoryJPA extends JpaRepository<HotelEntity, Integer> {

}

