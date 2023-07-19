package com.example.accommodation.controller;

import com.example.accommodation.model.Catalogue;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.InvalidRequestException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.accommodation.util.Globals.BASE_URL;
@RestController
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor

public class HotelsController {
    private final HotelsService service;

    /**
     *  GET request for getting all hotels in the catalogue.
     *  Support optional filters by fields:
     * - reputation
     * - location
     * - reputationBadge
     * @param reputation - reputation filter value
     * @param location - location filter value
     * @param reputationBadge - reputationBadge filter value
     * @return list of hotel objects in JSON format
     */
    @GetMapping()
    @ResponseBody
    public Catalogue getHotels(@RequestParam(required = false) Integer reputation,
                               @RequestParam(required = false) String location,
                               @RequestParam(required = false) String reputationBadge)
    {
        if (reputation != null && reputation > 0) {
            return new Catalogue(service.getHotelsByRating(reputation));
        } else if (location != null && !location.isEmpty()) {
            return new Catalogue(service.getHotelsByLocation(location));
        } else if (reputationBadge != null && !reputationBadge.isEmpty()) {
            return new Catalogue(service.getHotelsByBadge(reputationBadge));
        }
        return new Catalogue(service.getAllHotels());
    }

    /**
     * GET request that returns accommodation by ID.
     * @param id - hotel ID
     * @return hotel object
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Hotel getHotel(@PathVariable int id) {
        return service.getHotel(id);
    }

    /**
     * Creates new hotel entity
     * Validates fields of hotel object from request
     * @param newHotel - hotel object
     */
    @PostMapping("/create")
    @ResponseBody
    public void createHotel (@RequestBody Hotel newHotel) {
        service.createHotel(newHotel);
    }

    /**
     * PUT method to update accommodation by ID.
     * @param id - hotel ID
     * @param updateHotel - JSON object containing fields that will be updated.
     * Note: it is strongly recommended to provide all fields (See README.md)
     * @return updated hotel object.
     */
    @PutMapping("/update/{id}")
    @ResponseBody
    public Hotel updateHotel (@PathVariable int id, @RequestBody Hotel updateHotel) {
        updateHotel.setId(id);
        return service.updateHotel(updateHotel);
    }

    /**
     * PUT method for hotel booking.
     * As the result of using this endpoint, availability of the hotel will be decremented.
     * @param id - id of the hotel to book
     * @return hotel object.
     */
    @PutMapping("/book/{id}")
    @ResponseBody
    public Hotel bookHotel (@PathVariable int id) {
        return service.bookHotel(id);
    }

    /**
     * DELETE request that removes accommodation by ID.
     * @param id - hotel ID
     */
    @DeleteMapping("/delete/{id}")
    public void deleteHotel(@PathVariable int id) {
        service.deleteHotel(id);
    }

    /**
     * Custom exception, returns 404 if the requested hotel does not exist.
     */
    @ExceptionHandler(NoSuchHotelFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchHotelFoundException(NoSuchHotelFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    /**
     * Custom exception, returns 405 if when booking a hotel, the availability is 0.
     */
    @ExceptionHandler(AvailabilityIsZeroException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleAvailabilityIsZeroException(AvailabilityIsZeroException exception) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(exception.getMessage());
    }

    /**
     * Custom exception, returns 400 if POST or PUT request body contains invalid values.
     */
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}