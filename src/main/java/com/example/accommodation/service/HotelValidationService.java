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
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class HotelValidationService {
    private final static Logger logger = LoggerFactory.getLogger(HotelValidationService.class);
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

    private boolean validateName(Hotel hotel) {
        String name = hotel.getName();
        boolean valid = false;
        if (name.length() > properties.getNameSymbolsMin()) {
            valid = properties.getNameBlackList().stream().noneMatch(name::contains);
            if (!valid)
                errorMessage = "Hotel name \"%s\" contains restricted words [%s]"
                        .formatted(name, properties.getNameBlackList().toString());
        } else {
            errorMessage = "Hotel name \"%s\" length must be longer than %d"
                    .formatted(name, properties.getNameSymbolsMin());
        }
        return valid;
    }
    private boolean validateRating(Hotel hotel) {
        int rating = hotel.getRating();
        boolean valid = false;
        valid = rating >= properties.getRatingMin() && rating <= properties.getRatingMax();
        if (!valid)
            errorMessage = "Hotel rating \"%d\" must be [%d, %d]"
                    .formatted(rating, properties.getRatingMin(), properties.getRatingMax());
        return valid;
    }
    private boolean validateCategory(Hotel hotel) {
        String category = hotel.getCategory();
        boolean valid = properties.getCategoryWhiteList().stream().anyMatch(category::contains);
        if (!valid)
            errorMessage = "Hotel category \"%s\" must be one of the words: [%s]"
                    .formatted(category, properties.getCategoryWhiteList().toString());
        return valid;
    }
    private boolean validateImagePath(Hotel hotel) throws MalformedURLException, URISyntaxException {
        String url = hotel.getImageUrl();
        boolean retVal = true;
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            errorMessage = "Hotel image URL \"%s\" is invalid, error: %s".formatted(url, e.getMessage());
            retVal = false;
        }
        return retVal;
    }
    private boolean validateLocation(Hotel hotel) {
        return true;
        // errorMessage = ""
        //return String.valueOf(location.getZipCode()).length() == properties.getZipCodeLength();
    }

    private boolean validateReputation(Hotel hotel) {
        int reputation = hotel.getReputation();
        boolean valid = reputation >= properties.getReputationMin() && reputation <= properties.getReputationMax();
        if (!valid)
            errorMessage = "Hotel reputation \"%d\" must be [%d, %d]"
                    .formatted(reputation, properties.getReputationMin(), properties.getReputationMax());
        return valid;
    }
}
