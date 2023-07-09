package tn.esprit.pi_backend.restControllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Conge;
import tn.esprit.pi_backend.repositories.UserRepository;

import tn.esprit.pi_backend.service.ICongeService;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/conges")
public class CongeController {


   @Autowired
    UserRepository userRepository;
    private final ICongeService congeService;

    public CongeController(ICongeService congeService) {
        this.congeService = congeService;
    }

    @GetMapping
    public List<Conge> getAllConges() {
        return congeService.getAllConges();
    }
    
    @GetMapping("/user/{userId}")
    public List<Conge> getAllConges(@PathVariable Long userId) {
        return congeService.getAllCurrentConges(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conge> getCongeById(@PathVariable Long id) {
        Conge conge = congeService.getCongeById(id);
        return ResponseEntity.ok(conge);
    }
    
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<Conge> getLatestUserConge(@PathVariable Long userId) {
    	Conge conge = congeService.getLatestUserConge(userId);
    	return ResponseEntity.ok(conge);
    }
    


    @PostMapping
    public ResponseEntity<Conge> createConge(@RequestBody Conge conge) {
        Conge createdConge = congeService.createConge(conge);
        if (createdConge != null) {
        	return ResponseEntity.status(HttpStatus.CREATED).body(createdConge);
		}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createdConge);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conge> updateConge(@PathVariable Long id, @RequestBody Conge congeDetails) {
        Conge updatedConge = congeService.updateConge(id, congeDetails);
        if (updatedConge != null) {
        	return ResponseEntity.status(HttpStatus.OK).body(updatedConge);
		}
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(updatedConge);
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
    @GetMapping("/{id}/duree")
    public int calculerDureeConge(@PathVariable Long id) {
        Conge conge = congeService.getCongeById(id);
        return congeService.calculerDureeConge(conge);
    }
    @GetMapping("/user/{userId}/soldess")
    public ResponseEntity<Double> getUserSoldeConge(@PathVariable Long userId) {
        System.out.println(userId);
        double soldeConge = congeService.calculerSoldeConge(userId);
            return ResponseEntity.ok(soldeConge);
    }
    @GetMapping("/count")
    public long getCongeCount() {
        return congeService.getCongeCount();
    }

}
