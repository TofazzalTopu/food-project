package com.bits.bdfp.reports

import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.geolocation.territorysubarea.ListDivisionByApplicationUserAction
import com.bits.bdfp.geolocation.territorysubarea.ListRegionByApplicationUserAction
import com.bits.bdfp.geolocation.territorysubarea.ListZoneByApplicationUserAction
import com.bits.bdfp.inventory.sales.distributionpoint.ListDistributionPointByApplicationUser
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.CommonConstants
import com.docu.commons.DateUtil
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.plugins.jasper.JasperExportFormat
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.springframework.beans.factory.annotation.Autowired

class SaleReportController {
    @Autowired
    ListDistributionPointByApplicationUser listDistributionPointByApplicationUser
    @Autowired
    ListZoneByApplicationUserAction listZoneByApplicationUserAction
    @Autowired
    ListRegionByApplicationUserAction listRegionByApplicationUserAction
    @Autowired
    ListDivisionByApplicationUserAction listDivisionByApplicationUserAction

    def jasperService

    def showBranchSalesView = {
        List distributionPointList = (List)listDistributionPointByApplicationUser.execute(params, null)
        List<CustomerCategory> listCustomerCategory = CustomerCategory.list()

        List<Map> customerCategoryList = new ArrayList<Map>()
        listCustomerCategory.each {
            if(it.id == ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID || it.id == ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID){
                Map customerCategory = [:]
                customerCategory.id = it.id
                customerCategory.name = it.name
                customerCategoryList.add(customerCategory)
            }
        }

        render (view: "/reportList/saleReport/branchSalesReport", model: [distributionPointList: distributionPointList, customerCategoryList: customerCategoryList])
    }

    def rptBranchSaleReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
        params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._name = "BranchSalesReport_" + DateUtil.now()
        params._file = "salesReport/rptBranchSale.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        params.put('username',applicationUser.username)

        params.dateRange = "From:" + params.fromDate + " To:" + params.toDate

        Date startDate = DateUtil.getSimpleDate(params.fromDate)
        params.fromDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

        Date endDate = DateUtil.getSimpleDate(params.toDate);
        params.toDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render(ex.message)
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showZonalSalesView = {
        List zoneList = (List)listZoneByApplicationUserAction.execute(params, null)
        List<CustomerCategory> listCustomerCategory = CustomerCategory.list()

        List<Map> customerCategoryList = new ArrayList<Map>()
        listCustomerCategory.each {
            if(it.id != ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID
                    && it.id != ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID){
                Map customerCategory = [:]
                customerCategory.id = it.id
                customerCategory.name = it.name
                customerCategoryList.add(customerCategory)
            }
        }

        render (view: "/reportList/saleReport/zonalSalesReport", model: [zoneList: zoneList, customerCategoryList: customerCategoryList])
    }

    def rptZonalSaleReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
        params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._name = "ZonalSalesReport_" + DateUtil.now()
        params._file = "salesReport/rptZonalSale.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        params.put('username',applicationUser.username)

        params.dateRange = "From:" + params.fromDate + " To:" + params.toDate

        Date startDate = DateUtil.getSimpleDate(params.fromDate)
        params.fromDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

        Date endDate = DateUtil.getSimpleDate(params.toDate);
        params.toDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render(ex.message)
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showRegionalSalesView = {
        List regionList = (List)listRegionByApplicationUserAction.execute(params, null)
        List<CustomerCategory> listCustomerCategory = CustomerCategory.list()

        List<Map> customerCategoryList = new ArrayList<Map>()
        listCustomerCategory.each {
            if(it.id != ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID
                    && it.id != ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID){
                Map customerCategory = [:]
                customerCategory.id = it.id
                customerCategory.name = it.name
                customerCategoryList.add(customerCategory)
            }
        }

        render (view: "/reportList/saleReport/regionalSalesReport", model: [regionList: regionList, customerCategoryList: customerCategoryList])
    }

    def rptRegionalSaleReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
        params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._name = "RegionalSalesReport_" + DateUtil.now()
        params._file = "salesReport/rptRegionalSale.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        params.put('username',applicationUser.username)

        params.dateRange = "From:" + params.fromDate + " To:" + params.toDate

        Date startDate = DateUtil.getSimpleDate(params.fromDate)
        params.fromDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

        Date endDate = DateUtil.getSimpleDate(params.toDate);
        params.toDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render(ex.message)
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }

    def showDivisionalSalesView = {
        List divisionList = (List)listDivisionByApplicationUserAction.execute(params, null)
        List<CustomerCategory> listCustomerCategory = CustomerCategory.list()

        List<Map> customerCategoryList = new ArrayList<Map>()
        listCustomerCategory.each {
            if(it.id != ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID
                    && it.id != ApplicationConstants.CUSTOMER_CATEGORY_RETAIL_ID){
                Map customerCategory = [:]
                customerCategory.id = it.id
                customerCategory.name = it.name
                customerCategoryList.add(customerCategory)
            }
        }

        render (view: "/reportList/saleReport/divisionalSalesReport", model: [divisionList: divisionList, customerCategoryList: customerCategoryList])
    }

    def rptDivisionalSaleReport = {
        params.SUBREPORT_DIR = servletContext.getRealPath("/") + "reports/salesReport/"
        params.COMMON_DIR = servletContext.getRealPath("/") + "reports/template/"
        params._name = "DivisionalSalesReport_" + DateUtil.now()
        params._file = "salesReport/rptDivisionalSale.jasper"
        ApplicationUser applicationUser=session?.applicationUser
        params.put('username',applicationUser.username)

        params.dateRange = "From:" + params.fromDate + " To:" + params.toDate

        Date startDate = DateUtil.getSimpleDate(params.fromDate)
        params.fromDate = DateUtil.getFlexibleDateFormatAsString(startDate, CommonConstants.DATE_FORMAT_Y_M_D);

        Date endDate = DateUtil.getSimpleDate(params.toDate);
        params.toDate = DateUtil.getFlexibleDateFormatAsString(endDate, CommonConstants.DATE_FORMAT_Y_M_D);

        def reportDef = new JasperReportDef(name: params._file,
                parameters: params,
                fileFormat: JasperExportFormat.PDF_FORMAT)

        ByteArrayOutputStream byteArrayOutputStream = null
        try {
            byteArrayOutputStream = jasperService.generateReport(reportDef)
        } catch (Exception ex) {
            render(ex.message)
            return
        }
        OutputStream os = response.outputStream
        response.contentLength = byteArrayOutputStream.size()
        response.contentType = "application/pdf"   // msword; pdf; richtext; html
        response.setHeader("Content-Disposition", "inline;filename=" + params._name + ".pdf");  // inline; attachment
        os << byteArrayOutputStream.toByteArray()

        os.flush()
        os.close()
    }
}
