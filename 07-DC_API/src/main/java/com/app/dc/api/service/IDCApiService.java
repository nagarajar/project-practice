package com.app.dc.api.service;

import java.util.List;

import com.app.dc.api.binding.EducationDetailsForm;
import com.app.dc.api.binding.IncomeDetailsForm;
import com.app.dc.api.binding.KidsDetailsForm;
import com.app.dc.api.binding.PlanForm;
import com.app.dc.api.entity.Plan;

public interface IDCApiService {
	
	Integer findByAppId(Integer appId);
	
	String createCaseId(Integer appId);
	
	String selectPlan(PlanForm planForm);
	
	String saveIncomeDetails(IncomeDetailsForm incomeDetailsForm);
	
	String saveEducationDetails(EducationDetailsForm educationDetailsForm);
	
	String saveKidsDetails(KidsDetailsForm kidsDetailsForm);
	
	//Plan Operations
	String addPlan(Plan plan);
	String updatePlan(Plan plan);
	Plan getPlanById(Integer planId);
	List<Plan> getAllPlans();
	String deletePlan(Integer planId);
	
}
