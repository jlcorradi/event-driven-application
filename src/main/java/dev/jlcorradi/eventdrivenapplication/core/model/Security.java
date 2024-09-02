package dev.jlcorradi.eventdrivenapplication.core.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@Entity
@Table(name = "security")
@ToString(exclude = {"sector", "histories"})
@EqualsAndHashCode
public class Security implements BaseEntity {
    @Id
    @EqualsAndHashCode.Include
    private String symbol;

    @EqualsAndHashCode.Include
    private String description;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @OneToMany(mappedBy = "security", fetch = FetchType.LAZY)
    private List<SecurityHistory> histories;
}
