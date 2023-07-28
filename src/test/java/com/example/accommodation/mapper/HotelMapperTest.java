package com.example.accommodation.mapper;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.util.HotelMapper;
import com.example.accommodation.util.LocationMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelMapperTest {
    @Mock
    private LocationMapper locationMapper;
    @InjectMocks
    private HotelMapper mapper;

    private Location locationModel;
    private LocationEntity locationEntity;
    private HotelEntity hotelEntity;
    private Hotel hotelModel;

    @BeforeEach
    public void setup() {
        locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        hotelEntity = HotelEntity.builder()
                .id(1)
                .name("name")
                .rating(100)
                .category("category")
                .locationEntity(locationEntity)
                .imageUrl("url")
                .reputation(500)
                .reputationBadge("yellow")
                .price(300)
                .availability(45)
                .build();
        hotelModel = Hotel.builder()
                .id(1)
                .name("name")
                .rating(100)
                .category("category")
                .location(locationModel)
                .imageUrl("url")
                .reputation(500)
                .reputationBadge("yellow")
                .price(300)
                .availability(45)
                .build();
    }
    @Test
    @DisplayName("Service should return correct Location object")
    void mapEntityToModel() {
        when(locationMapper.toModel(locationEntity)).thenReturn(locationModel);
        Hotel mappedModel = mapper.toModel(hotelEntity);
        assertModelEqualsEntity(mappedModel, hotelEntity);
    }

    @Test
    @DisplayName("Service should return correct LocationEntity object")
    void mapModelToEntity() {
        when(locationMapper.toEntity(locationModel)).thenReturn(locationEntity);
        HotelEntity mappedEntity = mapper.toEntity(hotelModel);
        assertModelEqualsEntity(hotelModel, mappedEntity);
    }

    @Test
    @DisplayName("Service should return correct LocationEntity object")
    void mapEntityListToModelList() {
        List<HotelEntity> entityList = List.of(hotelEntity);
        when(locationMapper.toModel(locationEntity)).thenReturn(locationModel);
        List<Hotel> modelList = mapper.toModels(entityList);
        assertModelEqualsEntity(modelList.get(0), entityList.get(0));
    }

    private void assertModelEqualsEntity(Hotel model, HotelEntity entity) {
        assertEquals(entity.getId(), model.getId());
        assertEquals(entity.getName(), model.getName());
        assertEquals(entity.getRating(), model.getRating());
        assertEquals(entity.getCategory(), model.getCategory());
        assertEquals(entity.getImageUrl(), model.getImageUrl());
        assertEquals(entity.getReputation(), model.getReputation());
        assertEquals(entity.getReputationBadge(), model.getReputationBadge());
        assertEquals(entity.getPrice(), model.getPrice());
        assertEquals(entity.getAvailability(), model.getAvailability());
    }
}