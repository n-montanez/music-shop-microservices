package com.montanez.stock_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.montanez.stock_service.model.album.dto.AlbumDetails;
import com.montanez.stock_service.model.album.dto.AlbumSimple;
import com.montanez.stock_service.service.AlbumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/album")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping(value = { "/", "" })
    public ResponseEntity<List<AlbumSimple>> getAllAlbums() {
        return new ResponseEntity<>(albumService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumDetails> getAlbum(@PathVariable UUID id) {
        return new ResponseEntity<>(albumService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AlbumSimple>> searchAlbums(
            @RequestParam(required = false) String artistName,
            @RequestParam(required = false) Integer releaseYear,
            @RequestParam(required = false) String title) {
        return new ResponseEntity<>(
                albumService.searchAlbums(artistName, releaseYear, title),
                HttpStatus.OK);
    }

}
