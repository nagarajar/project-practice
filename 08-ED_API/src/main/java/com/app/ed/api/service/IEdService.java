package com.app.ed.api.service;

import com.app.ed.api.binding.EligResponse;

public interface IEdService {
	public EligResponse determineEligibility(Integer caseNum);
}
