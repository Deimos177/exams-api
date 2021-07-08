package com.deimos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResetPasswordRequest {
	
	@JsonProperty("password")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}