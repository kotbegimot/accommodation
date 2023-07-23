package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.util.ValidationProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class HotelValidationServiceTest {
    @Mock
    private ValidationProperties properties;
    @InjectMocks
    private HotelValidationService service;

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameIsShort() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
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

            when(properties.getNameSymbolsMin()).thenReturn(10);
            when(properties.getErrorMsgNameIsShort()).thenReturn("Hotel name is too short");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorMsgNameIsShort());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelNameContainsRestrictedWords() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
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

            when(properties.getNameBlackList()).thenReturn(List.of("name","free"));
            when(properties.getErrorMsgNameContainsRestrictedWords()).thenReturn("Hotel name contains restricted words");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorMsgNameContainsRestrictedWords());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidRating() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
                    .id(0)
                    .name("name")
                    .rating(-1)
                    .category("category")
                    .location(new Location(0, "city", "state", "country", 0, "address"))
                    .imageUrl("url")
                    .reputation(500)
                    .reputationBadge("")
                    .price(0)
                    .availability(0)
                    .build();

            when(properties.getRatingMin()).thenReturn(0);
            when(properties.getRatingMin()).thenReturn(5);
            when(properties.getErrorInvalidRating()).thenReturn("Hotel name contains restricted words");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorInvalidRating());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidCategory() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
                    .id(0)
                    .name("name")
                    .rating(-1)
                    .category("category")
                    .location(new Location(0, "city", "state", "country", 0, "address"))
                    .imageUrl("url")
                    .reputation(500)
                    .reputationBadge("")
                    .price(0)
                    .availability(0)
                    .build();

            when(properties.getCategoryWhiteList()).thenReturn(List.of("hotel", "alternative", "hostel", "lodge"));
            when(properties.getErrorInvalidCategory()).thenReturn("Hotel category is invalid");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorInvalidCategory());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidImageUrl() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
                    .id(0)
                    .name("name")
                    .rating(-1)
                    .category("category")
                    .location(new Location(0, "city", "state", "country", 0, "address"))
                    .imageUrl("url")
                    .reputation(500)
                    .reputationBadge("")
                    .price(0)
                    .availability(0)
                    .build();

            when(properties.getErrorImageUrl()).thenReturn("Hotel image url is invalid");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorImageUrl());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidLocationZipCode() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
                    .id(0)
                    .name("name")
                    .rating(-1)
                    .category("category")
                    .location(new Location(0, "city", "state", "country", 0, "address"))
                    .imageUrl("url")
                    .reputation(500)
                    .reputationBadge("")
                    .price(0)
                    .availability(0)
                    .build();

            when(properties.getZipCodeLength()).thenReturn(5);
            when(properties.getErrorLocationZipCode()).thenReturn("Hotel zip code is invalid");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorLocationZipCode());
        });
    }

    @Test
    @DisplayName("Service should throw the Invalid Request exception")
    void hotelInvalidReputation() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            Hotel hotel = Hotel.builder()
                    .id(0)
                    .name("name")
                    .rating(-1)
                    .category("category")
                    .location(new Location(0, "city", "state", "country", 0, "address"))
                    .imageUrl("url")
                    .reputation(1200)
                    .reputationBadge("")
                    .price(0)
                    .availability(0)
                    .build();

            when(properties.getReputationMin()).thenReturn(0);
            when(properties.getReputationMax()).thenReturn(1000);
            when(properties.getErrorReputation()).thenReturn("Hotel reputation is invalid");
            service.validate(hotel);
            assertEquals(service.getErrorMessage(), properties.getErrorReputation());
        });
    }
}