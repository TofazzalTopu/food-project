package com.bits.bdfp.customer

import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Service
import com.docu.security.ApplicationUser
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

public class CustomerAssetLendingAndRecoveryService extends Service{
    static transactional = false
    DataSource dataSource
    Sql sql

    @Override
    Object create(Object object) {
        return null
    }

    @Override
    Integer update(Object object) {
        return null
    }

    @Override
    Integer delete(Object object) {
        return null
    }

    @Override
    Object read(Long id) {
        return null
    }

    @Override
    java.util.List list() {
        return null
    }


    @Transactional
    public boolean createAssetLending(Object object){
        try {
            AssetLending assetLending = object.get("assetLending")
            java.util.List<CustomerAssetLending> customerAssetLendingList = object.get("customerAssetLendingList")

            if(assetLending.id==null)
            {
                assetLending.save()
                customerAssetLendingList.each {
                    it.save()
                }
            }
            else
            {
                customerAssetLendingList.each {
                    it.save()
                }
            }
        return true
    }catch (Exception ex){
        log.error(ex.message)
        throw new RuntimeException(ex.message)
    }
}
    @Transactional
    public boolean createAssetRecovery(Object object){
        try {
            AssetLending assetLending = object.get("assetLending")
            java.util.List<CustomerAssetRecovery> customerAssetRecoveryList = object.get("customerAssetRecoveryArrayList")

            if(assetLending.id==null)
            {
                assetLending.save()
                customerAssetRecoveryList.each {
                    it.save()
                }
            }
            else
            {
                customerAssetRecoveryList.each {
                    it.save()
                }
            }
            return true
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public java.util.List list(Object params) {
        try
        {
            String strSql = ""
            sql = new Sql(dataSource)
            if (params.customerId) {
                strSql = """
                           SELECT
                            `customer_asset_lending`.id
                            ,`customer_asset_lending`.`asset_name`
                            ,DATE_FORMAT(`customer_asset_lending`.`lending_date`,'%d-%m-%Y') as lending_date
                            ,`customer_asset_lending`.`model_number`
                            ,`customer_asset_lending`.`asset_cost`

                             FROM
                            `asset_lending`
                            INNER JOIN `customer_asset_lending` ON `asset_lending`.`id`=`customer_asset_lending`.`asset_lending_id`

                            WHERE `asset_lending`.`customer_master_id`=${params.customerId}

                      """

                java.util.List list = sql.rows(strSql.toString())
                return list
            }

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List loadCustomerCategoryList (Object params){
        String strSql = ""
        sql = new Sql(dataSource)
        strSql="""
            SELECT DISTINCT  customer_category.id, customer_category.name
            FROM `customer_master`
                    INNER JOIN `customer_category` ON (customer_category.id = customer_master.category_id)
                    INNER JOIN `customer_territory_sub_area` ON (customer_territory_sub_area.customer_master_id = customer_master.id)
            WHERE customer_master.`customer_level` = 'PRIMARY'
                    AND `customer_territory_sub_area`.`territory_sub_area_id` IN (SELECT id FROM `territory_sub_area` WHERE  `territory_sub_area`.`territory_configuration_id` = ${params.territoryId})   """


        List  resultList=sql.rows(strSql.toString())
        return resultList




    }

//
//    @Transactional(readOnly = true)
//    public List list(Object params) {
//        sql = new Sql(dataSource)
//        String cond = ''
//        String strSql = ''
//        java.util.List objList
//        if (params.customerId) {
//            strSql = """
//                           SELECT
//                            `customer_asset_lending`.id
//                            ,`customer_asset_lending`.`asset_name`
//                            ,`customer_asset_lending`.`lending_date`
//                            ,`customer_asset_lending`.`model_number`
//                            ,`customer_asset_lending`.`asset_cost`
//
//                             FROM
//                            `asset_lending`
//                            INNER JOIN `customer_asset_lending` ON `asset_lending`.`id`=`customer_asset_lending`.`asset_lending_id`
//
//                            WHERE `asset_lending`.`customer_master_id`=${params.customerId}
//
//                      """
//            objList = sql.rows(strSql)
//        }
//    }


    @Transactional(readOnly = true)
    public java.util.List listRecovery(Object params) {
        try
        {
            sql = new Sql(dataSource)
            String  strSql = """
                                SELECT
                                `customer_asset_recovery`.id
                                ,`customer_asset_recovery`.`amount`
                                ,DATE_FORMAT(`customer_asset_recovery`.`recovery_date`,'%d-%m-%Y') as recovery_date
                                 FROM
                                `asset_lending`
                               INNER JOIN `customer_asset_recovery` ON `asset_lending`.`id`=`customer_asset_recovery`.`asset_lending_id`

                                WHERE `asset_lending`.`customer_master_id`=${params.customerId}"""

            java.util.List list = sql.rows(strSql.toString())
            return list

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public java.util.List listCustomerByCode(Object params) {
        try{
            sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " AND (`customer_master`.`name` LIKE '%${params.query}%' OR customer_master.code LIKE '%${params.query}%')"
            }
            String strSql = """

            SELECT DISTINCT
                 `customer_master`.`id`
                , `customer_master`.`code`
                , `customer_master`.`legacy_id`
                , `customer_master`.`name`
                , `customer_status` AS `status`
                , `customer_master`.`present_address` AS `address`
                , `customer_category`.`name` AS `category`
            FROM `customer_territory_sub_area`
                INNER JOIN `customer_master` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id` AND `customer_master`.`master_type_id` = 2)
                INNER JOIN `customer_category` ON (`customer_master`.`category_id` = `customer_category`.`id`)
                INNER JOIN `territory_sub_area` ON customer_territory_sub_area.`territory_sub_area_id`=territory_sub_area.`id`
                INNER JOIN `territory_configuration` ON territory_sub_area.`territory_configuration_id`=territory_configuration.`id`

            WHERE `customer_master`.`customer_status` = '${CustomerStatus.ACTIVE}'
                AND `customer_master`.`master_type_id` = ${ApplicationConstants.CUSTOMER_MASTER_TYPE_ID}
                AND territory_configuration.`id` =${params.territorySubAreaId}
              AND `customer_master`.`category_id` = ${params.customerCategory}
                AND `customer_master`.`customer_level`='primary'
        ${query}
        ORDER BY `customer_master`.`name`
            """

            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public Object getNetReceivable(Object params) {
        try
        {
            sql = new Sql(dataSource)
            String  strSql = """

                            SELECT
                            ROUND(IFNULL((SELECT
                            SUM(`customer_asset_lending`.`asset_cost`)
                                FROM `asset_lending`
                                INNER JOIN `customer_asset_lending` ON `asset_lending`.`id`=`customer_asset_lending`.`asset_lending_id`
                                WHERE `asset_lending`.`customer_master_id`=${params.customerId}),0)-
                                IFNULL((SELECT
                            SUM(`customer_asset_recovery`.`amount`)
                                FROM `asset_lending`
                                INNER JOIN `customer_asset_recovery` ON `asset_lending`.`id`=`customer_asset_recovery`.`asset_lending_id`
                                WHERE `asset_lending`.`customer_master_id`=${params.customerId}),0),2) AS netReceivable"""

            Object obj = sql.firstRow(strSql.toString())
            return obj

        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }



}
