package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class FeedbackRequest {
     
    private String name;

    @Email
    private String email;

    private MealPackageRequest mealPackage;

    private String feedback;
}
