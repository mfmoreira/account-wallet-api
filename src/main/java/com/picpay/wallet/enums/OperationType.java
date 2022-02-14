package com.picpay.wallet.enums;

public enum OperationType {

	MONEY_DEPOSIT(0, "Money Deposit"),
	MONEY_OUT(1, "Money Out");
	
	private int id;
	private String description;
		
	OperationType(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}	
	
}
