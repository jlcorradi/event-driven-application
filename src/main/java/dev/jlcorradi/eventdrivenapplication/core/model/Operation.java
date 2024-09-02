package dev.jlcorradi.eventdrivenapplication.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "operation")
public class Operation implements BaseEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;

    private Date operationDate;

    @ManyToOne
    @JoinColumn(name = "symbol")
    private Security security;

    private BigDecimal price;
    private int quantity;
    private Date executionDate;
}
