package com.bookMyStay.service.impl;

import com.bookMyStay.Entity.Address;
import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.Hotel;
import com.bookMyStay.Enums.HotelType;
import com.bookMyStay.Enums.Role;
import com.bookMyStay.dto.request.HotelRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.HotelResponse;
import com.bookMyStay.exception.CustomerException;
import com.bookMyStay.exception.HotelException;
import com.bookMyStay.repository.CustomerDatabase;
import com.bookMyStay.repository.HotelDatabase;
import com.bookMyStay.service.HotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

	private HotelDatabase hotelDatabase;
	private CustomerDatabase customerDatabase;
	private PasswordEncoder passwordEncoder;

	public HotelResponse registerHotel(HotelRequest hotelRequest) {

		log.info("Performing email validation");
		if (isEmailExists(hotelRequest.getHotelEmail())) {
			throw new HotelException("This email is already registered. Please use a different email to register.");
		}

		log.info("Verifying Hotel name");
		if (hotelWithNameAlreadyExitsInYourCity(hotelRequest.getName(), hotelRequest.getAddress())) {
			throw new HotelException("Hotel already exits in your city with name : " + hotelRequest.getName());
		}

		Hotel hotel = buildHotel(hotelRequest);
		hotelDatabase.save(hotel);

		log.info("Registration successfull");
		return buildHotelResponse(hotel);
	}

	@Override
	public String updateName(UpdateRequest updateRequest) {
		Hotel currentHotel = getCurrentLoggedInHotel();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentHotel.getPassword())) {
			throw new HotelException("Wrong credentials!");
		}
		hotelDatabase.setNameOfHotel(currentHotel.getHotelId(), updateRequest.getField());

		log.info("Updation successfull");
		return "Updated hotel name successfully";
	}

	@Override
	public String updatePassword(UpdatePasswordRequest updatePasswordRequest) {
		Hotel currentHotel = getCurrentLoggedInHotel();

		log.info("Verifying credentials");
		String currentPassword = new String(updatePasswordRequest.getCurrentPassword());
		if (!passwordEncoder.matches(currentPassword, currentHotel.getPassword())) {
			throw new HotelException("Wrong credentials!");
		}

		log.info("Validating new password");
		String newPassword = new String(updatePasswordRequest.getNewPassword());
		if (!matchesRegex(newPassword)) {
			throw new CustomerException("New password validation failed!");
		}

		hotelDatabase.setHotelPassword(currentHotel.getHotelId(), passwordEncoder.encode(newPassword));

		log.info("Updation successfull");
		return "Password updated successfully!";
	}

	@Override
	public String updatePhone(UpdateRequest updateRequest) {
		Hotel currentHotel = getCurrentLoggedInHotel();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentHotel.getPassword())) {
			throw new HotelException("Wrong credentials!");
		}
		hotelDatabase.setPhoneOfHotel(currentHotel.getHotelId(), updateRequest.getField());

		log.info("Updation successfull");
		return "Updated hotel phone successfully";
	}

	@Override
	public String updateTelephone(UpdateRequest updateRequest) {
		Hotel currentHotel = getCurrentLoggedInHotel();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, currentHotel.getPassword())) {
			throw new HotelException("Wrong credentials!");
		}
		hotelDatabase.setTelephoneOfHotel(currentHotel.getHotelId(), updateRequest.getField());

		log.info("Updation successfull");
		return "Updated hotel telephone successfully";
	}

	@Override
	public String updateHotelType(UpdateRequest updateRequest) {
		Hotel hotel = getCurrentLoggedInHotel();

		log.info("Verifying credentials");
		String password = new String(updateRequest.getPassword());
		if (!passwordEncoder.matches(password, hotel.getPassword())) {
			throw new HotelException("Wrong credentials!");
		}
		hotelDatabase.setHotelType(hotel.getHotelId(), HotelType.valueOf(updateRequest.getField()));

		log.info("Updation successfull");
		return "Updated hotel type successfully";
	}

	@Override
	public HotelResponse getHotelById(Long id) {
		Optional<Hotel> optional = hotelDatabase.findById(id);
		if (optional.isPresent())
			return buildHotelResponse(optional.get());
		throw new HotelException("No hotel found with id " + id);
	}

	@Override
	public List<HotelResponse> getHotelsNearMe() {
		Customer customer = getCurrentLoggedInCustomer();
		System.out.println(111);
		List<Hotel> hotels = hotelDatabase.getHotelByCity(customer.getAddress().getCity());
		System.out.println(222);
		if (hotels.isEmpty())
			throw new HotelException("Hotels Not Found In Your Area!");
		return hotels.stream().map(this::buildHotelResponse).collect(Collectors.toList());
	}

	@Override
	public List<HotelResponse> getHotelsInCity(String city) {
		List<Hotel> hotels = hotelDatabase.getHotelByCity(city);
		if (hotels.isEmpty())
			throw new HotelException("Hotels Not Found In Your Area!");
		return hotels.stream().map(this::buildHotelResponse).collect(Collectors.toList());
	}

	private boolean isEmailExists(String email) {
		return customerDatabase.findByEmail(email).isPresent() || hotelDatabase.findByHotelEmail(email).isPresent();
	}

	private Hotel getCurrentLoggedInHotel() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return hotelDatabase.findByHotelEmail(email).get();
	}

	private Customer getCurrentLoggedInCustomer() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerDatabase.findByEmail(email).get();
	}

	private boolean hotelWithNameAlreadyExitsInYourCity(String name, Address address) {
		Optional<Hotel> opt = hotelDatabase.getHotelByNameAndCity(name, address.getCity());
		if (opt.isPresent())
			return true;
		return false;
	}

	private boolean matchesRegex(String input) {
		if(input.length()<8)
			return false;
		String regex = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
		return Pattern.compile(regex).matcher(input).matches();
	}

	private Hotel buildHotel(HotelRequest hotelRequest) {
		return Hotel.builder()
				.name(hotelRequest.getName())
				.hotelEmail(hotelRequest.getHotelEmail()).password(passwordEncoder.encode(new String(hotelRequest.getPassword())))
				.hotelPhone(hotelRequest.getHotelPhone())
				.hotelTelephone(hotelRequest.getHotelTelephone())
				.role(Role.ROLE_HOTEL)
				.hotelType(hotelRequest.getHotelType())
				.address(hotelRequest.getAddress())
				.amenities(hotelRequest.getAmenities())
				.rooms(new ArrayList<>()).reservations(new ArrayList<>()).feedbacks(new ArrayList<>())
				.build();
	}

	private HotelResponse buildHotelResponse(Hotel hotel) {
		return HotelResponse.builder()
				.hotelId(hotel.getHotelId())
				.name(hotel.getName())
				.hotelPhoneNumber(hotel.getHotelTelephone())
				.address(hotel.getAddress())
				.hotelType(hotel.getHotelType())
				.amenities(hotel.getAmenities())
				.build();
	}
}
