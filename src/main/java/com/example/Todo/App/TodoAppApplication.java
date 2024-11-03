package com.example.Todo.App;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Todo.App.entity.Role;
import com.example.Todo.App.entity.user;
import com.example.Todo.App.repository.UserRepository;

@SpringBootApplication
public class TodoAppApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	public static void main(String[] args) {
		SpringApplication.run(TodoAppApplication.class, args);
	}

		public void run(String... args){
			user adminAccount = userRepository.findByRole(Role.ADMIN);
			if(null==adminAccount){
				user user = new user();

				user.setEmail("admin@gmail.com");
				user.setFirstName("admin");
				user.setLastName("admin");
				user.setRole(Role.ADMIN);
				user.setPassword(new BCryptPasswordEncoder().encode("admin"));
				userRepository.save(user);
			}
		}
	

}
