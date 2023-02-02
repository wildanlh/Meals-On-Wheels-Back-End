package com.lithan.mow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lithan.mow.model.Customer;
import com.lithan.mow.model.MealPackage;
import com.lithan.mow.model.Order;
import com.lithan.mow.model.Partner;
import com.lithan.mow.model.constraint.EGender;
import com.lithan.mow.model.constraint.ERole;
import com.lithan.mow.model.constraint.EStatus;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.MealPackageRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.repository.PartnerRepository;

@SpringBootApplication
public class MealsOnWheelsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealsOnWheelsBackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner initRunner(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
			OrderRepository orderRepository, MealPackageRepository mealPackageRepository,
			PartnerRepository partnerRepository) {
		return args -> {
			System.out.println("start the app inti the mock data");

			if (customerRepository.count() > 1)
				return;

			List<Customer> customerList = new ArrayList<>();

			Partner partner = new Partner();
			partner.setName("wildan");
			partner.setAddress("java, indonesia");
			partner.setEmail("wildan@gmail.com");
			partner.setPassword(passwordEncoder.encode("qwerty"));

			partnerRepository.save(partner);

			Customer member = new Customer();
			member.setName("bagus");
			member.setAddress("bali, indonesia");
			member.setGender(EGender.MALE);
			member.setRole(ERole.ROLE_MEMBER);
			member.setEmail("bagus@gmail.com");
			member.setPassword(passwordEncoder.encode("qwerty"));

			Customer raider = new Customer();
			raider.setName("stefansim");
			raider.setAddress("bali, indonesia");
			raider.setGender(EGender.MALE);
			raider.setRole(ERole.ROLE_RIDER);
			raider.setEmail("stefansim@gmail.com");
			raider.setPassword(passwordEncoder.encode("qwerty"));

			Customer volunteer = new Customer();
			volunteer.setName("norman");
			volunteer.setAddress("kualalumpur, malaysia");
			volunteer.setGender(EGender.MALE);
			volunteer.setRole(ERole.ROLE_VOLUNTEER);
			volunteer.setEmail("norman@gmail.com");
			volunteer.setPassword(passwordEncoder.encode("qwerty"));

			customerList.addAll(Arrays.asList(member, raider, volunteer));

			customerRepository.saveAll(customerList);

			MealPackage packageA = new MealPackage();
			packageA.setDessert("puding");
			packageA.setDrink("cocacola");
			packageA.setMainCourse("fried chiken");
			packageA.setPackageName("Package A");
			packageA.setSalad("kol");
			packageA.setSoup("sayur bening");

			mealPackageRepository.save(packageA);

			Order order = new Order();
			order.setDeliveredBy(raider);
			order.setMealPackage(packageA);
			order.setOrderdBy(member);
			order.setOrderdOn(new Date());
			order.setPreparedBy(partner);
			order.setStatus(EStatus.COMPLETE);

			orderRepository.save(order);

		};
	}

}
