package com.example.accommodation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    @JsonProperty("city")
    String city;
    @JsonProperty("state")
    String state;
    @JsonProperty("country")
    String country;
    @JsonProperty("zipCode")
    int zipCode;
    @JsonProperty("address")
    int address;
}
