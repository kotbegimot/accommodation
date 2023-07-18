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
     * "Get" request for getting all products after deduplication
     * @return all products in JSON format
     */

    @GetMapping()
    @ResponseBody
    public Catalogue getHotels(@PathVariable(required = false) Integer reputation) {
        if (reputation != null) {
            return new Catalogue(service.getHotelsByRating(reputation));
        }
        return new Catalogue(service.getAllHotels());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Hotel getHotel(@PathVariable int id) {
        return service.getHotel(id);
    }

    @PostMapping("/create")
    @ResponseBody
    public void createHotel (@RequestBody Hotel newHotel) {
        service.createHotel(newHotel);
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public Hotel updateHotel (@PathVariable int id, @RequestBody Hotel updateHotel) {
        updateHotel.setId(id);
        return service.updateHotel(updateHotel);
    }

    @PutMapping("/book/{id}")
    @ResponseBody
    public Hotel bookHotel (@PathVariable int id) {
        return service.bookHotel(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteHotel(@PathVariable int id) {
        service.deleteHotel(id);
    }


    @ExceptionHandler(NoSuchHotelFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleNoSuchHotelFoundException(NoSuchHotelFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(AvailabilityIsZeroException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<String> handleAvailabilityIsZeroException(AvailabilityIsZeroException exception) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}