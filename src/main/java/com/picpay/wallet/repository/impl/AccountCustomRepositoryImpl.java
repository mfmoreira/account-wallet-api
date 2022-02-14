package com.picpay.wallet.repository.impl;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.picpay.wallet.repository.AccountCustomRepository;

public class AccountCustomRepositoryImpl implements AccountCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public BigDecimal getBalance(Long id) {

		StringBuilder query = new StringBuilder();

		query.append("select coalesce(sum(e.amount),0) from Entry e where e.account.id = ").append(id)
				.append(" and operation_type = 0");
		BigDecimal in = (BigDecimal) entityManager.createQuery(query.toString()).getSingleResult();

		StringBuilder query2 = new StringBuilder();
		query2.append("select coalesce(sum(e.amount),0) from Entry e where e.account.id = ").append(id)
				.append(" and operation_type = 1");
		BigDecimal out = (BigDecimal) entityManager.createQuery(query2.toString()).getSingleResult();

		return in.subtract(out);

	}

}
