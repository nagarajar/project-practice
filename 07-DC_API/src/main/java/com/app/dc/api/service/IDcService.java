package com.app.dc.api.service;

import com.app.dc.api.binding.Education;
import com.app.dc.api.binding.Income;
import com.app.dc.api.binding.KidsInfo;
import com.app.dc.api.binding.PlanSelection;
import com.app.dc.api.binding.Summary;

public interface IDcService {
	
	PlanSelection createCase(Integer appId);
	
	Integer selectCitizenPlan(PlanSelection planSelection);
	
	Integer saveIncomeDetails(Income income);
	
	Integer saveEducationDetails(Education education);
	
	Summary saveKidsDetails(KidsInfo kidsInfo);
	
	Summary getSummary(Integer caseId);

}
