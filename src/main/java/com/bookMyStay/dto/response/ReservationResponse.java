package com.bookMyStay.dto.response;

import com.bookMyStay.Enums.ReservationStatus;
import com.bookMyStay.dto.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponse {

	private Long reservationId;

	private String customerName;

	private Integer roomNumber;

	private String hotelName;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate checkInDate;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate checkOutDate;

	private Integer noOfPerson;

	private Payment payment;

	private ReservationStatus status;

}
