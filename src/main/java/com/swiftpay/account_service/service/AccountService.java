package com.swiftpay.account_service.service;

import com.swiftpay.account_service.dto.AccountDto;
import com.swiftpay.account_service.exception.ResourseNotFoundException;
import com.swiftpay.account_service.feignclient.UserClient;
import com.swiftpay.account_service.model.Account;
import com.swiftpay.account_service.repository.AccountRepository;
import com.swiftpay.account_service.service.util.AccountIdGenerator;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final ModelMapper mapper;
    private final AccountRepository accountRepository;
    private final UserClient userClient;

    @Autowired
    public AccountService(ModelMapper mapper, AccountRepository accountRepository,
                          UserClient userClient) {
        this.mapper = mapper;
        this.accountRepository = accountRepository;
        this.userClient = userClient;
    }


    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        try{
            Account account = mapper.map(accountDto, Account.class);
            account.setAccountId(AccountIdGenerator.generateAccountId());// Generate unique ID
            account.setUserId(userClient.getUser(accountDto.getUserId()).getEmail());
            return mapper.map(accountRepository.save(account), AccountDto.class);
        } catch (Exception e) {
            throw new RuntimeException("something went wrong");
        }

    }

    public List<AccountDto> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    public List<AccountDto> getUserAccounts(String userId) {
        return accountRepository.findByUserId(userId)
                .stream()
                .map(account -> mapper.map(account, AccountDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void closeAccount(String accountId) {
        Account account = accountRepository.findByAccountId(accountId);
        if (account == null) {
            throw new ResourseNotFoundException("Account not found with ID: " + accountId);
        }
        accountRepository.delete(account);
    }

    @Transactional
    public void updateBalance(String accountId, Double amount) {
        Account account = accountRepository.findByAccountId(accountId);
        if (account == null) {
            throw new ResourseNotFoundException("Account not found with ID: " + accountId);
        }
        account.setBalance(amount);
        accountRepository.save(account);
    }
}
