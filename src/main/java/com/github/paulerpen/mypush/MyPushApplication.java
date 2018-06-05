package com.github.paulerpen.mypush;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.github.paulerpen.mypush.user.User;
import com.github.paulerpen.mypush.user.UserRepository;

@SpringBootApplication
public class MyPushApplication {
	
	@Autowired
	private UserRepository repository;
	
    public static void main(String[] args) throws Throwable {
        SpringApplication.run(MyPushApplication.class, args);
    }
    /*
     * Gives out all users in the database on startup.
     */
    @PostConstruct
    public void setup() {
		// fetch all user
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for (User user : repository.findAll()) {
			System.out.println(user.toString());
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(11);
			String result = encoder.encode(user.getPassword());
			System.out.println(result);
		}
    }

}
