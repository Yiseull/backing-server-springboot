package com.numble.backingserver.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@DynamicInsert
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;
    @Column(name = "account_number", nullable = false)
    private String accountNumber;
    @Column(nullable = false)
    private int pin;
    @Column(nullable = false)
    private int balance;
    @Column(name = "user_id", nullable = false)
    private int userId;

}