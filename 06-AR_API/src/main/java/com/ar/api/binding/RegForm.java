package com.ar.api.binding;

import java.time.LocalDate;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class RegForm {
	private String fullName;
	private String email;
	private Long mobileNum;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	private Integer createdBy;
	private Integer updatedBy;
}
