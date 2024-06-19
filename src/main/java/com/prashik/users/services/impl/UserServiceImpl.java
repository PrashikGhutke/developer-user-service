package com.prashik.users.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prashik.users.exceptions.ResourceNotFoundException;
import com.prashik.users.model.Hotel;
import com.prashik.users.model.Rating;
import com.prashik.users.model.User;
import com.prashik.users.repository.UserRepository;
import com.prashik.users.services.UserService;
import com.prashik.users.services.external.services.HotelFeignClient;
import com.prashik.users.services.external.services.RatingFeignClient;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	HotelFeignClient hotelFeignClient;
	
	@Autowired
	RatingFeignClient ratingFeignClient;
	
	@Override
	public User saveUser(User user) {
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);
		 User savedUser = userRepository.save(user);
		 List<Rating> ratings = user.getRatings();
		 for(Rating rating : ratings)
		 {
			 rating.setUserId(userId);
			 ResponseEntity<Hotel> createHotel = hotelFeignClient.createHotel(rating.getHotel());
			 Hotel hotel = createHotel.getBody();
			 rating.setHotelId(hotel.getId());
			 ratingFeignClient.createRating(rating);
			// hotelFeignClient.createHotel(rating.getHotel());
		 }
		 return savedUser;
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) throws ResourceNotFoundException {
		 User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with ID = "+userId+" is Not Found"));
		 ResponseEntity<List<Rating>> ratingsByUserId = ratingFeignClient.getRatingsByUserId(userId);
		 List<Rating> ratings = ratingsByUserId.getBody();
		 for(Rating rating : ratings)
		 {
			 ResponseEntity<Hotel> hotel = hotelFeignClient.getHotel(rating.getHotelId());
			 rating.setHotel(hotel.getBody());
		 }
		 user.setRatings(ratings);
		 return user;
		
	}

}
