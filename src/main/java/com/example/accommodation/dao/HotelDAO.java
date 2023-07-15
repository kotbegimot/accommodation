package com.example.accommodation.dao;

import com.example.accommodation.entity.Hotel;

import java.util.List;

public interface  HotelDAO {
    List<Hotel> getAll();
    Hotel getById(int id);
    void save(Hotel hotel);
}
