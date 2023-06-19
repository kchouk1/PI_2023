package tn.esprit.pi_backend.service;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Conge;

import tn.esprit.pi_backend.entities.StatusOfDemand;
import tn.esprit.pi_backend.repositories.CongeRepo;
import tn.esprit.pi_backend.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service

@Slf4j
public class CongeService implements ICongeService {

	private static final int PRIORITE_MINIMALE = 3;
	private final CongeRepo congeRepository;

	private final UserRepository userRepository;
	private final TeamService teamService;

	public CongeService(CongeRepo congeRepository, UserRepository userRepository, TeamService teamService) {
		this.congeRepository = congeRepository;
		this.userRepository = userRepository;
		this.teamService = teamService;
	}

	public List<Conge> getAllConges() {
		return congeRepository.findAll();
	}

	public List<Conge> getAllCurrentConges(Long userId) {
		LocalDate initialDate = LocalDate.now();
		return congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserIdAndStatus(
				initialDate.withDayOfMonth(1),
				initialDate.withDayOfMonth(initialDate.getMonth().length(initialDate.isLeapYear())), userId,
				StatusOfDemand.ACCEPTED);
	}

	public Conge getCongeById(Long id) {
		return congeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Conge not found"));
	}

	public Conge createConge(Conge conge) {

		return congeRepository.save(conge);
	}

	public Conge updateConge(Long id, Conge congeDetails) {
		Conge conge = getCongeById(id);
		if (congeDetails.getDuree() != 0) {
			conge.setDuree(congeDetails.getDuree());
		}
		if (congeDetails.getSoldeConge() != 0) {
			conge.setSoldeConge(congeDetails.getSoldeConge());
		}
		if (congeDetails.getStatus() != null) {
			conge.setStatus(congeDetails.getStatus());
		}
		if (congeDetails.getDateDebut() != null) {
			conge.setDateDebut(congeDetails.getDateDebut());
		}
		if (congeDetails.getDateFin() != null) {
			conge.setDateFin(congeDetails.getDateFin());
		}
		conge.setUser(congeDetails.getUser());
		return congeRepository.save(conge);
	}

	public void deleteConge(Long id) {
		congeRepository.deleteById(id);
	}

//    public Conge createConge(Conge conge) {
//        if (!isEffectifSuffisant(conge)) {
//            throw new IllegalArgumentException("L'effectif de l'équipe est insuffisant pour couvrir le travail pendant cette période.");
//        }
//
//        if (hasCongesApprouvesPendantLaPeriode(conge)) {
//            throw new IllegalArgumentException("D'autres congés ont déjà été approuvés pendant cette période.");
//        }
//
//        if (!hasPrioriteElevee(conge)) {
//            throw new IllegalArgumentException("La priorité de l'employé est insuffisante pour faire la demande de congé.");
//        }
//
//        if (!isEquilibreCongesMaintenu(conge)) {
//            throw new IllegalArgumentException("L'équilibre des congés est compromis.");
//        }
//
//        return congeRepository.save(conge);
//    }

//    private boolean isEffectifSuffisant(Conge conge) {
//        Team team = teamService.getTeamById(conge.getUser().getTeam().getId());
//        int effectif = team.getUsers().size();
//        LocalDate dateDebut = conge.getDateDebut();
//        LocalDate dateFin = conge.getDateFin();
//
//        List<Conge> congésPendantLaPériode = congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqual(dateDebut, dateFin);
//        int congésApprouvésPendantLaPériode = congésPendantLaPériode.stream()
//                .filter(Conge::isApprouve)
//                .collect(Collectors.toList())
//                .size();
//
//        int effectifDisponible = effectif - congésApprouvésPendantLaPériode;
//        return effectifDisponible > 0;
//    }

//    private boolean hasCongesApprouvesPendantLaPeriode(Conge conge) {
//        LocalDate dateDebut = conge.getDateDebut();
//        LocalDate dateFin = conge.getDateFin();
//
//        List<Conge> congésPendantLaPériode = congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqual(dateDebut, dateFin);
//        return congésPendantLaPériode.stream()
//                .anyMatch(Conge::isApprouve);
//    }

//    private boolean hasPrioriteElevee(Conge conge) {
//        User user = conge.getUser();
//        int prioriteUtilisateur = user.getPriority(); // Supposons que la priorité de l'utilisateur soit stockée dans un attribut "priority"
//
//        // Vérifier si la priorité de l'utilisateur est élevée
//        if (prioriteUtilisateur >= PRIORITE_MINIMALE) { // PRIORITE_MINIMALE représente la priorité minimale pour demander un congé
//            return true;
//        } else {
//            return false;
//        }
//    }

//    private boolean isEquilibreCongesMaintenu(Conge conge) {
//        Team team = teamService.getTeamById(conge.getUser().getTeam().getId());
//        int effectif = team.getUsers().size();
//        LocalDate dateDebut = conge.getDateDebut();
//        LocalDate dateFin = conge.getDateFin();
//
//        List<Conge> congésPendantLaPériode = congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqual(dateDebut, dateFin);
//        int congésApprouvésPendantLaPériode = congésPendantLaPériode.stream()
//                .filter(Conge::isApprouve)
//                .collect(Collectors.toList())
//                .size();
//
//        int congésRestants = effectif - congésApprouvésPendantLaPériode - 1; // Soustraire 1 pour le congé demandé
//        return congésRestants >= (effectif / 2); // Par exemple, l'équilibre est maintenu si plus de la moitié des membres peuvent encore prendre des congés
//    }
	public Conge accepterConge(Long congeId) {
		Conge conge = getCongeById(congeId);
		conge.setStatus(StatusOfDemand.ACCEPTED);
		return congeRepository.save(conge);
	}

	public Conge refuserConge(Long congeId) {
		Conge conge = getCongeById(congeId);
		conge.setStatus(StatusOfDemand.REJECTED);
		return congeRepository.save(conge);
	}

	@Override
	public Conge ajouterConge(Conge conge) {
		return null;
	}
}