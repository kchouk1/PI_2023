package tn.esprit.pi_backend.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;

    private LocalDate startDate;

    private LocalDate deadline;

    private String taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // Getters and setters
}

