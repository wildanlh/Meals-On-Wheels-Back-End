package com.lithan.mow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_meal_package")
public class MealPackage {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "package_name")
   private String packageName;

   @Column(name = "main_course")
   private String mainCaurse;

   private String salad;

   private String soup;

   private String dessert;

   private String drink;

   @Column(name = "package_image")
   private String packageImage;
}
