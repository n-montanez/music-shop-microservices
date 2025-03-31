package com.montanez.stock_service.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.montanez.stock_service.model.artist.Artist;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArtistRepositoryTest {

    @Autowired
    private ArtistRepository artistRepository;

    private static UUID artistId;

    @Test
    @Order(1)
    @Rollback(value = false)
    public void testCreateAndSaveArtist() {
        Artist artist = new Artist("Test Artist");

        Artist savedArtist = artistRepository.save(artist);
        assertNotNull(savedArtist);
        assertNotNull(savedArtist.getId());

        artistId = savedArtist.getId();

        assertEquals("Test Artist", savedArtist.getName());
    }

    @Test
    @Order(2)
    @Rollback(value = false)
    public void testReadArtist() {
        Optional<Artist> foundArtist = artistRepository.findById(artistId);

        assertTrue(foundArtist.isPresent());
        assertEquals(foundArtist.get().getId(), artistId);
        assertEquals("Test Artist", foundArtist.get().getName());
        assertTrue(foundArtist.get().getAlbums().isEmpty());
    }

    @Test
    @Order(3)
    @Rollback(value = false)
    public void testUpdateArtist() {
        Optional<Artist> foundArtist = artistRepository.findById(artistId);

        assertTrue(foundArtist.isPresent());

        Artist toUpdate = foundArtist.get();
        toUpdate.setName("Updated Name");
        artistRepository.save(toUpdate);

        Optional<Artist> updatedArtist = artistRepository.findById(artistId);
        assertTrue(updatedArtist.isPresent());
        assertEquals("Updated Name", updatedArtist.get().getName());
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void testDeleteArtist() {
        artistRepository.deleteById(artistId);
        Optional<Artist> foundArtist = artistRepository.findById(artistId);
        assertFalse(foundArtist.isPresent());
    }

}
