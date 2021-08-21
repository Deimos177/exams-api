package com.exams.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "health_insurances")
@Table(name = "health_insurances")
public class HealthInsurance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "healthInsurance")
	private List<User> users = new ArrayList<>();
	
	@ManyToMany
	@JoinTable(name = "insurances_clinics", joinColumns = @JoinColumn(name = "health_insurance_id"), inverseJoinColumns = @JoinColumn(name = "clinic_id"))
	private List<Clinic> clinics;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public HealthInsurance() {
	}
}