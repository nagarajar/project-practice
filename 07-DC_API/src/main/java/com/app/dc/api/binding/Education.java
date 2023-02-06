package com.app.dc.api.binding;

import lombok.Data;

@Data
public class Education {
	private Integer educationId;
	private String highestDegree;
	private Integer graduationYear;
	private String universityName;
	
	private Integer caseIdFk;
}
