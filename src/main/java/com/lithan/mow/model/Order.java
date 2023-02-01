package com.lithan.mow.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_order")
public class Order {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne
   @JoinColumn(name = "orderd_by")
   private Customer orderdBy;

   @ManyToOne
   @JoinColumn(name = "prepared_by")
   private Customer preparedBy;

   @ManyToOne
   @JoinColumn(name = "delivered_by")
   private Customer deliveredBy;

   @OneToOne
   private Status status;

   @Column(name = "orderd_on")
   private Date orderdOn;
}
