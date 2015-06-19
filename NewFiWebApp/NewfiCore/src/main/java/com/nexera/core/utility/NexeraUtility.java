package com.nexera.core.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.PDFToImage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.nexera.common.commons.CommonConstants;
import com.nexera.common.commons.WebServiceMethodParameters;
import com.nexera.common.entity.ExceptionMaster;
import com.nexera.common.entity.ExceptionMasterExecution;
import com.nexera.common.entity.UploadedFilesList;
import com.nexera.common.exception.NonFatalException;
import com.nexera.common.vo.UserVO;
import com.nexera.common.vo.lqb.CreditScoreResponseVO;
import com.nexera.common.vo.lqb.LQBedocVO;
import com.nexera.core.lqb.broker.LqbInvoker;
import com.nexera.core.service.ExceptionService;
import com.nexera.core.service.SendGridEmailService;
import com.nexera.core.service.impl.S3FileUploadServiceImpl;
import com.nexera.workflow.exception.FatalException;
import com.sendgrid.SendGrid.Email;
import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;

@Component
public class NexeraUtility {

	@Autowired
	private S3FileUploadServiceImpl s3FileUploadServiceImpl;

	@Autowired
	private SendGridEmailService sendGridEmailService;

	@Autowired
	private ExceptionService exceptionService;

	@Autowired
	private LqbInvoker lqbInvoker;

	@Value("${cryptic.key}")
	private String crypticKey;

	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
	        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	private static final Logger LOGGER = LoggerFactory
	        .getLogger(NexeraUtility.class);

	private final String OUTPUT_FILENAME_EXT = "jpg";
	private final String OUTPUT_PREFIX = "-outputPrefix";
	private final String END_PAGE = "-endPage";

	private final String PAGE_NUMBER = "1";
	private final String START_PAGE = "-startPage";

