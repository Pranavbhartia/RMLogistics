package com.nexera.web.rest.util;

import org.springframework.stereotype.Component;

import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;


@Component
public class LQBResponseMapping {

	public void setLqbTeaserRateVo(LqbTeaserRateVo teaserRateVo,LoadResponseVO loadResponseVO) {
		
		
		String fieldId = loadResponseVO.getFieldId();
		
		switch (fieldId) {
		case "sApr":
			teaserRateVo.setAPR(loadResponseVO.getFieldValue());
			break;
		case "sMonthlyPmt":
			teaserRateVo.setPayment(loadResponseVO.getFieldValue());
			break;
		
	    case "sLpTemplateNm":
		    teaserRateVo.setlLpTemplateId(loadResponseVO.getFieldValue());
		    break;
		 
		case "sLpTemplateId":
			teaserRateVo.setlLpTemplateId(loadResponseVO.getFieldValue());
			break;
			
		case "sBrokerLockOriginatorPriceNoteIR":
			teaserRateVo.setTeaserRate(loadResponseVO.getFieldValue());
			break;
		case "sBrokerLockOriginatorPriceBrokComp1PcFee":
			teaserRateVo.setPoint(loadResponseVO.getFieldValue());
			break;
			
		case "s800U1F":
			teaserRateVo.setLenderFee813(loadResponseVO.getFieldValue());
			break;
		case "sLDiscnt":
			teaserRateVo.setCreditOrCharge802(loadResponseVO.getFieldValue());
			break;
		case "sApprF":
			teaserRateVo.setAppraisalFee804(loadResponseVO.getFieldValue());
			break;
		case "sCrF":
			teaserRateVo.setCreditReport805(loadResponseVO.getFieldValue());
			break;
		case "sFloodCertificationF":
			teaserRateVo.setFloodCertification807(loadResponseVO
			        .getFieldValue());
			break;
		case "sWireF":
			teaserRateVo.setWireFee812(loadResponseVO.getFieldValue());
			break;
		case "sTitleInsF":
			teaserRateVo.setLendersTitleInsurance1104(loadResponseVO
			        .getFieldValue());
			break;
		case "sEscrowF":
			teaserRateVo
			        .setClosingEscrowFee1102(loadResponseVO.getFieldValue());
			break;
		case "sRecF":
			teaserRateVo.setRecordingFees1201(loadResponseVO.getFieldValue());
			break;
		case "sRecDeed":
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sRecMortgage":
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sRecRelease":
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sCountyRtc":
			teaserRateVo.setCityCountyTaxStamps1204(loadResponseVO
			        .getFieldValue());
			break;
		case "sHazInsRsrv":
			teaserRateVo.setHazInsReserve1002(loadResponseVO.getFieldValue());
			break;
		case "sOwnerTitleInsF":
			teaserRateVo.setOwnersTitleInsurance1103(loadResponseVO
			        .getFieldValue());
			break;

		default:
			break;
		}
	}

}
