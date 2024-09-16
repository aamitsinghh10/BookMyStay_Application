package com.bookMyStay.dto.response;

import com.bookMyStay.Entity.Address;
import com.bookMyStay.Enums.HotelType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {

	private Long hotelId;

	private String name;

	private String hotelPhoneNumber;

	private Address address;

	@Enumerated(EnumType.STRING)
	private HotelType hotelType;

	private List<String> amenities;

}
