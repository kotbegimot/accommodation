package com.example.accommodation.util;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Global variables used in the app
 */
public class Globals
{
    public final static String BASE_URL = "/api/v1/hotels";
    public static String toJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
