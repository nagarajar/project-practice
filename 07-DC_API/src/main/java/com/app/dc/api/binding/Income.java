package com.app.dc.api.binding;

import lombok.Data;

@Data
public class Income {
	private Integer incomeId;	
	private Double monthlySalIncome;	
	private Double rentIncome;	
	private Double propertyIncome;
	
	private Integer caseIdFk;

}
