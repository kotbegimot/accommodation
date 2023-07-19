package com.example.accommodation.mapper;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.model.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class used to convert objects from Hotel Entity to Hotel Model and back.
 */
@Component
@RequiredArgsConstructor
public class HotelMapper {
    private final LocationMapper locationMapper;
    public Hotel toModel(HotelEntity entity) {
        return Hotel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rating(entity.getRating())
                .category(entity.getCategory())
                .location(locationMapper.toModel(entity.getLocationEntity()))
                .imageUrl(entity.getImageUrl())
                .reputation(entity.getReputation())
                .reputationBadge(entity.getReputationBadge())
                .price(entity.getPrice())
                .availability(entity.getAvailability())
                .build();
    }

    public List<Hotel> toModels(List<HotelEntity> hotels) {
        return hotels.stream()
                .map(this::toModel)
                .toList();
    }

    public HotelEntity toEntity(Hotel hotel) {
        return HotelEntity.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .rating(hotel.getRating())
                .category(hotel.getCategory())
                .locationEntity(locationMapper.toEntity(hotel.getLocation()))
                .imageUrl(hotel.getImageUrl())
                .reputation(hotel.getReputation())
                .reputationBadge(hotel.getReputationBadge())
                .price(hotel.getPrice())
                .availability(hotel.getAvailability())
                .build();
    }
}
