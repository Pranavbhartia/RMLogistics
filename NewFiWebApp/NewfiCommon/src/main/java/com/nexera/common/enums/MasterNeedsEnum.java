package com.nexera.common.enums;
/**
 * @author Charu Joshi
 */

/**
 * @author charu
 *
 */
public enum MasterNeedsEnum {
	Divorce_Settlement_Agree_No1 (1,"DIVORCE_SETTLEMENT_AGREE_NO1"),
	Contact_Information_Landlord_Property(2,"Contact_Information_Landlord_Property"),
	Divorce_Separation_Settlement_Agreement_No3 (3,"Divorce_Separation_Settlement_Agreement_No3"),
	Cancelled_Check_Child_Support (4,"Cancelled_Check_Child_Support"),
	Copy_of_Check_Transfer_Escrow_Receipt (5,"Copy_of_Check_Transfer_Escrow_Receipt"),
	Mortgage_Statement (6,"Mortgage_Statement"),
	Mortgage_Equity_Line_Statement (7,"Mortgage_Equity_Line_Statement"),
	Cancelled_Checks_Rent_Payments (8,"Cancelled_Checks_Rent_Payments"),
	Homeowner_Insurance (9,"Homeowner_Insurance"),
	Purchase_Contract_Home_Currently_Sold (10,"Purchase_Contract_Home_Currently_Sold"),
	Homeowner_Insurance_Quote (11,"Homeowner_Insurance_Quote"),
	Additional_Properties (12,"Additional_Properties"),
	Loan_Agreement_2nd_Mortgage_Line (13,"Loan_Agreement_2nd_Mortgage_Line"),
	Purchase_Contract_Including_Addendums (14,"Purchase_Contract_Including_Addendums"),
	Settlement_HUD_1_Property_Recently_Sold (15,"Settlement_HUD_1_Property_Recently_Sold"),
	Proof_of_HOA_Dues (16,"Proof_of_HOA_Dues"),
	Paychecks_Most_Recent_30_Days (17,"Paychecks_Most_Recent_30_Days"),
	W2s_Previous_2_Years (18,"W2s_Previous_2_Years"),
	Social_Security_Award_Letter (19,"Social_Security_Award_Letter"),
	Previous_2_years_1099_1099_s_1099_Rs (20,"Previous_2_years_1099_1099_s_1099_Rs"),
	Evidence_recent_receipt_1099_1099_s_1099_Rs_Income (21,"Evidence_recent_receipt_1099_1099_s_1099_Rs_Income"),
	Federal_Tax_Returns_Previous_2_Years (22,"Federal_Tax_Returns_Previous_2_Years"),
	Tax_Return_Extension_Cancelled_Check_Extension_Payment (23,"Tax_Return_Extension_Cancelled_Check_Extension_Payment"),
	Federal_Corporation_Partnership_K_1s_all_partnerships (24,"Federal_Corporation_Partnership_K_1s_all_partnerships"),
	Year_to_date_Profit_Loss_Business (25,"Year_to_date_Profit_Loss_Business"),
	Year_to_date_Balance_Sheet_Business (26,"Year_to_date_Balance_Sheet_Business"),
	Copies_of_Notes_Held (27,"Copies_of_Notes_Held"),
	Months2_Statements_Bank_Accounts (28,"Months2_Statements_Bank_Accounts"),
	Quarterly_Statement_Retirement_Investment_Accounts (29,"Quarterly_Statement_Retirement_Investment_Accounts"),
	Copy_of_Trust (30,"Copy_of_Trust"),
	Large_Deposits_Source_Documentation (31,"Large_Deposits_Source_Documentation"),
	Rental_Lease_Agreements (32,"Rental_Lease_Agreements"),
	Driver_License_Passport (33,"Driver_License_Passport"),
	Gift_Letter_from_Donor (34,"Gift_Letter_from_Donor"),
	Current_Business_License (35,"Current_Business_License"),
	Copy_of_Relocation_Agreement (36,"Copy_of_Relocation_Agreement"),
	
	System_Disclosure_Need(99, "System_Disclosure_Need"),
	Appraisal_Report(100, "Appraisals Report")
	;
	

    private final int indx;

    private final String identifier;
    MasterNeedsEnum(int levelCode,String identifier) {
        this.indx = levelCode;
        this.identifier=identifier;
    }
    
    public String getIndx() {
    	return this.indx+"";
    }
    public String getIdentifier(){
    	return this.identifier;
    }
    
}
