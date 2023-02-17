package com.app.admin.api.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PlanRequest {
	private String planName;
	private LocalDate planStartDate;
	private LocalDate planEndDate;
	private String planCategory;
	private boolean active;
}
