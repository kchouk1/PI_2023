package tn.esprit.pi_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.WeekEntry;
import tn.esprit.pi_backend.repositories.WeekEntryRepository;

@Service
public class WeekEntryService {
    private final WeekEntryRepository weekEntryRepository;

    @Autowired
    public WeekEntryService(WeekEntryRepository weekEntryRepository) {
        this.weekEntryRepository = weekEntryRepository;
    }

    public WeekEntry addWeekEntry(WeekEntry weekEntry) {
        return weekEntryRepository.save(weekEntry);
    }

    public WeekEntry updateWeekEntry(Long id, WeekEntry updatedWeekEntry) {
        WeekEntry existingWeekEntry = weekEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Week entry not found"));

        existingWeekEntry.setStartDate(updatedWeekEntry.getStartDate());
        existingWeekEntry.setEndDate(updatedWeekEntry.getEndDate());
        existingWeekEntry.setDescription(updatedWeekEntry.getDescription());
        existingWeekEntry.setHourEntries(updatedWeekEntry.getHourEntries());

        return weekEntryRepository.save(existingWeekEntry);
    }

    public void deleteWeekEntry(Long id) {
        weekEntryRepository.deleteById(id);
    }
}
