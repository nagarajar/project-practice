package com.app.admin.api.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CwRequest {
	private String fullName; 
	private String email;
	private Long mobileNum; 
	private String gender; 
	private LocalDate dob; 
	private Long ssn;
	private boolean active;
}
