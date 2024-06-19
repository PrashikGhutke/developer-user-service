package com.prashik.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prashik.users.model.User;

public interface UserRepository extends JpaRepository<User, String>{

}
