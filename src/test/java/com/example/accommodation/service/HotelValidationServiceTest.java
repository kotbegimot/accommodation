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
                .name("Hotel Grand Budapest")
                .rating(5)
                .category("hotel")
                .location(new Location(0, "city", "state", "country", 12345, "address"))
                .imageUrl("http://grand_budapest/img.com")
                .reputation(999)
                .reputationBadge("green")
                .price(150)
                .availability(30)
                .build();
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameIsShortTest() {
        hotel.setName("name");
        when(properties.getNameSymbolsMin()).thenReturn(10);
        when(properties.getErrorMsgNameIsShort()).thenReturn("Hotel name is too short");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorMsgNameIsShort(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameContainsRestrictedWordsTest() {
        hotel.setName("name");
        when(properties.getNameBlackList()).thenReturn(List.of("name", "free"));
        when(properties.getErrorMsgNameContainsRestrictedWords()).thenReturn("Hotel name contains restricted words");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorMsgNameContainsRestrictedWords(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidRatingTest() {
        hotel.setRating(-1);
        when(properties.getRatingMin()).thenReturn(0);
        when(properties.getRatingMin()).thenReturn(5);
        when(properties.getErrorInvalidRating()).thenReturn("Hotel name contains restricted words");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorInvalidRating(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidCategoryTest() {
        hotel.setCategory("category");
        when(properties.getCategoryWhiteList()).thenReturn(List.of("hotel", "alternative", "hostel", "lodge"));
        when(properties.getErrorInvalidCategory()).thenReturn("Hotel category is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorInvalidCategory(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidImageUrlTest() {
        hotel.setImageUrl("url");
        when(properties.getErrorImageUrl()).thenReturn("Hotel image url is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorImageUrl(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidLocationZipCodeTest() {
        hotel.getLocation().setZipCode(0);
        when(properties.getZipCodeLength()).thenReturn(5);
        when(properties.getErrorLocationZipCode()).thenReturn("Hotel zip code is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorLocationZipCode(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidReputationTest() {
        hotel.setReputation(1200);
        when(properties.getReputationMin()).thenReturn(0);
        when(properties.getReputationMax()).thenReturn(1000);
        when(properties.getErrorReputation()).thenReturn("Hotel reputation is invalid");
        Assertions.assertThrows(InvalidRequestException.class, () -> service.validate(hotel));
        assertEquals(properties.getErrorReputation(), service.getErrorMessages().get(0));
        assertEquals(1, service.getErrorMessages().size());
    }

    @Test
    @DisplayName("Service should return correct error string")
    void getErrorMessageTest() {
        assertEquals(0, service.getErrorMessages().size());
    }

    @AfterEach
    public void tearDown() {
        reset(properties);
    }
}