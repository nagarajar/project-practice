package com.app.admin.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.admin.api.binding.PlanRequest;
import com.app.admin.api.entity.CwEntity;
import com.app.admin.api.entity.PlanEntity;
import com.app.admin.api.repository.PlanRepository;

@Service
public class PlanServiceImpl implements IPlanService {

	@Autowired
	private PlanRepository planRepository;
	
	@Override
	public Integer createPlan(PlanRequest planRequest) {
		PlanEntity planEntity = new PlanEntity();
		BeanUtils.copyProperties(planRequest, planEntity);
		planRepository.save(planEntity); 
		return planEntity.getPlanId();
	}

	@Override
	public PlanEntity findByPlanId(Integer id) {
		Optional<PlanEntity> findById = planRepository.findById(id);
		if(findById.isPresent()) {
			return findById.get();
		}
		return null;
	}

	@Override
	public List<PlanEntity> getAllPlans() {
		return planRepository.findAll();
	}

	@Override
	public PlanEntity updatePlan(PlanEntity planEntity) {
		return planRepository.save(planEntity);
	}

	@Override
	public String activateOrDeactivatePlan(Integer id) {
		String msg = null;
		PlanEntity planEntity = findByPlanId(id);
		if(planEntity != null) {
			if(planEntity.isActive() == true) {
				planEntity.setActive(false);	
				msg = "Deactivated";
			}else {
				planEntity.setActive(true);
				msg = "Activated";
			}
			planRepository.save(planEntity);
		}
		return msg;
	}

	@Override
	public String deleteByPlanId(Integer id) {
		String msg = null;
		if(planRepository.existsById(id)) {
			planRepository.deleteById(id);
			msg = "Deleted";
		}
		return msg;
	}

}
