package com.bookMyStay.dto.request;

import com.bookMyStay.Entity.Address;
import com.bookMyStay.Enums.HotelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRequest {

	@NotNull
	@NotEmpty
	@NotBlank
	private String name;

	@NotNull
	@NotEmpty
	@NotBlank
	@Email
	private String hotelEmail;

	@NotNull
	@NotEmpty
	@NotBlank
	private String hotelPhone;

	@NotNull
	@NotEmpty
	@NotBlank
	private String hotelTelephone;

	@NotNull
	private char[] password;

	@Valid
	private Address address;

	@NotNull
	@Enumerated(EnumType.STRING)
	private HotelType hotelType;

	private List<String> amenities;

}
