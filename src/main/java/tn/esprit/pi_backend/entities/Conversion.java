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

import tn.esprit.pi_backend.enums.EConversionType;

@Entity
public class Conversion implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private EConversionType conversionType;
	private StatusOfDemand status = StatusOfDemand.NOT_YET_TREATED;
	private LocalDate requestDate = LocalDate.now();

	@ManyToOne
	@JoinTable(name = "user_conversion", joinColumns = @JoinColumn(name = "conversion_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private User user;

	public EConversionType getConversionType() {
		return conversionType;
	}

	public void setConversionType(EConversionType conversionType) {
		this.conversionType = conversionType;
	}

	public StatusOfDemand getStatus() {
		return status;
	}

	public void setStatus(StatusOfDemand status) {
		this.status = status;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Conversion [id=" + id + ", conversionType=" + conversionType + ", status=" + status + ", requestDate="
				+ requestDate + ", user=" + user.toString() + "]";
	}

}
