package com.bits.bdfp.accounts

/**
 * Created by mdalinaser.khan on 4/25/16.
 */
public enum COAType {
    ONE_PERCENT_EXPENSE("1% Expense", "ONE_PERCENT_EXPENSE"),
    ONE_PERCENT_INCOME("1% Income", "ONE_PERCENT_INCOME"),
    ACCOUNTS_PAYABLE("Accounts Payable", "ACCOUNTS_PAYABLE"),
    ACCOUNTS_RECEIVABLE("Accounts Receivable", "ACCOUNTS_RECEIVABLE"),
    ACCOMMODATION_RENT("Accommodation/rent", "ACCOMMODATION_RENT"),
    ACTUAL_LEAK_OR_SHORT("Actual Leak/Short", "ACTUAL_LEAK_OR_SHORT"),
    ACTUAL_LEAK_SHORT_WASTAGE("Actual Leak/Short/Wastage","ACTUAL_LEAK_SHORT_WASTAGE"),
    ADVANCE_FROM_CUSTOMER("Advance from Customer", "ADVANCE_FROM_CUSTOMER"),
    BAD_DEPT_RESERVE("Bad Dept Reserve", "BAD_DEPT_RESERVE"),
    BANK("Bank", "BANK"),
    CASH("CASH", "CASH"),
    COMMISSION("Commission", "COMMISSION"),
    CURRENT_ACCOUNT_WITH_HO("Current Account With HO","CURRENT_ACCOUNT_WITH_HO"),
    DEPOSIT_TO_HO("Deposit to HO", "DEPOSIT_TO_HO"),
    DISCOUNT("Discount", "DISCOUNT"),
    FINISH_GOOD_STOCK("Finish Good Stock","FINISH_GOOD_STOCK"),
    GENERAL_EXPENSE("General Expense", "GENERAL_EXPENSE"),
    MARKETING_EXPENSE("Marketing Expense", "MARKETING_EXPENSE"),
    PURCHASE("Purchase", "PURCHASE"),
    RENT("Rent", "RENT"),
    SALES_DISCOUNT("Sales Discount","SALES_DISCOUNT"),
    SALE_OF_PASTEURIZED_MILK_100_ML("Sale of Pasteurized Milk 1000 ml","SALE_OF_PASTEURIZED_MILK_100_ML"),
    SALES_INCENTIVE("Sales Incentive", "SALES_INCENTIVE"),
    SALES_RETURN("Sales Return", "SALES_RETURN"),
    SECURITY_DEPOSIT("Security Deposit", "SECURITY_DEPOSIT"),
    SERVICE_CHARGE("Service Charge", "SERVICE_CHARGE"),
    VAT_CURRENT_ACCOUNT("VAT Current Account", "VAT_CURRENT_ACCOUNT"),
    VAT_EXPENSES("VAT Expenses","VAT_EXPENSES"),
    ADVERTISEMENT("Advertisement","ADVERTISEMENT")





    private String displayName;
    private String code

    COAType(String displayName, String code) {
        this.displayName = displayName;
        this.code = code
    }

    public String displayName() { return displayName; }
    public String code() { return code; }

    // Optionally and/or additionally, toString.
    @Override public String toString() { return code; }
}