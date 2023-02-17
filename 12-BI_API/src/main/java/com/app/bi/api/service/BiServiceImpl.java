package com.app.bi.api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bi.api.entity.BiEntity;
import com.app.bi.api.entity.EdEligDtlsEntity;
import com.app.bi.api.repository.BiRepository;
import com.app.bi.api.repository.EdEligRepository;

@Service
public class BiServiceImpl implements IBiService {

	@Autowired
	private BiRepository biRepository;

	@Autowired
	private EdEligRepository edEligRepository;

	private StringBuilder builder = null;

	@Override
	public String generateBiInfo() {
		builder = new StringBuilder();
		
		List<EdEligDtlsEntity> edList = edEligRepository.findByPlanStatusAndPlanEndDateAfter("APPROVED",LocalDate.now());

		edList.forEach(edEntity -> {
			builder.append(edEntity.getCaseNum() + "," + edEntity.getHolderName() + "," + edEntity.getHolderSsn() + ","
					+ edEntity.getPlanName() + "," + edEntity.getBenefitAmt());
			builder.append(System.lineSeparator())	;
		});
		
		BiEntity biEntity = new BiEntity();
		biEntity.setCsvFile(builder.toString().getBytes());
		biRepository.save(biEntity);
		return "Benefit Issuence Generated Succefully";
	}

}
