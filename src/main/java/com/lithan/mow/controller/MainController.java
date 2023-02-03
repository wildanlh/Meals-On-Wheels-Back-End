package com.lithan.mow.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Feedback;
import com.lithan.mow.model.MealPackage;
import com.lithan.mow.payload.request.FeedbackRequest;
import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.repository.FeedbackRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MainController {

    @Autowired
    CustomerService customerService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    MealPackageRepository mealPackageRepository;

    @GetMapping("/profile/me")
    public CustomerResponse getProfile() {
        return new CustomerResponse(customerService.getCurrentUser());
    }

    @PostMapping("/feadback")
    public MessageResponse postFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {

        Feedback feedback = new Feedback();
        feedback.setEmail(feedbackRequest.getEmail());
        feedback.setName(feedbackRequest.getName());
        feedback.setFeedback(feedbackRequest.getFeedback());
        feedback.setMealPackage(new MealPackage(feedbackRequest.getMealPackage()));

        feedbackRepository.save(feedback);

        return new MessageResponse("thank for you feedback");
    }

    @GetMapping("/menu")
    public List<MealPackageRequest> getAllMenu() {
        List<MealPackageRequest> mealPackageList = new ArrayList<>();
        mealPackageRepository.findAll().forEach(data -> mealPackageList.add(new MealPackageRequest(data)));

        return mealPackageList;
    }

    // todo: add post donate contreoller
}
