package com.montanez.order_service.model.payment;

import java.util.Date;
import java.util.UUID;
import com.montanez.order_service.model.order.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "date")
    private Date date;

    @Column(name = "amount")
    @Min(value = 0, message = "Payment transaction cannot be performed with negative value")
    private int amount;

    @Column(name = "method")
    @Enumerated(EnumType.STRING)
    private PayMethod method;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PayStatus status;

    @Size(min = 64, max = 64, message = "Payment reference should be 64 length")
    @Column(name = "reference", unique = true)
    private String reference;
}
