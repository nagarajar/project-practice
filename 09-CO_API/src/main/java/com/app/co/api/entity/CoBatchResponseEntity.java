package com.app.co.api.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "co_batch_response_dtls")
public class CoBatchResponseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer responseId;
	private Long totalRecords;
	private Long successCount;
	private Long failureCount;
	
	@CreationTimestamp
	private LocalDate createdDate;
	
	private Integer createdBy;
}
