package com.app.cw.api.binding;

import lombok.Data;

@Data
public class DashboardResponse {
	private Long noOfPlans;
	private Long approvedCitizenCount;
	private Long deniedCitizenCount;
}
