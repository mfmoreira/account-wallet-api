package com.picpay.wallet.request;

import javax.validation.constraints.NotBlank;

public class UserRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String cpf;

	public String getName() {
		return name;
	}

	public void setName(String nome) {
		this.name = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
