package com.example.accommodation.util;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.model.Hotel;

import java.util.List;

/**
 * Class used to convert objects from Hotel Entity to Hotel Model and back.
 */
public class HotelMapper {
    public static Hotel toModel(HotelEntity entity) {
        return Hotel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rating(entity.getRating())
                .category(entity.getCategory())
                .location(LocationMapper.toModel(entity.getLocationEntity()))
                .imageUrl(entity.getImageUrl())
                .reputation(entity.getReputation())
                .reputationBadge(entity.getReputationBadge())
                .price(entity.getPrice())
                .availability(entity.getAvailability())
                .build();
    }

    public static List<Hotel> toModels(List<HotelEntity> hotels) {
        return hotels.stream()
                .map(HotelMapper::toModel)
                .toList();
    }

    public static HotelEntity toEntity(Hotel hotel) {
        return HotelEntity.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .rating(hotel.getRating())
                .category(hotel.getCategory())
                .locationEntity(LocationMapper.toEntity(hotel.getLocation()))
                .imageUrl(hotel.getImageUrl())
                .reputation(hotel.getReputation())
                .reputationBadge(hotel.getReputationBadge())
                .price(hotel.getPrice())
                .availability(hotel.getAvailability())
                .build();
    }
}
