package com.bookMyStay.Entity;

import com.bookMyStay.Enums.RoomType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;

    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private Integer noOfPerson;

    private BigDecimal price;

    private Boolean available;

    @ManyToOne(fetch = FetchType.EAGER)
    private Hotel hotel;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Reservation> reservations;
}

