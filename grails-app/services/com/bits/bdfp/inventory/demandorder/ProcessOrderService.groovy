package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.bonus.CustomerBonusFinishGoodService
import com.bits.bdfp.bonus.QuantityBasedBonus
import com.bits.bdfp.customer.*
import com.bits.bdfp.inventory.sales.*
import com.bits.bdfp.inventory.warehouse.*
import com.bits.bdfp.promotion.AdjustBonusPromotionWithInvoice
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Service
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource
import java.text.SimpleDateFormat

class ProcessOrderService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    PrimaryDemandOrderService primaryDemandOrderService
    FinishGoodWarehouseService finishGoodWarehouseService
    CustomerMasterService customerMasterService
    CustomerBonusFinishGoodService customerBonusFinishGoodService
    SpringSecurityService springSecurityService

    @Transactional
    public PrimaryDemandOrderDetails create(Object object) {
        PrimaryDemandOrderDetails primaryDemandOrderDetails = (PrimaryDemandOrderDetails) object
        if (primaryDemandOrderDetails.save(false)) {
            return primaryDemandOrderDetails
        }
        return null
    }

    @Transactional
    public SecondaryDemandOrderDetails createSecondary(Object object) {
        SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
        if (secondaryDemandOrderDetails.save(false)) {
            return secondaryDemandOrderDetails
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        return new Integer(0)
    }

    @Transactional
    public double getAdvanceAmountFromSubLedger(String accCode) {
        sql = new Sql(dataSource)
        String strSql = """
                        SELECT IFNULL(SUM(credit)-SUM(debit),0) AS advance FROM sub_ledger
                        WHERE acc_code = '${accCode}' AND is_active = true
                        """
        List objList = sql.rows(strSql.toString())
        def amt = objList[0].advance
        return amt
    }

    @Transactional
    public Integer delete(Object object) {
//        PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
//        primaryDemandOrder.delete()
        return new Integer(1)
    }


    @Transactional(readOnly = true)
    public List<PrimaryDemandOrder> list() {
//        return PrimaryDemandOrder.list()
        return null
    }

    @Transactional(readOnly = true)
    public PrimaryDemandOrder read(Long id) {
//        return PrimaryDemandOrder.read(id)
        return null
    }

    @Transactional
    public boolean SaveInvoiceSubLedger(Map object) {

        List<Invoice> invoiceList = []
        List<List<SubLedger>> subLedgerList = []
        try {
            if (object.containsKey("invoiceList")) {
                invoiceList = (List<Invoice>) object.get("invoiceList")
                invoiceList.each {
                    it.save()
                }
            }
            if (object.containsKey("subLedgerList")) {
                subLedgerList = (List<List<SubLedger>>) object.get("subLedgerList")
                subLedgerList.each {
                    it.each {
                        it.save()
                    }
                }
            }
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false, demandMet: null]
        }
    }

    @Transactional
    public Map getProcessOrderDetails(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String filter = "";
        String offSet = ""
        String salesChannel = ""
        String customerId = ""
        String legacyId = ""

        if (params.customerId) {
            customerId = """  AND  customer_master.id = ${params.customerId}
        """
        }
        if (params.legacyId) {
            legacyId = """  AND  customer_master.legacy_id LIKE '%${params.legacyId}%'
        """
        }

        if (params.salesChannel) {
            salesChannel = """  AND  customer_master.customer_sales_channel_id = ${params.salesChannel}
        """
        }
        if (params.orderNo) {
            filter = """AND primary_demand_order.order_no = '${params.orderNo}'
        """

        } else if (params.deliveryDate) {
            String compareOperator = "="
            if (params?.checked?.toString() == 'true') {
                compareOperator = "<="
            }
            filter = """AND  DATE(`primary_demand_order`.`date_expected_deliver`) ${compareOperator} STR_TO_DATE('${
                params.deliveryDate
            }', '${ApplicationConstants.DATE_FORMAT_DB}') """
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
            SELECT `primary_demand_order`.`id`, `primary_demand_order`.`order_no`,  `csa`.`address`,
                ROUND(SUM(`primary_demand_order_details`.`quantity` * `primary_demand_order_details`.rate),2 ) AS totalAmout,
                DATE_FORMAT(`primary_demand_order`.`order_date`,'%d-%m-%Y') AS `order_date`,
                DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'%d-%m-%Y') AS `date_expected_deliver`,
                `customer_master`.`name` AS `customer_name`, `customer_master`.`id` AS `customer_id`
                , `customer_master`.`legacy_id` AS `legacy_id` , `customer_master`.`code` AS `customer_code`,
                CONCAT(`customer_master`.`code`, `customer_type`.`advance_code`) AS adv_acc_code,
                CONCAT(`customer_master`.`code`, `customer_type`.`receivable_code`) AS rcv_acc_code
            FROM `primary_demand_order`
                INNER JOIN `primary_demand_order_details` ON (`primary_demand_order`.id = `primary_demand_order_details`.`primary_demand_order_id`)
                INNER JOIN `customer_master` ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
                INNER JOIN `customer_type` ON (`customer_master`.`customer_type_id` = `customer_type`.`id`)
                LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
            WHERE 1 = 1
                ${salesChannel}
                ${customerId}
                ${legacyId}
                AND (`primary_demand_order`.`demand_order_status` = '${
            DemandOrderStatus.APPROVED
        }' OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                ${filter}
            GROUP BY `primary_demand_order`.`order_no`
            ORDER BY `primary_demand_order`.`id` ASC
            ${strLIMIT}
            ${offSet}
          """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
//            objList.each {
//                updateOrderStatus(it.id, DemandOrderStatus.UNDER_REVIEW, params.applicationUser)
//            }
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional
    public void updateOrderStatus(Long orderId, DemandOrderStatus demandOrderStatus, ApplicationUser applicationUser) {
        PrimaryDemandOrder primaryDemandOrder = primaryDemandOrderService.read(orderId)
        primaryDemandOrder.demandOrderStatus = demandOrderStatus
        primaryDemandOrder.userUpdated = applicationUser
        primaryDemandOrder.lastUpdated = new Date()
        primaryDemandOrder.save()
    }

    // Primary Order Check
    @Transactional(readOnly = true)
    public Map itemAvailabilityDetailsForProcessOrder(Object params) {
        try{
            sql = new Sql(dataSource)

            String strSql = """
                SELECT  `finish_product`.`id`, `finish_product`.`name`, `finish_product`.`code`,
                    SUM(`primary_demand_order_details`.`quantity`) AS `order_qty`,
                    IFNULL(tbl.`batch_no`,'NA') AS `batch_no`, IFNULL(tbl.qty,0) AS `qty`
                FROM `primary_demand_order_details`
                    INNER JOIN `primary_demand_order` ON (`primary_demand_order`.`id` = `primary_demand_order_details`.`primary_demand_order_id`)
                    INNER JOIN `finish_product` ON (`finish_product`.`id` = `primary_demand_order_details`.`finish_product_id`)
                    LEFT OUTER JOIN(
                        SELECT `finish_good_stock`.`finish_product_id` AS `id`, IFNULL(SUM(`finish_good_stock_transaction`.`in_quantity` - `finish_good_stock_transaction`.`out_quantity`),0) AS qty, GROUP_CONCAT(DISTINCT IFNULL(`finish_good_stock`.`batch_no`,'NA')) AS `batch_no`
                        FROM `finish_good_stock`
                            INNER JOIN `sub_warehouse` ON (`finish_good_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                            INNER JOIN `finish_good_stock_transaction` ON (`finish_good_stock_transaction`.`finish_good_stock_id` = `finish_good_stock`.`id`)
                        WHERE `sub_warehouse`.`warehouse_id` = ${params.warehouseId}
                            AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                            AND `finish_good_stock`.`in_quantity` > `finish_good_stock`.`out_quantity`
                        GROUP BY `finish_good_stock`.`finish_product_id`)
                        AS tbl
                        ON (tbl.id = `finish_product`.`id`)
                WHERE `primary_demand_order`.`id` IN (${params.ids})
                GROUP BY `finish_product`.`id`
            """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }
            return [objList: objList, count: resultCount]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public List primaryOrderDetailsList(PrimaryDemandOrder primaryDemandOrder, long warehouseId, long subWarehouseTypeId) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT  `finish_product`.`id`, `finish_product`.`name`, `finish_product`.`code`,
                SUM(`primary_demand_order_details`.`quantity`) AS `order_qty`,
                IFNULL(tbl.`batch_no`, '') AS `batch_no`, IFNULL(tbl.qty, 0) `qty`
            FROM
                `primary_demand_order_details`
                    INNER JOIN `primary_demand_order` ON (`primary_demand_order`.`id` = `primary_demand_order_details`.`primary_demand_order_id`)
                    INNER JOIN `finish_product` ON (`finish_product`.`id` = `primary_demand_order_details`.`finish_product_id`)
                    LEFT JOIN(
                        SELECT `finish_good_stock`.`finish_product_id` AS `id`, SUM(`finish_good_stock`.`in_quantity` - `finish_good_stock`.`out_quantity`) AS qty, GROUP_CONCAT(DISTINCT `finish_good_stock`.`batch_no`) AS `batch_no`
                        FROM `finish_good_stock`
                            INNER JOIN `sub_warehouse` ON (`finish_good_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                        WHERE `sub_warehouse`.`warehouse_id` = ${warehouseId}
                            AND `sub_warehouse`.`sub_warehouse_type_id` = ${subWarehouseTypeId}
                            AND `finish_good_stock`.`in_quantity` > `finish_good_stock`.`out_quantity`
                        GROUP BY `finish_good_stock`.`finish_product_id`)
                        AS tbl ON (tbl.id = `finish_product`.`id`)
            WHERE `primary_demand_order`.`id` = ${primaryDemandOrder.id}
            GROUP BY `finish_product`.`id`
        """

        List list = sql.rows(strSql)
        return list
    }

    @Transactional(readOnly = true)
    public List secondaryOrderDetailsList(SecondaryDemandOrder secondaryDemandOrder, long warehouseId, long subWarehouseTypeId) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT  `finish_product`.`id`, `finish_product`.`name`, `finish_product`.`code`,
                SUM(`secondary_demand_order_details`.`quantity`) AS `order_qty`,
                IFNULL(tbl.`batch_no`, '') AS `batch_no`, IFNULL(tbl.qty, 0) `qty`
            FROM
                `secondary_demand_order_details`
                    INNER JOIN `secondary_demand_order` ON (`secondary_demand_order`.`id` = `secondary_demand_order_details`.`secondary_demand_order_id`)
                    INNER JOIN `finish_product` ON (`finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`)
                    LEFT JOIN(
                        SELECT `distribution_point_stock`.`finish_product_id` AS `id`, SUM(`distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity`) AS qty, GROUP_CONCAT(DISTINCT `distribution_point_stock`.`batch_no`) AS `batch_no`
                        FROM `distribution_point_stock`
                            INNER JOIN `sub_warehouse` ON (`distribution_point_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                        WHERE `sub_warehouse`.`warehouse_id` = ${warehouseId}
                            AND `sub_warehouse`.`sub_warehouse_type_id` = ${subWarehouseTypeId}
                            AND `distribution_point_stock`.`in_quantity` > `distribution_point_stock`.`out_quantity`
                        GROUP BY `distribution_point_stock`.`finish_product_id`)
                        AS tbl ON (tbl.id = `finish_product`.`id`)
            WHERE `secondary_demand_order`.`id` = ${secondaryDemandOrder.id}
            GROUP BY `finish_product`.`id`
        """
        List list = sql.rows(strSql)
        return list
    }

    @Transactional
    public Double getUpdatedInvoiceAmount(PrimaryDemandOrder primaryDemandOrder) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id


        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF('${primaryDemandOrder.updatedDeliveryDate}', `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`total_amount` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${primaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getInvoiceAmount(PrimaryDemandOrder primaryDemandOrder) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`total_amount` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${primaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getUpdatedInvoiceAmountSecondary(SecondaryDemandOrder secondaryDemandOrder) {

        CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(secondaryDemandOrder.customerMaster)

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${customerTerritorySubArea.territorySubArea.id}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`total_amount` * `secondary_demand_order_details`.`quantity`) AS amt
                        FROM `secondary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `secondary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                secondaryDemandOrder.customerMaster.pricingCategory.id
            }
                        AND `secondary_demand_order_details`.`secondary_demand_order_id` = ${secondaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }

    }

    @Transactional
    public Double getUpdatedInvoiceAmountAct(PrimaryDemandOrder primaryDemandOrder) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF('${primaryDemandOrder.updatedDeliveryDate}', `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`price` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${primaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getInvoiceAmountAct(PrimaryDemandOrder primaryDemandOrder) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`price` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${primaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getUpdatedInvoiceAmountSecondaryAct(SecondaryDemandOrder secondaryDemandOrder) {

        CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(secondaryDemandOrder.customerMaster)

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${customerTerritorySubArea.territorySubArea.id}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`price` * `secondary_demand_order_details`.`quantity`) AS amt
                        FROM `secondary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `secondary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                secondaryDemandOrder.customerMaster.pricingCategory.id
            }
                        AND `secondary_demand_order_details`.`secondary_demand_order_id` = ${secondaryDemandOrder.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }

    }

    @Transactional
    public Double getUpdatedInvoiceAmountDetails(PrimaryDemandOrderDetails primaryDemandOrderDetails) {
        DistributionPointTerritorySubArea dp = DistributionPointTerritorySubArea.findByDistributionPoint(primaryDemandOrderDetails.primaryDemandOrder.distributionPoint)
        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(primaryDemandOrderDetails.primaryDemandOrder.distributionPoint)

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${dp.territorySubArea.id}
                        ORDER BY
                        ABS(DATEDIFF('${primaryDemandOrderDetails.primaryDemandOrder.updatedDeliveryDate}', `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`total_amount` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                distributionPointWarehouse.defaultCustomer.pricingCategory.id
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${
                primaryDemandOrderDetails.primaryDemandOrder.id
            }
                        AND `primary_demand_order_details`.`finish_product_id` = ${
                primaryDemandOrderDetails.finishProductId
            }
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getInvoiceAmountDetails(PrimaryDemandOrderDetails primaryDemandOrderDetails) {
        DistributionPointTerritorySubArea dp = DistributionPointTerritorySubArea.findByDistributionPoint(primaryDemandOrderDetails.primaryDemandOrder.distributionPoint)
        DistributionPointWarehouse distributionPointWarehouse = DistributionPointWarehouse.findByDistributionPoint(primaryDemandOrderDetails.primaryDemandOrder.distributionPoint)

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${dp.territorySubArea.id}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT SUM(`product_price_product`.`total_amount` * `primary_demand_order_details`.`quantity`) AS amt
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                distributionPointWarehouse.defaultCustomer.pricingCategory.id
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${
                primaryDemandOrderDetails.primaryDemandOrder.id
            }
                         AND `primary_demand_order_details`.`finish_product_id` = ${
                primaryDemandOrderDetails.finishProductId
            }
                    """
            List list1 = sql.rows(strSql)
            return list1[0].amt
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getUpdatedInvoiceRateDetails(PrimaryDemandOrderDetails primaryDemandOrderDetails) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrderDetails.primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrderDetails.primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id


        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF('${primaryDemandOrderDetails.primaryDemandOrder.updatedDeliveryDate}', `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT `product_price_product`.`total_amount` AS rate
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${
                primaryDemandOrderDetails.primaryDemandOrder.id
            }
                        AND `primary_demand_order_details`.`finish_product_id` = ${
                primaryDemandOrderDetails.finishProductId
            }
                    """
            List list1 = sql.rows(strSql)
            return list1[0].rate
        } else {
            return 0.0
        }
    }

    @Transactional
    public Double getInvoiceRateDetailsSecondary(SecondaryDemandOrderDetails secondaryDemandOrderDetails) {
        CustomerTerritorySubArea customerTerritorySubArea = CustomerTerritorySubArea.findByCustomerMaster(secondaryDemandOrderDetails.secondaryDemandOrder.customerMaster)

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${customerTerritorySubArea.territorySubArea.id}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT `product_price_product`.`total_amount` AS rate
                        FROM `secondary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `secondary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                secondaryDemandOrderDetails.secondaryDemandOrder.customerMaster.pricingCategory.id
            }
                        AND `secondary_demand_order_details`.`id` = ${secondaryDemandOrderDetails.id}
                    """
            List list1 = sql.rows(strSql)
            return list1[0].rate
        } else {
            return 0.0
        }
    }

    public Double getInvoiceRateDetails(PrimaryDemandOrderDetails primaryDemandOrderDetails) {
        Long territorySubAreaId
        Long pricingCategoryId

        CustomerMaster customerMaster = primaryDemandOrderDetails.primaryDemandOrder.customerOrderFor
        territorySubAreaId = primaryDemandOrderDetails.primaryDemandOrder.territorySubArea.id
        pricingCategoryId = customerMaster.pricingCategory.id

        String strSql = """
                    SELECT `product_price`.`id` FROM `product_price`
                        JOIN `territory_sub_area_product_price`
                        ON (`territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            JOIN `territory_sub_area`
                            ON (`territory_sub_area_product_price`.`territory_sub_area_id` = `territory_sub_area`.`id`)
                        WHERE `territory_sub_area`.id = ${territorySubAreaId}
                        ORDER BY
                        ABS(DATEDIFF(NOW(), `product_price`.`date_effective_from`))
                        LIMIT 1
                            """
        List list = sql.rows(strSql)
        if (list.size() > 0) {
            strSql = """
                    SELECT `product_price_product`.`total_amount` AS rate
                        FROM `primary_demand_order_details`
                            JOIN `product_price_product`
                            ON (`product_price_product`.`finish_product_id` = `primary_demand_order_details`.`finish_product_id`)
                    WHERE `product_price_product`.`product_price_id` = ${
                list[0].id
            } AND `product_price_product`.`pricing_category_id` = ${
                pricingCategoryId
            }
                        AND `primary_demand_order_details`.`primary_demand_order_id` = ${
                primaryDemandOrderDetails.primaryDemandOrder.id
            }
                         AND `primary_demand_order_details`.`finish_product_id` = ${
                primaryDemandOrderDetails.finishProductId
            }
                    """
            List list1 = sql.rows(strSql)
            return list1[0].rate
        } else {
            return 0.0
        }
    }

    @Transactional
    public Object getInvoiceAndSubLedgerList(Object params, Object object) {
        Map mapInstance = [:]
        StringBuffer msgBuffer = new StringBuffer();
        try {
            SubLedger subLedger = null
            List<SubLedger> subLedgerList = []
            List<List<SubLedger>> listList = []
            ApplicationUser applicationUser = (ApplicationUser) object
            ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
            EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration
            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            List<Invoice> invoiceList = []

            Invoice invoice = null
            params.invItems.each { key, val ->
                if (val instanceof Map) {
                    invoice = new Invoice()
                    if (val.orderId) {
                        invoice.primaryDemandOrder = PrimaryDemandOrder.read(val.orderId)
                        if (invoice.primaryDemandOrder.updatedDeliveryDate) {
                            invoice.invoiceAmount = getUpdatedInvoiceAmount(invoice.primaryDemandOrder)
                            invoice.vat = invoice.invoiceAmount - getUpdatedInvoiceAmountAct(invoice.primaryDemandOrder)
                        } else {
                            invoice.invoiceAmount = getInvoiceAmount(invoice.primaryDemandOrder)
                            invoice.vat = invoice.invoiceAmount - getInvoiceAmountAct(invoice.primaryDemandOrder)
                        }
                        invoice.defaultCustomer = invoice.primaryDemandOrder.customerOrderFor
                    } else {
                        invoice.secondaryDemandOrder = SecondaryDemandOrder.read(val.orderIds)
                        invoice.invoiceAmount = getUpdatedInvoiceAmountSecondary(invoice.secondaryDemandOrder)
                        invoice.vat = invoice.invoiceAmount - getUpdatedInvoiceAmountSecondaryAct(invoice.secondaryDemandOrder)
                        invoice.defaultCustomer = invoice.secondaryDemandOrder.customerMaster
                    }

                    invoice.ait = 0
                    if (getAdvanceAmountFromSubLedger(val.advAccCode)) {
                        if (getAdvanceAmountFromSubLedger(val.advAccCode) > invoice.invoiceAmount) {
                            invoice.paidAmount = invoice.invoiceAmount
                        } else {
                            invoice.paidAmount = getAdvanceAmountFromSubLedger(val.advAccCode)
                        }

                    } else {
                        invoice.paidAmount = 0.0
                    }

                    invoice.userCreated = applicationUser
                    invoice.dateCreated = new Date()
                    invoice.code = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "INVOICE", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
                    invoice.isActive = true
                    invoice.isDirectInvoice = false
                    if (invoice.primaryDemandOrder) {
                        List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = PrimaryDemandOrderDetails.findAllByPrimaryDemandOrder(invoice.primaryDemandOrder)
                    } else {
                        List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = SecondaryDemandOrderDetails.findAllBySecondaryDemandOrder(invoice.secondaryDemandOrder)
                    }
                    invoiceList.add(invoice)
                    subLedgerList = getSubLedgerList(invoice.invoiceAmount, invoice.paidAmount, applicationUser, val.advAccCode, val.rcvAccCode, invoice.defaultCustomer.name, invoice.code)
                    listList.add(subLedgerList)
                }
            }

            mapInstance.put("invoiceList", invoiceList)
            mapInstance.put("subLedgerList", listList)
        }
        catch (Exception ex) {
            log.error(ex.message)
            return [message: ex.message, isValid: false, demandMet: null]
        }

        return mapInstance


    }

    @Transactional
    public List<SubLedger> getSubLedgerList(double invoiceAmount, double advanceAmount, ApplicationUser applicationUser, String advAccCode, String rcvAccCode, String customerName, String invoiceNumber) {
        SubLedger subLedger = null
        StringBuffer msgBuffer = new StringBuffer();
        ApplicationUserEnterprise applicationUserEnterprise = ApplicationUserEnterprise.findByApplicationUser(applicationUser)
        EnterpriseConfiguration enterpriseConfiguration = applicationUserEnterprise.enterpriseConfiguration
        Date dateNow = new Date()
        SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
        String currentMonth = formatMonth.format(dateNow)
        SimpleDateFormat formatYear = new SimpleDateFormat("YY")
        String currentYear = formatYear.format(dateNow)
        List<SubLedger> subLedgerList = []
        if (invoiceAmount > advanceAmount && advanceAmount == 0.0) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = rcvAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount
            subLedger.description = "Receivable from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)

        } else if (invoiceAmount > advanceAmount && advanceAmount != 0.0) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = rcvAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount - advanceAmount
            subLedger.description = "Receivable from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = advAccCode
            subLedger.credit = 0.0
            subLedger.debit = advanceAmount
            subLedger.description = "Advance Settlement from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
        } else if (invoiceAmount <= advanceAmount) {
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = "212330069"
            subLedger.credit = invoiceAmount
            subLedger.debit = 0.0
            subLedger.description = "Sales"
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
            subLedger = new SubLedger()
            subLedger.vin = CodeGenerationUtil.instance.getCode(enterpriseConfiguration, "VOUCHER", enterpriseConfiguration.code, "", "", "", "", "", currentYear, currentMonth, "", "")
            subLedger.accCode = advAccCode
            subLedger.credit = 0.0
            subLedger.debit = invoiceAmount
            subLedger.description = "Advance Settlement from " + customerName
            subLedger.isActive = true
            subLedger.transactionNo = invoiceNumber
            subLedger.transactionType = 1
            subLedger.userCreated = applicationUser
            subLedger.dateCreated = new Date()
            subLedgerList.add(subLedger)
        }
        return subLedgerList
    }

    @Transactional
    public Integer cancelInvoice(Map object, ApplicationUser applicationUser) {
        try{
            Map map = (Map) object
            List<Invoice> invoiceList = map.cancelInvoiceStatusList
            Integer count = 0
            invoiceList.each { inv ->
                if((inv.paidAmount == 0.00) && inv.isActive){
                    Journal journal = Journal.findByTableNameAndTransactionNo('invoice', inv.code)
                    if(journal){
                        List<JournalDetails> journalDetailsList = JournalDetails.findAllByJournal(journal)
                        journalDetailsList.each { journalDetails->
                            journalDetails.isActive = false
                            journalDetails.save()
                        }
                        journal.isActive = false
                        journal.save()
                    }
                    if(inv.primaryDemandOrder){
                        // Reverse Primary Invoice (Reverse Factory Stock)
                        List<FinishGoodStockTransaction> finishGoodStockTransactionList = FinishGoodStockTransaction.findAllByOutInvoice(inv)
                        finishGoodStockTransactionList.each {  finishGoodStockTransaction->
                            FinishGoodStock finishGoodStock = finishGoodStockTransaction.finishGoodStock
                            finishGoodStock.outQuantity -= finishGoodStockTransaction.outQuantity
                            finishGoodStock.userUpdated = applicationUser
                            finishGoodStock.save()

                            finishGoodStockTransaction.inQuantity = 0
                            finishGoodStockTransaction.outQuantity = 0
                            finishGoodStockTransaction.userUpdated = applicationUser
                            finishGoodStockTransaction.save()
                        }
                        inv.isActive = false
                        inv.userUpdated = applicationUser
                        inv.lastUpdated = new Date()
                        inv.save()
                        // Change Primary Order Status
                        PrimaryDemandOrder primaryDemandOrder = inv.primaryDemandOrder
                        primaryDemandOrder.demandOrderStatus = DemandOrderStatus.SENT_FOR_PROCESSING
                        primaryDemandOrder.save()
                    }
                    else if(inv.secondaryDemandOrder){
                        // Reverse Secondary Invoice (Reverse DP Stock)
                        List<DistributionPointStockTransaction> distributionPointStockTransactionList = DistributionPointStockTransaction.findAllByOutInvoice(inv)
                        distributionPointStockTransactionList.each {  distributionPointStockTransaction->
                            DistributionPointStock distributionPointStock = distributionPointStockTransaction.distributionPointStock
                            distributionPointStock.outQuantity -= distributionPointStockTransaction.outQuantity
                            distributionPointStock.userUpdated = applicationUser
                            distributionPointStock.save()

                            distributionPointStockTransaction.inQuantity = 0
                            distributionPointStockTransaction.outQuantity = 0
                            distributionPointStockTransaction.userUpdated = applicationUser
                            distributionPointStockTransaction.save()
                        }

                        // Reverse Sales Man Stock
                        List<CustomerStockTransaction> customerStockTransactionList = CustomerStockTransaction.findAllByInInvoice(inv)
                        customerStockTransactionList.each {  customerStockTransaction->
                            CustomerStock customerStock = customerStockTransaction.customerStock
                            customerStock.inQuantity -= customerStockTransaction.inQuantity
                            customerStock.userUpdated = applicationUser
                            customerStock.save()

                            customerStockTransaction.inQuantity = 0
                            customerStockTransaction.outQuantity = 0
                            customerStockTransaction.userUpdated = applicationUser
                            customerStockTransaction.save()
                        }

                        inv.isActive = false
                        inv.userUpdated = applicationUser
                        inv.lastUpdated = new Date()
                        inv.save()

                        SecondaryDemandOrder secondaryDemandOrder = inv.secondaryDemandOrder
                        secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.SENT_FOR_PROCESSING
                        secondaryDemandOrder.save()
                    }
                    count++
                }
            }
            return count
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer cancelSecondaryInvoice(Map object, ApplicationUser applicationUser) {
        try{
            Map map = (Map) object
            List<Invoice> invoiceList = map.cancelSecondaryInvoiceStatusList
            Integer count = 0
            invoiceList.each { inv ->
                if((inv.paidAmount == 0.00) && inv.isActive){
                    Journal journal = Journal.findByTableNameAndTransactionNo('invoice', inv.code)
                    if(journal){
                        List<JournalDetails> journalDetailsList = JournalDetails.findAllByJournal(journal)
                        journalDetailsList.each { journalDetails->
                            journalDetails.isActive = false
                            journalDetails.save()
                        }
                        journal.isActive = false
                        journal.save()
                    }

                    // Reverse Secondary Invoice (Reverse DP Stock)
                    List<DistributionPointStockTransaction> distributionPointStockTransactionList = DistributionPointStockTransaction.findAllByOutInvoice(inv)
                    distributionPointStockTransactionList.each {  distributionPointStockTransaction->
                        DistributionPointStock distributionPointStock = distributionPointStockTransaction.distributionPointStock
                        distributionPointStock.outQuantity = distributionPointStock.outQuantity - distributionPointStockTransaction.outQuantity
                        distributionPointStock.userUpdated = applicationUser
                        distributionPointStock.save()

                        distributionPointStockTransaction.inQuantity = 0
                        distributionPointStockTransaction.outQuantity = 0
                        distributionPointStockTransaction.userUpdated = applicationUser
                        distributionPointStockTransaction.save()
                    }

                    // Reverse Sales Man Stock
                    List<CustomerStockTransaction> customerStockTransactionList = CustomerStockTransaction.findAllByInInvoice(inv)
                    customerStockTransactionList.each {  customerStockTransaction->
                        CustomerStock customerStock = customerStockTransaction.customerStock
                        customerStock.inQuantity = customerStock.inQuantity - customerStockTransaction.inQuantity
                        customerStock.userUpdated = applicationUser
                        customerStock.save()

                        customerStockTransaction.inQuantity = 0
                        customerStockTransaction.outQuantity = 0
                        customerStockTransaction.userUpdated = applicationUser
                        customerStockTransaction.save()
                    }

                    inv.isActive = false
                    inv.userUpdated = applicationUser
                    inv.lastUpdated = new Date()
                    inv.save()

                    SecondaryDemandOrder secondaryDemandOrder = inv.secondaryDemandOrder
                    secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.ORDER_BOOKED
                    secondaryDemandOrder.save()

                    count++
                }
            }
            return count
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public void bookOrderChangeInventoryAndOrderStatus(PrimaryDemandOrder primaryDemandOrder, ApplicationUser applicationUser, Object object) {
        List<Invoice> invoiceList = []
        List<InvoiceDetails> invoiceDetailsArrayList = []
        List<List<SubLedger>> subLedgerList = []

        if (object.containsKey("invoiceList")) {
            invoiceList = (List<Invoice>) object.get("invoiceList")
            invoiceList.each {
                it.save()
            }
        }
        if (object.containsKey("subLedgerList")) {
            subLedgerList = (List<List<SubLedger>>) object.get("subLedgerList")
            subLedgerList.each {
                it.each {
                    it.save()
                }
            }
        }

        for (int k = 0; k < invoiceList.size(); k++) {
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = PrimaryDemandOrderDetails.findAllByPrimaryDemandOrder(primaryDemandOrder)
            if (primaryDemandOrderDetailsList && primaryDemandOrderDetailsList.size() > 0) {
                FinishedProductBooked finishedProductBooked = new FinishedProductBooked()
                finishedProductBooked.primaryDemandOrder = primaryDemandOrder
                finishedProductBooked.invoiceNo = invoiceList[k].code
                finishedProductBooked.dateCreated = new Date()
                finishedProductBooked.userCreated = applicationUser
                finishedProductBooked.save()
                primaryDemandOrderDetailsList.each { p_d ->
                    FinishGoodStock finishGoodStock = finishGoodWarehouseService.findFinishGoodByProduct(p_d.finishProduct)
                    if (finishGoodStock.quantity != 0) {
                        Double rate = finishGoodStock.totalPrice / finishGoodStock.quantity
                        FinishedProductBookedDetails finishedProductBookedDetails = new FinishedProductBookedDetails()
                        finishedProductBookedDetails.finishedProductBooked = finishedProductBooked
                        finishedProductBookedDetails.finishProduct = p_d.finishProduct
                        finishedProductBookedDetails.quantity = p_d.quantity
                        if (p_d.primaryDemandOrder.updatedDeliveryDate) {
                            finishedProductBookedDetails.purchageRate = getUpdatedInvoiceRateDetails(p_d)
                        } else {
                            finishedProductBookedDetails.purchageRate = getInvoiceRateDetails(p_d)
                        }
                        finishedProductBookedDetails.amount = finishedProductBookedDetails.purchageRate * finishedProductBookedDetails.quantity
                        finishedProductBookedDetails.userCreated = applicationUser
                        finishedProductBookedDetails.dateCreated = new Date()
                        finishedProductBookedDetails.save()
                        Float qt = p_d.quantity
                        Double batchRate = 0.0
                        Double total_price = 0.0
                        Double total_quantity = 0.0
                        InvoiceDetails invoiceDetails = null
                        List<FinishGoodBatchStock> finishGoodBatchStocks = FinishGoodBatchStock.findAllByFinishProduct(p_d.finishProduct, [sort: "dateCreated", order: "asc"])
                        for (int i = 0; i < finishGoodBatchStocks.size(); i++) {
                            total_price += finishGoodBatchStocks[i].totalPrice
                            total_quantity += finishGoodBatchStocks[i].quantity
                        }
                        batchRate = total_price / total_quantity
                        finishGoodBatchStocks.each {
                            if (qt != 0) {
                                if (it.quantity <= qt) {
                                    qt -= it.quantity
                                    it.totalPrice -= it.quantity * batchRate
                                    it.quantity -= it.quantity
                                    invoiceDetails = new InvoiceDetails()
                                    invoiceDetails.quantity = it.quantity
                                    invoiceDetails.finishProduct = p_d.finishProduct
                                    invoiceDetails.unitPrice = p_d.rate
                                    invoiceDetails.batchNumber = it.batchNo
                                    invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                    invoiceDetails.save()
                                } else {
                                    Double tempQt = qt
                                    qt -= qt
                                    it.totalPrice -= tempQt * batchRate
                                    it.quantity -= tempQt
                                    invoiceDetails = new InvoiceDetails()
                                    invoiceDetails.quantity = tempQt
                                    invoiceDetails.finishProduct = p_d.finishProduct
                                    invoiceDetails.unitPrice = p_d.rate
                                    invoiceDetails.batchNumber = it.batchNo
                                    invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                    invoiceDetails.save()
                                }
                                it.userUpdated = applicationUser
                                it.dateUpdated = new Date()
                                it.save(flush: true)
                            }
                        }
                        finishGoodStock.quantity -= p_d.quantity
                        finishGoodStock.totalPrice -= p_d.quantity * rate
                        finishGoodStock.userUpdated = applicationUser
                        finishGoodStock.dateUpdated = new Date()
                        finishGoodStock.save(flush: true)
                    }
                }
            }
            primaryDemandOrder.demandOrderStatus = DemandOrderStatus.ORDER_BOOKED
            primaryDemandOrder.lastUpdated = new Date()
            primaryDemandOrder.userUpdated = applicationUser
            primaryDemandOrder.save()
        }
    }

    @Transactional
    public void bookSecondaryOrderChangeInventoryAndOrderStatus(SecondaryDemandOrder secondaryDemandOrder, ApplicationUser applicationUser, String orderNo, Map object) {
        List<Invoice> invoiceList = []
        List<List<SubLedger>> subLedgerList = []
        InvoiceDetails invoiceDetails = null
        if (object.containsKey("invoiceList")) {
            invoiceList = (List<Invoice>) object.get("invoiceList")
            invoiceList.each {
                it.save()
            }
        }
        if (object.containsKey("subLedgerList")) {
            subLedgerList = (List<List<SubLedger>>) object.get("subLedgerList")
            subLedgerList.each {
                it.each {
                    it.save()
                }
            }
        }
        for (int k = 0; k < invoiceList.size(); k++) {
            List<SecondaryDemandOrderDetails> secondaryDemandOrderDetailsList = SecondaryDemandOrderDetails.findAllBySecondaryDemandOrder(secondaryDemandOrder)
            if (secondaryDemandOrderDetailsList && secondaryDemandOrderDetailsList.size() > 0) {
                secondaryDemandOrderDetailsList.each { secondaryOrderDetails ->

                    DistributionPointStockTransaction primaryDemandOrderDistributionPointStockTransaction = DistributionPointStockTransaction.findByFinishProductAndOrderNo(secondaryOrderDetails.finishProduct, orderNo)
                    DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()
                    distributionPointStockTransaction.finishProduct = secondaryOrderDetails.finishProduct
                    distributionPointStockTransaction.distributionPoint = primaryDemandOrderDistributionPointStockTransaction.distributionPoint
                    distributionPointStockTransaction.subWarehouse = primaryDemandOrderDistributionPointStockTransaction.subWarehouse
                    distributionPointStockTransaction.transactionDate = new Date()
                    distributionPointStockTransaction.orderNo = secondaryDemandOrder.orderNo
                    distributionPointStockTransaction.receivedQuantity = 0
                    distributionPointStockTransaction.deliveredQuantity = secondaryOrderDetails.quantity
                    distributionPointStockTransaction.rcvInvoiceNo = primaryDemandOrderDistributionPointStockTransaction.rcvInvoiceNo
                    distributionPointStockTransaction.outInvoiceNo = invoiceList[k].code
                    distributionPointStockTransaction.unitPrice = getInvoiceRateDetailsSecondary(secondaryOrderDetails)
                    distributionPointStockTransaction.userCreated = applicationUser
                    distributionPointStockTransaction.save(validate: false, insert: true)

                    invoiceDetails = new InvoiceDetails()
                    invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                    invoiceDetails.finishProduct = secondaryOrderDetails.finishProduct
                    invoiceDetails.quantity = secondaryOrderDetails.quantity
                    invoiceDetails.unitPrice = secondaryOrderDetails.rate
                    invoiceDetails.batchNumber = distributionPointStockTransaction.batchNo
                    invoiceDetails.save(validate: false, insert: true)

                    /************ Process Customer(Delivery Man) Stock  ***********/

                    CustomerStock customerStock = CustomerStock.findByDeliveryManAndFinishProductAndBatchNo(secondaryDemandOrder.userTentativeDelivery, secondaryOrderDetails.finishProduct, distributionPointStockTransaction.batchNo)
                    if(customerStock){
                        customerStock.userUpdated = applicationUser
                        customerStock.inQuantity += secondaryOrderDetails.quantity
                        customerStock.save()
                    }else{
                        customerStock = new CustomerStock()
                        customerStock.userCreated = applicationUser
                        customerStock.deliveryMan = secondaryDemandOrder.userTentativeDelivery
                        customerStock.batchNo = distributionPointStockTransaction.batchNo
                        customerStock.inQuantity = secondaryOrderDetails.quantity
                        customerStock.finishProduct = secondaryOrderDetails.finishProduct
                        customerStock.outQuantity = 0
                        customerStock.save(validate: false, insert: true)
                    }

                    CustomerStockTransaction customerStockTransaction = new CustomerStockTransaction()
                    customerStockTransaction.customerStock = customerStock
                    customerStockTransaction.transactionDate = new Date()
                    customerStockTransaction.inInvoice = Invoice.read(invoiceList[k].id)
                    customerStockTransaction.inQuantity = secondaryOrderDetails.quantity
                    customerStockTransaction.outQuantity = 0
                    customerStockTransaction.unitPrice = secondaryOrderDetails.rate
                    customerStockTransaction.userCreated = applicationUser
                    customerStockTransaction.save(validate: false, insert: true)
                    /********************************************************/
                }
            }

            secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.DELIVERED
            secondaryDemandOrder.lastUpdated = new Date()
            secondaryDemandOrder.userUpdated = applicationUser
            secondaryDemandOrder.save()
        }
    }

    @Transactional(readOnly = true)
    public Map updateDemandOrderDetails(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""


        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT primary_demand_order_details.id, finish_product.id AS product_id, primary_demand_order.customer_order_for_id,
                finish_product.name,finish_product.code, primary_demand_order_details.quantity, primary_demand_order_details.rate,
                ROUND((`finish_product`.`qty_in_ltr` * `primary_demand_order_details`.`quantity`), 2) AS qtyInLtr,
                primary_demand_order_details.amount, (SELECT SUM(primary_demand_order_details.amount)
                    FROM primary_demand_order_details
                        INNER JOIN primary_demand_order ON (primary_demand_order.id = primary_demand_order_details.primary_demand_order_id)
                    WHERE primary_demand_order.id = ${params.id}) AS total_amount
            FROM primary_demand_order_details
                INNER JOIN primary_demand_order ON (primary_demand_order.id = primary_demand_order_details.primary_demand_order_id)
                INNER JOIN finish_product ON (finish_product.id = primary_demand_order_details.finish_product_id)
            WHERE primary_demand_order.id = ${params.id}
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }
        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List productListForUpdateOrder(Object params) {
        long partnerTypeId = -1
        long customerId = -1
        String strSql = ""
        try{
            sql = new Sql(dataSource)
            String query = """
                SELECT customer_master.`pricing_category_id`, customer_master.id AS customer_id
                FROM primary_demand_order
                    INNER JOIN customer_master ON (`primary_demand_order`.`customer_order_for_id` = customer_master.`id`)
                WHERE  `primary_demand_order`.id = ${params.id} LIMIT 1
            """

            List resultList = sql.rows(query)
            if(resultList && resultList.size() > 0){
                partnerTypeId = resultList.first().pricing_category_id
                customerId = resultList.first().customer_id
            }
            if(partnerTypeId == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID){
                // Negotiated Customer
                strSql = """
                    SELECT DISTINCT CONCAT(finish_product.id, '-', product_price_product.`total_amount`) AS id, CONCAT(finish_product.name, '[', finish_product.code, ']') AS NAME
                    FROM customer_product_price
                        INNER JOIN `product_price` ON (`customer_product_price`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN `product_price_product` ON (`product_price_product`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`)
                        INNER JOIN main_product AS map ON map.id = finish_product.main_product_id
                        INNER  JOIN master_product AS mp ON mp.id = finish_product.master_product_id
                    WHERE product_price.`is_active` = TRUE
                        AND `finish_product`.is_active = TRUE
                        AND `customer_product_price`.`customer_master_id` = ${customerId}
                        AND `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}
                        AND product_price_product.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID}
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        GROUP BY mp.sequence_number,map.sequence_number, finish_product.sequence_number
                        ORDER BY mp.sequence_number ASC , map.sequence_number ASC , finish_product.sequence_number ASC
                """
            }
            else{
                // Non Negotiated ie DP/TP/MRP Customer
                strSql = """
                    SELECT DISTINCT CONCAT(finish_product.id, '-', product_price_product.`total_amount`) AS id, CONCAT(finish_product.name, '[', finish_product.code, ']') AS NAME
                    FROM `customer_master`
                        INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                        INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                        INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                            AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                            AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                        INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                        INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                            AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN main_product AS map ON map.id = finish_product.main_product_id
                        INNER  JOIN master_product AS mp ON mp.id = finish_product.master_product_id
                    WHERE product_price.`is_active` = TRUE
                        AND `finish_product`.is_active = TRUE
                        AND `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}
                        AND product_price_product.`pricing_category_id` = ${partnerTypeId}
                        AND `customer_master`.`id` = ${customerId}
                        AND `customer_territory_sub_area`.`territory_sub_area_id` = (SELECT `primary_demand_order`.`territory_sub_area_id`FROM `primary_demand_order`
                            WHERE  `primary_demand_order`.id = ${params.id})
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        GROUP BY mp.sequence_number,map.sequence_number, finish_product.sequence_number
                        ORDER BY mp.sequence_number ASC , map.sequence_number ASC , finish_product.sequence_number ASC
                """
            }

            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List productListForUpdateOrderSecondary(Object params) {
        long partnerTypeId = -1
        long customerId = -1
        String strSql = ""
        try{
            sql = new Sql(dataSource)
            String query = """
                SELECT customer_master.`pricing_category_id`, customer_master.id AS customer_id
                FROM secondary_demand_order
                    INNER JOIN customer_master ON (`secondary_demand_order`.`customer_master_id` = customer_master.`id`)
                WHERE  `secondary_demand_order`.id = ${params.id} LIMIT 1
            """
            List resultList = sql.rows(query)
            if(resultList && resultList.size() > 0){
                partnerTypeId = resultList.first().pricing_category_id
                customerId = resultList.first().customer_id
            }

            if(partnerTypeId == ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID){
                // Negotiated Customer
                strSql = """
                    SELECT DISTINCT CONCAT(finish_product.id, '-', product_price_product.`total_amount`) AS id, CONCAT(finish_product.name, '[', finish_product.code, ']') AS NAME
                    FROM customer_product_price
                        INNER JOIN `product_price` ON (`customer_product_price`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN `product_price_product` ON (`product_price_product`.`product_price_id` = `product_price`.`id`)
                        INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`)
                        INNER JOIN `distribution_point_warehouse` ON (`distribution_point_warehouse`.`warehouse_id` = ${params.wId})
                        INNER JOIN `sub_warehouse` ON (`sub_warehouse`.`warehouse_id` = `distribution_point_warehouse`.`warehouse_id`)
                        INNER JOIN `distribution_point_stock` ON (`distribution_point_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`
                            AND `distribution_point_stock`.`finish_product_id` = `finish_product`.`id`)
                        INNER JOIN main_product AS map ON map.id = finish_product.main_product_id
                        INNER  JOIN master_product AS mp ON mp.id = finish_product.master_product_id
                    WHERE product_price.`is_active` = TRUE
                        AND `finish_product`.is_active = TRUE
                        AND `customer_master`.`id` = ${customerId}
                        AND `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}
                        AND product_price_product.`pricing_category_id` = ${ApplicationConstants.PRICING_CATEGORY_NEGOTIATED_ID}
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        AND (`distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity`) > 0
                        GROUP BY mp.sequence_number,map.sequence_number, finish_product.sequence_number
                        ORDER BY mp.sequence_number ASC , map.sequence_number ASC , finish_product.sequence_number ASC
                """
            }
            else{
                // Non Negotiated ie DP/TP/MRP Customer
                strSql = """
                    SELECT DISTINCT CONCAT(finish_product.id, '-', product_price_product.`total_amount`) AS id, CONCAT(finish_product.name, '[', finish_product.code, ']') AS NAME
                        FROM `customer_master`
                            INNER JOIN `pricing_category` ON (`pricing_category`.id = `customer_master`.`pricing_category_id`)
                            INNER JOIN `product_price_product` ON (`pricing_category`.`id` = `product_price_product`.`pricing_category_id`)
                            INNER JOIN `product_price` ON (`product_price_product`.`product_price_id` = `product_price`.`id`
                                AND `product_price`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                            INNER JOIN `product_pricing_type` ON (`product_pricing_type`.`id` = `product_price`.`product_pricing_type_id`)
                            INNER JOIN `finish_product` ON (`finish_product`.`id` = `product_price_product`.`finish_product_id`
                                AND `finish_product`.`enterprise_configuration_id` = `customer_master`.`enterprise_configuration_id`)
                            INNER JOIN `customer_territory_sub_area` ON (`customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`)
                            INNER JOIN `territory_sub_area_product_price` ON (`customer_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area_product_price`.`territory_sub_area_id`
                                AND `territory_sub_area_product_price`.`product_price_id` = `product_price`.`id`)
                            INNER JOIN `distribution_point_warehouse` ON (`distribution_point_warehouse`.`warehouse_id` = ${params.wId})
                            INNER JOIN `sub_warehouse` ON (`sub_warehouse`.`warehouse_id` = `distribution_point_warehouse`.`warehouse_id`)
                            INNER JOIN `distribution_point_stock` ON (`distribution_point_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`
                                AND `distribution_point_stock`.`finish_product_id` = `finish_product`.`id`)
                            INNER JOIN main_product AS map ON map.id = finish_product.main_product_id
                            INNER  JOIN master_product AS mp ON mp.id = finish_product.master_product_id
                    WHERE `customer_master`.`id` = ${customerId}
                        AND `finish_product`.is_active = TRUE
                        AND product_price.`is_active` = TRUE
                        AND `product_price`.`product_pricing_type_id` = ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}
                        AND product_price_product.`pricing_category_id` = ${partnerTypeId}
                        AND `customer_territory_sub_area`.`territory_sub_area_id` = (SELECT `secondary_demand_order`.`territory_sub_area_id` FROM `secondary_demand_order`
                            WHERE  `secondary_demand_order`.id = ${params.id})
                        AND DATE(NOW()) >= DATE(`product_price`.`date_effective_from`) AND DATE(NOW()) <= DATE(IFNULL(`product_price`.`date_effective_to`, NOW()))
                        AND (`distribution_point_stock`.`in_quantity` - `distribution_point_stock`.`out_quantity`) > 0
                        GROUP BY mp.sequence_number,map.sequence_number, finish_product.sequence_number
                        ORDER BY mp.sequence_number ASC , map.sequence_number ASC , finish_product.sequence_number ASC
                """
            }
            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public PrimaryDemandOrderDetails readPrimaryDemandOrderDetails(Long id) {
        return PrimaryDemandOrderDetails.read(id)
    }

    @Transactional
    public Integer updatePrimaryDemandOrderDetails(Object object) {
        PrimaryDemandOrderDetails primaryDemandOrderDetails = (PrimaryDemandOrderDetails) object
        if (primaryDemandOrderDetails.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional(readOnly = true)
    public List productOrderDetails(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT finish_product.name, finish_product.code, SUM(primary_demand_order_details.quantity) AS quantity
            FROM primary_demand_order_details
                INNER JOIN primary_demand_order ON (primary_demand_order.id = primary_demand_order_details.primary_demand_order_id)
                INNER JOIN finish_product ON (finish_product.id = primary_demand_order_details.finish_product_id)
            WHERE primary_demand_order_details.finish_product_id = ${params.id}
                AND primary_demand_order.demand_order_status != '${DemandOrderStatus.ORDER_BOOKED}'
                AND primary_demand_order.id IN (${params.orderIds})
            GROUP BY finish_product.id
        """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public Map productBatchAllocationDetails(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `finish_good_stock`.`id`, `finish_good_stock`.`batch_no`,
                    SUM(`finish_good_stock_transaction`.`in_quantity` - `finish_good_stock_transaction`.`out_quantity`) AS `quantity`, DATE_FORMAT(`finish_good_stock`.`date_created`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `batch_date`
                FROM `finish_good_stock`
                    INNER JOIN `sub_warehouse` ON (`finish_good_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                    INNER JOIN `finish_good_stock_transaction` ON (`finish_good_stock_transaction`.`finish_good_stock_id` = `finish_good_stock`.`id`)
                WHERE `sub_warehouse`.`warehouse_id` = ${params.warehouseId}
                    AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                    AND `finish_good_stock`.`finish_product_id` = ${params.id}
                    AND `finish_good_stock`.`in_quantity` > `finish_good_stock`.`out_quantity`
                GROUP BY `finish_good_stock`.`id`, `finish_good_stock`.`batch_no`
                ORDER BY `finish_good_stock`.`date_created`, `finish_good_stock`.`batch_no`
            """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }
            return [objList: objList, count: resultCount]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public void bookBatchWiseOrderChangeInventoryAndOrderStatus(Object params, PrimaryDemandOrder primaryDemandOrder, ApplicationUser applicationUser, Object object) {
        List<Invoice> invoiceList = []
        List<List<SubLedger>> subLedgerList = []

        if (object.containsKey("invoiceList")) {
            invoiceList = (List<Invoice>) object.get("invoiceList")
            invoiceList.each {
                it.save()
            }
        }
        if (object.containsKey("subLedgerList")) {
            subLedgerList = (List<List<SubLedger>>) object.get("subLedgerList")
            subLedgerList.each {
                it.each {
                    it.save()
                }
            }
        }

        for (int k = 0; k < invoiceList.size(); k++) {
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = PrimaryDemandOrderDetails.findAllByPrimaryDemandOrder(primaryDemandOrder)
            if (primaryDemandOrderDetailsList && primaryDemandOrderDetailsList.size() > 0) {
                FinishedProductBooked finishedProductBooked = new FinishedProductBooked()
                finishedProductBooked.primaryDemandOrder = primaryDemandOrder
                finishedProductBooked.invoiceNo = invoiceList[k].code
                finishedProductBooked.dateCreated = new Date()
                finishedProductBooked.userCreated = applicationUser
                finishedProductBooked.save()

                primaryDemandOrderDetailsList.each { p_d ->
                    FinishGoodStock finishGoodStock = finishGoodWarehouseService.findFinishGoodByProduct(p_d.finishProduct)
                    Double rate = finishGoodStock.totalPrice / finishGoodStock.quantity
                    List<FinishGoodBatchStock> finishGoodBatchStocks = finishGoodWarehouseService.batchWiseFinisGoodStockList(p_d.finishProduct)
                    FinishedProductBookedDetails finishedProductBookedDetails = new FinishedProductBookedDetails()
                    finishedProductBookedDetails.finishedProductBooked = finishedProductBooked
                    finishedProductBookedDetails.finishProduct = p_d.finishProduct
                    finishedProductBookedDetails.quantity = p_d.quantity
                    finishedProductBookedDetails.userCreated = applicationUser
                    finishedProductBookedDetails.dateCreated = new Date()
                    if (p_d.primaryDemandOrder.updatedDeliveryDate) {
                        finishedProductBookedDetails.purchageRate = getUpdatedInvoiceRateDetails(p_d)
                    } else {
                        finishedProductBookedDetails.purchageRate = getInvoiceRateDetails(p_d)
                    }
                    finishedProductBookedDetails.amount = finishedProductBookedDetails.purchageRate * finishedProductBookedDetails.quantity
                    finishedProductBookedDetails.save()
                    Float qt = p_d.quantity
                    Double batchRate = 0.0
                    Double total_price = 0.0
                    Double total_quantity = 0.0
                    List batchIdList = []
                    InvoiceDetails invoiceDetails = null

                    for (int i = 0; i < finishGoodBatchStocks.size(); i++) {
                        total_price += finishGoodBatchStocks[i].totalPrice
                        total_quantity += finishGoodBatchStocks[i].quantity
                        batchIdList.add(finishGoodBatchStocks[i].id)
                    }
                    batchRate = total_price / total_quantity                    // batches code
                    List batchesIdList = []
                    params.batchItems.each { key, val ->
                        if (val instanceof Map) {
                            String batchInfo = val.batchesDetails
                            CharSequence cs1 = ","
                            boolean retval = batchInfo.contains(cs1);
                            if (retval) {
                                String[] batches = batchInfo.split(",")
                                for (String batchProductInfo : batches) {
                                    String[] batchProducts = batchProductInfo.split("_")
                                    Long batchId = Long.parseLong(batchProducts[1])
                                    Float batchPqty = Float.parseFloat(batchProducts[2])
                                    if (batchIdList.contains(batchId)) {
                                        FinishGoodBatchStock finishGoodBatchStock = finishGoodWarehouseService.findFinishGoodBatchById(batchId)
                                        if (batchPqty <= finishGoodBatchStock.quantity) {
                                            if (qt != 0) {
                                                qt -= batchPqty
                                                finishGoodBatchStock.totalPrice -= batchPqty * batchRate
                                                finishGoodBatchStock.quantity -= batchPqty
                                                finishGoodBatchStock.userUpdated = applicationUser
                                                finishGoodBatchStock.dateUpdated = new Date()
                                                finishGoodBatchStock.save(flush: true)
                                                invoiceDetails = new InvoiceDetails()
                                                invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                                invoiceDetails.finishProduct = p_d.finishProduct
                                                invoiceDetails.unitPrice = p_d.rate
                                                invoiceDetails.quantity = batchPqty
                                                invoiceDetails.batchNumber = finishGoodBatchStock.batchNo
                                                invoiceDetails.save()
                                                batchesIdList.add(batchId)
                                            }

                                        }
                                    }

                                }
                            } else {
                                if (batchInfo && batchInfo != '') {
                                    String[] batchProducts = batchInfo.split("_");
                                    BigInteger batchId = new BigInteger(batchProducts[1])
                                    Float batchPqty = Float.parseFloat(batchProducts[2])
                                    if (batchIdList.contains(batchId)) {
                                        FinishGoodBatchStock finishGoodBatchStock = finishGoodWarehouseService.findFinishGoodBatchById(batchId)
                                        if (batchPqty <= finishGoodBatchStock.quantity) {
                                            if (qt != 0) {
                                                qt -= batchPqty
                                                finishGoodBatchStock.totalPrice -= batchPqty * batchRate
                                                finishGoodBatchStock.quantity -= batchPqty
                                                finishGoodBatchStock.userUpdated = applicationUser
                                                finishGoodBatchStock.dateUpdated = new Date()
                                                finishGoodBatchStock.save(flush: true)
                                                invoiceDetails = new InvoiceDetails()
                                                invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                                invoiceDetails.finishProduct = p_d.finishProduct
                                                invoiceDetails.unitPrice = p_d.rate
                                                invoiceDetails.quantity = batchPqty
                                                invoiceDetails.batchNumber = finishGoodBatchStock.batchNo
                                                invoiceDetails.save()
                                                batchesIdList.add(batchId)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    //end batches code

                    finishGoodBatchStocks.each {
                        if (!batchesIdList.contains(it.id)) {
                            if (qt != 0) {
                                if (it.quantity <= qt) {
                                    qt -= it.quantity
                                    it.totalPrice -= it.quantity * batchRate
                                    it.quantity -= it.quantity
                                    invoiceDetails = new InvoiceDetails()
                                    invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                    invoiceDetails.finishProduct = p_d.finishProduct
                                    invoiceDetails.unitPrice = p_d.rate
                                    invoiceDetails.quantity = it.quantity
                                    invoiceDetails.batchNumber = it.batchNo
                                    invoiceDetails.save()

                                } else {
                                    Double tempQt = qt
                                    qt -= qt
                                    it.totalPrice -= tempQt * batchRate
                                    it.quantity -= tempQt
                                    invoiceDetails = new InvoiceDetails()
                                    invoiceDetails.invoice = Invoice.read(invoiceList[k].id)
                                    invoiceDetails.finishProduct = p_d.finishProduct
                                    invoiceDetails.unitPrice = p_d.rate
                                    invoiceDetails.quantity = tempQt
                                    invoiceDetails.batchNumber = it.batchNo
                                    invoiceDetails.save()
                                }
                                it.userUpdated = applicationUser
                                it.dateUpdated = new Date()
                                it.save(flush: true)
                            }
                        }
                    }

                    finishGoodStock.quantity -= p_d.quantity
                    finishGoodStock.totalPrice -= p_d.quantity * rate
                    finishGoodStock.userUpdated = applicationUser
                    finishGoodStock.dateUpdated = new Date()
                    finishGoodStock.save(flush: true)
                }
            }

            primaryDemandOrder.demandOrderStatus = DemandOrderStatus.ORDER_BOOKED
            primaryDemandOrder.lastUpdated = new Date()
            primaryDemandOrder.userUpdated = applicationUser
            primaryDemandOrder.save()
        }

    }

    @Transactional(readOnly = true)
    public Map getSecondaryProcessOrderDetails(Action action, Object params) {
        try{
            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
            sql = new Sql(dataSource)
            String strLIMIT = "";
            String offSet = ""
            String filter = ""
            if (params.orderNo) {
                filter = """
                    -- AND `primary_demand_order`.`order_no` = '${params.orderNo}'
                """
            }else if(params.deliveryDateFrom && params.deliveryDateTo){
                filter = """ AND DATE(`secondary_demand_order`.`date_deliver`) BETWEEN STR_TO_DATE('${params.deliveryDateFrom}', '${ApplicationConstants.DATE_FORMAT_DB}') AND STR_TO_DATE('${params.deliveryDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')"""
            }else if (params.secondaryOrderNo) {
                filter = """
                     AND `secondary_demand_order`.`order_no` = '${params.secondaryOrderNo}'
                """
            }
            /*
            if (action.resultPerPage != -1) {
                strLIMIT = """LIMIT ${action.resultPerPage}"""
                offSet = """
                    OFFSET ${action.start}
                """
            } else {
                action.resultPerPage = -1;
            }  */
            String strSql = """
                SELECT `secondary_demand_order`.`id`, `secondary_demand_order`.`order_no`,
                    DATE_FORMAT(`secondary_demand_order`.`date_order`,'${ApplicationConstants.DATE_FORMAT_DB}') AS `order_date`,
                    DATE_FORMAT(`secondary_demand_order`.`date_deliver`,'${ApplicationConstants.DATE_FORMAT_DB}') AS `date_expected_deliver`,
                    `customer_master`.`name` AS `customer_name`, `customer_master`.`code` AS `customer_id`,
                    (   SELECT ROUND(SUM(`secondary_demand_order_details`.`quantity` * `secondary_demand_order_details`.`rate`),2)
                        FROM `secondary_demand_order_details`
                        WHERE `secondary_demand_order_details`.`secondary_demand_order_id` = `secondary_demand_order`.`id`
                    ) AS `order_amount`
                FROM `secondary_demand_order`
                    INNER JOIN `customer_master` ON (`secondary_demand_order`.`customer_master_id` = `customer_master`.`id`)
                WHERE `secondary_demand_order`.`demand_order_status` != '${DemandOrderStatus.DELIVERED}'
                    AND `secondary_demand_order`.`demand_order_status` != '${DemandOrderStatus.REJECTED}'
                    AND (`secondary_demand_order`.`territory_sub_area_id` IN (
                     SELECT `territory_sub_area_id`
                     FROM `distribution_point_territory_sub_area`
                     INNER JOIN `distribution_point_warehouse` ON `distribution_point_warehouse`.`distribution_point_id` = `distribution_point_territory_sub_area`.`distribution_point_id`
                     WHERE `distribution_point_warehouse`.`warehouse_id` = ${params.warehouse})
                     OR `secondary_demand_order`.`user_order_placed_id` = ${applicationUser.id})
                    ${filter}
                    ORDER BY `secondary_demand_order`.`id` ASC
              """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }
            return [objList: objList, count: resultCount]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map itemAvailabilitySecondaryOrderProcess(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT `finish_product`.`id`, `finish_product`.`name`, `finish_product`.`code`,
                SUM(`secondary_demand_order_details`.`quantity`) AS `order_qty` ,
                SUM(IFNULL(`primary_demand_order_details`.`quantity`, 0))  AS `process_qty`,
                IFNULL((
                    SELECT SUM(`distribution_point_stock`.`in_quantity`- `distribution_point_stock`.`out_quantity`)
                    FROM `distribution_point_stock`
                        INNER JOIN `sub_warehouse` ON (`distribution_point_stock`.`sub_warehouse_id` = `sub_warehouse`.`id`)
                    WHERE `sub_warehouse`.`warehouse_id` = ${params.warehouseId}
                        AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                        AND `distribution_point_stock`.`finish_product_id` = `finish_product`.`id`
                        AND `sub_warehouse`.`warehouse_id` IN (SELECT `distribution_point_warehouse`.`warehouse_id`
                                                                   FROM `application_user_distribution_point`
                                                                        INNER JOIN `distribution_point_warehouse` ON (`distribution_point_warehouse`.`distribution_point_id` = `application_user_distribution_point`.`distribution_point_id`)
                                                                   WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUser.id})
                        AND `distribution_point_stock`.`in_quantity` > `distribution_point_stock`.`out_quantity`
                    GROUP BY `distribution_point_stock`.`finish_product_id`), 0
                ) AS `available_qty`
            FROM `secondary_demand_order_details`
                INNER JOIN `secondary_demand_order`
                    ON (`secondary_demand_order`.`id` = `secondary_demand_order_details`.`secondary_demand_order_id`)
                INNER JOIN `finish_product`
                    ON (`finish_product`.`id` = `secondary_demand_order_details`.`finish_product_id`)
                LEFT JOIN `primary_demand_order`
                    ON (`primary_demand_order`.`id`= `secondary_demand_order`.`primary_demand_order_id`)
                LEFT JOIN primary_demand_order_details
                    ON (primary_demand_order_details.`primary_demand_order_id` = secondary_demand_order.`primary_demand_order_id`
                        AND finish_product.id = primary_demand_order_details.finish_product_id)

            WHERE secondary_demand_order.id IN (${params.ids})
            GROUP BY `finish_product`.`id`
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map updateSecondaryDemandOrderDetails(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT DISTINCT secondary_demand_order_details.id, finish_product.name,finish_product.code,
                secondary_demand_order_details.quantity AS order_qty ,`secondary_demand_order_details`.`rate`,
                `secondary_demand_order_details`.`amount`,finish_product.id AS pid,
                primary_demand_order_details.quantity AS qty
            FROM secondary_demand_order_details
                INNER JOIN secondary_demand_order
                    ON secondary_demand_order.id=secondary_demand_order_details.secondary_demand_order_id
                INNER JOIN finish_product
                    ON (finish_product.id = secondary_demand_order_details.finish_product_id)
                LEFT JOIN primary_demand_order_details
                    ON (primary_demand_order_details.`primary_demand_order_id` = secondary_demand_order.`primary_demand_order_id`
                        AND finish_product.id = primary_demand_order_details.finish_product_id)
            WHERE secondary_demand_order.id = ${params.id}
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional
    public SecondaryDemandOrderDetails secondaryDemandOrderDetails(Long id) {
        return SecondaryDemandOrderDetails.read(id)
    }

    @Transactional
    public Integer updateSecondaryDemandOrderDetails(Object object) {
        SecondaryDemandOrderDetails secondaryDemandOrderDetails = (SecondaryDemandOrderDetails) object
        if (secondaryDemandOrderDetails.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer processSecondaryDemandOrder(String id, Object user) {
        ApplicationUser appUser = (ApplicationUser) user
        sql = new Sql(dataSource)
        String strSql = """update secondary_demand_order set demand_order_status='${
            DemandOrderStatus.DELIVERED
        }',date_deliver=now(),last_updated=now(),user_updated_id=${appUser.id}  where id=${id} """
        sql.executeUpdate(strSql)
        return new Integer(1)
    }

    @Transactional
    public checkSecondaryOrderDetailsList(String id, String primaryOrderNO) {
        sql = new Sql(dataSource)
        String strSql = """  SELECT DISTINCT secondary_demand_order_details.id, finish_product.name,finish_product.code,
        secondary_demand_order_details.quantity AS order_qty ,
        (SELECT SUM(`distribution_point_stock_transaction`.`received_quantity`) - SUM(`distribution_point_stock_transaction`.`delivered_quantity`) FROM `distribution_point_stock_transaction`
        WHERE
        `distribution_point_stock_transaction`.`finish_product_id`= secondary_demand_order_details.`finish_product_id` ) AS qty
        FROM
        secondary_demand_order_details
        INNER JOIN
        secondary_demand_order
        ON secondary_demand_order.id=secondary_demand_order_details.secondary_demand_order_id
        INNER JOIN
        finish_product
        ON
        finish_product.id=secondary_demand_order_details.finish_product_id
        INNER JOIN
        primary_demand_order_details
        ON
        primary_demand_order_details.`primary_demand_order_id` = secondary_demand_order.`primary_demand_order_id`
        AND finish_product.id=primary_demand_order_details.finish_product_id
        WHERE secondary_demand_order.id = ${id}
            AND primary_demand_order_details.primary_demand_order_id = (SELECT id FROM primary_demand_order WHERE order_no = '${
            primaryOrderNO
        }')
        GROUP BY finish_product.id"""
        return sql.rows(strSql)
    }

//    @Transactional
//    public void changeStatus(Object params) {
//        sql = new Sql(dataSource)
//        String orderNo = ""
//        String deliveryDate = ""
//        String pendingOrderSql = """AND DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'%Y-%m-%d %T') >= CURRENT_TIMESTAMP"""
//        if (params.orderNo) {
//            orderNo = """AND primary_demand_order.order_no='${params.orderNo}'
//        """
//            pendingOrderSql = ""
//
//        }
//        if (params.deliveryDate) {
//            deliveryDate = """AND  DATE_FORMAT(primary_demand_order.date_expected_deliver,'%d-%m-%Y')='${
//                params.deliveryDate
//            }'"""
//        }
//        if (params?.checked?.toString() == 'true') {
//            if (params.deliveryDate) {
//                deliveryDate = """AND  (DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'%Y-%m-%d %T') <= CURRENT_TIMESTAMP
//                                OR DATE_FORMAT(primary_demand_order.date_expected_deliver,'%d-%m-%Y')='${
//                    params.deliveryDate
//                }')"""
//
//            }
//            if (params.orderNo) {
//                orderNo = """AND (primary_demand_order.order_no='${
//                    params.orderNo
//                }' OR DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'%Y-%m-%d %T') <= CURRENT_TIMESTAMP)"""
//            }
//            pendingOrderSql = ""
//        }
//        String strSql = """UPDATE `primary_demand_order`
//                        INNER JOIN `customer_master` ON `customer_master`.`id` = `primary_demand_order`.`user_order_placed_id`
//                        INNER JOIN `enterprise_configuration` ON `enterprise_configuration`.`id` = `customer_master`.`enterprise_configuration_id`
//                        SET `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.APPROVED}'
//                        WHERE `enterprise_configuration`.`id` = ${params.entId}
//                        AND `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_REVIEW}'
//                        """
//        sql.execute(strSql)
//
//        strSql = """UPDATE `primary_demand_order`
//                    INNER JOIN `customer_master` ON `customer_master`.`id` = `primary_demand_order`.`user_order_placed_id`
//                    INNER JOIN `enterprise_configuration` ON `enterprise_configuration`.`id` = `customer_master`.`enterprise_configuration_id`
//                    SET `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_REVIEW}'
//                    WHERE `enterprise_configuration`.`id` = ${params.entId}
//                    AND `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.APPROVED}'
//                            ${pendingOrderSql}
//                            ${orderNo}
//                            ${deliveryDate}
//                          """
//        sql.execute(strSql)
//    }

    @Transactional
    public Object processPrimaryDemandOrder(Map data) {
        try{
            Invoice primaryInvoice = (Invoice) data.get('primaryInvoice')
            primaryInvoice.save(validate: false, insert: true)
            List<InvoiceDetails> invoiceDetailsList = (List<InvoiceDetails>) data.get('primaryInvoiceDetails')
            invoiceDetailsList.each { invoiceDetails->
                invoiceDetails.save(validate: false, insert: true)
            }

            List<FinishGoodStock> finishGoodStockList = (List<FinishGoodStock>) data.get('finishGoodStockList')
            finishGoodStockList.each { finishGoodStock->
                finishGoodStock.save()
            }
            List<FinishGoodStockTransaction> finishGoodStockTransactionList = (List<FinishGoodStockTransaction>) data.get('finishGoodStockTransactionList')
            finishGoodStockTransactionList.each { finishGoodStockTransaction->
                finishGoodStockTransaction.save(validate: false, insert: true)
            }

            List<QuantityBasedBonus> quantityBasedBonusList = (List<QuantityBasedBonus>) data.get('quantityBasedBonusList')
            quantityBasedBonusList.each { quantityBasedBonus->
                quantityBasedBonus.save(validate: false, insert: true)
            }

            // Bonus Promotion data Save
            List<FinishGoodStock> finishGoodStockBonusPromotionList = data.get("finishGoodStockBonusPromotionList")
            List<FinishGoodStockTransaction> finishGoodStockTransactionBonusPromotionList = data.get("finishGoodStockTransactionBonusPromotionList")
            List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = data.get("adjustBonusPromotionWithInvoiceList")
            List<JournalDetails> journalDetailsListForBonus = data.get("journalDetailsListForBonus")

            finishGoodStockBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            finishGoodStockTransactionBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            adjustBonusPromotionWithInvoiceList.each {
                it.save(validate: false, insert: true)
            }

            /************* Save COA Data  ************/
            Journal journalHead = (Journal) data.get('journalHead')
            journalHead.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) data.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }

            journalDetailsListForBonus.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }

            /****************************************/
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) data.get('primaryDemandOrder')
            primaryDemandOrder.save()

            return primaryInvoice
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Object processSecondaryDemandOrder(Map data) {
        try{
            Invoice secondaryInvoice = (Invoice) data.get('secondaryInvoice')
            secondaryInvoice.save(validate: false, insert: true)
            List<InvoiceDetails> invoiceDetailsList = (List<InvoiceDetails>) data.get('secondaryInvoiceDetails')
            invoiceDetailsList.each { invoiceDetails->
                invoiceDetails.save(validate: false, insert: true)
            }

//            List<SubLedger> subLedgerList = (List<SubLedger>) data.get('subLedgerList')
//            subLedgerList.each { subLedger->
//                subLedger.save(validate: false, insert: true)
//            }

            List<CustomerStock> customerStockList = (List<CustomerStock>) data.get('customerStockList')
            customerStockList.each { customerStock->
                customerStock.save()
            }

            List<CustomerStockTransaction> customerStockTransactionList = (List<CustomerStockTransaction>) data.get('customerStockTransactionList')
            customerStockTransactionList.each { customerStockTransaction->
                customerStockTransaction.save(validate: false, insert: true)
            }

            List<DistributionPointStock> distributionPointStockList = (List<DistributionPointStock>) data.get('distributionPointStockList')
            distributionPointStockList.each { distributionPointStock->
                distributionPointStock.save()
            }

            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) data.get('distributionPointStockTransactionList')
            distributionPointStockTransactionList.each { distributionPointStockTransaction->
                distributionPointStockTransaction.save(validate: false, insert: true)
            }

            List<QuantityBasedBonus> quantityBasedBonusList = (List<QuantityBasedBonus>) data.get('quantityBasedBonusList')
            quantityBasedBonusList.each { quantityBasedBonus->
                quantityBasedBonus.save(validate: false, insert: true)
            }

            // Bonus Promotion data Save
            List<DistributionPointStock> distributionPointStockBonusPromotionList = data.get("distributionPointStockBonusPromotionList")
            List<DistributionPointStockTransaction> distributionPointStockTransactionBonusPromotionList = data.get("distributionPointStockTransactionBonusPromotionList")
            List<AdjustBonusPromotionWithInvoice> adjustBonusPromotionWithInvoiceList = data.get("adjustBonusPromotionWithInvoiceList")
            List<JournalDetails> journalDetailsListForBonus = data.get("journalDetailsListForBonus")

            distributionPointStockBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            distributionPointStockTransactionBonusPromotionList.each {
                it.save(validate: false, insert: true)
            }

            adjustBonusPromotionWithInvoiceList.each {
                it.save(validate: false, insert: true)
            }

            /************* Save COA Data  ************/
            Journal journalHead = (Journal) data.get('journalHead')
            journalHead.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) data.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }

            journalDetailsListForBonus.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
            /****************************************/
            /** **************************************/

            SecondaryDemandOrder secondaryDemandOrder = (SecondaryDemandOrder) data.get('secondaryDemandOrder')
            secondaryDemandOrder.save()
            return secondaryInvoice
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public List<JournalDetails> getJournalDetailsList(double invoiceAmount, double advanceAmount, Journal journalHead, CustomerMaster customerMaster, ApplicationUser applicationUser, ChartOfAccounts accountsReceivable, ChartOfAccounts advanceAccounts, ChartOfAccounts vatCurrentAccount, List<InvoiceDetails> invoiceDetailsList, DistributionPoint factoryDp) {
        try{
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            if (invoiceAmount > advanceAmount && advanceAmount == 0.0) {
                // Full Credit
                // Set Receivable Accounts
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = accountsReceivable
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code  // Factory DP
                journalDetails.debitAmount = invoiceAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.SALES + " [Full Credit] for HO Book from Customer: [" + customerMaster.code + "] " + customerMaster.name
                journalDetailsList.add(journalDetails)
            } else if (invoiceAmount > advanceAmount && advanceAmount != 0.0) {
                // Partial Credit
                // Set Receivable Accounts
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = accountsReceivable
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = invoiceAmount - advanceAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.SALES + " [Partial (Credit)] for HO Book from Customer: [" + customerMaster.code + "] " + customerMaster.name
                journalDetailsList.add(journalDetails)

                // Set Advance Accounts
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = advanceAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = advanceAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.SALES + " [Partial (Advance)] for HO Book from Customer: [" + customerMaster.code + "] " + customerMaster.name
                journalDetailsList.add(journalDetails)

            } else if (invoiceAmount <= advanceAmount) {
                // Full Advance
                // Set Advance Accounts
                journalDetails = new JournalDetails(userCreated: applicationUser, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = advanceAccounts
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = invoiceAmount
                journalDetails.creditAmount = 0.00
                journalDetails.particular = ApplicationConstants.SALES + " Full Advance from Customer: [" + customerMaster.code + "] " + customerMaster.name
                journalDetailsList.add(journalDetails)
            }
            Float vatTotal = 0.00
            invoiceDetailsList.each { invoiceDetails ->
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = invoiceDetails.finishProduct.chartOfAccountHead
                journalDetails.prefixCode = ""
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = (invoiceDetails.unitPrice - invoiceDetails.unitVat) * invoiceDetails.quantity
                journalDetails.particular = ApplicationConstants.SALES + ": for HO Book of" + invoiceDetails.finishProduct.name
                journalDetailsList.add(journalDetails)
                vatTotal += invoiceDetails.unitVat * invoiceDetails.quantity
            }

            if(vatTotal > 0.00){
                journalDetails = new JournalDetails(journal: journalHead, userCreated: applicationUser, isActive: true)
                journalDetails.chartOfAccounts = vatCurrentAccount
                journalDetails.prefixCode = customerMaster.code
                journalDetails.postfixCode = factoryDp.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = vatTotal
                journalDetails.particular = ApplicationConstants.VAT +  " for HO Book"
                journalDetailsList.add(journalDetails)
            }

            return journalDetailsList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public List fetchBonusList(Long productId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                        SELECT `bonus_quantity`,`required_quantity`,`is_multiplexing`
                        FROM `bonus_criteria_setup`
                        WHERE `finish_product_id` = ${productId}
                            AND `is_active` IS TRUE
                        LIMIT 1
                        """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public List fetchStockList(Long productId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                        SELECT `finish_good_stock`.`id` AS fid,
                            (SELECT AVG(`unit_price`)
                            FROM `finish_good_stock_transaction`
                            WHERE `out_quantity` = 0
                                AND `finish_good_stock_id` = fid
                            ) AS rate,
                            (`finish_good_stock`.`in_quantity`-`finish_good_stock`.`out_quantity`) AS quantity
                        FROM `finish_good_stock`
                        INNER JOIN `sub_warehouse` ON `sub_warehouse`.`id` = `finish_good_stock`.`sub_warehouse_id`
                        WHERE `finish_good_stock`.`finish_product_id` = ${productId}
                            AND `finish_good_stock`.`in_quantity`-`finish_good_stock`.`out_quantity` > 0
                            AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.SALABLE_TYPE_INVENTORY_ID}
                        """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public List fetchDpStockList(Long productId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                        SELECT `distribution_point_stock`.`id` AS fid,
                            (SELECT AVG(`unit_price`)
                            FROM `distribution_point_stock_transaction`
                            WHERE `out_quantity` = 0
                                AND `distribution_point_stock_id` = fid
                            ) AS rate,
                            (`distribution_point_stock`.`in_quantity`-`distribution_point_stock`.`out_quantity`) as quantity
                        FROM `distribution_point_stock`
                        INNER JOIN `sub_warehouse` ON `sub_warehouse`.`id` = `distribution_point_stock`.`sub_warehouse_id`
                        WHERE `distribution_point_stock`.`finish_product_id` = ${productId}
                            AND `distribution_point_stock`.`in_quantity`-`distribution_point_stock`.`out_quantity` > 0
                            AND `sub_warehouse`.`sub_warehouse_type_id` = ${ApplicationConstants.BONUS_TYPE_INVENTORY_ID}
                        """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Object checkBonusPromotions(Long customerId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                        SELECT p.id AS promotionId, pp.id AS packageId, ppc.`customer_id` AS customerId, p.`calculation_status` AS calculationStatus
                        FROM promotion p
                        INNER JOIN promotion_package pp
                            ON (p.`id` = pp.`promotion_id` AND pp.`is_active` IS TRUE)
                        INNER JOIN promotion_package_customers ppc
                            ON (pp.`id` = ppc.`promotion_package_id` AND ppc.`is_active` IS TRUE)
                        WHERE p.`is_active` IS TRUE
                        AND (DATE(NOW()) BETWEEN DATE(p.effective_from) AND DATE(p.effective_to))
                        AND ppc.`customer_id` = ${customerId};
                        """
            return sql.firstRow(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
