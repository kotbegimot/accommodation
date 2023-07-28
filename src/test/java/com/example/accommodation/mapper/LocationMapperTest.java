package com.example.accommodation.mapper;

import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Location;
import com.example.accommodation.util.LocationMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LocationMapperTest {
    @Test
    @DisplayName("Mapper should return correct Location object")
    void mapEntityToModel() {

        LocationEntity locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        Location locationModel = LocationMapper.toModel(locationEntity);
        assertModelEqualsEntity(locationModel, locationEntity);
    }

    @Test
    @DisplayName("Mapper should return null")
    void mapEntityToModelNull() {
        assertNull(LocationMapper.toModel(null));
    }

    @Test
    @DisplayName("Mapper should return correct LocationEntity object")
    void mapModelToEntity() {
        Location locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        LocationEntity locationEntity = LocationMapper.toEntity(locationModel);
        assertModelEqualsEntity(locationModel, locationEntity);
    }

    @Test
    @DisplayName("Mapper should return null")
    void mapModelToEntityNull() {
        assertNull(LocationMapper.toEntity(null));
    }

    private void assertModelEqualsEntity(Location model, LocationEntity entity) {
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getCity(), model.getCity());
        assertEquals(entity.getState(), model.getState());
        assertEquals(entity.getCountry(), model.getCountry());
        assertEquals(entity.getZipCode(), model.getZipCode());
        assertEquals(entity.getAddress(), model.getAddress());
    }
}