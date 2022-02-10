package com.picpay.wallet.service;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import com.picpay.wallet.component.IntegrationRest;
import com.picpay.wallet.entities.Account;
import com.picpay.wallet.entities.User;
import com.picpay.wallet.exception.AccountAlreadyExistsException;
import com.picpay.wallet.exception.AccountNotFoundException;
import com.picpay.wallet.exception.UserNotFoundException;
import com.picpay.wallet.model.UserModel;
import com.picpay.wallet.repository.AccountRepository;
import com.picpay.wallet.request.AccountRequest;

@Service
public class AccountService {

	@Value("${host.api.user}")
	private String path;

	@Autowired
	private IntegrationRest integrationRest;

	@Autowired
	private AccountRepository accountRepository;

	public List<Account> findAll(String name) {
		if (name == null) {
			return accountRepository.findAll();
		} else {
			return accountRepository.findByUserName(name);
		}
	}

	public Account findById(Long id) {
		return accountRepository.findById(id).orElse(null);
	}

	public Account findByAgencyNumber(String agency, String number) {
		return accountRepository.findByAgencyAndNumberAgency(agency, number).orElse(null);
	}

	@Transactional
	public Account create(AccountRequest account) throws UnsupportedEncodingException {
		Account accountDb = this.findByAgencyNumber(account.getAgency(), account.getNumberAgency());
		if (accountDb != null) {
			throw new AccountAlreadyExistsException(account.getAgency(), account.getNumberAgency());
		}
		ResponseEntity<UserModel> user = callFindById(account.getUserRequest().getId());
		if (user != null) {
			Account entity = new Account();
			entity.setAgency(account.getAgency());
			entity.setNumberAgency(account.getNumberAgency());
			User userResponse = new User();
			userResponse.setCpf(user.getBody().getCpf());
			userResponse.setId(user.getBody().getId());
			userResponse.setName(user.getBody().getName());
			entity.setUser(userResponse);
			return accountRepository.save(entity);
		}
		return null;
	}

	@Transactional
	public Account update(Long id, Account account) throws UnsupportedEncodingException {
		Account dbAccount = this.findById(id);
		if (dbAccount != null) {

			ResponseEntity<UserModel> user = callFindById(account.getUser().getId());
			if (user == null) {
				throw new UserNotFoundException(account.getUser().getId());
			}

			BeanUtils.copyProperties(account, dbAccount, "id");
			return accountRepository.save(dbAccount);
		} else {
			throw new AccountNotFoundException(id);
		}
	}

	@Transactional
	public void delete(Long id) {
		Account dbAccount = this.findById(id);
		if (dbAccount != null) {
			accountRepository.delete(dbAccount);
		} else {
			throw new AccountNotFoundException(id);
		}
	}

	public BigDecimal getBalance(Long id) {
		Account dbAccount = this.findById(id);
		if (dbAccount != null) {
			return accountRepository.getBalance(id);
		} else {
			throw new AccountNotFoundException(id);
		}
	}

	private ResponseEntity<UserModel> callFindById(Long id) throws UnsupportedEncodingException {
		String url = path + "/user/" + id;
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		return integrationRest.get(url, builder, UserModel.class);
	}
}
