package com.app.cw.api.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.cw.api.binding.DashboardResponse;
import com.app.cw.api.binding.LoginForm;
import com.app.cw.api.binding.UnlockAccForm;
import com.app.cw.api.entity.CwEntity;
import com.app.cw.api.repository.CwRepository;
import com.app.cw.api.repository.EdEligRepository;
import com.app.cw.api.util.EmailUtils;

@Service
public class CwServiceImpl implements ICwService {

	@Autowired
	private CwRepository cwRepository;

	@Autowired
	private EdEligRepository edEligRepository;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public String unlockAccount(UnlockAccForm unlockAccForm) {
		// 1. Get user details based on email
		CwEntity cwEntity = cwRepository.findByEmail(unlockAccForm.getEmail());

		// 2. Validate the tempPwd and reset to new pwd
		if (cwEntity != null && cwEntity.getPassword().equals(unlockAccForm.getTempPwd())) {
			cwEntity.setPassword(unlockAccForm.getNewPwd());
			// Change the accSatus
			cwEntity.setAccStatus("UNLOCKED");
			// Save user
			cwRepository.save(cwEntity);
			return "‘Account unlocked, please proceed with login";
		}
		return "Please enter the valid temporary password";
	}

	@Override
	public String login(LoginForm loginForm) {
		// 1. Get user details based on email and password
		CwEntity cwEntity = cwRepository.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPassword());

		// 2. If user obj is null
		if (cwEntity == null) {
			return "Invalid Credentials";
		}

		// 3. If Account is locked
		if (cwEntity.getAccStatus().equals("LOCKED")) {
			return "Your Account Is Locked";
		}

		// 4. validation of email and password is true and status is unlocked
		return "Your logged in sucessfully";
	}

	@Override
	public String forgotPwd(String email) {
		// 1. Get user details based on email
		CwEntity cwEntity = cwRepository.findByEmail(email);
		// 2. If user obj is null
		if (cwEntity == null) {
			return "No Account Fond...!";
		}
		// 3. If user found
		String subject = "Recovery Password";
		String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt", cwEntity);
		emailUtils.sendMail(email, subject, body);
		return "Password sent to registered email";
	}

	@Override
	public CwEntity updateProfile(CwEntity cwEntity) {
		if(cwRepository.existsById(cwEntity.getCwId())) {
			return cwRepository.save(cwEntity);	
		}
		return null;
	}

	@Override
	public DashboardResponse dashboardDisply() {
		Long noOfPlans = (long) edEligRepository.findAll().size();
		Long approvedCitizenCount = (long) edEligRepository.findByPlanStatus("APPROVED").size();
		Long deniedCitizenCount = (long) edEligRepository.findByPlanStatus("DENIED").size();
		
		DashboardResponse dashboardResponse = new DashboardResponse();
		dashboardResponse.setNoOfPlans(noOfPlans);
		dashboardResponse.setApprovedCitizenCount(approvedCitizenCount);
		dashboardResponse.setDeniedCitizenCount(deniedCitizenCount);
		
		return dashboardResponse;
	}
	
	private String readEmailBody(String filename, CwEntity cwEntity) {
		//2. Take a StringBuffer to store the file content bcs it is not Immutable
		StringBuilder sb = new StringBuilder();
		
		//1.Files.lines is used to read all lines at a time introduced in 1.8v
		try(Stream<String> lines = Files.lines(Paths.get(filename))) {
			//Replace all the Dynamic variables based on user
			lines.forEach(line ->{
				line = line.replace("${FNAME}", cwEntity.getFullName());
				line = line.replace("${TEMP_PWD}", cwEntity.getPassword());
				line = line.replace("${EMAIL}", cwEntity.getEmail());
				line = line.replace("${PWD}", cwEntity.getPassword());
				
				sb.append(line);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Return as string
		return sb.toString();
	}

}
