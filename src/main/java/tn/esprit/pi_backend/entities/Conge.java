package tn.esprit.pi_backend.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Conge implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int duree ;
    private int soldeConge ;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatusOfDemand status =StatusOfDemand.NOT_YET_TREATED ;

@ManyToOne
@JoinTable(
        name = "user_conge",
        joinColumns = @JoinColumn(name = "conge_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
private User user;


}