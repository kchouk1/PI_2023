package tn.esprit.pi_backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pi_backend.entities.Formation;
import tn.esprit.pi_backend.repositories.FormationRepository;

import java.util.List;
@Service
public class FormationService implements IFormationService{
    @Autowired
    private FormationRepository formationRepository;
    @Override
    public Formation createFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public Formation getFormationById(Long id) {
        return formationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Formation> getAllFormations() {
        return (List<Formation>) formationRepository.findAll();
    }

    @Override
    public Formation updateFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public void deleteFormation(Long id) {
        formationRepository.deleteById(id);
    }

    @Override
    public List<Formation> getFormationsByName(String formationName) {
        return formationRepository.findFormationByFormationName(formationName);
    }

    //@Override
    //public List<String> getMembers() {
       // return formationRepository.findFormationByMembers();
    //}


}
