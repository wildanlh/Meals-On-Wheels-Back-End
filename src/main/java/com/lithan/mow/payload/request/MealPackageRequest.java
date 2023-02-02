package com.lithan.mow.payload.request;

import lombok.Data;

@Data
public class MealPackageRequest {

    private Long id;
 
    private String packageName;
 
    private String mainCaurse;
 
    private String salad;
 
    private String soup;
 
    private String dessert;
 
    private String drink;
 
    private String packageImage;
}
