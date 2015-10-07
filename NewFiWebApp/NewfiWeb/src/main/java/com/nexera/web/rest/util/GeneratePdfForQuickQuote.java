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

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Annotation;
import com.itextpdf.text.BadElementException;
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
import com.nexera.common.commons.Utils;
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
import com.nexera.core.utility.NexeraUtility;
import com.nexera.web.rest.util.GeneratePdfForQuickQuote.CustomCell;


@Component
public class GeneratePdfForQuickQuote {
	
	private static final Logger LOGGER = LoggerFactory
	        .getLogger(GeneratePdfForQuickQuote.class);

	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;
	
	@Autowired
	protected Utils utils;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	@Autowired
	private NexeraUtility nexeraUtility;
	
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
    private static String basePath;
    private static String nmlsId;
    public GeneratePdfForQuickQuote() {
    }

     static class HeaderFooterPageEventForPdf extends PdfPageEventHelper {
		public void onStartPage(PdfWriter writer, Document document) {

		}

		public void onEndPage(PdfWriter writer, Document document) {
		
			String homeIconPath = basePath + File.separator + "resources"
			        + File.separator + "images" + File.separator
			        + "footer_home.png";
			
			File file = new File(homeIconPath);
		        Image image = null;
				try {
					image = Image.getInstance(file.getAbsolutePath());
				} catch (BadElementException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		      image.scaleAbsolute(16f, 10f);// image width,height
			
			Rectangle page = document.getPageSize();
			PdfPTable foot = new PdfPTable(1);
			
			Phrase p = new Phrase();
			p.add(new Chunk("Newfi dba of Nexera Holding LLC  | NMLS ID 1231327  |  ",FontFactory.getFont("Calibri", 6,Font.BOLD)));
			p.add(new Chunk(image, 0, 0));
			p.add(new Chunk("  Equal Housing Lender",FontFactory.getFont(FontFactory.HELVETICA, 6,Font.BOLD)));
			
			PdfPCell footCell = new PdfPCell(p);			

			footCell.setBorder(0);
			footCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			foot.addCell(footCell);
			foot.setTotalWidth(page.getWidth());
			foot.writeSelectedRows(0, -1, document.leftMargin() - 30,
			        document.bottomMargin() - 15, writer.getDirectContent());

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
    public String addDollarAndCommaWithoutDecimal(String number)
    {
    	double amount = Double.parseDouble(number);
		DecimalFormat formatter = new DecimalFormat("$#,###");
		return formatter.format(amount);
    }
    
    public String removeDollarAndComma(String number){
    	return number.replaceAll("(?<=\\d),(?=\\d)|\\$|\\%", "");
    }
    
    public String removeDollarCommaAndBracket(String number){
    	if(number.contains("(")){
    		number = number.substring(1, number.length()-1);
    	}
    	return number.replaceAll("(?<=\\d),(?=\\d)|\\$|\\%", "");
    }
    
    public String phoneNumberFormating(String phoneNumber)
    {
    	return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
    }
    
    public boolean isNegative(String number){
    	if(number.contains("(")){
    		number = number.substring(1, number.length()-1);
    	}
    	if(number.contains("$")){
    		number = removeDollarAndComma(number);
    	}
    	
    	if(Float.parseFloat(number) < 0){
    		return true;
    	}
    	return false;	
    }
    

    public void generatePdf(GeneratePdfVO generatePdfVO, HttpServletRequest httpServletRequest, ByteArrayOutputStream byteOutput,UserVO user) throws DocumentException, IOException {
    	Paragraph logoParagraph = new Paragraph();
    	Paragraph horizontalLine = new Paragraph();
        Paragraph paragraph = new Paragraph();
        Paragraph imageParagraph = new Paragraph();
        Font font = FontFactory.getFont("Calibri", 7);
        Font lineFont = FontFactory.getFont("Calibri", 3);
        Font fontWithBigSize = FontFactory.getFont("Calibri", 9);
        Font emailIdFont = FontFactory.getFont("Calibri", 9, Font.UNDERLINE);
        emailIdFont.setColor(BaseColor.BLUE);
        Font boldFont = FontFactory.getFont("Calibri", 8, Font.BOLD);
        Font boldFontWithBigSize = FontFactory.getFont("Calibri", 10, Font.BOLD);
        Font footerFont = FontFactory.getFont("Calibri", 6);
        Font boldFooterFont = FontFactory.getFont("Calibri", 7, Font.BOLD);
        PdfPCell cell = null;
     
		 
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
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
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

       // String nmlsId = user.get

        if (firstName == null)
        	firstName = "N/A";
        if (lastName == null)
        	lastName = "N/A";
        
       
      
        if (phoneNumber == null)
        	phoneNumber = "N/A";
        else
        	phoneNumber = phoneNumberFormating(phoneNumber);
        cell = new PdfPCell();
        cell.addElement(new Phrase(firstName+" "+lastName+"\nSenior Loan Advisor\nNMLS ID "+nmlsId+"\n"+phoneNumber, fontWithBigSize));

        Paragraph para= new Paragraph();
        Anchor anchor = new Anchor(user.getEmailId(), emailIdFont);
        anchor.setReference("mailto:"+user.getEmailId());
        para.add(anchor);
        cell.addElement(para);
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
   
        
        cell = new PdfPCell(new Phrase("\n2200 Powell St, Suite 340\nEmeryville, CA 94608\nNMLS ID 1231327",fontWithBigSize));
        cell.setBorder(PdfPCell.NO_BORDER);
        imageTable.addCell(cell);
        
        String imageFilePath = "";
		imageFilePath = basePath + File.separator + "resources"
		        + File.separator + "images" + File.separator
		        + "apply_button.png";
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
        BaseColor newfiBlue = WebColors.getRGBColor("#14498f");
        cell.setBorderColor(newfiBlue);
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
        
        BaseColor lightBlueHeading = WebColors.getRGBColor("#EEF7F7");
        cell = new PdfPCell(new Phrase("Loan Summary", boldFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        cell.setColspan(2);
        //cell.setPaddingTop(4);
       // cell.setPaddingBottom(4);
        cell.setBackgroundColor(lightBlueHeading);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
       // cell.setVerticalAlignment(Element.ALIGN_CENTER);
        firstTable.addCell(cell);
    
//        cell = new PdfPCell(new Phrase("\n"));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
//        firstTable.addCell(cell);
        
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
        
       // String loanProgram = generatePdfVO.getLoanProgram();
        String loanProgram = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getYearData();
        if(loanProgram.equals("5") || loanProgram.equals("7")){
        	loanProgram = loanProgram+" Year ARM";
        }
        else{
        	loanProgram = loanProgram+" Year Fixed";
        }
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
	        
	       
	        cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(purchasePrice),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);
	        cell = new PdfPCell(new Phrase("  "+"Down payment",font));
	        cell.setBorder(Rectangle.LEFT);
	        firstTable.addCell(cell);
	        
	       
	        Float downPayment = Float.parseFloat(purchasePrice) - Float.parseFloat(loanAmount);
	        cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(""+downPayment),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);      
        }
        
        cell = new PdfPCell(new Phrase("  "+"Loan Amount",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        String loanAmountDuringRefinance="";
        
        
        if (loanType.equals("PUR")){
        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(loanAmount),font));
        }
        else if(loanType.equals("REF")){
        	loanAmountDuringRefinance = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCurrentMortgageBalance();
        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(loanAmountDuringRefinance),font));
        }
        else{
        	String cashOut = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCashTakeOut();
        	if(cashOut.contains("$")){
        		cashOut = removeDollarAndComma(cashOut);
        	}
        	
        	Float cashOutLoanAmount = Float.parseFloat(cashOut) + Float.parseFloat(generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCurrentMortgageBalance());
        	loanAmountDuringRefinance = ""+cashOutLoanAmount;
        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(""+cashOutLoanAmount),font));
        }
        
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        String homeWorthTodayDuringRefinance="";
        if (!loanType.equals("PUR")){
	        cell = new PdfPCell(new Phrase("  "+"Estimated Home Value",font));
	        cell.setBorder(Rectangle.LEFT);
	        firstTable.addCell(cell);
	        
	        homeWorthTodayDuringRefinance = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getHomeWorthToday();
	        cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(homeWorthTodayDuringRefinance),font));
	        cell.setBorder(Rectangle.RIGHT);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        firstTable.addCell(cell);
        }
        
        cell = new PdfPCell(new Phrase("  "+"Loan-to-value",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        
        Float loanToValue;
        if (loanType.equals("PUR")){
        	loanToValue = (Float.parseFloat(loanAmount) / Float.parseFloat(purchasePrice) ) * 100;
        }
        else{
        	 loanToValue = (Float.parseFloat(loanAmountDuringRefinance) / Float.parseFloat(homeWorthTodayDuringRefinance) ) * 100;
        }
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        cell = new PdfPCell(new Phrase(df.format(loanToValue)+"%",font));
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
        
        //String rateAndApr = generatePdfVO.getRateAndApr();
        String teaserRate = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTeaserRate();
        String APR = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getAPR();
        String rateAndApr = teaserRate+"% / "+APR+"%";
        
        cell = new PdfPCell(new Phrase(rateAndApr,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase("  "+"Loan term - months",font));
        cell.setBorder(Rectangle.LEFT );
        firstTable.addCell(cell);
        String monthLoanProgram="";
        if (loanProgram != null){
        String[] getYear = loanProgram.split(" ");
        monthLoanProgram = ""+Integer.parseInt(getYear[0]) * 12; 
        }
        cell = new PdfPCell(new Phrase(monthLoanProgram,font));
        cell.setBorder(Rectangle.RIGHT);
    
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        
        String impounds = generatePdfVO.getImpounds();
        cell = new PdfPCell(new Phrase("  "+"Impounds",font));
        cell.setBorder(Rectangle.LEFT);
        firstTable.addCell(cell);
        cell = new PdfPCell(new Phrase(impounds,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        firstTable.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase("\n"));
        cell.setColspan(2);
        cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.BOTTOM);
        firstTable.addCell(cell);
        
        PdfPTable seventhTable = new PdfPTable(2);
        float[] seventhTablecolumnWidths = {3f, 1f};
        seventhTable.setWidths(seventhTablecolumnWidths);
        seventhTable.setWidthPercentage(100.0f);
    
        
        cell = new PdfPCell(new Phrase("Estimated Cash from / to Borrower", boldFont));
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
        //cell.setColspan(2);
        cell.setColspan(2);
      //  cell.setPaddingTop(4);
     //   cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setBackgroundColor(lightBlueHeading);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
      //  cell.setVerticalAlignment(Element.ALIGN_CENTER);
        GeneratePdfForQuickQuote app1 = new GeneratePdfForQuickQuote();

        seventhTable.addCell(cell);
        
//        cell = new PdfPCell(new Phrase("\n"));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.TOP);
//        seventhTable.addCell(cell);
        
        if (loanType.equals("PUR")){
        cell = new PdfPCell(new Phrase("  "+"Purchase Price",font));
        }
        else if(loanType.equals("REF")){
        	cell = new PdfPCell(new Phrase("  "+"Current Loan Balance",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"New Loan Amount",font));
        }
        	
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        String currentMortgageBalance = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCurrentMortgageBalance();
        String newLoanBalancePrice ;
        if (loanType.equals("PUR")){
        	  cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(purchasePrice),font)); 
        	  newLoanBalancePrice = purchasePrice;
        }
        else if(loanType.equals("REF")){
        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(currentMortgageBalance),font));
        	newLoanBalancePrice = currentMortgageBalance;
        }
        else{
        	String cashOut = generatePdfVO.getInputCustmerDetailUnderQuickQuote().getCashTakeOut();
        	if(cashOut.contains("$")){
        		cashOut = removeDollarAndComma(cashOut);
        	}
        	
        	Float cashOutLoanAmount = Float.parseFloat(cashOut) + Float.parseFloat(currentMortgageBalance);
        	newLoanBalancePrice = addDollarAndCommaWithoutDecimal(""+cashOutLoanAmount);
        	cell = new PdfPCell(new Phrase(newLoanBalancePrice,font));
        }
      
        String totEstimatedClosingCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstimatedClosingCost();
        if(totEstimatedClosingCost != null){
        totEstimatedClosingCost = removeDollarAndComma(totEstimatedClosingCost);
       
        }
        
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        if (loanType.equals("PUR") || loanType.equals("REF")){
        cell = new PdfPCell(new Phrase("  "+"+Estimated Closing Costs",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"-Estimated Closing Costs",font));
        }
        
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
      
        
       
        String totEstThdPtyCst = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstThdPtyCst();
        String TotEstLenCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstLenCost();
        
        boolean isEstClosingCostNegative = false;
        if(Float.parseFloat(removeDollarCommaAndBracket(TotEstLenCost)) > Float.parseFloat(removeDollarCommaAndBracket(totEstThdPtyCst))){
        	if(TotEstLenCost.contains("(")){
        	isEstClosingCostNegative = true;
        	}
        }
        
        String estimatingClosingCost = "$0";
        if(!isEstClosingCostNegative){
        	estimatingClosingCost = totEstimatedClosingCost;
        }

        
        if(estimatingClosingCost.equals("$0"))
        	cell = new PdfPCell(new Phrase(estimatingClosingCost,font));
        else{
		       	 if(estimatingClosingCost.contains("$") && !(estimatingClosingCost.contains("("))){
					 cell = new PdfPCell(new Phrase(estimatingClosingCost,font));
				 }
				 else if(!(estimatingClosingCost.contains("$")) && (estimatingClosingCost.contains("("))){
					 cell = new PdfPCell(new Phrase(addDollarAndComma(removeDollarCommaAndBracket(""+estimatingClosingCost)),font));
				 }
				 else{
					 cell = new PdfPCell(new Phrase(addDollarAndComma(""+estimatingClosingCost),font));
				 }
        	
        	
        }
        	
        
        //cell.setPaddingBottom(5f);
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        

        if (loanType.equals("PUR") || loanType.equals("REF")){
        	cell = new PdfPCell(new Phrase("  "+"+Estimated Prepaids",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"-Estimated Prepaids",font));
        }
      
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        
        String totEstResDepWthLen = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstResDepWthLen();
        String estimatedPrepaids = "$0";
        String prepaidComment = "$0";
        
        boolean showPrepaidComment = true; 
        String estimatedNumber;
        if(totEstimatedClosingCost.contains("(")){
        	estimatedNumber = removeDollarCommaAndBracket(totEstimatedClosingCost);
        }
        else if(totEstimatedClosingCost.contains("$")){
        	estimatedNumber = removeDollarAndComma(totEstimatedClosingCost);
        }
        else{
        	estimatedNumber = totEstimatedClosingCost;
        }
        
        String prepaidCost = estimatedNumber;
        if(prepaidCost.substring(prepaidCost.length() - 3).equals(".00")){
        	prepaidCost = prepaidCost.substring(0, prepaidCost.length() - 3);
        }
        
        String prepaidAgainstNumber;
        if(totEstResDepWthLen.contains("(")){
        	prepaidAgainstNumber = removeDollarCommaAndBracket(totEstResDepWthLen);
        }
        else if(totEstimatedClosingCost.contains("$")){
        	prepaidAgainstNumber = removeDollarAndComma(totEstResDepWthLen);
        }
        else{
        	prepaidAgainstNumber = totEstResDepWthLen;
        }
        
        
        if(prepaidCost.substring(prepaidCost.length() - 3).equals(".00")){
        	prepaidCost = prepaidCost.substring(0, prepaidCost.length() - 3);
        }
        if(isEstClosingCostNegative){
        	if(Float.parseFloat(estimatedNumber)  > Float.parseFloat(removeDollarAndComma(totEstResDepWthLen))){
        		
        	}
        	else if(Float.parseFloat(estimatedNumber)  < Float.parseFloat(removeDollarAndComma(totEstResDepWthLen))){
        		Float prepaidNumber = Float.parseFloat(removeDollarAndComma(totEstResDepWthLen)) -(Float.parseFloat(removeDollarAndComma(totEstResDepWthLen)) - Float.parseFloat(estimatedNumber));
        		prepaidComment = addDollarAndComma(""+prepaidNumber);
        		
        		Float estimatedPrepaidNumber = Float.parseFloat(removeDollarAndComma(totEstResDepWthLen)) - Float.parseFloat(estimatedNumber);
        		estimatedPrepaids = addDollarAndComma(""+estimatedPrepaidNumber);
        	}
        	else{
        		prepaidComment = totEstResDepWthLen;
        	}
        	
        }
        else{
        	estimatedPrepaids = totEstResDepWthLen;
        	showPrepaidComment = false; 
        }
        
        if (!loanType.equals("PUR")){
        	purchasePrice = currentMortgageBalance;
        }
        	
      
        cell = new PdfPCell(new Phrase(estimatedPrepaids,font));
        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
              
        if (loanType.equals("PUR") || loanType.equals("REF")){
        cell = new PdfPCell(new Phrase("  "+"-New Loan Amount",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"-Current Loan Balance",font));
        }
     
        cell.setBorder(Rectangle.LEFT);
        seventhTable.addCell(cell);
        if (loanType.equals("PUR")){
	        if(!loanAmount.contains("$")){
	        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(loanAmount),font));
	        }
	        else{
	        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(removeDollarAndComma(loanAmount)),font));
	        }
        }
        else {
        	if(!currentMortgageBalance.contains("$")){
	        	cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(currentMortgageBalance),font));
	        }
        	else{
        		cell = new PdfPCell(new Phrase(addDollarAndCommaWithoutDecimal(removeDollarAndComma(currentMortgageBalance)),font));
        	}
        }
        
        

        cell.setBorder(Rectangle.RIGHT);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        Paragraph linePara = new Paragraph("  _________________________________________________________________________________________________________________________________________________  ", lineFont);
           
        String lenderCredit = "$0";
      
        boolean isLenderCostNegative = false;
        String lenderFee813 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getLenderFee813();
        String creditOrCharge802 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getCreditOrCharge802();
        if(Float.parseFloat(removeDollarCommaAndBracket(lenderFee813)) < Float.parseFloat(removeDollarCommaAndBracket(creditOrCharge802))){
        	if(creditOrCharge802.contains("(")){
        		isLenderCostNegative = true;
        	}
        }

        cell.setColspan(2);
        cell.addElement(linePara);
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(8f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        seventhTable.addCell(cell);
        
        
        if (loanType.equals("PUR") || loanType.equals("REF") ){
        cell = new PdfPCell(new Phrase("  "+"Cash from Borrower",font));
        }
        else{
        	cell = new PdfPCell(new Phrase("  "+"Cash to Borrower",font));
        }
        	
       // cell.setPaddingTop(5f);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        if(showPrepaidComment){
            cell.setBorder(Rectangle.LEFT);
        }
        else{
        	cell.setBorder(Rectangle.LEFT |Rectangle.BOTTOM);
        }
        seventhTable.addCell(cell);
        
        if(!newLoanBalancePrice.contains("$"))
        {
        	newLoanBalancePrice = addDollarAndComma(newLoanBalancePrice);
        }
        if(!estimatingClosingCost.contains("$")){
        	estimatingClosingCost = addDollarAndComma(estimatingClosingCost);
        }
        if(!estimatedPrepaids.contains("$")){
        	estimatedPrepaids = addDollarAndComma(estimatedPrepaids);
        }
        String cashFromToBorrower;
        if (loanType.equals("PUR") ){
        	Float cashFromToBorrowerNumber = Float.parseFloat(removeDollarCommaAndBracket(newLoanBalancePrice)) + Float.parseFloat(removeDollarCommaAndBracket(estimatingClosingCost)) + Float.parseFloat(removeDollarCommaAndBracket(estimatedPrepaids)) - Float.parseFloat(removeDollarCommaAndBracket(loanAmount)) ;
        	cashFromToBorrower = addDollarAndComma(""+cashFromToBorrowerNumber);
        }
        else if(loanType.equals("REF")){
        	Float cashFromToBorrowerNumber = Float.parseFloat(removeDollarCommaAndBracket(newLoanBalancePrice)) + Float.parseFloat(removeDollarCommaAndBracket(estimatingClosingCost)) + Float.parseFloat(removeDollarCommaAndBracket(estimatedPrepaids)) - Float.parseFloat(removeDollarCommaAndBracket(loanAmountDuringRefinance)) ;
        	cashFromToBorrower = addDollarAndComma(""+cashFromToBorrowerNumber);
        }
        else {
        	Float cashFromToBorrowerNumber = Float.parseFloat(removeDollarCommaAndBracket(newLoanBalancePrice)) - Float.parseFloat(removeDollarCommaAndBracket(estimatingClosingCost)) - Float.parseFloat(removeDollarCommaAndBracket(estimatedPrepaids)) - Float.parseFloat(removeDollarCommaAndBracket(currentMortgageBalance));
        	cashFromToBorrower = addDollarAndComma(""+cashFromToBorrowerNumber);
        }
   
        cell = new PdfPCell(new Phrase(cashFromToBorrower,font));
       // cell.setPaddingTop(5f);
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        if(showPrepaidComment){
        cell.setBorder(Rectangle.RIGHT);
        }
        else{
        	cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        }
        
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        seventhTable.addCell(cell);
        

        
   
        if(showPrepaidComment){
//    	cell = new PdfPCell(new Phrase("\n"));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//        seventhTable.addCell(cell);	
        	
        cell = new PdfPCell(new Phrase("  *A credit of "+prepaidCost+" was applied against the "+prepaidAgainstNumber +" in prepaids.",font));
      //  cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        cell.setColspan(2);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
        seventhTable.addCell(cell);
        }
        
        
        
