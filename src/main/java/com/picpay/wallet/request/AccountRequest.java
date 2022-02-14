package com.picpay.wallet.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AccountRequest {

	@NotBlank
	private String agency;

	@NotBlank
	private String numberAgency;

	@NotNull
	private UserIdRequest userRequest;

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public String getNumberAgency() {
		return numberAgency;
	}

	public void setNumberAgency(String numberAgency) {
		this.numberAgency = numberAgency;
	}

	public UserIdRequest getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(UserIdRequest userRequest) {
		this.userRequest = userRequest;
	}

}
