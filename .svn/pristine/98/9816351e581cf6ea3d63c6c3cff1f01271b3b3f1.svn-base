package com.docu.commons

/**
 * Created by rafiqul.islam on 3/4/2015.
 */
class RptAccountsDefaultTemplate {
    long countryId
    long officeTypeId
    String costCenterId
    String officeCode
    String officeId
    String voucherType
    String voucherInfoId
    String hidNoteType
    String chartOfAcId

    String transactionDateFromOpeningBalance    // Used for Account Head wise Ledger
    String transactionDateToOpeningBalance     // Used for Account Head wise Ledger
    String transactionDateFrom  // Used for Account Head wise Ledger
    String transactionDateTo    // Used for Account Head wise Ledger
    String currentStartDate    // Used for Balance Sheet
    String currentEndDate    // Used for Balance Sheet
    String previousStartDate    // Used for Balance Sheet
    String previousEndDate    // Used for Balance Sheet
    String fromDate    // Used for Receipts and Payment
    String toDate    // Used for Receipts and Payment
    String asOnDate
    Double currencyExchangeRate

    String reportType    // Used for Chart of Accounts and Master Chart of Accounts
    String loginUserId

    public RptAccountsDefaultTemplate(Map params){
        if(params.countryId != null && params.countryId != "null" && params.countryId != ""){
            this.countryId = Long.parseLong(params.countryId)
        }
        else{
            this.countryId = 0
        }

        if(params.officeTypeId != null && params.officeTypeId != "null" && params.officeTypeId != ""){
            this.officeTypeId = Long.parseLong(params.officeTypeId)
        }
        else{
            this.officeTypeId = 0
        }

        if(params.currencyExchangeRate != null && params.currencyExchangeRate != "null" && params.currencyExchangeRate != ""){
            this.currencyExchangeRate = Double.valueOf(params.currencyExchangeRate)
        }
        else{
            this.currencyExchangeRate = 1
        }

        this.costCenterId = params.costCenterId
        this.officeId = params.officeId
        this.officeCode = params.officeCode
        this.voucherType = params.voucherType
        this.voucherInfoId = params.voucherInfoId
        this.hidNoteType = params.hidNoteType
        this.chartOfAcId = params.chartOfAcId

        this.transactionDateFromOpeningBalance = params.transactionDateFromOpeningBalance
        this.transactionDateToOpeningBalance = params.transactionDateToOpeningBalance
        this.transactionDateFrom = params.transactionDateFrom
        this.transactionDateTo = params.transactionDateTo
        this.currentStartDate = params.currentStartDate
        this.currentEndDate = params.currentEndDate
        this.previousStartDate = params.previousStartDate
        this.previousEndDate = params.previousEndDate

        this.fromDate = params.fromDate
        this.toDate = params.toDate
        this.asOnDate = params.asOnDate
        this.reportType = params.reportType
    }
}
