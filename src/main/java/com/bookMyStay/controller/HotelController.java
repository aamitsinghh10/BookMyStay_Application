package com.bookMyStay.controller;

import com.bookMyStay.dto.request.HotelRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.HotelResponse;
import com.bookMyStay.service.HotelService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookMyStay/hotels")
@AllArgsConstructor
public class HotelController {

	private HotelService hotelService;

	@PostMapping("/register")
	public ResponseEntity<HotelResponse> registerHotel(@Valid @RequestBody HotelRequest hotelRequest) {
		return new ResponseEntity<>(hotelService.registerHotel(hotelRequest), HttpStatus.CREATED);
	}

	@PutMapping("/update/name")
	public ResponseEntity<String> updateName(@RequestBody UpdateRequest updateDetailsRequest) {
		return new ResponseEntity<>(hotelService.updateName(updateDetailsRequest), HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/password")
	public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordRequest updatePasswordRequest) {
		String res = hotelService.updatePassword(updatePasswordRequest);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/update/phone")
	public ResponseEntity<String> updatePhone(@RequestBody UpdateRequest updateDetailsRequest) {
		return new ResponseEntity<>(hotelService.updatePhone(updateDetailsRequest), HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/telephone")
	public ResponseEntity<String> updateTelephone(@RequestBody UpdateRequest updateDetailsRequest) {
		return new ResponseEntity<>(hotelService.updateTelephone(updateDetailsRequest), HttpStatus.ACCEPTED);
	}

	@PutMapping("/update/hoteltype")
	public ResponseEntity<String> updateHotelType(@RequestBody UpdateRequest updateDetailsRequest) {
		return new ResponseEntity<>(hotelService.updateHotelType(updateDetailsRequest), HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/by-id/{hotelId}")
	public ResponseEntity<HotelResponse> getHotelById(@PathVariable("hotelId") long hotelId) {
		return new ResponseEntity<>(hotelService.getHotelById(hotelId), HttpStatus.FOUND);
	}

	@GetMapping("/near-me")
	public ResponseEntity<List<HotelResponse>> getHotelsNearMe() {
		ResponseEntity<List<HotelResponse>> responseEntity = new ResponseEntity<>(hotelService.getHotelsNearMe(),
				HttpStatus.FOUND);
		return responseEntity;
	}

	@GetMapping("/in-city/{city}")
	public ResponseEntity<List<HotelResponse>> getHotelsInCity(@PathVariable String city) {
		ResponseEntity<List<HotelResponse>> responseEntity = new ResponseEntity<>(hotelService.getHotelsInCity(city),
				HttpStatus.FOUND);
		return responseEntity;
	}

}
