package dev.jlcorradi.eventdrivenapplication.core.repository;

import dev.jlcorradi.eventdrivenapplication.core.model.Security;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityRepository extends JpaRepository<Security, String> {
}
