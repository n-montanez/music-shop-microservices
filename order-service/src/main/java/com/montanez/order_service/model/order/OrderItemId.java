package com.montanez.order_service.model.order;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemId implements Serializable {
    private UUID orderId;
    private UUID physicalCopyId;
}
