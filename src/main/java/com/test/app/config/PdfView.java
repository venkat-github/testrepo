package com.test.app.config;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.test.app.domain.UserDoctorVisitRecord;



public class PdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserDoctorVisitRecord dto = (UserDoctorVisitRecord)model.get("record");
		PdfPTable table = new PdfPTable(3);
		table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
		table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.getDefaultCell().setBackgroundColor(Color.lightGray);

		table.addCell("Symptoms");
		table.addCell("Prescription");
		table.addCell("Prescription");

		table.addCell(dto.getSymptoms());
		table.addCell(dto.getPrescription());
		table.addCell(dto.getPrescription());

		
		document.add(table);

	}
}
