package com.example.accommodation.service;

import com.example.accommodation.model.Location;
import org.springframework.stereotype.Service;

@Service
public class HotelValidationService {
    public boolean validateName(String name) {
        return true;
    }
    public boolean validateRating(int rating) {
        return true;
    }
    public boolean validateCategory(String category) {
        return true;
    }
    public boolean validateLocation(Location location) {
        return true;
    }
    public boolean validateImagePath(String url) {
        return true;
    }
    public boolean validateReputation(int reputation) {
        return true;
    }
}
