package com.example.accommodation.repository;

import com.example.accommodation.entity.LocationEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LocationRepository {
    private final EntityManager entityManager;

    public List<LocationEntity> getAllLocations() {
        return entityManager.createQuery("FROM LocationEntity", LocationEntity.class).getResultList();
    }

    public LocationEntity getLocationById(int id) {
        return entityManager.find(LocationEntity.class, id);
    }

    public void createLocation(LocationEntity location) {
        entityManager.persist(location);
    }

    @Transactional
    public LocationEntity updateLocation(LocationEntity location) {
        entityManager.merge(location);
        return location;
    }

    @Transactional
    public void deleteLocation(int locationId) {
        LocationEntity deletingLocation = entityManager.find(LocationEntity.class, locationId);
        entityManager.remove(deletingLocation);
    }

    public List<LocationEntity> getLocationsByAddress(String address) {
        return entityManager
                .createQuery("From LocationEntity WHERE address = \"%s\"".formatted(address),
                        LocationEntity.class)
                .getResultList();
    }
}
