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

import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_customer", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   private String email;

   private String password;

   private String address;

   @Enumerated(EnumType.STRING)
   private EGender gender;

   @Enumerated(EnumType.STRING)
   private ERole role;

   @Enumerated(EnumType.STRING)
   private EStatus status;

   private String fileUrl;

   @Column(name = "image_url")
   private String imageUrl;

   private boolean active;

}
