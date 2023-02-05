package com.lithan.mow.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lithan.mow.model.Feedback;
import com.lithan.mow.model.MealPackage;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.payload.request.FeedbackRequest;
import com.lithan.mow.payload.request.MealPackageRequest;
import com.lithan.mow.payload.response.CustomerResponse;
import com.lithan.mow.payload.response.MessageResponse;
import com.lithan.mow.repository.FeedbackRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.PartnerRepository;
import com.lithan.mow.service.CustomerService;

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

    @Autowired
    PartnerRepository partnerRepository;

    @GetMapping("/user/me")
    public CustomerResponse getProfile() {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
       
        if (partnerRepository.findByEmail(currentUserEmail).isPresent()) {
            Partner x = customerService.getCurrentPartner();
            new CustomerResponse();
            return CustomerResponse.builder().id(x.getId()).name(x.getName()).imageUrl(x.getImageUrl())
                    .address(x.getAddress()).email(x.getEmail()).status(x.getStatus()).role(ERole.ROLE_PARTNER).build();
        }
        return new CustomerResponse(customerService.getCurrentUser());
    }

    @GetMapping("/mealcount")
    public List<MealPackage> postFeedback() {

         List<MealPackage> allMeals = mealPackageRepository.findAll();

        return allMeals;
    }

    @PostMapping("/feedback")
    public MessageResponse postFeedback(@Valid @RequestBody FeedbackRequest feedbackRequest) {

        Feedback feedback = new Feedback();
        feedback.setName(feedbackRequest.getName());
        feedback.setEmail(feedbackRequest.getEmail());
        feedback.setMealPackageId(feedbackRequest.getMealPackageId());
        feedback.setFeedback(feedbackRequest.getFeedback());

        feedbackRepository.save(feedback);

        return new MessageResponse("thank for you feedback");
    }

    @GetMapping("/menu")
    public List<MealPackageRequest> getAllMenu() {
        // get day name
        Format f = new SimpleDateFormat("EEEE");
        String today = f.format(new Date());

        List<MealPackageRequest> mealPackageList = new ArrayList<>();

        // return frozen meal on weekend
        if (today.equalsIgnoreCase("monday") || today.equalsIgnoreCase("saturday")) {

            mealPackageRepository.findByFrozen(true).forEach(data -> mealPackageList.add(new MealPackageRequest(data)));

            return mealPackageList;
        }
        mealPackageRepository.findByFrozen(false).forEach(data -> mealPackageList.add(new MealPackageRequest(data)));

        return mealPackageList;
    }

    @GetMapping("menu/{id}")
    public MealPackageRequest getMenu(@PathVariable Long id) {

        return new MealPackageRequest(mealPackageRepository.findById(id).get());
    }

}
