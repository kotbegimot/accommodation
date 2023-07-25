package com.example.accommodation.repository;

import com.example.accommodation.entity.HotelEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@CacheConfig(cacheNames={"hotels"})
public class HotelRepository {
    private final EntityManager entityManager;


    public List<HotelEntity> getAllHotels() {
        return entityManager.createQuery("FROM HotelEntity", HotelEntity.class).getResultList();
    }

    @Cacheable()
    public HotelEntity getHotelById(int id) {
        return entityManager.find(HotelEntity.class, id);
    }

    @Transactional
    public void createHotel(HotelEntity hotel) {
        entityManager.persist(hotel);
    }

    @Transactional
    @CachePut()
    public HotelEntity updateHotel(HotelEntity hotel) {
        entityManager.merge(hotel);
        return hotel;
    }

    @Transactional
    @CacheEvict(allEntries=true)
    public void deleteHotel(int hotelId) {
        HotelEntity deletingHotel = entityManager.find(HotelEntity.class, hotelId);
        entityManager.remove(deletingHotel);
    }

    public List<HotelEntity> getHotelsByRating(int reputation) {
        return entityManager
                .createQuery(String.format("From HotelEntity WHERE reputation=%s", reputation),
                        HotelEntity.class)
                .getResultList();
    }

    public List<HotelEntity> getHotelsByLocation(String location) {

        return entityManager
                .createQuery(String.format("SELECT h FROM HotelEntity h, LocationEntity l " +
                                        "WHERE h.locationEntity = l.id AND l.city = %s",
                                location),
                        HotelEntity.class)
                .getResultList();
    }

    public List<HotelEntity> getHotelsByBadge(String reputationBadge) {
        return entityManager
                .createQuery(String.format("From HotelEntity WHERE reputationBadge = %s", reputationBadge),
                        HotelEntity.class)
                .getResultList();
    }
}
