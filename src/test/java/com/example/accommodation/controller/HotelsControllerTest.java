package com.example.accommodation.controller;

import com.example.accommodation.model.Hotel;
import com.example.accommodation.model.Location;
import com.example.accommodation.service.HotelsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.example.accommodation.util.Globals.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HotelsController.class)
@AutoConfigureMockMvc(addFilters = false)
class HotelsControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private HotelsService service;

    private List<Hotel> hotels;

    private Hotel hotelRequest;
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
        hotelRequest = new Hotel(0, "name4", 400, "category4", location,
                "url4", 400, "green", 400, 40);
    }

    @AfterEach
    public void tearDown() {
        hotels.clear();
    }

    @Test
    @DisplayName("Should fetch all hotels")
    @WithMockUser
    public void getAllHotelsTest() throws Exception {
        when(service.getAllHotels()).thenReturn(hotels);
        mvc.perform(get(BASE_URL)
                        .accept(MediaType.APPLICATION_JSON))
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
    public void getHotelByIdTest() throws Exception {
        int id = 2;
        when(service.getHotel(id)).thenReturn(hotels.get(id-1));
        mvc.perform(get(BASE_URL + "/%d".formatted(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("name2")))
                .andExpect(jsonPath("$.rating", is(200)))
                .andExpect(jsonPath("$.reputationBadge", is("yellow")));
    }

    @Test
    @DisplayName("Should call hotel creation")
    @WithMockUser
    public void createHotelTest() throws Exception {
        doNothing().when(service).createHotel(hotelRequest);
        mvc.perform(post(BASE_URL + "/create")
                        .content(toJsonString(hotelRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(service, times(1)).createHotel(hotelRequest);
    }


    @Test
    @DisplayName("Should call hotel updating")
    @WithMockUser
    public void updateHotelTest() throws Exception {
        int id = 4;
        hotelRequest.setId(id);
        when(service.updateHotel(hotelRequest)).thenReturn(hotelRequest);
        mvc.perform(put(BASE_URL + "/update/%d".formatted(id))
                    .content(toJsonString(hotelRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(hotelRequest.getName())))
                .andExpect(jsonPath("$.rating", is(hotelRequest.getRating())))
                .andExpect(jsonPath("$.reputationBadge", is(hotelRequest.getReputationBadge())));
        verify(service, times(1)).updateHotel(hotelRequest);
    }

    @Test
    @DisplayName("Should call hotel booking")
    @WithMockUser
    public void bookHotelTest() throws Exception {
        int id = 4;
        hotelRequest.setId(id);
        Hotel bookedHotel = hotelRequest.toBuilder().availability(hotelRequest.getAvailability()-1).build();
        when(service.bookHotel(id)).thenReturn(bookedHotel);
        mvc.perform(put(BASE_URL + "/book/%d".formatted(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(hotelRequest.getName())))
                .andExpect(jsonPath("$.rating", is(hotelRequest.getRating())))
                .andExpect(jsonPath("$.reputationBadge", is(hotelRequest.getReputationBadge())))
                .andExpect(jsonPath("$.availability", is(hotelRequest.getAvailability()-1)));
        verify(service, times(1)).bookHotel(id);
    }

    @Test
    @DisplayName("Should call hotel deleting")
    @WithMockUser
    public void deleteHotelTest() throws Exception {
        int id = 1;
        doNothing().when(service).deleteHotel(id);
        mvc.perform(delete(BASE_URL + "/delete/{id}", id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
        verify(service, times(1)).deleteHotel(id);
    }
}