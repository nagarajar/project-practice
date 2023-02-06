package com.app.dc.api.binding;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Kid {
	private Integer kidId;
	private String kidName;
	private LocalDate kidDob;
	private String kidGender;
	private Long kidSsn;
}
