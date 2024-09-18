package com.bookMyStay.controller;

import com.bookMyStay.dto.request.ReservationRequest;
import com.bookMyStay.dto.response.ReservationResponse;
import com.bookMyStay.exception.ReservationException;
import com.bookMyStay.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookMyStay/reservations")
@AllArgsConstructor
public class ReservationController {

	private ReservationService reservationService;

	@PostMapping("/book/{roomId}")
	public ResponseEntity<ReservationResponse> createReservation(@PathVariable("roomId") Long roomId,
																 @RequestBody ReservationRequest reservationRequest) {
		return new ResponseEntity<>(reservationService.createReservation(roomId, reservationRequest),
				HttpStatus.CREATED);
	}

	@PutMapping("/cancel/{reservationId}")
	public ResponseEntity<String> cancelReservation(@PathVariable("reservationId") Long reservationId)
			throws ReservationException {
		return new ResponseEntity<>(reservationService.cancelReservation(reservationId), HttpStatus.ACCEPTED);
	}

	@GetMapping("/get/by-hotel")
	public ResponseEntity<List<ReservationResponse>> getAllReservationsOfHotel() {
		return new ResponseEntity<>(reservationService.getAllReservationsOfHotel(), HttpStatus.OK);
	}

	@GetMapping("/get/by-customer")
	public ResponseEntity<List<ReservationResponse>> getAllReservationsOfCustomer() {
		return new ResponseEntity<>(reservationService.getAllReservationsOfCustomer(), HttpStatus.OK);
	}

	@GetMapping("/get/by-id/{reservationId}")
	public ResponseEntity<ReservationResponse> getReservationById(@PathVariable("reservationId") Long reservationId) {
		return new ResponseEntity<>(reservationService.getReservationById(reservationId), HttpStatus.OK);
	}

}
