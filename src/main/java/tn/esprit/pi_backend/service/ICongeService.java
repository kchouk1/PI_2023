package tn.esprit.pi_backend.service;


import tn.esprit.pi_backend.entities.Conge;

import java.util.List;

public interface ICongeService {


    public Conge ajouterConge(Conge conge);


    List<Conge> getAllConges();
    
    List<Conge> getAllCurrentConges(Long id);

    Conge getCongeById(Long id);

    Conge createConge(Conge conge);

    void deleteConge(Long id);

    Conge accepterConge(Long id);

    Conge refuserConge(Long id);

    Conge updateConge(Long id, Conge congeDetails);
}