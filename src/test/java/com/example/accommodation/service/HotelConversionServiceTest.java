package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.properties.ValidationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelConversionServiceTest {
    private final ValidationProperties properties = mock(ValidationProperties.class);
    private HotelConversionService service;
    private Hotel hotel;
    @BeforeEach
    void setup() {
        service = new HotelConversionService(properties);
        hotel = Hotel.builder()
                .id(0)
                .name("name")
                .rating(0)
                .category("category")
                .location(new Location(0, "city", "state", "country", 0, "address"))
                .imageUrl("url")
                .reputation(0)
                .reputationBadge("")
                .price(0)
                .availability(0)
                .build();
    }

    @Test
    @DisplayName("Service should set the red badge")
    void calculateRedBadgeTest() {
        hotel.setReputation(500);
        when(properties.getReputationRedThreshold()).thenReturn(500);
        when(properties.getReputationRedBadge()).thenReturn("red");
        hotel = service.convert(hotel);
        assertEquals(properties.getReputationRedBadge(), hotel.getReputationBadge());
    }

    @Test
    @DisplayName("Service should set the yellow badge")
    void calculateYellowBadgeTest() {
        hotel.setReputation(799);
        when(properties.getReputationYellowThreshold()).thenReturn(799);
        when(properties.getReputationYellowBadge()).thenReturn("yellow");
        hotel = service.convert(hotel);
        assertEquals(properties.getReputationYellowBadge(), hotel.getReputationBadge());
    }

    @Test
    @DisplayName("Service should set the green badge")
    void calculateGreenBadgeTest() {
        hotel.setReputation(800);
        when(properties.getReputationRedThreshold()).thenReturn(500);
        when(properties.getReputationYellowThreshold()).thenReturn(799);
        when(properties.getReputationGreenBadge()).thenReturn("green");
        hotel = service.convert(hotel);
        assertEquals(properties.getReputationGreenBadge(), hotel.getReputationBadge());
    }

    @AfterEach
    public void tearDown() {
        reset(properties);
    }
}