package com.bookMyStay.controller;

import com.bookMyStay.Entity.Admin;
import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.Hotel;
import com.bookMyStay.repository.AdminDatabase;
import com.bookMyStay.repository.CustomerDatabase;
import com.bookMyStay.repository.HotelDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staywell")
public class LoginController {
    @Autowired
    private AdminDatabase adminDatabase;

    @Autowired
    private CustomerDatabase customerDatabase;

    @Autowired
    private HotelDatabase hotelDatabase;

    @PostMapping("/admins/login")
    public ResponseEntity<Admin> getLoggedInAdminDetailsHandler(Authentication auth) {
        Admin admin = adminDatabase.findByEmail(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
        return new ResponseEntity<>(admin, HttpStatus.ACCEPTED);
    }

    @PostMapping("/customers/login")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(Authentication auth) {
        Customer customer = customerDatabase.findByEmail(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
        return new ResponseEntity<>(customer, HttpStatus.ACCEPTED);
    }

    @PostMapping("/hotels/login")
    public ResponseEntity<Hotel> getLoggedInHotelDetailsHandler(Authentication auth) {
        Hotel hotel = hotelDatabase.findByHotelEmail(auth.getName())
                .orElseThrow(() -> new BadCredentialsException("Invalid Username or password"));
        return new ResponseEntity<>(hotel, HttpStatus.ACCEPTED);
    }
    
}    
