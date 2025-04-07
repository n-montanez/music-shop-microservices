package com.montanez.stock_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.montanez.stock_service.model.album.dto.AlbumDetails;
import com.montanez.stock_service.model.album.dto.AlbumSimple;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.service.AlbumService;

@WebMvcTest(controllers = AlbumController.class)
@ExtendWith(MockitoExtension.class)
public class AlbumControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AlbumService albumService;

    private UUID artistId;
    private UUID albumId;
    private AlbumSimple albumSimple;
    private AlbumDetails albumDetails;

    @BeforeEach
    public void setUp() {
        artistId = UUID.randomUUID();
        albumId = UUID.randomUUID();
        albumSimple = AlbumSimple
                .builder()
                .id(albumId)
                .title("Test Album")
                .releaseDate(LocalDate.of(2023, 10, 24))
                .duration(LocalTime.of(0, 32))
                .tracks(12)
                .build();
        albumDetails = AlbumDetails
                .builder()
                .id(albumId)
                .title("Test Album")
                .releaseDate(LocalDate.of(2023, 10, 24))
                .duration(LocalTime.of(0, 32))
                .tracks(12)
                .artist(ArtistSimple
                        .builder()
                        .id(artistId)
                        .name("Test Artist")
                        .build())
                .build();
    }

    @Test
    public void testGetAllAlbums() throws Exception {
        given(albumService.getAll()).willReturn(List.of(albumSimple));

        mockMvc
                .perform(get("/album")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(albumId.toString()))
                .andExpect(jsonPath("$[0].title").value("Test Album"))
                .andExpect(jsonPath("$[0].artist").doesNotExist());

    }

    @Test
    public void testGetAlbumById() throws Exception {
        given(albumService.getById(albumId)).willReturn(albumDetails);

        mockMvc
                .perform(get("/album/{id}", albumId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(albumId.toString()))
                .andExpect(jsonPath("$.title").value("Test Album"))
                .andExpect(jsonPath("$.artist").exists())
                .andExpect(jsonPath("$.artist.id").value(artistId.toString()))
                .andExpect(jsonPath("$.artist.name").value("Test Artist"));
    }

    @Test
    public void testGetAlbumById_NotFound() throws Exception {
        given(albumService.getById(any(UUID.class))).willReturn(null);

        mockMvc
                .perform(get("/album/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSearchAlbums_ByYear() throws Exception {
        given(albumService.searchAlbums(any(), any(), any())).willReturn(List.of(albumSimple));

        mockMvc
                .perform(get("/album/search")
                        .param("releaseYear", "2023")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Test Album"));
    }

}
