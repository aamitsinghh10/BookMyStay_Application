package com.bookMyStay.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRequest {

	@NotNull
	@FutureOrPresent
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDate checkIn;

	@NotNull
	@FutureOrPresent
	@JsonFormat(pattern = "dd-MM-yyyy")
	LocalDate checkOut;

}
