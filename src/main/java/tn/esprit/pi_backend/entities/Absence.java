package tn.esprit.pi_backend.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
public class Absence implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDate dateAbsence;

	private String description;

	@ManyToOne
	@JoinTable(name = "user_abscence", joinColumns = @JoinColumn(name = "abscence_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User user;

	public Long getId() {
		return id;
	}

	public LocalDate getDateAbsence() {
		return dateAbsence;
	}

	public void setDateAbsence(LocalDate dateAbsence) {
		this.dateAbsence = dateAbsence;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Absence [id=" + id + ", dateAbsence=" + dateAbsence + ", description=" + description + ", user="
				+ user.toString() + "]";
	}

}
