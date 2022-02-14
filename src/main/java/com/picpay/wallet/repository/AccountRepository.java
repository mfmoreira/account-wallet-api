package com.picpay.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.picpay.wallet.entities.Account;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountCustomRepository {

	public Optional<Account> findByAgencyAndNumberAgency(String agency, String numberAgency);

	@Query("SELECT a FROM Account a WHERE a.user.name =:name")
	public List<Account> findByUserName(@Param("name") String name);

}
