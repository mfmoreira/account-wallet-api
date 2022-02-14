package com.picpay.wallet.request;

import java.math.BigDecimal;

public class TransferRequest {

	private BigDecimal amount;
	private Long idAccountOrigin;
	private Long idDestinationAccount;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getIdAccountOrigin() {
		return idAccountOrigin;
	}

	public void setIdAccountOrigin(Long idAccountOrigin) {
		this.idAccountOrigin = idAccountOrigin;
	}

	public Long getIdDestinationAccount() {
		return idDestinationAccount;
	}

	public void setIdDestinationAccount(Long idDestinationAccount) {
		this.idDestinationAccount = idDestinationAccount;
	}

}
