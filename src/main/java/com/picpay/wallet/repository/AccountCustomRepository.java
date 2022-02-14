package com.picpay.wallet.repository;

import java.math.BigDecimal;

import org.springframework.data.repository.query.Param;

public interface AccountCustomRepository {
	
    public BigDecimal getBalance(@Param("id") Long id);

}
