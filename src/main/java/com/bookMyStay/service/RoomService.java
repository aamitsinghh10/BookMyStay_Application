package com.bookMyStay.service;

import com.bookMyStay.dto.request.DateRequest;
import com.bookMyStay.dto.request.RoomRequest;
import com.bookMyStay.dto.request.UpdateRequest;
import com.bookMyStay.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {

	public RoomResponse addRoom(RoomRequest roomRequest);

	public String updateRoomType(UpdateRequest updateRequest, Long roomId);

	public String updateNoOfPerson(UpdateRequest updateRequest, Long roomId);

	public String updatePrice(UpdateRequest updateRequest, Long roomId);

	public String updateAvailable(UpdateRequest updateRequest, Long roomId);

	public String removeRoom(UpdateRequest updateRequest);

	public List<RoomResponse> getAllAvailableRoomsByHotelId(Long hotelId, DateRequest dateRequest);

	public List<RoomResponse> getAllRoomsByHotel();

}
