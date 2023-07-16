package com.example.accommodation.dao;

import com.example.accommodation.entity.HotelEntity;

import java.util.List;

public interface  HotelDAO {
    List<HotelEntity> getAll();
    HotelEntity getById(int id);
    void create(HotelEntity hotel);
}
