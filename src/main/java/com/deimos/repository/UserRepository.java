package com.deimos.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deimos.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	@Query(value = "select user from users user where user.username = ?1")
	Optional<Users> findByUsername(String username);
	
	@Query(value = "select user from users user where user.email = ?1")
	Optional<Users> findByEmail(String email);
	
	@Query(value = "select user from users user where user.token = ?1")
	Optional<Users> findByResetToken(String token);
}
