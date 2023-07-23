package com.example.accommodation.service;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.mapper.HotelMapper;
import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.exceptions.AvailabilityIsZeroException;
import com.example.accommodation.model.exceptions.NoSuchHotelFoundException;
import com.example.accommodation.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Contains set of methods that process requests from HotelsController for each business case.
 */
@Service
@RequiredArgsConstructor
public class HotelsService {
    private final HotelRepository repository;
    private final HotelMapper mapper;
    private final HotelValidationService validationService;
    private final HotelConversionService conversionService;
    private HotelEntity checkEntityById(int id) throws NoSuchHotelFoundException {
        HotelEntity entity = repository.getById(id);
        if (entity == null) {
            throw new NoSuchHotelFoundException(id);
        }
        return entity;
    }

    public List<Hotel> getAllHotels() {
        return mapper.toModels(repository.getAll());
    }

    public Hotel getHotel(int id) {
        HotelEntity entity = checkEntityById(id);
        return mapper.toModel(entity);
    }

    public void createHotel(Hotel newHotel) {
        validationService.validate(newHotel);
        newHotel = conversionService.convert(newHotel);
        repository.create(mapper.toEntity(newHotel));
    }

    public Hotel updateHotel(Hotel updateHotel) {
        checkEntityById(updateHotel.getId());
        validationService.validate(updateHotel);
        updateHotel = conversionService.convert(updateHotel);
        return mapper.toModel(repository.updateHotel(mapper.toEntity(updateHotel)));
    }

    public Hotel bookHotel(int id) throws AvailabilityIsZeroException {
        HotelEntity entity = checkEntityById(id);
        if (entity.getAvailability() < 1) {
            throw new AvailabilityIsZeroException(id);
        }
        entity.setAvailability(entity.getAvailability() - 1);
        return mapper.toModel(repository.updateHotel(entity));
    }

    public void deleteHotel(int id) {
        checkEntityById(id);
        repository.deleteHotel(id);
    }
    public List<Hotel> getHotelsByRating(int rating) {
        return mapper.toModels(repository.getHotelsByRating(rating));
    }

    public List<Hotel> getHotelsByLocation(String location) {
        return mapper.toModels(repository.getHotelsByLocation(location));
    }

    public List<Hotel> getHotelsByBadge(String reputationBadge) {
        return mapper.toModels(repository.getHotelsByBadge(reputationBadge));
    }
}
