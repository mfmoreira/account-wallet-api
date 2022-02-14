package com.picpay.wallet.request;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.picpay.wallet.enums.OperationType;


public class EntryRequest {

	@NotNull
	private AccountIdRequest account;

	@NotNull
	private OperationType operationType;

	@DecimalMin(value = "0.0", inclusive = false)
	private BigDecimal amount;

	public AccountIdRequest getAccount() {
		return account;
	}

	public void setAccount(AccountIdRequest account) {
		this.account = account;
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
