package com.exams.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "clinics")
public class Clinic {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column(nullable = false)
	private String zipCode;

	@Column(nullable = false)
	private String addressNumber;

	private String averageScore;

	@ManyToMany
	@JoinTable(name = "doctors_clinics", joinColumns = @JoinColumn(name = "clinic_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private List<Doctor> doctors;
	
	@OneToOne(mappedBy = "clinic_id")
	private Administrator administrator;
	
	@ManyToMany(mappedBy = "clinics")
	private List<HealthInsurance> healthInsurances;
	
	@OneToOne(mappedBy = "clinic_id")
	private Exam exam;

	public Clinic() {
	}

	public Clinic(Long id, Double latitude, Double longitude, String zipCode, String addressNumber,
			String averageScore) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.zipCode = zipCode;
		this.addressNumber = addressNumber;
		this.averageScore = averageScore;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}

	public Long getId() {
		return id;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Administrator getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Administrator administrator) {
		this.administrator = administrator;
	}

	public List<HealthInsurance> getHealthInsurances() {
		return healthInsurances;
	}

	public void setHealthInsurances(List<HealthInsurance> healthInsurances) {
		this.healthInsurances = healthInsurances;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
