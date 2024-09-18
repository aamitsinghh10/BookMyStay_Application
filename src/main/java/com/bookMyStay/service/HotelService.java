package com.bookMyStay.service;

import com.bookMyStay.dto.request.HotelRequest;
import com.bookMyStay.dto.request.UpdatePasswordRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {

	public HotelResponse registerHotel(HotelRequest hotelRequest);

	public String updateName(UpdateRequest updateRequest);

	public String updatePassword(UpdatePasswordRequest updatePasswordRequest);

	public String updatePhone(UpdateRequest updateRequest);

	public String updateTelephone(UpdateRequest updateRequest);

	public String updateHotelType(UpdateRequest updateRequest);

	public HotelResponse getHotelById(Long id);

	public List<HotelResponse> getHotelsNearMe();

	public List<HotelResponse> getHotelsInCity(String city);

}
