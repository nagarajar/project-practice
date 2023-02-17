package com.app.co.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.co.api.entity.CitizenEntity;
import com.app.co.api.entity.CoBatchResponseEntity;
import com.app.co.api.entity.CoTrgEntity;
import com.app.co.api.entity.DcCaseEntity;
import com.app.co.api.entity.EdEligDtlsEntity;
import com.app.co.api.repository.CitizenRepository;
import com.app.co.api.repository.CoBatchResponseRepository;
import com.app.co.api.repository.CoTriggerRepository;
import com.app.co.api.repository.DcCaseRepository;
import com.app.co.api.repository.EdEligRepository;
import com.app.co.api.util.EmailUtils;
import com.app.co.api.util.PdfGenerator;
import com.lowagie.text.DocumentException;

@Service
public class CoServiceImpl implements ICoService {

	@Autowired
	private CoTriggerRepository coTriggerRepository;

	@Autowired
	private EdEligRepository edEligRepository;

	@Autowired
	private CitizenRepository citizenRepository;

	@Autowired
	private DcCaseRepository dcCaseRepository;

	@Autowired
	private CoBatchResponseRepository batchResponseRepository;

	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;


	Long successCount = 0l;
	Long failureCount = 0l;

	@Override
	public CoBatchResponseEntity processPendingTriggers() {

		// 1) Fetch all pending triggers from CO_TRIGGERS table
		List<CoTrgEntity> pendingCoTrgs = coTriggerRepository.findByTrgStatus("Pending");

		// 2) Process Each Pending Trigger
		pendingCoTrgs.forEach(coTrg -> {
			try {
				processTrigger(coTrg);
				successCount++;
			} catch (Exception e) {
				// TODO: handle exception
				failureCount++;
			}
		});

		CoBatchResponseEntity batchResponse = new CoBatchResponseEntity();
		batchResponse.setTotalRecords(Long.valueOf(pendingCoTrgs.size()));
		batchResponse.setSuccessCount(successCount);
		batchResponse.setFailureCount(failureCount);

		// 3) Send the final response ( Total Records Processed, Success Count, Failure Count ) from API
		return batchResponseRepository.save(batchResponse);
	}

	private void processTrigger(CoTrgEntity coTrg) throws DocumentException, IOException {

		Integer caseNum = coTrg.getCaseNum();
		CitizenEntity citizenEntity = null;

		// 2.1 ) Get Eligibility data based on caseNum from elig_dtls table
		EdEligDtlsEntity eligDtlsEntity = edEligRepository.findByCaseNum(caseNum);

		// 2.2 ) Get Citizen data based on AppId, so appId is available in dc_cases
		// table
		Optional<DcCaseEntity> caseEntityOptional = dcCaseRepository.findById(caseNum);
		if (caseEntityOptional.isPresent()) {
			Integer appId = caseEntityOptional.get().getAppIdFk();
			Optional<CitizenEntity> citizenEntityOptional = citizenRepository.findById(appId);
			if (citizenEntityOptional.isPresent()) {
				citizenEntity = citizenEntityOptional.get();
			}
		}

		// 2.3 ) Prepare PDF with Citizen Data + Eligibility data
		byte[] file = pdfGenerator.generatePdf(eligDtlsEntity);

		// 2.4 ) Store PDF into DB Table
		coTrg.setCoPdf(file);

		// 2.5 ) Send PDF to citizen email
		String citizenName = citizenEntity.getFullName();
		String to = citizenEntity.getEmail();
		String subject = "RIHIS PLAN STATUS NOTIFICATION";
		String body = readEmailBody("RIHIS_EMAIL_BODY.txt", citizenName);
		emailUtils.sendMail(to, subject, body, file);

		// 2.6 ) Update the trigger as completed and save
		coTrg.setTrgStatus("Completed");
		coTriggerRepository.save(coTrg);

	}


	private String readEmailBody(String filename, String citizenName) {
		// 2. Take a StringBuffer to store the file content bcs it is not Immutable
		StringBuilder sb = new StringBuilder();

		// 1.Files.lines is used to read all lines at a time introduced in 1.8v
		try (Stream<String> lines = Files.lines(Paths.get(filename))) {
			// Replace all the Dynamic variables based on user
			lines.forEach(line -> {
				line = line.replace("${FNAME}", citizenName);
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return as string
		return sb.toString();
	}

}
