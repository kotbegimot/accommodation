package com.example.accommodation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    @EqualsAndHashCode.Exclude
    int id;

    @Column(name = "city")
    String city;

    @Column(name = "state")
    String state;

    @Column(name = "country")
    String country;

    @Column(name = "zip_code")
    int zipCode;

    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "locationEntity",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @Builder.Default
    List<HotelEntity> hotels = new ArrayList<>();

    public void addHotel(HotelEntity hotel) {
        if (hotels == null) {
            hotels = new ArrayList<>();
        }
        hotel.setLocationEntity(this);
        hotels.add(hotel);
    }
}