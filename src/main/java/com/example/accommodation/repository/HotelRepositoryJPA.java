package com.example.accommodation.repository;

import com.example.accommodation.entity.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
public interface HotelRepositoryJPA extends JpaRepository<HotelEntity, Integer> {
    Optional<HotelEntity> findByName(String name);
    List<HotelEntity> findByRating(int rating);
    @Query("SELECT h FROM HotelEntity h, LocationEntity l WHERE h.locationEntity = l.id AND l.city = ?1")
    List<HotelEntity> findByLocation(String location);
    List<HotelEntity> findByReputationBadge(String reputationBadge);
}

