package com.bookMyStay.repository;

import com.bookMyStay.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminDatabase extends JpaRepository<Admin,Integer>{
	Optional<Admin> findByEmail(String email);
}
