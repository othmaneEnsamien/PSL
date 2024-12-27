package com.PSL.PSL;

import com.PSL.PSL.grossiste.Grossiste;
import com.PSL.PSL.grossiste.GrossisteRepository;
import com.PSL.PSL.role.Role;
import com.PSL.PSL.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class PslApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PslApiApplication.class, args);
	}


	@Bean
	public CommandLineRunner runner(RoleRepository roleRepository, GrossisteRepository grossisteRepository) {
		return args -> {
			// Vérifier et créer le rôle "ROLE_ADMIN" si nécessaire
			Role grossisteRole = roleRepository.findByName("ROLE_LIVREUR")
					.orElseGet(() -> roleRepository.save(Role.builder().name("ROLE_LIVREUR").build()));

			if (grossisteRepository.findByEmail("livreurr@gmail.com").isEmpty()) {
				Grossiste grossiste = Grossiste.builder()
						.nom("admin1")
						.email("livreurr@gmail.com")
						.password(new BCryptPasswordEncoder().encode("Othmane-2001"))
						.roles(List.of(grossisteRole))
						.build();
				grossisteRepository.save(grossiste);
			}
		};
	}

//		@Bean
//	public CommandLineRunner runner(RoleRepository roleRepository) {
//		return args -> {
//			if (roleRepository.findByName("ROLE_LIVREUR").isEmpty()) {
//				roleRepository.save(Role.builder().name("ROLE_LIVREUR").build());
//			}
//		};
//	}

}
