package com.picpay.wallet.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay.wallet.converter.EntryConverter;
import com.picpay.wallet.entities.Entry;
import com.picpay.wallet.exception.InsufficientFundsException;
import com.picpay.wallet.model.EntryModel;
import com.picpay.wallet.request.EntryRequest;
import com.picpay.wallet.request.TransferRequest;
import com.picpay.wallet.service.EntryService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/entry")
public class EntryController {

	@Autowired
	private EntryService entryService;

	@Autowired
	private EntryConverter entryConverter;

	@ApiOperation(value = "Find entrey account recorded")
	@GetMapping("/account_entry/{idAccount}")
	public List<EntryModel> findByAccountId(@PathVariable Long idAccount) {
		return entryConverter.toCollectionModel(entryService.findByAccountId(idAccount));
	}

	@ApiOperation(value = "Raise money or depositing money into the account")
	@PostMapping
	@Transactional
	public ResponseEntity<EntryModel> create(@RequestBody EntryRequest entryRequest) throws InsufficientFundsException {
		Entry entry = entryService.create(entryRequest);
		return ResponseEntity.ok(entryConverter.toModel(entry));
	}
	
	@PostMapping("/transfer")
	@Transactional
	public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) throws InsufficientFundsException {
		entryService.transfer(transferRequest);
		return ResponseEntity.ok().build();
	}

}
