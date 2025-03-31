package com.montanez.stock_service.service;

import com.montanez.stock_service.model.artist.Artist;
import com.montanez.stock_service.model.artist.dto.ArtistDetail;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.repository.ArtistRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ArtistServiceTest {

    @Mock
    private ArtistRepository artistRepository;

    @InjectMocks
    private ArtistService artistService;

    private UUID artistId;
    private Artist artist;

    @BeforeEach
    public void setUp() {
        artistId = UUID.randomUUID();
        artist = Artist.builder()
                .id(artistId)
                .name("Test Artist")
                .albums(new ArrayList<>())
                .build();
    }

    @Test
    public void testGetById_ArtistFound() {
        given(artistRepository.findById(artistId)).willReturn(Optional.of(artist));

        ArtistDetail result = artistService.getById(artistId);

        assertNotNull(result);
        assertEquals(artistId, result.getId());
        assertEquals("Test Artist", result.getName());
        assertTrue(result.getAlbums().isEmpty());
    }

    @Test
    public void testGetById_ArtistNotFound() {
        given(artistRepository.findById(artistId)).willReturn(Optional.empty());

        ArtistDetail result = artistService.getById(artistId);

        assertNull(result);
    }

    @Test
    public void getAll() {
        List<Artist> mockArtists = List.of(artist);
        given(artistRepository.findAll()).willReturn(mockArtists);

        List<ArtistSimple> result = artistService.getAll();

        assertEquals(1, result.size());
        assertEquals(artistId, result.get(0).getId());
        assertEquals("Test Artist", result.get(0).getName());
    }

}
