package com.bookMyStay.service;

import com.bookMyStay.dto.request.ReservationRequest;
import com.bookMyStay.dto.response.ReservationResponse;

import java.util.List;

public interface ReservationService {

	public ReservationResponse createReservation(Long roomId, ReservationRequest reservationRequest);

	public String cancelReservation(Long reservationId);

	public List<ReservationResponse> getAllReservationsOfHotel();

	public List<ReservationResponse> getAllReservationsOfCustomer();

	public ReservationResponse getReservationById(Long ReservationId);

}
