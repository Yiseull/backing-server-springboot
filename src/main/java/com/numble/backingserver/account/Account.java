package com.numble.backingserver.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(nullable = false)
    private String pin;
    private int balance;
    @Column(name = "user_id", nullable = false)
    private int userId;

    @PrePersist
    public void prePersist() {
        this.accountNumber = this.accountNumber == null ? "1606-11-" : this.accountNumber;
    }
}