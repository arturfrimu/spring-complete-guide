package com.arturfrimu.spring.complete.guide.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "ACCOUNT")
@Getter
@Setter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    private String accountName;

    public Account(String accountName) {
        this.accountName = Objects.requireNonNull(accountName, "Account name can't be null");
    }

    public Account(long id, String accountName) {
        this.id = Objects.requireNonNull(id, "Id can't be null");
        this.accountName = Objects.requireNonNull(accountName, "Account name can't be null");
    }
}