package com.nexera.core.utility;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.PDFToImage;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDCcitt;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageDecoder;
@Component
public class NexeraUtility {

	private  static final Logger LOGGER = LoggerFactory
	        .getLogger(NexeraUtility.class);

	private  final String OUTPUT_FILENAME_EXT = "jpg";
	private  final String OUTPUT_PREFIX = "-outputPrefix";
	private  final String END_PAGE = "-endPage";

	private  final String PAGE_NUMBER = "1";
	private  final String START_PAGE = "-startPage";

	@SuppressWarnings("unchecked")
	public  List<PDPage> splitPDFTOPages(File file) {
		PDDocument document = null;
		List<PDPage> pdfPages = null;
		// Create a Splitter object
		try {
			document = new PDDocument();
			document = PDDocument.loadNonSeq(file, null);
			return document.getDocumentCatalog().getAllPages();
		} catch (IOException e) {
			LOGGER.info("Exception in splitting pdf document : "
			        + e.getMessage());
		}
		return pdfPages;
	}

	public  List<File> splitPDFPages(File file) {

		List<PDPage> pdfPages = splitPDFTOPages(file);
		List<File> newPdfpages = new ArrayList<File>();
		Integer pageNum = 0;
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

	public  String tomcatDirectoryPath() {
		String rootPath = System.getProperty("catalina.home");
		return rootPath + File.separator + "tmpFiles";
	}

	public  String uploadFileToLocal(MultipartFile file) {
		String filePath = null;
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file

				File dir = new File(this.tomcatDirectoryPath());
				if (!dir.exists())
					dir.mkdirs();

				String fileName = file.getOriginalFilename();

				filePath = dir.getAbsolutePath() + File.separator + fileName;
				// Create the file on server
				File serverFile = new File(filePath);
				BufferedOutputStream stream = new BufferedOutputStream(
				        new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				LOGGER.info("Server File Location="
				        + serverFile.getAbsolutePath());

			} catch (Exception e) {
				LOGGER.info("Exception in uploading file in local "
				        + e.getMessage());
				return null;
			}
		} else {
			return null;
		}
		return filePath;
	}

	public  String joinPDDocuments(List<String> fileUrls)
	        throws IOException, COSVisitorException {
		PDFMergerUtility mergePDF = new PDFMergerUtility();
		String newFilePath = this.tomcatDirectoryPath()
		        + File.separator + randomStringOfLength() + ".pdf";

		for (String fileUrl : fileUrls) {
			LOGGER.info("Adding File with URL" + fileUrl);
			mergePDF.addSource(new File(fileUrl));
		}
		mergePDF.setDestinationFileName(newFilePath);
		mergePDF.mergeDocuments();
		return newFilePath;
	}

	public  String randomStringOfLength() {
		Integer length = 10;
		StringBuffer buffer = new StringBuffer();
		while (buffer.length() < length) {
			buffer.append(uuidString());
		}

		// this part controls the length of the returned string
		return buffer.substring(0, length);
	}

	public  String convertPDFToThumbnail(String pdfFile,
	        String imageFilePath) throws Exception {

		String[] args = new String[7];
		args[0] = START_PAGE;
		args[1] = PAGE_NUMBER;
		args[2] = END_PAGE;
		args[3] = PAGE_NUMBER;
		args[4] = OUTPUT_PREFIX;
		args[5] = imageFilePath + File.separator;
		args[6] = pdfFile;

		try {
			File file = new File(pdfFile);
			String fileName = file.getName().replace(
			        FilenameUtils.getExtension(file.getName()), "");
			PDFToImage.main(args);
			String imageFile = imageFilePath + File.separator + PAGE_NUMBER
			        + "." + OUTPUT_FILENAME_EXT;
			LOGGER.info("Image path for thumbnail : " + imageFile);
			return imageFile;

		} catch (Exception e) {
			throw new Exception();
		}
	}

	private  String uuidString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public  String convertImageToPDF(MultipartFile multipartFile) {
		MultipartFile multipartPDF = null;
		String filepath = null;
		try {
			File file = multipartToFile(multipartFile);

			PDDocument document = new PDDocument();

			// InputStream in = new FileInputStream(file);
			// BufferedImage bimg = ImageIO.read(in);
			float width, height;
			if (multipartFile.getContentType().equalsIgnoreCase("image/tiff")) {
				FileSeekableStream fss = new FileSeekableStream(file);
				ImageDecoder decoder = ImageCodec.createImageDecoder("tiff",
				        fss, null);
				RenderedImage image = decoder.decodeAsRenderedImage();
				System.out.println(image);
				BufferedImage bimg = convertRenderedImage(image);
				width = bimg.getWidth();
				height = bimg.getHeight();
				System.out.println("width : " + width);
				System.out.println("height : " + height);
			} else {
				InputStream in = new FileInputStream(file);
				BufferedImage bimg = ImageIO.read(in);
				width = bimg.getWidth();
				height = bimg.getHeight();

			}

			PDPage page = new PDPage(new PDRectangle(width, height));
			document.addPage(page);

			PDXObjectImage img = null;

			if (multipartFile.getContentType().equalsIgnoreCase("image/jpeg")) {
				img = new PDJpeg(document, new FileInputStream(file));
			} else if (multipartFile.getContentType().equalsIgnoreCase("image/png")) {
				img = new PDPixelMap(document, ImageIO.read(file));
			} else if (multipartFile.getContentType().equalsIgnoreCase("image/tiff")) {
				img = new PDCcitt(document, new RandomAccessFile(file, "r"));

			}

			PDPageContentStream contentStream = new PDPageContentStream(
			        document, page);
			contentStream.drawImage(img, 0, 0);

			contentStream.close();
			// in.close();

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
			e.printStackTrace();
		}
		return filepath;

	}

	public  File multipartToFile(MultipartFile multipart)
	        throws IllegalStateException, IOException {
		File convFile = new File(multipart.getOriginalFilename());
		multipart.transferTo(convFile);
		return convFile;
	}

	public  MultipartFile filePathToMultipart(String filePath)
	        throws IOException {
		File file = new File(filePath);
		DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false,
		        file.getName(), (int) file.length(), file.getParentFile());
		fileItem.getOutputStream();
		MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
		return multipartFile;
	}

	public  String convertImageToPDFDocument(MultipartFile multipartFile) {

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
			e.printStackTrace();
		} finally {
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return filepath;

	}

	public  BufferedImage convertRenderedImage(RenderedImage img) {
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

}