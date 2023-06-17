package tn.esprit.pi_backend.entities;


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


    private Long UserId;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatusOfDemand status ;
    private boolean approuve;

    // constructeurs, getters et setters

    public boolean isApprouve() {
        return approuve;
    }

    public void setApprouve(boolean approuve) {
        this.approuve = approuve;
    }
//    @ManyToOne
//    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
//    private User_Users user;

@ManyToOne
    User_Users users;


}