package com.example.accommodation.model;

import com.example.accommodation.entity.HotelEntity;
//import com.example.accommodation.model.Hotel;

import java.util.List;

public record Catalogue(List<HotelEntity> products) { }
