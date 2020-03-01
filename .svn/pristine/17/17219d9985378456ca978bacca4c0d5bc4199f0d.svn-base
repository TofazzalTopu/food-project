package com.docu.app

import com.docu.commons.CommonConstants
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created with IntelliJ IDEA.
 * User: Rafiq
 * Date: 8/12/13
 * Time: 4:55 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
class ApplicationConfigUtil {
    ConfigObject configObject = ConfigurationHolder.config
    private List<ApplicationConfig> applicationConfigList = []

    void resolve() {
        applicationConfigList = ApplicationConfig.list()
    }


    boolean showLoginText() {
        return true;
    }
    String getCompanyName() {
            return "bITS"
    }

    // Login Logo
    String getLoginLogo() {
        return "../images/banner_img/login_logo.png"
    }

    // Fav Icon (With Country)
    String getFavIcon(long countryId) {
        ApplicationConfig config = applicationConfigList.find { it.countryId == countryId }
        if (config) {
            return config.favIconPath
        }
        return ""
    }

    // Application Banner (With Country)
    String getAppBanner(long countryId) {
        ApplicationConfig config = applicationConfigList.find { it.countryId == countryId }
        if (config) {
            return config.appBannerPath
        }
        return ""
    }

    List<ApplicationConfig> list() {
        return applicationConfigList
    }


    void destroy() {
        applicationConfigList = null
    }










    /* BEGIN :: Report Related Combo Box Values [Will be Placed in a Different Class]*/

    public List reportFormatList() {
        List formatList = []
        Map printFormatName = ['PDF': 'PDF']
        //Please give here your option list where key is optionValue and value is optionText
        printFormatName.each { key, val ->
            formatList.add(optionValue: key, optionText: val)
        }
        return formatList
    }

    public List getPARCategoryReportList() {
        List parCategoryReportList = []
        Map printPARCategoryReportName = ['1': 'Installment Wise', '2': 'Day Wise']
        //Please give here your option list where key is optionValue and value is optionText
        printPARCategoryReportName.each { key, val ->
            parCategoryReportList.add(optionValue: key, optionText: val)
        }
        return parCategoryReportList
    }

    public List getCollectionSheetTypeList() {
        List collectionSheetCategoryReportList = []
        Map printCollectionSheetCategoryReportName = ['1': 'General Collection Sheet', '2': 'Bad Loan Collection Sheet']
        //Please give here your option list where key is optionValue and value is optionText
        printCollectionSheetCategoryReportName.each { key, val ->
            collectionSheetCategoryReportList.add(optionValue: key, optionText: val)
        }
        return collectionSheetCategoryReportList
    }

    public List getPortfolioSummaryReportList() {
        List portfolioSummaryReportList = []
        Map printPortfolioSummaryReportName = ['1': 'Project Summary', '2': 'Basic Principal Outstanding', '3': 'Security Deposit Information']
        //Please give here your option list where key is optionValue and value is optionText
        printPortfolioSummaryReportName.each { key, val ->
            portfolioSummaryReportList.add(optionValue: key, optionText: val)
        }
        return portfolioSummaryReportList
    }

    public List getTransferredReceivedGroupReportList() {
        List transferredReceivedGroupReportList = []
        Map printTransferredReceivedGroupReportName = ['1': 'Transferred Group List', '2': 'Received Group List']
        //Please give here your option list where key is optionValue and value is optionText
        printTransferredReceivedGroupReportName.each { key, val ->
            transferredReceivedGroupReportList.add(optionValue: key, optionText: val)
        }
        return transferredReceivedGroupReportList
    }

    public List getTransferredReceivedMemberReportList() {
        List transferredReceivedMemberReportList = []
        Map printTransferredReceivedMemberReportName = ['1': 'Transferred Member List', '2': 'Received Member List']
        //Please give here your option list where key is optionValue and value is optionText
        printTransferredReceivedMemberReportName.each { key, val ->
            transferredReceivedMemberReportList.add(optionValue: key, optionText: val)
        }
        return transferredReceivedMemberReportList
    }


    public String getBaseUrl() {
//        println "IP " + configObject.baseIPAddress
        return "http://" + configObject.baseIPAddress
    }
    /* END :: Report Related Combo Box Values */

    private long generateRandomInteger(){
        int aStart = 1
        int aEnd = 6
        Random aRandom = new Random()
        //get the range, casting to long to avoid overflow problems
        long range = (long)aEnd - (long)aStart + 1
        // compute a fraction of the range, 0 <= frac < range
        long fraction = (long)(range * aRandom.nextDouble())
        int randomNumber =  (int)(fraction + aStart)
        return randomNumber
    }
}
