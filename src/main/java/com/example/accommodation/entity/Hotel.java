package com.example.accommodation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "hotels")
@AllArgsConstructor
@Data
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hotel_id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="rating")
    private int rating;

    @Column(name="category")
    private String category;

    @Column(name="location_id")
    private int location_id;

    @Column(name="image")
    private String imageUrl;

    @Column(name="reputation")
    private int reputation;

    @Column(name="reputation_badge")
    private String reputationBadge;

    @Column(name="price")
    private int price;

    @Column(name="availability")
    private int availability;

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", category='" + category + '\'' +
                ", location_id=" + location_id +
                ", imageUrl='" + imageUrl + '\'' +
                ", reputation=" + reputation +
                ", reputationBadge='" + reputationBadge + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}