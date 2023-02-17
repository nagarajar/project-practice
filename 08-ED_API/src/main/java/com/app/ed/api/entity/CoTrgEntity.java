package com.app.ed.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CO_TRIGGERS")
public class CoTrgEntity {
	@Id
	@GeneratedValue
	private Integer coTrgId;
	private Integer caseNum;
	private byte[] pdf;
	private String trgStatus;
}
