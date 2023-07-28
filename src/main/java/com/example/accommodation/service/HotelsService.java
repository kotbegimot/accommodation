package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.util.HotelMapper;
import com.example.accommodation.util.LocationMapper;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepository;
import com.example.accommodation.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contains set of methods that process requests from HotelsController for each business case.
 */
@Service
@RequiredArgsConstructor
public class HotelsService {
    private final HotelRepository hotelRepository;
    private final LocationRepository locationRepository;
    private final HotelValidationService validationService;
    private final HotelConversionService conversionService;
    private HotelEntity checkHotelEntityById(int id) throws NoSuchHotelFoundException {
        HotelEntity entity = hotelRepository.getHotelById(id);
        if (entity == null) {
            throw new NoSuchHotelFoundException(id);
        }
        return entity;
    }

    private LocationEntity findLocationEntity(Location model) {
        LocationEntity entity = null;
        if (model.getAddress() != null) {
            List<LocationEntity> locations = locationRepository.getLocationsByAddress(model.getAddress());
               for (LocationEntity location : locations) {
                if (model.equals(LocationMapper.toModel(location))) {
                    entity = location;
                    break;
                }
            }
        }
        return entity;
    }

    /**
     * Checks location existence in repository
     * @param model - location from request
     * @return null if location is not found
     *         LocationEntity object of the found location
     */
    private LocationEntity checkLocationEntityExistence(Location model) {
        LocationEntity entity;
        if (model.getId() != 0) {
            entity = locationRepository.getLocationById(model.getId());
        } else {
            entity = findLocationEntity(model);
        }
        return entity;
    }

    private LocationEntity createLocationEntity(Location model) {
        model.setId(0);
        locationRepository.createLocation(LocationMapper.toEntity(model));
        return findLocationEntity(model);
    }

    private LocationEntity getLocationEntity(Location locationModel) {
        // check location existence
        LocationEntity locationEntity = checkLocationEntityExistence(locationModel);
        // create location if it doesn't exist
        if (locationEntity == null) {
            locationEntity = createLocationEntity(locationModel);
        }
        return locationEntity;
    }

    public List<Hotel> getAllHotels() {
        return HotelMapper.toModels(hotelRepository.getAllHotels());
    }

    public Hotel getHotel(int id) {
        HotelEntity entity = checkHotelEntityById(id);
        return HotelMapper.toModel(entity);
    }

    public void createHotel(Hotel newHotel) {
        validationService.validate(newHotel);
        newHotel = conversionService.convert(newHotel);
        HotelEntity entity = HotelMapper.toEntity(newHotel);
        entity.setLocationEntity(getLocationEntity(newHotel.getLocation()));
        hotelRepository.createHotel(entity);
    }

    public boolean isLocationExist(Location locationModel) {
        return (checkLocationEntityExistence(locationModel) != null);
    }

    public Hotel updateHotel(Hotel updateHotel) {
        checkHotelEntityById(updateHotel.getId());
        validationService.validate(updateHotel);
        updateHotel = conversionService.convert(updateHotel);
        HotelEntity entity = HotelMapper.toEntity(updateHotel);
        entity.setLocationEntity(getLocationEntity(updateHotel.getLocation()));
        return HotelMapper.toModel(hotelRepository.updateHotel(entity));
    }

    public Hotel bookHotel(int id) throws AvailabilityIsZeroException {
        HotelEntity entity = checkHotelEntityById(id);
        if (entity.getAvailability() < 1) {
            throw new AvailabilityIsZeroException(id);
        }
        entity.setAvailability(entity.getAvailability() - 1);
        return HotelMapper.toModel(hotelRepository.updateHotel(entity));
    }

    public void deleteHotel(int id) {
        checkHotelEntityById(id);
        hotelRepository.deleteHotel(id);
    }
    public List<Hotel> getHotelsByRating(int rating) {
        return HotelMapper.toModels(hotelRepository.getHotelsByRating(rating));
    }

    public List<Hotel> getHotelsByLocation(String location) {
        return HotelMapper.toModels(hotelRepository.getHotelsByLocation(location));
    }

    public List<Hotel> getHotelsByBadge(String reputationBadge) {
        return HotelMapper.toModels(hotelRepository.getHotelsByBadge(reputationBadge));
    }
}
