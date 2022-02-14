package com.picpay.wallet.exception;

import javax.persistence.EntityNotFoundException;

public class AccountNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public AccountNotFoundException(String message) {
		super(message);
	}
	
	public AccountNotFoundException(Long id) {
		this(String.format("Conta %d não encontrada.", id));
	}
}
