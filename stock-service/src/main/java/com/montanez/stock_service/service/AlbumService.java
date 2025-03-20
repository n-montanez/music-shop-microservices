package com.montanez.stock_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import com.montanez.stock_service.model.album.Album;
import com.montanez.stock_service.model.album.dto.AlbumDetails;
import com.montanez.stock_service.model.album.dto.AlbumSimple;
import com.montanez.stock_service.model.album.dto.AlbumWithCopies;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.model.physical_copy.dto.PhysicalCopySimple;
import com.montanez.stock_service.repository.AlbumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    public AlbumDetails getById(UUID id) {
        Optional<Album> foundAlbum = albumRepository.findById(id);
        if (foundAlbum.isPresent()) {
            Album album = foundAlbum.get();
            AlbumDetails albumDetails = AlbumDetails.builder()
                    .id(album.getId())
                    .title(album.getTitle())
                    .releaseDate(album.getReleaseDate())
                    .duration(album.getDuration())
                    .tracks(album.getTracks())
                    .artist(
                            ArtistSimple.builder()
                                    .id(album.getArtist().getId())
                                    .name(album.getArtist().getName())
                                    .build())
                    .build();
            return albumDetails;
        }
        return null;
    }

    public List<AlbumSimple> getAll() {
        List<Album> albums = albumRepository.findAll();
        List<AlbumSimple> albumSimples = new ArrayList<>();

        for (Album album : albums) {
            albumSimples.add(AlbumSimple.builder()
                    .id(album.getId())
                    .title(album.getTitle())
                    .releaseDate(album.getReleaseDate())
                    .duration(album.getDuration())
                    .tracks(album.getTracks())
                    .build());
        }
        return albumSimples;
    }

    public List<AlbumSimple> searchAlbums(String artistName, Integer releaseYear, String title) {
        List<Album> albums = albumRepository.searchAlbums(artistName, releaseYear, title);
        return albums.stream().map(album -> AlbumSimple.builder()
                .id(album.getId())
                .title(album.getTitle())
                .releaseDate(album.getReleaseDate())
                .duration(album.getDuration())
                .tracks(album.getTracks())
                .build()).toList();
    }

    public AlbumWithCopies getCopiesOfAlbum(UUID albumId) {
        Optional<Album> foundAlbum = albumRepository.findById(albumId);

        if (foundAlbum.isPresent()) {
            Album album = foundAlbum.get();
            return AlbumWithCopies.builder()
                    .id(albumId)
                    .title(album.getTitle())
                    .releaseDate(album.getReleaseDate())
                    .duration(album.getDuration())
                    .tracks(album.getTracks())
                    .physicalCopies(album.getPhysicalCopies().stream().map(copy -> {
                        return PhysicalCopySimple.builder()
                                .id(copy.getId())
                                .stock(copy.getStock())
                                .price(copy.getPrice())
                                .mediaType(copy.getMediaType())
                                .build();
                    }).toList())
                    .build();
        }
        return null;
    }
}
