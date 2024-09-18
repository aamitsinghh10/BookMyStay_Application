package com.bookMyStay.service.impl;

import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.Hotel;
import com.bookMyStay.Entity.Reservation;
import com.bookMyStay.Entity.Room;
import com.bookMyStay.Enums.ReservationStatus;
import com.bookMyStay.dto.request.ReservationRequest;
import com.bookMyStay.dto.response.ReservationResponse;
import com.bookMyStay.exception.ReservationException;
import com.bookMyStay.exception.RoomException;
import com.bookMyStay.repository.CustomerDatabase;
import com.bookMyStay.repository.HotelDatabase;
import com.bookMyStay.repository.ReservationDatabase;
import com.bookMyStay.repository.RoomDatabase;
import com.bookMyStay.service.ReservationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ReservationServiceImpl implements ReservationService {

	private ReservationDatabase reservationDatabase;
	private CustomerDatabase customerDatabase;
	private HotelDatabase hotelDatabase;
	private RoomDatabase roomDatabase;

	public ReservationResponse createReservation(Long roomId, ReservationRequest reservationRequest) {
		Customer customer = getCurrentLoggedInCustomer();

		Optional<Room> opt = roomDatabase.findById(roomId);
		if (opt.isEmpty())
			throw new RoomException("Room Not Found With Id : " + roomId);
		Room room = opt.get();

		Hotel hotel = room.getHotel();
		reservationDatabase.updateReservationStatus(hotel);

		List<Reservation> reservations = reservationDatabase.findByRoomAndStatus(room, ReservationStatus.BOOKED);

		LocalDate checkIn = reservationRequest.getCheckInDate();
		LocalDate checkOut = reservationRequest.getCheckInDate();
		log.info("Checking Room availability for dates : " + checkIn + " -> " + checkOut);
		for (Reservation r : reservations) {
			if ((checkIn.isEqual(r.getCheckInDate()) || checkIn.isEqual(r.getCheckInDate()))
					|| (checkOut.isEqual(r.getCheckInDate()) || checkOut.isEqual(r.getCheckInDate()))
					|| (checkIn.isAfter(r.getCheckInDate()) && checkIn.isBefore(r.getCheckInDate()))
					|| (checkOut.isAfter(r.getCheckInDate()) && checkOut.isBefore(r.getCheckInDate()))) {
				throw new ReservationException("Room not available for this date! Your payment will be transferred back to your account within 30 minutes");
			}
		}

		log.info("Building Reservation");
		Reservation reservation = buildReservation(reservationRequest);

		log.info("Assigning Reservation to the Room : " + roomId);
		room.getReservations().add(reservation);
		reservation.setRoom(room);

		log.info("Assigning Reservation to the Hotel : " + hotel.getName());
		hotel.getReservations().add(reservation);
		reservation.setHotel(hotel);

		log.info("Assigning Reservation to the Customer : " + customer.getName());
		customer.getReservations().add(reservation);
		reservation.setCustomer(customer);

		reservationDatabase.save(reservation);

		log.info("Reservation successfull");
		return buildReservationResponse(reservation);
	}

	@Override
	public String cancelReservation(Long reservationId) {
		Customer customer = getCurrentLoggedInCustomer();

		Optional<Reservation> opt = reservationDatabase.findById(reservationId);
		if (opt.isEmpty())
			throw new ReservationException("Reservation not found with reservation id : " + reservationId);
		Reservation reservation = opt.get();

		log.info("Checking if cancellation is allowed");
		if (reservation.getCheckInDate().isEqual(LocalDate.now())
				|| reservation.getCheckInDate().isBefore(LocalDate.now()))
			throw new ReservationException("Reservation can't be cancelled now!");

		log.info("Removing reference of Reservation from Room");
		Room room = reservation.getRoom();
		List<Reservation> curReservationsOfRoom = room.getReservations();
		curReservationsOfRoom.remove(reservation);
		room.setReservations(curReservationsOfRoom);

		log.info("Removing reference of Reservation from Hotel");
		Hotel hotel = reservation.getHotel();
		List<Reservation> curReservationsOfHotel = hotel.getReservations();
		curReservationsOfHotel.remove(reservation);
		hotel.setReservations(curReservationsOfHotel);

		log.info("Removing reference of Reservation from Customer");
		List<Reservation> curReservationsOfCustomer = customer.getReservations();
		curReservationsOfCustomer.remove(reservation);
		customer.setReservations(curReservationsOfCustomer);

		log.info("Deletion in progress");
		reservationDatabase.delete(reservation);

		log.info("Reservation cancelled successfully");
		return "Reservation cancelled successfully";
	}

	@Override
	public List<ReservationResponse> getAllReservationsOfHotel() {
		Hotel hotel = getCurrentLoggedInHotel();
		List<Reservation> reservations = reservationDatabase.findByHotel(hotel);
		if (reservations.isEmpty())
			throw new ReservationException("Reservations Not Found!");
		return reservations.stream().map(this::buildReservationResponse).collect(Collectors.toList());
	}

	@Override
	public List<ReservationResponse> getAllReservationsOfCustomer() {
		Customer customer = getCurrentLoggedInCustomer();
		List<Reservation> reservations = reservationDatabase.findByCustomer(customer);
		if (reservations.isEmpty())
			throw new ReservationException("Reservations Not Found!");
		return reservations.stream().map(this::buildReservationResponse).collect(Collectors.toList());
	}

	@Override
	public ReservationResponse getReservationById(Long ReservationId) {
		Optional<Reservation> optional = reservationDatabase.findById(ReservationId);
		if (optional.isEmpty())
			throw new ReservationException("Reservation not found with reservation id: " + ReservationId);
		return buildReservationResponse(optional.get());
	}

	private Hotel getCurrentLoggedInHotel() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return hotelDatabase.findByHotelEmail(email).get();
	}

	private Customer getCurrentLoggedInCustomer() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		return customerDatabase.findByEmail(email).get();
	}

	private Reservation buildReservation(ReservationRequest reservationRequest) {
		return Reservation.builder()
				.checkInDate(reservationRequest.getCheckInDate())
				.checkoutDate(reservationRequest.getCheckOutDate())
				.noOfPerson(reservationRequest.getNumberOfPersons())
				.status(ReservationStatus.BOOKED)
				.payment(reservationRequest.getPayment())
				.build();
	}

	private ReservationResponse buildReservationResponse(Reservation reservation) {
		return ReservationResponse.builder()
				.reservationId(reservation.getReservationId())
				.customerName(reservation.getCustomer().getName())
				.roomNumber(reservation.getRoom().getRoomNumber())
				.hotelName(reservation.getHotel().getName())
				.checkInDate(reservation.getCheckInDate())
				.checkOutDate(reservation.getCheckoutDate())
				.noOfPerson(reservation.getNoOfPerson())
				.payment(reservation.getPayment())
				.status(reservation.getStatus())
				.build();
	}

}
