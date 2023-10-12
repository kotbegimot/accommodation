package com.example.accommodation.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hotels")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    @EqualsAndHashCode.Exclude
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "rating")
    int rating;

    @Column(name = "category")
    String category;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @Cascade(CascadeType.SAVE_UPDATE)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @JoinColumn(name = "location_id", nullable = false)
    @EqualsAndHashCode.Exclude
    LocationEntity locationEntity;

    @Column(name = "image")
    String imageUrl;

    @Column(name = "reputation")
    int reputation;

    @Column(name = "reputation_badge")
    String reputationBadge;

    @Column(name = "price")
    int price;

    @Column(name = "availability")
    int availability;
}