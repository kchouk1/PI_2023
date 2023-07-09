package tn.esprit.pi_backend.services;

import tn.esprit.pi_backend.entities.Formation;
import java.util.List;
public interface IFormationService {
    public Formation createFormation(Formation formation);
    public Formation getFormationById(Long id);
    public List<Formation> getAllFormations();
    public Formation updateFormation(Formation formation);
    public void deleteFormation(Long id);
    List<Formation> getFormationsByName(String formationName);



    //List<String> getMembers();
}
