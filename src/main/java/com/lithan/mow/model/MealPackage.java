package com.lithan.mow.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lithan.mow.payload.request.MealPackageRequest;

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
   private String mainCourse;

   private String salad;

   private String soup;

   private String dessert;

   private String drink;

   private boolean frozen;

   private boolean active;

   @Column(name = "package_image")
   private String packageImage;

   public MealPackage(MealPackageRequest meal) {
      this.id = meal.getId();
      this.packageName = meal.getPackageName();
      this.mainCourse = meal.getMainCourse();
      this.salad = meal.getSalad();
      this.soup = meal.getSoup();
      this.dessert = meal.getDessert();
      this.drink = meal.getDrink();
      this.frozen = meal.isFrozen();
      this.packageImage = meal.getPackageImage();
   }

   public MealPackage() {
   }

}
