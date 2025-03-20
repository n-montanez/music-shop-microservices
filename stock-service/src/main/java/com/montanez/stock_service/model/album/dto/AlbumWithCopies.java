package com.montanez.stock_service.model.album.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.montanez.stock_service.model.physical_copy.dto.PhysicalCopySimple;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumWithCopies {
    private UUID id;

    private String title;

    private LocalDate releaseDate;

    private LocalTime duration;

    private int tracks;

    private List<PhysicalCopySimple> physicalCopies;
}
