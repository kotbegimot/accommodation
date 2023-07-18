package com.example.accommodation.repository;

import com.example.accommodation.entity.HotelEntity;
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

    public List<HotelEntity> getHotelsByRating(int reputation) {
        return entityManager
                .createQuery(String.format("From HotelEntity WHERE reputation=%s", reputation)
                        , HotelEntity.class)
                .getResultList();
    }

    public List<HotelEntity> getHotelsByLocation(String location){
        //TODO: test when added second table
        return entityManager
                .createQuery(String.format("SELECT h FROM HotelEntity h, Location l, WHERE l.location=%s", location),
                        HotelEntity.class)
                .getResultList();
    }

    public List<HotelEntity> getHotelsByBadge(String reputationBadge){
        return entityManager
                .createQuery(String.format("From HotelEntity WHERE reputationBadge = %s", reputationBadge)
                        , HotelEntity.class)
                .getResultList();
    }
}
