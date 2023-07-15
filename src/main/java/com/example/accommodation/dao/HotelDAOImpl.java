package com.example.accommodation.dao;
import com.example.accommodation.entity.Hotel;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelDAOImpl implements HotelDAO {
    private final EntityManager entityManager;

    @Override
    public List<Hotel> getAll() {
        return entityManager.createQuery("FROM Hotel", Hotel.class).getResultList();
    }
    @Override
    public Hotel getById(int id) {
        return entityManager.find(Hotel.class, id);
    }
    @Override
    public void save(Hotel hotel) {
        entityManager.persist(hotel);
    }
}
