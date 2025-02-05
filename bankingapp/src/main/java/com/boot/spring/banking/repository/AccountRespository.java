package com.boot.spring.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.spring.banking.entity.Account;

public interface AccountRespository extends JpaRepository<Account, Long>{

}
