package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.properties.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Class contains method to calculate the correct reputation badge from numerical value.
 */
@Service
@RequiredArgsConstructor
public class HotelConversionService {
    private final ValidationProperties properties;

    public Hotel convert(Hotel hotel) {
        hotel.setReputationBadge(calculateReputationBadge(hotel.getReputation()));
        return hotel;
    }

    private String calculateReputationBadge(int reputation) {
        String reputationBadge;
        if (reputation <= properties.getReputationRedThreshold()) {
            reputationBadge = properties.getReputationRedBadge();
        } else if (reputation <= properties.getReputationYellowThreshold()) {
            reputationBadge = properties.getReputationYellowBadge();
        } else {
            reputationBadge = properties.getReputationGreenBadge();
        }
        return reputationBadge;
    }
}
