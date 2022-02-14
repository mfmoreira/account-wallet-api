package com.picpay.wallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.picpay.wallet.entities.Account;
import com.picpay.wallet.enums.OperationType;

public class EntryModel {

	private Long id;
	private Account account;
	private LocalDateTime releaseDate;
	private OperationType operationType;
	private BigDecimal amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public LocalDateTime getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDateTime releaseDate) {
		this.releaseDate = releaseDate;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
