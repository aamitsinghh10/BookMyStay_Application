package com.bookMyStay.repository;

import com.bookMyStay.Entity.Room;
import com.bookMyStay.Enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface RoomDao extends JpaRepository<Room, Long>{

	@Modifying
	@Query("update Room set roomType=?2 where roomId=?1")
	Integer setRoomType(Long roomId, RoomType roomType);

	@Modifying
	@Query("update Room set noOfPerson=?2 where roomId=?1")
	Integer setNoOfPerson(Long roomId, Integer noOfPerson);

	@Modifying
	@Query("update Room set price=?2 where roomId=?1")
	Integer setPrice(Long roomId, BigDecimal price);

	@Modifying
	@Query("update Room set available=?2 where roomId=?1")
	Integer setAvailable(Long roomId, Boolean available);
	
}
