package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepositoryJPA;
import com.example.accommodation.repository.LocationRepositoryJPA;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class HotelsServiceTest {
    private final HotelRepositoryJPA hotelRepository = mock(HotelRepositoryJPA.class);

    private final LocationRepositoryJPA locationRepository = mock(LocationRepositoryJPA.class);

    private final HotelValidationService validationService = mock(HotelValidationService.class);

    private final HotelConversionService conversionService = mock(HotelConversionService.class);

    private HotelsService service;
    private HotelEntity entity;
    private Hotel hotel;
    private List<HotelEntity> entityList;
    private List<Hotel> modelList;

    @BeforeEach
    void setup() {
        service = new HotelsService(hotelRepository, locationRepository, validationService, conversionService);
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

        assertEquals(modelList, service.getAllHotels());
        verify(hotelRepository, times(1)).getAllHotels();
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void getUnexistingHotelTest() {
        int id = 1;
        when(hotelRepository.getHotelById(id)).thenReturn(null);

        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> service.getHotel(id));

        verify(hotelRepository, times(1)).getHotelById(id);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should return Hotel object")
    void getExistingHotelTest() {
        int id = 1;
        entity.setId(id);
        hotel.setId(id);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);

        assertEquals(hotel, service.getHotel(id));
        verify(hotelRepository, times(1)).getHotelById(id);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should throw the InvalidRequestException exception")
    void createHotelValidationFailedTest() {
        doThrow(InvalidRequestException.class).when(validationService).validate(hotel);

        Assertions.assertThrows(InvalidRequestException.class, () -> service.createHotel(hotel));

        verify(validationService, times(1)).validate(hotel);
        verifyNoMoreInteractions(validationService);
        verifyNoInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should execute hotel creation")
    void createHotelExecutedTest() {
        doNothing().when(validationService).validate(hotel);
        when(conversionService.convert(hotel)).thenReturn(hotel);
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());

        service.createHotel(hotel);

        ArgumentCaptor<HotelEntity> captor = ArgumentCaptor.forClass(HotelEntity.class);
        verify(hotelRepository, times(1)).createHotel(captor.capture());
        assertEquals(captor.getValue().getName(), hotel.getName());
        assertEquals(captor.getValue().getCategory(), hotel.getCategory());
        assertEquals(captor.getValue().getReputation(), hotel.getReputation());
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
        verifyNoMoreInteractions(hotelRepository);
        verifyNoMoreInteractions(locationRepository);
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
        verifyNoMoreInteractions(hotelRepository);
        verifyNoMoreInteractions(locationRepository);
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
        verifyNoMoreInteractions(hotelRepository);
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should throw the NoSuchHotelFoundException exception")
    void updateUnexistingHotelTest() {
        hotel.setId(1);
        when(hotelRepository.getHotelById(hotel.getId())).thenReturn(null);

        Assertions.assertThrows(NoSuchHotelFoundException.class, () -> service.updateHotel(hotel));

        verify(hotelRepository, times(1)).getHotelById(hotel.getId());
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
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
        verifyNoMoreInteractions(hotelRepository);
        verifyNoMoreInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should throw the AvailabilityIsZeroException exception")
    void bookFullBookedHotelTest() {
        int id = 1;
        entity.setId(id);
        entity.setAvailability(0);
        when(hotelRepository.getHotelById(id)).thenReturn(entity);

        Assertions.assertThrows(AvailabilityIsZeroException.class, () -> service.bookHotel(id));

        verify(hotelRepository, times(1)).getHotelById(id);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
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

        assertEquals(0, entity.getAvailability());
        verify(hotelRepository, times(1)).getHotelById(id);
        verify(hotelRepository, times(1)).updateHotel(entity);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should execute hotel deletion")
    void deleteHotelExecutedTest() {
        int id = 1;
        when(hotelRepository.getHotelById(id)).thenReturn(entity);

        service.deleteHotel(id);

        verify(hotelRepository, times(1)).deleteHotel(id);
        verify(hotelRepository, times(1)).getHotelById(id);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByRatingSuccessTest() {
        int rating = 0;
        when(hotelRepository.getHotelsByRating(rating)).thenReturn(entityList);

        assertEquals(modelList, service.getHotelsByRating(rating));
        verify(hotelRepository, times(1)).getHotelsByRating(rating);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByLocationSuccessTest() {
        String location = "city";
        when(hotelRepository.getHotelsByLocation(location)).thenReturn(entityList);

        assertEquals(modelList, service.getHotelsByLocation(location));
        verify(hotelRepository, times(1)).getHotelsByLocation(location);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should return list of Hotel objects")
    void getHotelsByBadgeSuccessTest() {
        String reputationBadge = "red";
        when(hotelRepository.getHotelsByBadge(reputationBadge)).thenReturn(entityList);

        assertEquals(modelList, service.getHotelsByBadge(reputationBadge));
        verify(hotelRepository, times(1)).getHotelsByBadge(reputationBadge);
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(locationRepository);
    }

    @Test
    @DisplayName("Service should return true")
    void isLocationExistTestTest() {
        when(locationRepository.getLocationById(hotel.getLocation().getId())).thenReturn(entity.getLocationEntity());

        assertTrue(service.isLocationExist(hotel.getLocation()));
        verify(locationRepository, times(1)).getLocationById(hotel.getLocation().getId());
        verifyNoMoreInteractions(locationRepository);
        verifyNoInteractions(hotelRepository);
    }

    @AfterEach
    public void tearDown() {
        reset(hotelRepository);
        reset(locationRepository);
        reset(conversionService);
        reset(validationService);
    }
}