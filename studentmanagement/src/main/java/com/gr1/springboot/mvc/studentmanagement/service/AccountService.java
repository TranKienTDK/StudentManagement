package com.gr1.springboot.mvc.studentmanagement.service;

import com.gr1.springboot.mvc.studentmanagement.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();
//
    Account findById(Long theId);
//
    Account save(Account theAccount);
//
//    void deleteById(Long theId);

    Optional<Account> authenticate(String userName, String password);

    Optional<Account> findByUserName(String userName);
}
