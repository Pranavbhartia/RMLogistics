package com.nexera.core.utility;

import org.apache.pdfbox.PDFToImage;
import org.springframework.stereotype.Component;

import com.nexera.common.exception.FatalException;
import com.nexera.common.exception.NonFatalException;

@Component
public class ThumbNailGenerator {

	private static final String OUTPUT_FILENAME = "1.jpg";
	private static final String OUTPUT_PREFIX = "-outputPrefix";
	private static final String END_PAGE = "-endPage";

	private static final String PAGE_NUMBER = "1";
	private static final String START_PAGE = "-startPage";

	/**
	 * Generates the image file from a pdf file
	 * 
	 * @param pdfFile
	 *            - Path and name of the PDF file
	 * @param imageFile
	 *            - Path and name of the image file to be generated, make sure a
	 *            unique folder is created for all files, result file is always
	 *            1.png
	 * @throws NonFatalException
	 *             - If there is an exception in generation of the image file
	 */

	public String convertPDFToThumbnail(String pdfFile, String imageFilePath)
			throws FatalException {

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
			return imageFilePath + OUTPUT_FILENAME;

		} catch (Exception e) {
			throw new FatalException("Error generating thumbnail for file: "
					+ pdfFile, e);
		}
	}

}
