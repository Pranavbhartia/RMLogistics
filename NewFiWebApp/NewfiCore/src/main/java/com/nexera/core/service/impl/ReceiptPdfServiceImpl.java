package com.nexera.core.service.impl;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

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
public class ReceiptPdfServiceImpl implements ReceiptPdfService {

	private static final Font genFont = new Font(Font.getFamily("Segoe UI"), 8,
			Font.NORMAL, new BaseColor(Color.decode("#222649")));
	private static final Font genBold = new Font(Font.getFamily("Segoe UI"), 9,
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
					.getInstance("/home/admin/Desktop/Projects/Nexara/Workspacefrom master/nexera_newfi/NewFiWebApp/NewfiWeb/src/main/webapp/resources/images/logo.png");
			im.scaleAbsolute(30f, 30f);
			PdfPTable tabletmp = new PdfPTable(1);
			tabletmp.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			tabletmp.setWidthPercentage(100);
			PdfPTable table = new PdfPTable(2);
			float[] colWidths = { 20, 45 };
			table.setWidths(colWidths);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

			PdfPCell cell1 = new PdfPCell();
			cell1.setBorder(Rectangle.NO_BORDER);
			cell1.addElement(im);
			table.addCell(cell1);

			PdfPCell cell2 = new PdfPCell();
			cell2.setBorder(Rectangle.NO_BORDER);
			Paragraph p1 = new Paragraph("Application Fee Receipt", capFont);
			p1.setAlignment(Element.ALIGN_CENTER);
			cell2.addElement(p1);
			table.addCell(cell2);

			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setHorizontalAlignment(
					Element.ALIGN_JUSTIFIED);
			tabletmp.addCell(table);
			document.add(tabletmp);

			Paragraph p3 = new Paragraph();

			LineSeparator ls = new LineSeparator();
			ls.setLineWidth(5f);
			ls.setLineColor(new BaseColor(34, 38, 73));

			p3.add(new Chunk(ls));
			document.add(p3);

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
			Paragraph p4 = new Paragraph();
			p4.getExtraParagraphSpace();
			p4.add(new Chunk(new LineSeparator()));

			document.add(p4);
			document.add(new Paragraph(
					"\nThis is to confirm that amount $"
							+ applicationReceiptVO.getFee()
							+ " has been received as Application fee from "
							+ applicationReceiptVO.getCustomerName()
							+ ". Thanks for the payment. Your loan will move to next stage after the transaction is confirmed.",
							h2Font));
			PdfPTable tbl = new PdfPTable(2);
			document.add(Chunk.NEWLINE);
			document.add(tbl);
			document.add(Chunk.NEWLINE);
			document.add(p4);
			document.close();
		} catch (Exception exception) {
			System.err.println("Exception" + exception);
		}
		return pdfInMemory;
	}
}
