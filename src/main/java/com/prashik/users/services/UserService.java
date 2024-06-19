package com.prashik.users.services;

import java.util.List;

import com.prashik.users.exceptions.ResourceNotFoundException;
import com.prashik.users.model.User;

public interface UserService {
	
	User saveUser(User user);
	
	List<User> getAllUsers();
	
	User getUser(String userId) throws ResourceNotFoundException;
	
	//TODO: update
	//TODO: delete
	

}
