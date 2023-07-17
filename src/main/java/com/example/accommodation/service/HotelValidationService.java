package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.util.ValidationProperties;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

@Service
@RequiredArgsConstructor

public class HotelValidationService {
    private final static Logger logger = LoggerFactory.getLogger(HotelValidationService.class);
    private final ValidationProperties properties;
    private String errorMessage = "";


    void validate(Hotel hotel) throws InvalidRequestException {
        Class c = this.getClass();
        System.out.println("void validate(Hotel hotel)");
        boolean valid = true;
        for (Method method : c.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                try {
                    valid = (boolean) method.invoke(this, hotel);
                    if (!valid)
                        break;
                } catch (Exception e) {
                    logger.error("OOOPS: {}", e.getMessage());
                }
            }
        }
        if (!valid)
            throw new InvalidRequestException(errorMessage);
    }

    private boolean validateName(Hotel hotel) {
        logger.info("validateName");
        String name = hotel.getName();
        boolean retVal = false;
        if (name.length() > properties.getNameSymbolsMin()) {
            retVal = !properties.getNameBlackList().stream().anyMatch(name::contains);
        }
        return retVal;
    }
    private boolean validateRating(Hotel hotel) {
        logger.info("validateRating");
        int rating = hotel.getRating();
        return rating >= properties.getRatingMin() && rating <= properties.getRatingMax();
    }
    private boolean validateCategory(Hotel hotel) {
        logger.info("validateCategory");
        String category = hotel.getCategory();
        return properties.getCategoryWhiteList().stream().anyMatch(category::contains);
    }
    private boolean validateImagePath(Hotel hotel) throws MalformedURLException, URISyntaxException {
        logger.info("validateImagePath");
        String url = hotel.getImageUrl();
        boolean retVal = true;
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            errorMessage = "URL %s is invalid, error: %s".formatted(url, e.getMessage());
            retVal = false;
        }
        return retVal;
    }
    private boolean validateLocation(Hotel hotel) {
        logger.info("validateLocation");
        return true;
        //return String.valueOf(location.getZipCode()).length() == properties.getZipCodeLength();
    }

    private boolean validateReputation(Hotel hotel) {
        logger.info("validateReputation");
        int reputation = hotel.getReputation();
        return reputation >= properties.getReputationMin() && reputation <= properties.getReputationMax();
    }
}
