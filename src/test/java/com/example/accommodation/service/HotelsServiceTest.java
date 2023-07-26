package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.mapper.HotelMapper;
import com.example.accommodation.mapper.LocationMapper;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepository;
import com.example.accommodation.repository.LocationRepository;
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
    private HotelRepository hotelRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private HotelMapper hotelMapper;
    @Mock
    private LocationMapper locationMapper;
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
                .locationEntity(new LocationEntity(1, "city", "state", "country", 0, "address", new ArrayList<>()))
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
                .location(new Location(1, "city", "state", "country", 0, "address"))
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
        when(hotelRepository.getAllHotels()).thenReturn(entityList);
        when(hotelMapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getAllHotels(), modelList);
        verify(hotelRepository, times(1)).getAllHotels();
        verify(hotelMapper, times(1)).toModels(entityList);
    }
    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void getUnexistingHotel() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            int id = 1;
            when(hotelRepository.getHotelById(id)).thenReturn(null);
            service.getHotel(id);
            verify(hotelRepository, times(1)).getHotelById(id);
        });
    }

    @Test
    @DisplayName("Service should return Hotel object")
    void getExistingHotel() {
        int id = 1;
        entity.setId(id);
        hotel.setId(id);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);
        when(hotelMapper.toModel(entity)).thenReturn(hotel);
        assertEquals(service.getHotel(id), hotel);
        verify(hotelRepository, times(1)).getHotelById(id);
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
        when(hotelMapper.toEntity(hotel)).thenReturn(entity);
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());
        service.createHotel(hotel);
        verify(hotelRepository, times(1)).createHotel(entity);
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void updateUnexistingHotel() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            hotel.setId(1);
            when(hotelRepository.getHotelById(hotel.getId())).thenReturn(null);
            service.updateHotel(hotel);
            verify(hotelRepository, times(1)).getHotelById(hotel.getId());
        });
    }

    @Test
    @DisplayName("Service should execute hotel updating")
    void updateExistingHotel() {
        hotel.setId(1);
        when(hotelRepository.getHotelById(hotel.getId())).thenReturn(entity);
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(hotelMapper.toEntity(hotel)).thenReturn(entity);
        when(hotelMapper.toModel(entity)).thenReturn(hotel);
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());
        when(hotelRepository.updateHotel(entity)).thenReturn(entity);
        service.updateHotel(hotel);
        verify(hotelRepository, times(1)).getHotelById(hotel.getId());
        verify(hotelRepository, times(1)).updateHotel(entity);
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookFullBookedHotel() {
        Assertions.assertThrows(AvailabilityIsZeroException.class, () -> {
            int id = 1;
            entity.setId(id);
            entity.setAvailability(0);
            when(hotelRepository.getHotelById(id)).thenReturn(entity);
            service.bookHotel(id);
        });
    }

    @Test
    @DisplayName("Service should execute book accommodation")
    void bookHotelSuccess() {
        int id = 1;
        entity.setId(id);
        entity.setAvailability(1);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);
        service.bookHotel(id);
        assertEquals(entity.getAvailability(), 0);
    }

    @Test
    @DisplayName("Service should execute hotel deletion")
    void deleteHotelExecuted() {
        int id = 1;
        when(hotelRepository.getHotelById(id)).thenReturn(entity);
        service.deleteHotel(id);
        verify(hotelRepository, times(1)).deleteHotel(id);
        verify(hotelRepository, times(1)).getHotelById(id);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByRatingSuccess() {
        int rating = 0;
        when(hotelRepository.getHotelsByRating(rating)).thenReturn(entityList);
        when(hotelMapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByRating(rating), modelList);
        verify(hotelRepository, times(1)).getHotelsByRating(rating);
        verify(hotelMapper, times(1)).toModels(entityList);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByLocationSuccess() {
        String location = "city";
        when(hotelRepository.getHotelsByLocation(location)).thenReturn(entityList);
        when(hotelMapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByLocation(location), modelList);
        verify(hotelRepository, times(1)).getHotelsByLocation(location);
        verify(hotelMapper, times(1)).toModels(entityList);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByBadgeSuccess() {
        String reputationBadge = "red";
        when(hotelRepository.getHotelsByBadge(reputationBadge)).thenReturn(entityList);
        when(hotelMapper.toModels(entityList)).thenReturn(modelList);
        assertEquals(service.getHotelsByBadge(reputationBadge), modelList);
        verify(hotelRepository, times(1)).getHotelsByBadge(reputationBadge);
        verify(hotelMapper, times(1)).toModels(entityList);
    }
}