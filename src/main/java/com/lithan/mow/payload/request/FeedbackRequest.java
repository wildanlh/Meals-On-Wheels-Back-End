package com.lithan.mow.payload.request;

import javax.validation.constraints.Email;

import com.lithan.mow.model.Feedback;

import lombok.Data;

@Data
public class FeedbackRequest {

    private long id;
     
    private String name;

    @Email
    private String email;

    private MealPackageRequest mealPackage;

    private String feedback;

    public FeedbackRequest(Feedback feedback) {
        this.id = feedback.getId();
        this.name = feedback.getName();
        this.email = feedback.getEmail();
        this.mealPackage = new MealPackageRequest(feedback.getMealPackage());
        this.feedback = feedback.getFeedback();
    }

    
}
