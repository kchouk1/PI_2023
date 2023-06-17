package tn.esprit.pi_backend.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.WeekEntry;
import tn.esprit.pi_backend.repositories.WeekEntryRepository;
import tn.esprit.pi_backend.service.WeekEntryService;

@CrossOrigin
@RestController
@RequestMapping("/weekentries")
public class WeekEntryController {
    private final WeekEntryService weekEntryService;
    
    @Autowired
    WeekEntryRepository weekEntryRepository;

    @Autowired
    public WeekEntryController(WeekEntryService weekEntryService) {
        this.weekEntryService = weekEntryService;
    }
    
    @GetMapping
    public List<WeekEntry> getAllWeekEntries() {
    	return weekEntryRepository.findAll();
    }
    
    @GetMapping("{id}")
    public List<WeekEntry> getUserWeekEntries(@PathVariable Long id) {
    	return weekEntryRepository.findByUserId(id);
    }

    @PostMapping
    public ResponseEntity<WeekEntry> addWeekEntry(@RequestBody WeekEntry weekEntry) {
        WeekEntry addedWeekEntry = weekEntryService.addWeekEntry(weekEntry);
        return new ResponseEntity<>(addedWeekEntry, HttpStatus.CREATED);
    }

    @PutMapping("updatePresence/{id}")
    public ResponseEntity<WeekEntry> updateWeekEntry(
            @PathVariable Long id,
            @RequestBody WeekEntry updatedWeekEntry) {
        WeekEntry updatedEntry = weekEntryService.updateWeekEntry(id, updatedWeekEntry);
        return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWeekEntry(@PathVariable Long id) {
        weekEntryService.deleteWeekEntry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
