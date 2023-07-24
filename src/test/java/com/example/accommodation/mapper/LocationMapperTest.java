package com.example.accommodation.mapper;

import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LocationMapperTest {
    @Autowired
    private LocationMapper mapper;

    @Test
    @DisplayName("Service should return correct Location object")
    void mapEntityToModel() {

        LocationEntity locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        Location locationModel = mapper.toModel(locationEntity);
        assertModelEqualsEntity(locationModel, locationEntity);
    }

    @Test
    @DisplayName("Service should return correct LocationEntity object")
    void mapModelToEntity() {
        Location locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        LocationEntity locationEntity = mapper.toEntity(locationModel);
        assertModelEqualsEntity(locationModel, locationEntity);
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