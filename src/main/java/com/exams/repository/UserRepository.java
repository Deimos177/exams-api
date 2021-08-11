package com.exams.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.exams.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select user from users user where user.username = ?1")
	Optional<User> findByUsername(String username);
	
	@Query(value = "select user from users user where user.email = ?1")
	Optional<User> findByEmail(String email);
	
	@Query(value = "select user from users user where user.token = ?1")
	Optional<User> findByResetToken(String token);
}
