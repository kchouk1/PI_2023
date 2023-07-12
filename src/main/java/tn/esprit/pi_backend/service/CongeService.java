package tn.esprit.pi_backend.service;

import java.time.LocalDate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.*;

import tn.esprit.pi_backend.repositories.*;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

@Slf4j
public class CongeService implements ICongeService {

	private static final int PRIORITE_MINIMALE = 3;
	private final CongeRepo congeRepository;

	private final UserRepository userRepository;
	private final TeamService teamService;

	private final WeekEntryRepository weekEntryRepository;
	private ReglesCongesRepository reglesCongesRepository;
	private final TeamRepository teamRepository ;

	private static final double CONGE_PAR_MOIS = 1.5;
	private static final int HEURES_PAR_JOUR = 8;
	private static int DURRE = 0;

	public CongeService(CongeRepo congeRepository, UserRepository userRepository, TeamService teamService , WeekEntryRepository  weekEntryRepository, ReglesCongesRepository reglesCongesRepository, TeamRepository teamRepository) {
		this.congeRepository = congeRepository;
		this.userRepository = userRepository;
		this.teamService = teamService;
		this.weekEntryRepository = weekEntryRepository;
		this.reglesCongesRepository = reglesCongesRepository;
		this.teamRepository = teamRepository;

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

	public boolean verifierReglesConges(Conge nouvelleDemandeConge) {
		Optional<Team> team = teamRepository.findByUsers(nouvelleDemandeConge.getUser());
		if (team.isPresent()){
			List<Conge> congésEquipe = new ArrayList<>();
			for (User user : team.get().getUsers()) {
				congésEquipe.addAll(congeRepository.findByUser(user));
			}

			int nombreCongesConsecutifsMax = reglesCongesRepository.findByType("rotation_conges").getValeur();
			Period pp = Period.between( nouvelleDemandeConge.getDateDebut() ,nouvelleDemandeConge.getDateFin() );
			 System.out.println(nombreCongesConsecutifsMax);
			 System.out.println("======");
			System.out.println(pp.getDays());


			int nombreCongesSimultanes = 0;
			int capaciteMaximaleVerification = reglesCongesRepository.findByType("verification_la_meme_date").getValeur();
			for (Conge congé : congésEquipe) {
				if (congé.getId().equals(nouvelleDemandeConge.getId())) {
					continue; // Ignorer la comparaison avec le même congé
				}
				if (nouvelleDemandeConge.getDateDebut().isBefore(congé.getDateFin())
						&& congé.getDateDebut().isBefore(nouvelleDemandeConge.getDateFin())) {
					nombreCongesSimultanes++;
				}
			}
			LocalDate currentDate = LocalDate.now();
			Period period = Period.between(currentDate, nouvelleDemandeConge.getDateDebut());
			if(period.getDays()< reglesCongesRepository.findByType("date_preavis").getValeur() || period.getMonths()<0) {
				return false;
			}

			if (nombreCongesSimultanes >= capaciteMaximaleVerification) {
				return false;
			}
			return true;
		}
		return false;
	}

	public Conge getLatestUserConge(Long userId) {
		return congeRepository.findTopByUserIdOrderByIdDesc(userId);
	}

	public Conge createConge(Conge conge) {
		Optional<List<Conge>> optionalConges = Optional.of(
				congeRepository.findByDateDebutGreaterThanEqualAndDateFinLessThanEqualAndUserId(conge.getDateDebut(),
						conge.getDateFin(), conge.getUser().getId()));


		if (optionalConges.isPresent() && optionalConges.get().isEmpty()) {
			boolean reglesCongesValidees = verifierReglesConges(conge);
			System.out.println(reglesCongesValidees);
			if (reglesCongesValidees) {
				System.out.println(reglesCongesValidees);
				conge.setDuree(calculerDureeConge( conge));
				return congeRepository.save(conge);
			}
		}

		return null;
	}

	//int membresEquipe = UserRepository.countByEquipe(nouvelleDemandeConge.getEquipe());
	//int nombreCongesConsecutifsMax = reglesCongesRepository.findByType("rotation_conges").getValeur();
//
//	int nombreCongesConsecutifs = congeRepository.countCongesConsecutifs(nouvelleDemandeConge.getDemandeur().getId());
//
//        if (nombreCongesConsecutifs >= nombreCongesConsecutifsMax) {
//		return false; // Le quota maximum de congés consécutifs est atteint
//	}





	public long getCongeCount() {
		return congeRepository.count();
	}



	public int calculerDureeConge(Conge conge) {
		 DURRE =(int) ChronoUnit.DAYS.between(conge.getDateDebut(), conge.getDateFin());
		 if(DURRE == 0){
			 DURRE=1;
			 System.out.println("dureeeeeee"+DURRE);

			 return DURRE;
		 }
		 else {
			 System.out.println(DURRE);

			 return DURRE;
		 }

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

	public double calculerSoldeConge(Long userId) {
		List<WeekEntry> weekEntries = weekEntryRepository.findByUserId(userId);
		LocalDateTime now = LocalDateTime.now();
		int heuresTravaillees = 0;

		for (WeekEntry weekEntry : weekEntries) {
			if (weekEntry.getEndDate().isBefore(now.toLocalDate())) {
				heuresTravaillees += weekEntry.getNumberOfHours();

			}
		}
		double joursTravailles = (double) heuresTravaillees / HEURES_PAR_JOUR;
		long moisTravailles = (long) (joursTravailles / 22);
		double soldeConge = moisTravailles * CONGE_PAR_MOIS;
		return soldeConge;
	}

	public void deleteConge(Long id) {
		congeRepository.deleteById(id);
	}

	public Conge accepterConge(Long congeId) {
		Conge conge = getCongeById(congeId);
		conge.setStatus(StatusOfDemand.ACCEPTED);
		conge = congeRepository.save(conge);


		String accountSid = "ACd7086ad292bed93eaae50de57b1684fb";
		String authToken = "d4702c7e2d3ed6556bc2af3c5b3b49e5";

		Twilio.init(accountSid, authToken);
		Message message = Message.creator(
						new PhoneNumber("+21658046046"),
						new PhoneNumber("+15416354179"),
						"Votre demande de congé a été acceptée. Profitez de votre congé !")
				.create();

		System.out.println(message.getSid());

		return conge;
	}

	public Conge refuserConge(Long congeId) {
		Conge conge = getCongeById(congeId);
		conge.setStatus(StatusOfDemand.REJECTED);
		conge = congeRepository.save(conge);


		String accountSid = "ACd7086ad292bed93eaae50de57b1684fb";
		String authToken = "d4702c7e2d3ed6556bc2af3c5b3b49e5";

		Twilio.init(accountSid, authToken);
		Message message = Message.creator(
						new PhoneNumber("+21658046046"),
						new PhoneNumber("+15416354179"),
						"Votre demande de congé a été Refusé !")
				.create();

		System.out.println(message.getSid());

		return conge;
	}

	@Override
	public Conge ajouterConge(Conge conge) {
		return null;
	}
}

