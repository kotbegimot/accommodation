package com.example.accommodation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hotels")
public class HotelEntity {
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

    @ManyToOne()
    @Cascade(CascadeType.PERSIST)
    @JoinColumn(name="location_id")
    private LocationEntity locationEntity;

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
                ", location_id=" + locationEntity.toString() +
                ", imageUrl='" + imageUrl + '\'' +
                ", reputation=" + reputation +
                ", reputationBadge='" + reputationBadge + '\'' +
                ", price=" + price +
                ", availability=" + availability +
                '}';
    }
}