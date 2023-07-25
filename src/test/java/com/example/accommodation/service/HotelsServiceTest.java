package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.mapper.HotelMapper;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.InvalidRequestException;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    private List<HotelEntity> entityList;
    private List<Hotel> modelList;
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
        entityList = List.of(entity);
        modelList = List.of(hotel);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getAllHotels() {
        when(repository.getAllHotels()).thenReturn(entityList);
        when(mapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getAllHotels(), modelList);
        verify(repository, times(1)).getAllHotels();
        verify(mapper, times(1)).toModels(entityList);
    }
    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void getUnexistingHotel() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            int id = 1;
            when(repository.getHotelById(id)).thenReturn(null);
            service.getHotel(id);
            verify(repository, times(1)).getHotelById(id);
        });
    }

    @Test
    @DisplayName("Service should return Hotel object")
    void getExistingHotel() {
        int id = 1;
        entity.setId(id);
        hotel.setId(id);
        when(repository.getHotelById(id)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(hotel);
        assertEquals(service.getHotel(id), hotel);
        verify(repository, times(1)).getHotelById(id);
    }
    @Test
    @DisplayName("Service should throw the InvalidRequestException exception")
    void createHotelValidationFailed() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            doThrow(InvalidRequestException.class).when(validationService).validate(hotel);
            service.createHotel(hotel);
        });
    }

    @Test
    @DisplayName("Service should execute hotel creation")
    void createHotelExecuted() {
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(mapper.toEntity(hotel)).thenReturn(entity);
        service.createHotel(hotel);
        verify(repository, times(1)).createHotel(entity);
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void updateUnexistingHotel() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            hotel.setId(1);
            when(repository.getHotelById(hotel.getId())).thenReturn(null);
            service.updateHotel(hotel);
            verify(repository, times(1)).getHotelById(hotel.getId());
        });
    }

    @Test
    @DisplayName("Service should execute hotel updating")
    void updateExistingHotel() {
        hotel.setId(1);
        when(repository.getHotelById(hotel.getId())).thenReturn(entity);
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(mapper.toEntity(hotel)).thenReturn(entity);
        when(mapper.toModel(entity)).thenReturn(hotel);
        when(repository.updateHotel(entity)).thenReturn(entity);
        service.updateHotel(hotel);
        verify(repository, times(1)).getHotelById(hotel.getId());
        verify(repository, times(1)).updateHotel(entity);
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookFullBookedHotel() {
        Assertions.assertThrows(AvailabilityIsZeroException.class, () -> {
            int id = 1;
            entity.setId(id);
            entity.setAvailability(0);
            when(repository.getHotelById(id)).thenReturn(entity);
            service.bookHotel(id);
        });
    }

    @Test
    @DisplayName("Service should execute book accommodation")
    void bookHotelSuccess() {
        int id = 1;
        entity.setId(id);
        entity.setAvailability(1);
        when(repository.getHotelById(id)).thenReturn(entity);
        service.bookHotel(id);
        assertEquals(entity.getAvailability(), 0);
    }

    @Test
    @DisplayName("Service should execute hotel deletion")
    void deleteHotelExecuted() {
        int id = 1;
        when(repository.getHotelById(id)).thenReturn(entity);
        service.deleteHotel(id);
        verify(repository, times(1)).deleteHotel(id);
        verify(repository, times(1)).getHotelById(id);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByRatingSuccess() {
        int rating = 0;
        when(repository.getHotelsByRating(rating)).thenReturn(entityList);
        when(mapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByRating(rating), modelList);
        verify(repository, times(1)).getHotelsByRating(rating);
        verify(mapper, times(1)).toModels(entityList);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByLocationSuccess() {
        String location = "city";
        when(repository.getHotelsByLocation(location)).thenReturn(entityList);
        when(mapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByLocation(location), modelList);
        verify(repository, times(1)).getHotelsByLocation(location);
        verify(mapper, times(1)).toModels(entityList);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByBadgeSuccess() {
        String reputationBadge = "red";
        when(repository.getHotelsByBadge(reputationBadge)).thenReturn(entityList);
        when(mapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByBadge(reputationBadge), modelList);
        verify(repository, times(1)).getHotelsByBadge(reputationBadge);
        verify(mapper, times(1)).toModels(entityList);
    }
}