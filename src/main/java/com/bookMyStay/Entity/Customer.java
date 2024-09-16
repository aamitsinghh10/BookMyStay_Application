package com.bookMyStay.Entity;

import com.bookMyStay.Enums.Gender;
import com.bookMyStay.Enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate dob;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime registrationDateTime;

    private boolean toBeDeleted;

    private LocalDateTime deletionScheduledAt;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private List<Reservation> reservations;

}
