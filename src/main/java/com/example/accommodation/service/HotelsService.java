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

@Service
@RequiredArgsConstructor
public class HotelsService {
    private final HotelRepository repository;
    private final HotelMapper mapper;
    private final HotelValidationService validationService;
    private final HotelConversionService conversionService;

    public List<Hotel> getAllHotels() {
        return mapper.toModels(repository.getAll());
    }

    public Hotel getHotel(int id) {
        HotelEntity entity = checkEntityById(id);
        validationService.validate(mapper.toModel(entity));
        return mapper.toModel(entity);
    }

    public void createHotel(Hotel newHotel) {
        repository.create(mapper.toEntity(newHotel));
    }

    public Hotel updateHotel(Hotel updateHotel) {
        checkEntityById(updateHotel.getId());
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

    private HotelEntity checkEntityById(int id) throws NoSuchHotelFoundException {
        HotelEntity entity = repository.getById(id);
        if (entity == null) {
            throw new NoSuchHotelFoundException(id);
        }
        return entity;
    }
}
