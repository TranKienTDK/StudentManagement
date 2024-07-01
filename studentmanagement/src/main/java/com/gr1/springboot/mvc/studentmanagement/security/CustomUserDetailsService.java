package com.gr1.springboot.mvc.studentmanagement.security;

import com.gr1.springboot.mvc.studentmanagement.dao.AccountRepository;
import com.gr1.springboot.mvc.studentmanagement.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Account account = accountRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + userName));

        return new CustomUserDetails(account);
    }
}
