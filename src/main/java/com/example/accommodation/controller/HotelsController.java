package com.example.accommodation.controller;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.service.HotelsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.accommodation.util.ControllerUtils.BASE_URL;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = BASE_URL)
@RequiredArgsConstructor

public class HotelsController {
    private final HotelsService service;

    /**
     * GET request for getting all hotels in the catalogue.
     * Support optional filters by fields:
     * - reputation
     * - location
     * - reputationBadge
     *
     * @param reputation      - reputation filter value
     * @param location        - location filter value
     * @param reputationBadge - reputationBadge filter value
     * @return list of hotel objects in JSON format
     */
    @GetMapping()
    @ResponseBody
    public List<Hotel> getHotels(@RequestParam(required = false) Integer reputation,
                                 @RequestParam(required = false) String location,
                                 @RequestParam(required = false) String reputationBadge) {
        return service.getHotels(reputation, location, reputationBadge);
    }

    /**
     * OPTIONS request for collection
     *
     * @return OK status with communication options for the target resource
     */
    @RequestMapping(method = RequestMethod.OPTIONS)
    ResponseEntity<String> collectionOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.OPTIONS)
                .build();
    }

    /**
     * GET request that returns accommodation by ID.
     *
     * @param id - hotel ID
     * @return hotel object
     */
    @GetMapping("/{id}")
    @ResponseBody
    public Hotel getHotel(@PathVariable int id) {
        return service.getHotel(id);
    }

    /**
     * OPTIONS request for single entity
     *
     * @return OK status with communication options for the target resource
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<String> singleRequestOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS)
                .build();
    }

    /**
     * Creates new hotel entity
     * Validates fields of hotel object from request
     *
     * @param newHotel - hotel object
     */
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void createHotel(@RequestBody Hotel newHotel) {
        service.createHotel(newHotel);
    }

    /**
     * PUT method to update accommodation by ID.
     *
     * @param id          - hotel ID
     * @param updateHotel - JSON object containing fields that will be updated.
     *                    Note: it is strongly recommended to provide all fields (See README.md)
     * @return updated hotel object.
     */
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Hotel> updateHotel(@PathVariable int id, @RequestBody Hotel updateHotel) {
        updateHotel.setId(id);
        if (service.isLocationExist(updateHotel.getLocation())) {
            return ResponseEntity.status(HttpStatus.OK).body(service.updateHotel(updateHotel));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.updateHotel(updateHotel));
        }
    }

    /**
     * PATCH method for hotel booking.
     * As the result of using this endpoint, availability of the hotel will be decremented.
     *
     * @param id - id of the hotel to book
     * @return hotel object.
     */
    @PatchMapping("/book/{id}")
    @ResponseBody
    public Hotel bookHotel(@PathVariable int id) {
        return service.bookHotel(id);
    }

    /**
     * OPTIONS request for entity booking
     *
     * @return OK status with communication options for the target resource
     */
    @RequestMapping(value = "/book/{id}", method = RequestMethod.OPTIONS)
    ResponseEntity<String> bookingOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.PATCH, HttpMethod.OPTIONS)
                .build();
    }


    /**
     * DELETE request that removes accommodation by ID.
     *
     * @param id - hotel ID
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteHotel(@PathVariable int id) {
        service.deleteHotel(id);
    }
}