	@SuppressWarnings("unchecked")
	public List<PDPage> splitPDFTOPages(File file) {
		PDDocument document = null;
		List<PDPage> pdfPages = null;
		// Create a Splitter object
		try {
			document = new PDDocument();
			// TODO: Look at this warning
			document = PDDocument.loadNonSeq(file, null);
			return document.getDocumentCatalog().getAllPages();
		} catch (IOException e) {
			LOGGER.info("Exception in splitting pdf document : "
			        + e.getMessage());
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					LOGGER.info("Unable to close the PDF document "
					        + e.getMessage());
				}
			}
		}
		return pdfPages;
	}

	public List<File> splitPDFPagesUsingIText(File file) throws IOException {
		List<File> newPdfpages = new ArrayList<File>();

		PdfReader reader = new PdfReader(file.getAbsolutePath());

		Integer numberOfpages = reader.getNumberOfPages();
		for (int i = 1; i <= numberOfpages; i++) {
			Document document = null;
			PdfCopy writer = null;
			String filepath = tomcatDirectoryPath() + File.separator
			        + file.getName().replace(".pdf", "") + "_" + i + ".pdf";
			try {
				document = new Document(reader.getPageSizeWithRotation(1));
				writer = new PdfCopy(document, new FileOutputStream(filepath));
				document.open();
				PdfImportedPage page = writer.getImportedPage(reader, i);
				writer.addPage(page);
				document.close();
				writer.close();
				newPdfpages.add(new File(filepath));
			} catch (Exception e) {
				LOGGER.info("Exception in converting pdf pages document : "
				        + e.getMessage());
				throw new FatalException("Error in Splitting");
			} finally {
				if (document != null) {
					document.close();
				}
				if (writer != null) {
					writer.close();
				}
			}
		}

		return newPdfpages;
	}

	public List<File> splitPDFPages(File file) {

		List<PDPage> pdfPages = splitPDFTOPages(file);
		List<File> newPdfpages = new ArrayList<File>();
		Integer pageNum = 0;
		PDPageContentStream contentStream = null;
		for (PDPage pdPage : pdfPages) {

			try {

				PDDocument newDocument = new PDDocument();
				newDocument.addPage(pdPage);
				String filepath = tomcatDirectoryPath() + File.separator
				        + file.getName().replace(".pdf", "") + "_" + pageNum
				        + ".pdf";

				File newFile = new File(filepath);
				newFile.createNewFile();

				newDocument.save(filepath);
				newDocument.close();
				pageNum++;

				newPdfpages.add(newFile);
			} catch (Exception e) {
				LOGGER.info("Exception in converting pdf pages document : "
				        + e.getMessage());
			}
		}

		return newPdfpages;
	}

	public String tomcatDirectoryPath() {
		String rootPath = System.getProperty("catalina.home");
		return rootPath + File.separator + "tmpFiles";
	}

	public String uploadFileToLocal(File file) {
		String filePath = null;

		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);

			String fileName = file.getName();

			filePath = file.getAbsolutePath() + File.separator + fileName;
			// Create the file on server
			File serverFile = new File(filePath);
			BufferedOutputStream stream = new BufferedOutputStream(
			        new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

			LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

		} catch (Exception e) {
			LOGGER.info("Exception in uploading file in local "
			        + e.getMessage());
			throw new FatalException("Cannot upload file to tomcat directory");
		}

		return filePath;
	}

	public File joinPDDocuments(List<File> files) throws IOException,
	        COSVisitorException {
		PDFMergerUtility mergePDF = new PDFMergerUtility();
		String newFilePath = this.tomcatDirectoryPath() + File.separator
		        + randomStringOfLength() + ".pdf";

		for (File file : files) {
			LOGGER.info("Adding File with URL" + file);
			mergePDF.addSource(file);
			if (file.exists()) {
				file.delete();
			}
		}
		mergePDF.setDestinationFileName(newFilePath);
		mergePDF.mergeDocuments();

		return new File(newFilePath);
	}

	public String randomStringOfLength() {
		Integer length = 30;
		StringBuffer buffer = new StringBuffer();
		while (buffer.length() < length) {
			buffer.append(uuidString());
		}

		// this part controls the length of the returned string
		return buffer.substring(0, length);
	}

	public String convertPDFToThumbnail(String pdfFile, String imageFilePath)
	        throws Exception {

		String[] args = new String[7];
		args[0] = START_PAGE;
		args[1] = PAGE_NUMBER;
		args[2] = END_PAGE;
		args[3] = PAGE_NUMBER;
		args[4] = OUTPUT_PREFIX;
		args[5] = imageFilePath;
		args[6] = pdfFile;

		try {
			PDFToImage.main(args);
			String imageFile = imageFilePath + PAGE_NUMBER + "."
			        + OUTPUT_FILENAME_EXT;

			String thumbpath = imageFilePath + (PAGE_NUMBER + 1) + "."
			        + OUTPUT_FILENAME_EXT;

			File imageFileObj = new File(imageFile);
			LOGGER.info("Image path for thumbnail : " + imageFile);
			Thumbnails.of(imageFileObj)
			        .size(Integer.parseInt("100"), Integer.parseInt("100"))
			        .toFile(thumbpath);

			if (imageFileObj.exists()) {
				imageFileObj.delete();
			}

			return thumbpath;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	private String uuidString() {
		return UUID.randomUUID().toString();
	}

	public String convertImageToPDF(File file, String contentType) {
		MultipartFile multipartPDF = null;
		String filepath = null;
		FileSeekableStream fss = null;
		InputStream in = null;
		try {

			PDDocument document = new PDDocument();

			// InputStream in = new FileInputStream(file);
			// BufferedImage bimg = ImageIO.read(in);
			float width, height;
			if (contentType.equalsIgnoreCase("image/tiff")) {
				fss = new FileSeekableStream(file);
				ImageDecoder decoder = ImageCodec.createImageDecoder("tiff",
				        fss, null);
				RenderedImage image = decoder.decodeAsRenderedImage();

				BufferedImage bimg = convertRenderedImage(image);
				width = bimg.getWidth();
				height = bimg.getHeight();

			} else {
				in = new FileInputStream(file);
				BufferedImage bimg = ImageIO.read(in);
				width = bimg.getWidth();
				height = bimg.getHeight();

			}

			PDPage page = new PDPage(new PDRectangle(width, height));
			document.addPage(page);

			PDXObjectImage img = null;

			if (contentType.equalsIgnoreCase("image/jpeg")) {
				img = new PDJpeg(document, new FileInputStream(file));
			} else if (contentType.equalsIgnoreCase("image/png")) {
				img = new PDPixelMap(document, ImageIO.read(file));
			} else if (contentType.equalsIgnoreCase("image/tiff")) {
				img = new PDCcitt(document, new RandomAccessFile(file, "r"));
			}

			PDPageContentStream contentStream = new PDPageContentStream(
			        document, page);
			contentStream.drawImage(img, 0, 0);

			contentStream.close();
			// in.close();

			filepath = file.getAbsolutePath().substring(0,
			        file.getAbsolutePath().lastIndexOf(File.separator))
			        + File.separator
			        + file.getName().replace(
			                FilenameUtils.getExtension(file.getName()), "")
			        + "pdf";

			LOGGER.info("filepath after convertin to PDF : " + filepath);

			File newFile = new File(filepath);
			newFile.createNewFile();

			document.save(filepath);
			document.close();

		} catch (Exception e) {

			LOGGER.error("Exception in convertImageToPDF : ", e);
			throw new FatalException("Cannot convert image to PDF");
		} finally {
			if (fss != null) {
				try {
					fss.close();
				} catch (IOException e) {
					// TODO Auto'-generated catch block
					LOGGER.warn("Issue closing file stream", e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.warn("Issue closing file stream", e);
				}
			}

		}
		return filepath;

	}

	public void deleteFileFolderFromLocalDirectory(File file) {
		if (file.exists()) {
			file.delete();
		}
	}

	public File multipartToFile(MultipartFile multipart)
	        throws IllegalStateException, IOException {
		String uniqueId = uuidString();
		String folderName = tomcatDirectoryPath() + File.separator + uniqueId;
		File directory = new File(folderName);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		File convFile = new File(directory.getAbsolutePath() + File.separator
		        + multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	public MultipartFile filePathToMultipart(File file) throws IOException {

		DiskFileItem fileItem = new DiskFileItem("file", "application/pdf",
		        false, file.getName(), (int) file.length(),
		        file.getParentFile());
		fileItem.getOutputStream();
		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		return multipartFile;
	}

	public String convertImageToPDFDocument(MultipartFile multipartFile) {

		File file = null;
		String filepath = null;
		PDDocument document = null;
		try {
			file = multipartToFile(multipartFile);
			document = PDDocument.loadNonSeq(file, null);

			// we will add the image to the first page.
			PDPage page = (PDPage) document.getDocumentCatalog().getAllPages()
			        .get(0);

			PDXObjectImage ximage = null;
			if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".jpg")) {
				ximage = new PDJpeg(document, new FileInputStream(file));
			} else if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".tif")
			        || FilenameUtils.getExtension(file.getName()).toLowerCase()
			                .endsWith(".tiff")) {
				ximage = new PDCcitt(document, new RandomAccessFile(file, "r"));
			} else if (FilenameUtils.getExtension(file.getName()).toLowerCase()
			        .endsWith(".png")) {
				ximage = new PDPixelMap(document, ImageIO.read(file));
			} else {
				// BufferedImage awtImage = ImageIO.read( new File( image ) );
				// ximage = new PDPixelMap(doc, awtImage);
				throw new IOException("Image type not supported:"
				        + FilenameUtils.getExtension(file.getName()));
			}

			PDPageContentStream contentStream = new PDPageContentStream(
			        document, page);
			contentStream.drawImage(ximage, 0, 0);

			contentStream.close();

			filepath = tomcatDirectoryPath()
			        + File.separator
			        + file.getName().replace(
			                FilenameUtils.getExtension(file.getName()), "")
			        + "pdf";
			LOGGER.info("filepath after convertin to PDF : " + filepath);

			File newFile = new File(filepath);
			newFile.createNewFile();

			document.save(filepath);
			document.close();
		} catch (Exception e) {
			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());

		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Exception in convertImageToPDF : "
					        + e.getMessage());
				}
			}
		}
		return filepath;

	}

	public BufferedImage convertRenderedImage(RenderedImage img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		ColorModel cm = img.getColorModel();
		int width = img.getWidth();
		int height = img.getHeight();
		WritableRaster raster = cm
		        .createCompatibleWritableRaster(width, height);
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		Hashtable properties = new Hashtable();
		String[] keys = img.getPropertyNames();
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				properties.put(keys[i], img.getProperty(keys[i]));
			}
		}
		BufferedImage result = new BufferedImage(cm, raster,
		        isAlphaPremultiplied, properties);
		img.copyData(raster);
		return result;
	}

	public String createPDFFromStream(InputStream inputStream)
	        throws IOException, COSVisitorException {
		PDDocument document = null;
		String filePath = null;
		try {
			// create the PDF document object
			document = new PDDocument();
			InputStream input = inputStream;
			BufferedReader bufferReader = new BufferedReader(
			        new InputStreamReader(input));
			String theString = "";
			String line = "";
			while ((line = bufferReader.readLine()) != null)
				theString += line + "\r\n";

			byte[] byteArray = theString.getBytes();
			theString = new String(byteArray, "UTF-8");
			PDPage page = new PDPage();
			document.addPage(page);
			PDPageContentStream stream = new PDPageContentStream(document, page);
			stream.beginText();
			PDFont font = PDType1Font.HELVETICA_BOLD;
			stream.setFont(font, 12);
			stream.moveTextPositionByAmount(100, 700);
			stream.setNonStrokingColor(Color.ORANGE);
			stream.drawString(theString);
			stream.endText();

			filePath = tomcatDirectoryPath() + File.separator
			        + randomStringOfLength() + ".pdf";

			// save the document to the file stream.
			document.save(filePath);
		} finally {
			if (document != null) {
				document.close();
			}

		}
		return filePath;
	}

	public String getContentFromFile(byte[] bytes) throws IOException,
	        Exception {

		String encodedText = new String(Base64.encodeBase64(bytes));
		return encodedText;
	}

	public byte[] getContentFromStream(InputStream stream) throws IOException,
	        Exception {

		byte[] bytes = IOUtils.toByteArray(stream);
		return bytes;
	}

	public File copyInputStreamToFile(InputStream in) throws IOException {
		File file = null;
		OutputStream out = null;
		try {
			file = new File(tomcatDirectoryPath() + File.separator
			        + randomStringOfLength() + ".pdf");
			out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			LOGGER.error("Exception in convertImageToPDF : " + e.getMessage());
		} finally {
			out.close();
			in.close();
		}
		return file;
	}

	public MultipartFile getMultipartFileFromInputStream(InputStream instream)
	        throws IOException {
		File file = copyInputStreamToFile(instream);
		return filePathToMultipart(file);

	}

	public byte[] convertInputStreamToByteArray(InputStream inputStream) {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		try {
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}
		} catch (IOException e) {
			// TODO call exception class
		}
		return baos.toByteArray();
	}

	public File convertInputStreamToFile(InputStream inputStream)
	        throws COSVisitorException, IOException {

		OutputStream outputStream = null;
		File file = null;
		try {
			String filePath = tomcatDirectoryPath();
			File directory = new File(filePath);
			if (!directory.exists()) {
				directory.mkdir();
			}
			file = new File(filePath + File.separator + randomStringOfLength()
			        + ".pdf");
			if (file.createNewFile()) {
				outputStream = new FileOutputStream(file);
				byte[] bytes = convertInputStreamToByteArray(inputStream);
				outputStream.write(bytes);
				outputStream.flush();

			}
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {

				}
			}
			if (outputStream != null) {
				try {
					// outputStream.flush();
					outputStream.close();
				} catch (IOException e) {

				}

			}

		}
		return file;

	}

	public Date convertToUTC(Date inputDate) {
		return null;
	}

	public String getUUIDBasedNoteForLQBDocument(String uuId, UserVO user) {

		StringBuffer stringBuf = new StringBuffer();
		stringBuf.append("UUID:").append(uuId);
		stringBuf.append(" UploadedBy:");
		stringBuf.append(user.getFirstName()).append("-")
		        .append(user.getLastName());

		return stringBuf.toString();
	}

	public void getStreamForThumbnailFromS3Path(HttpServletResponse response,
	        byte[] bytes) throws Exception {

		response.setContentLength(bytes.length);
		response.setContentType("image/jpeg");

		ServletOutputStream servletoutputstream = response.getOutputStream();
		servletoutputstream.write(bytes);
		servletoutputstream.flush();

	}

	public InputStream getInputStreamFromFile(String fileUrl, String isImage)
	        throws Exception {

		InputStream input = null;
		try {
			input = new URL(fileUrl).openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new NonFatalException("Exception in reading thumbnail");

		} finally {
			try {
				input.close();
			} catch (IOException e) {
				LOGGER.info("exception in closing document");
			}
		}

		return input;
	}

	public List<String> getFileUUIDList(List<UploadedFilesList> uploadList) {

		List<String> uploadFileUUIDList = new ArrayList<String>();
		if (uploadList != null) {
			for (UploadedFilesList uploadFiles : uploadList) {
				uploadFileUUIDList.add(uploadFiles.getUuidFileId());
			}
		}
		return uploadFileUUIDList;
	}

	public String fetchUUID(String uuidString) {
		if (uuidString != null) {
			if (uuidString.contains("UUID")) {
				String keyValuePair[] = uuidString.split(" ");
				Map<String, String> map = new HashMap<String, String>();
				String pair = keyValuePair[0];

				String[] entry = pair.split(":");
				map.put(entry[0].trim(), entry[1].trim());

				return map.get("UUID");

			}
		}
		return null;

	}

	public List<String> getEdocsUUIDList(List<LQBedocVO> lqbedocVOList) {
		List<String> edocList = new ArrayList<String>();
		for (LQBedocVO edoc : lqbedocVOList) {
			String uuidDetails = edoc.getDescription();
			String uuid = fetchUUID(uuidDetails);
			if (uuid != null)
				edocList.add(uuid);
		}
		return edocList;
	}

	public List<String> getEdocsDocList(List<LQBedocVO> lqbedocVOList) {
		List<String> edocList = new ArrayList<String>();
		for (LQBedocVO edoc : lqbedocVOList) {
			edocList.add(edoc.getDocid());
		}
		return edocList;
	}

	public byte[] getBytes(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	public ExceptionMasterExecution putExceptionMasterIntoExecution(
	        ExceptionMaster exceptionMaster, String exceptionMessage) {
		ExceptionMasterExecution exceptionMasterExecution = new ExceptionMasterExecution();
		exceptionMasterExecution.setExceptionMaster(exceptionMaster);
		exceptionMasterExecution.setExceptionMessage(exceptionMessage);
		exceptionMasterExecution.setExceptionTime(new Date());
		return exceptionService
		        .putExceptionMasterIntoExecution(exceptionMasterExecution);
	}

	public ExceptionMaster getExceptionMasterByType(String exceptionType) {

		return exceptionService.getExceptionMasterByType(exceptionType);
	}

	public void sendExceptionEmail(String exceptionMessage) {
		String[] tos = new String[1];
		tos[0] = CommonConstants.RAREMILE_SUPPORT_EMAIL_ID;
		Email email = new Email();
		String subject = "Exception Occured";
		email.setText(exceptionMessage);
		email.setFrom(CommonConstants.SENDER_DEFAULT_USER_NAME
		        + CommonConstants.SENDER_EMAIL_ID);
		email.setSubject(subject);
		email.setTo(tos);
		sendGridEmailService.sendExceptionEmail(email);

	}

	public String asciiTrim(String string) {
		if (string == null) {
			return null;
		} else {
			return string.trim().replaceAll("[^\\x00-\\x7F]", "");
		}

	}

	public String encryptEmailAddress(String emailId) {
		MessageDigest messageDigest = null;
		StringBuffer sb = new StringBuffer();
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {

			LOGGER.error("Unable to fetch the encryption algorithm ");
		}
		if (messageDigest != null) {
			messageDigest.reset();
			messageDigest.update(emailId.getBytes());
			byte[] byteData = messageDigest.digest();
			// convert the byte to hex format method 1
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
				        .substring(1));
			}
			String milliSecond = String.valueOf(System.currentTimeMillis());
			sb.append(milliSecond);
		}
		return sb.toString();

	}

	public String removeBackSlashDelimiter(String string) {
		if (string != null) {
			string = string.replace("\\", "");
		}
		return string;
	}

	public String encrypt(byte[] salt, String secretKey, String plainText)
	        throws NoSuchAlgorithmException, InvalidKeySpecException,
	        NoSuchPaddingException, InvalidKeyException,
	        InvalidAlgorithmParameterException, UnsupportedEncodingException,
	        IllegalBlockSizeException, BadPaddingException {
		// Key generation for enc and desc
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, 19);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
		        .generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, 19);

		// Enc process
		Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		String charSet = "UTF-8";
		byte[] in = plainText.getBytes(charSet);
		byte[] out = ecipher.doFinal(in);
		String encStr = new sun.misc.BASE64Encoder().encode(out);
		return encStr;
	}

	public String decrypt(byte[] salt, String secretKey, String encryptedText)
	        throws NoSuchAlgorithmException, InvalidKeySpecException,
	        NoSuchPaddingException, InvalidKeyException,
	        InvalidAlgorithmParameterException, UnsupportedEncodingException,
	        IllegalBlockSizeException, BadPaddingException, IOException {
		// Key generation for enc and desc
		if (null == encryptedText || encryptedText.isEmpty()) {
			return null;
		}
		KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, 19);
		SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES")
		        .generateSecret(keySpec);
		// Prepare the parameter to the ciphers
		AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, 19);
		// Decryption process; same key will be used for decr
		Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		byte[] enc = new sun.misc.BASE64Decoder().decodeBuffer(encryptedText);
		byte[] utf8 = dcipher.doFinal(enc);
		String charSet = "UTF-8";
		String plainStr = new String(utf8, charSet);
		return plainStr;
	}

	public static JSONObject createAuthObject(String opName, String userName,
	        String password) {
		JSONObject json = new JSONObject();
		try {
			json.put(WebServiceMethodParameters.PARAMETER_USERNAME, userName);
			json.put(WebServiceMethodParameters.PARAMETER_PASSWORD, password);
			json.put("opName", opName);
		} catch (JSONException e) {
			// LOG.error("Invalid Json String ");
			throw new FatalException("Could not parse json " + e.getMessage());
		}
		return json;
	}

	public JSONObject createCreditScoreJSONObject(String opName,
	        String sTicket, String lqbLoanId, int format) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_TICKET,
			        sTicket);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);

			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			json = null;
		}
		return json;
	}

	public List<CreditScoreResponseVO> parseCreditScoresResponse(
	        String creditScoreResponse) {

		// get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			// get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			CreditScoreXMLHandler handler = new CreditScoreXMLHandler();
			// parse the file and also register this class for call backs
			sp.parse(new InputSource(new StringReader(creditScoreResponse)),
			        handler);
			return handler.getCreditScoreResponseList();

		} catch (SAXException se) {
			LOGGER.error("Exception caught " + se.getMessage());
		} catch (ParserConfigurationException pce) {
			LOGGER.error("Exception caught " + pce.getMessage());
		} catch (IOException ie) {
			LOGGER.error("Exception caught " + ie.getMessage());
		}

		return null;
	}

	public Map<String, String> fillCreditScoresInMap(
	        List<CreditScoreResponseVO> creditScoreResponseVOList) {
		LOGGER.debug("Inside method fillCreditScoresInMap");
		Map<String, String> creditScoreMap = new HashMap<String, String>();
		for (CreditScoreResponseVO creditScoreResponseVO : creditScoreResponseVOList) {
			if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_BORROWER_EQUIFAX_SCORE)) {
				creditScoreMap.put(
				        CoreCommonConstants.SOAP_XML_BORROWER_EQUIFAX_SCORE,
				        creditScoreResponseVO.getFieldValue());
			} else if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_BORROWER_TRANSUNION_SCORE)) {
				creditScoreMap.put(
				        CoreCommonConstants.SOAP_XML_BORROWER_TRANSUNION_SCORE,
				        creditScoreResponseVO.getFieldValue());
			} else if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_BORROWER_EXPERIAN_SCORE)) {
				creditScoreMap.put(
				        CoreCommonConstants.SOAP_XML_BORROWER_EXPERIAN_SCORE,
				        creditScoreResponseVO.getFieldValue());
			} else if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_CO_BORROWER_EQUIFAX_SCORE)) {
				creditScoreMap.put(
				        CoreCommonConstants.SOAP_XML_CO_BORROWER_EQUIFAX_SCORE,
				        creditScoreResponseVO.getFieldValue());
			} else if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_CO_BORROWER_TRANSUNION_SCORE)) {
				creditScoreMap
				        .put(CoreCommonConstants.SOAP_XML_CO_BORROWER_TRANSUNION_SCORE,
				                creditScoreResponseVO.getFieldValue());
			} else if (creditScoreResponseVO.getFieldId().equals(
			        CoreCommonConstants.SOAP_XML_CO_BORROWER_EXPERIAN_SCORE)) {
				creditScoreMap
				        .put(CoreCommonConstants.SOAP_XML_CO_BORROWER_EXPERIAN_SCORE,
				                creditScoreResponseVO.getFieldValue());
			}
		}
		return creditScoreMap;
	}

	public JSONObject createLoadJsonObject(Map<String, String> requestXMLMap,
	        String opName, String lqbLoanId, int format,
	        ExceptionMaster exceptionMaster) {
		JSONObject json = new JSONObject();
		JSONObject jsonChild = new JSONObject();
		try {
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_LOAN_NUMBER,
			        lqbLoanId);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_FORMAT, format);
			jsonChild.put(WebServiceMethodParameters.PARAMETER_S_XML_QUERY_MAP,
			        requestXMLMap);
			json.put("opName", opName);
			json.put("loanVO", jsonChild);
		} catch (JSONException e) {
			LOGGER.error("Invalid Json String ");
			if (exceptionMaster != null) {
				putExceptionMasterIntoExecution(exceptionMaster, e.getMessage());
				sendExceptionEmail(e.getMessage());
			}
		}
		return json;
	}

}