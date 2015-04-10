package com.nexera.core.service.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.nexera.common.vo.LoanApplicationReceiptVO;
import com.nexera.core.service.ReceiptPdfService;

@Component
public class ReceiptPdfServiceImpl implements ReceiptPdfService ,InitializingBean {
	
	@Value("${receipt_logo_url}")
	private String logoUrl;
	
	private static final Font genFont = new Font(Font.getFamily("Segoe UI"), 9,
			Font.NORMAL, new BaseColor(Color.decode("#222649")));
	private static final Font genBold = new Font(Font.getFamily("Segoe UI"), 10,
			Font.BOLD, new BaseColor(Color.decode("#222649")));

	private static final Font capFont = new Font(Font.getFamily("Segoe UI"),
			14, Font.BOLD, new BaseColor(Color.decode("#222649")));

	private static final Font redFont = new Font(Font.getFamily("Segoe UI"),
			12, Font.NORMAL, BaseColor.RED);

	private static final Font h2Font = new Font(Font.getFamily("Segoe UI"), 12,
			Font.NORMAL, new BaseColor(Color.decode("#222649")));

	public ByteArrayOutputStream generateReceipt(
			LoanApplicationReceiptVO applicationReceiptVO) {

		ByteArrayOutputStream pdfInMemory = new ByteArrayOutputStream();
		Rectangle pageSize = new Rectangle(595, 842);
		pageSize.setBackgroundColor(new BaseColor(Color.decode("#F2F3F4")));

		Document document = new Document(pageSize);

		try {
			PdfWriter.getInstance(document, pdfInMemory);
			document.open();

			Image im = null;
			im = Image
					.getInstance(logoUrl);
			im.scaleAbsolute(45f, 45f);
			
			PdfPTable table = new PdfPTable(2);
			float[] colWidths = { 25, 75 };
			table.setWidths(colWidths);
			table.getDefaultCell().setBorder(0);
			
			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(im);
			cell1.disableBorderSide(Rectangle.BOX);
			cell1.setBackgroundColor(new BaseColor(Color.decode("#428bca")));
			cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			table.addCell(cell1);

			PdfPCell cell2 = new PdfPCell();
			cell2.setBorder(Rectangle.NO_BORDER);
	
			
			Paragraph p1 = new Paragraph("Application Fee Receipt", capFont);
			p1.setAlignment(Element.ALIGN_LEFT);
			p1.setLeading(0, 1);
			cell2.addElement(p1);
			cell2.setBackgroundColor(new BaseColor(Color.decode("#428bca")));
			cell2.setMinimumHeight(30);
			cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell2);
			
			table.setWidthPercentage(100);	
			document.add(table);
			
			LineSeparator ls = new LineSeparator();
			ls.setLineWidth(10f);
			ls.setLineColor(new BaseColor(34, 38, 73));
			document.add(ls);

			document.add(new Paragraph("Customer: "
					+ applicationReceiptVO.getCustomerName(), genBold));
			document.add(new Paragraph("\n Receipt / Tax Invoice", genBold));
			document.add(new Paragraph("Loan Number: "
					+ applicationReceiptVO.getLoanId(), genFont));
			document.add(new Paragraph("Invoice #: "
					+ applicationReceiptVO.getInvoice(), genFont));
			document.add(new Paragraph(applicationReceiptVO.getCreatedAt()
					.toString(), genFont));
			document.add(new Paragraph("Type Of Card: "
					+ applicationReceiptVO.getTypeOfCard(), genFont));

			document.add(new Paragraph("Payment Status: "
					+ applicationReceiptVO.getPaymentStatus(), genFont));
			document.add(Chunk.NEWLINE);
		
			document.add(new Paragraph(
					"\nThis is to confirm that amount $"
							+ applicationReceiptVO.getFee()
							+ " has been received as Application fee from "
							+ applicationReceiptVO.getCustomerName()
							+ ". Thanks for the payment. Your loan will move to next stage after the transaction is confirmed.",
							h2Font));
			document.close();
		} catch (Exception exception) {
			System.err.println("Exception" + exception);
		}
		return pdfInMemory;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
