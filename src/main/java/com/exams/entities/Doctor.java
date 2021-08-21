package com.exams.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "doctors")
public class Doctor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String specialty;
	
	@ManyToMany(mappedBy = "doctors")
	private List<Clinic> clinics;
	
	@OneToOne(mappedBy = "doctor_id")
	private Exam exam;

	public Doctor() {
	}

	public Doctor(String name, String specialty) {
		this.name = name;
		this.specialty = specialty;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public List<Clinic> getClinics() {
		return clinics;
	}

	public void setClinics(List<Clinic> clinics) {
		this.clinics = clinics;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}
