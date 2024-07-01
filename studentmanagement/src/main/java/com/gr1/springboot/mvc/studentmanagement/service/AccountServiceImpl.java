package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.dao.AccountRepository;
import com.gr1.springboot.mvc.studentmanagement.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository theAccountRepository) {
        accountRepository = theAccountRepository;
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account findById(Long theId) {

        Optional<Account> result = accountRepository.findById(theId);

        Account theAccount = null;
        if (result.isPresent()) {
            theAccount = result.get();
        }
        else {
            throw new RuntimeException("Did not found account id - " + theId);
        }
        return theAccount;
    }

    @Override
    public Account save(Account theAccount) {
        return accountRepository.save(theAccount);
    }

    @Override
    public Optional<Account> authenticate(String userName, String password) {
        return accountRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public Optional<Account> findByUserName(String userName) {
        return accountRepository.findByUserName(userName);
    }
}
