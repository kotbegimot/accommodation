package com.example.accommodation.util;

import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Location;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Class used to convert objects from Location Entity to Location Model and back.
 */
@Component
public class LocationMapper {
    public static Location toModel(LocationEntity entity) {
        if (entity == null) {
            return null;
        } else {
            return Location.builder()
                    .id(entity.getId())
                    .city(entity.getCity())
                    .state(entity.getState())
                    .country(entity.getCountry())
                    .zipCode(entity.getZipCode())
                    .address(entity.getAddress())
                    .build();
        }
    }
    public static LocationEntity toEntity(Location location) {
        if (location == null) {
            return null;
        } else {
            return LocationEntity.builder()
                    .id(location.getId())
                    .city(location.getCity())
                    .state(location.getState())
                    .country(location.getCountry())
                    .zipCode(location.getZipCode())
                    .address(location.getAddress())
                    .hotels(new ArrayList<>())
                    .build();
        }
    }
}
