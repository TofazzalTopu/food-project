package com.bits.bdfp.inventory.product

import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class FinishProductService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql


    @Transactional
    public FinishProduct create(Object object) {
        FinishProduct finishProduct = (FinishProduct) object
        if (finishProduct.save(false)) {
            return finishProduct
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        FinishProduct finishProduct = (FinishProduct) object
        if (finishProduct.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        FinishProduct finishProduct = (FinishProduct) object
        finishProduct.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        if (params.query) {
            searchCondition = """ WHERE ( lower(finish_product.name) like lower('%${params?.query}%')
                OR lower(finish_product.code) like lower('%${params?.query}%')
                OR lower(enterprise_configuration.name) like lower('%${params?.query}%')
                OR lower(finish_product.name) like lower('%${params?.query}%')
                OR lower(business_unit_configuration.name) like lower('%${params?.query}%')
                OR lower(product_category.name) like lower('%${params?.query}%')
                OR lower(main_product.name) like lower('%${params?.query}%')
                OR lower(master_product.name) like lower('%${params?.query}%')
                OR lower(product_type.name) like lower('%${params?.query}%')
            )
        """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT
                finish_product.id,finish_product.version,
                CONCAT(enterprise_configuration.name,'[',enterprise_configuration.code,']') AS en_name,
                enterprise_configuration.name,CONCAT(business_unit_configuration.name,'[',business_unit_configuration.code,']') AS bu_name,
                CONCAT(product_category.name,'[',product_category.code,']') AS pc_name,
                CONCAT(master_product.name,'[',master_product.code,']') AS master_name,
                CONCAT(main_product.name,'[',main_product.code,']') AS main_name,
                product_type.name AS product_type_name,
                finish_product.code,finish_product.old_code,finish_product.name,
                finish_product.description,finish_product.pack_size,measure_unit_configuration.name AS measure_name,
                finish_product.length,finish_product.width,finish_product.color,finish_product.density,
                finish_product.texture,finish_product.other, finish_product.sequence_number, finish_product.is_active
            FROM
                finish_product
                INNER JOIN enterprise_configuration
                    ON enterprise_configuration.id = finish_product.enterprise_configuration_id
                INNER JOIN business_unit_configuration
                    ON business_unit_configuration.id = finish_product.business_unit_configuration_id
                INNER JOIN product_category
                    ON product_category.id = finish_product.product_category_id
                INNER JOIN main_product
                    ON main_product.id = finish_product.main_product_id
                INNER JOIN master_product
                    ON master_product.id = finish_product.master_product_id
                INNER JOIN product_type
                    ON product_type.id = finish_product.product_type_id
                INNER JOIN measure_unit_configuration
                    ON measure_unit_configuration.id = finish_product.measure_unit_configuration_id
            ${searchCondition}
            ORDER BY master_product.`sequence_number`, main_product.`sequence_number`, `finish_product`.`sequence_number`
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)

        strSql = """
            SELECT COUNT(finish_product.id) as  total_rows
            FROM finish_product
                INNER JOIN enterprise_configuration
                    ON enterprise_configuration.id = finish_product.enterprise_configuration_id
                INNER JOIN business_unit_configuration
                    ON business_unit_configuration.id = finish_product.business_unit_configuration_id
                INNER JOIN product_category
                    ON product_category.id = finish_product.product_category_id
                INNER JOIN main_product
                    ON main_product.id = finish_product.main_product_id
                INNER JOIN master_product
                    ON master_product.id = finish_product.master_product_id
                INNER JOIN product_type
                    ON product_type.id = finish_product.product_type_id
                INNER JOIN measure_unit_configuration
                    ON measure_unit_configuration.id = finish_product.measure_unit_configuration_id
            ${searchCondition}
        """

        List resultCount = sql.rows(strSql)
        return [objList: objList, count: resultCount?.get(0)?.total_rows]
    }

    @Transactional(readOnly = true)
    public List<FinishProduct> list() {
        return FinishProduct.list()
    }

    @Transactional(readOnly = true)
    public FinishProduct read(Long id) {
        return FinishProduct.read(id)
    }

    @Transactional(readOnly = true)
    public FinishProduct search(String fieldName, String fieldValue) {
        String query = "from FinishProduct as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return FinishProduct.find(query)
    }

    @Transactional(readOnly = true)
    public Map productTypeCategoryForEnterprise(Object params) {
        sql = new Sql(dataSource)
        List categoryList = []
        List masterProductList = []
        List mainProductList = []
        List productTypeList = []
        List businessUnitList = []
        List measureUnitList = []
        String strSql = ''
        strSql = """SELECT id,CONCAT(name,'[',code,']') AS name
                             FROM product_category WHERE enterprise_configuration_id=${params.id} """
        categoryList = sql.rows(strSql)
        strSql = """SELECT id,CONCAT(name,'[',code,']') AS name
                             FROM master_product WHERE enterprise_configuration_id=${params.id} """
        masterProductList = sql.rows(strSql)
        strSql = """SELECT id,CONCAT(name,'[',code,']') AS name
                             FROM main_product WHERE enterprise_configuration_id=${params.id} """
        mainProductList = sql.rows(strSql)
        strSql = """SELECT id,name
                             FROM product_type WHERE enterprise_configuration_id=${params.id} """
        productTypeList = sql.rows(strSql)
        strSql = """SELECT business_unit_configuration.id,CONCAT(business_unit_configuration.name,' [',business_unit_configuration.code,']') as name
                    FROM business_unit_configuration
                    WHERE business_unit_configuration.enterprise_configuration_id=${params.id}"""
        businessUnitList = sql.rows(strSql)
        strSql = """SELECT id,name
                             FROM measure_unit_configuration WHERE enterprise_configuration_id=${params.id} """
        measureUnitList = sql.rows(strSql)
        return [businessUnitList: businessUnitList, categoryList: categoryList, masterProductList: masterProductList,
                mainProductList : mainProductList, productTypeList: productTypeList, measureUnitList: measureUnitList]

    }

    @Transactional(readOnly = true)
    public Map getFinishProductForEdit(Long id) {
        Map result = [:]
        FinishProduct finishProduct = FinishProduct.read(id)
        EnterpriseConfiguration enterpriseConfiguration = finishProduct.enterpriseConfiguration
        BusinessUnitConfiguration businessUnitConfiguration = finishProduct.businessUnitConfiguration
        ProductCategory productCategory = finishProduct.productCategory
        MasterProduct masterProduct = finishProduct.masterProduct
        MainProduct mainProduct = finishProduct.mainProduct
        ProductType productType = finishProduct.productType
        MeasureUnitConfiguration measureUnitConfiguration = finishProduct.measureUnitConfiguration
        result.put("finishProduct", finishProduct)
        result.put("enterpriseConfiguration", enterpriseConfiguration)
        result.put("businessUnitConfiguration", businessUnitConfiguration)
        result.put("productCategory", productCategory)
        result.put("masterProduct", masterProduct)
        result.put("mainProduct", mainProduct)
        result.put("productType", productType)
        result.put("measureUnitConfiguration", measureUnitConfiguration)
        result.put("chartOfAccountGroup", finishProduct.chartOfAccountGroup)
        result.put("chartOfAccountHead", finishProduct.chartOfAccountHead)
        result.put("sequenceNumber", finishProduct.sequenceNumber)
        return result
    }

    @Transactional(readOnly = true)
    public List listProductByEnterprise(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """AND ( finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%'
                          OR pricing_category.name LIKE '%${params.query}%' OR product_pricing_type.name LIKE '%${
                params.query
            }%'
                            OR product_price_product.price LIKE '%${params.query}%'   )
                """
        }
        String strSql = """
            SELECT `finish_product`.`id`, `finish_product`.`code`, `finish_product`.`name`,
                `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`price`
            FROM `customer_master`
                INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`)
                INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`)
                LEFT OUTER JOIN `customer_product_price` ON (`customer_master`.`id` = `customer_product_price`.`customer_master_id`
                    AND `product_price`.`id` = `customer_product_price`.`product_price_id`)
            WHERE `customer_master`.id = ${params.id}
                ${query}
        """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List<GroovyRowResult> listProductPriceByCustomerForRetail(Object params) {
        try {
            long partnerTypeId = -1
            sql = new Sql(dataSource)
            String query = ""
            String condition = ""
            if (params.query) {
                query = """
                    AND ( finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%'
                    OR pricing_category.name LIKE '%${params.query}%' OR product_pricing_type.name LIKE '%${params.query}%'
                    OR product_price_product.price LIKE '%${params.query}%' )
                """
            }
            if (params.territorySubAreaId) {
                condition = """ AND `customer_territory_sub_area`.`territory_sub_area_id`=:territorySubAreaId"""
            }
            String strSql = """
                SELECT `pricing_category_id` FROM `customer_master`
                WHERE `customer_master`.`id`=:customerId
                LIMIT 1
            """
            List partnerTypeList = sql.rows(strSql, params)
            if (partnerTypeList && partnerTypeList.size() > 0) {
                partnerTypeId = partnerTypeList.first().pricing_category_id
            }

            if (partnerTypeId == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID) {
                // Negotiated Customer
                strSql = """
                    SELECT DISTINCT `product_price`.`id` AS ppId, `finish_product`.`id` AS productId , `finish_product`.`code` AS productCode, `finish_product`.`name` AS productName,
                        `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`total_amount` AS price, finish_product.qty_in_ltr AS qtyInLtr,
                        master_product.name AS masterName, main_product.name AS mainName
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                            AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `customer_product_price` ON (`customer_product_price`.`product_price_id` = `product_price`.`id`
                            AND `customer_product_price`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN main_product
                            ON main_product.id=finish_product.main_product_id
                        INNER JOIN master_product
                            ON master_product.id=finish_product.master_product_id
                    WHERE `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}
                        AND `finish_product`.is_active = TRUE
                        AND `customer_master`.`id`=:customerId
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        ${query}
                    ORDER BY master_product.`sequence_number`, main_product.`sequence_number`, `finish_product`.`sequence_number`
                """
            } else {
                // Non Negotiated ie DP/TP/MRP Customer
                strSql = """
                    SELECT distinct `product_price`.`id` AS ppId, `finish_product`.`id`  AS productId, `finish_product`.`code` AS productCode, `finish_product`.`name` AS productName,
                        `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`total_amount` AS price, finish_product.qty_in_ltr AS qtyInLtr,
                         master_product.name AS masterName, main_product.name AS mainName
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id` AND product_price.`is_active` = ${true})
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                            AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                            AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN main_product
                            ON main_product.id=finish_product.main_product_id
                        INNER JOIN master_product
                            ON master_product.id=finish_product.master_product_id
                    WHERE `customer_master`.`id`=:customerId
                        AND `finish_product`.is_active = TRUE
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        ${query}
                        ${condition}
                    ORDER BY master_product.`sequence_number`, main_product.`sequence_number`, `finish_product`.`sequence_number`
                """
            }

            List resultList = sql.rows(strSql, params)
            return resultList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<GroovyRowResult> listProductPriceByCustomer(Object params) {
        try {
            long partnerTypeId = -1
            sql = new Sql(dataSource)
            String query = ""
            String condition = ""
            if (params.query) {
                query = """ AND ( finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%'
                            OR pricing_category.name LIKE '%${params.query}%' OR product_pricing_type.name LIKE '%${params.query}%'
                            OR product_price_product.price LIKE '%${params.query}%')
                """
            }
            if (params.territorySubAreaId) {
                condition = """ AND `customer_territory_sub_area`.`territory_sub_area_id`=:territorySubAreaId"""
            }
            String strSql = """
                SELECT `pricing_category_id` FROM `customer_master`
                WHERE `customer_master`.`id`=:customerId
                LIMIT 1
            """
            List partnerTypeList = sql.rows(strSql, params)
            if (partnerTypeList && partnerTypeList.size() > 0) {
                partnerTypeId = partnerTypeList.first().pricing_category_id
            }

            if (partnerTypeId == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID) {
                // Negotiated Customer
                strSql = """
                    SELECT DISTINCT `product_price`.`id` AS ppId, `finish_product`.`id` , `finish_product`.`code` , `finish_product`.`name`,
                        `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`total_amount` AS price, finish_product.qty_in_ltr AS qtyInLtr,
                        master_product.name AS masterName, main_product.name AS mainName
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                            AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `customer_product_price` ON (`customer_product_price`.`product_price_id` = `product_price`.`id`
                            AND `customer_product_price`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN main_product
                            ON main_product.id=finish_product.main_product_id
                        INNER JOIN master_product
                            ON master_product.id=finish_product.master_product_id
                    WHERE `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}
                        AND `finish_product`.is_active = TRUE
                        AND `customer_master`.`id`=:customerId
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        ${query}
                        ORDER BY master_product.`sequence_number`, main_product.`sequence_number`, `finish_product`.`sequence_number`
                """
            } else {
                // Non Negotiated ie DP/TP/MRP Customer
                strSql = """
                    SELECT distinct `product_price`.`id` AS ppId, `finish_product`.`id` , `finish_product`.`code` , `finish_product`.`name`,
                        `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`total_amount` AS price, finish_product.qty_in_ltr AS qtyInLtr,
                        master_product.name AS masterName, main_product.name AS mainName
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id` AND product_price.`is_active` = ${true})
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                            AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                            AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN main_product
                            ON main_product.id=finish_product.main_product_id
                        INNER JOIN master_product
                            ON master_product.id=finish_product.master_product_id
                    WHERE `customer_master`.`id`=:customerId
                        AND `finish_product`.is_active = TRUE
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        ${condition}
                        ${query}
                        ORDER BY master_product.`sequence_number`, main_product.`sequence_number`, `finish_product`.`sequence_number`
                """
            }

            List<GroovyRowResult> resultList = sql.rows(strSql, params)
            return resultList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listProductByEnterpriseForUpdate(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """AND ( finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%'
                          OR pricing_category.name LIKE '%${params.query}%' OR product_pricing_type.name LIKE '%${
                params.query
            }%'
                            OR product_price_product.price LIKE '%${params.query}%'   )
                """
        }
        String strSql = """
            SELECT DISTINCT `finish_product`.`id`, `finish_product`.`code`, `finish_product`.`name`,
                `pricing_category`.`name` AS `p_cat`, `product_pricing_type`.`name` AS `p_type`, `product_price_product`.`price`
            FROM `customer_master`
                INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`)
                INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`)
                LEFT OUTER JOIN `customer_product_price` ON (`customer_master`.`id` = `customer_product_price`.`customer_master_id`
                    AND `product_price`.`id` = `customer_product_price`.`product_price_id`)
                INNER JOIN `enterprise_configuration` ON (`enterprise_configuration`.`id` = `customer_master`.`enterprise_configuration_id`)
            WHERE
            `enterprise_configuration`.`id` = ${params.id}
                AND `finish_product`.`id` NOT IN (SELECT `finish_product_id`
                                                FROM `secondary_demand_order_details`
                                                    INNER JOIN `secondary_demand_order` ON (`secondary_demand_order`.`id` = `secondary_demand_order_details`.`secondary_demand_order_id`)
                                                WHERE `order_no` = ${params.orderNo}
                                                )
           ${query}
        """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional(readOnly = true)
    public List listProductForPackageByEnterprise(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """ AND (finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%')
                """
        }
        String strSql = """
            SELECT `finish_product`.`id`
                , `finish_product`.`code`
                , `finish_product`.`name`
                , `finish_product`.`pack_size`
                , `product_category`.`name` AS p_cat
                , `product_type`.`name` AS p_type
                , `measure_unit_configuration`.`name` AS mu
                , `measure_unit_configuration`.`id` AS muId
            FROM
                `finish_product`
                INNER JOIN `product_category`
                    ON (`finish_product`.`product_category_id` = `product_category`.`id`)
                INNER JOIN `product_type`
                    ON (`finish_product`.`product_type_id` = `product_type`.`id`)
                INNER JOIN `measure_unit_configuration`
                    ON (`finish_product`.`measure_unit_configuration_id` = `measure_unit_configuration`.`id`)
                INNER JOIN master_product ON (finish_product.`master_product_id` = master_product.`id`)
                INNER JOIN main_product ON (finish_product.`main_product_id` = main_product.`id`)
            WHERE `finish_product`.`enterprise_configuration_id` = ${params.id}
                AND `finish_product`.is_active = TRUE
                ${query}
            ORDER BY master_product.`sequence_number`, main_product.`sequence_number`,
                finish_product.`sequence_number`, finish_product.`name`
        """
        List resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listProductWithoutPrice(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """AND (finish_product.code LIKE '%${params.query}%' OR finish_product.name LIKE '%${params.query}%')
                """
        }
        if (params.enterpriseId) {
            query += " AND `finish_product`.`enterprise_configuration_id`  = ${params.enterpriseId}"
        }
        String strSql = """
            SELECT `finish_product`.`id`, `finish_product`.`code`, `finish_product`.`name`, `enterprise_configuration`.`name` AS `enterprise`, `finish_product`.`pack_size` AS `packSize`,
                `product_category`.`name` AS `category`, `product_type`.`name` AS `type`, `measure_unit_configuration`.`name` AS `measurementUnit`
            FROM `finish_product`
                INNER JOIN `product_category` ON (`product_category`.id = `finish_product`.`product_category_id`)
                INNER JOIN `product_type` ON (`product_type`.`id` = `finish_product`.`product_type_id`)
                INNER JOIN `measure_unit_configuration` ON (`measure_unit_configuration`.`id` = `finish_product`.`measure_unit_configuration_id`)
                INNER JOIN `enterprise_configuration` ON (`finish_product`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
            WHERE 1 = 1
               ${query}
        """

        List resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public boolean isUsedCao(long productId, long chartOfAccountHeadId) {
        try {
            sql = new Sql(dataSource)
            String query = """
                SELECT COUNT(id) AS totalAvailable FROM `finish_product`
                WHERE `finish_product`.`id` != ${productId} AND `finish_product`.`chart_of_account_head_id` = ${
                chartOfAccountHeadId
            }
            """
            List result = sql.rows(query)
            if (result && result.size() > 0) {
                if (result.first().totalAvailable > 0) {
                    return true
                } else {
                    return false
                }
            }
            return false
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List finishProductList(Object params) {
        sql = new Sql(dataSource)
        String query = ""

        String strSql = """
           SELECT fp.id, CONCAT(fp.pack_size,' ', muc.name) AS finish_product
            FROM finish_product AS fp
            INNER JOIN measure_unit_configuration AS muc ON muc.id = fp.measure_unit_configuration_id
            ORDER BY muc.id
        """

        List resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public Map finishProductPackSizeByProduct(Object params) {
        List packSizeList = getPackSizeListByCriteria(params)
        Map map = [:]
        map.put("packSizeList", packSizeList)
        return map
    }

    @Transactional(readOnly = true)
    public List getPackSizeListByCriteria(Object params) {
        List resultList = []
        sql = new Sql(dataSource)

        String condition = ''
        if (params.productIds) {
            condition += """ AND mp.id IN (${params.productIds}) """
        }
        String strSql = """
            SELECT fp.id, fp.pack_size, fp.name, mp.name AS main_product, muc.name AS measurement_name, CONCAT(fp.pack_size,' ', muc.name,' ',mp.name) AS finish_product
            FROM finish_product AS fp
                INNER JOIN main_product AS mp ON mp.id = fp.main_product_id
                INNER JOIN product_category AS c ON c.id = fp.product_category_id
                INNER JOIN measure_unit_configuration AS muc ON muc.id = fp.measure_unit_configuration_id
            WHERE c.name != 'Bonus'
                AND fp.is_active = TRUE
                ${condition}
            ORDER BY muc.id, mp.sequence_number, fp.sequence_number, fp.name
        """
        resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public List getMainProductList(Object params) {
        List resultList = []
        sql = new Sql(dataSource)

        String strSql = """
                    SELECT DISTINCT mp.id, mp.name
                    FROM finish_product AS fp
                    INNER JOIN main_product AS mp ON mp.id = fp.main_product_id
                    INNER JOIN product_category AS c ON c.id = fp.product_category_id
                    WHERE c.name != 'Bonus'
                    ORDER BY mp.id
                """
        resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public Map getCategoryByChannelId(Object params) {
        List categoryList = getCategoryListByChannelId(params)
        Map map = [:]
        map.put("categoryList", categoryList)
        return map
    }

    @Transactional(readOnly = true)
    public List getCategoryListByChannelId(Object params) {
        List resultList = []
        sql = new Sql(dataSource)

        String condition = ''
        if (params.channelIds) {
            condition += """ WHERE channel.id IN (${params.channelIds}) """
        }
        String strSql = """
                   SELECT DISTINCT category.id, category.name AS name
                   FROM customer_category AS category
                    INNER JOIN customer_master AS cm ON category.id = cm.category_id
                    INNER JOIN customer_sales_channel AS channel ON channel.id = cm.customer_sales_channel_id
                    ${condition}
                    ORDER BY category.id
                """
        resultList = sql.rows(strSql)
        return resultList
    }


}
