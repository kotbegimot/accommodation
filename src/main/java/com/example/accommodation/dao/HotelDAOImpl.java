package com.example.accommodation.dao;
import com.example.accommodation.entity.HotelEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelDAOImpl implements HotelDAO {
    private final EntityManager entityManager;

    @Override
    public List<HotelEntity> getAll() {
        return entityManager.createQuery("FROM HotelEntity", HotelEntity.class).getResultList();
    }
    @Override
    public HotelEntity getById(int id) {
        return entityManager.find(HotelEntity.class, id);
    }
    @Override
    @Transactional
    public void create(HotelEntity hotel) {
        entityManager.persist(hotel);
    }

    @Transactional
    public HotelEntity updateHotel (HotelEntity hotel) {
        HotelEntity updatingHotel = entityManager.find(HotelEntity.class, hotel.getId());
        if (updatingHotel != null) {
            entityManager.merge(hotel);
        }
        return hotel;
    }
    @Transactional
    public HotelEntity bookHotel (int hotelId) {
        HotelEntity updatingHotel = entityManager.find(HotelEntity.class, hotelId);
        if  (updatingHotel != null) {
            int availability = updatingHotel.getAvailability();
            if (availability > 0) availability--;
            updatingHotel.setAvailability(availability);
            entityManager.merge(updatingHotel);
        }
        return updatingHotel;
    }

    @Transactional
    public void deleteHotel (int hotelId) {
        HotelEntity deletingHotel = entityManager.find(HotelEntity.class, hotelId);
        entityManager.remove(deletingHotel);
    }
}
