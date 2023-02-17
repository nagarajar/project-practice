package com.app.co.api.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;


import org.springframework.stereotype.Component;

import com.app.co.api.entity.EdEligDtlsEntity;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Component
public class PdfGenerator {

	public byte[] generatePdf(EdEligDtlsEntity eligDtlsEntity)
			throws DocumentException, IOException {

		// Convert PdfDocument to byte array to save in db
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 0. Add OpenPdf Dependency
		// 1. Create Document(A4) and pass that into pdf writer
		Document document = new Document(PageSize.A4);
		// PdfWriter.getInstance(document, baos);
		PdfWriter pdfWriter = PdfWriter.getInstance(document, baos);

		// 2. Open the document and set the font style,size and color of heading
		document.open();
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(20);
		fontTitle.setColor(Color.BLUE);

		// 3. Give paragraph(title) name, align to center and add to page
		Paragraph p = new Paragraph(eligDtlsEntity.getPlanStatus() + " Notice", fontTitle);
		// Aligning the paragraph in the document
		p.setAlignment(Paragraph.ALIGN_CENTER);
		// Adding the created paragraph in the document
		document.add(p);

		// Add Blank Lines
		document.add(Chunk.NEWLINE);

		// body
		Font fontBody = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontBody.setSize(14);

		// Add Case Number
		Paragraph paragraph2 = new Paragraph("Case Number : " + eligDtlsEntity.getCaseNum(), fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph2);

		// Add Name
		Paragraph paragraph3 = new Paragraph("Name : " + eligDtlsEntity.getHolderName(), fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph3);

		// Add SSN
		Paragraph paragraph4 = new Paragraph("SSN : XXX XX " + eligDtlsEntity.getHolderSsn().toString().substring(5),
				fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph4);

		// Add Plan Name
		Paragraph paragraph5 = new Paragraph("Plan Name : " + eligDtlsEntity.getPlanName(), fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph5);

		// Add Plan Status
		Paragraph paragraph6 = new Paragraph("Plan Status : " + eligDtlsEntity.getPlanStatus(), fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph6);

		if ("Approved".equalsIgnoreCase(eligDtlsEntity.getPlanStatus())) {
			// Add Plan Start Date
			Paragraph paragraph7 = new Paragraph("Start Date : " + eligDtlsEntity.getPlanStartDate(), fontBody);
			paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(paragraph7);

			// Add Plan End Date
			Paragraph paragraph8 = new Paragraph("End Date : " + eligDtlsEntity.getPlanEndDate(), fontBody);
			paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(paragraph8);

			// Add Plan Benefit Amount
			Paragraph paragraph9 = new Paragraph("Benefit Amount : " + eligDtlsEntity.getBenefitAmt(), fontBody);
			paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(paragraph9);
		} else {
			// Add Plan Benefit Amount
			Paragraph paragraph10 = new Paragraph("Denial Reason : " + eligDtlsEntity.getDenialReason(), fontBody);
			paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(paragraph10);
		}

		// Add Blank Lines
		document.add(Chunk.NEWLINE);

		String pdfFooter = "DHS Office Address : #123, abcd, RI.\n" + "Contact Number : 123467894\n"
				+ "Website : https://rihs.com";
		// Add Plan Benefit Amount
		Paragraph paragraph11 = new Paragraph(pdfFooter, fontBody);
		paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
		document.add(paragraph11);

		// Close document
		document.close();

		pdfWriter.flush();

		byte[] pdfAsBytes = baos.toByteArray();

		return pdfAsBytes;
	}

}
