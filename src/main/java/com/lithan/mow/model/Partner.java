package com.lithan.mow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_partner", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private String email;

    private String password;

    // @Enumerated(EnumType.STRING)
    // private ERole role;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Column(name = "image_url")
    private String imageUrl;

    private boolean active;

}
