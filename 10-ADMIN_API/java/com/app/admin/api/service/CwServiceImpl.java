package com.app.admin.api.service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.admin.api.binding.CwRequest;
import com.app.admin.api.binding.CwResponse;
import com.app.admin.api.entity.CwEntity;
import com.app.admin.api.repository.CwRepository;
import com.app.admin.api.util.EmailUtils;

@Service
public class CwServiceImpl implements ICwService {
	
	private Random random = new Random();
	
	@Autowired
	private CwRepository cwRepository;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Integer createCw(CwRequest cwRequest) {
		
		String checkEmail = checkEmail(cwRequest.getEmail());
		
		if (checkEmail.equalsIgnoreCase("UNIQUE")) {
			// 1. Copy the data from binding obj to entity obj
			CwEntity cwEntity = new CwEntity();
			BeanUtils.copyProperties(cwRequest, cwEntity);
			// 2. Generate and set random password
			cwEntity.setPassword(generateRandomPwd());
			// 3. Set Account as LOCKED
			cwEntity.setAccStatus("LOCKED");
			// 4. Save the entity
			cwEntity = cwRepository.save(cwEntity);
			// 5. Send email to unlock account
			String to = cwEntity.getEmail();
			String subject = "Unlock CW Account";
			String body = readEmailBody("UNLOCK_ACC_EMAIL_BODY.txt", cwEntity);
			emailUtils.sendMail(to, subject, body);
			return cwEntity.getCwId();
		}
		return null;
	
	}
	
	private String checkEmail(String email) {
		CwEntity cwEntity = cwRepository.findByEmail(email);
		if (cwEntity == null) {
			return "UNIQUE";
		}
		return "DUPLICATE";
	}

	@Override
	public CwResponse findByCwId(Integer id) {
		Optional<CwEntity> cwEntity = cwRepository.findById(id);
		if(cwEntity.isPresent()) {
			CwResponse cwResponse =  new CwResponse();
			BeanUtils.copyProperties(cwEntity.get(), cwResponse);
			return cwResponse;
		}
		return null;
	}

	@Override
	public List<CwEntity> getAllCws() {
		return cwRepository.findAll();
	}

	@Override
	public CwEntity updateCw(CwResponse cwResponse) {
		if(cwRepository.existsById(cwResponse.getCwId())) {
			CwEntity cwEntity = new CwEntity();
			BeanUtils.copyProperties(cwResponse, cwEntity);
			return cwRepository.save(cwEntity);	
		}
		return null;
	}

	@Override
	public String activateOrDeactivateCw(Integer id) {
		String msg = null;
		Optional<CwEntity> findById = cwRepository.findById(id);
		if(findById.isPresent()) {
			CwEntity cwEntity = findById.get();
			if(cwEntity.isActive() == true) {
				cwEntity.setActive(false);	
				msg = "Deactivated";
			}else {
				cwEntity.setActive(true);
				msg = "Activated";
			}
			cwRepository.save(cwEntity);
		}
		return msg;
	}

	@Override
	public String deleteByCwId(Integer id) {
		String msg = null;
		Optional<CwEntity> findById = cwRepository.findById(id);
		if(findById.isPresent()) {
			cwRepository.deleteById(id);
			msg = "Deleted";
		}
		return msg;
	}

	private String generateRandomPwd() {
		String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&";
		StringBuilder sb = new StringBuilder();
		int pwdLength = 8;
		for (int i = 1; i <= 8; i++) {
			int index = random.nextInt(pwdLength);
			sb.append(text.charAt(index));
		}

		return sb.toString();
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
