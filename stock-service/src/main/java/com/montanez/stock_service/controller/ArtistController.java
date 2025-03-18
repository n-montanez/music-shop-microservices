package com.montanez.stock_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.montanez.stock_service.model.artist.dto.ArtistDetail;
import com.montanez.stock_service.model.artist.dto.ArtistSimple;
import com.montanez.stock_service.service.ArtistService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping(value = { "/", "" })
    public ResponseEntity<List<ArtistSimple>> getAllArtists() {
        return new ResponseEntity<>(artistService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistDetail> getArtist(@PathVariable UUID id) {
        return new ResponseEntity<>(artistService.getById(id), HttpStatus.OK);
    }

}
