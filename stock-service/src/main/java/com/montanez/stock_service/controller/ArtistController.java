package com.montanez.stock_service.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montanez.stock_service.model.artist.Artist;
import com.montanez.stock_service.service.ArtistService;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
public class ArtistController {
    private final ArtistService artistService;

    @GetMapping(value = { "/", "" })
    public ResponseEntity<List<Artist>> getAllArtists() {
        return new ResponseEntity<>(artistService.getAllArtists(), HttpStatus.OK);
    }

}
