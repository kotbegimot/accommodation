package com.example.accommodation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

class AccommodationApplicationTest {

    @Test
    void runTest() {
        String[] args = new String[]{""};
        assertAll(() -> AccommodationApplication.main(args));
    }
}