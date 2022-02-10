package com.picpay.wallet.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.picpay.wallet.entities.Entry;
import com.picpay.wallet.enums.OperationType;
import com.picpay.wallet.model.EntryModel;
import com.picpay.wallet.request.EntryRequest;
import com.picpay.wallet.service.EntryService;

@Component
public class EntryConverter {

	@Autowired
	private EntryService entryService;

	public EntryModel toModel(Entry entry) {

		EntryModel model = new EntryModel();
		model.setAccount(entry.getAccount());
		model.setAmount(entry.getAmount());
		model.setId(entry.getId());
		model.setReleaseDate(entry.getReleaseDate());
		model.setOperationType(entry.getOperationType() == 0 ? OperationType.MONEY_DEPOSIT : OperationType.MONEY_OUT);
		return model;
	}

	public List<EntryModel> toCollectionModel(List<Entry> releases) {
		return releases.stream().map(entry -> this.toModel(entry)).collect(Collectors.toList());
	}

	public Entry toDomainObject(EntryRequest entryRequest) {
		Entry response = new Entry();
		response.setAccount(entryService
				.populateAccount(entryService.callApiAccountFindById(entryRequest.getAccount().getId())));
		response.setAmount(entryRequest.getAmount());
		response.setOperationType(entryRequest.getOperationType().getId());
		return response;
	}

}
