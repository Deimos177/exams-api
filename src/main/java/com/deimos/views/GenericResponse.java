package com.deimos.views;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenericResponse {
	
	@JsonProperty("error")
	protected Boolean error;
	
	@JsonProperty("message")
	protected String message;

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}