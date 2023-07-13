package com.example.accommodation.service;

import org.springframework.stereotype.Service;

@Service
public class HotelConversionService {
    public String calculateReputationBadge(int reputation) {
        String reputationBadge = "";
        if (reputation <= 500) {
            reputationBadge = "red";
        } else if (reputation <= 799) {
            reputationBadge = "yellow";
        } else {
            reputationBadge = "green";
        }
        return reputationBadge;
    }
}
