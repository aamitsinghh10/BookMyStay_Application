package com.bookMyStay.Entity;

import com.bookMyStay.Enums.HotelType;
import com.bookMyStay.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long hotelId;

    private String name;

    @Column(unique = true)
    private String hotelEmail;

    private String password;

    private String hotelPhone;

    private String hotelTelephone;

    @Enumerated(EnumType.STRING)
    private HotelType hotelType;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> amenities;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER)
    private List<Feedback> feedbacks;

}

