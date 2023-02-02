package com.lithan.mow.payload.request;

import com.lithan.mow.model.MealPackage;

import lombok.Data;

@Data
public class MealPackageRequest {

    private Long id;

    private String packageName;

    
    private String salad;

    private String soup;

    private String dessert;

    private String drink;

    private String packageImage;

    public MealPackageRequest(MealPackage meal) {
        this.id = meal.getId();
        this.packageName = meal.getPackageName();
        this.mainCourse = meal.getMainCourse();
        this.salad = meal.getSalad();
        this.soup = meal.getSoup();
        this.dessert = meal.getSoup();
        this.drink = meal.getDrink();
        this.packageImage = meal.getPackageImage();
    }

    public MealPackageRequest() {
    }
}
