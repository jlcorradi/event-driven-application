package dev.jlcorradi.eventdrivenapplication.core.repository;

import dev.jlcorradi.eventdrivenapplication.core.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SectorRepository extends JpaRepository<Sector, UUID> {
}
