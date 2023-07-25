package com.example.accommodation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(toBuilder=true)
public class Hotel {
    @JsonProperty("id")
    int id;
    @JsonProperty("name")
    String name;
    @JsonProperty("rating")
    int rating;
    @JsonProperty("category")
    String category;
    @JsonProperty("location")
    //int location;
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
