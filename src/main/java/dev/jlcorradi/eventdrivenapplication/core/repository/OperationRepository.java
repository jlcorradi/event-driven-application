package dev.jlcorradi.eventdrivenapplication.core.repository;

import dev.jlcorradi.eventdrivenapplication.core.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperationRepository extends JpaRepository<Operation, UUID> {
}
