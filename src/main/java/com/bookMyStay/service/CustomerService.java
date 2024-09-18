package com.bookMyStay.service;

import com.bookMyStay.dto.request.CustomerRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.CustomerResponse;

import java.util.List;

public interface CustomerService {

	public CustomerResponse registerCustomer(CustomerRequest customerRequest);

	public String updateName(UpdateRequest updateRequest);

	public String updatePassword(UpdatePasswordRequest updatePasswordRequest);

	public String updatePhone(UpdateRequest updateRequest);

	public String deleteCustomer(UpdateRequest updateRequest);

	public List<CustomerResponse> getToBeDeletedCustomers();

	public CustomerResponse viewProfile();

}
