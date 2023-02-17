package com.app.co.api.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {
	
	@Autowired(required=false)
	private JavaMailSender mailSender;
	
	public boolean sendMail(String to, String subject, String body, byte[] pdfByteArray) {
		boolean isSent = false;
		try {
			//1. MimeMessage used to different styles, fonts, html tags and attaching doc's
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			//2. With the help of MimeMessageHelper we can construct mail
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom("rajanagaraja36@gmail.com");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			

	        InputStreamSource inputStreamSource = new ByteArrayResource(pdfByteArray);
	        helper.addAttachment("rihis_plan_status_notice.pdf", inputStreamSource);
	        
			mailSender.send(mimeMessage);
			isSent = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return isSent;
		
	}
	

}
