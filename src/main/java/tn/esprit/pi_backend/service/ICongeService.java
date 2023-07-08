package tn.esprit.pi_backend.service;


import tn.esprit.pi_backend.entities.Conge;
import tn.esprit.pi_backend.entities.User;

import java.util.List;

public interface ICongeService {


    public Conge ajouterConge(Conge conge);

    double calculerSoldeConge(Long userId) ;
    public int calculerDureeConge(Conge conge);
    List<Conge> getAllConges();
    
    List<Conge> getAllCurrentConges(Long id);

    Conge getCongeById(Long id);
    
    Conge getLatestUserConge(Long id);

    Conge createConge(Conge conge);

    void deleteConge(Long id);

    Conge accepterConge(Long id);

    Conge refuserConge(Long id);

    Conge updateConge(Long id, Conge congeDetails);
}