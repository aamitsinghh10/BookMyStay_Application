package com.bookMyStay.service;

import com.bookMyStay.Entity.Admin;
import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.Hotel;
import com.bookMyStay.repository.AdminDatabase;
import com.bookMyStay.repository.CustomerDatabase;
import com.bookMyStay.repository.HotelDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomDetailsService implements UserDetailsService {

	@Autowired
	private CustomerDatabase customerDatabase;

	@Autowired
	private AdminDatabase adminDatabase;

	@Autowired
	private HotelDatabase hotelDatabase;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> customerOptional = customerDatabase.findByEmail(username);

		Optional<Admin> adminOptional = adminDatabase.findByEmail(username);

		Optional<Hotel> hotelOptional = hotelDatabase.findByHotelEmail(username);

		if (!customerOptional.isPresent() && !adminOptional.isPresent() && !hotelOptional.isPresent()) {
			throw new UsernameNotFoundException("User with email " + username + " not found");
		}

		List<GrantedAuthority> authorities = new ArrayList<>();

		if (customerOptional.isPresent()) {
			Customer customer = customerOptional.get();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(customer.getRole().toString());
			authorities.add(sga);
			return new User(customer.getEmail(), customer.getPassword(), authorities);

		} else if (adminOptional.isPresent()) {
			Admin admin = adminOptional.get();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(admin.getRole().toString());
			authorities.add(sga);
			return new User(admin.getEmail(), admin.getPassword(), authorities);
		} else {
			Hotel hotel = hotelOptional.get();
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(hotel.getRole().toString());
			authorities.add(sga);
			return new User(hotel.getHotelEmail(), hotel.getPassword(), authorities);
		}
	}
}
