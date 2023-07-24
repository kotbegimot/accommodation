package com.example.accommodation.controller;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.service.HotelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.accommodation.util.Globals.BASE_URL;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HotelsController.class)
class HotelsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelsService service;

    private List<Hotel> hotels;

    @BeforeEach
    public void setUp() {
        Location location = new Location(1, "city", "state",
                "country", 480011, "address");
        hotels = new ArrayList<>();
        hotels.add(new Hotel(1, "name1", 100, "category1", location,
                "url1", 100, "red", 100, 10));
        hotels.add(new Hotel(2, "name2", 200, "category2", location,
                "url2", 200, "yellow", 200, 20));
        hotels.add(new Hotel(3, "name3", 300, "category3", location,
                "url3", 300, "green", 300, 30));
    }

    @AfterEach
    public void tearDown() {
        hotels.clear();
    }

    @Test
    @DisplayName("Should fetch all hotels")
    @WithMockUser
    public void getAllHotels() throws Exception {
        when(service.getAllHotels()).thenReturn(hotels);
        mvc.perform(get(BASE_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.hotels.size()", is(hotels.size())))
                .andExpect(jsonPath("$.hotels[0].name", is("name1")))
                .andExpect(jsonPath("$.hotels[1].name", is("name2")))
                .andExpect(jsonPath("$.hotels[2].name", is("name3")));
    }

    @Test
    @DisplayName("Should fetch the requested hotel by id")
    @WithMockUser
    public void getHotelById() throws Exception {
        int id = 2;
        when(service.getHotel(id)).thenReturn(hotels.get(id-1));
        mvc.perform(get(BASE_URL + "/%d".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("name2")))
                .andExpect(jsonPath("$.rating", is(200)))
                .andExpect(jsonPath("$.reputationBadge", is("yellow")));
    }
}