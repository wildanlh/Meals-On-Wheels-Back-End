package com.lithan.mow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_donate")
public class Donate {
    @Id
    @GeneratedValue()
    private Long id;

    private long amount;

    @Column(name = "card_number")
    private long cardNumber;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    @Column(name = "expiration_date")
    private String expirationDate;

    private String cw;

    private String message;

}
