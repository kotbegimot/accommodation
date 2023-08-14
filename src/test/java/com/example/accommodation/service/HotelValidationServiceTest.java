package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.util.ValidationProperties;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.reset;

class HotelValidationServiceTest {
    private final ValidationProperties properties = mock(ValidationProperties.class);
    private HotelValidationService service;
    private Hotel hotel;

    @BeforeEach
    public void setup() {
        service = new HotelValidationService(properties);
        hotel = Hotel.builder()
                .id(0)
                .name("name")
                .rating(0)
                .category("category")
                .location(new Location(0, "city", "state", "country", 0, "address"))
                .imageUrl("url")
                .reputation(500)
                .reputationBadge("")
                .price(0)
                .availability(0)
                .build();
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameIsShortTest() {
        when(properties.getNameSymbolsMin()).thenReturn(10);
        when(properties.getErrorMsgNameIsShort()).thenReturn("Hotel name is too short");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorMsgNameIsShort(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameContainsRestrictedWordsTest() {
        when(properties.getNameBlackList()).thenReturn(List.of("name", "free"));
        when(properties.getErrorMsgNameContainsRestrictedWords()).thenReturn("Hotel name contains restricted words");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorMsgNameContainsRestrictedWords(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidRatingTest() {
        hotel.setRating(-1);
        when(properties.getRatingMin()).thenReturn(0);
        when(properties.getRatingMin()).thenReturn(5);
        when(properties.getErrorInvalidRating()).thenReturn("Hotel name contains restricted words");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorInvalidRating(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidCategoryTest() {
        when(properties.getCategoryWhiteList()).thenReturn(List.of("hotel", "alternative", "hostel", "lodge"));
        when(properties.getErrorInvalidCategory()).thenReturn("Hotel category is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorInvalidCategory(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidImageUrlTest() {
        when(properties.getErrorImageUrl()).thenReturn("Hotel image url is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorImageUrl(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidLocationZipCodeTest() {
        when(properties.getZipCodeLength()).thenReturn(5);
        when(properties.getErrorLocationZipCode()).thenReturn("Hotel zip code is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorLocationZipCode(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidReputationTest() {
        hotel.setReputation(1200);
        when(properties.getReputationMin()).thenReturn(0);
        when(properties.getReputationMax()).thenReturn(1000);
        when(properties.getErrorReputation()).thenReturn("Hotel reputation is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorReputation(), service.getErrorMessage());
    }

    @Test
    @DisplayName("Service should return correct error string")
    void getErrorMessageTest() {
        assertEquals("", service.getErrorMessage());
    }

    @AfterEach
    public void tearDown() {
        reset(properties);
    }
}