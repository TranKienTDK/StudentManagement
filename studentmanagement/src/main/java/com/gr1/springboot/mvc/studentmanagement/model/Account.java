package com.gr1.springboot.mvc.studentmanagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {

    // Add field to class
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    // Add constructor to class
    public Account() {

    }

    public Account(Long accountId, String userName, String password, String role) {
        this.accountId = accountId;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    // Add getter/setter to class

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
