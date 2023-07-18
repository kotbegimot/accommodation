package com.example.accommodation.mapper;

import com.example.accommodation.entity.HotelEntity;
import com.example.accommodation.model.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelMapper {
    public Hotel toModel(HotelEntity entity) {
        return Hotel.builder()
                .id(entity.getId())
                .name(entity.getName())
                .rating(entity.getRating())
                .category(entity.getCategory())
                .location(entity.getLocation())
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
                .location(hotel.getLocation())
                .imageUrl(hotel.getImageUrl())
                .reputation(hotel.getReputation())
                .reputationBadge(hotel.getReputationBadge())
                .price(hotel.getPrice())
                .availability(hotel.getAvailability())
                .build();
    }
}
