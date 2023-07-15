package com.example.accommodation.controller;

import com.example.accommodation.dao.HotelDAO;
import com.example.accommodation.model.Catalogue;
//import com.example.accommodation.model.Hotel;
import com.example.accommodation.entity.Hotel;
import com.example.accommodation.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CatalogueController {
    //private final HotelsService service;
    private HotelDAO hotelDAO;

    /**
     * "Get" request for getting all products after deduplication
     * @return all products in JSON format
     */

    @GetMapping(value = { "/api/v1/hotels/{id}", "/api/v1/hotels" })
    @ResponseBody
    public Catalogue getCatalogue(@PathVariable Optional<Integer> id) {
        List<Hotel> hotels;
        if (id.isPresent()) {
            hotels = new ArrayList<Hotel>();
            hotels.add(hotelDAO.getById(id.get()));
            //hotels.add(service.getHotel(id.get()));
        } else {
            hotels = hotelDAO.getAll();
            //hotels = service.getAllHotels();
        }
        return new Catalogue(hotels);
    }
}