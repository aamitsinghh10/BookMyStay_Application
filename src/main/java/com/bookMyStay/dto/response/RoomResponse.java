package com.bookMyStay.dto.response;

import com.bookMyStay.Enums.RoomType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomResponse {

	private Long roomId;

	private Integer roomNumber;

	@Enumerated(EnumType.STRING)
	private RoomType roomType;

	private Integer noOfPerson;

	private BigDecimal price;

	private Boolean available;

}
