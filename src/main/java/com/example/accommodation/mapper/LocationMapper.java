package com.example.accommodation.mapper;

import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Location;
import org.springframework.stereotype.Component;
/**
 * Class used to convert objects from Location Entity to Location Model and back.
 */
@Component
public class LocationMapper {
    public Location toModel(LocationEntity entity) {
        return Location.builder()
                .id(entity.getId())
                .city(entity.getCity())
                .state(entity.getState())
                .country(entity.getCountry())
                .zipCode(entity.getZipCode())
                .address(entity.getAddress())
                .build();
    }
    public LocationEntity toEntity(Location location) {
        return LocationEntity.builder()
                .id(location.getId())
                .city(location.getCity())
                .state(location.getState())
                .country(location.getCountry())
                .zipCode(location.getZipCode())
                .address(location.getAddress())
                .build();
    }
}
