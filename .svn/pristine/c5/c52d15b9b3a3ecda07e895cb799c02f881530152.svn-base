package com.bits.bdfp.bonus

import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrderDetails
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.warehouse.FinishGoodBatchStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseService
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.inventory.warehouse.FinishedProductBookedDetails
import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class CustomerBonusFinishGoodService extends Service {
    static transactional = false
    DataSource dataSource
    Sql sql
    FinishGoodWarehouseService finishGoodWarehouseService
    @Transactional
    public CustomerBonusFinishGood create(Object object) {
        CustomerBonusFinishGood customerBonusFinishGood = (CustomerBonusFinishGood) object
        if (customerBonusFinishGood.save(false)) {
            return customerBonusFinishGood
        }
        return null
    }

    @Transactional
    public Integer update(Object object) {
        CustomerBonusFinishGood customerBonusFinishGood = (CustomerBonusFinishGood) object
        if (customerBonusFinishGood.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public Integer delete(Object object) {
        CustomerBonusFinishGood customerBonusFinishGood = (CustomerBonusFinishGood) object
        customerBonusFinishGood.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<CustomerBonusFinishGood> objList = CustomerBonusFinishGood.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = CustomerBonusFinishGood.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<CustomerBonusFinishGood> list() {
        return CustomerBonusFinishGood.list()
    }

    @Transactional(readOnly = true)
    public CustomerBonusFinishGood read(Long id) {
        return CustomerBonusFinishGood.read(id)
    }

    @Transactional(readOnly = true)
    public CustomerBonusFinishGood search(String fieldName, String fieldValue) {
        String query = "from CustomerBonusFinishGood as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return CustomerBonusFinishGood.find(query)
    }

    @Transactional(readOnly = true)
    public Map getListForCheckEligibility(Action action, Object params, ApplicationUser applicationUser) {
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
                            SELECT DISTINCT tbl.id,tbl.order_no,
                            tbl.order_date,
                            tbl.deliver_date,
                            tbl.name,tbl.cus_name,tbl.address,
                            tbl.demand_order_status,tbl.code,
                             tbl.amount

                             FROM

                           (SELECT DISTINCT primary_demand_order.id,primary_demand_order.`order_no`,

                            DATE_FORMAT(primary_demand_order.`order_date`,'%d-%m-%Y') AS order_date,
                            DATE_FORMAT(primary_demand_order.`date_expected_deliver`,'%d-%m-%Y') AS deliver_date,
                            customer_master.name,application_user.username AS cus_name,customer_shipping_address.`address`,
                            `primary_demand_order`.`demand_order_status`,customer_master.code,
                             primary_demand_order_details.`amount`,
                             finished_product_booked_details.id AS fid
                            FROM `primary_demand_order`

                            INNER JOIN `primary_demand_order_details`
                            ON primary_demand_order.id=primary_demand_order_details.`primary_demand_order_id`

                            INNER JOIN `customer_master`
                            ON customer_master.id = primary_demand_order_details.`customer_order_for_id`

                            INNER JOIN `application_user`
                            ON   application_user.id= primary_demand_order.`user_order_placed_id`

                            LEFT OUTER JOIN `customer_shipping_address`
                            ON `customer_master`.id = customer_shipping_address.`customer_master_id`

                            INNER JOIN `finished_product_booked`
                            ON finished_product_booked.`primary_demand_order_id` = `primary_demand_order`.`id`
                            INNER JOIN `finished_product_booked_details`
                            ON `finished_product_booked_details`.`finished_product_booked_id` = `finished_product_booked`.`id`



                            WHERE primary_demand_order.`user_order_placed_id`=${applicationUser.id}

                            AND  `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.ORDER_BOOKED}'


                            GROUP BY primary_demand_order.`id`)
                            AS tbl

                           LEFT OUTER JOIN `customer_bonus_finish_good`
                           ON `customer_bonus_finish_good`.`finished_product_booked_details_id` = tbl.fid
                           WHERE customer_bonus_finish_good.`is_confirmed_bonus` IS NULL
                           OR (customer_bonus_finish_good.`is_confirmed_bonus` ='false')

                           ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List checkBonusEligibility( Object params) {
        sql = new Sql(dataSource)

        String strSql = """SELECT  primary_demand_order.id,primary_demand_order.`order_no`,finish_product.id as f_id, finish_product.name,finish_product.code,
                            SUM(primary_demand_order_details.quantity) AS order_qty,
                            tbl.batch_no,tbl.qty,bonus_criteria_setup.id as b_id,
                            (CASE WHEN (SUM(primary_demand_order_details.quantity))>bonus_criteria_setup.`required_quantity` THEN
                            (CASE WHEN (tbl.qty)>1 THEN 'Y' ELSE 'N' END)
                            ELSE 'N' END)AS eligibility,
                            FLOOR((SUM(primary_demand_order_details.quantity))/bonus_criteria_setup.`required_quantity`) AS bonus_qty,
                            bonus_criteria_setup.`required_quantity`
                            FROM
                            primary_demand_order_details
                            INNER JOIN
                            primary_demand_order
                            ON primary_demand_order.id=primary_demand_order_details.primary_demand_order_id
                            INNER JOIN
                            finish_product
                            ON
                            finish_product.id=primary_demand_order_details.finish_product_id
                            LEFT OUTER JOIN(
                            SELECT finish_good_batch_stock.finish_product_id AS id,
                            SUM(finish_good_batch_stock.quantity) AS qty,
                            GROUP_CONCAT(DISTINCT finish_good_batch_stock.batch_no)
                             AS batch_no
                            FROM
                            finish_good_batch_stock
                            WHERE finish_good_batch_stock.quantity > 0
                            GROUP BY finish_good_batch_stock.finish_product_id)
                            AS tbl
                            ON
                            tbl.id=finish_product.id
                            INNER JOIN `bonus_criteria_setup`
                            ON tbl.id=bonus_criteria_setup.`finish_product_id`
                            WHERE

                            primary_demand_order.id IN (${params.ids})
                            GROUP BY primary_demand_order.id,finish_product.id

                          """
        List objList = sql.rows(strSql.toString())
       return objList

    }
    @Transactional(readOnly = true)
    public List checkBonusEligibilityList( PrimaryDemandOrder primaryDemandOrder) {
        sql = new Sql(dataSource)

        String strSql = """SELECT  primary_demand_order.id,primary_demand_order.`order_no`,finish_product.id as f_id, finish_product.name,finish_product.code,
                            SUM(primary_demand_order_details.quantity) AS order_qty,
                            tbl.batch_no,tbl.qty,bonus_criteria_setup.id as b_id,
                            (CASE WHEN (SUM(primary_demand_order_details.quantity))>bonus_criteria_setup.`required_quantity` THEN
                            (CASE WHEN (tbl.qty)>1 THEN 'Y' ELSE 'N' END)
                            ELSE 'N' END)AS eligibility,
                            FLOOR((SUM(primary_demand_order_details.quantity))/bonus_criteria_setup.`required_quantity`) AS bonus_qty,
                            bonus_criteria_setup.`required_quantity`
                            FROM
                            primary_demand_order_details
                            INNER JOIN
                            primary_demand_order
                            ON primary_demand_order.id=primary_demand_order_details.primary_demand_order_id
                            INNER JOIN
                            finish_product
                            ON
                            finish_product.id=primary_demand_order_details.finish_product_id
                            LEFT OUTER JOIN(
                            SELECT finish_good_batch_stock.finish_product_id AS id,
                            SUM(finish_good_batch_stock.quantity) AS qty,
                            GROUP_CONCAT(DISTINCT finish_good_batch_stock.batch_no)
                             AS batch_no
                            FROM
                            finish_good_batch_stock
                            WHERE finish_good_batch_stock.quantity > 0
                            GROUP BY finish_good_batch_stock.finish_product_id)
                            AS tbl
                            ON
                            tbl.id=finish_product.id
                            INNER JOIN `bonus_criteria_setup`
                            ON tbl.id=bonus_criteria_setup.`finish_product_id`
                            WHERE

                            primary_demand_order.id = ${primaryDemandOrder.id}
                            GROUP BY primary_demand_order.id,finish_product.id

                          """
        List objList = sql.rows(strSql.toString())
        return objList

    }
    @Transactional
    public void confirmBonus(PrimaryDemandOrder primaryDemandOrder, ApplicationUser applicationUser) {

        List eligibileList = checkBonusEligibilityList(primaryDemandOrder)
        FinishedProductBooked finishedProductBooked = FinishedProductBooked.findByPrimaryDemandOrder(primaryDemandOrder)
        FinishedProductBookedDetails finishedProductBookedDetails = FinishedProductBookedDetails.findByFinishedProductBooked(finishedProductBooked)
        if (eligibileList && eligibileList.size() > 0) {

            eligibileList.each { p_d ->
                FinishProduct finishProduct = FinishProduct.read(Long.parseLong(p_d.f_id.toString()))
                FinishGoodStock finishGoodStock = finishGoodWarehouseService.findFinishGoodByProduct(finishProduct)
                Double rate = finishGoodStock.totalPrice / finishGoodStock.quantity

                Float qt = p_d.bonus_qty
                Double batchRate = 0.0
                Double total_price = 0.0
                Double total_quantity = 0.0

                List<FinishGoodBatchStock> finishGoodBatchStocks = finishGoodWarehouseService.batchWiseFinisGoodStockList(finishProduct)
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

                        } else {
                            Double tempQt = qt
                            qt -= qt
                            it.totalPrice -= tempQt * batchRate
                            it.quantity -= tempQt
                        }
                        it.userUpdated = applicationUser
                        it.dateUpdated = new Date()
                        it.save(flush: true)

                    }
                }


                finishGoodStock.quantity -= p_d.bonus_qty
                finishGoodStock.totalPrice -= p_d.bonus_qty * rate
                finishGoodStock.userUpdated = applicationUser
                finishGoodStock.dateUpdated = new Date()
                finishGoodStock.save(flush: true)

                CustomerBonusFinishGood customerBonusFinishGood = new CustomerBonusFinishGood()
                customerBonusFinishGood.finishedProductBookedDetails = finishedProductBookedDetails
                customerBonusFinishGood.bonusQuantity = p_d.bonus_qty
                customerBonusFinishGood.bonusCriteriaSetup = BonusCriteriaSetup.read(Long.parseLong(p_d.b_id.toString()))
                customerBonusFinishGood.dateCreated = new Date()
                customerBonusFinishGood.userCreated = applicationUser
                customerBonusFinishGood.isConfirmedBonus = true
                customerBonusFinishGood.save()
            }
        }


    }
}
