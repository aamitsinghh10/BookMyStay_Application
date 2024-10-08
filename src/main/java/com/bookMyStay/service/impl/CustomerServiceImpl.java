package com.bookMyStay.service.impl;

import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.DeleteReason;
import com.bookMyStay.Entity.Reservation;
import com.bookMyStay.Enums.Role;
import com.bookMyStay.dto.request.CustomerRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.CustomerResponse;
import com.bookMyStay.exception.CustomerException;
import com.bookMyStay.repository.CustomerDatabase;
import com.bookMyStay.repository.DeleteReasonDatabase;
import com.bookMyStay.repository.HotelDatabase;
import com.bookMyStay.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

	private CustomerDatabase customerDatabase;
	private HotelDatabase hotelDatabase;
	private PasswordEncoder passwordEncoder;
	private DeleteReasonDatabase deleteReasonDao;

	@Override
	public CustomerResponse registerCustomer(CustomerRequest customerRequest) {
		log.info("Performing email validation");
		if (isEmailExists(customerRequest.getEmail())) {
			throw new CustomerException("Customer is already exist ...!");
		}

		log.info("Validating password");
		String password = new String(customerRequest.getPassword());
		if (!matchesRegex(password)) {
			throw new CustomerException("Password validation failed!");
		}

		Customer customer = buildCustomer(customerRequest);
		customerDatabase.save(customer);

		log.info("Registration successfull");
		return buildCustomerResponse(customer);
	}

	public String updateName(UpdateRequest updateRequest) {
		Customer currentCustomer = getCurrentLoggedInCustomer();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentCustomer.getPassword())) {
			throw new CustomerException("Wrong credentials!");
		}
		customerDatabase.setCustomerEmail(currentCustomer.getCustomerId(), updateRequest.getField());

		log.info("Updation successfull");
		return "Name updated successfully!";
	}

	public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		Customer currentCustomer = getCurrentLoggedInCustomer();

		log.info("Verifying credentials");
		String currentPassword = new String(updatePasswordRequest.getCurrentPassword());
		if (!passwordEncoder.matches(currentPassword, currentCustomer.getPassword())) {
			throw new CustomerException("Wrong credentials!");
		}

		log.info("Validating new password");
		String newPassword = new String(updatePasswordRequest.getNewPassword());
		if (!matchesRegex(newPassword)) {
			throw new CustomerException("New password validation failed!");
		}

		customerDatabase.setCustomerPassword(currentCustomer.getCustomerId(), passwordEncoder.encode(newPassword));

		log.info("Updation successfull");
		return "Password updated successfully!";
	}

	@Override
	public String updatePhone(UpdateRequest updateRequest) {
		Customer currentCustomer = getCurrentLoggedInCustomer();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentCustomer.getPassword())) {
			throw new CustomerException("Wrong credentials!");
		}
		customerDatabase.setCustomerPhone(currentCustomer.getCustomerId(), updateRequest.getField());

		log.info("Updation successfull");
		return "Phone updated successfully!";
	}

	@Override
	public String deleteCustomer(UpdateRequest updateRequest) {
		Customer currentCustomer = getCurrentLoggedInCustomer();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentCustomer.getPassword())) {
			throw new CustomerException("Wrong credentials!");
		}

		List<Reservation> reservations = currentCustomer.getReservations();
		List<Reservation> pendingReservations = reservations.stream().filter(
				r -> r.getCheckoutDate().isAfter(LocalDate.now()) || r.getCheckoutDate().isEqual(LocalDate.now()))
				.collect(Collectors.toList());

		log.info("Checking for any pending reservations");
		if (!pendingReservations.isEmpty())
			throw new CustomerException("Pending reservations! Account can't be deleted");

		log.info("Setting Account status to be deleted");
		currentCustomer.setToBeDeleted(true);
		currentCustomer.setDeletionScheduledAt(LocalDateTime.now());
		customerDatabase.save(currentCustomer);

		DeleteReason deleteReason = new DeleteReason();
		deleteReason.setReason(updateRequest.getField());
		deleteReasonDao.save(deleteReason);

		log.info("Account schelduled for deletion and Logged out!");
		return "Account schelduled for deletion. To recovered loggin again within 24 hours";
	}

	@Override
	public List<CustomerResponse> getToBeDeletedCustomers() {
		List<Customer> customers = customerDatabase.findByToBeDeleted(true);
		if (customers.isEmpty())
			throw new CustomerException("No accounts found for deletion");
		
		return customers.stream().map(this::buildCustomerResponse).collect(Collectors.toList());
	}

	@Override
	public CustomerResponse viewProfile() {
		return buildCustomerResponse(getCurrentLoggedInCustomer());
	}

	private Customer getCurrentLoggedInCustomer() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerDatabase.findByEmail(email).get();
	}

	private boolean isEmailExists(String email) {
		return customerDatabase.findByEmail(email).isPresent() || hotelDatabase.findByHotelEmail(email).isPresent();
	}

	private boolean matchesRegex(String input) {
		if(input.length()<8)
			return false;
		String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return Pattern.compile(regex).matcher(input).matches();
	}

	private Customer buildCustomer(CustomerRequest customerRequest) {
		return Customer.builder()
				.name(customerRequest.getName())
				.email(customerRequest.getEmail()).password(passwordEncoder.encode(new String(customerRequest.getPassword())))
				.phone(customerRequest.getPhone())
				.gender(customerRequest.getGender())
				.registrationDateTime(LocalDateTime.now())
				.role(Role.ROLE_CUSTOMER)
				.dob(customerRequest.getDob())
				.address(customerRequest.getAddress())
				.toBeDeleted(false)
				.reservations(new ArrayList<>())
				.build();
	}

	private CustomerResponse buildCustomerResponse(Customer customer) {
		return CustomerResponse.builder()
				.customerId(customer.getCustomerId())
				.name(customer.getName())
				.email(customer.getEmail())
				.phone(customer.getPhone())
				.gender(customer.getGender())
				.dob(customer.getDob())
				.address(customer.getAddress())
				.registrationDateTime(customer.getRegistrationDateTime())
				.build();
	}
}
