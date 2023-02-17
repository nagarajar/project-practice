package com.ar.api.entity;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "citizen_dtls")
public class Citizen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appId;
	private String fullName;
	private String email;
	private Long mobileNum;
	private String gender;
	private LocalDate dob;
	private Long ssn;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	@UpdateTimestamp
	private LocalDate updatedDate;
	
	private Integer createdBy;
	private Integer updatedBy;

}
