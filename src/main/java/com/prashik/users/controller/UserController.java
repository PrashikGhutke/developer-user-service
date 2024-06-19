package com.prashik.users.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prashik.users.exceptions.ResourceNotFoundException;
import com.prashik.users.model.User;
import com.prashik.users.services.UserService;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserService userService;
	
	@PostMapping("/save-user")
	public ResponseEntity<User> saveUser(@RequestBody User user)
	{
		LOGGER.info("Called SaveUSer Details Method");
		User savedUser = userService.saveUser(user);
		LOGGER.info("SAVED SUCCESSFULLY Details : {}",savedUser);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser); 
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllUsers()
	{
		LOGGER.info("Get ALL USER DEATILS");
		List<User> users = userService.getAllUsers();
		LOGGER.debug("USER LIST : {}",users);
		return ResponseEntity.ok(users);
	}
	
	
	@GetMapping("/user/{userId}")
//	@CircuitBreaker(name = "ratingHotelCircuitBreaker", fallbackMethod = "ratingHotelFallbackMethod")
//	@Retry(name = "ratingHotelRetry", fallbackMethod = "ratingHotelFallbackMethod")
	@RateLimiter(name = "ratingHotelRateLimiter", fallbackMethod = "ratingHotelFallbackMethod")
	public ResponseEntity<User> getUser(@PathVariable String userId) throws ResourceNotFoundException
	{
		User user = userService.getUser(userId);
		return ResponseEntity.ok(user);
	}
	
	public ResponseEntity<User> ratingHotelFallbackMethod(String userId, Exception ex)
	{
		User dummyUser = new User();
		dummyUser.setName("DUMMY USER");
		dummyUser.setAbout("USER SERVICE IS DOWN");
		dummyUser.setEmail("ghutke@gmail.com");
		return new ResponseEntity<User>(dummyUser,HttpStatus.FAILED_DEPENDENCY);
	}
}
