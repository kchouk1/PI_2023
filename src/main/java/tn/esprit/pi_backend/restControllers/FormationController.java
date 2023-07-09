package tn.esprit.pi_backend.restControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pi_backend.entities.Formation;
import tn.esprit.pi_backend.services.EmailService;
import tn.esprit.pi_backend.services.IEmailService;
import tn.esprit.pi_backend.services.IFormationService;

import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:4200","http://localhost:4200"})
@RestController
@RequestMapping("/formations")
public class FormationController {
    @Autowired
    private IFormationService formationService;
    @Autowired
    private IEmailService emailService;

    @PostMapping
    public Formation createFormation(@RequestBody Formation formation) {
        //return formationService.createFormation(formation);
        Formation createdFormation = formationService.createFormation(formation);
       // List<String> members = formationService.getMembers();
        String name = formation.getFormationName();
        System.out.println(name);
        String text = "<p>🌟 New Formation Alert! 🌟</p>" +
                "<p>Dear team members,</p>" +
                "<p>We are excited to announce a new formation named :"+name+" that has been added to our learning platform. This formation provides valuable insights and knowledge on various topics.</p>" +
                "<p>To access the formation, please click on the link below:</p>" +
                "<p><a href='http://localhost:4200/#/contenu/formation/meet/" + name + "'>Access Formation</a></p>" +
                "<p>Make the most out of this opportunity to enhance your skills and stay ahead in your professional journey. If you have any questions or need assistance, feel free to reach out to us.</p>" +
                "<p>Thank you for your dedication and commitment to continuous learning!</p>" +
                "<p>Best regards,</p>" +
                "<p>Your Learning Team 🚀</p>";;
        // Send email
        String emailAddress = "wajdi.hassyaoui@esprit.tn";
        String subject="Formation Added";
        for (String member:formation.getMembers()) {

            emailService.sendFormationAddedEmail(member,subject,text);
        }
        return createdFormation;
    }

    @GetMapping("/{id}")
    public Formation getFormationById(@PathVariable Long id) {
        return formationService.getFormationById(id);
    }

    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }

    @PutMapping("/{id}")
    public Formation updateFormation(@PathVariable Long id, @RequestBody Formation formation) {
        Formation existingFormation = formationService.getFormationById(id);
        if (existingFormation != null) {
            existingFormation.setFormationName(formation.getFormationName());
            existingFormation.setFormationStatus(formation.getFormationStatus());
            existingFormation.setDateStart(formation.getDateStart());
            existingFormation.setDateFinish(formation.getDateFinish());
            return formationService.updateFormation(existingFormation);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteFormation(@PathVariable Long id) {
        formationService.deleteFormation(id);
    }
    @GetMapping("/search/{formationName}")
    public ResponseEntity<List<Formation>> getFormationsByName(@PathVariable String formationName) {
        List<Formation> formations = formationService.getFormationsByName(formationName);
        return ResponseEntity.ok(formations);
    }
}
