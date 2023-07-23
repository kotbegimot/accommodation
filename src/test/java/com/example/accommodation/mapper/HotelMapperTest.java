package com.example.accommodation.mapper;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @Test
    @DisplayName("Service should return correct Location object")
    void mapEntityToModel() {
        Location locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        LocationEntity locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        HotelEntity hotelEntity = HotelEntity.builder()
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

        when(locationMapper.toModel(locationEntity)).thenReturn(locationModel);
        Hotel hotelModel = mapper.toModel(hotelEntity);
        assertEquals(hotelModel.getId(), hotelEntity.getId());
        assertEquals(hotelModel.getName(), hotelEntity.getName());
        assertEquals(hotelModel.getRating(), hotelEntity.getRating());
        assertEquals(hotelModel.getCategory(), hotelEntity.getCategory());
        assertEquals(hotelModel.getImageUrl(), hotelEntity.getImageUrl());
        assertEquals(hotelModel.getReputation(), hotelEntity.getReputation());
        assertEquals(hotelModel.getReputationBadge(), hotelEntity.getReputationBadge());
        assertEquals(hotelModel.getPrice(), hotelEntity.getPrice());
        assertEquals(hotelModel.getAvailability(), hotelEntity.getAvailability());
    }

    @Test
    @DisplayName("Service should return correct LocationEntity object")
    void mapModelToEntity() {
        Location locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        LocationEntity locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        Hotel hotelModel = Hotel.builder()
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

        when(locationMapper.toEntity(locationModel)).thenReturn(locationEntity);
        HotelEntity hotelEntity = mapper.toEntity(hotelModel);
        assertEquals(hotelModel.getId(), hotelEntity.getId());
        assertEquals(hotelModel.getName(), hotelEntity.getName());
        assertEquals(hotelModel.getRating(), hotelEntity.getRating());
        assertEquals(hotelModel.getCategory(), hotelEntity.getCategory());
        assertEquals(hotelModel.getImageUrl(), hotelEntity.getImageUrl());
        assertEquals(hotelModel.getReputation(), hotelEntity.getReputation());
        assertEquals(hotelModel.getReputationBadge(), hotelEntity.getReputationBadge());
        assertEquals(hotelModel.getPrice(), hotelEntity.getPrice());
        assertEquals(hotelModel.getAvailability(), hotelEntity.getAvailability());
    }

    @Test
    @DisplayName("Service should return correct LocationEntity object")
    void mapEntityListToModelList() {
        Location locationModel = new Location(2, "city", "state",
                "country", 480011, "address");
        LocationEntity locationEntity = new LocationEntity(2, "city", "state",
                "country", 480011, "address", new ArrayList<>());
        HotelEntity hotelEntity = HotelEntity.builder()
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
        List<HotelEntity> entityList = List.of(hotelEntity);

        when(locationMapper.toModel(locationEntity)).thenReturn(locationModel);
        List<Hotel> modelList = mapper.toModels(entityList);
        assertEquals(modelList.get(0).getId(), entityList.get(0).getId());
        assertEquals(modelList.get(0).getName(), entityList.get(0).getName());
        assertEquals(modelList.get(0).getRating(), entityList.get(0).getRating());
        assertEquals(modelList.get(0).getCategory(), entityList.get(0).getCategory());
        assertEquals(modelList.get(0).getImageUrl(), entityList.get(0).getImageUrl());
        assertEquals(modelList.get(0).getReputation(), entityList.get(0).getReputation());
        assertEquals(modelList.get(0).getReputationBadge(), entityList.get(0).getReputationBadge());
        assertEquals(modelList.get(0).getPrice(), entityList.get(0).getPrice());
        assertEquals(modelList.get(0).getAvailability(), entityList.get(0).getAvailability());
    }
}