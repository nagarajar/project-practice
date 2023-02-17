package com.app.ed.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DC_INCOME")
public class DcIncomeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer incomeId;	
	private Double monthlySalIncome;	
	private Double rentIncome;	
	private Double propertyIncome;
	
	private Integer caseIdFk;

}
