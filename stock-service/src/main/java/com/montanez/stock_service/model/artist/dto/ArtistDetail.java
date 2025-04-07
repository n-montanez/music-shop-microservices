package com.montanez.stock_service.model.artist.dto;

import java.util.List;
import java.util.UUID;

import com.montanez.stock_service.model.album.dto.AlbumSimple;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDetail {
    private UUID id;

    private String name;

    private List<AlbumSimple> albums;
}
