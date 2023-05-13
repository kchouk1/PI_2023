package tn.esprit.pi_backend.entities;



import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;

    private String projectStatus;


    // Getters and setters
    @ManyToMany
    private List<Formation> formations=new ArrayList<>();
}