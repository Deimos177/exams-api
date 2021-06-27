package com.deimos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.deimos.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	@Query(value = "select u from users u where u.username like ?1")
	Users findByUsername(String username);
}
