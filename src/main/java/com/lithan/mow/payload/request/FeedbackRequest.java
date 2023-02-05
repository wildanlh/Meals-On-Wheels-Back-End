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

    private int mealPackageId;

    private String feedback;

    public FeedbackRequest() {

    }

    public FeedbackRequest(Feedback feedback) {
        this.id = feedback.getId();
        this.name = feedback.getName();
        this.email = feedback.getEmail();
        this.mealPackageId = feedback.getMealPackageId();
        this.feedback = feedback.getFeedback();
    }

    
}
