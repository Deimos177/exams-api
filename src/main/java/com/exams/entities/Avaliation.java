package com.exams.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name = "avaliations")
@Table(name = "avaliations")
public class Avaliation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private Double stars;
	
	@Column
	private  String description;
	
	@Column
	private String status;
	
	@OneToOne(mappedBy = "avaliation_id")
	private Exam exam;

	public Double getStars() {
		return stars;
	}

	public void setStars(Double stars) {
		this.stars = stars;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}
}