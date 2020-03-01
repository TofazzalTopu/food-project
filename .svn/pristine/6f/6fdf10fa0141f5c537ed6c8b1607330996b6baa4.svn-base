package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.util.ApplicationConstants
import com.docu.commons.DateUtil
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import com.docu.common.Action

import javax.sql.DataSource

class SalesHeadByVolumeService extends Service {
    static transactional = false
    DataSource dataSource

    @Transactional(readOnly = true)
    public SalesHeadByVolume read(Long id) {
        return SalesHeadByVolume.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<SalesHeadByVolume> salesHeadByVolumeList = SalesHeadByVolume.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long salesHeadByVolumeCount = SalesHeadByVolume.count()
            return [objList: salesHeadByVolumeList, count: salesHeadByVolumeCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<SalesHeadByVolume> list() {
        return SalesHeadByVolume.list()
    }

    @Transactional
    public SalesHeadByVolume create(Object object) {
        try {
            SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) object
            return salesHeadByVolume.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesHeadByVolume createSalesHead(Map data) {
        try {
            SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) data.get("salesHeadByVolume")
            salesHeadByVolume.save(vaidate: false, insert: true)

            List<SalesHeadFinishProduct> salesHeadFinishProductList = (List<SalesHeadFinishProduct>) data.get("salesHeadFinishProductList")
            salesHeadFinishProductList.each { salesHeadFinishProduct->
                salesHeadFinishProduct.save(vaidate: false, insert: true)
            }
            return salesHeadByVolume
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) object
            if (salesHeadByVolume.save(vaidate: false)) {
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        try {
            SalesHeadByVolume salesHeadByVolume = (SalesHeadByVolume) object
            salesHeadByVolume.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public SalesHeadByVolume search(String fieldName, String fieldValue) {
        String query = "from SalesHeadByVolume as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return SalesHeadByVolume.find(query)
    }

    @Transactional(readOnly = true)
    public Map listFinishProductGrid(Object params) {
        try{
            Sql sql = new Sql(dataSource)
            String strSql = """
                SELECT
                    `finish_product`.`id`
                    , `finish_product`.`code` AS `productCode`
                    , `finish_product`.`name` AS `productName`
                    , `sales_head_finish_product`.`id` AS `salesHeadFinishProductId`
                    , `sales_head_finish_product`.`quantity`
                FROM
                    `sales_head_finish_product`
                    INNER JOIN `finish_product`
                        ON (`sales_head_finish_product`.`finish_product_id` = `finish_product`.`id`)
                WHERE `sales_head_finish_product`.`sales_head_by_volume_id` = :salesHeadByVolumeId
                ORDER BY `sales_head_finish_product`.`id`
            """
            List objList = sql.rows(strSql, params)
            int resultCount = objList.size()
            return [objList: objList, count: resultCount]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

//    @Transactional(readOnly = true)
//    public Float previousMonthTarget(int year, long employeeId, longProductId) {
//        Float previousTarget = 0
//        Sql sql = new Sql(dataSource)
//        String query = """
//            SELECT SUM(`quantity`) AS `quantity`
//            FROM
//                `monthly_sales_target_by_volume`
//            WHERE `target_year` = ${year}
//                AND `target_month` <= MONTH(NOW())
//                AND `employee_id` = ${employeeId}
//            GROUP BY `target_year`, `target_month`
//        """
//        List result = sql.rows(query)
//        if(result && result.size() > 0){
//            previousTarget = result.first().target_amount
//        }
//        return previousTarget
//    }

    @Transactional
    public SalesHeadByVolume replaceSalesHeadByVolume(SalesHeadByVolume currentSalesHeadByVolume, SalesHeadByVolume newSalesHeadByVolume, List<SalesHeadFinishProduct> salesHeadFinishProductList) {
        try {
            int currentYear  = DateUtil.getCurrentSystemYear()
            Date currentDate = new Date()
            if(currentYear == currentSalesHeadByVolume.targetYear){
                currentSalesHeadByVolume.endDate = currentDate
            }
            currentSalesHeadByVolume.isActive = false
            currentSalesHeadByVolume.save()

            if(currentYear == newSalesHeadByVolume.targetYear){
                newSalesHeadByVolume.startDate = currentDate
            }else {
                newSalesHeadByVolume.startDate = DateUtil.getSimpleDateWithFormat('01-01-' + newSalesHeadByVolume.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            }
            newSalesHeadByVolume.isActive = true
            newSalesHeadByVolume.endDate = DateUtil.getSimpleDateWithFormat('31-12-' + newSalesHeadByVolume.targetYear.toString(), ApplicationConstants.DATE_FORMAT)
            newSalesHeadByVolume.save(validate: false, insert: true)

            salesHeadFinishProductList.each { salesHeadFinishProduct->
                salesHeadFinishProduct.save(validate: false, insert: true)
            }

            // Replace Supper visor
            List<CustomerMaster> employeeList = CustomerMaster.findAllByCustomerMaster(currentSalesHeadByVolume.employee)
            employeeList.each { employee->
                employee.customerMaster = newSalesHeadByVolume.employee
                employee.save(validate: false)
            }
            return newSalesHeadByVolume
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public SalesHeadByVolume updateSalesHeadByVolume(SalesHeadByVolume salesHeadByVolume, List<SalesHeadFinishProduct> salesHeadFinishProductList) {
        try {
            salesHeadByVolume.save(vaidate: false)
            salesHeadFinishProductList.each { salesHeadFinishProduct->
                salesHeadFinishProduct.save()
            }
            return salesHeadByVolume
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
