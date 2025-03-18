package com.montanez.stock_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

import com.montanez.stock_service.model.album.Album;
import com.montanez.stock_service.model.album.dto.AlbumSimple;
import com.montanez.stock_service.model.artist.Artist;
import com.montanez.stock_service.model.artist.dto.ArtistDetail;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.repository.ArtistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;

    public ArtistDetail getById(UUID id) {
        Optional<Artist> foundArtist = artistRepository.findById(id);
        if (foundArtist.isPresent()) {
            Artist artist = foundArtist.get();
            List<AlbumSimple> albumSimples = new ArrayList<>();

            for (Album album : artist.getAlbums()) {
                albumSimples.add(AlbumSimple.builder()
                        .id(album.getId())
                        .title(album.getTitle())
                        .releaseDate(album.getReleaseDate())
                        .duration(album.getDuration())
                        .tracks(album.getTracks())
                        .build());
            }

            return ArtistDetail.builder()
                    .id(artist.getId())
                    .name(artist.getName())
                    .albums(albumSimples)
                    .build();
        }
        return null;
    }

    public List<ArtistSimple> getAll() {
        List<Artist> artists = artistRepository.findAll();
        List<ArtistSimple> artistSimples = new ArrayList<>();
        for (Artist artist : artists) {
            artistSimples.add(
                    ArtistSimple.builder()
                            .name(artist.getName())
                            .id(artist.getId())
                            .build());
        }
        return artistSimples;
    }
}
