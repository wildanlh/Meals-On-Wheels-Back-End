package com.lithan.mow.controller;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
import com.lithan.mow.service.FileStorageService;

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

    @Autowired
    private FileStorageService fileStorageService;

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
        if (today.equalsIgnoreCase("sunday") || today.equalsIgnoreCase("saturday")) {

            mealPackageRepository.findByFrozenAndActive(true, true).forEach(data -> mealPackageList.add(new MealPackageRequest(data)));

            return mealPackageList;
        }
        mealPackageRepository.findByFrozenAndActive(false,true).forEach(data -> mealPackageList.add(new MealPackageRequest(data)));

        return mealPackageList;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_PARTNER')")
    @PostMapping("menu/add")
    public MessageResponse addMenu(@RequestParam("packageName") String packageName,
    @RequestParam("mainCourse") String mainCourse, @RequestParam("salad") String salad, 
    @RequestParam("soup") String soup,
    @RequestParam("dessert") String dessert, @RequestParam("drink") String drink,
    @RequestParam("frozen") String frozen, @RequestParam("packageImage") MultipartFile packageImage) {
      
        boolean frozenBool = false;

        if (frozen.equals("1")) {
            frozenBool = true;
        }

        String imageName = fileStorageService.storeFile(packageImage);
        String imageDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/file/downloadFile/")
            .path(imageName).toUriString();

        MealPackage meal = new MealPackage();
        meal.setPackageName(packageName);
        meal.setMainCourse(mainCourse);
        meal.setSalad(salad);
        meal.setSoup(soup);
        meal.setDessert(dessert);
        meal.setDrink(drink);
        meal.setFrozen(frozenBool);
        meal.setPackageImage(imageDownloadUri);
        meal.setActive(true);
        mealPackageRepository.save(meal);

        return new MessageResponse("success add menu with name: "+ meal.getPackageName());
    }

    @GetMapping("menu/{id}")
    public MealPackageRequest getMenu(@PathVariable Long id) {

        return new MealPackageRequest(mealPackageRepository.findById(id).get());
    }

}
