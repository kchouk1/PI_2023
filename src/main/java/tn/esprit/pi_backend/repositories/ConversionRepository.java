package tn.esprit.pi_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pi_backend.entities.Conversion;
import tn.esprit.pi_backend.entities.StatusOfDemand;

public interface ConversionRepository extends JpaRepository<Conversion, Long> {
	Optional<Conversion> findByStatusAndUserId(StatusOfDemand status, Long userId);
}
