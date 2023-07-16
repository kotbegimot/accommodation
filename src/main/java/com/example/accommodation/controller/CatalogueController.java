package com.example.accommodation.controller;

import com.example.accommodation.dao.HotelDAO;
import com.example.accommodation.dao.HotelDAOImpl;
import com.example.accommodation.model.Catalogue;
//import com.example.accommodation.model.Hotel;
import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CatalogueController {
    //private final HotelsService service;
    private final HotelDAOImpl hotelDAO;

    /**
     * "Get" request for getting all products after deduplication
     * @return all products in JSON format
     */

    @GetMapping(value = { "/api/v1/hotels/{hotel_id}", "/api/v1/hotels" })
    @ResponseBody
    public Catalogue getCatalogue(@PathVariable Optional<Integer> hotel_id) {
        List<HotelEntity> hotels;
        if (hotel_id.isPresent()) {
            hotels = new ArrayList<HotelEntity>();
            hotels.add(hotelDAO.getById(hotel_id.get()));
            //hotels.add(service.getHotel(id.get()));
        } else {
            hotels = hotelDAO.getAll();
            //hotels = service.getAllHotels();
        }
        return new Catalogue(hotels);
    }

    @PostMapping("/api/v1/hotels/create")
    public HotelEntity createAccommodation (@RequestBody HotelEntity newHotel) {
        //in case input tries to assign an ID and to prevent it from mapping to the existig one
        newHotel.setId(0);
        hotelDAO.create(newHotel);
        return newHotel;
    }

    @PutMapping("/api/v1/hotels/update/{hotel_id}")
    public HotelEntity updateHotel (@PathVariable int hotel_id, @RequestBody HotelEntity hotel) {
        hotel.setId(hotel_id);
        HotelEntity dbHotel = hotelDAO.updateHotel(hotel);
        return dbHotel;
    }

    @PutMapping("/api/v1/hotels/book/{hotel_id}")
    public HotelEntity bookHotel (@PathVariable int hotel_id) {
        HotelEntity dbHotel = hotelDAO.bookHotel(hotel_id);
        return dbHotel;
    }

    @DeleteMapping("/api/v1/hotels/delete/{hotel_id}")
    public void deleteHotel(@PathVariable int hotel_id) {
        HotelEntity tempHotel = hotelDAO.getById(hotel_id);
        if (tempHotel == null) {
            throw new RuntimeException("Not Found "+ hotel_id);
        }
        hotelDAO.deleteHotel(hotel_id);
    }
}