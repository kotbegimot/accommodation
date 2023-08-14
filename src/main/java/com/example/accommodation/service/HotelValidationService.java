package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.util.ValidationProperties;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;

/**
 * Implementation of validation per parameters:
 * - name
 * - rating
 * - category
 * - image URL
 * - location
 * - reputation
 */
@Service
@RequiredArgsConstructor
public class HotelValidationService {
    private static final Logger logger = LoggerFactory.getLogger(HotelValidationService.class);
    private final ValidationProperties properties;
    private String errorMessage = "";

    void validate(Hotel hotel) throws InvalidRequestException {
        boolean valid = true;
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                try {
                    valid = (boolean) method.invoke(this, hotel);
                    if (!valid)
                        break;
                } catch (Exception e) {
                    logger.error("Validation method invocation error: {}, method: {}", e.getMessage(), method.getName());
                }
            }
        }
        if (!valid)
            throw new InvalidRequestException(errorMessage);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private boolean validateName(Hotel hotel) {
        String name = hotel.getName();
        boolean valid = false;
        if (name.length() > properties.getNameSymbolsMin()) {
            valid = properties.getNameBlackList().stream().noneMatch(name::contains);
            if (!valid)
                errorMessage = properties.getErrorMsgNameContainsRestrictedWords()
                        .formatted(name, properties.getNameBlackList().toString());
        } else {
            errorMessage = properties.getErrorMsgNameIsShort()
                    .formatted(name, properties.getNameSymbolsMin());
        }
        return valid;
    }

    private boolean validateRating(Hotel hotel) {
        int rating = hotel.getRating();
        boolean valid = rating >= properties.getRatingMin() && rating <= properties.getRatingMax();
        if (!valid)
            errorMessage = properties.getErrorInvalidRating()
                    .formatted(rating, properties.getRatingMin(), properties.getRatingMax());
        return valid;
    }

    private boolean validateCategory(Hotel hotel) {
        String category = hotel.getCategory();
        boolean valid = properties.getCategoryWhiteList().stream().anyMatch(category::contains);
        if (!valid)
            errorMessage = properties.getErrorInvalidCategory()
                    .formatted(category, properties.getCategoryWhiteList().toString());
        return valid;
    }

    private boolean validateImagePath(Hotel hotel) {
        String url = hotel.getImageUrl();
        boolean retVal = true;
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            errorMessage = properties.getErrorImageUrl().formatted(url, e.getMessage());
            retVal = false;
        }
        return retVal;
    }

    private boolean validateLocation(Hotel hotel) {
        int zipCode = hotel.getLocation().getZipCode();
        boolean valid = String.valueOf(zipCode).length() == properties.getZipCodeLength();
        if (!valid)
            errorMessage = properties.getErrorLocationZipCode()
                    .formatted(zipCode, properties.getZipCodeLength());
        return valid;
    }

    private boolean validateReputation(Hotel hotel) {
        int reputation = hotel.getReputation();
        boolean valid = reputation >= properties.getReputationMin() && reputation <= properties.getReputationMax();
        if (!valid)
            errorMessage = properties.getErrorReputation()
                    .formatted(reputation, properties.getReputationMin(), properties.getReputationMax());
        return valid;
    }
}
