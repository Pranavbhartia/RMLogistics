var contxtHolder={
    bredCrumbContxts:{},
    addBreadCrumbContxt:function(contxt,indx){
        var ob=this;
        if(ob.bredCrumbContxts[(indx-1)]){
            var tmpCntxt=ob.bredCrumbContxts[(indx-1)];
            tmpCntxt.nextItem=contxt;
            contxt.prevItem=tmpCntxt;
        }
        ob.bredCrumbContxts[indx]=contxt;
    },
    removeBreadCrumbContxt:function(indx){
        var ob=this;
        var prevItem;
        var nextItem;
        if(ob.bredCrumbContxts[(indx)]){
            prevItem=ob.bredCrumbContxts[(indx)].prevItem;
        }
    },
    createBreadCrumbItem:function(element,item,indx){
        var ob=this;
        var contxt=getBredCrumContext(element,item,indx);
        ob.addBreadCrumbContxt(contxt,indx);
        return contxt;
    },
    getContxtByCompletionStatus:function(){
        var ob=this;
        var count=Object.keys(ob.bredCrumbContxts).length;
        var indx=Math.round((appUserDetails.loanAppFormCompletionStatus*count)/100);
        if(indx==0)
            indx=1;
        return ob.bredCrumbContxts[(indx)];
    },
    getPercentageForStep:function(num){
        var ob=this;
        var count=Object.keys(ob.bredCrumbContxts).length;
        var percentage=(num*100)/count;
        return percentage;
    },
    switchBreadCrumb:function(){
        var ob=this;
        if(appUserDetails.loanAppFormCompletionStatus&&appUserDetails.loanAppFormCompletionStatus>0||appUserDetails.loanAppFormCompletionStatus==1){
	    if(appUserDetails.loan&&appUserDetails.loan.loanType&&appUserDetails.loan.loanType.id){
                if(appUserDetails.loan.loanType.id==1)
                    purchase = true;
                else
                    purchase = false;
            }
            var contxt=ob.getContxtByCompletionStatus();
            contxt.clickHandler();
        }else{
        	if(appUserDetails.loanType.loanTypeCd && appUserDetails.loanType.loanTypeCd != "NONE"){
        		if(typeof(appUserDetails.purchaseDetails.livingSituation)==='undefined'){
        			paintSelectLoanTypeQuestion();
        		}
        		else{
        		paintRefinanceStep2();
        		}
        	//	paintSelectLoanTypeQuestion();
        	}else{
            paintSelectLoanTypeQuestion();
        	}
        }
        
    }
}
//sample Call
//contxtHolder.createBreadCrumbItem(element,item,indx);

function getBredCrumContext(element,item,indx){
    var contxt={
        cntxtElement:element,
        nextItem:undefined,
        prevItem:undefined,
        clickable:true,
        item:item,
        indx:indx,
        changeItemProgress:function(status){

        },
        resetForParentChange:function(callback){
            
        },
        clickHandler:function(callback){
            var ob=this;
            var applicationLocked=checkLqbFileId();
            if((Math.round(contxtHolder.getPercentageForStep(ob.indx))<=Math.round(appUserDetails.loanAppFormCompletionStatus)||appUserDetails.loanAppFormCompletionStatus==1)||applicationLocked)
            if(ob.item.onselect){
                ob.item.onselect();
            }
            removeToastMessage();
        }
    };

    return contxt;
}