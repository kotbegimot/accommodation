package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.util.ValidationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class HotelConversionServiceTest {
    @Mock
    private ValidationProperties properties;
    @InjectMocks
    private HotelConversionService service;
    private Hotel hotel;
    @BeforeEach
    public void setup() {
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
        assertEquals(hotel.getReputationBadge(), properties.getReputationRedBadge());
    }

    @Test
    @DisplayName("Service should set the yellow badge")
    void calculateYellowBadgeTest() {
        hotel.setReputation(799);
        when(properties.getReputationYellowThreshold()).thenReturn(799);
        when(properties.getReputationYellowBadge()).thenReturn("yellow");
        hotel = service.convert(hotel);
        assertEquals(hotel.getReputationBadge(), properties.getReputationYellowBadge());
    }

    @Test
    @DisplayName("Service should set the green badge")
    void calculateGreenBadgeTest() {
        hotel.setReputation(800);
        when(properties.getReputationRedThreshold()).thenReturn(500);
        when(properties.getReputationYellowThreshold()).thenReturn(799);
        when(properties.getReputationGreenBadge()).thenReturn("green");
        hotel = service.convert(hotel);
        assertEquals(hotel.getReputationBadge(), properties.getReputationGreenBadge());
    }
}