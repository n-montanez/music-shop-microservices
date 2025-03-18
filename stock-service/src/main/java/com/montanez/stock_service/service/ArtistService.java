package com.montanez.stock_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import com.montanez.stock_service.model.Artist;
import com.montanez.stock_service.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public Artist getSingleArtist(UUID id) {
        Optional<Artist> foundArtist = artistRepository.findById(id);
        if (foundArtist.isPresent())
            return foundArtist.get();
        // TODO: Handle 404
        return null;
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }
}
