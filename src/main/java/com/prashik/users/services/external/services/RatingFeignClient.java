package com.prashik.users.services.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.prashik.users.model.Rating;

@FeignClient(name = "RATING-SERVICE", path = "/rating-details")
public interface RatingFeignClient {
	
	@PostMapping("/ratings/create-ratings")
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating);
	
	@GetMapping("/ratings/get-ratings")
	public ResponseEntity<List<Rating>> getRatings();
	
	@GetMapping("/ratings/get-ratings-by-user-id")
	public ResponseEntity<List<Rating>> getRatingsByUserId(@RequestParam String userId);
	
	@GetMapping("/ratings/get-ratings-by-hotel-id")
	public ResponseEntity<List<Rating>> getRatingsByHotelId(@RequestParam String hotelId);
	
}
