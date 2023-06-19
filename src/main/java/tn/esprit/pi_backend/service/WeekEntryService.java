package tn.esprit.pi_backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.pi_backend.entities.User;
import tn.esprit.pi_backend.entities.WeekEntry;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.repositories.WeekEntryRepository;

@Service
public class WeekEntryService {
	private final WeekEntryRepository weekEntryRepository;
	private final UserRepository userRepository;

	@Autowired
	public WeekEntryService(WeekEntryRepository weekEntryRepository, UserRepository userRepository) {
		this.weekEntryRepository = weekEntryRepository;
		this.userRepository = userRepository;
	}

	public WeekEntry addWeekEntry(WeekEntry weekEntry, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			weekEntry.setUser(user.get());
		}
		return weekEntryRepository.save(weekEntry);
	}

	public WeekEntry updateWeekEntry(Long id, WeekEntry updatedWeekEntry, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		WeekEntry existingWeekEntry = weekEntryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Week entry not found"));
		
		if (user.isPresent()) {
			existingWeekEntry.setUser(user.get());
		}
		existingWeekEntry.setStartDate(updatedWeekEntry.getStartDate());
		existingWeekEntry.setEndDate(updatedWeekEntry.getEndDate());
		existingWeekEntry.setDescription(updatedWeekEntry.getDescription());
		existingWeekEntry.setHourEntries(updatedWeekEntry.getHourEntries());
		existingWeekEntry.setNumberOfHours(updatedWeekEntry.getNumberOfHours());

		return weekEntryRepository.save(existingWeekEntry);
	}

	public void deleteWeekEntry(Long id) {
		weekEntryRepository.deleteById(id);
	}
}
