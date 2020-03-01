package com.bits.bdfp.reports

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.product.FinishProduct
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.customer.CustomerCategory

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * Created by sarahafreen.orny on 4/25/2016.
 */
@Component("searchReportInfoAction")
class SearchReportInfoAction {

        private static final Log log = LogFactory.getLog(this)
        @Autowired
        SearchReportInfoService searchReportInfoService

        public Object preCondition(Object user, Object object) {

                return null
        }

    public List<TerritoryConfiguration>  detailTerritoryList() {

        try {

            return searchReportInfoService.fetchGeolocationList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public  Object distributionPointList(Integer userId){
        try{

        }
        catch (Exception ex){
            throw new RuntimeException(ex.message)

        }
    }

    public List<CustomerMaster>  salesOfficerList(Long userId) {

        try {
            return searchReportInfoService.salesOfficerList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public List<CustomerCategory>  detailCustomerCatagoryList() {

        try {

            return searchReportInfoService.fetchCustomerCategoryList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public List<CustomerCategory>  detailCustomerCatagorywithoutSalesmanList() {

        try {

            return searchReportInfoService.fetchCustomerCatagoryListwithoutSalesman()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public List<Object>  chartOfAccountList() {

        try {

            return searchReportInfoService.chartOfAccounts()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public List<Object>  dpList() {

        try {

            return searchReportInfoService.dpList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
    public List<Object>  customerList() {
        try {

            return searchReportInfoService.customerList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public  List<Object> fetchSalesmanListAll(){
        try {
            return searchReportInfoService.fetchSalesmanListAll()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    public  List getSalesmanListByDp(Long dpId){
        try {
            return searchReportInfoService.getSalesmanListByDp(dpId)
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }
    public List<CustomerCategory>  detailSalesChannelList() {

        try {

            return searchReportInfoService.fetchCustomerSalesChannelList()
        }catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

        public Object execute(Object params) {
            try {
                List list
                if (params.id == 'nonfactory') {
                    list = searchReportInfoService.fetchSalesmanList()
                }
                else if (params.id == 'factory') {
                    list = searchReportInfoService.fetchNonSalesmanList()
                }
                else if (params.id == 'saleschannel') {
                    list = searchReportInfoService.fetchSalesChannelList()
                }
                else if (params.id == 'division') {
                    list = searchReportInfoService.fetchDivisionList()
                }
                else if (params.id == 'district') {
                    list = searchReportInfoService.fetchDistrictList()
                }
                else if (params.id == 'thana') {
                    list = searchReportInfoService.fetchThanaList()
                }

                return list

            } catch (Exception ex) {
                log.error(ex.message)
                return null
            }
        }

    public Object executeReport(Object object, Object data) {
        try {
            return searchReportInfoService.getStockOutReport(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    public Object getSummaryDailyDeliveryReport(Object object, Object data) {
        try {
            return searchReportInfoService.getSummaryDailyDeliveryReport(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    public Object getSummaryDailyDemandOrder(Object object, Object data) {
        try {
            return searchReportInfoService.getSummaryDailyDemandOrder(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    public Object getSaleStockStatement(Object object, Object data) {
        try {
            return searchReportInfoService.getSaleStockStatement(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }
    public Object executeUhtSaleStatementDetails(Object object, Object data) {
        try {
            return searchReportInfoService.getUhtSaleStockStatementDetails(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    public Object executeBranchStockReport(Object object, Object data) {
        try {
            return searchReportInfoService.getBranchStockReport(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }

    @Override
    public Map checkConditionForSummaryDailyDeliver(Object params, Object user) {
        try {
            params.put("_format", "PDF")
            params.put('fromDate', params.ledgerDateFrom)
            params.put('toDate', params.ledgerDateTo)
            params.put("_file", "deliveryReport/summary_daily_delivery_report.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }


    @Override
    public Map checkConditionDailyDemandOrder(Object params, Object user) {
        try {
            params.put("_format", "PDF")
            params.put("_file", "DailyDemandOrder/DailyDemandOrder.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    @Override
    public Map checkConditionForUHTStatement(Object params, Object user) {
        try {
            params.put("_format", "PDF")
            params.put("_file", "UHTSale/UHTSale&StockStatement.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    @Override
    public Map checkConditionForUHTStatementDetails(Object params, Object user) {
        try {
            params.put("_format", "PDF")
            params.put("_file", "UHTSale/uhtSaleStockStatementDetails.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    @Override
    public Map checkCondition1(Object params, Object user) {
        try {
            params.put("_format", "PDF")
            params.put("_file", "stockOutReport/BranchStockReport.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    protected Object executeDistributionPoint() {
        try {
            List list

            list = searchReportInfoService.fetchDistributionPointList()


            return list
        }catch(Exception ex){
            log.error(ex.message)
            return null
        }
    }


    }

