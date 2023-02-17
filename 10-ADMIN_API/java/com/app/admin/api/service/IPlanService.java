package com.app.admin.api.service;

import java.util.List;

import com.app.admin.api.binding.PlanRequest;
import com.app.admin.api.entity.PlanEntity;

public interface IPlanService {
	Integer createPlan(PlanRequest planRequest);
	PlanEntity findByPlanId(Integer id);
	List<PlanEntity> getAllPlans();
	PlanEntity updatePlan(PlanEntity planEntity);
	String activateOrDeactivatePlan(Integer id);
	String deleteByPlanId(Integer id);
}
