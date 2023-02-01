package com.lithan.mow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lithan.mow.model.ERole;
import com.lithan.mow.model.EStatus;
import com.lithan.mow.model.Role;
import com.lithan.mow.model.Status;
import com.lithan.mow.repository.CustomerRepository;
import com.lithan.mow.repository.OrderRepository;
import com.lithan.mow.repository.RoleRepository;
import com.lithan.mow.repository.StatusRepository;

@SpringBootApplication
public class MealsOnWheelsBackEndApplication {

	public static void main(String[] args) {
		SpringApplication.run(MealsOnWheelsBackEndApplication.class, args);
	}

	@Bean
	CommandLineRunner initRunner(
			RoleRepository roleRepository,
			CustomerRepository customerRepository,
			PasswordEncoder passwordEncoder,
			OrderRepository orderRepository,
			StatusRepository statusRepository) {
		return args -> {

			if (roleRepository.count() < 2) {
				List<Role> roles = new ArrayList<>();
				roles.add(new Role(ERole.ROLE_ADMIN));
				roles.add(new Role(ERole.ROLE_CAREGIVER));
				roles.add(new Role(ERole.ROLE_MEMBER));
				roles.add(new Role(ERole.ROLE_RAIDER));
				roles.add(new Role(ERole.ROLE_VOLUNTEER));

				roleRepository.saveAll(roles);

			}

			if (statusRepository.count() < 2) {
				List<Status> status = new ArrayList<>();
				status.add(new Status(EStatus.AVAILABLE));
				status.add(new Status(EStatus.BUSY));
				status.add(new Status(EStatus.COMPLATE));
				status.add(new Status(EStatus.NOT_AVAILABLE));
				status.add(new Status(EStatus.ON_THE_WAY));
				status.add(new Status(EStatus.PENDING));
				status.add(new Status(EStatus.PREPARING));

				statusRepository.saveAll(status);
			}
		};
	}

}
