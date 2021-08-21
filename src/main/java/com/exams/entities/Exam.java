package com.exams.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exams")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Date date;

	@Column(nullable = false)
	private String status;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient_id", referencedColumnName = "id", nullable = false)
	private User patient_id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "avaliation_id", referencedColumnName = "id", nullable = true)
	private Avaliation avaliation_id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctor_id", referencedColumnName = "id", nullable = false)
	private Doctor doctor_id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "clinic_id", referencedColumnName = "id", nullable = false)
	private Clinic clinic_id;

	@Column
	private String paied;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(User patient_id) {
		this.patient_id = patient_id;
	}

	public Avaliation getAvaliation_id() {
		return avaliation_id;
	}

	public void setAvaliation_id(Avaliation avaliation_id) {
		this.avaliation_id = avaliation_id;
	}

	public Doctor getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(Doctor doctor_id) {
		this.doctor_id = doctor_id;
	}

	public Clinic getClinic_id() {
		return clinic_id;
	}

	public void setClinic_id(Clinic clinic_id) {
		this.clinic_id = clinic_id;
	}

	public String getPaied() {
		return paied;
	}

	public void setPaied(String paied) {
		this.paied = paied;
	}

	public Exam() {
	}

	public Exam(Date date, String status, User patient_id, Avaliation avaliation_id, Doctor doctor_id, Clinic clinic_id,
			String paied) {
		this.date = date;
		this.status = status;
		this.patient_id = patient_id;
		this.avaliation_id = avaliation_id;
		this.doctor_id = doctor_id;
		this.clinic_id = clinic_id;
		this.paied = paied;
	}
}