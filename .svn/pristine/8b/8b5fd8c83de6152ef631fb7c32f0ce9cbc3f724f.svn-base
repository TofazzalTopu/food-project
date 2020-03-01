package com.bits.common

import com.bits.bdfp.common.CodeGenerationFormat
import com.bits.bdfp.common.CodeGenerationFormatService
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.docu.commons.DateUtil
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by ripon.rana on 6/25/2015.
 */
@Singleton
class CodeGenerationUtil {
    public static final Log log = LogFactory.getLog(CodeGenerationUtil.class)
    CodeGenerationFormatService codeGenerationFormatService = new CodeGenerationFormatService()
    CodeGenerationFormat codeGenerationFormat = null

    public String getCode(EnterpriseConfiguration enterpriseConfiguration, String keyCode, String enterPriseCode, String buCode, String pcCode, String mpCode, String mnCode, String ptCode, String mntCode, String yrCode, String productCode, String dtCode) {

        String code = ''
        try {
            CodeGenerationFormat codeGenerationFormat = codeGenerationFormatService.findByKeyAndEnterprise(keyCode, enterpriseConfiguration)
            if (codeGenerationFormat == null) {
                CodeGenerationFormat codeGenerationFormat1 = codeGenerationFormatService.findByKey(keyCode)
                if (codeGenerationFormat1) {
                    codeGenerationFormat=new CodeGenerationFormat()
                    codeGenerationFormat.keyCode = codeGenerationFormat1.keyCode
                    codeGenerationFormat.enterpriseConfiguration = enterpriseConfiguration
                    codeGenerationFormat.currentNo = 0
                    if (keyCode == "FINISH_PRODUCT_TRANSACTION_REF" || keyCode == "FINISH_PRODUCT_PRODUCT_REF") {
                        codeGenerationFormat.productYear = yrCode
                    }
                    codeGenerationFormat.format = codeGenerationFormat1.format
                    codeGenerationFormatService.save(codeGenerationFormat)
                }
            }
            if (codeGenerationFormat) {
                String[] temp = codeGenerationFormat.format.split("\\|")
                temp.each { val ->
                    if (val == 'ENPC') {
                        code += enterPriseCode
                    } else if (val == 'BUC') {
                        code += buCode
                    } else if (val == 'PCC') {
                        code += pcCode
                    } else if (val == 'MPC') {
                        code += mpCode
                    } else if (val == 'MNPC') {
                        code += mnCode
                    } else if (val == 'PTC') {
                        code += ptCode
                    } else if (val == 'YY') {
                        code += yrCode
                    } else if (val == 'MM') {
                        code += mntCode
                    } else if (val == 'DD') {
                        code += dtCode
                    } else if (val == 'PC') {
                        code += productCode
                    } else if (val == 'VHCL'){
                        code += enterPriseCode
                    } else if (val.contains('#')) {
                        int length = val.length()
                        int currentNo = codeGenerationFormat.currentNo.toInteger()
                        if ((keyCode == "FINISH_PRODUCT_TRANSACTION_REF" || keyCode == "FINISH_PRODUCT_PRODUCT_REF") &&(codeGenerationFormat.productYear!= yrCode)) {
                            currentNo = 0
                            codeGenerationFormat.productYear = yrCode
                        }
                        currentNo = currentNo + 1
                        for (int i = 0; i < length - currentNo.toString().length(); i++) {
                            code += '0'
                        }
                        code += currentNo.toString()
                        codeGenerationFormat.currentNo = currentNo
                        codeGenerationFormatService.save(codeGenerationFormat)
                    } else {
                        code += val
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.message)
            code = ""
        }

        return code
    }

    public String generateCode(EnterpriseConfiguration enterprise, String keyCode, String businessUnitCode, String productCategoryCode, String masterProductCode, String mainProductCode, String productTypeCode, String monthData, String yearData, String productCode, String dateData) {
        String enterPriseCode = enterprise.code
        String code = ''
        try {
            CodeGenerationFormat codeGenerationFormat = codeGenerationFormatService.findByKeyAndEnterprise(keyCode, enterprise)
            if (codeGenerationFormat == null) {
                CodeGenerationFormat codeGenerationFormat1 = codeGenerationFormatService.findByKey(keyCode)
                if (codeGenerationFormat1) {
                    codeGenerationFormat = new CodeGenerationFormat()
                    codeGenerationFormat.keyCode = codeGenerationFormat1.keyCode
                    codeGenerationFormat.enterpriseConfiguration = enterprise
                    codeGenerationFormat.prefix = codeGenerationFormat1.prefix
                    codeGenerationFormat.currentNo = 0
                    if (keyCode == "FINISH_PRODUCT_TRANSACTION_REF" || keyCode == "FINISH_PRODUCT_PRODUCT_REF") {
                        codeGenerationFormat.productYear = yearData
                    }
                    codeGenerationFormat.format = codeGenerationFormat1.format
                    codeGenerationFormatService.save(codeGenerationFormat)
                }
            }
            if (codeGenerationFormat) {
                String[] temp = codeGenerationFormat.format.split("\\|")
                if(codeGenerationFormat.prefix){
                    code += codeGenerationFormat.prefix
                }
                temp.each { val ->
                    if (val == 'ENPC') {
                        code += enterPriseCode
                    } else if (val == 'BUC') {
                        code += businessUnitCode
                    } else if (val == 'PCC') {
                        code += productCategoryCode
                    } else if (val == 'MPC') {
                        code += masterProductCode
                    } else if (val == 'MNPC') {
                        code += mainProductCode
                    } else if (val == 'PTC') {
                        code += productTypeCode
                    } else if (val == 'YY') {
                        code += yearData
                    } else if (val == 'MM') {
                        code += monthData
                    } else if (val == 'DD') {
                        code += dateData
                    } else if (val == 'PC') {
                        code += productCode
                    } else if (val == 'VHCL'){
                        code += enterPriseCode
                    } else if (val.contains('#')) {
                        int length = val.length()

                        int currentNo = codeGenerationFormat.currentNo.toInteger()
                        Date currentDate = new Date()
                        int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
                        int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
                        int savedDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
                        int newYear = DateUtil.getYear(currentDate)
                        int newMonth = DateUtil.getMonth(currentDate)
                        switch (keyCode){
                            case "FINISH_PRODUCT_TRANSACTION_REF":
                            case "FINISH_PRODUCT_PRODUCT_REF":
                                if(codeGenerationFormat.productYear!= yearData){
                                    currentNo = 0
                                    codeGenerationFormat.productYear = yearData
                                }
                                break
                            case "TENTATIVE_CUSTOMER":
                                // Reset Retail Order every day
                                if(savedYear != newYear){
                                    if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
                                        currentNo = 0
                                    }
                                    else {
                                        if(currentNo > sizeTable[length]){
                                            currentNo = 0
                                        }
                                    }
                                }
                                break
                            case "SECONDARY_DEMAND_ORDER":
                            case "PRIMARY_DEMAND_ORDER":
                            case "RETAIL_INVOICE":
                            case "SECONDARY_INVOICE":
                            case "PRIMARY_INVOICE":
                                if(savedYear != newYear || savedMonth != newMonth){
                                    if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
                                        currentNo = 0
                                    }
                                    else {
                                        if(currentNo > sizeTable[length]){
                                            currentNo = 0
                                        }
                                    }
                                }
                                break

                            case "RETAIL_ORDER":
                            case "JOURNAL":
                            case "LOADING_SLIP":
                            case "DEPOSIT_CASH_TO_DEPOSIT_POOL":
                            case "QUOTATION":
                                int newDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
                                if (savedYear != newYear || savedMonth != newMonth || savedDay != newDay) {
                                    if (ConfigurationHolder.config.application.codeGeneration.allowResetCode) {
                                        currentNo = 0
                                    } else {
                                        if (currentNo > sizeTable[length]) {
                                            currentNo = 0
                                        }
                                    }
                                }
                                break

                            default:
                                break
                        }
//                        if ((keyCode == "FINISH_PRODUCT_TRANSACTION_REF" || keyCode == "FINISH_PRODUCT_PRODUCT_REF") &&(codeGenerationFormat.productYear!= yearData)) {
//                            currentNo = 0
//                            codeGenerationFormat.productYear = yearData
//                        }
//                        if(codeGenerationFormat.dateUpdated && (keyCode == "SECONDARY_DEMAND_ORDER" || keyCode == "PRIMARY_DEMAND_ORDER")) {
//                            // Reset Secondary Order every month
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            int newMonth = DateUtil.getMonth(currentDate)
//                            if(savedYear != newYear || savedMonth != newMonth){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 9999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

//                        if(codeGenerationFormat.dateUpdated && (keyCode == "RETAIL_ORDER" || keyCode == "JOURNAL" || keyCode == "LOADING_SLIP" || keyCode == "DEPOSIT_CASH_TO_DEPOSIT_POOL")) {
//                            // Reset Retail Order every day
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
//                            int savedDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            int newMonth = DateUtil.getMonth(currentDate)
//                            int newDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
//                            if(savedYear != newYear || savedMonth != newMonth || savedDay != newDay){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 99999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

//                        if(codeGenerationFormat.dateUpdated && keyCode == "RETAIL_INVOICE") {
//                            // Reset Retail Invoice No every month
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            int newMonth = DateUtil.getMonth(currentDate)
//                            if(savedYear != newYear || savedMonth != newMonth){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 99999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

//                        if(codeGenerationFormat.dateUpdated && (keyCode == "SECONDARY_INVOICE" || keyCode == "PRIMARY_INVOICE")) {
//                            // Reset Retail Invoice No every month
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            int newMonth = DateUtil.getMonth(currentDate)
//                            if(savedYear != newYear || savedMonth != newMonth){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 9999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

//                        if(codeGenerationFormat.dateUpdated && keyCode == "QUOTATION") {
//                            // Reset Retail Order every day
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int savedMonth = DateUtil.getMonth(codeGenerationFormat.dateUpdated)
//                            int savedDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            int newMonth = DateUtil.getMonth(currentDate)
//                            int newDay = DateUtil.getDayOfMonth(codeGenerationFormat.dateUpdated)
//                            if(savedYear != newYear || savedMonth != newMonth || savedDay != newDay){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 9999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

//                        if(codeGenerationFormat.dateUpdated && keyCode == "TENTATIVE_CUSTOMER") {
//                            // Reset Retail Order every day
//                            Date currentDate = new Date()
//                            int savedYear = DateUtil.getYear(codeGenerationFormat.dateUpdated)
//                            int newYear = DateUtil.getYear(currentDate)
//                            if(savedYear != newYear){
//                                if(ConfigurationHolder.config.application.codeGeneration.allowResetCode){
//                                    currentNo = 0
//                                }
//                                else {
//                                    if(codeGenerationFormat.currentNo > 9999){
//                                        currentNo = 0
//                                    }
//                                }
//                            }
//                        }

                        currentNo = currentNo + 1
                        for (int i = 0; i < length - currentNo.toString().length(); i++) {
                            code += '0'
                        }
                        code += currentNo.toString()
                        codeGenerationFormat.currentNo = currentNo
                        codeGenerationFormat.dateUpdated = new Date()
                        codeGenerationFormatService.save(codeGenerationFormat)
                    } else {
                        code += val
                    }
                }
            }
            else {
                throw new RuntimeException(keyCode + "not found")
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

        return code
    }

    final static int[] sizeTable = [0, 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE]

    private static Random random = new Random();

    public static int generate(int digit)
    {
        int highest = sizeTable[digit] + 1;
        int lowest = sizeTable[digit - 1] + 1;
        String id;
        int generated = random.nextInt(highest);


        if (generated < lowest)
            generated = generate(digit);

        return generated;
    }
}
