package com.montanez.stock_service.repository;

import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.montanez.stock_service.model.album.Album;
import com.montanez.stock_service.model.artist.Artist;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    private static UUID albumId;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void testCreateAndSaveAlbum() {
        Artist artist = new Artist("Test Artist");
        Artist savedArtist = artistRepository.save(artist);

        Album album = Album.builder()
                .title("Test Album")
                .releaseDate(LocalDate.of(2023, 3, 31))
                .duration(LocalTime.of(0, 42, 30))
                .tracks(10)
                .artist(savedArtist)
                .build();

        Album savedAlbum = albumRepository.save(album);
        albumId = savedAlbum.getId();

        assertNotNull(savedAlbum);
        assertNotNull(savedAlbum.getId());
        assertEquals("Test Album", savedAlbum.getTitle());
        System.out.println("Album: " + album.getId() + " - " + album.getTitle());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testReadAlbum() {
        Optional<Album> foundAlbum = albumRepository.findById(albumId);

        assertTrue(foundAlbum.isPresent());
        assertEquals("Test Album", foundAlbum.get().getTitle());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testSearchAlbums() {
        List<Album> results = albumRepository.searchAlbums("Test Artist", 2023, "Test Album");

        assertFalse(results.isEmpty());
        assertEquals("Test Album", results.get(0).getTitle());
        assertEquals("Test Artist", results.get(0).getArtist().getName());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testSearchAlbums_Empty() {
        List<Album> results = albumRepository.searchAlbums("Non Artist", null, null);

        assertTrue(results.isEmpty());
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void testUpdateAlbum() {
        Optional<Album> foundAlbum = albumRepository.findById(albumId);
        assertTrue(foundAlbum.isPresent());

        Album toUpdate = foundAlbum.get();
        toUpdate.setTitle("Updated Album");
        albumRepository.save(toUpdate);

        Optional<Album> updatedAlbum = albumRepository.findById(albumId);
        assertTrue(updatedAlbum.isPresent());
        assertEquals("Updated Album", updatedAlbum.get().getTitle());
    }

    @Test
    @Order(6)
    @Rollback(value = false)
    public void testDeleteAlbum() {
        albumRepository.deleteById(albumId);
        Optional<Album> foundAlbum = albumRepository.findById(albumId);
        assertFalse(foundAlbum.isPresent());
    }
}
