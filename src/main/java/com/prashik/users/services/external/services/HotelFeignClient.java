package com.prashik.users.services.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.prashik.users.model.Hotel;


@FeignClient(name = "HOTEL-SERVICE", path = "/hotel-details")
public interface HotelFeignClient {
	
	@PostMapping("/hotels/save-hotel")
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel);
	
	@GetMapping("/hotels/get-hotel/{id}")
	public ResponseEntity<Hotel> getHotel(@PathVariable String id);
	
	@GetMapping("/hotels/get-hotels")
	public ResponseEntity<List<Hotel>> getHotels();
	
	

}
