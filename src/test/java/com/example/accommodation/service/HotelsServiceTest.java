package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.mapper.HotelMapper;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class HotelsServiceTest {
    @Mock
    private HotelRepository repository;
    @Mock
    private HotelMapper mapper;
    @Mock
    private HotelValidationService validationService;
    @Mock
    private HotelConversionService conversionService;
    @InjectMocks
    private HotelsService service;
    private HotelEntity entity;
    private Hotel hotel;
    @BeforeEach
    public void setup() {
        entity = HotelEntity.builder()
                .id(0)
                .name("name")
                .rating(0)
                .category("category")
                .locationEntity(new LocationEntity(0, "city", "state", "country", 0, "address", new ArrayList<>()))
                .imageUrl("url")
                .reputation(0)
                .reputationBadge("red")
                .price(0)
                .availability(0)
                .build();
        hotel = Hotel.builder()
                .id(0)
                .name("name")
                .rating(0)
                .category("category")
                .location(new Location(0, "city", "state", "country", 0, "address"))
                .imageUrl("url")
                .reputation(0)
                .reputationBadge("red")
                .price(0)
                .availability(0)
                .build();
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void getUnexistingHotel() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            int id = 1;
            when(repository.getById(id)).thenReturn(null);
            service.getHotel(id);
        });
    }

    @Test
    @DisplayName("Service should return Hotel object")
    void getExistingHotel() {
        int id = 1;
        entity.setId(id);
        hotel.setId(id);
        when(repository.getById(id)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(hotel);
        assertEquals(service.getHotel(id), hotel);
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookFullBookedHotel() {
        Assertions.assertThrows(AvailabilityIsZeroException.class, () -> {
            int id = 1;
            entity.setId(id);
            entity.setAvailability(0);
            when(repository.getById(id)).thenReturn(entity);
            service.bookHotel(id);
        });
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookHotelSuccess() {
        int id = 1;
        entity.setId(id);
        entity.setAvailability(1);
        when(repository.getById(id)).thenReturn(entity);
        service.bookHotel(id);
        assertEquals(entity.getAvailability(), 0);
    }
}