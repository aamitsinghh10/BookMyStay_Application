package com.bookMyStay.repository;

import com.bookMyStay.Entity.Customer;
import com.bookMyStay.Entity.Hotel;
import com.bookMyStay.Entity.Reservation;
import com.bookMyStay.Entity.Room;
import com.bookMyStay.Enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationDatabase extends JpaRepository<Reservation, Long> {

	List<Reservation> findByCustomer(Customer customer);

	@Modifying
	@Query("UPDATE Reservation r SET r.status = 'CLOSED' WHERE r.hotel = :hotel AND r.checkoutDate < CURRENT_DATE")
	void updateReservationStatus(@Param("hotel") Hotel hotel);


	@Query("SELECT r FROM Reservation r WHERE r.hotel = ?1 AND r.checkoutDate >= CURDATE()")
	List<Reservation> getPendingReservationsOfHotel(Hotel hotel);

	List<Reservation> findByRoomAndStatus(Room room, ReservationStatus status);

	List<Reservation> findByHotel(Hotel hotel);

	List<Reservation> findByHotelAndCustomer(Hotel hotel, Customer customer);

}
