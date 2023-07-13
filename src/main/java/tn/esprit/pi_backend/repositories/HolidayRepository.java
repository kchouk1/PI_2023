package tn.esprit.pi_backend.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pi_backend.entities.Holiday;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {
	List<Holiday> findAllByEndDateLessThanEqualAndStartDateGreaterThanEqual(LocalDate startDate, LocalDate endDate);
}