//        cell = new PdfPCell(new Phrase("\n"));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
//        seventhTable.addCell(cell);
        
//        cell = new PdfPCell(new Phrase(""));
//        cell.setColspan(2);
//        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
//        seventhTable.addCell(cell);
     
        firstTableCell.addElement(firstTable);
        firstTableCell.addElement(Chunk.NEWLINE);
       // firstTableCell.addElement(Chunk.NEWLINE);
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
      

//        cell = new PdfPCell(new Phrase(" Closing Cost Summary", boldFont));
//        cell.setBorder(PdfPCell.NO_BORDER);
//        cell.setColspan(2);
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_CENTER);
//        secondTable.addCell(cell);
        
     
        cell = new PdfPCell(new Phrase("Estimated Lender Costs", boldFont));
        BaseColor lightBlue = WebColors.getRGBColor("#EEF7F7");
        cell.setBorder(Rectangle.TOP | Rectangle.LEFT | Rectangle.RIGHT);
     //   cell.setPaddingTop(4);
      //  cell.setPaddingBottom(4);
        cell.setBackgroundColor(lightBlue);
        cell.setColspan(2);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(1);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        GeneratePdfForQuickQuote app = new GeneratePdfForQuickQuote();
        CustomCell border = app.new CustomCell();       
        cell.setCellEvent(border);
        secondTable.addCell(cell);
        
        
        
        PdfPCell cellOne = new PdfPCell(new Phrase("  "+"Lender Fee",font));
        cellOne.setPaddingTop(4);
        cellOne.setPaddingBottom(4); 
        CustomCell border1 = app.new CustomCell();       
        cellOne.setCellEvent(border1);
        cellOne.setBorder(Rectangle.LEFT);
        secondTable.addCell(cellOne);
        
        
        
       // String lenderFee813 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getLenderFee813();
        PdfPCell cellTwo = new PdfPCell(new Phrase(lenderFee813,font));
        cellTwo.setPaddingTop(4); 
        cellTwo.setPaddingBottom(4);
        cellTwo.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border2 = app.new CustomCell();       
        cellTwo.setCellEvent(border2);
        cellTwo.setBorder(Rectangle.RIGHT);
        secondTable.addCell(cellTwo);
        
        
        cell = new PdfPCell(new Phrase("  "+"Your cost or credit based on rate selected",font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setBorder(Rectangle.LEFT);
        secondTable.addCell(cell);
        
      //  String creditOrCharge802 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getCreditOrCharge802();
        cell = new PdfPCell(new Phrase(creditOrCharge802,font));
        cell.setPaddingTop(4); cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT);
        secondTable.addCell(cell);
        
        cell.setColspan(2);
        cell.addElement(linePara);
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(8f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        secondTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Estimated Lender Costs",font));
      //  cell.setPaddingTop(4); 
      //  cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT);
       // cell.setBackgroundColor(lightBlue);
        secondTable.addCell(cell);
  
       
        if(isLenderCostNegative){
        	 if(TotEstLenCost.contains("$") && TotEstLenCost.contains("(")){
        		 cell = new PdfPCell(new Phrase(TotEstLenCost,font));
        	 }
        	 else{
        		 if(TotEstLenCost.contains("$") && !(TotEstLenCost.contains("("))){
        			 cell = new PdfPCell(new Phrase("("+TotEstLenCost+")",font));
        		 }
        		 else if(!(TotEstLenCost.contains("$")) && (TotEstLenCost.contains("("))){
        			 cell = new PdfPCell(new Phrase("("+addDollarAndComma(removeDollarCommaAndBracket(TotEstLenCost))+")",font));
        		 }
        		 else{
        			 cell = new PdfPCell(new Phrase("("+addDollarAndComma(TotEstLenCost)+")",font));
        		 }
        	 }
        } 
        else{
        	
        	if(TotEstLenCost.contains("(")){
        		cell = new PdfPCell(new Phrase(TotEstLenCost.substring(1, TotEstLenCost.length()-1),font));
       	 	}
        	else if(!(TotEstLenCost.contains("$"))){
        		cell = new PdfPCell(new Phrase(addDollarAndComma(TotEstLenCost),font));
        	}
        	else{
        		cell = new PdfPCell(new Phrase(TotEstLenCost,font));
        	}
        	
        }
        
     //   cell.setPaddingTop(4); cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
       // cell.setBackgroundColor(lightBlue);
        cell.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
        secondTable.addCell(cell);
  
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        secondTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        secondTable.addCell(cell);
        
        secondTableCell.addElement(secondTable);
       // secondTableCell.addElement(Chunk.NEWLINE);
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
        //cell.setPaddingTop(4); 
       // cell.setPaddingBottom(4);
     
        cell.setBackgroundColor(lightBlue); 
        cell.setColspan(2);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        CustomCell borderET = app.new CustomCell();       
        cell.setCellEvent(borderET);
        thirdTable.addCell(cell);
        
  
        cell = new PdfPCell(new Phrase("  "+"Appraisal Fee",font));
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
        
        cell = new PdfPCell(new Phrase("  "+"Credit Report",font));
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
        
        cell = new PdfPCell(new Phrase("  "+"Flood Certification",font));
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
        
        
        cell = new PdfPCell(new Phrase("  "+"Wire Fee",font));
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
        
        cell = new PdfPCell(new Phrase("  "+"Owners Title Insurance",font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border20 = app.new CustomCell();       
        cell.setCellEvent(border20);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String ownerTitleInsurance = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getOwnersTitleInsurance1103();
        cell = new PdfPCell(new Phrase(ownerTitleInsurance,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border21 = app.new CustomCell();       
        cell.setCellEvent(border21);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        
        
        cell = new PdfPCell(new Phrase("  "+"Lenders Title Insurance",font));
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
        
        cell = new PdfPCell(new Phrase("  "+"Closing/Escrow Fee",font));
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
        
    
        cell = new PdfPCell(new Phrase("  "+"Recording Fee",font));
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
        
        
        cell = new PdfPCell(new Phrase("  "+"City/County Transfer Taxes",font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        CustomCell border22 = app.new CustomCell();       
        cell.setCellEvent(border22);
        cell.setBorder(Rectangle.LEFT);
        thirdTable.addCell(cell);
        
        String cityCountyTax = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getCityCountyTaxStamps1204();
        cell = new PdfPCell(new Phrase(cityCountyTax,font));
        cell.setPaddingTop(4); 
        cell.setPaddingBottom(4);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        CustomCell border23 = app.new CustomCell();       
        cell.setCellEvent(border23);
        cell.setBorder(Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell.setColspan(2);
        cell.addElement(linePara);
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(8f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Estimated Third Party Costs",font));
      //  cell.setPaddingTop(4); 
     //   cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM );
       // cell.setBackgroundColor(lightBlue); 
        thirdTable.addCell(cell);
        
    //    String totEstThdPtyCst = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstThdPtyCst();
        cell = new PdfPCell(new Phrase(totEstThdPtyCst,font));
      //  cell.setPaddingTop(4); 
      //  cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
     //   cell.setBackgroundColor(lightBlue);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
        thirdTable.addCell(cell);
        
  
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        thirdTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        thirdTable.addCell(cell);
        
        
        thirdTableCell.addElement(thirdTable);    
        mainTable.addCell(thirdTableCell);
        
     // Fourth table
        PdfPCell fourthTableCell = new PdfPCell();
        fourthTableCell.setBorder(PdfPCell.NO_BORDER);
        fourthTableCell.setPaddingLeft(8);
        PdfPTable fourthTable = new PdfPTable(2);
        float[] fourthTablecolumnWidths = {4f, 1f};
        fourthTable.setWidths(fourthTablecolumnWidths);
        fourthTable.setWidthPercentage(100.0f);
   
        cell = new PdfPCell(new Phrase("Total Estimated Closing Costs", boldFont));
    //    cell.setPaddingTop(4); cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM | Rectangle.TOP);
        cell.setBackgroundColor(lightBlue); 
        fourthTable.addCell(cell);
        
        boolean isTotalEstClosingCostNegative = false;
        if(Float.parseFloat(removeDollarCommaAndBracket(TotEstLenCost)) > Float.parseFloat(removeDollarCommaAndBracket(totEstThdPtyCst))){
        	if(TotEstLenCost.contains("(")){
        	isTotalEstClosingCostNegative = true;
        	}
        }
      
        if(isTotalEstClosingCostNegative){
       	 if(totEstimatedClosingCost.contains("$") && totEstimatedClosingCost.contains("(")){
       		 cell = new PdfPCell(new Phrase(totEstimatedClosingCost,font));
       	 }
       	 else{
       		 if(totEstimatedClosingCost.contains("$") && !(totEstimatedClosingCost.contains("("))){
       			 cell = new PdfPCell(new Phrase("("+totEstimatedClosingCost+")",font));
       		 }
       		 else if(!(totEstimatedClosingCost.contains("$")) && (totEstimatedClosingCost.contains("("))){
       			 cell = new PdfPCell(new Phrase("("+addDollarAndComma(removeDollarCommaAndBracket(totEstimatedClosingCost))+")",font));
       		 }
       		 else{
       			 cell = new PdfPCell(new Phrase("("+addDollarAndComma(totEstimatedClosingCost)+")",font));
       		 }
       	 }
       } 
       else{
       	
       	if(totEstimatedClosingCost.contains("(")){
       		cell = new PdfPCell(new Phrase(totEstimatedClosingCost.substring(1, totEstimatedClosingCost.length()-1),font));
      	 	}
       	else if(!(totEstimatedClosingCost.contains("$"))){
    		cell = new PdfPCell(new Phrase(addDollarAndComma(totEstimatedClosingCost),font));
    	}
       	else{
       		cell = new PdfPCell(new Phrase(totEstimatedClosingCost,font));
       	}
       	
       }
        
       // String totEstimatedClosingCost = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstimatedClosingCost();
//        if(totEstimatedClosingCost.contains("(")){
//        	totEstimatedClosingCost = totEstimatedClosingCost.substring(1, totEstimatedClosingCost.length()-1);
//        }
//        if(totEstimatedClosingCost.contains("("))
//        	cell = new PdfPCell(new Phrase(addDollarAndComma(totEstimatedClosingCost), font));
//        else
    //    cell = new PdfPCell(new Phrase(addDollarAndComma(totEstimatedClosingCost), font));
        //cell.setPaddingTop(4); cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM | Rectangle.TOP);
        cell.setBackgroundColor(lightBlue); 
        fourthTable.addCell(cell);
     
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        fourthTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("",font));
        cell.setColspan(2);
        cell.setBorder(PdfPCell.NO_BORDER);
        fourthTable.addCell(cell);
        
        fourthTableCell.addElement(fourthTable);
        mainTable.addCell(fourthTableCell);
        
     // Fifth table
        PdfPCell fifthTableCell = new PdfPCell();
        fifthTableCell.setBorder(PdfPCell.NO_BORDER);
        fifthTableCell.setPaddingLeft(8);
        PdfPTable fifthTable = new PdfPTable(2);
        float[] fifthTablecolumnWidths = {4f, 1f};
        fifthTable.setWidths(fifthTablecolumnWidths);
        fifthTable.setWidthPercentage(100.0f);
   
        if(impounds.equals("No")){
        cell = new PdfPCell(new Phrase("Estimated Prepaids", boldFont));
        cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
       // cell.setPaddingTop(4); 
       // cell.setPaddingBottom(4);
        cell.setBackgroundColor(lightBlue); 
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setColspan(2);
        CustomCell borderFT = app.new CustomCell();       
        cell.setCellEvent(borderFT);
        fifthTable.addCell(cell);
        
   
        cell = new PdfPCell(new Phrase("  "+"Interest",font));
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
        
//        cell = new PdfPCell(new Phrase("  "+"Homeowners Insurance",font));
//        cell.setPaddingTop(4); 
//        cell.setPaddingBottom(4);
//        CustomCell borderFT3 = app.new CustomCell();       
//        cell.setCellEvent(borderFT3);
//        cell.setBorder(Rectangle.LEFT);
//        fifthTable.addCell(cell);
//        
//        String hazIns903 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getHazIns903();
//        cell = new PdfPCell(new Phrase(hazIns903,font));
//        cell.setPaddingTop(4); 
//        cell.setPaddingBottom(4);
//        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//        CustomCell borderFT4 = app.new CustomCell();       
//        cell.setCellEvent(borderFT4);
//        cell.setBorder(Rectangle.RIGHT);
//        fifthTable.addCell(cell);
        
        cell = new PdfPCell();
        cell.addElement(new Phrase("  "+"Tax Reserve",font));
    //    cell.addElement(new Phrase("  "+"Varies based on calendar month of closing",font));
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
        cell.addElement(new Phrase("  "+"Homeowners Insurance Reserve",font));
//        cell.addElement(new Phrase("  "+"Provided you have 6 months of remaining coverage",font));
//        cell.addElement(new Phrase("  "+"Note: Taxes for 1st and 2nd installments must be paid or",font));
//        cell.addElement(new Phrase("  "+"will be collected at closing.",font));
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
        
        cell.setColspan(2);
        cell.addElement(linePara);
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(8f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
        fifthTable.addCell(cell);
        
        cell = new PdfPCell(new Phrase("Total Estimated Prepaids",font));
    //    cell.setPaddingTop(4); 
   //    cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
      // cell.setBackgroundColor(lightBlue); 
        fifthTable.addCell(cell);
        
      //  String totEstResDepWthLen = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstResDepWthLen();
        cell = new PdfPCell(new Phrase(totEstResDepWthLen,font));
  //      cell.setPaddingTop(4); 
      //  cell.setPaddingBottom(4);
        
        cell.setUseAscender(true);
        cell.setUseDescender(false);
        cell.setPadding(2);
        cell.setPaddingTop(0);
        cell.setFixedHeight(16f);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setBackgroundColor(lightBlue); 
        cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM );
        fifthTable.addCell(cell);
    }
        else{
        	cell = new PdfPCell(new Phrase("Estimated Prepaids", boldFont));
            cell.setBorder(Rectangle.RIGHT | Rectangle.LEFT | Rectangle.TOP);
           // cell.setPaddingTop(4); 
            //cell.setPaddingBottom(4);
            cell.setBackgroundColor(lightBlue); 
            cell.setColspan(2);
            
            cell.setUseAscender(true);
            cell.setUseDescender(false);
            cell.setPadding(2);
            cell.setPaddingTop(0);
            cell.setFixedHeight(16f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            CustomCell borderFT = app.new CustomCell();       
            cell.setCellEvent(borderFT);
            fifthTable.addCell(cell);
            
       
            cell = new PdfPCell(new Phrase("  "+"Interest",font));
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
            
//            cell = new PdfPCell(new Phrase("  "+"Homeowners Insurance",font));
//            cell.setPaddingTop(4); 
//            cell.setPaddingBottom(4);
//            CustomCell borderFT3 = app.new CustomCell();       
//            cell.setCellEvent(borderFT3);
//            cell.setBorder(Rectangle.LEFT);
//            fifthTable.addCell(cell);
//            
//            String hazIns903 = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getHazIns903();
//            cell = new PdfPCell(new Phrase(hazIns903,font));
//            cell.setPaddingTop(4); 
//            cell.setPaddingBottom(4);
//            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
//            CustomCell borderFT4 = app.new CustomCell();       
//            cell.setCellEvent(borderFT4);
//            cell.setBorder(Rectangle.RIGHT);
//            fifthTable.addCell(cell);
            
            cell = new PdfPCell();
            cell.addElement(new Phrase("  "+"Tax Reserve - Estimated 6 Months",font));
         //   cell.addElement(new Phrase("  "+"Varies based on calendar month of closing",font));
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
            cell.addElement(new Phrase("  "+"Homeowners Insurance Reserve - Estimated 6 Months",font));
//            cell.addElement(new Phrase("  "+"Provided you have 6 months of remaining coverage",font));
//            cell.addElement(new Phrase("  "+"Note: Taxes for 1st and 2nd installments must be paid or",font));
//            cell.addElement(new Phrase("  "+"will be collected at closing.",font));
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
            
            cell.setColspan(2);
            cell.addElement(linePara);
            cell.setUseAscender(true);
            cell.setUseDescender(false);
            cell.setPadding(2);
            cell.setPaddingTop(0);
            cell.setFixedHeight(8f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
            fifthTable.addCell(cell);
            
            cell = new PdfPCell(new Phrase("Total Estimated Prepaids",font));
           // cell.setPaddingTop(4); 
          //  cell.setPaddingBottom(4);
            
            cell.setUseAscender(true);
            cell.setUseDescender(false);
            cell.setPadding(2);
            cell.setPaddingTop(0);
            cell.setFixedHeight(16f);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            
            cell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM );
       //     cell.setBackgroundColor(lightBlue); 
            fifthTable.addCell(cell);
            
          //  String totEstResDepWthLen = generatePdfVO.getLqbTeaserRateUnderQuickQuote().getTotEstResDepWthLen();
            cell = new PdfPCell(new Phrase(totEstResDepWthLen,font));
            cell.setPaddingTop(4); 
            cell.setPaddingBottom(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
           // cell.setBackgroundColor(lightBlue); 
            cell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM );
            fifthTable.addCell(cell);
        	
        }
        
  
        fifthTableCell.addElement(fifthTable);
        mainTable.addCell(fifthTableCell);
        
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(mainTable);
        document.add(paragraph);
        document.add(horizontalLine);
        document.add(new Phrase("\nRATE QUOTE ASSUMPTIONS\n", boldFooterFont));
        document.add(new Chunk(""
                + "Rate displayed are subject to change. On adjustable-rate loans, interest rates are subject to potential increases over the life of the loan, once the initial "
                + "fixed-rate period expires. Rates, loan products & fees subject to change without notice. Your rate and term may vary. If you do not lock in a rate when you "
                + "apply, your rate at closing may differ from the rate in effect when you applied. Subject to underwriting approval. Not all applicants will be approved, Full "
                + "documentation & property insurance required. Loan secured by a lien against your poperty. Consolidating or refinancing debts may increase the time and/or the finance "
                + "charges/total loan amount needed to repay your debt. Terms, conditions & restrictions apply."
                + "\nCall newfi for details at 888-316-3934.",footerFont));
        
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
    public String sendPurchasePdf(GeneratePdfVO generatePdfVO, HttpServletRequest request)
    {
    	String s3Path = "";
    	try {
	    	UserVO user = userProfileService.findUser(generatePdfVO.getUserId());
	    	nmlsId = user.getInternalUserDetail().getNmlsID();
	    	if(nmlsId == null){
	    		nmlsId = "N/A";
	    	}
	    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    	openPdf(byteArrayOutputStream);
	    	
	    	basePath = request.getRealPath("/");
	
		    //UserVO user = userProfileService.findUser(generatePdfVO.getUserId());
		    generatePdf(generatePdfVO, request, byteArrayOutputStream,user);
		    closePdf();
		    s3Path = writeAndUploadFile(byteArrayOutputStream);
			sendPurchasePdfEmail(user,byteArrayOutputStream,generatePdfVO );
			//byteArrayOutputStream.close();
 
    	} catch (Exception ex) {
    		LOGGER.error("Error in generating purchase pdf under quick quote:", ex);
    	}
    	finally{
    		return s3Path; 
    	}
    	 
    }
    
    public String writeAndUploadFile(ByteArrayOutputStream byteOutStream) throws IOException{
    	 FileOutputStream outStream = null; 
    	 String s3Path = "";
    	 try {  
    		 
    		File dir = new File(nexeraUtility.tomcatDirectoryPath()
 			        + File.separator + nexeraUtility.randomStringOfLength());
 			if (!dir.exists()) {
 				dir.mkdirs();
 			}
 			
 			String filePathDest = dir.getAbsolutePath() + File.separator
			        + utils.randomNumber()+ ".pdf";
    		 
    	   outStream = new FileOutputStream(filePathDest);  
    	   byteOutStream.writeTo(outStream);    	   
    	   File fileLocal = new File(filePathDest);
    	   s3Path = s3FileUploadServiceImpl.uploadToS3(fileLocal, "Quote_PDF",
			        "quote_pdf_");
    	 } catch (IOException e) {  
    	   e.printStackTrace();  
    	 } finally {  
    	   outStream.close();  
    	   return s3Path;
    	 } 
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
		substitutions.put("-custname-", new String[] {generatePdfVO.getFirstName() + " " + generatePdfVO.getFirstName() });

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
