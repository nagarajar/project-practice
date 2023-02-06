package com.app.dc.api.binding;

import java.util.List;

import lombok.Data;

@Data
public class Summary {
	
	private String fullName;
	private String planName;
	private Long ssn;
	
	private Income incomeInfo;
	private Education educationInfo;
	//private KidsInfo kidsInfo;
	private List<KidsList> kidsList;
}
