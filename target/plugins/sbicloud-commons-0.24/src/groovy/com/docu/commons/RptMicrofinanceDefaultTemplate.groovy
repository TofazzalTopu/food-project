package com.docu.commons

/**
 * Created by rafiqul.islam on 3/4/2015.
 */
class RptMicrofinanceDefaultTemplate {
    long countryId
    long programId
    String projectId
    String officeId
    String coId
    String groupInfoId
    String memberInfoId
    String loanAccountId
    long loanStatusId
    String fromDate
    String toDate
    String asOnDate
    String PARReportType    // Used for PAR & Ageing Of Principal Outstanding
    String projectAssociationType
    Double currencyExchangeRate
    String loginUserId

    public RptMicrofinanceDefaultTemplate(Map params){
        if(params.countryId != null && params.countryId != "null" && params.countryId != ""){
            this.countryId = Long.parseLong(params.countryId)
        }
        else{
            this.countryId = 0
        }

        if(params.programId != null && params.programId != "null" && params.programId != ""){
            this.programId = Long.parseLong(params.programId)
        }
        else{
            this.programId = 0
        }

        if(params.loanStatusId != null && params.loanStatusId != "null" && params.loanStatusId != ""){
            this.loanStatusId = Long.parseLong(params.loanStatusId)
        }
        else{
            this.loanStatusId = 0
        }

        if(params.currencyExchangeRate != null && params.currencyExchangeRate != "null" && params.currencyExchangeRate != ""){
            this.currencyExchangeRate = Double.valueOf(params.currencyExchangeRate)
        }
        else{
            this.currencyExchangeRate = 1
        }

        this.projectId = params.projectId
        this.officeId = params.officeId
        this.coId = params.coId
        this.groupInfoId = params.groupInfoId
        this.memberInfoId = params.memberInfoId
        this.loanAccountId = params.loanAccountId
        this.fromDate = params.fromDate
        this.toDate = params.toDate
        this.asOnDate = params.asOnDate
        this.PARReportType = params.PARReportType   // Us
        this.projectAssociationType = params.projectAssociationType
    }
}
