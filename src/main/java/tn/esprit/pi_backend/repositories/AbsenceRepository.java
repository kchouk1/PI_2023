package tn.esprit.pi_backend.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pi_backend.entities.Absence;

public interface AbsenceRepository extends JpaRepository <Absence,Long> {
	List<Absence> findByUserId(Long id);
    List<Absence> findByDateAbsenceGreaterThanEqualAndDateAbsenceLessThanEqualAndUserId(LocalDate startDate, LocalDate endDate, Long id);
    Optional<Absence> findByDateAbsenceAndUserId(LocalDate dateAbsence, Long userId);
}
