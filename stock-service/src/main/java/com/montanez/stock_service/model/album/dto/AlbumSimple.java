package com.montanez.stock_service.model.album.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumSimple {
    private UUID id;

    private String title;

    private LocalDate releaseDate;

    private LocalTime duration;

    private int tracks;
}
