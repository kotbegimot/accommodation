package com.example.accommodation.repository;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelRepository {
    private final EntityManager entityManager;

    public List<HotelEntity> getAll() {
        return entityManager.createQuery("FROM HotelEntity", HotelEntity.class).getResultList();
    }

    public HotelEntity getById(int id) {
        return entityManager.find(HotelEntity.class, id);
    }

    @Transactional
    public void create(HotelEntity hotel) {
        entityManager.persist(hotel);
    }

    @Transactional
    public HotelEntity updateHotel(HotelEntity hotel) {
        entityManager.merge(hotel);
        return hotel;
    }

    @Transactional
    public void deleteHotel(int hotelId) {
        HotelEntity deletingHotel = entityManager.find(HotelEntity.class, hotelId);
        entityManager.remove(deletingHotel);
    }


}
