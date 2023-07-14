package com.example.accommodation.service;

import com.example.accommodation.model.Location;
import com.example.accommodation.util.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class HotelValidationService {
    private final static Logger logger = LoggerFactory.getLogger(HotelValidationService.class);
    private final ValidationProperties properties;

    public boolean validateName(String name) {
        boolean retVal = false;
        if (name.length() > properties.getNameSymbolsMin()) {
            retVal = properties.getNameBlackList().stream().anyMatch(name::contains);
        }
        return retVal;
    }
    public boolean validateRating(int rating) {
        return rating >= properties.getRatingMin() && rating <= properties.getRatingMax();
    }
    public boolean validateCategory(String category) {
        return properties.getCategoryWhiteList().stream().anyMatch(category::contains);
    }
    public boolean validateLocation(Location location) {
        return String.valueOf(location.getZipCode()).length() == properties.getZipCodeLength();
    }
    public boolean validateImagePath(String url) throws MalformedURLException, URISyntaxException {
        boolean retVal = true;
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            logger.error("URL {} is invalid, error: {}", url, e.getMessage());
            retVal = false;
        }
        return retVal;
    }
    public boolean validateReputation(int reputation) {
        return reputation >= properties.getReputationMin() && reputation <= properties.getReputationMax();
    }
}
