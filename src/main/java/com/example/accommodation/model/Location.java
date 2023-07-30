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
        if (obj == this) return true;
        if (!(obj instanceof Location)) {
            return false;
        }
        final Location other = (Location) obj;

        return this.zipCode == other.zipCode &&
                Objects.equals(this.city, other.city) &&
                Objects.equals(this.state, other.state) &&
                Objects.equals(this.country, other.country) &&
                Objects.equals(this.address, other.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, city, state, country, zipCode, address);
    }
}
