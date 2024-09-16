package com.bookMyStay.Entity;

import com.bookMyStay.Enums.ReservationStatus;
import com.bookMyStay.dto.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reservationId;

    private LocalDate checkInDate;

    private LocalDate checkoutDate;

    private Integer noOfPerson;

    @Embedded
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

}

