package com.montanez.stock_service.controller;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.montanez.stock_service.model.artist.dto.ArtistDetail;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.service.ArtistService;

@WebMvcTest(controllers = ArtistController.class)
@ExtendWith(MockitoExtension.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ArtistService artistService;

    private UUID artistId;
    private ArtistSimple artistSimple;
    private ArtistDetail artistDetail;

    @BeforeEach
    public void setUp() {
        artistId = UUID.randomUUID();
        artistSimple = new ArtistSimple(artistId, "Test Artist");
        artistDetail = new ArtistDetail(artistId, "Test Artist", Collections.emptyList());
    }

    @Test
    public void testGetAllArtists() throws Exception {
        given(artistService.getAll()).willReturn(List.of(artistSimple));

        mockMvc
                .perform(get("/artist")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(artistId.toString()))
                .andExpect(jsonPath("$[0].name").value("Test Artist"));
    }

    @Test
    public void testGetArtistById() throws Exception {
        given(artistService.getById(artistId)).willReturn(artistDetail);

        mockMvc
                .perform(get("/artist/{id}", artistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(artistId.toString()))
                .andExpect(jsonPath("$.name").value("Test Artist"));
    }

    @Test
    public void testGetArtistById_NotFound() throws Exception {
        given(artistService.getById(artistId)).willReturn(null);

        mockMvc
                .perform(get("/artist/{id}", artistId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
