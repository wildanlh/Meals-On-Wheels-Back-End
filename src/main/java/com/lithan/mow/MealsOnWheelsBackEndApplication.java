package com.lithan.mow;

import java.util.Arrays;
import java.util.Date;
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

			Partner partner = new Partner();
			partner.setName("wildan");
			partner.setAddress("java, indonesia");
			partner.setEmail("wildan@gmail.com");
			partner.setActive(true);
			partner.setPassword(passwordEncoder.encode("qwerty"));

			partnerRepository.save(partner);

			Customer member = new Customer();
			member.setName("bagus");
			member.setAddress("bali, indonesia");
			member.setGender(EGender.MALE);
			member.setRole(ERole.ROLE_MEMBER);
			member.setEmail("bagus@gmail.com");
			member.setActive(true);
			member.setPassword(passwordEncoder.encode("qwerty"));

			Customer raider = new Customer();
			raider.setName("stefansim");
			raider.setAddress("bali, indonesia");
			raider.setGender(EGender.MALE);
			raider.setRole(ERole.ROLE_RIDER);
			raider.setEmail("stefansim@gmail.com");
			raider.setActive(true);
			raider.setPassword(passwordEncoder.encode("qwerty"));

			Customer volunteer = new Customer();
			volunteer.setName("norman");
			volunteer.setAddress("kualalumpur, malaysia");
			volunteer.setGender(EGender.MALE);
			volunteer.setRole(ERole.ROLE_VOLUNTEER);
			volunteer.setEmail("norman@gmail.com");
			volunteer.setActive(true);
			volunteer.setPassword(passwordEncoder.encode("qwerty"));

			Customer admin = new Customer();
			admin.setName("admin");
			admin.setAddress("singapore");
			admin.setGender(EGender.MALE);
			admin.setRole(ERole.ROLE_ADMIN);
			admin.setEmail("admin@gmail.com");
			admin.setActive(true);
			admin.setPassword(passwordEncoder.encode("qwerty"));

			customerRepository.saveAll(Arrays.asList(member, raider, volunteer, admin));

			MealPackage packageA = new MealPackage();
			packageA.setDessert("puding");
			packageA.setDrink("cocacola");
			packageA.setMainCourse("fried chiken");
			packageA.setPackageName("Package Ayam");
			packageA.setSalad("kol");
			packageA.setSoup("sayur bening");
			packageA.setActive(true);

			MealPackage packageB = new MealPackage();
			packageB.setDessert("puding");
			packageB.setDrink("cocacola");
			packageB.setMainCourse("fried chiken");
			packageB.setPackageName("Package Babi");
			packageB.setSalad("kol");
			packageB.setSoup("sayur bening");
			packageB.setActive(true);

			MealPackage packageC = new MealPackage();
			packageC.setDessert("puding");
			packageC.setDrink("cocacola");
			packageC.setMainCourse("fried chiken");
			packageC.setPackageName("Package Cetik");
			packageC.setSalad("kol");
			packageC.setSoup("sayur bening");
			packageC.setFrozen(true);
			packageC.setActive(true);

			mealPackageRepository.saveAll(Arrays.asList(packageA, packageB, packageC));

			Order pending = new Order();
			pending.setMealPackage(packageA);
			pending.setOrderdBy(member);
			pending.setOrderdOn(new Date());
			pending.setPreparedBy(partner);
			pending.setStatus(EStatus.PENDING);

			Order prepare = new Order();
			prepare.setMealPackage(packageB);
			prepare.setOrderdBy(member);
			prepare.setOrderdOn(new Date());
			prepare.setPreparedBy(partner);
			prepare.setStatus(EStatus.PREPARING);

			Order readyToDeliver = new Order();
			readyToDeliver.setMealPackage(packageC);
			readyToDeliver.setOrderdBy(member);
			readyToDeliver.setOrderdOn(new Date());
			readyToDeliver.setPreparedBy(partner);
			readyToDeliver.setStatus(EStatus.READY_TO_DELIVER);

			Order onDeliver = new Order();
			onDeliver.setDeliveredBy(raider);
			onDeliver.setMealPackage(packageA);
			onDeliver.setOrderdBy(member);
			onDeliver.setOrderdOn(new Date());
			onDeliver.setPreparedBy(partner);
			onDeliver.setStatus(EStatus.ON_DELIVERY);

			Order deliverComplate = new Order();
			deliverComplate.setDeliveredBy(raider);
			deliverComplate.setMealPackage(packageB);
			deliverComplate.setOrderdBy(member);
			deliverComplate.setOrderdOn(new Date());
			deliverComplate.setPreparedBy(partner);
			deliverComplate.setStatus(EStatus.DELIVERY_COMPLETE);

			Order order = new Order();
			order.setDeliveredBy(raider);
			order.setMealPackage(packageC);
			order.setOrderdBy(member);
			order.setOrderdOn(new Date());
			order.setPreparedBy(partner);
			order.setStatus(EStatus.ORDER_COMPLETE);

			orderRepository.saveAll(Arrays.asList(pending, prepare, readyToDeliver, onDeliver, deliverComplate, order));

		};
	}

}
