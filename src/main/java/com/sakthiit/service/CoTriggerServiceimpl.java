package com.sakthiit.service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sakthiit.entity.CitizenAppEntiry;
import com.sakthiit.entity.CoTriggersEntity;
import com.sakthiit.entity.DcCasesEntity;
import com.sakthiit.entity.EligDtlsEntity;
import com.sakthiit.repo.CitizenAppRepositery;
import com.sakthiit.repo.CoTriggersRepo;
import com.sakthiit.repo.DcCasesRepo;
import com.sakthiit.repo.EligDtlsRepo;
import com.sakthiit.utils.EmailUtils;

@Service
public class CoTriggerServiceimpl implements CoTriggerService {

	@Autowired
	private CoTriggersRepo triggersRepo;
	@Autowired
	private EligDtlsRepo eligDtlsRepo;
	
	@Autowired
	private DcCasesRepo  casesRepo;

	@Autowired
	private CitizenAppRepositery citizenAppRepo;
	
	
	
	@Autowired
	EmailUtils emailUtil;

	public String processPendingTriggerDtls() {

		List<CoTriggersEntity> trgEntity = triggersRepo.findByTrgStatus("Pending");
		Long caseNum = null;
		String emailTo = null;
		String emailSubject = "Appliction Notification";
		String body = null;
		String fileName = null;
		String source = null;
		Integer appId = null;

		String citizenName = null;
		
		String path = "D:/Workspace-Spring-Course/JRTP/13_CO_API/";
		for (CoTriggersEntity entity : trgEntity) {

			caseNum = entity.getCaseNum();
			
			Optional<DcCasesEntity> findById = casesRepo.findById(caseNum);
			
			if(findById.isPresent()) {
				DcCasesEntity caseEntity = findById.get();
				appId = caseEntity.getAppId();
			
			}
			
			Optional<CitizenAppEntiry>  findByIdCitizen = citizenAppRepo.findById(appId);
			
			if(findByIdCitizen.isPresent()) {
				CitizenAppEntiry citizenAppEntiry = findByIdCitizen.get();
				emailTo = citizenAppEntiry.getEmail();
				citizenName = citizenAppEntiry.getFullname();
			}
			
			body = "Hi "+citizenName+",<br> Please find the attachent file for insurance application.";
			
			List<EligDtlsEntity> dtlsEntities = eligDtlsRepo.findByCaseNum(caseNum);

			for (EligDtlsEntity eligDtlsEntity : dtlsEntities) {

				if (generatePdf(eligDtlsEntity)) {
					entity.setTrgStatus("Completed");
					try {
						source = path + caseNum + ".pdf";
						fileName = caseNum + ".pdf";

						entity.setCoPdf(getByteArrayFromFile(path + caseNum + ".pdf"));
						emailUtil.sendMail(emailTo, body, emailSubject, fileName, source);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					triggersRepo.save(entity);
				}
			}

		}

		return null;
	}

	private boolean generatePdf(EligDtlsEntity eligDtlsEntity) {

		Document document = new Document(PageSize.A4);

		boolean isFileCreated = false;
		try {
			PdfWriter.getInstance(document, new FileOutputStream(eligDtlsEntity.getCaseNum() + ".pdf"));

			document.open();

			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setSize(18);
			font.setColor(Color.BLUE);

			Paragraph p = new Paragraph("Acknowledgement", font);
			p.setAlignment(Paragraph.ALIGN_CENTER);

			document.add(p);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100f);
			table.setWidths(new float[] { 1.5f, 3.5f });
			table.setSpacingBefore(10);

			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(Color.BLUE);
			cell.setPadding(5);

			font = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(Color.WHITE);

			cell.setPhrase(new Phrase("Citizen Name", font));
			table.addCell(cell);
			table.addCell(eligDtlsEntity.getHolderName());

			cell.setPhrase(new Phrase("Plan Name", font));
			table.addCell(cell);
			table.addCell(eligDtlsEntity.getPlanName());

			cell.setPhrase(new Phrase("Plan Status", font));
			table.addCell(cell);
			table.addCell(eligDtlsEntity.getPlanStatus());

			cell.setPhrase(new Phrase("Plan Start Date", font));
			table.addCell(cell);
			table.addCell(String.valueOf(eligDtlsEntity.getPlanStartDate()));

			cell.setPhrase(new Phrase("Plan End Date:", font));
			table.addCell(cell);
			table.addCell(String.valueOf(eligDtlsEntity.getPlanEndDate()));

			cell.setPhrase(new Phrase("Benefit Amt:", font));
			table.addCell(cell);
			table.addCell(String.valueOf(eligDtlsEntity.getBenefitAmt()));

			cell.setPhrase(new Phrase("Denial Reason:", font));
			table.addCell(cell);
			table.addCell(eligDtlsEntity.getDenialReson());

			document.add(table);

			isFileCreated = true;

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		document.close();

		return isFileCreated;
	}

	private byte[] getByteArrayFromFile(String fileName) throws IOException {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final InputStream in = new FileInputStream(fileName);
		final byte[] buffer = new byte[500];

		int read = -1;
		while ((read = in.read(buffer)) > 0) {
			baos.write(buffer, 0, read);
		}
		in.close();

		return baos.toByteArray();
	}

}
