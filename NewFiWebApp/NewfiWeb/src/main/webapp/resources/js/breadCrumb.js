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
        var indx=parseInt((appUserDetails.loanAppFormCompletionStatus*count)/100);
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
        if(appUserDetails.loanAppFormCompletionStatus&&appUserDetails.loanAppFormCompletionStatus>0){
            var contxt=ob.getContxtByCompletionStatus();
            contxt.clickHandler();
        }else{
            paintSelectLoanTypeQuestion();
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
            if(contxtHolder.getPercentageForStep(ob.indx)<=appUserDetails.loanAppFormCompletionStatus)
            if(ob.item.onselect){
                ob.item.onselect();
            }
        }
    };

    return contxt;
}