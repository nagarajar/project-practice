package com.app.dc.api.binding;

import java.util.List;

import lombok.Data;

@Data
public class KidsInfo {
	
	private Integer caseIdFk;
	private List<Kid> kids;
}
