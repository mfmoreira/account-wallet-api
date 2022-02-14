package com.picpay.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.picpay.wallet.entities.Entry;

public interface EntryRepository extends JpaRepository<Entry, Long> {

	@Query("SELECT e FROM Entry e WHERE e.account.id = :id")
	public List<Entry> findByAccountId(@Param("id") Long idAccount);
	
}
