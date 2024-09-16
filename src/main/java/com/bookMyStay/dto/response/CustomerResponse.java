package com.bookMyStay.dto.response;

import com.bookMyStay.Entity.Address;
import com.bookMyStay.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {

	private Long customerId;

	private String name;

	private String email;

	private String phone;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonFormat(pattern = "dd-MM-yyyy")
	private LocalDate dob;

	private Address address;

	@JsonFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime registrationDateTime;

}
