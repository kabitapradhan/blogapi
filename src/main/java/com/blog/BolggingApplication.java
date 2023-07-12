package com.blog;

import org.modelmapper.ModelMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.configuration.AppConstant;
import com.blog.entity.Role;
import com.blog.repository.ReloRepository;



@SpringBootApplication
public class BolggingApplication implements CommandLineRunner {
	
	@Autowired
	private ReloRepository roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BolggingApplication.class, args);
	}
	
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("This is main:!!!!!!!!!");
		try {
			Role role1 = new Role();
			
			role1.setId(AppConstant.ADMIN_USER);
			role1.setName("ROLE_ADMIN");
			
			Role role2 = new Role();
			role2.setId(AppConstant.NORMAL_USER);
			role2.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role1 , role2);
				
			
			List<Role> saveAll = this.roleRepo.saveAll(roles);
			saveAll.forEach(rol -> System.out.println(rol.getName() + " & " + rol.getId()));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
