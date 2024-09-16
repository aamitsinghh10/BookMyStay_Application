package com.bookMyStay.dto.request;

import com.bookMyStay.dto.Payment;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {

	@FutureOrPresent
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate checkInDate;

	@FutureOrPresent
	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate checkOutDate;

	@Min(1)
	private Integer numberOfPersons;

	@Valid
	private Payment payment;

}
