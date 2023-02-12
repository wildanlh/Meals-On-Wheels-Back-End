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
			partner.setName("Wildan Company");
			partner.setAddress("java, indonesia");
			partner.setEmail("wildan@gmail.com");
			// partner.setRole(ERole.ROLE_PARTNER);
			partner.setActive(true);
			partner.setPassword(passwordEncoder.encode("qwerty"));
			partner.setStatus(EStatus.AVAILABLE);
			partner.setImageUrl("http://localhost:8080/api/file/downloadFile/avatar.jpeg");

			partnerRepository.save(partner);

			Customer member = new Customer();
			member.setName("bagus");
			member.setAddress("bali, indonesia");
			member.setGender(EGender.MALE);
			member.setRole(ERole.ROLE_MEMBER);
			member.setEmail("bagus@gmail.com");
			member.setActive(true);
			member.setPassword(passwordEncoder.encode("qwerty"));
			member.setStatus(EStatus.AVAILABLE);
			member.setImageUrl("http://localhost:8080/api/file/downloadFile/avatar.jpeg");
			member.setFileUrl("http://localhost:8080/api/file/downloadFile/certificate.jpeg");

			Customer rider = new Customer();
			rider.setName("stefansim");
			rider.setAddress("bali, indonesia");
			rider.setGender(EGender.MALE);
			rider.setRole(ERole.ROLE_RIDER);
			rider.setEmail("stefansim@gmail.com");
			rider.setActive(true);
			rider.setPassword(passwordEncoder.encode("qwerty"));
			rider.setStatus(EStatus.AVAILABLE);
			rider.setImageUrl("http://localhost:8080/api/file/downloadFile/avatar.jpeg");
			rider.setFileUrl("http://localhost:8080/api/file/downloadFile/certificate.jpeg");

			Customer volunteer = new Customer();
			volunteer.setName("norman");
			volunteer.setAddress("kualalumpur, malaysia");
			volunteer.setGender(EGender.MALE);
			volunteer.setRole(ERole.ROLE_VOLUNTEER);
			volunteer.setEmail("norman@gmail.com");
			volunteer.setActive(true);
			volunteer.setPassword(passwordEncoder.encode("qwerty"));
			volunteer.setStatus(EStatus.AVAILABLE);
			volunteer.setImageUrl("http://localhost:8080/api/file/downloadFile/avatar.jpeg");
			volunteer.setFileUrl("http://localhost:8080/api/file/downloadFile/certificate.jpeg");

			Customer admin = new Customer();
			admin.setName("admin");
			admin.setAddress("singapore");
			admin.setGender(EGender.MALE);
			admin.setRole(ERole.ROLE_ADMIN);
			admin.setEmail("admin@gmail.com");
			admin.setActive(true);
			admin.setPassword(passwordEncoder.encode("qwerty"));
			admin.setStatus(EStatus.AVAILABLE);
			admin.setImageUrl("http://localhost:8080/api/file/downloadFile/avatar.jpeg");
			admin.setFileUrl("http://localhost:8080/api/file/downloadFile/certificate.jpeg");

			customerRepository.saveAll(Arrays.asList(member, rider, volunteer, admin));

			MealPackage packageA = new MealPackage();
			packageA.setDessert("Baked Apple");
			packageA.setDrink("Peppermint Tea");
			packageA.setMainCourse("Vegetable Plate");
			packageA.setPackageName("Meal Package 01");
			packageA.setSalad("Fruit Salad");
			packageA.setSoup("Mushroom Soup");
			packageA.setActive(true);
			packageA.setPackageImage("http://localhost:8080/api/file/downloadFile/VegetablePlate.jpeg");

			MealPackage packageB = new MealPackage();
			packageB.setDessert("Stewed Fresh Fruits");
			packageB.setDrink("Acidophilus Milk");
			packageB.setMainCourse("Baked Fish");
			packageB.setPackageName("Meal Package 02");
			packageB.setSalad("Grilled Halloumi");
			packageB.setSoup("Creamy Carrot Soup");
			packageB.setActive(true);
			packageB.setPackageImage("http://localhost:8080/api/file/downloadFile/Baked%20Fish.jpeg");

			MealPackage packageC = new MealPackage();
			packageC.setDessert("Mousse Dome");
			packageC.setDrink("Lavendar Tea");
			packageC.setMainCourse("Japanese Curry Udon");
			packageC.setPackageName("Meal Package 03");
			packageC.setSalad("Mushroom Salad");
			packageC.setSoup("Boiled Maca Soup");
			packageC.setFrozen(true);
			packageC.setActive(true);
			packageC.setPackageImage("http://localhost:8080/api/file/downloadFile/Japanese%20Curry%20Udon.jpeg");

			MealPackage packageD = new MealPackage();
			packageD.setDessert("Tiramisu");
			packageD.setDrink("Carrot Juice");
			packageD.setMainCourse("Calzone Pizza");
			packageD.setPackageName("Frozen Package 01");
			packageD.setSalad("Green Salad");
			packageD.setSoup("Miso Soup");
			packageD.setFrozen(true);
			packageD.setActive(true);
			packageD.setPackageImage("http://localhost:8080/api/file/downloadFile/Calzone%20Pizza.jpeg");

			MealPackage packageE = new MealPackage();
			packageE.setDessert("Tiramisu");
			packageE.setDrink("Orange Juice");
			packageE.setMainCourse("Tempura with Red Rice Roll");
			packageE.setPackageName("Frozen Package 02");
			packageE.setSalad("Celery Salad");
			packageE.setSoup("Miso Soup");
			packageE.setFrozen(true);
			packageE.setActive(true);
			packageE.setPackageImage("http://localhost:8080/api/file/downloadFile/Tempura%20with%20Red%20Rice%20Roll.jpeg");

			MealPackage packageF = new MealPackage();
			packageF.setDessert("Assorted Nuts");
			packageF.setDrink("Alvita Tea");
			packageF.setMainCourse("Broiled Lamp Chop");
			packageF.setPackageName("Meal Package 04");
			packageF.setSalad("Rojak");
			packageF.setSoup("5 Elements Soup");
			packageF.setActive(true);
			packageF.setPackageImage("http://localhost:8080/api/file/downloadFile/Broiled%20Lamp%20Chop.jpeg");

			mealPackageRepository.saveAll(Arrays.asList(packageA, packageB, packageC, packageD, packageE, packageF));

			Order pending = new Order();
			pending.setMealPackage(packageA);
			pending.setOrderdBy(member);
			pending.setOrderdOn(new Date());
			pending.setStatus(EStatus.PENDING);

			Order pending2 = new Order();
			pending2.setMealPackage(packageA);
			pending2.setOrderdBy(member);
			pending2.setOrderdOn(new Date());
			pending2.setPreparedBy(partner);
			pending2.setStatus(EStatus.PENDING);

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

			Order readyToDeliver2 = new Order();
			readyToDeliver2.setMealPackage(packageC);
			readyToDeliver2.setDeliveredBy(rider);
			readyToDeliver2.setOrderdBy(member);
			readyToDeliver2.setOrderdOn(new Date());
			readyToDeliver2.setPreparedBy(partner);
			readyToDeliver2.setStatus(EStatus.READY_TO_DELIVER);

			Order onDeliver = new Order();
			onDeliver.setDeliveredBy(rider);
			onDeliver.setMealPackage(packageA);
			onDeliver.setOrderdBy(member);
			onDeliver.setOrderdOn(new Date());
			onDeliver.setPreparedBy(partner);
			onDeliver.setStatus(EStatus.ON_DELIVERY);

			Order deliverComplate = new Order();
			deliverComplate.setDeliveredBy(rider);
			deliverComplate.setMealPackage(packageB);
			deliverComplate.setOrderdBy(member);
			deliverComplate.setOrderdOn(new Date());
			deliverComplate.setPreparedBy(partner);
			deliverComplate.setStatus(EStatus.DELIVERY_COMPLETE);

			Order order = new Order();
			order.setDeliveredBy(rider);
			order.setMealPackage(packageC);
			order.setOrderdBy(member);
			order.setOrderdOn(new Date());
			order.setPreparedBy(partner);
			order.setStatus(EStatus.DELIVERY_COMPLETE);

			orderRepository.saveAll(Arrays.asList(pending, pending2, prepare, readyToDeliver, readyToDeliver2, onDeliver,
					deliverComplate, order));

		};
	}

}
