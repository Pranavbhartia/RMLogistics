package com.nexera.core.service;

import java.io.ByteArrayOutputStream;

import com.nexera.common.vo.LoanApplicationReceiptVO;

public interface ReceiptPdfService {

	public ByteArrayOutputStream generateReceipt(
			LoanApplicationReceiptVO applicationReceiptVO);
}
