package tn.esprit.pi_backend.repositories;

import org.springframework.stereotype.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pi_backend.entities.Conge;
import tn.esprit.pi_backend.entities.StatusOfDemand;
import tn.esprit.pi_backend.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CongeRepo extends JpaRepository <Conge,Long> {

    Optional<Conge> findById(Long id);

    List<Conge> findByDateDebutGreaterThanEqualAndDateFinLessThanEqual(LocalDate dateDebut, LocalDate dateFin);
    List<Conge> findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserId(LocalDate dateDebut, LocalDate dateFin, Long userId);
    List<Conge> findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserIdAndStatus(LocalDate dateDebut, LocalDate dateFin, Long userId, StatusOfDemand status);
    Conge findTopByUserIdOrderByIdDesc(Long userId);
    long count();


    List<Conge> findCongeByUser(Optional<User> user);

    List<Conge> findByUser(User user);

}