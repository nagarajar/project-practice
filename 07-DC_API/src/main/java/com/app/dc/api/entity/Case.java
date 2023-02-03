package com.app.dc.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "case_tab")
public class Case {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String caseId;
	
	private Integer appIdfk;
	
	@ManyToOne
	@JoinColumn(name = "plan_idfk")
	private Plan plan;
}
