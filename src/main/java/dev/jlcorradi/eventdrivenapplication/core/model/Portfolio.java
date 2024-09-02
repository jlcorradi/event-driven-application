package dev.jlcorradi.eventdrivenapplication.core.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @GeneratedValue
    private UUID id;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "symbol")
    private Security security;

    private int quantity;
}
