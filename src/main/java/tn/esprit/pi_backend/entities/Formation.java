package tn.esprit.pi_backend.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "formation")
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String formationName;

    private String formationStatus;

    private LocalDate dateStart;

    private LocalDate dateFinish;

    @ManyToMany(mappedBy = "formations")
    private List<Project> projects = new ArrayList<>();

    /*@ElementCollection
    private List<String> members = new ArrayList<>();
    public List<String> getAllMembers() {
        return members;
    }*/

    // Getters and setters
}
