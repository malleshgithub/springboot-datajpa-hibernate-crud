package com.boot.spring.banking.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.boot.spring.banking.dto.AccountDto;
import com.boot.spring.banking.entity.Account;
import com.boot.spring.banking.mapper.AccountMapper;
import com.boot.spring.banking.repository.AccountRespository;
import com.boot.spring.banking.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{
	
	private AccountRespository accountRepository;
	
	public AccountServiceImpl(AccountRespository accountRepository) {
		this.accountRepository = accountRepository;
	}


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account=AccountMapper.mapToAccount(accountDto);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto getAccountByID(Long id) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account is not exist"));
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account is not exist"));
		double total=account.getBalance()+amount;
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account is not exist"));
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficent Amount");
		}
		double total=account.getBalance()-amount;
		account.setBalance(total);
		Account savedAccount=accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts=accountRepository.findAll();
		return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
	}


	@Override
	public void deleteAccount(Long id) {
		Account account=accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account is not exist"));
		accountRepository.deleteById(id);
	}

}
