package tn.esprit.pi_backend.restControllers;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Conge;
import tn.esprit.pi_backend.entities.StatusOfDemand;

import tn.esprit.pi_backend.repositories.CongeRepo;
import tn.esprit.pi_backend.repositories.UserRepository;
import tn.esprit.pi_backend.service.ICongeService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@RestController

@RequestMapping("/conges")
public class CongeController {


   CongeRepo congeRepo ;
    UserRepository userRepository;
    private final ICongeService congeService;


    public CongeController(ICongeService congeService) {
        this.congeService = congeService;
    }

    @GetMapping
    public List<Conge> getAllConges() {
        return congeService.getAllConges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conge> getCongeById(@PathVariable Long id) {
        Conge conge = congeService.getCongeById(id);
        return ResponseEntity.ok(conge);
    }

    @PostMapping
    public ResponseEntity<Conge> createConge(@RequestBody Conge conge) {
        Conge createdConge = congeService.createConge(conge);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdConge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conge> updateConge(@PathVariable Long id, @RequestBody Conge congeDetails) {
        Conge updatedConge = congeService.updateConge(id, congeDetails);
        return ResponseEntity.ok(updatedConge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConge(@PathVariable Long id) {
        congeService.deleteConge(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/accepter")
    public ResponseEntity<Conge> accepterConge(@PathVariable Long id) {
        Conge acceptedConge = congeService.accepterConge(id);
        return ResponseEntity.ok(acceptedConge);
    }

    @PostMapping("/{id}/refuser")
    public ResponseEntity<Conge> refuserConge(@PathVariable Long id) {
        Conge refusedConge = congeService.refuserConge(id);
        return ResponseEntity.ok(refusedConge);
    }

}
