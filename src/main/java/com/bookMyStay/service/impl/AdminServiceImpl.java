package com.bookMyStay.service.impl;

import com.bookMyStay.Entity.Admin;
import com.bookMyStay.Enums.Role;
import com.bookMyStay.exception.AdminException;
import com.bookMyStay.repository.AdminDatabase;
import com.bookMyStay.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

	private AdminDatabase adminDatabase;
	private PasswordEncoder passwordEncoder;

	public Admin registerAdmin(Admin admin) throws AdminException {
		Optional<Admin> adminExist = adminDatabase.findByEmail(admin.getEmail());

		if (adminExist.isPresent()) {
			throw new AdminException("Admin already registered with this email!");
		}

		String hashedPassword = passwordEncoder.encode(admin.getPassword());
		admin.setPassword(hashedPassword);
		admin.setRole(Role.ROLE_ADMIN);

		return adminDatabase.save(admin);
	}

}
