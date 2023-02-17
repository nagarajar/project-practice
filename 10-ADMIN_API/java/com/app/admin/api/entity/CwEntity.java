package com.app.admin.api.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "case_worker_dtls")
@Data
public class CwEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cwId;
	private String fullName; 
	private String email;
	private Long mobileNum; 
	private String gender; 
	private LocalDate dob; 
	private Long ssn;
	private boolean active;
	private String password;
	private String accStatus;
}
