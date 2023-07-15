package com.example.accommodation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Hotel {
    @JsonProperty("id")
    String hotelId;
    @JsonProperty("name")
    String name;
    @JsonProperty("rating")
    int rating;
    @JsonProperty("category")
    String category;
    @JsonProperty("location")
    Location location;
    @JsonProperty("image")
    String imagePath;
    @JsonProperty("reputation")
    int reputation;
    @JsonProperty("reputationBadge")
    String reputationBadge;
    @JsonProperty("price")
    int price;
    @JsonProperty("availability")
    int availability;
}
