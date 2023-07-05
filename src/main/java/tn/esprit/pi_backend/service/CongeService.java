package tn.esprit.pi_backend.service;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Conge;

import tn.esprit.pi_backend.entities.StatusOfDemand;
import tn.esprit.pi_backend.repositories.CongeRepo;
import tn.esprit.pi_backend.repositories.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
	
	public Conge getLatestUserConge(Long userId) {
		return congeRepository.findTopByUserIdOrderByIdDesc(userId);
	}

	public Conge createConge(Conge conge) {
		Optional<List<Conge>> optionalConges = Optional.of(
				congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserId(conge.getDateDebut(),
						conge.getDateFin(), conge.getUser().getId()));
		if (optionalConges.get().isEmpty()) {
			return congeRepository.save(conge);
		}
		return null;
	}

	public Conge updateConge(Long id, Conge congeDetails) {
		Optional<List<Conge>> optionalConges = Optional
				.of(congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserId(
						congeDetails.getDateDebut(), congeDetails.getDateFin(), congeDetails.getUser().getId()));
		if (optionalConges.get().isEmpty()) {
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
		return null;
	}

	public void deleteConge(Long id) {
		congeRepository.deleteById(id);
	}

	public Conge accepterConge(Long congeId) {
		Conge conge = getCongeById(congeId);
		conge.setStatus(StatusOfDemand.ACCEPTED);
		conge = congeRepository.save(conge);

		// Votre code Twilio pour envoyer un SMS
		String accountSid = "ACd7086ad292bed93eaae50de57b1684fb";
		String authToken = "d4702c7e2d3ed6556bc2af3c5b3b49e5";

		Twilio.init(accountSid, authToken);
		Message message = Message.creator(
						new PhoneNumber("+21658046046"),  // Remplacez par le numéro de téléphone du destinataire
						new PhoneNumber("+15416354179"),  // Remplacez par votre numéro de téléphone Twilio
						"Votre demande de congé a été acceptée. Profitez de votre congé !")
				.create();

		System.out.println(message.getSid());

		return conge;
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

