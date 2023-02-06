package com.app.dc.api.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dc.api.binding.Education;
import com.app.dc.api.binding.Income;
import com.app.dc.api.binding.Kid;
import com.app.dc.api.binding.KidsInfo;
import com.app.dc.api.binding.KidsList;
import com.app.dc.api.binding.PlanSelection;
import com.app.dc.api.binding.Summary;
import com.app.dc.api.entities.CitizenEntity;
import com.app.dc.api.entities.DcCaseEntity;
import com.app.dc.api.entities.DcEducationEntity;
import com.app.dc.api.entities.DcIncomeEntity;
import com.app.dc.api.entities.DcKidsEntity;
import com.app.dc.api.entities.PlanEntity;
import com.app.dc.api.repository.CitizenRepository;
import com.app.dc.api.repository.DcCaseRepository;
import com.app.dc.api.repository.DcEducationRepository;
import com.app.dc.api.repository.DcIncomeRepository;
import com.app.dc.api.repository.DcKidsRepository;
import com.app.dc.api.repository.PlanRepository;
import com.netflix.discovery.converters.Auto;

@Service
public class DcServiceImpl implements IDcService {

	@Autowired
	private CitizenRepository citizenRepository;
	
	@Autowired
	private DcCaseRepository caseRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Autowired
	private DcIncomeRepository incomeRepository;
	
	@Autowired
	private DcEducationRepository educationRepository;
	
	@Autowired
	private DcKidsRepository kidsRepository;
	
	@Override
	public PlanSelection createCase(Integer appId) {
		
		
		//1. Search appId
		Optional<CitizenEntity> findById = citizenRepository.findById(appId);
		
		//2. Found
		if(findById.isPresent())
		{
			//2.1 Create Case Id/Num
			DcCaseEntity caseEntity = new DcCaseEntity();
			caseEntity.setAppIdFk(appId);
			caseEntity  = caseRepository.save(caseEntity);
			Integer caseId = caseEntity.getCaseId();
			
			//2.2 Send CaseId and Plans Info to UI
			PlanSelection planSelection = new PlanSelection();
			
			//2.2.1 Get all plans
			List<PlanEntity> planList = planRepository.findAll();
			Map<Integer, String> planMap = new HashMap<>();
			
			planList.forEach(plan -> planMap.put(plan.getPlanId(), plan.getPlanName()));
			
			//2.3 Prepare the response
			planSelection.setCaseId(caseId);
			planSelection.setPlansInfo(planMap);
			
			return planSelection;
		}
		
		//3 If appId not found planSelection will be null
		return null;
	}

	@Override
	public Integer selectCitizenPlan(PlanSelection planSelection) {
		Integer caseId = planSelection.getCaseId();
		Integer planId = planSelection.getPlanId();
		
		Optional<DcCaseEntity> findById = caseRepository.findById(caseId);
		
		if(findById.isPresent()) {
			DcCaseEntity caseEntity = findById.get();
			caseEntity.setPlanIdFk(planId);
			caseRepository.save(caseEntity);
		}
			
		return caseId;
	}

	@Override
	public Integer saveIncomeDetails(Income income) {
		DcIncomeEntity incomeEntity = new DcIncomeEntity();
		BeanUtils.copyProperties(income, incomeEntity);
		incomeEntity = incomeRepository.save(incomeEntity);
		
		return incomeEntity.getCaseIdFk();
	}

	@Override
	public Integer saveEducationDetails(Education education) {
		DcEducationEntity educationEntity = new DcEducationEntity();
		BeanUtils.copyProperties(education, educationEntity);
		educationEntity = educationRepository.save(educationEntity);
		
		return educationEntity.getCaseIdFk();
	}

	@Override
	public Summary saveKidsDetails(KidsInfo kidsInfo) {
		Integer caseIdFk = kidsInfo.getCaseIdFk();
		List<Kid> kids = kidsInfo.getKids();
		
		List<DcKidsEntity> kidsEntities = new ArrayList<>();
		
		kids.forEach(
				kid -> { 
					DcKidsEntity kidsEntity = new DcKidsEntity();
					BeanUtils.copyProperties(kid, kidsEntity);
					kidsEntity.setCaseIdFk(caseIdFk);
					kidsEntities.add(kidsEntity);	
				}
		);
		
		kidsRepository.saveAll(kidsEntities);
		
		return getSummary(caseIdFk);
	}
	
	public Summary getSummary(Integer caseId)
	{
		Optional<DcCaseEntity> dcCase = caseRepository.findById(caseId);
		
		Integer planIdFk = dcCase.get().getPlanIdFk();
		Integer appIdFk = dcCase.get().getAppIdFk();
		
		Optional<PlanEntity> plan = planRepository.findById(planIdFk);
		String planName = plan.get().getPlanName();
		
		Optional<CitizenEntity> citizen = citizenRepository.findById(appIdFk);
		String fullName = citizen.get().getFullName();
		Long ssn = citizen.get().getSsn();
		
		DcIncomeEntity incomeEntity = incomeRepository.findByCaseIdFk(caseId);
		DcEducationEntity educationEntity = educationRepository.findByCaseIdFk(caseId);
		List<DcKidsEntity> kidsEntities = kidsRepository.findByCaseIdFk(caseId);
		
		Summary summary = new Summary();
		summary.setFullName(fullName);
		summary.setPlanName(planName);
		summary.setSsn(ssn);
		
		//set incomedata
		Income income = new Income();
		BeanUtils.copyProperties(incomeEntity, income);
		summary.setIncomeInfo(income);
		//set education data
		Education education =  new Education();
		BeanUtils.copyProperties(educationEntity, education);
		summary.setEducationInfo(education);
		//set kids data
//		KidsInfo kidsInfo = new KidsInfo();
//		BeanUtils.copyProperties(kidsEntities, kidsInfo);
//		summary.setKidsInfo(kidsInfo);
		
		List<KidsList> kids = new ArrayList<>();
		kidsEntities.forEach(kid -> {
			KidsList kidsList = new KidsList();
			BeanUtils.copyProperties(kid, kidsList);
			kids.add(kidsList);
		});
		
		summary.setKidsList(kids);
		
		return summary;
	}
		

}
