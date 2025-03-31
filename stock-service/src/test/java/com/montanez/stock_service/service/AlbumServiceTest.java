package com.montanez.stock_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.BDDMockito.given;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.montanez.stock_service.model.album.Album;
import com.montanez.stock_service.model.album.dto.AlbumDetails;
import com.montanez.stock_service.model.album.dto.AlbumSimple;
import com.montanez.stock_service.model.album.dto.AlbumWithCopies;
import com.montanez.stock_service.model.artist.Artist;
import com.montanez.stock_service.model.physical_copy.MediaType;
import com.montanez.stock_service.model.physical_copy.PhysicalCopy;
import com.montanez.stock_service.repository.AlbumRepository;

@ExtendWith(MockitoExtension.class)
public class AlbumServiceTest {

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private AlbumService albumService;

    private UUID albumId;
    private Album album;
    private Artist artist;

    @BeforeEach
    public void setUp() {
        albumId = UUID.randomUUID();

        artist = Artist.builder()
                .id(UUID.randomUUID())
                .name("Test Artist")
                .build();

        album = Album.builder()
                .id(albumId)
                .title("Test Album")
                .releaseDate(LocalDate.of(2022, 8, 12))
                .duration(LocalTime.of(0, 30, 0))
                .tracks(10)
                .artist(artist)
                .build();
    }

    @Test
    public void testGetById_AlbumFound() {
        given(albumRepository.findById(albumId)).willReturn(Optional.of(album));

        AlbumDetails result = albumService.getById(albumId);

        assertNotNull(result);
        assertEquals(albumId, result.getId());
        assertEquals("Test Album", result.getTitle());
        assertEquals("Test Artist", result.getArtist().getName());
    }

    @Test
    public void testGetById_AlbumNotFound() {
        given(albumRepository.findById(albumId)).willReturn(Optional.empty());

        AlbumDetails result = albumService.getById(albumId);

        assertNull(result);
    }

    @Test
    public void testGetAll() {
        given(albumRepository.findAll()).willReturn(List.of(album));

        List<AlbumSimple> result = albumService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(albumId, result.get(0).getId());
        assertEquals("Test Album", result.get(0).getTitle());
    }

    @Test
    public void testSearchAlbums() {
        given(albumRepository
                .searchAlbums("Test Artist", 2022, "Test Album"))
                .willReturn(List.of(album));

        List<AlbumSimple> result = albumService.searchAlbums("Test Artist", 2022, "Test Album");

        assertEquals(1, result.size());
        assertEquals(albumId, result.get(0).getId());
        assertEquals("Test Album", result.get(0).getTitle());
    }

    @Test
    public void testGetCopiesOfAlbum_AlbumFound() {
        PhysicalCopy copy = PhysicalCopy
                .builder()
                .id(UUID.randomUUID())
                .album(album)
                .price(19.99)
                .stock(10)
                .mediaType(MediaType.CD)
                .build();
        album.setPhysicalCopies(List.of(copy));

        given(albumRepository.findById(albumId)).willReturn(Optional.of(album));

        AlbumWithCopies result = albumService.getCopiesOfAlbum(albumId);

        assertNotNull(result);
        assertEquals(1, result.getPhysicalCopies().size());
        assertEquals(10, result.getPhysicalCopies().get(0).getStock());
        assertEquals(MediaType.CD, result.getPhysicalCopies().get(0).getMediaType());
    }

    @Test
    public void testGetCopiesOfAlbum_AlbumNotFound() {
        given(albumRepository.findById(albumId)).willReturn(Optional.empty());

        AlbumWithCopies result = albumService.getCopiesOfAlbum(albumId);

        assertNull(result);
    }

}
