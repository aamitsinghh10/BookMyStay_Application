package com.bookMyStay.dto.request;

import com.bookMyStay.Entity.Address;
import com.bookMyStay.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

	@NotNull @NotEmpty @NotBlank
    private String name;

	@NotNull @NotEmpty @NotBlank @Email
    private String email;

	@NotNull @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}")
    private char[] password;

	@NotNull @NotEmpty @NotBlank @Size(min = 10)
    private String phone;

	@NotNull
	@Enumerated(EnumType.STRING)
    private Gender gender;

	@NotNull
	@JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

	@Valid
    private Address address;

}
