package com.nexera.web.rest.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.UserVO;
import com.nexera.core.service.UserProfileService;

@Component
public class PreQualificationletter {

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(PreQualificationletter.class);

	@Autowired
	private UserProfileService userProfileService;

	public void sendPreQualificationletter(LoanAppFormVO loaAppFormVO,
	        String thirtyYearRateVoDataSet,
	        HttpServletRequest httpServletRequest) {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		createPdf(loaAppFormVO, thirtyYearRateVoDataSet, byteArrayOutputStream,
		        httpServletRequest);
		try {
			userProfileService.sendEmailPreQualification(loaAppFormVO,
			        byteArrayOutputStream);
		} catch (InvalidInputException | UndeliveredEmailException e) {

			LOGGER.error("sendPreQualificationletter ", e);
		}

	}

	public String thirtyYearRateVoDataSet(String lockRateData) {

		JSONObject thirtyYearRateVoDataSet = null;
		LOGGER.debug("In thirtyYearRateVoDataSet. lockRateData is: "
		        + lockRateData);
		JSONArray jsonArray = new JSONArray(lockRateData);

		for (int i = 1; i < jsonArray.length(); i++) {
			org.json.JSONObject item = jsonArray.getJSONObject(i);
			String loanDuration = item.getString("loanDuration");
			if (loanDuration.indexOf("30") == 0) {
				JSONArray rateVOArray = item.getJSONArray("rateVO");
				System.out.println(rateVOArray.length());
				thirtyYearRateVoDataSet = rateVOArray.getJSONObject(rateVOArray
				        .length() / 2);
				break;
			}

		}

		return thirtyYearRateVoDataSet.toString();
	}

	public static void createPdf(LoanAppFormVO loaAppFormVO,
	        String thirtyYearRateVoData, OutputStream outputStream,
	        HttpServletRequest httpServletRequest) {

		// OutputStream file;
		Document document = new Document();
		try {
			// file = new FileOutputStream(new
			// File("D:\\Abhishek-Project\\RareMile_Projects\\Nexera\\pdf\\test.pdf"));

			JSONObject thirtyYearRateVoDataSet = new JSONObject(
			        thirtyYearRateVoData);

			@SuppressWarnings("deprecation")
			String basePath = httpServletRequest.getRealPath("/");

			String absoluteFilePath = "";
			absoluteFilePath = basePath + File.separator + "resources"
			        + File.separator + "images" + File.separator
			        + "newfi_logo_big.png";
			File file = new File(absoluteFilePath);

			PdfWriter writer = PdfWriter.getInstance(document, outputStream);

			// Inserting Image in PDF
			Image image = Image.getInstance(file.getAbsolutePath());

			// Open Declaration void com.itextpdf.text.Image.scaleAbsolute(float
			// newWidth, float newHeight)
			image.scaleAbsolute(120f, 37f);// image width,height
			Rectangle rect = new Rectangle(30, 30, 550, 800);
			writer.setBoxSize("art", rect);
			HeaderFooterPageEvent event = new PreQualificationletter.HeaderFooterPageEvent();
			writer.setPageEvent(event);
			// Now Insert Every Thing Into PDF Document
			document.open();// PDF document opened........

			image.setAbsolutePosition(210f, 800f);
			document.add(image);

			document.add(Chunk.NEWLINE);

			SimpleDateFormat formater = new SimpleDateFormat("MM/dd/YYYY");
			formater.format(new Date());
			document.add(new Paragraph(formater.format(new Date())));

			document.add(Chunk.NEWLINE);

			String name = loaAppFormVO.getUser().getDisplayName();
			String Street = loaAppFormVO.getUser().getCustomerDetail()
			        .getAddressStreet();
			String city = loaAppFormVO.getUser().getCustomerDetail()
			        .getAddressCity();
			String state = loaAppFormVO.getUser().getCustomerDetail()
			        .getAddressState();
			String zip = loaAppFormVO.getUser().getCustomerDetail()
			        .getAddressZipCode();

			String loanAmount = dollerFormatedAmount(loaAppFormVO
			        .getPurchaseDetails().getLoanAmount());
			String loanProgram = "30 Year Fixed";
			String term = "360 months";
			String rate = thirtyYearRateVoDataSet.getString("teaserRate")
			        + " %";
			String aPR = thirtyYearRateVoDataSet.getString("APR") + " %";

			String propcity = loaAppFormVO.getPropertyTypeMaster()
			        .getPropCity();
			String propstate = loaAppFormVO.getPropertyTypeMaster()
			        .getPropState();
			String propzip = loaAppFormVO.getPropertyTypeMaster()
			        .getHomeZipCode();

			UserVO internalUser = findInternalUserDetailVO(loaAppFormVO);
			String loanAdvisorName = "";
			String loanAdvisor = "";
			String directPhoneNum = "";
			String NMLS = "";

			if (null != internalUser) {

				loanAdvisorName = internalUser.getDisplayName();
				loanAdvisor = "Senior Loan Advisor";
				if (null != internalUser.getPhoneNumber()
				        && internalUser.getPhoneNumber() != " ")
					directPhoneNum = "Phone No : "
					        + internalUser.getPhoneNumber();
				else {
					directPhoneNum = "";
				}
				if (null != internalUser.getInternalUserDetail().getNmlsID())
					NMLS = "NMLS ID :"
					        + internalUser.getInternalUserDetail().getNmlsID();
				else {
					NMLS = "";
				}
			} else {
				loanAdvisorName = "Sales Manager";
			}

			String text = "Congratulations! Based on the financial information you provided newfi,  you have been pre-qualified for a loan with the following terms:";

			document.add(new Paragraph(name));
			document.add(new Paragraph(Street));
			document.add(new Paragraph(city + ", " + state + ", " + zip));

			document.add(Chunk.NEWLINE);

			document.add(new Paragraph("Dear " + name + ","));
			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(text));

			document.add(Chunk.NEWLINE);

			Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);

