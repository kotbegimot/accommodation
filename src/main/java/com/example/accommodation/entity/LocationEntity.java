package com.example.accommodation.entity;

import com.example.accommodation.model.Hotel;
import jakarta.persistence.*;
import lombok.*;
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
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private int id;

    @Column(name="city")
    private String city;

    @Column(name="state")
    private String state;

    @Column(name="country")
    private String country;

    // some countries zip codes contain symbols: e.g. United Kingdom: LU2 9RN (Luton)
    @Column(name="zip_code")
    private int zipCode;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "locationEntity",
            orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<HotelEntity> hotels = new ArrayList<>();
    @Override
    public String toString() {
        return "LocationEntity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zip_code='" + zipCode + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}