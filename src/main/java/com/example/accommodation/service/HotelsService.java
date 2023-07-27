package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.mapper.HotelMapper;
import com.example.accommodation.mapper.LocationMapper;
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
    private final HotelMapper hotelMapper;
    private final LocationMapper locationMapper;
    private final HotelValidationService validationService;
    private final HotelConversionService conversionService;
    private HotelEntity checkHotelEntityById(int id) throws NoSuchHotelFoundException {
        System.out.println("!!!!!!!! checkHotelEntityById !!!!!!!!!!");
        HotelEntity entity = hotelRepository.getHotelById(id);
        if (entity == null) {
            System.out.println("!!!!!!!! checkHotelEntityById  null!!!!!!!!!!");
            throw new NoSuchHotelFoundException(id);
        }
        System.out.println("!!!!!!!! checkHotelEntityById  return!!!!!!!!!!");
        return entity;
    }

    private LocationEntity findLocationEntity(Location model) {
        LocationEntity entity = null;
        if (model.getAddress() != null) {
            List<LocationEntity> locations = locationRepository.getLocationsByAddress(model.getAddress());
               for (LocationEntity location : locations) {
                if (model.equals(locationMapper.toModel(location))) {
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
        locationRepository.createLocation(locationMapper.toEntity(model));
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
        return hotelMapper.toModels(hotelRepository.getAllHotels());
    }

    public Hotel getHotel(int id) {
        System.out.println("!!!!!!!! getHotel !!!!!!!!!!");
        HotelEntity entity = checkHotelEntityById(id);
        return hotelMapper.toModel(entity);
    }

    public void createHotel(Hotel newHotel) {
        validationService.validate(newHotel);
        newHotel = conversionService.convert(newHotel);
        HotelEntity entity = hotelMapper.toEntity(newHotel);
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
        HotelEntity entity = hotelMapper.toEntity(updateHotel);
        entity.setLocationEntity(getLocationEntity(updateHotel.getLocation()));
        return hotelMapper.toModel(hotelRepository.updateHotel(entity));
    }

    public Hotel bookHotel(int id) throws AvailabilityIsZeroException {
        HotelEntity entity = checkHotelEntityById(id);
        if (entity.getAvailability() < 1) {
            throw new AvailabilityIsZeroException(id);
        }
        entity.setAvailability(entity.getAvailability() - 1);
        return hotelMapper.toModel(hotelRepository.updateHotel(entity));
    }

    public void deleteHotel(int id) {
        checkHotelEntityById(id);
        hotelRepository.deleteHotel(id);
    }
    public List<Hotel> getHotelsByRating(int rating) {
        return hotelMapper.toModels(hotelRepository.getHotelsByRating(rating));
    }

    public List<Hotel> getHotelsByLocation(String location) {
        return hotelMapper.toModels(hotelRepository.getHotelsByLocation(location));
    }

    public List<Hotel> getHotelsByBadge(String reputationBadge) {
        return hotelMapper.toModels(hotelRepository.getHotelsByBadge(reputationBadge));
    }
}
