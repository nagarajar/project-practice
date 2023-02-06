package com.app.dc.api.binding;

import java.util.Map;

import lombok.Data;

@Data
public class PlanSelection {
	
	private Integer caseId;
	private Integer planId;
	
	private Map<Integer, String> plansInfo;
}
