package com.example.accommodation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
public class Hotel {
    @JsonProperty("id")
    @EqualsAndHashCode.Exclude
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("rating")
    int rating;
    @JsonProperty("category")
    String category;
    @JsonProperty("location")
    Location location;
    @JsonProperty("image")
    String imageUrl;
    @JsonProperty("reputation")
    int reputation;
    @JsonProperty("reputationBadge")
    String reputationBadge;
    @JsonProperty("price")
    int price;
    @JsonProperty("availability")
    int availability;
}
