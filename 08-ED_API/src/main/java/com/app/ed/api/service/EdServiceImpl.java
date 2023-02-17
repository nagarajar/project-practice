package com.app.ed.api.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.ed.api.binding.EligResponse;
import com.app.ed.api.entity.CitizenEntity;
import com.app.ed.api.entity.CoTrgEntity;
import com.app.ed.api.entity.DcCaseEntity;
import com.app.ed.api.entity.DcEducationEntity;
import com.app.ed.api.entity.DcIncomeEntity;
import com.app.ed.api.entity.DcKidsEntity;
import com.app.ed.api.entity.EdEligDtlsEntity;
import com.app.ed.api.entity.PlanEntity;
import com.app.ed.api.repository.CitizenRepository;
import com.app.ed.api.repository.CoTriggerRepository;
import com.app.ed.api.repository.DcCaseRepository;
import com.app.ed.api.repository.DcEducationRepository;
import com.app.ed.api.repository.DcIncomeRepository;
import com.app.ed.api.repository.DcKidsRepository;
import com.app.ed.api.repository.EdEligRepository;
import com.app.ed.api.repository.PlanRepository;

@Service
public class EdServiceImpl implements IEdService {

	@Autowired
	private DcCaseRepository dcCaseRepository;

	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private DcIncomeRepository dcIncomeRepository;

	@Autowired
	private DcKidsRepository dcKidsRepository;
	
	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private DcEducationRepository dcEducationRepository;
	
	@Autowired
	private CoTriggerRepository coTriggerRepository;
	
	@Autowired
	private EdEligRepository edEligRepository;
	
	boolean noKidsFlag = false;
	boolean kidsAgeFlag = true; 
	
	@Override
	public EligResponse determineEligibility(Integer caseNum) {
		EligResponse response = new EligResponse();

		Integer planId = null;
		String planName = null;
		Integer appId = null;
	
		
		Optional<DcCaseEntity> dcCaseEntity = dcCaseRepository.findById(caseNum);

		if (dcCaseEntity.isPresent()) {
			planId = dcCaseEntity.get().getPlanIdFk();
			appId = dcCaseEntity.get().getAppIdFk();
		}else {
			return null;
		}

		Optional<PlanEntity> planEntity = planRepository.findById(planId);

		if (planEntity.isPresent()) {
			planName = planEntity.get().getPlanName();
		}

		DcIncomeEntity dcIncomeEntity = dcIncomeRepository.findByCaseIdFk(caseNum);

		List<DcKidsEntity> kids = dcKidsRepository.findByCaseIdFk(caseNum);
		
		Optional<CitizenEntity> findById = citizenRepository.findById(appId);
		CitizenEntity citizenEntity = findById.get();
		response.setCaseNum(caseNum);
		response.setHolderName(citizenEntity.getFullName());
		response.setHolderSsn(citizenEntity.getSsn());

		if ("SNAP".equals(planName)) {
			
			if (dcIncomeEntity.getMonthlySalIncome() > 300) {
				response.setDenialReason("High Salary Income");
			}
			
		} else if ("CCAP".equals(planName)) {
			
			if (!kids.isEmpty()) {
				kids.forEach(kid -> {
					LocalDate dob = kid.getKidDob();
					LocalDate today = LocalDate.now();
					Period p = Period.between(dob, today);
					int years = p.getYears();
					if(years > 16) {
						kidsAgeFlag = false;
					}				
				});

			} else {
				response.setDenialReason("No Kids Available");
				noKidsFlag = true;
			}
			
			if(dcIncomeEntity.getMonthlySalIncome() > 300) {
				response.setDenialReason("High Salary Income");
			}
			
			if(dcIncomeEntity.getMonthlySalIncome() > 300 && noKidsFlag) {
				response.setDenialReason("High Salary Income + No Kids Available");
			}
			
			if(!kidsAgeFlag) {
				response.setDenialReason("Kids Age > 16");
			}
			
			if(dcIncomeEntity.getMonthlySalIncome() > 300 && !kidsAgeFlag) {
				response.setDenialReason("High Salary Income + Kids Age > 16");
			}
			

		} else if ("Medicaid".equals(planName)) {
			
			Double income = dcIncomeEntity.getMonthlySalIncome();
			Double propertyIncome = dcIncomeEntity.getPropertyIncome();
			Double rentIncome = dcIncomeEntity.getRentIncome();
			
			if(income > 300) {
				response.setDenialReason("High Salary Income");
			}
			if(propertyIncome > 0) {
				response.setDenialReason("Property Income Available");
			}
			if(rentIncome > 0) {
				response.setDenialReason("Rental Income Available");
			}
			if(propertyIncome > 0 && rentIncome > 0) {
				response.setDenialReason("Property + Rental Income Available");
			}
			if(income > 300 &&propertyIncome > 0 && rentIncome > 0) {
				response.setDenialReason("High Salary Income + Property + Rental Income Available");
			}
			
			
		} else if ("Medicare".equals(planName)) {
			LocalDate dob = citizenEntity.getDob();
			LocalDate today = LocalDate.now();
			
			Period p = Period.between(dob, today);
			int years = p.getYears();
			
			if(years < 65) {
				response.setDenialReason("Age < 65 years");
			}
			
		} else if ("RIW".equals(planName)) {
			DcEducationEntity dcEducationEntity = dcEducationRepository.findByCaseIdFk(caseNum);
			Integer graduationYear = dcEducationEntity.getGraduationYear();
			
			if(graduationYear <= 0) {
				response.setDenialReason("Not Graduated");
			}
			if(dcIncomeEntity.getMonthlySalIncome() > 300) {
				response.setDenialReason("Already an Employee");
			}
		}

		response.setPlanName(planName);
		
		if(response.getDenialReason() != null) {
			response.setPlanStatus("DENIED");
		}else {
			response.setPlanStatus("APPROVED");
			response.setPlanStartDate(LocalDate.now().plusDays(1));
			response.setPlanEndDate(LocalDate.now().plusMonths(3));
			response.setBenefitAmt(350.00);
		}
		
		EdEligDtlsEntity eligDtlsEntity = new EdEligDtlsEntity();
		BeanUtils.copyProperties(response, eligDtlsEntity);
		edEligRepository.save(eligDtlsEntity);
		
		CoTrgEntity coTrgEntity = new CoTrgEntity();
		coTrgEntity.setCaseNum(caseNum);
		coTrgEntity.setTrgStatus("Pending");
		
		coTriggerRepository.save(coTrgEntity);
		
		return response;
	}

}
