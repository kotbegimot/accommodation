package com.example.accommodation.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Global variables used in the app
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Globals
{
    public static final  String BASE_URL = "/api/v1/hotels";
    public static String toJsonString(final Object obj) throws RuntimeException {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