			/* Loan Amount */

			Paragraph paraloanAmount = new Paragraph("Loan Amount :"
			        + loanAmount, font);
			paraloanAmount.setIndentationLeft(120);
			document.add(paraloanAmount);

			document.add(Chunk.NEWLINE);

			/* Loan Program */

			Paragraph paraloanProgram = new Paragraph("Loan Program :"
			        + loanProgram, font);
			paraloanProgram.setIndentationLeft(120);
			document.add(paraloanProgram);

			document.add(Chunk.NEWLINE);

			/* Term */

			Paragraph paraTerm = new Paragraph("Term :" + term, font);
			paraTerm.setIndentationLeft(120);
			document.add(paraTerm);

			document.add(Chunk.NEWLINE);

			/* Interest Rate */

			Paragraph paraRate = new Paragraph("Interest Rate : " + rate, font);
			paraRate.setIndentationLeft(120);
			document.add(paraRate);

			document.add(Chunk.NEWLINE);

			/* Estimate APR */

			Paragraph paraApr = new Paragraph("Estimate APR :" + aPR, font);
			paraApr.setIndentationLeft(120);
			document.add(paraApr);

			document.add(Chunk.NEWLINE);

			/* Property Address */

			Paragraph paraAddress = new Paragraph("Property Address :"
			        + propcity + "," + propstate + "," + propzip, font);
			paraAddress.setIndentationLeft(120);
			document.add(paraAddress);

			document.add(Chunk.NEWLINE);

			String test2 = "This pre-qualification was based on the information you provided newfi. A final approval will be determined after we receive and validate:  a completed loan application, property appraisal, income and assets, credit report, all subsequent loan conditions by the newfi underwriter, and all closing items required prior to the closing of your loan.";
			document.add(new Paragraph(test2));

			document.add(Chunk.NEWLINE);

			String text3 = "We are excited to partner with you in buying your new home. I am here to support you through this entire process so please call me directly with any questions.";
			document.add(new Paragraph(text3));

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph("Thank You,"));

			document.add(Chunk.NEWLINE);
			document.add(new Paragraph(loanAdvisorName));
			document.add(new Paragraph(loanAdvisor));
			document.add(new Paragraph(NMLS));
			document.add(new Paragraph(directPhoneNum));

			// file.close();

		} catch (FileNotFoundException e) {

			LOGGER.error("Exception with file not found", e);

		} catch (DocumentException e) {
			LOGGER.error("DocumentException with file not found", e);

		} catch (MalformedURLException e) {
			LOGGER.error("MalformedURLException with file not found", e);
		} catch (IOException e) {
			LOGGER.error("IOException with file not found", e);
		} finally {
			try {
				document.close();
			} catch (Exception e) {
				LOGGER.error("Finally block file could not be closed", e);
			}

		}

	}

	static class HeaderFooterPageEvent extends PdfPageEventHelper {
		public void onStartPage(PdfWriter writer, Document document) {

		}

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle page = document.getPageSize();
			PdfPTable foot = new PdfPTable(1);
			PdfPCell footCell = new PdfPCell(
			        new Phrase(
			                "2200 Powell Street, Suite 340 | Emeryville, CA 94608 | 888-316-3934",
			                FontFactory.getFont(FontFactory.HELVETICA, 6,
			                        Font.BOLDITALIC)));
			footCell.setBorder(0);
			footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			foot.addCell(footCell);
			footCell = new PdfPCell(new Phrase(
			        "newfi is a dba of Nexera Holding LLC | NMLS ID 1231237",
			        FontFactory.getFont(FontFactory.HELVETICA, 6,
			                Font.BOLDITALIC)));

			footCell.setBorder(0);
			footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			foot.addCell(footCell);
			foot.setTotalWidth(page.getWidth());
			foot.writeSelectedRows(0, -1, document.leftMargin() - 30,
			        document.bottomMargin(), writer.getDirectContent());

		}
	}

	public static String dollerFormatedAmount(String amount) {

		// Get a currency formatter for the current locale.
		NumberFormat fmt = NumberFormat.getCurrencyInstance();
		if (amount != null) {
			return fmt.format(Double.parseDouble(amount));
		}
		return amount;

	}

	public static UserVO findInternalUserDetailVO(LoanAppFormVO loaAppFormVO) {

		UserVO internalUser = null;
		List<UserVO> loanTeam = loaAppFormVO.getLoan().getLoanTeam();
		if (null != loanTeam && loanTeam.size() > 0) {
			for (UserVO user : loanTeam) {
				if (null != user.getInternalUserDetail()
				        && null != user.getInternalUserDetail()
				                .getInternalUserRoleMasterVO()
				        && user.getInternalUserDetail()
				                .getInternalUserRoleMasterVO().getRoleName()
				                .equalsIgnoreCase("LM") && user.getStatus()!= CommonConstants.STATUS_IS_DELETE) {
					/* this user would be either realtor or LM */
					internalUser = user;

					break;
				}
			}
		}
		return internalUser;
	}

}
