package com.nexera.web.rest.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Annotation;
import com.itextpdf.text.BaseColor;
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
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPCellEvent;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.mongodb.io.ByteStream;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.entity.Template;
import com.nexera.common.enums.InternalUserRolesEum;
import com.nexera.common.enums.UserRolesEnum;
import com.nexera.common.exception.InvalidInputException;
import com.nexera.common.exception.UndeliveredEmailException;
import com.nexera.common.vo.GeneratePdfVO;
import com.nexera.common.vo.LoanAppFormVO;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.email.EmailVO;
import com.nexera.core.batchprocessor.LoanBatchProcessor;
import com.nexera.core.service.SendEmailService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.TemplateService;
import com.nexera.core.service.UserProfileService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.web.rest.util.GeneratePdfForQuickQuote.CustomCell;


@Component
public class GeneratePdfForQuickQuote {
	
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeneratePdfForQuickQuote.class);

	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private TemplateService templateService;
	
	@Value("${profile.url}")
	private String baseUrl;
	
	@Autowired
	private S3FileUploadServiceImpl s3FileUploadService;

	private static final Logger LOG = LoggerFactory
	        .getLogger(GeneratePdfForQuickQuote.class);
    private Document document;
    private PdfWriter writer;
    public GeneratePdfForQuickQuote() {
    }

     static class HeaderFooterPageEventForPdf extends PdfPageEventHelper {
		public void onStartPage(PdfWriter writer, Document document) {

		}

		public void onEndPage(PdfWriter writer, Document document) {
			Rectangle page = document.getPageSize();
			PdfPTable foot = new PdfPTable(1);
			PdfPCell footCell = new PdfPCell(
			        new Phrase(
			                "",
			                FontFactory.getFont(FontFactory.HELVETICA, 6,
			                        Font.BOLDITALIC)));
			footCell.setBorder(0);
			footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			foot.addCell(footCell);
			footCell = new PdfPCell(new Phrase(
			        "Newfi dba of Nexera Holding LLC  | NMLS ID 1231327  |  Equal Housing Lender",
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
    
    public void openPdf(ByteArrayOutputStream byteArrayOutputStream) {
        boolean status = false;
        try {
            	document = new Document();
            	//writer =  PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            	writer =  PdfWriter.getInstance(document, byteArrayOutputStream);
            	Rectangle rect = new Rectangle(30, 30, 550, 800);
                writer.setBoxSize("art", rect);  
                HeaderFooterPageEventForPdf event = new GeneratePdfForQuickQuote.HeaderFooterPageEventForPdf();
    			writer.setPageEvent(event);
                document.open();
            
        }  catch (DocumentException ex) {
        	LOGGER.error("Error in opening pdf under quick quote:", ex);
        } catch (Exception ex) {
        	LOGGER.error("Error in opening pdf under quick quote, catch-2:", ex);
        }
    }

    public void closePdf() {
        document.close();
    }
    
    public String addDollarAndComma(String number)
    {
    	double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("$#,###.00");
		return formatter.format(amount);
    }

    public void generatePdf(GeneratePdfVO generatePdfVO, HttpServletRequest httpServletRequest, ByteArrayOutputStream byteOutput,UserVO user) throws DocumentException, IOException {
    	Paragraph logoParagraph = new Paragraph();
    	Paragraph horizontalLine = new Paragraph();
        Paragraph paragraph = new Paragraph();
        Paragraph imageParagraph = new Paragraph();
        Font font = new Font(Font.FontFamily.TIMES_ROMAN, 7);
        Font fontWithBigSize = new Font(Font.FontFamily.TIMES_ROMAN, 8);
        Font emailIdFont = new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.UNDERLINE);
        emailIdFont.setColor(BaseColor.BLUE);
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN  , 7, Font.BOLD);
        Font boldFontWithBigSize = new Font(Font.FontFamily.TIMES_ROMAN  , 9, Font.BOLD);
        PdfPCell cell = null;
        @SuppressWarnings("deprecation")
		String basePath = httpServletRequest.getRealPath("/");
  
        try{
        String absoluteFilePath = "";
		absoluteFilePath = basePath + File.separator + "resources"
		        + File.separator + "images" + File.separator
		        + "newfi_logo_big.png";
        File file = new File(absoluteFilePath);
        Image image = Image.getInstance(file.getAbsolutePath());
        image.scaleAbsolute(120f, 37f);// image width,height
        image.setAbsolutePosition(210f, 780f);    
        logoParagraph.add(Chunk.NEWLINE);
        logoParagraph.add(image);
        document.add(logoParagraph);

        }
        catch(Exception e)
        {
        	LOGGER.error("Error in reading image section:", e);
        }
        
        PdfPTable imageTable = new PdfPTable(3);
        float[] imageTablecolumnWidths = {1f, 2f, 3f};
        imageTable.setWidths(imageTablecolumnWidths);
        imageTable.setWidthPercentage(100.0f);
        byte[] bytes = null ;
        Image profileImage = null;
        try{
        	File file = null;

    		String absoluteFilePath = "";
        	if(user.getPhotoImageUrl() != null){
        		 bytes = s3FileUploadService.getInputStreamOfFile(user.getPhotoImageUrl());
        		 profileImage = Image.getInstance(bytes);
        	}else{
        		absoluteFilePath = basePath + File.separator + "resources"
          		        + File.separator + "images" + File.separator
          		        + "profilePDF1.png";
     		     file = new File(absoluteFilePath);
     		     profileImage = Image.getInstance(file.getAbsolutePath());
        	}
        profileImage.scaleAbsolute(200f, 200f);	
        float w = profileImage.getScaledWidth();
        float h = profileImage.getScaledHeight();
        PdfTemplate t = writer.getDirectContent().createTemplate(200, 200);
        t.ellipse(0, 0, 190, 200);
        t.clip();
        t.newPath();
        t.addImage(profileImage, w, 0, 0, h, 0, 0);
        Image clipped = Image.getInstance(t);
        clipped.scalePercent(30);
       
        cell = new PdfPCell(clipped, false);
        cell.setPaddingTop(5f);
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setRowspan(2);
        imageTable.addCell(cell);

        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
      
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = user.getPhoneNumber();
        if (firstName == null)
        	firstName = "N/A";
        if (lastName == null)
        	lastName = "N/A";
        if (phoneNumber == null)
        	phoneNumber = "N/A";
 
        cell = new PdfPCell();
        cell.addElement(new Phrase("  "+firstName+" "+lastName+"\n  Senior Loan Advisor\n  NMLS ID 123456\n  "+phoneNumber, fontWithBigSize));
        cell.addElement(new Phrase("  "+user.getEmailId(), emailIdFont));
        cell.setBorder(PdfPCell.NO_BORDER);
        imageTable.addCell(cell);
        
        DateFormat fmt = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
        String todaysDate = fmt.format(new Date());
        String customerFirstName = generatePdfVO.getFirstName();
        String customerLastName = generatePdfVO.getLastName();
        cell = new PdfPCell(new Phrase("Loan Summary Prepared for "+customerFirstName+" "+customerLastName+"\n\n    "+todaysDate,boldFontWithBigSize));
         
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(PdfPCell.NO_BORDER);
        imageTable.addCell(cell);
   
        
        cell = new PdfPCell(new Phrase("\n  Newfi\n  2200 Powell St, Suite 340\n  Emeryville, CA 94608\n  NMLS ID 1231327",fontWithBigSize));
        cell.setBorder(PdfPCell.NO_BORDER);
        imageTable.addCell(cell);
        
        String imageFilePath = "";
		imageFilePath = basePath + File.separator + "resources"
		        + File.separator + "images" + File.separator
		        + "rsz_button.png";
        File imageFilePathLocation = new File(imageFilePath);

 
        Image buttonImage = Image.getInstance(imageFilePathLocation.getAbsolutePath());
        buttonImage.setAnnotation(new Annotation(5, 5, 5, 5,  baseUrl)); 
     
        buttonImage.scaleAbsolute(130f, 40f);
   
        cell = new PdfPCell(buttonImage, false);
        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(PdfPCell.NO_BORDER);
        imageTable.addCell(cell);
        imageParagraph.add(Chunk.NEWLINE);
        imageParagraph.add(Chunk.NEWLINE);
        imageParagraph.add(imageTable);
        document.add(imageParagraph);
        
        PdfPTable horizontalLineTable = new PdfPTable(2);
        horizontalLineTable.setWidthPercentage(100.0f);
        cell = new PdfPCell(new Phrase(""));
        cell.setColspan(2);
        cell.setBorder(Rectangle.TOP);
        cell.setBorderWidth(2f); 
        cell.setBorderColor(BaseColor.BLUE);
        horizontalLineTable.addCell(cell);
        horizontalLine.add(Chunk.NEWLINE);
        horizontalLine.add(horizontalLineTable);
        document.add(horizontalLine);
        
        
        // Main table
        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100.0f);
                
        // First table
        PdfPCell firstTableCell = new PdfPCell();
        firstTableCell.setBorder(PdfPCell.NO_BORDER);
        firstTableCell.setPaddingRight(8);
        firstTableCell.setRowspan(4);
        PdfPTable firstTable = new PdfPTable(2);
        float[] firstTablecolumnWidths = {3f, 1f};
        firstTable.setWidths(firstTablecolumnWidths);
        firstTable.setWidthPercentage(100.0f);
        
        cell = new PdfPCell(new Phrase("Loan Summary", boldFont));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        firstTable.addCell(cell);
    
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        firstTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Loan Purpose", font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        String loanType = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getLoanType();
        if (loanType.equals("PUR"))
        	cell = new PdfPCell(new Phrase("Purchase",font));
        else if(loanType.equals("REF"))
        	cell = new PdfPCell(new Phrase("Refinance",font));
        else
        	cell = new PdfPCell(new Phrase("Refinance-cash out",font));
        
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Loan Program",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        String loanProgram = generatePdfVO.getLoanProgram();
        cell = new PdfPCell(new Phrase(loanProgram,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Property Type",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        String propertyType = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getPropertyType();
        if(propertyType.equals("0"))
        	cell = new PdfPCell(new Phrase("Single family",font));
        else if(propertyType.equals("1"))
        	cell = new PdfPCell(new Phrase("Condo",font));
        else
        	cell = new PdfPCell(new Phrase("2-4 Units",font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Property Use",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        String propertyUse = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getResidenceType();
        if(propertyUse.equals("0"))
        	cell = new PdfPCell(new Phrase("Primary residence",font));
        else if(propertyUse.equals("1"))
        	cell = new PdfPCell(new Phrase("Secondary residence",font));
        else
        	cell = new PdfPCell(new Phrase("Investment",font));
        
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
      
        String purchasePrice = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getPurchaseDetails().getHousePrice();
        String loanAmount = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getPurchaseDetails().getLoanAmount();
   
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        firstTable.addCell(cell);
        
        if (loanType.equals("PUR"))
        {
	        
	        cell = new PdfPCell(new Phrase("  "+"Purchase price",font));
	        cell.setBorder(Rectangle.LEFT);
	        firstTable.addCell(cell);
	        
	       
	        cell = new PdfPCell(new Phrase(addDollarAndComma(purchasePrice),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);
	        cell = new PdfPCell(new Phrase("  "+"Down payment",font));
	        cell.setBorder(Rectangle.LEFT);
	        firstTable.addCell(cell);
	        
	       
	        Float downPayment = Float.parseFloat(purchasePrice) - Float.parseFloat(loanAmount);
	        cell = new PdfPCell(new Phrase(addDollarAndComma(""+downPayment),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);      
        }
        
        cell = new PdfPCell(new Phrase("  "+"Loan Amount",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        if (loanType.equals("PUR")){
        	cell = new PdfPCell(new Phrase(addDollarAndComma(loanAmount),font));
        }
        else{
        	String loanAmountDuringRefinance = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCurrentMortgageBalance();
        	cell = new PdfPCell(new Phrase(addDollarAndComma(loanAmountDuringRefinance),font));
        }
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        
        if (!loanType.equals("PUR")){
	        cell = new PdfPCell(new Phrase("  "+"Estimated Home Value",font));
	        cell.setBorder(Rectangle.LEFT);
	        firstTable.addCell(cell);
	        
	        String homeWorthTodayDuringRefinance = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getHomeWorthToday();
	        cell = new PdfPCell(new Phrase(addDollarAndComma(homeWorthTodayDuringRefinance),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);
        }
        
        cell = new PdfPCell(new Phrase("  "+"Loan-to-value",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        Float loanToValue = (Float.parseFloat(loanAmount) / Float.parseFloat(purchasePrice) ) * 100;
        cell = new PdfPCell(new Phrase(loanToValue+"%",font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        firstTable.addCell(cell);
       
        cell = new PdfPCell(new Phrase("  "+"Loan Program",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase(loanProgram,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Interest Rate / APR",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        String rateAndApr = generatePdfVO.getRateAndApr();
        cell = new PdfPCell(new Phrase(rateAndApr,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Loan term - months",font));
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        firstTable.addCell(cell);
        
        String[] getYear = loanProgram.split("-");
        Integer monthLoanProgram = Integer.parseInt(getYear[0]) * 12; 
        cell = new PdfPCell(new Phrase(""+monthLoanProgram,font));
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
    
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        
        PdfPTable seventhTable = new PdfPTable(2);
        float[] seventhTablecolumnWidths = {3f, 1f};
        seventhTable.setWidths(seventhTablecolumnWidths);
        seventhTable.setWidthPercentage(100.0f);
    
        cell = new PdfPCell(new Phrase("Estimated Cash from / to Borrower", boldFont));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        GeneratePdfForQuickQuote app1 = new GeneratePdfForQuickQuote();

        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        seventhTable.addCell(cell);
        
        if (loanType.equals("PUR")){
        cell = new PdfPCell(new Phrase("  "+"Purchase Price",font));
        }
        else if(loanType.equals("REF")){
        	cell = new PdfPCell(new Phrase("  "+"Current Loan Balance",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"Current Loan",font));
        }
        	
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        
        
        if (loanType.equals("PUR")){
        	  cell = new PdfPCell(new Phrase(addDollarAndComma(purchasePrice),font));
        }
        else {
        	String homeWorthToday = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getHomeWorthToday();
        	cell = new PdfPCell(new Phrase(addDollarAndComma(homeWorthToday),font));
        }
      
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"+Estimated Closing Costs",font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        seventhTable.addCell(cell);
        
        String estimatingClosingCost = "0";
        cell = new PdfPCell(new Phrase(addDollarAndComma(estimatingClosingCost),font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);

        cell = new PdfPCell(new Phrase("  "+"Total Cash Investment",font));
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        Float totalCashInvestment = Float.parseFloat(estimatingClosingCost) + Float.parseFloat(purchasePrice);
        cell = new PdfPCell(new Phrase(addDollarAndComma(""+totalCashInvestment),font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        seventhTable.addCell(cell);
        
        if (loanType.equals("PUR")){
        cell = new PdfPCell(new Phrase("  "+"Loan Amount",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"New Loan Amount",font));
        }
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        cell = new PdfPCell(new Phrase(addDollarAndComma(loanAmount),font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"+Lender Credit",font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        seventhTable.addCell(cell);
        
        String totEstimatedClosingCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstimatedClosingCost();
        if(totEstimatedClosingCost != null){
        totEstimatedClosingCost = totEstimatedClosingCost.replaceAll("(?<=\\d),(?=\\d)|\\$|\\%", "");
       
        }
        cell = new PdfPCell(new Phrase(addDollarAndComma(totEstimatedClosingCost),font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Total Credits",font));
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        Float totalCredits = Float.parseFloat(totEstimatedClosingCost) + Float.parseFloat(loanAmount);
        cell = new PdfPCell(new Phrase(addDollarAndComma(""+totalCredits),font));
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Total Cash Investment",font));
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        cell = new PdfPCell(new Phrase(addDollarAndComma(""+totalCashInvestment),font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"-Total Credits",font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(addDollarAndComma(""+totalCredits),font));
        cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        
        if (loanType.equals("REFCO")){
        	cell = new PdfPCell(new Phrase("  "+"Cash to borrower",boldFont));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"Cash from borrower",boldFont));
        }
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        Float cashFromBorrower = totalCashInvestment - totalCredits;
        cell = new PdfPCell(new Phrase(addDollarAndComma(""+cashFromBorrower),boldFont));
        cell.setPaddingTop(5f);
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        seventhTable.addCell(cell);
        
        String totEstResDepWthLen = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstResDepWthLen();
        cell = new PdfPCell(new Phrase("  "+"Cash reserves for pre-paids/escrows     "+totEstResDepWthLen,font));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        cell.setColspan(2);
        seventhTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase(""));
        cell.setColspan(2);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        seventhTable.addCell(cell);
     
        firstTableCell.addElement(firstTable);
        firstTableCell.addElement(Chunk.NEWLINE);
        firstTableCell.addElement(Chunk.NEWLINE);
        firstTableCell.addElement(seventhTable);
        mainTable.addCell(firstTableCell);
        
        // Second table
        PdfPCell secondTableCell = new PdfPCell();
        secondTableCell.setBorder(PdfPCell.NO_BORDER);
        secondTableCell.setPaddingLeft(8);
        PdfPTable secondTable = new PdfPTable(2);
        float[] secondTablecolumnWidths = {4f, 1f};
        secondTable.setWidths(secondTablecolumnWidths);
        secondTable.setWidthPercentage(100.0f);
      

        cell = new PdfPCell(new Phrase("Estimated Closing Cost Summary", boldFont));
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        secondTable.addCell(cell);
        
     
        cell = new PdfPCell(new Phrase("Estimated Lender Costs", boldFont));
        BaseColor lightBlue = WebColors.getRGBColor("#EEF7F7");
        cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
        cell.setPaddingTop(4);
        cell.setPaddingBottom(4);
        cell.setBackgroundColor(lightBlue);
        cell.setColspan(2);
        GeneratePdfForQuickQuote app = new GeneratePdfForQuickQuote();
        CustomCell border = app.new CustomCell();       
        cell.setCellEvent(border);
        secondTable.addCell(cell);
        
        
        
        PdfPCell cellOne = new PdfPCell(new Phrase("  "+"Lender Fee",boldFont));
        cellOne.setPaddingTop(4);
        cellOne.setPaddingBottom(4); 
        CustomCell border1 = app.new CustomCell();       
        cellOne.setCellEvent(border1);
        cellOne.setBorder(Rectangle.LEFT);
        secondTable.addCell(cellOne);
        
        
        
        String lenderFee813 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getLenderFee813();
        PdfPCell cellTwo = new PdfPCell(new Phrase(lenderFee813,font));
        cellTwo.setPaddingTop(4); 
        cellTwo.setPaddingBottom(4);
        cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border2 = app.new CustomCell();       
        cellTwo.setCellEvent(border2);
        cellTwo.setBorder(Rectangle.RIGHT);
        secondTable.addCell(cellTwo);
        
        
        cell = new PdfPCell(new Phrase("  "+"Your cost or credit based on rate selected",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT);
        secondTable.addCell(cell);
        
        String creditOrCharge802 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getCreditOrCharge802();
        cell = new PdfPCell(new Phrase(creditOrCharge802,font));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        secondTable.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("Total Estimated Lender Costs",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.LEFT);
        secondTable.addCell(cell);
   //     TotEstLenCost
        
        String TotEstLenCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstLenCost();
        cell = new PdfPCell(new Phrase(TotEstLenCost,font));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM | Rectangle.RIGHT);
        secondTable.addCell(cell);
  
        secondTableCell.addElement(secondTable);
        secondTableCell.addElement(Chunk.NEWLINE);
        mainTable.addCell(secondTableCell);
       
        
     // Third table
        PdfPCell thirdTableCell = new PdfPCell();
        thirdTableCell.setBorder(PdfPCell.NO_BORDER);
        thirdTableCell.setPaddingLeft(8);
        PdfPTable thirdTable = new PdfPTable(2);
        float[] thirdTablecolumnWidths = {4f, 1f};
        thirdTable.setWidths(thirdTablecolumnWidths);
        thirdTable.setWidthPercentage(100.0f);
      
        cell = new PdfPCell(new Phrase("Estimated Third Party Costs", boldFont));
        cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
     
        cell.setBackgroundColor(lightBlue); 
        cell.setColspan(2);
        CustomCell borderET = app.new CustomCell();       
        cell.setCellEvent(borderET);
        thirdTable.addCell(cell);
        
  
        cell = new PdfPCell(new Phrase("  "+"Appraisal Fee",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border4 = app.new CustomCell();       
        cell.setCellEvent(border4);
        cell.setBorder(Rectangle.LEFT);
   
        thirdTable.addCell(cell);
        
        String appraisalFee804 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getAppraisalFee804();
        cell = new PdfPCell(new Phrase(appraisalFee804,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell  border5 = app.new CustomCell();       
        cell.setCellEvent(border5);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Credit Report",boldFont));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        CustomCell border6 = app.new CustomCell();       
        cell.setCellEvent(border6);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String creditReport805 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getCreditReport805();
        cell = new PdfPCell(new Phrase(creditReport805,font));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border7 = app.new CustomCell();       
        cell.setCellEvent(border7);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Flood Certification",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border8 = app.new CustomCell();       
        cell.setCellEvent(border8);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String floodCertification807 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getFloodCertification807();
        cell = new PdfPCell(new Phrase(floodCertification807,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border9 = app.new CustomCell();       
        cell.setCellEvent(border9);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("  "+"Wire Fee",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border12 = app.new CustomCell();       
        cell.setCellEvent(border12);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String wireFee812 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getWireFee812();
        cell = new PdfPCell(new Phrase(wireFee812,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border13 = app.new CustomCell();       
        cell.setCellEvent(border13);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Lenders Title Insurance",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border14 = app.new CustomCell();       
        cell.setCellEvent(border14);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        
        String lendersTitleInsurance1104 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getLendersTitleInsurance1104();
        cell = new PdfPCell(new Phrase(lendersTitleInsurance1104,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border15 = app.new CustomCell();       
        cell.setCellEvent(border15);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Closing/Escrow Fee",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border16 = app.new CustomCell();       
        cell.setCellEvent(border16);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String closingEscrowFee1102 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getClosingEscrowFee1102();
        cell = new PdfPCell(new Phrase(closingEscrowFee1102,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border17 = app.new CustomCell();       
        cell.setCellEvent(border17);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
    
        cell = new PdfPCell(new Phrase("  "+"Recording Fee",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border18 = app.new CustomCell();       
        cell.setCellEvent(border18);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String recordingFees1201 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getRecordingFees1201();
        cell = new PdfPCell(new Phrase(recordingFees1201,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border19 = app.new CustomCell();       
        cell.setCellEvent(border19);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Estimated Third Party Costs",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
        cell.setBackgroundColor(lightBlue); 
        thirdTable.addCell(cell);
        
        String totEstThdPtyCst = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstThdPtyCst();
        cell = new PdfPCell(new Phrase(totEstThdPtyCst,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
        thirdTable.addCell(cell);
        
  
        thirdTableCell.addElement(thirdTable);
        thirdTableCell.addElement(Chunk.NEWLINE);
        
        mainTable.addCell(thirdTableCell);
        
     // Fourth table
        PdfPCell fourthTableCell = new PdfPCell();
        fourthTableCell.setBorder(PdfPCell.NO_BORDER);
        fourthTableCell.setPaddingLeft(8);
        PdfPTable fourthTable = new PdfPTable(2);
        float[] fourthTablecolumnWidths = {4f, 1f};
        fourthTable.setWidths(fourthTablecolumnWidths);
        fourthTable.setWidthPercentage(100.0f);
   
        cell = new PdfPCell(new Phrase("Total Estimating Closing Costs", boldFont));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
        cell.setBackgroundColor(lightBlue); 
        fourthTable.addCell(cell);
        
       // String totEstimatedClosingCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstimatedClosingCost();
        cell = new PdfPCell(new Phrase(totEstimatedClosingCost, font));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
        cell.setBackgroundColor(lightBlue); 
        fourthTable.addCell(cell);
     
        fourthTableCell.addElement(fourthTable);
        fourthTableCell.addElement(Chunk.NEWLINE);
        mainTable.addCell(fourthTableCell);
        
     // Fifth table
        PdfPCell fifthTableCell = new PdfPCell();
        fifthTableCell.setBorder(PdfPCell.NO_BORDER);
        fifthTableCell.setPaddingLeft(8);
        PdfPTable fifthTable = new PdfPTable(2);
        float[] fifthTablecolumnWidths = {4f, 1f};
        fifthTable.setWidths(fifthTablecolumnWidths);
        fifthTable.setWidthPercentage(100.0f);
   
        cell = new PdfPCell(new Phrase("Estimated Prepaid and Escrows", boldFont));
        cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBackgroundColor(lightBlue); 
        cell.setColspan(2);
        CustomCell borderFT = app.new CustomCell();       
        cell.setCellEvent(borderFT);
        fifthTable.addCell(cell);
        
   
        cell = new PdfPCell(new Phrase("  "+"Interest",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell borderFT1 = app.new CustomCell();       
        cell.setCellEvent(borderFT1);
        cell.setBorder(Rectangle.LEFT);
        fifthTable.addCell(cell);
        
        String interest901 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getInterest901();
        cell = new PdfPCell(new Phrase(interest901,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell borderFT2 = app.new CustomCell();       
        cell.setCellEvent(borderFT2);
        cell.setBorder(Rectangle.RIGHT);
        fifthTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("  "+"Homeowners Insurance",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell borderFT3 = app.new CustomCell();       
        cell.setCellEvent(borderFT3);
        cell.setBorder(Rectangle.LEFT);
        fifthTable.addCell(cell);
        
        String hazIns903 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getHazIns903();
        cell = new PdfPCell(new Phrase(hazIns903,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell borderFT4 = app.new CustomCell();       
        cell.setCellEvent(borderFT4);
        cell.setBorder(Rectangle.RIGHT);
        fifthTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.addElement(new Phrase("  "+"Tax Reserve - Estimated 2 Months",boldFont));
        cell.addElement(new Phrase("  "+"Varies based on calendar month of closing",font));
        cell.setPaddingTop(4);
        cell.setPaddingBottom(4);
        CustomCell borderFT5 = app.new CustomCell();       
        cell.setCellEvent(borderFT5);
        cell.setBorder(Rectangle.LEFT);
        fifthTable.addCell(cell);
        
        String taxResrv1004 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTaxResrv1004();
        cell = new PdfPCell(new Phrase(taxResrv1004,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell borderFT6 = app.new CustomCell();       
        cell.setCellEvent(borderFT6);
        cell.setBorder(Rectangle.RIGHT);
        fifthTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.addElement(new Phrase("  "+"Homeowners Insurance Reserve - Estimated 2 Months",boldFont));
        cell.addElement(new Phrase("  "+"Provided you have 6 months of remaining coverage",font));
        cell.addElement(new Phrase("  "+"Note: Taxes for 1st and 2nd installments must be paid or",font));
        cell.addElement(new Phrase("  "+"will be collected at closing.",font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT);
        fifthTable.addCell(cell);
        
        String hazInsReserve1002 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getHazInsReserve1002();
        cell = new PdfPCell(new Phrase(hazInsReserve1002,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        fifthTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Estimated Prepaids and Escrows",boldFont));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
        fifthTable.addCell(cell);
        
      //  String totEstResDepWthLen = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstResDepWthLen();
        cell = new PdfPCell(new Phrase(totEstResDepWthLen,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
        fifthTable.addCell(cell);
       
  
        fifthTableCell.addElement(fifthTable);
        mainTable.addCell(fifthTableCell);
        
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(mainTable);
        document.add(paragraph);
        document.add(horizontalLine);
        
        
        String footerImageForPdf = basePath + File.separator + "resources"
    		        + File.separator + "images" + File.separator
    		        + "footerForPdf.jpg";
            File fileFooterImageForPdf = new File(footerImageForPdf);
            Image footerPdfImage = Image.getInstance(fileFooterImageForPdf.getAbsolutePath());
            footerPdfImage.scaleAbsolute(520f, 50f);// image width,height
          //  footerPdfImage.setAbsolutePosition(210f, 780f);    
            document.add(footerPdfImage);
    }
    
    public class CustomCell implements PdfPCellEvent {
   	 public void cellLayout(PdfPCell cell, Rectangle position,
                PdfContentByte[] canvas) {
   		 
   	  PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
      cb.saveState();
      cb.setLineDash(2, 5, 1);
      cb.setLineWidth(0.01f);
      BaseColor lightGray = WebColors.getRGBColor("#E9F0F2");
      cb.setColorStroke(lightGray);
      cb.setLineCap(PdfContentByte.LINE_CAP_PROJECTING_SQUARE);
      cb.moveTo(position.getLeft(), position.getBottom());
      cb.lineTo(position.getRight(), position.getBottom());
      cb.stroke();
      cb.restoreState();
       }
   }
    public class BorderThick implements PdfPCellEvent {
      	 public void cellLayout(PdfPCell cell, Rectangle position,
                   PdfContentByte[] canvas) {
      	  PdfContentByte cb = canvas[PdfPTable.LINECANVAS];
         cb.saveState();
         cb.setLineDash(2, 4, 1);
         cb.setLineWidth(1.5f);
         cb.setLineCap(PdfContentByte.LINE_CAP_PROJECTING_SQUARE);
         cb.moveTo(position.getLeft(), position.getBottom());
         cb.lineTo(position.getRight(), position.getBottom());
         cb.stroke();
         cb.restoreState();
          }
      }
    public ByteArrayOutputStream sendPurchasePdf(GeneratePdfVO generatePdfVO, HttpServletRequest request)
    {
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    	openPdf(byteArrayOutputStream);
    	try {
    		    UserVO user = userProfileService.findUser(generatePdfVO.getUserId());
    		    generatePdf(generatePdfVO, request, byteArrayOutputStream,user);
    		    closePdf();
    			sendPurchasePdfEmail(user,byteArrayOutputStream,generatePdfVO );
    			//byteArrayOutputStream.close();
    	        
 
    	} catch (Exception ex) {
    		LOGGER.error("Error in generating purchase pdf under quick quote:", ex);
    	}
    	 return byteArrayOutputStream; 
    }
    
    
	
	public void sendPurchasePdfEmail(UserVO userVo,
	        ByteArrayOutputStream byteArrayOutputStream,GeneratePdfVO generatePdfVO)
	        throws InvalidInputException, UndeliveredEmailException {
		
		String subject = CommonConstants.PURCHASE_LETTER;
		EmailVO emailEntity = new EmailVO();
		Template template = null;
		template = templateService
				.getTemplateByKey(CommonConstants.TEMPLATE_KEY_NAME_PURCHASE_PDF);

		// We create the substitutions map
		Map<String, String[]> substitutions = new HashMap<String, String[]>();
		substitutions.put("-name-", new String[] {userVo
		        .getFirstName() + " " + userVo.getLastName() });

		emailEntity.setAttachmentStream(byteArrayOutputStream);
		emailEntity.setSenderEmailId(userVo.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		
		emailEntity.setSenderName(CommonConstants.SENDER_NAME);
		emailEntity.setSubject(subject);
		emailEntity.setTokenMap(substitutions);
		emailEntity.setFileName(CommonConstants.FILE_NAME_PURCHASE_PDF+generatePdfVO.getFirstName()+"_"+
															generatePdfVO.getLastName()+".pdf");
		emailEntity.setTemplateId(template.getValue());
		List<String> ccList = new ArrayList<String>();
		ccList.add(userVo.getEmailId());
		ccList.add(userVo.getUsername()
		        + CommonConstants.SENDER_EMAIL_ID);
		emailEntity.setCCList(ccList);
		sendEmailService.sendEmailForCustomer(emailEntity, userVo, template);

	}
}
