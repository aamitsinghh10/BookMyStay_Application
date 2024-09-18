package com.bookMyStay.controller;

import com.bookMyStay.dto.request.CustomerRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.CustomerResponse;
import com.bookMyStay.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookMyStay/customers")
@AllArgsConstructor
public class CustomerController {

	private CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<CustomerResponse> registerCustomer(@RequestBody CustomerRequest customerRequest) {
		CustomerResponse res = customerService.registerCustomer(customerRequest);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PutMapping("/update/name")
	public ResponseEntity<String> updateName(@RequestBody UpdateRequest updateRequest) {
		String res = customerService.updateName(updateRequest);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@PutMapping("/update/password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		String res = customerService.updatePassword(updatePasswordRequest);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@PutMapping("/update/phone")
	public ResponseEntity<String> updatePhone(@RequestBody UpdateRequest updateRequest) {
		String res = customerService.updatePhone(updateRequest);
		return new ResponseEntity<>(res, HttpStatus.OK);

	}

	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteCustomer(@RequestBody UpdateRequest updateRequest) {
		String res = customerService.deleteCustomer(updateRequest);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/view-profile")
	public ResponseEntity<CustomerResponse> getCustomerById() {
		CustomerResponse res = customerService.viewProfile();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/get/to-be-deleted")
	public ResponseEntity<List<CustomerResponse>> getAllCustomer() {
		List<CustomerResponse> res = customerService.getToBeDeletedCustomers();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
