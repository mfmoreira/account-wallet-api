package com.picpay.wallet.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.picpay.wallet.converter.AccountConverter;
import com.picpay.wallet.entities.Account;
import com.picpay.wallet.model.AccountModel;
import com.picpay.wallet.request.AccountRequest;
import com.picpay.wallet.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountConverter accountConverter;

	@GetMapping
	public List<AccountModel> findAll(@RequestParam(required = false) String name) {
		return accountConverter.toCollectionModel(accountService.findAll(name));
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountModel> findById(@PathVariable Long id) {
		Account account = accountService.findById(id);
		if (account != null) {
			return ResponseEntity.ok(accountConverter.toModel(account));
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/agency_number_agency")
	public ResponseEntity<AccountModel> findByAgencyNumber(@RequestParam("agency") String agency,
			@RequestParam("numberAgency") String numberAgency) {
		Account account = accountService.findByAgencyNumber(agency, numberAgency);
		if (account != null) {
			return ResponseEntity.ok(accountConverter.toModel(account));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	public ResponseEntity<AccountModel> create(@RequestBody final AccountRequest accountRequest) throws UnsupportedEncodingException {
		Account account = accountService.create(accountRequest);
		return ResponseEntity.ok(accountConverter.toModel(account));
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<AccountModel> update(@PathVariable Long id, @RequestBody @Valid AccountRequest input) throws UnsupportedEncodingException {
		Account conta = accountConverter.toDomainObject(input);
		return ResponseEntity.ok(accountConverter.toModel(accountService.update(id, conta)));
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		accountService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/amount/{id}")
	public ResponseEntity<BigDecimal> getBalance(@PathVariable Long id) {
		return ResponseEntity.ok(accountService.getBalance(id));
	}

}
