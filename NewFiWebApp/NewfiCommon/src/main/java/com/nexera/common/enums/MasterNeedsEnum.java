package com.nexera.common.enums;
/**
 * @author Charu Joshi
 */

/**
 * @author charu
 *
 */
public enum MasterNeedsEnum {
	Divorce_Settlement_Agree_No1 (1),
	Contact_Information_Landlord_Property(2),
	Divorce_Separation_Settlement_Agreement_No3 (3),
	Cancelled_Check_Child_Support (4),
	Copy_of_Check_Transfer_Escrow_Receipt (5),
	Mortgage_Statement (6),
	Mortgage_Equity_Line_Statement (7),
	Cancelled_Checks_Rent_Payments (8),
	Homeowner_Insurance (9),
	Purchase_Contract_Home_Currently_Sold (10),
	Homeowner_Insurance_Quote (11),
	Additional_Properties (12),
	Loan_Agreement_2nd_Mortgage_Line (13),
	Purchase_Contract_Including_Addendums (14),
	Settlement_HUD_1_Property_Recently_Sold (15),
	Proof_of_HOA_Dues (16),
	Paychecks_Most_Recent_30_Days (17),
	W2s_Previous_2_Years (18),
	Social_Security_Award_Letter (19),
	Previous_2_years_1099_1099_s_1099_Rs (20),
	Evidence_recent_receipt_1099_1099_s_1099_Rs_Income (21),
	Federal_Tax_Returns_Previous_2_Years (22),
	Tax_Return_Extension_Cancelled_Check_Extension_Payment (23),
	Federal_Corporation_Partnership_K_1s_all_partnerships (24),
	Year_to_date_Profit_Loss_Business (25),
	Year_to_date_Balance_Sheet_Business (26),
	Copies_of_Notes_Held (27),
	Months2_Statements_Bank_Accounts (28),
	Quarterly_Statement_Retirement_Investment_Accounts (29),
	Copy_of_Trust (30),
	Large_Deposits_Source_Documentation (31),
	Rental_Lease_Agreements (32),
	Driver_License_Passport (33),
	Gift_Letter_from_Donor (34),
	Current_Business_License (35),
	Copy_of_Relocation_Agreement (36)
    ;


    private final int indx;

    MasterNeedsEnum(int levelCode) {
        this.indx = levelCode;
    }
    
    public String getIndx() {
    	return this.indx+"";
    }
    
}
