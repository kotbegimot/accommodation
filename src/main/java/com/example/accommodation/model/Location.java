package com.example.accommodation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
@Builder
public class Location {
    @JsonProperty("id")
    int id;
    @JsonProperty("city")
    String city;
    @JsonProperty("state")
    String state;
    @JsonProperty("country")
    String country;
    @JsonProperty("zip_code")
    int zipCode;
    @JsonProperty("address")
    String address;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.country, other.country)) {
            return false;
        }
        if (this.zipCode != other.zipCode) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        return true;
    }
}
