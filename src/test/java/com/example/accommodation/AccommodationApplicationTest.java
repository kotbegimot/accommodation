package com.example.accommodation;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertAll;

@ActiveProfiles(value = "dev")
class AccommodationApplicationTest {

    @Test
    void runTest() {
        String[] args = new String[]{""};
        assertAll(() -> AccommodationApplication.main(args));
    }
}