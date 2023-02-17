package com.app.co.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CoTrgEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer coTrgId;
	private Integer caseNum;
	@Lob
	private byte[] coPdf;
	private String trgStatus;
}
