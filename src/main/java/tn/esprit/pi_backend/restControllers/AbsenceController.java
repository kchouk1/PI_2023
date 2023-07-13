package tn.esprit.pi_backend.restControllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.pi_backend.entities.Absence;
import tn.esprit.pi_backend.entities.WeekEntry;
import tn.esprit.pi_backend.repositories.AbsenceRepository;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/abscences")
public class AbsenceController {
	@Autowired
	AbsenceRepository abscenceRepo;

	@GetMapping
	public ResponseEntity<List<Absence>> getAllAbscences() {
		return new ResponseEntity<>(abscenceRepo.findAll(), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Absence>> getUserAbscences(@PathVariable Long userId) {
		return new ResponseEntity<>(abscenceRepo.findByUserId(userId), HttpStatus.OK);
	}

	@GetMapping("/user/{userId}/current")
	public ResponseEntity<List<Absence>> getCurrentMonthUserAbscences(@PathVariable Long userId) {
		LocalDate initialDate = LocalDate.now();
		return new ResponseEntity<>(
				abscenceRepo.findByDateAbsenceGreaterThanEqualAndDateAbsenceLessThanEqualAndUserId(
						initialDate.withDayOfMonth(1),
						initialDate.withDayOfMonth(initialDate.getMonth().length(initialDate.isLeapYear())), userId),
				HttpStatus.OK);
	}

	@PostMapping("/user/{userId}/range")
	public ResponseEntity<List<Absence>> getRangeUserAbsence(@PathVariable Long userId,
			@RequestBody WeekEntry weekEntry) {
		return new ResponseEntity<>(abscenceRepo.findByDateAbsenceGreaterThanEqualAndDateAbsenceLessThanEqualAndUserId(
				weekEntry.getStartDate(), weekEntry.getEndDate(), userId), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Absence> createAbscence(@RequestBody Absence absence) {
		Optional<Absence> optionalAbsence = abscenceRepo.findByDateAbsenceAndUserId(absence.getDateAbsence(),
				absence.getUser().getId());
		if (optionalAbsence.isPresent()) {
			return new ResponseEntity<>(abscenceRepo.save(absence), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Absence> updateAbscence(@PathVariable Long id, @RequestBody Absence abscence) {
		Optional<Absence> originalAbscence = abscenceRepo.findById(id);
		if (originalAbscence.isPresent()) {
			Absence abscenceUpdate = originalAbscence.get();
			if (abscence.getDateAbsence() != null) {
				abscenceUpdate.setDateAbsence(abscence.getDateAbsence());
			}
			if (abscence.getDescription() != null) {
				abscenceUpdate.setDescription(abscence.getDescription());
			}
			return new ResponseEntity<>(abscenceRepo.save(abscenceUpdate), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteAbscence(@PathVariable("id") Long id) {
		abscenceRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
