package com.app.bi.api.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Data
@Entity
@Table(name = "BI_INFO")
public class BiEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer biId;
	
	@Lob
	private byte[] csvFile;
	
	@CreationTimestamp
	private LocalDate ceatedDate;
}
