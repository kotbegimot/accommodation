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

@RestController("/api/v1/hotels")
@RequiredArgsConstructor
public class HotelsController {
    private final HotelsService service;

    /**
     * "Get" request for getting all products after deduplication
     * @return all products in JSON format
     */

    @GetMapping("/api/v1/hotels")
    @ResponseBody
    public Catalogue getHotels() {
        return new Catalogue(service.getAllHotels());
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/api/v1/hotels/{id}")
    @ResponseBody
    public Hotel getHotel(@PathVariable int id) {
        return service.getHotel(id);
    }

    /**
     *
     * @param newHotel
     * @return
     */
    @PostMapping("/api/v1/hotels/create")
    @ResponseBody
    public void createHotel (@RequestBody Hotel newHotel) {
        service.createHotel(newHotel);
    }

    /**
     *
     * @param id
     * @param updateHotel
     * @return
     */
    @PutMapping("/api/v1/hotels/update/{id}")
    @ResponseBody
    public Hotel updateHotel (@PathVariable int id, @RequestBody Hotel updateHotel) {
        updateHotel.setId(id);
        return service.updateHotel(updateHotel);
    }

    /**
     *
     * @param id
     * @return changed hotel object
     */
    @PutMapping("/api/v1/hotels/book/{id}")
    @ResponseBody
    public Hotel bookHotel (@PathVariable int id) {
        return service.bookHotel(id);
    }

    /**
     *
     * @param id
     */
    @DeleteMapping("/api/v1/hotels/delete/{id}")
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