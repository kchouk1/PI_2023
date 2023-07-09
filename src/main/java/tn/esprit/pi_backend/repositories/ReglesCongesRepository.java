package tn.esprit.pi_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.ReglesConges;

public interface ReglesCongesRepository  extends JpaRepository <ReglesConges, Long> {
    ReglesConges findByType(String type);
}

