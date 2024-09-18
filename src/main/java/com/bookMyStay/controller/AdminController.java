package com.bookMyStay.controller;

import com.bookMyStay.Entity.Admin;
import com.bookMyStay.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookMyStay/admins")
@AllArgsConstructor
public class AdminController {

	private AdminService adminService;
	
	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin){
		
		Admin res = adminService.registerAdmin(admin);
		
		return new ResponseEntity<Admin>(res,HttpStatus.CREATED);
	}
}
