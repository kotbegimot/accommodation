package com.example.accommodation.repository;

import com.example.accommodation.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
public interface LocationRepositoryJPA extends JpaRepository<LocationEntity, Integer> {
    List<LocationEntity> findByAddress(String address);
}
