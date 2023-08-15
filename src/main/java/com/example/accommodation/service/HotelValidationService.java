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
import java.util.ArrayList;
import java.util.List;

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
    private final List<String> errors = new ArrayList<>();

    void validate(Hotel hotel) throws InvalidRequestException {
        errors.clear();
        for (Method method : this.getClass().getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                try {
                    method.invoke(this, hotel);
                } catch (Exception e) {
                    logger.error("Validation method invocation error: {}, method: {}", e.getMessage(), method.getName());
                }
            }
        }
        if (!errors.isEmpty())
            throw new InvalidRequestException(errors);
    }

    public List<String> getErrorMessages() {
        return errors;
    }

    private void validateName(Hotel hotel) {
        String name = hotel.getName();
        if (name.length() > properties.getNameSymbolsMin()) {
            if (properties.getNameBlackList().stream().anyMatch(name::contains))
                errors.add(properties.getErrorMsgNameContainsRestrictedWords()
                        .formatted(name, properties.getNameBlackList().toString()));
        } else {
            errors.add(properties.getErrorMsgNameIsShort()
                    .formatted(name, properties.getNameSymbolsMin()));
        }
    }

    private void validateRating(Hotel hotel) {
        int rating = hotel.getRating();
        if (!(rating >= properties.getRatingMin() && rating <= properties.getRatingMax()))
            errors.add(properties.getErrorInvalidRating()
                    .formatted(rating, properties.getRatingMin(), properties.getRatingMax()));
    }

    private void validateCategory(Hotel hotel) {
        String category = hotel.getCategory();
        if (properties.getCategoryWhiteList().stream().noneMatch(category::contains))
            errors.add(properties.getErrorInvalidCategory()
                    .formatted(category, properties.getCategoryWhiteList().toString()));
    }

    private void validateImagePath(Hotel hotel) {
        String url = hotel.getImageUrl();
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            errors.add(properties.getErrorImageUrl().formatted(url, e.getMessage()));
        }
    }

    private void validateLocation(Hotel hotel) {
        int zipCode = hotel.getLocation().getZipCode();
        if (String.valueOf(zipCode).length() != properties.getZipCodeLength())
            errors.add(properties.getErrorLocationZipCode()
                    .formatted(zipCode, properties.getZipCodeLength()));
    }

    private void validateReputation(Hotel hotel) {
        int reputation = hotel.getReputation();
        if (!(reputation >= properties.getReputationMin() && reputation <= properties.getReputationMax()))
            errors.add(properties.getErrorReputation()
                    .formatted(reputation, properties.getReputationMin(), properties.getReputationMax()));
    }
}
