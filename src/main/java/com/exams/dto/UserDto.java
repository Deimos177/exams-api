package com.exams.dto;

public class UserDto {
	
	public String username;
	public String password;
	public String email;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDto(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public UserDto() {
	}
	
	public UserDto(String password, String email) {
		this.password = password;
		this.email = email;
	}
}
