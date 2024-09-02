package dev.jlcorradi.eventdrivenapplication.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "security_history")
public class SecurityHistory {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symbol")
    private Security security;
    private Date date;
    private BigDecimal price;
}
