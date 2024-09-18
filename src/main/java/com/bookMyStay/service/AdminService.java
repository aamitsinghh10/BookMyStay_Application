package com.bookMyStay.service;


import com.bookMyStay.Entity.Admin;
import com.bookMyStay.exception.AdminException;

public interface AdminService {
	public Admin registerAdmin(Admin admin) throws AdminException;
	
}
