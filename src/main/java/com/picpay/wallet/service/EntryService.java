package com.picpay.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.picpay.wallet.converter.EntryConverter;
import com.picpay.wallet.entities.Account;
import com.picpay.wallet.entities.Entry;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.enums.OperationType;
import com.picpay.wallet.exception.AccountNotFoundException;
import com.picpay.wallet.exception.InsufficientFundsException;
import com.picpay.wallet.model.AccountModel;
import com.picpay.wallet.model.UserModel;
import com.picpay.wallet.repository.EntryRepository;
import com.picpay.wallet.request.EntryRequest;
import com.picpay.wallet.request.TransferRequest;

@Service
public class EntryService {

	@Autowired
	private EntryRepository entryRepository;

	@Autowired
	private EntryConverter entryConverter;
	
	@Autowired
	private AccountService accountService;

	public List<Entry> findAll() {
		return entryRepository.findAll();
	}

	public Entry findById(Long id) {
		return entryRepository.findById(id).orElse(null);
	}

	public List<Entry> findByAccountId(Long idConta) {
		return entryRepository.findByAccountId(idConta);
	}

	@Transactional
	public Entry create(EntryRequest entryRequest) throws InsufficientFundsException {
		Entry entry = entryConverter.toDomainObject(entryRequest);

		if (entry.getAccount() != null) {

			if (entry.getOperationType() == OperationType.MONEY_OUT.getId() && entry.getAmount()
					.compareTo(this.callApiAccountGetBalance(entry.getAccount().getId())) == 1) {
				throw new InsufficientFundsException();
			} else {
				entry.setReleaseDate(LocalDateTime.now());
				return entryRepository.save(entry);
			}

		}
		throw new AccountNotFoundException(entryRequest.getAccount().getId());
	}

	@Async
	public void transfer(TransferRequest transferRequest) throws InsufficientFundsException {
		Entry entryOrigin = new Entry();
		Entry entryDestination = new Entry();
		AccountModel model = this.callApiAccountFindById(transferRequest.getIdAccountOrigin());
		Account accountOrigin = populateAccount(model);
		User user = populateUser(model);
		accountOrigin.setUser(user);
		BigDecimal balance = this.callApiAccountGetBalance(accountOrigin.getId());
		if (balance != null) {
			if (transferRequest.getAmount().compareTo(balance) == 1) {
				throw new InsufficientFundsException();
			}
		}

		entryOrigin.setReleaseDate(LocalDateTime.now());
		entryOrigin.setOperationType(OperationType.MONEY_OUT.getId());
		entryOrigin.setAccount(accountOrigin);
		entryOrigin.setAmount(transferRequest.getAmount());
		entryRepository.save(entryOrigin);

		AccountModel modelDestination = this.callApiAccountFindById(transferRequest.getIdDestinationAccount());
		if (modelDestination != null) {
			Account accountDestinstion = populateAccount(modelDestination);
			User userDestination = populateUser(modelDestination);
			accountDestinstion.setUser(userDestination);
			entryDestination.setReleaseDate(LocalDateTime.now());
			entryDestination.setOperationType(OperationType.MONEY_DEPOSIT.getId());
			entryDestination.setAccount(accountDestinstion);
			entryDestination.setAmount(transferRequest.getAmount());
			entryRepository.save(entryDestination);
		} else {
			throw new AccountNotFoundException(transferRequest.getIdDestinationAccount());
		}
	}

	private User populateUser(AccountModel model) {
		User user = new User();
		user.setCpf(model.getUser().getCpf());
		user.setId(model.getId());
		user.setName(model.getUser().getName());
		return user;
	}

	public Account populateAccount(AccountModel model) {
		Account accountOrigin = new Account();
		accountOrigin.setAgency(model.getAgency());
		accountOrigin.setId(model.getId());
		accountOrigin.setNumberAgency(model.getNumber());
		return accountOrigin;
	}

	public BigDecimal callApiAccountGetBalance(Long id) {
		return accountService.getBalance(id);
	}

	public AccountModel callApiAccountFindById(Long id) {
		Account account = accountService.findById(id);
		AccountModel accountModel = new AccountModel();
		UserModel userModel = new UserModel();
		
		accountModel.setAgency(account.getAgency());
		accountModel.setId(account.getId());
		accountModel.setNumber(account.getNumberAgency());
		userModel.setCpf(account.getUser().getCpf());
		userModel.setId(account.getUser().getId());
		userModel.setName(account.getUser().getName());
		accountModel.setUser(userModel);
		
		return accountModel;
	}
}
