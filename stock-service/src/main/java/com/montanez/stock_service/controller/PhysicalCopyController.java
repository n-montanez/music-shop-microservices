package com.montanez.stock_service.controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.montanez.stock_service.model.album.dto.AlbumWithCopies;
import com.montanez.stock_service.service.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/copies")
@RequiredArgsConstructor
public class PhysicalCopyController {

    private final AlbumService albumService;

    @GetMapping("/{albumId}")
    public ResponseEntity<AlbumWithCopies> getCopiesOfAlbum(@PathVariable UUID albumId) {
        return new ResponseEntity<>(albumService.getCopiesOfAlbum(albumId), HttpStatus.OK);
    }
}
