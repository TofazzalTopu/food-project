package com.bits.bdfp.setup.salestarget

import com.bits.bdfp.util.ApplicationConstants
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class DailySalesTargetFinishProductService extends Service {
    static transactional = false
    DataSource dataSource
    SpringSecurityService  springSecurityService

    @Transactional(readOnly = true)
    public DailySalesTargetFinishProduct read(Long id) {
        return DailySalesTargetFinishProduct.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList = DailySalesTargetFinishProduct.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long dailySalesTargetFinishProductCount = DailySalesTargetFinishProduct.count()
            return [objList: dailySalesTargetFinishProductList, count: dailySalesTargetFinishProductCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<DailySalesTargetFinishProduct> list() {
        return DailySalesTargetFinishProduct.list()
    }

    @Transactional
    public DailySalesTargetFinishProduct create(Object object) {
        try {
            DailySalesTargetFinishProduct dailySalesTargetFinishProduct = (DailySalesTargetFinishProduct) object
            return dailySalesTargetFinishProduct.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            DailySalesTargetFinishProduct dailySalesTargetFinishProduct = (DailySalesTargetFinishProduct) object
            if (dailySalesTargetFinishProduct.save(vaidate: false)) {
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
            DailySalesTargetFinishProduct dailySalesTargetFinishProduct = (DailySalesTargetFinishProduct) object
            dailySalesTargetFinishProduct.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public DailySalesTargetFinishProduct search(String fieldName, String fieldValue) {
        String query = "from DailySalesTargetFinishProduct as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return DailySalesTargetFinishProduct.find(query)
    }

    @Transactional(readOnly = true)
    public List listProductForDailySalesTarget(Object params ) {
        Sql sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """AND (finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%')
                """
        }
        if(params.enterpriseId){
            query += " AND `finish_product`.`enterprise_configuration_id`  = ${params.enterpriseId}"
        }
        String  strSql = """
            SELECT `finish_product`.`id`, `finish_product`.`code`, `finish_product`.`name`, `monthly_sales_target_finish_product`.`quantity`, `enterprise_configuration`.`name` AS `enterprise`, `finish_product`.`pack_size` AS `packSize`,
                `product_category`.`name` AS `category`, `product_type`.`name` AS `type`, `measure_unit_configuration`.`name` AS `measurementUnit`
            FROM `monthly_sales_target_finish_product`
                INNER JOIN `finish_product`
                    ON (`monthly_sales_target_finish_product`.`finish_product_id` = `finish_product`.`id`)
                INNER JOIN `monthly_sales_target_by_volume`
                    ON (`monthly_sales_target_finish_product`.`monthly_sales_target_by_volume_id` = `monthly_sales_target_by_volume`.`id`)
                INNER JOIN `product_category` ON (`product_category`.id = `finish_product`.`product_category_id`)
                INNER JOIN `product_type` ON (`product_type`.`id` = `finish_product`.`product_type_id`)
                INNER JOIN `measure_unit_configuration` ON (`measure_unit_configuration`.`id` = `finish_product`.`measure_unit_configuration_id`)
                INNER JOIN `enterprise_configuration` ON (`finish_product`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE `monthly_sales_target_by_volume`.`yearly_sales_target_by_volume_id` = :yearlySalesTargetByVolumeId AND `monthly_sales_target_by_volume`.`target_month` = :targetMonth
                AND `monthly_sales_target_by_volume`.`employee_id` = :employeeId
               ${query}
        """

        List resultList = sql.rows(strSql, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listSubordinateForDailyTarget(Object params ) {
        Sql sql = new Sql(dataSource)
        String  strSql = """
            SELECT
                `customer_master`.`id`
                , CONCAT ('[', `customer_master`.`code`, '] ', `customer_master`.`name`, ' (' , `designation`.`name`, ')') AS `customer`
            FROM `yearly_sales_target_by_volume`
                INNER JOIN `customer_master`
                    ON (`yearly_sales_target_by_volume`.`employee_id` = `customer_master`.`id`)
                INNER JOIN `designation`
                    ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `yearly_sales_target_by_volume`.`yearly_sales_target_by_volume_id` = :yearlySalesTargetByVolumeId
        """

        List resultList = sql.rows(strSql, params)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listDailyTarget(Object params ) {
        Sql sql = new Sql(dataSource)
        String  strSql = """
            SELECT
                `daily_sales_target_finish_product`.`id`
                , DATE_FORMAT(`daily_sales_target_by_volume`.`target_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `targetDate`
                , `daily_sales_target_finish_product`.`quantity`
            FROM `daily_sales_target_finish_product`
                INNER JOIN `daily_sales_target_by_volume`
                    ON (`daily_sales_target_finish_product`.`daily_sales_target_by_volume_id` = `daily_sales_target_by_volume`.`id`)
            WHERE `daily_sales_target_by_volume`.`yearly_sales_target_by_volume_id` = :yearlySalesTargetByVolumeId
                AND MONTH(`daily_sales_target_by_volume`.`target_date`) = :targetMonth
                AND `daily_sales_target_by_volume`.`employee_id` = :employeeId
                AND `daily_sales_target_finish_product`.`finish_product_id` = :productId
        """

        List resultList = sql.rows(strSql, params)
        return resultList
    }

    @Transactional
    public boolean updateMonthlyTarget(List<DailySalesTargetFinishProduct> dailySalesTargetFinishProductList) {
        try {
            dailySalesTargetFinishProductList.each { dailySalesTargetFinishProduct ->
                 dailySalesTargetFinishProduct.save()
            }
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
