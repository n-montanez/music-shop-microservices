package com.montanez.stock_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.montanez.stock_service.model.album.Album;
import com.montanez.stock_service.repository.AlbumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    public Album getById(UUID id) {
        Optional<Album> foundAlbum = albumRepository.findById(id);
        if (foundAlbum.isPresent())
            return foundAlbum.get();
        // TODO: Handle 404
        return null;
    }

    public List<Album> getAll() {
        return albumRepository.findAll();
    }
}
