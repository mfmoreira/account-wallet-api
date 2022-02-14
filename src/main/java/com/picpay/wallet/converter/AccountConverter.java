package com.picpay.wallet.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.picpay.wallet.entities.Account;
import com.picpay.wallet.model.AccountModel;
import com.picpay.wallet.request.AccountRequest;



@Component
public class AccountConverter {
	
	@Autowired
	private ModelMapper modelMapper;

	public AccountModel toModel(Account account) {
		return modelMapper.map(account, AccountModel.class);
	}

	public List<AccountModel> toCollectionModel(List<Account> accounts) {
		return accounts.stream()
				.map(account -> this.toModel(account))
				.collect(Collectors.toList());
	}

	public Account toDomainObject(AccountRequest accountRequest) {
		return modelMapper.map(accountRequest, Account.class);
	}
}
