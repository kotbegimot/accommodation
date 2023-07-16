package com.example.accommodation.service;

import com.example.accommodation.model.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelsService {

    public List<Hotel> getAllHotels() {
        return new ArrayList<>();
    }
    public Hotel getHotel(int id) {
        return null;
    }
}
