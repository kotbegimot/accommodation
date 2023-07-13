package com.example.accommodation.controller;

import com.example.accommodation.model.Catalogue;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogueController {
    private final HotelsService service;

    /**
     * "Get" request for getting all products after deduplication
     * @return all products in JSON format
     */
    @GetMapping("/api/v1/hotels/all")
    public Catalogue getCatalogue() {
        List<Hotel> hotels = service.getAllHotels();
        return new Catalogue(hotels);
    }
}