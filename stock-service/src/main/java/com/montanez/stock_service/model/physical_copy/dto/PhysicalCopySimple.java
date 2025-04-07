package com.montanez.stock_service.model.physical_copy.dto;

import java.util.UUID;
import com.montanez.stock_service.model.physical_copy.MediaType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalCopySimple {
    private UUID id;

    private int stock;

    private double price;

    private MediaType mediaType;
}
