package com.nexera.web.rest.util;

import org.springframework.stereotype.Component;

import com.nexera.common.vo.lqb.LoadResponseVO;
import com.nexera.common.vo.lqb.LqbTeaserRateVo;


@Component
public class LQBResponseMapping {

	public LqbTeaserRateVo setLqbTeaserRateVo(LoadResponseVO loadResponseVO) {
		
		
		String fieldId = loadResponseVO.getFieldId();
		LqbTeaserRateVo teaserRateVo = null;
		switch (fieldId) {
		case "sApr":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setAPR(loadResponseVO.getFieldValue());
			break;
		case "sMonthlyPmt":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setPayment(loadResponseVO.getFieldValue());
			break;
		
	    case "sLpTemplateNm":
	    	teaserRateVo = new LqbTeaserRateVo();
		    teaserRateVo.setlLpTemplateId(loadResponseVO.getFieldValue());
		    break;
		 
		case "sLpTemplateId":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setlLpTemplateId(loadResponseVO.getFieldValue());
			break;
			
		case "sBrokerLockOriginatorPriceNoteIR":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setTeaserRate(loadResponseVO.getFieldValue());
			break;
		case "sBrokerLockOriginatorPriceBrokComp1PcFee":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setPoint(loadResponseVO.getFieldValue());
			break;
			
		case "s800U1F":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setLenderFee813(loadResponseVO.getFieldValue());
			break;
		case "sLDiscnt":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setCreditOrCharge802(loadResponseVO.getFieldValue());
			break;
		case "sApprF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setAppraisalFee804(loadResponseVO.getFieldValue());
			break;
		case "sCrF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setCreditReport805(loadResponseVO.getFieldValue());
			break;
		case "sFloodCertificationF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setFloodCertification807(loadResponseVO
			        .getFieldValue());
			break;
		case "sWireF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setWireFee812(loadResponseVO.getFieldValue());
			break;
		case "sTitleInsF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setLendersTitleInsurance1104(loadResponseVO
			        .getFieldValue());
			break;
		case "sEscrowF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo
			        .setClosingEscrowFee1102(loadResponseVO.getFieldValue());
			break;
		case "sRecF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setRecordingFees1201(loadResponseVO.getFieldValue());
			break;
		case "sRecDeed":
			teaserRateVo = new LqbTeaserRateVo();
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sRecMortgage":
			teaserRateVo = new LqbTeaserRateVo();
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sRecRelease":
			teaserRateVo = new LqbTeaserRateVo();
			if (null != teaserRateVo.getRecordingFees1202()
			        || !loadResponseVO.getFieldValue().equals("$0.00"))
				teaserRateVo.setRecordingFees1202(loadResponseVO
				        .getFieldValue());
			break;
		case "sCountyRtc":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setCityCountyTaxStamps1204(loadResponseVO
			        .getFieldValue());
			break;
		case "sHazInsRsrv":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setHazInsReserve1002(loadResponseVO.getFieldValue());
			break;
		case "sOwnerTitleInsF":
			teaserRateVo = new LqbTeaserRateVo();
			teaserRateVo.setOwnersTitleInsurance1103(loadResponseVO
			        .getFieldValue());
			break;

		default:
			break;
		}
		return teaserRateVo;
	}

}
