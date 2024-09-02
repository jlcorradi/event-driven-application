package dev.jlcorradi.eventdrivenapplication.core.repository;

import dev.jlcorradi.eventdrivenapplication.core.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PortfolioRepository extends JpaRepository<Portfolio, UUID> {
}
