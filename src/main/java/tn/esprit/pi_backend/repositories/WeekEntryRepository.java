package tn.esprit.pi_backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.pi_backend.entities.WeekEntry;

public interface WeekEntryRepository extends JpaRepository<WeekEntry, Long> {
	List<WeekEntry> findByUserId(Long id);
}
