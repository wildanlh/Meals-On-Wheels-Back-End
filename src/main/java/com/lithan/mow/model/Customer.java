package com.lithan.mow.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_customer", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String email;

   private String password;

   private String address;

   @ManyToMany
   @JoinTable(joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
         "customer_id", "role_id" }))
   private List<Role> roles = new ArrayList<>();

   @ManyToMany
   @JoinTable(joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "status_id"), uniqueConstraints = @UniqueConstraint(columnNames = {
         "customer_id", "status_id" }))
   private List<Status> status = new ArrayList<>();

}
