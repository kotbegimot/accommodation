package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelsServiceTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private LocationRepository locationRepository;
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
    void getAllHotelsTest() {
        when(hotelRepository.getAllHotels()).thenReturn(entityList);

        assertEquals(service.getAllHotels(), modelList);
        verify(hotelRepository, times(1)).getAllHotels();
    }
    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void getUnexistingHotelTest() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            int id = 1;
            when(hotelRepository.getHotelById(id)).thenReturn(null);

            service.getHotel(id);

            verify(hotelRepository, times(1)).getHotelById(id);
        });
    }

    @Test
    @DisplayName("Service should return Hotel object")
    void getExistingHotelTest() {
        int id = 1;
        entity.setId(id);
        hotel.setId(id);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);

        assertEquals(service.getHotel(id), hotel);
        verify(hotelRepository, times(1)).getHotelById(id);
    }
    @Test
    @DisplayName("Service should throw the InvalidRequestException exception")
    void createHotelValidationFailedTest() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            doThrow(InvalidRequestException.class).when(validationService).validate(hotel);

            service.createHotel(hotel);

            verify(validationService, times(1)).validate(hotel);
        });
    }

    @Test
    @DisplayName("Service should execute hotel creation")
    void createHotelExecutedTest() {
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());

        service.createHotel(hotel);

        verify(hotelRepository, times(1)).createHotel(entity);
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
    }

    @Test
    @DisplayName("Service should execute hotel creation")
    void createHotelAndLocationTest() {
        LocationEntity newLocationEntity = new LocationEntity(0, "city", "state", "country", 0, "address", new ArrayList<>());
        List<LocationEntity> locations = List.of(entity.getLocationEntity());
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(locationRepository.getLocationById(1)).thenReturn(null);
        doNothing().when(locationRepository).createLocation(newLocationEntity);
        when(locationRepository.getLocationsByAddress(hotel.getLocation().getAddress())).thenReturn(locations);

        service.createHotel(hotel);

        verify(validationService, times(1)).validate(hotel);
        verify(conversionService, times(1)).convert(hotel);
        verify(locationRepository, times(1)).getLocationById(1);
        verify(locationRepository, times(1)).createLocation(newLocationEntity);
        verify(locationRepository, times(1)).getLocationsByAddress(hotel.getLocation().getAddress());
        verify(hotelRepository, times(1)).createHotel(entity);
    }

    @Test
    @DisplayName("Service should execute hotel creation")
    void createHotelLocationIsFindedByNameTest() {
        hotel.getLocation().setId(0);
        List<LocationEntity> locations = List.of(entity.getLocationEntity());
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(locationRepository.getLocationsByAddress(hotel.getLocation().getAddress())).thenReturn(locations);

        service.createHotel(hotel);

        verify(validationService, times(1)).validate(hotel);
        verify(conversionService, times(1)).convert(hotel);
        verify(locationRepository, times(0)).getLocationById(0);
        verify(locationRepository, times(1)).getLocationsByAddress(hotel.getLocation().getAddress());
        verify(hotelRepository, times(1)).createHotel(entity);
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void updateUnexistingHotelTest() {
        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> {
            hotel.setId(1);
            when(hotelRepository.getHotelById(hotel.getId())).thenReturn(null);

            service.updateHotel(hotel);

            verify(hotelRepository, times(1)).getHotelById(hotel.getId());
        });
    }

    @Test
    @DisplayName("Service should execute hotel updating")
    void updateExistingHotelTest() {
        hotel.setId(1);
        entity.setId(1);
        when(hotelRepository.getHotelById(hotel.getId())).thenReturn(entity);
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());
        when(hotelRepository.updateHotel(entity)).thenReturn(entity);

        service.updateHotel(hotel);

        verify(hotelRepository, times(1)).getHotelById(hotel.getId());
        verify(hotelRepository, times(1)).updateHotel(entity);
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
        verify(conversionService, times(1)).convert(hotel);
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookFullBookedHotelTest() {
        Assertions.assertThrows(AvailabilityIsZeroException.class, () -> {
            int id = 1;
            entity.setId(id);
            entity.setAvailability(0);
            when(hotelRepository.getHotelById(id)).thenReturn(entity);

            service.bookHotel(id);

            verify(hotelRepository, times(1)).getHotelById(id);
        });
    }

    @Test
    @DisplayName("Service should execute book accommodation")
    void bookHotelSuccessTest() {
        int id = 1;
        entity.setId(id);
        entity.setAvailability(1);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);
        when(hotelRepository.updateHotel(entity)).thenReturn(entity);
        service.bookHotel(id);

        assertEquals(entity.getAvailability(), 0);
        verify(hotelRepository, times(1)).getHotelById(id);
        verify(hotelRepository, times(1)).updateHotel(entity);
    }

    @Test
    @DisplayName("Service should execute hotel deletion")
    void deleteHotelExecutedTest() {
        int id = 1;
        when(hotelRepository.getHotelById(id)).thenReturn(entity);

        service.deleteHotel(id);

        verify(hotelRepository, times(1)).deleteHotel(id);
        verify(hotelRepository, times(1)).getHotelById(id);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByRatingSuccessTest() {
        int rating = 0;
        when(hotelRepository.getHotelsByRating(rating)).thenReturn(entityList);

        assertEquals(service.getHotelsByRating(rating), modelList);
        verify(hotelRepository, times(1)).getHotelsByRating(rating);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByLocationSuccessTest() {
        String location = "city";
        when(hotelRepository.getHotelsByLocation(location)).thenReturn(entityList);

        assertEquals(service.getHotelsByLocation(location), modelList);
        verify(hotelRepository, times(1)).getHotelsByLocation(location);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByBadgeSuccessTest() {
        String reputationBadge = "red";
        when(hotelRepository.getHotelsByBadge(reputationBadge)).thenReturn(entityList);

        assertEquals(service.getHotelsByBadge(reputationBadge), modelList);
        verify(hotelRepository, times(1)).getHotelsByBadge(reputationBadge);
    }

    @Test
    @DisplayName("Service should return true")
    void isLocationExistTestTest() {
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());

        assertTrue(service.isLocationExist(hotel.getLocation()));
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
    }


}