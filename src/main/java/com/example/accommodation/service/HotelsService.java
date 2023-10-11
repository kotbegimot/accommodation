package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.entity.LocationEntity;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.HotelAlreadyExistsException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepositoryJPA;
import com.example.accommodation.repository.LocationRepositoryJPA;
import com.example.accommodation.util.HotelMapper;
import com.example.accommodation.util.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Contains set of methods that process requests from HotelsController for each business case.
 */
@Service
@RequiredArgsConstructor
public class HotelsService {
    private final HotelValidationService validationService;
    private final HotelConversionService conversionService;
    private final HotelRepositoryJPA hotelRepositoryJPA;
    private final LocationRepositoryJPA locationRepositoryJPA;

    private LocationEntity findLocationEntity(Location model) {
        LocationEntity entity = null;
        if (model.getAddress() != null) {
            List<LocationEntity> locations = locationRepositoryJPA.findByAddress(model.getAddress());
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
     *
     * @param model - location from request
     * @return null if location is not found
     * LocationEntity object of the found location
     */
    private LocationEntity checkLocationEntityExistence(Location model) {
        LocationEntity entity;
        if (model.getId() != 0) {
            entity = locationRepositoryJPA.findById(model.getId()).get();
        } else {
            entity = findLocationEntity(model);
        }
        return entity;
    }

    @Transactional
    private LocationEntity createLocationEntity(Location model) {
        model.setId(0);
        locationRepositoryJPA.save(LocationMapper.toEntity(model));
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

    public List<Hotel> getHotels(Integer reputation, String location, String reputationBadge) {
        if (reputation != null && reputation > 0) {
            return getHotelsByRating(reputation);
        } else if (location != null && !location.isEmpty()) {
            return getHotelsByLocation(location);
        } else if (reputationBadge != null && !reputationBadge.isEmpty()) {
            return getHotelsByBadge(reputationBadge);
        }
        return getAllHotels();
    }

    public List<Hotel> getAllHotels() {
        return HotelMapper.toModels(hotelRepositoryJPA.findAll());
    }

    //@Cacheable()
    public Hotel getHotel(int id) {
        return HotelMapper.toModel(hotelRepositoryJPA.findById(id).orElseThrow(() -> new NoSuchHotelFoundException(id)));
    }

    @Transactional
    public void createHotel(Hotel newHotel) {
        // validation
        validationService.validate(newHotel);
        String hotelName = newHotel.getName();
        hotelRepositoryJPA.findByName(hotelName).orElseThrow(() -> new HotelAlreadyExistsException(hotelName));
        // conversion
        newHotel = conversionService.convert(newHotel);
        HotelEntity entity = HotelMapper.toEntity(newHotel);
        entity.setLocationEntity(getLocationEntity(newHotel.getLocation()));
        // persist
        hotelRepositoryJPA.save(entity);
    }

    public boolean isLocationExist(Location locationModel) {
        return (checkLocationEntityExistence(locationModel) != null);
    }

    @Transactional
    //@CachePut()
    public Hotel updateHotel(Hotel updateHotel) {
        // validation
        int id = updateHotel.getId();
        hotelRepositoryJPA.findById(id).orElseThrow(() -> new NoSuchHotelFoundException(id));
        validationService.validate(updateHotel);
        // conversion
        updateHotel = conversionService.convert(updateHotel);
        HotelEntity entity = HotelMapper.toEntity(updateHotel);
        entity.setLocationEntity(getLocationEntity(updateHotel.getLocation()));
        // persistence
        return HotelMapper.toModel(hotelRepositoryJPA.save(entity));
    }

    @Transactional
    //@CachePut()
    public Hotel bookHotel(int id) throws AvailabilityIsZeroException {
        HotelEntity entity = hotelRepositoryJPA.findById(id).orElseThrow(() -> new NoSuchHotelFoundException(id));
        if (entity.getAvailability() < 1) {
            throw new AvailabilityIsZeroException(id);
        }
        entity.setAvailability(entity.getAvailability() - 1);
        return HotelMapper.toModel(hotelRepositoryJPA.save(entity));
    }

    @Transactional
    //@CacheEvict(allEntries = true)
    public void deleteHotel(int id) {
        hotelRepositoryJPA.findById(id).orElseThrow(() -> new NoSuchHotelFoundException(id));
        hotelRepositoryJPA.deleteById(id);
    }

    public List<Hotel> getHotelsByRating(int rating) {
        return HotelMapper.toModels(hotelRepositoryJPA.findByRating(rating));
    }

    public List<Hotel> getHotelsByLocation(String location) {
        return HotelMapper.toModels(hotelRepositoryJPA.findByLocation(location));
    }

    public List<Hotel> getHotelsByBadge(String reputationBadge) {
        return HotelMapper.toModels(hotelRepositoryJPA.findByReputationBadge(reputationBadge));
    }
}
