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

import tn.esprit.pi_backend.entities.Holiday;
import tn.esprit.pi_backend.repositories.HolidayRepository;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/holidays")
public class HolidayController {
	@Autowired
	private HolidayRepository holidayRepository;

	@GetMapping()
	public ResponseEntity<List<Holiday>> getAllHolidays() {
		return new ResponseEntity<>(holidayRepository.findAll(), HttpStatus.OK);
	}

	@GetMapping("/current")
	public ResponseEntity<List<Holiday>> getCurrentMonthHolidays() {
		LocalDate initialDate = LocalDate.now();
		return new ResponseEntity<>(holidayRepository.findAllByEndDateLessThanEqualAndStartDateGreaterThanEqual(
				initialDate.withDayOfMonth(initialDate.getMonth().length(initialDate.isLeapYear())),
				initialDate.withDayOfMonth(1)), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<Holiday> createHoliday(@RequestBody Holiday holiday) {
		return new ResponseEntity<>(holidayRepository.save(holiday), HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<Holiday> updateHoliday(@PathVariable Long id, @RequestBody Holiday holiday) {
		Optional<Holiday> originalHoliday = holidayRepository.findById(id);
		if (originalHoliday.isPresent()) {
			Holiday holidayUpdate = originalHoliday.get();
			if (holiday.getName() != null) {
				holidayUpdate.setName(holiday.getName());
			}
			if (holiday.getStartDate() != null) {
				holidayUpdate.setStartDate(holiday.getStartDate());
			}
			if (holiday.getEndDate() != null) {
				holidayUpdate.setEndDate(holiday.getEndDate());
			}
			return new ResponseEntity<>(holidayRepository.save(holidayUpdate), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteHoliday(@PathVariable("id") Long id) {
		holidayRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
