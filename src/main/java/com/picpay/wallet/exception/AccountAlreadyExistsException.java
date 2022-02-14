package com.picpay.wallet.exception;

import javax.persistence.EntityExistsException;

public class AccountAlreadyExistsException extends EntityExistsException {
	
	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException(String message) {
		super(message);
	}
	
	public AccountAlreadyExistsException(String agency, String numberAgency) {
		this(String.format("Existe uma conta na agência %s com o número %s.", agency, numberAgency));
	}
}
