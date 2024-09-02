package dev.jlcorradi.eventdrivenapplication.core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "sector")
@ToString(exclude = "securities")
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(36)")
    private UUID id;
    private String description;

    @OneToMany(mappedBy = "sector", fetch = FetchType.EAGER)
    private Set<Security> securities;
}
