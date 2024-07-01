package com.gr1.springboot.mvc.studentmanagement.dao;

import com.gr1.springboot.mvc.studentmanagement.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByUserNameAndPassword(String username, String password);

    Optional<Account> findByUserName(String username);
}
