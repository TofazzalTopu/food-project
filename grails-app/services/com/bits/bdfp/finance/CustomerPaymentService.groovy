package com.bits.bdfp.finance

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalDetailsService
import com.bits.bdfp.accounts.SubLedger
import com.bits.bdfp.customer.CustomerLevel
import com.bits.bdfp.inventory.demandorder.CustomerDemandOrderPayment
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Action
import com.docu.security.ApplicationUser
import com.sun.xml.internal.bind.v2.util.StackRecorder
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource
import java.text.SimpleDateFormat

/**
 * Created by prianka.adhikary on 9/30/2015.
 */
class CustomerPaymentService {

    static transactional = false
    DataSource dataSource
    Sql sql
    JournalDetailsService journalDetailsService
    @Transactional
    public Integer createPaymentDepositPool(Object object) {
        Map map = (Map) object
//        CustomerPayment customerPayment = map.customerPayment
//        CustomerPayment customerPaymentOld = map.customerPaymentOld
//        List <CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = map.customerPaymentCurrencyDenominationList
        List <SubLedger> subLedgers = map.subLedgers
        if (subLedgers) {
//            customerPayment.save(false)


//            if (customerPaymentCurrencyDenominationList && customerPaymentCurrencyDenominationList.size()>0){
//                customerPaymentCurrencyDenominationList.each{
//                    it.save(false)
//                }
//            }
            if(subLedgers && subLedgers.size()>0){
                subLedgers.each{
                    it.save(false)
                }
            }
//            customerPaymentOld.isDeposited = true
//            customerPaymentOld.save()
            return new Integer(1)
        }else{
            return new Integer(0)
        }
    }

    @Transactional
    public boolean createPayment(Object object) {
        Map map = (Map) object
        CustomerPayment customerPayment = map.customerPayment
        SecurityDeposit securityDeposit = map.securityDeposit
        CustomerAccount customerAccount = map.customerAccount
        List<CustomerPaymentInvoice> customerPaymentInvoiceList = map.customerPaymentInvoiceList
        List <CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = map.customerPaymentCurrencyDenominationList
//        List <SubLedger> subLedgers = map.subLedgers
        List<Invoice> invoiceList = map.invoiceList
        if (customerPayment) {
            customerPayment.save(false)


            if (securityDeposit) {
                securityDeposit.save(validate: false, insert: true)
            }

            if (customerAccount) {
                customerAccount.save(false)
            }

            if (customerPaymentInvoiceList && customerPaymentInvoiceList.size() > 0) {
                customerPaymentInvoiceList.each {
                    it.save(validate: false, insert: true)
                }
            }
            if (customerPaymentCurrencyDenominationList && customerPaymentCurrencyDenominationList.size()>0){
                customerPaymentCurrencyDenominationList.each{
                    it.save(false)
                }
            }
//            if(subLedgers && subLedgers.size()>0){
//                subLedgers.each{
//                    it.save(false)
//                }
//            }
            if(invoiceList && invoiceList.size()>0){
                invoiceList.each {
                    it.save(validate: false, insert: true)
                }
            }
            Journal journal = (Journal) map.get('journal')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    public Map getListForCashDepositPaymentsTotal(Action action, Object params){
        sql = new Sql(dataSource)
        String transactionDate = ""
        String poolName = ""

        if(params.transactionDate){
            transactionDate = """ AND DATE_FORMAT(customer_payment.date_transaction,'%d-%m-%Y') ='${params.transactionDate}' """
        }
        if(params.name){
            poolName = """ AND cash_pool.name = '${params.name}' """
        }

        String query = """
            SELECT
              customer_payment.id,
              DATE_FORMAT(
                customer_payment.date_transaction,
                '%d-%m-%Y'
              ) AS date_transaction,
              cash_pool.name,
              customer_payment.mr_no,
              (SELECT SUM(sub_ledger.debit) - SUM(sub_ledger.credit) FROM sub_ledger WHERE sub_ledger.acc_code = cash_pool.account_no) AS "amount"
            FROM
              customer_payment
              JOIN cash_pool
                ON cash_pool.id = customer_payment.cash_pool_id
            WHERE customer_payment.payment_mode = 'cash'
              AND customer_payment.is_deposited = FALSE
              AND (SELECT SUM(sub_ledger.debit) - SUM(sub_ledger.credit) FROM sub_ledger WHERE sub_ledger.acc_code = cash_pool.account_no) > 0
              ${transactionDate}
              ${poolName}
            GROUP BY cash_pool.name
        """

        List objList = sql.rows(query.toString())
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }
        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getListForCashDepositPayments(Action action, Object params){
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
        String transactionDate = ""
        String poolName = ""
        if(params.transactionDate){
            transactionDate = """
                                AND DATE_FORMAT(`customer_payment`.`date_transaction`,'%d-%m-%Y') ='${params.transactionDate}'"""
        }
        if(params.name){
            poolName = """
                        AND `cash_pool`.`name` = '${params.name}'
                        """
        }
        String strSql = """
                            SELECT
                            `customer_payment`.`id`,
                            DATE_FORMAT(`customer_payment`.`date_transaction`,'%d-%m-%Y') AS `date_transaction`,
                            `cash_pool`.`name`,
                            `customer_payment`.`mr_no`,
                            `customer_payment`.`amount`,
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 1),0) AS '1000',
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 2),0) AS '500',
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 3),0) AS '100',IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 4),0) AS '50',IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 5),0) AS '20',
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 6),0) AS '10',IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 7),0) AS '5',IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 8),0) AS '2',
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 9),0) AS '1',
                            IFNULL((SELECT `customer_payment_currency_denomination`.`quantity` FROM `customer_payment_currency_denomination`
                            WHERE `customer_payment_currency_denomination`.`customer_payment_id` = `customer_payment`.`id`
                            AND `customer_payment_currency_denomination`.`currency_demonstration_id` = 10),0) AS '0.5'
                            FROM `customer_payment`
                            JOIN `cash_pool` ON `cash_pool`.`id` = `customer_payment`.`cash_pool_id`
                            WHERE `customer_payment`.`payment_mode` = 'cash' AND `customer_payment`.`is_deposited` = FALSE
                            ${transactionDate}
                            ${poolName}
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
    public Map getListForSecurityDepositPayments(Action action, Object params){
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
            SELECT sd.customer_master_id AS "customerId",
                   cm.name AS "customerName",
                   ROUND(sd.deposited,2) AS "deposited",
                   IFNULL(ROUND(sd.withdrawn,2),0) AS "withdrawn",
                   sd.date_transaction AS "dateTransaction"

            FROM security_deposit sd
            INNER JOIN customer_master cm
                    ON cm.id = sd.customer_master_id

            WHERE sd.customer_master_id = ${params.customerId}

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
    public Map getListForCurrencyDenomination(Action action, Object params, ApplicationUser applicationUser) {
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
                          SELECT id,VALUE AS amount
                             FROM `currency_demonstration`
                             WHERE is_active IS TRUE


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
    public Map listMRNo(Action action, Object params) {
        try{
            sql = new Sql(dataSource)
            String strLIMIT = "";
            String offSet = ""
            String orderNo = ""
            String date = ""

            if (params.slipNo) {
                orderNo = """ AND `customer_payment`.`mr_no` LIKE '${params.slipNo}%'
            """
            }
            if (params.dateFrom && params.dateTo) {
                date = """ AND DATE(customer_payment.`date_transaction`) BETWEEN  STR_TO_DATE('${params.dateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.dateTo}','%d-%m-%Y')
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
                SELECT `customer_payment`.`id`,`customer_payment`.`mr_no`,`customer_master`.`name`,
                    `customer_payment`.trans_no, `customer_payment`.invoices,
                    DATE_FORMAT(`customer_payment`.`date_transaction`, '%d-%m-%Y') AS date_transaction,
                    `customer_payment`.`amount`
                FROM `customer_payment`
                    INNER JOIN `customer_master` ON (`customer_master`.`id` = `customer_payment`.`customer_master_id`)
                WHERE `customer_master`.`enterprise_configuration_id` = ${params.entId}
                    AND `customer_payment`.`user_created_id` = ${params.userId}
                    ${orderNo}
                    ${date}
                ORDER BY `customer_payment`.`mr_no`
                ${strLIMIT}
                ${offSet}
            """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }

            /* Calculate Count Data */
            strSql = """
                SELECT COUNT(*) AS resultCount
                FROM `customer_payment`
                    INNER JOIN `customer_master` ON (`customer_master`.`id` = `customer_payment`.`customer_master_id`)
                WHERE `customer_master`.`enterprise_configuration_id` = ${params.entId}
                    AND `customer_payment`.`user_created_id` = ${params.userId}
                    ${orderNo}
                    ${date}
            """
            List objCountList = sql.rows(strSql)
            if (objCountList && objCountList.size() > 0) {
                resultCount = objCountList.first().resultCount
            }

            return [objList: objList, count: resultCount]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listMRNoForAutoComplete(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        String orderNo = ""
        String date = ""

        if (params.searchKey) {
            orderNo = """ AND `customer_payment`.`mr_no` LIKE '%${params.searchKey}%'
        """
        }
        String strSql = """SELECT `customer_payment`.`id`,`customer_payment`.`mr_no`,`customer_master`.`name`
                            -- ,`customer_payment`.trans_no, `customer_payment`.invoices,
                           -- DATE_FORMAT(`customer_payment`.`date_transaction`, '%d-%m-%Y') AS date_transaction,
                           -- `customer_payment`.`amount`
                        FROM `customer_payment`
                        INNER JOIN `customer_master` ON `customer_master`.`id` = `customer_payment`.`customer_master_id`
                        LEFT JOIN `customer_demand_order_payment` ON `customer_demand_order_payment`.`customer_master_id` = `customer_master`.`id`
                        LEFT JOIN `finished_product_booked` ON `customer_demand_order_payment`.`finished_product_booked_id` = `finished_product_booked`.`id`
                        WHERE `customer_master`.`enterprise_configuration_id` = ${params.enterpriseConfiguration}
                            AND `customer_payment`.`user_created_id` = ${params.userId}
                            ${orderNo}
                            ${date}
                        GROUP BY `customer_payment`.`mr_no`
                        ORDER BY `customer_payment`.`mr_no`
                            ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql.toString())
        return objList
    }

    @Transactional(readOnly = true)
    public CustomerDemandOrderPayment fetchDemandPayment(Long goodId, Long customerId, String isSecondary) {
        if(isSecondary == 'null'){
            return null;
        }
        sql = new Sql(dataSource)
        String and = """"""
        if(isSecondary != 'false')
            and = """ AND `distribution_point_stock_transaction_id` = ${Long.parseLong(isSecondary)}"""
        else
            and = """AND `finished_product_booked_id` = ${goodId}"""
        String strSql = """SELECT id
                        FROM `customer_demand_order_payment`
                        WHERE `customer_master_id` = ${customerId}
                        ${and}
                          """

        List list = sql.rows(strSql.toString())
        if(list && list.size() > 0){
             return CustomerDemandOrderPayment.read(list[0].id)
        } else {
            return null
        }
    }

    @Transactional(readOnly = true)
    public CustomerAccount fetchCustomerAccount(Long customerId) {
        sql = new Sql(dataSource)
        String strSql = """SELECT id
                        FROM `customer_account`
                        WHERE `customer_master_id` = ${customerId}
                          """

        List list = sql.rows(strSql.toString())
        CustomerAccount customerAccount = CustomerAccount.read(list[0].id)
        return customerAccount
    }

    @Transactional(readOnly = true)
    public List listAccount(Long entId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `bank_account`.`id`, CONCAT(`bank_account`.`account_no`, ' [', bank.name, ' (', `bank_branch`.`name`, ')', ']') AS account_no
                FROM `bank_account`
                    INNER JOIN `bank` ON (`bank`.`id` = `bank_account`.`bank_id`)
                    INNER JOIN `bank_branch` ON (`bank_account`.`bank_id` = `bank_branch`.`id`)
                WHERE `bank`.`enterprise_configuration_id` = ${entId}
            """
            return sql.rows(strSql)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listCashPool(Long dpId) {
        sql = new Sql(dataSource)
        String strSql = """SELECT `cash_pool`.id,`cash_pool`.`name`
                        FROM `cash_pool`
                        WHERE `distribution_point_id` = ${dpId}
                          """

        List list = sql.rows(strSql.toString())
        return list
    }

    @Transactional(readOnly = true)
    public List listDepositPool(Long entId) {
        sql = new Sql(dataSource)
        String strSql = """SELECT `deposit_pool`.id,`deposit_pool`.`name`
                        FROM `deposit_pool`
                        INNER JOIN `distribution_point`
                            ON `distribution_point`.id = `deposit_pool`.`distribution_point_id`
                        WHERE `distribution_point`.`is_factory` IS TRUE
                            AND `deposit_pool`.`enterprise_configuration_id` = ${entId}
                          """

        List list = sql.rows(strSql.toString())
        return list
    }

    @Transactional(readOnly = true)
    public List isFactory(Long applicationUserId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `is_factory`
                FROM `distribution_point`
                    INNER JOIN `application_user_distribution_point`
                        ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUserId}
            """
            List list = sql.rows(strSql)
            return list
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List fetchCustomerByGeo(Object params) {

        sql = new Sql(dataSource)
        String cond = ""
        List list = listDpForUser(params)
        if(list[0].is_factory == true){
            cond = """
                    AND `customer_master`.`category_id` != 3
                    AND `customer_master`.`category_id` != 7
                    """
        }else{
            cond = """ AND `customer_master`.`category_id` = 3"""
        }
        if (params.searchKey) {
            cond = """ AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${
                params.searchKey
            }%')"""
        }
        String strSql = """
                            SELECT `customer_master`.`name`,`customer_master`.id,`customer_master`.code,
                                `customer_category`.`name` AS category,master_type.`name` AS status,'' AS geo_location,
                                IFNULL(`customer_master`.`present_address`, '') AS `present_address`
                            FROM `customer_master`
                            INNER JOIN `master_type`
                                ON `master_type`.`id` = `customer_master`.`master_type_id`
                            INNER JOIN `customer_category`
                                ON `customer_category`.id = `customer_master`.`category_id`
                            INNER JOIN `customer_territory_sub_area`
                                ON `customer_master`.id = `customer_territory_sub_area`.`customer_master_id`
                            INNER JOIN `distribution_point_territory_sub_area`
                                ON `customer_territory_sub_area`.`territory_sub_area_id` = `distribution_point_territory_sub_area`.`territory_sub_area_id`
                            WHERE `distribution_point_territory_sub_area`.`distribution_point_id` = ${list[0].id}
                                ${cond}
                            GROUP BY `customer_master`.`id`
                            ORDER BY `customer_master`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listDpForUser(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT distribution_point.`id`,`is_factory`
                            FROM `distribution_point`
                            INNER JOIN `application_user_distribution_point`
                                ON `application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`
                            WHERE `enterprise_configuration_id` = ${params.entId}
                                AND `application_user_distribution_point`.`application_user_id` = ${params.userId}
                            GROUP BY distribution_point.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List getTerritoryListByEnterprise(Long enterpriseId) {
        sql = new Sql(dataSource)
        String strSql = """SELECT tc.id, tc.name
                           FROM territory_configuration tc
                           WHERE tc.enterprise_configuration_id = ${enterpriseId}
                           ORDER BY tc.name ASC
                          """

        List list = sql.rows(strSql.toString())
        return list
    }

    @Transactional(readOnly = true)
    public List getDpListByTerritory(Long territoryId) {
        sql = new Sql(dataSource)
        String strSql = """SELECT dp.id, dp.name
                           FROM distribution_point dp
                           INNER JOIN distribution_point_territory_sub_area dpt
                                   ON dp.id = dpt.distribution_point_id
                           INNER JOIN territory_sub_area tsa
                                   ON tsa.id = dpt.territory_sub_area_id
                           WHERE dp.enterprise_configuration_id = 1 AND tsa.territory_configuration_id = ${territoryId}
                           GROUP BY dp.id
                           ORDER BY dp.name ASC
                          """

        List list = sql.rows(strSql.toString())
        return list
    }


    @Transactional(readOnly = true)
    public List getDpListByTerritoryAndEnterprise(Object params) {
        try {
            sql = new Sql(dataSource)
            String strSql = """SELECT dp.id, dp.name
                           FROM distribution_point dp
                           INNER JOIN distribution_point_territory_sub_area dpt
                                   ON dp.id = dpt.distribution_point_id
                           INNER JOIN territory_sub_area tsa
                                   ON tsa.id = dpt.territory_sub_area_id
                           WHERE dp.enterprise_configuration_id = ${params.enterpriseConfiguration} AND tsa.territory_configuration_id = ${params.territoryId}
                           GROUP BY dp.id
                           ORDER BY dp.name ASC
                          """

            List list = sql.rows(strSql.toString())
            return list
        }
        catch(Exception Ex)
        {
            log.error(Ex.message)
            throw new RuntimeException(Ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Object getDpDefaultCustomer(Long dpId, String date) {
        sql = new Sql(dataSource)

        String strSql = """SELECT dpw.default_customer_id, cm.name, cm.code, cm.legacy_id, dp.name, dp.code AS dpCode
                           FROM distribution_point_warehouse dpw
                           INNER JOIN distribution_point dp
                                   ON dp.id = dpw.distribution_point_id
                           INNER JOIN customer_master cm
                                   ON cm.id = dpw.default_customer_id
                           WHERE dpw.distribution_point_id = ${dpId}
                          """

        Object obj = sql.firstRow(strSql.toString())
        return obj
    }


    @Transactional(readOnly = true)
    public Object getDpDefaultCustomerWithDepositBalance(Long dpId, String date) {
        sql = new Sql(dataSource)

        String dateWithdrawDeposit=""
        String dateDeposit=""
        String dateAdjustDeposit=""
        String dateInterestDeposit=""
        if(date){
            dateWithdrawDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateDeposit=""" where DATE(date_transaction) <= STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateAdjustDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateInterestDeposit=""" WHERE DATE(transaction_date)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
        }
        String strSql = """  SELECT dpw.default_customer_id, cm.name as customerName, cm.code, cm.legacy_id, dp.name as dpName, dp.code AS dpCode
                            ,ROUND(SUM(IFNULL(sd.deposited,0)-IFNULL(wsd.wdAmount,0)-IFNULL(asdi.amount_adjusted,0)+IFNULL(sdi.interest_amount,0)),2) totalAvailableSd
                            FROM distribution_point_warehouse dpw
                            INNER JOIN distribution_point dp ON dp.id = dpw.distribution_point_id
                            INNER JOIN customer_master cm ON cm.id = dpw.default_customer_id

                            LEFT JOIN (SELECT SUM(deposited) AS deposited, `customer_master_id`, `date_created` FROM `security_deposit` ${dateDeposit} GROUP BY `customer_master_id` ) AS sd ON cm.id= sd.customer_master_id
                            LEFT JOIN (SELECT SUM(IFNULL(withdrawal_amount,0)+IFNULL(`force_withdrawal_amount`,0)) AS wdAmount, customer_master_id, date_created FROM `withdraw_security_deposit` ${dateWithdrawDeposit} GROUP BY customer_master_id) wsd ON cm.id=wsd.customer_master_id
                            LEFT JOIN (SELECT SUM(amount_adjusted) AS amount_adjusted,customer_master_id, date_created FROM `adjust_security_deposit_with_invoice` ${dateAdjustDeposit} GROUP BY customer_master_id) asdi ON cm.id=asdi.customer_master_id
                            LEFT JOIN (SELECT SUM(interest_amount) AS interest_amount ,customer_master_id, transaction_date FROM  `security_deposit_interest`  ${dateInterestDeposit} GROUP BY customer_master_id) sdi ON cm.id=sdi.customer_master_id

                            WHERE dpw.distribution_point_id = ${dpId}
                          """

        Object obj = sql.firstRow(strSql.toString())
        return obj
    }

    @Transactional(readOnly = true)
    public Map getDpDefaultCustomerWithSecurityDepositBalance(String dpId, String toDate) {
        try{
            String defaultCustomerId = ""
            String defaultCustomerName = ""
            String defaultCustomerCode = ""
            String defaultCustomerLegacyId = ""
            Float defaultCustomerSD_Balance = 0.00
            sql = new Sql(dataSource)
            String strSql = """
                SELECT DISTINCT dpw.default_customer_id, cm.name, cm.code, cm.legacy_id
                FROM distribution_point_warehouse dpw
                    INNER JOIN distribution_point dp ON dp.id = dpw.distribution_point_id
                    INNER JOIN customer_master cm ON cm.id = dpw.default_customer_id
                WHERE dpw.distribution_point_id = ${dpId}
                LIMIT 1
            """

            List result = sql.rows(strSql)
            if(result && result.size() > 0){
                defaultCustomerId = result.first().default_customer_id
                defaultCustomerName = result.first().name
                defaultCustomerCode = result.first().code
                defaultCustomerLegacyId = result.first().legacy_id
            }
            if(defaultCustomerCode != ""){
                ChartOfAccountsMapping chartOfAccountsMappingSD = ChartOfAccountsMapping.findByCoaType(COAType.SECURITY_DEPOSIT)
                if(chartOfAccountsMappingSD){
                    defaultCustomerSD_Balance = journalDetailsService.getCustomerSDBalance(chartOfAccountsMappingSD.chartOfAccounts, defaultCustomerCode, '', toDate)
                }

            }

            return [defaultCustomerId: defaultCustomerId, defaultCustomerName: defaultCustomerName, defaultCustomerCode: defaultCustomerCode, defaultCustomerLegacyId: defaultCustomerLegacyId, defaultCustomerSD_Balance: defaultCustomerSD_Balance]
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }




    @Transactional(readOnly = true)
    public Object getOtherCustomersSd(Long cId, String date) {
        sql = new Sql(dataSource)

        String transactionDate = """"""
        if(date){
            transactionDate = """ AND STR_TO_DATE(DATE_FORMAT(sd.date_transaction,'%d-%m-%Y'),'%d-%m-%Y') <= STR_TO_DATE('${date}','%d-%m-%Y') """
        }

        String strSql = """SELECT cm.name, cm.code, cm.legacy_id, IFNULL(SUM(sd.deposited - IFNULL(sd.withdrawn,0)),0) securityDeposit
                           FROM customer_master cm
                           INNER JOIN security_deposit sd
                                   ON sd.customer_master_id = cm.id
                           WHERE cm.id = ${cId}
                                 ${transactionDate}
                          """

        Object obj = sql.firstRow(strSql.toString())
        return obj
    }
    @Transactional(readOnly = true)
    public Map getDpDefaultCustomersSdList(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""

        String transactionDate = """"""
        if(params.date){
            transactionDate = """ AND DATE(sd.date_transaction) >= STR_TO_DATE('${params.date}','%d-%m-%Y') """
        }

        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """SELECT sd.id, sd.customer_master_id, cm.name, sd.deposited, IFNULL(sd.withdrawn,0) withdrawn, sd.date_transaction
                           FROM security_deposit sd
                           INNER JOIN customer_master cm
                                   ON cm.id = sd.customer_master_id
                           WHERE sd.customer_master_id = ${params.customerId}
                                 ${transactionDate}
                           ORDER BY sd.id ASC
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
    public List getDpDefaultCustomersTp(Long customerId, String date) {
        sql = new Sql(dataSource)
        String transactionDate = ""
        String dateWithdrawDeposit=""
        String dateDeposit=""
        String dateAdjustDeposit=""
        String dateInterestDeposit=""
        String dateJournal=""
        if(date){
           // transactionDate = """ AND DATE(sd.date_transaction) <= STR_TO_DATE('${date}','%d-%m-%Y') """

//            transactionDate="""AND DATE(sd.date_transaction) <= STR_TO_DATE('${date}','%d-%m-%Y')
//            AND DATE(asdi.date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')
//            AND DATE(sdi.transaction_date)<=STR_TO_DATE('${date}','%d-%m-%Y')
//            AND DATE(wsd.date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""

            dateWithdrawDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateDeposit=""" WHERE DATE(date_transaction) <= STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateAdjustDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateInterestDeposit=""" WHERE DATE(transaction_date)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateJournal=""" and DATE(journal.date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
        }

//        String strSql = """SELECT ctsa.customer_master_id, cm.name AS customer_name,
//                                  (SELECT SUM(deposited - IFNULL(withdrawn,0)) FROM security_deposit
//                                    WHERE customer_master_id = ctsa.customer_master_id
//                                  )AS amount,
//                                  IFNULL((SELECT  (SUM(jd.debit_amount) - SUM(jd.credit_amount)) receivable
//                                FROM chart_of_accounts_mapping coam
//                                INNER JOIN chart_of_accounts coa
//                                    ON coa.id = coam.chart_of_accounts_id
//                                INNER JOIN journal_details jd
//                                    ON coa.id = jd.chart_of_accounts_id
//                                WHERE coam.coa_type = 'ACCOUNTS_RECEIVABLE'
//                                    AND jd.prefix_code = cm.code),0) receivableAmount
//
//                           FROM customer_territory_sub_area ctsa
//                           INNER JOIN customer_master cm
//                                   ON cm.id = ctsa.customer_master_id
//                           INNER JOIN customer_category cc
//                                   ON cc.id = cm.category_id
//                           INNER JOIN pricing_category pc
//                                   ON (pc.id = cm.pricing_category_id)
//                           INNER JOIN security_deposit sd
//                                   ON sd.customer_master_id = cm.id
//
//                           WHERE ctsa.territory_sub_area_id IN(
//                                 SELECT territory_sub_area_id
//                                 FROM customer_territory_sub_area
//                                 WHERE customer_master_id = ${customerId})
//
//                                 AND ctsa.customer_master_id != ${customerId}
//                                 AND cc.name = 'Sales Man'
//                                 AND pc.short_name = 'TP'
//                                 ${transactionDate}
//
//                            GROUP BY ctsa.customer_master_id
//                            ORDER BY cm.name ASC
//                          """



            String strSql = """SELECT ctsa.customer_master_id
            , cm.name AS customer_name
            , cm.code, cm.legacy_id
            ,ROUND(SUM(IFNULL(sd.deposited,0)-IFNULL(wsd.wdAmount,0)-IFNULL(asdi.amount_adjusted,0)+IFNULL(sdi.interest_amount,0)),2) amount
            , ROUND(SUM(receivableAmount),2) receivableAmount

            FROM customer_territory_sub_area ctsa
            INNER JOIN customer_master cm ON cm.id = ctsa.customer_master_id
            INNER JOIN customer_category cc ON cc.id = cm.category_id
            INNER JOIN pricing_category pc ON (pc.id = cm.pricing_category_id)

            LEFT JOIN (SELECT SUM(deposited) AS deposited, `customer_master_id`, `date_transaction` FROM `security_deposit`  ${dateDeposit} GROUP BY `customer_master_id` ) AS sd ON cm.id= sd.customer_master_id
            LEFT JOIN (SELECT SUM(IFNULL(withdrawal_amount,0)+IFNULL(`force_withdrawal_amount`,0)) AS wdAmount, customer_master_id, date_created FROM `withdraw_security_deposit` ${dateWithdrawDeposit} GROUP BY customer_master_id) wsd ON cm.id=wsd.customer_master_id
            LEFT JOIN (SELECT SUM(amount_adjusted) AS amount_adjusted,customer_master_id, date_created FROM `adjust_security_deposit_with_invoice` ${dateAdjustDeposit} GROUP BY customer_master_id) asdi ON cm.id=asdi.customer_master_id
            LEFT JOIN (SELECT SUM(interest_amount) AS interest_amount ,customer_master_id, transaction_date FROM  `security_deposit_interest` ${dateInterestDeposit} GROUP BY customer_master_id) sdi ON cm.id=sdi.customer_master_id
            LEFT JOIN (SELECT SUM(IFNULL(journal_details.`debit_amount`,0)-IFNULL(journal_details.`credit_amount`,0)) AS receivableAmount, journal_details.`prefix_code`, journal.`date_created`, journal.`id`  FROM journal_details INNER JOIN journal ON journal.id=journal_details.journal_id WHERE journal.is_active=TRUE ${dateJournal} GROUP BY journal_details.`prefix_code`) jd ON cm.`code`=jd.`prefix_code`

            WHERE ctsa.territory_sub_area_id IN(
                                 SELECT territory_sub_area_id
                                 FROM customer_territory_sub_area
                                WHERE customer_master_id = ${customerId})

            AND ctsa.customer_master_id != ${customerId}
            AND cc.name = 'Sales Man'
            AND pc.short_name = 'TP'

            GROUP BY ctsa.customer_master_id
            ORDER BY cm.name ASC"""



//        String strSql = """SELECT ctsa.customer_master_id, cm.name AS customer_name, cm.code, cm.legacy_id,
//                                  (SELECT SUM(deposited - IFNULL(withdrawn,0)) FROM security_deposit
//                                    WHERE customer_master_id = ctsa.customer_master_id
//                                  )AS amount,
//                                  SUM(IFNULL(jd.debit_amount,0)) - SUM(IFNULL(jd.credit_amount,0)) receivableAmount
//
//                           FROM customer_territory_sub_area ctsa
//                           INNER JOIN customer_master cm
//                                   ON cm.id = ctsa.customer_master_id
//                           INNER JOIN customer_category cc
//                                   ON cc.id = cm.category_id
//                           INNER JOIN pricing_category pc
//                                   ON (pc.id = cm.pricing_category_id)
//                           INNER JOIN security_deposit sd
//                                   ON sd.customer_master_id = cm.id
//                            LEFT JOIN journal_details jd
//                                    ON cm.`code`=jd.`prefix_code`
//                               WHERE ctsa.territory_sub_area_id IN(
//                                 SELECT territory_sub_area_id
//                                 FROM customer_territory_sub_area
//                                 WHERE customer_master_id = ${customerId})
//
//                                 AND ctsa.customer_master_id != ${customerId}
//                                 AND cc.name = 'Sales Man'
//                                 AND pc.short_name = 'TP'
//                                 AND jd.is_active = TRUE
//                                 ${transactionDate}
//
//                            GROUP BY ctsa.customer_master_id
//                            ORDER BY cm.name ASC
//                          """


        List list = (List)sql.rows(strSql.toString())
        return list
    }

    @Transactional(readOnly = true)
    public List getDpDefaultCustomersIc(Long customerId, String quarter) {
        String lastQuarterRange = ""
        String lastQuarter = ""
        String lastDate = ""

        Date dateNow = new Date()
        SimpleDateFormat formatYear = new SimpleDateFormat("YYYY")
        String currentYear = formatYear.format(dateNow)

        if(quarter == '1'){
            lastQuarter = """ STR_TO_DATE('01-10-${Integer.parseInt(currentYear) - 1}','%d-%m-%Y') AND STR_TO_DATE('31-12-${Integer.parseInt(currentYear) - 1}','%d-%m-%Y') """
            lastDate = """ STR_TO_DATE('31-12-${Integer.parseInt(currentYear) - 1}','%d-%m-%Y') """
        }
        else if(quarter == '2'){
            lastQuarter = """ STR_TO_DATE('01-01-${currentYear}','%d-%m-%Y') AND STR_TO_DATE('31-03-${currentYear}','%d-%m-%Y') """
            lastDate = """ STR_TO_DATE('31-03-${currentYear}','%d-%m-%Y') """
        }
        else if(quarter == '3'){
            lastQuarter = """ STR_TO_DATE('01-04-${currentYear}','%d-%m-%Y') AND STR_TO_DATE('30-06-${currentYear}','%d-%m-%Y') """
            lastDate = """ STR_TO_DATE('30-06-${currentYear}','%d-%m-%Y') """
        }
        else if(quarter == '4'){
            lastQuarter = """ STR_TO_DATE('01-07-${currentYear}','%d-%m-%Y') AND STR_TO_DATE('30-09-${currentYear}','%d-%m-%Y') """
            lastDate = """ STR_TO_DATE('30-09-${currentYear}','%d-%m-%Y') """
        }

        if(lastQuarter){
            lastQuarterRange = """ AND (STR_TO_DATE(DATE_FORMAT(transaction_date, '%d-%m-%Y'),'%d-%m-%Y') BETWEEN ${lastQuarter}
                                        OR quarter_name = ${Integer.parseInt(quarter) - 1})
                                 """
        }

        sql = new Sql(dataSource)

        String strSql = """SELECT ctsa.customer_master_id, cm.name AS customer_name, cm.code,
                                (SELECT SUM(deposited - IFNULL(withdrawn,0)) FROM security_deposit
                                    WHERE customer_master_id = ctsa.customer_master_id
                                ) +
                                (SELECT IFNULL(SUM(interest_amount),0) AS interest
                                    FROM security_deposit_interest
                                    WHERE customer_master_id = ctsa.customer_master_id
                                    ${lastQuarterRange}
                                ) AS lastQob

                           FROM customer_territory_sub_area ctsa
                           INNER JOIN customer_master cm
                                   ON cm.id = ctsa.customer_master_id
                           INNER JOIN customer_category cc
                                   ON cc.id = cm.category_id
                           INNER JOIN pricing_category pc
                                   ON (pc.id = cm.pricing_category_id)
                           INNER JOIN security_deposit sd
                                   ON sd.customer_master_id = cm.id

                           WHERE ctsa.territory_sub_area_id IN(
                                 SELECT territory_sub_area_id
                                 FROM customer_territory_sub_area
                                 WHERE customer_master_id = ${customerId})

                                 AND ctsa.customer_master_id != ${customerId}
                                 AND cc.name = 'Sales Man'
                                 AND pc.short_name = 'TP'

                            GROUP BY ctsa.customer_master_id
                            ORDER BY cm.name ASC
                          """

        List list = (List)sql.rows(strSql.toString())
        return list
    }

    public List getCustomerLisByQuarter(Long customerId, String quarter){
        String lastMonth = '0'

        if(quarter == '1'){
            lastMonth = '3'
        }else if(quarter == '2'){
            lastMonth = '6'
        }else if(quarter == '3'){
            lastMonth = '9'
        }else if(quarter == '4'){
            lastMonth = '12'
        }

        String strSql = """SELECT ctsa.customer_master_id
            , cm.name AS customer_name
            , cm.code, cm.legacy_id
            ,ROUND(SUM(IFNULL(sd.deposited,0)-IFNULL(wsd.wdAmount,0)-IFNULL(asdi.amount_adjusted,0)+IFNULL(sdi.interest_amount,0)),2) lastQob
            -- , ROUND(SUM(receivableAmount),2) receivableAmount

            FROM customer_territory_sub_area ctsa
            INNER JOIN customer_master cm ON cm.id = ctsa.customer_master_id
            INNER JOIN customer_category cc ON cc.id = cm.category_id
            INNER JOIN pricing_category pc ON (pc.id = cm.pricing_category_id)

            INNER JOIN (SELECT SUM(deposited) AS deposited, `customer_master_id`, `date_transaction` FROM `security_deposit`   GROUP BY `customer_master_id` ) AS sd ON (cm.id= sd.customer_master_id
                AND YEAR(sd.date_transaction) <= YEAR(NOW())
                AND CASE WHEN YEAR(sd.date_transaction) = YEAR(NOW())
                    THEN MONTH(sd.date_transaction) <= ${lastMonth}
                    ELSE TRUE
                END
            )
            LEFT JOIN (SELECT SUM(IFNULL(withdrawal_amount,0)+IFNULL(`force_withdrawal_amount`,0)) AS wdAmount, customer_master_id, date_created FROM `withdraw_security_deposit` GROUP BY customer_master_id) wsd ON cm.id=wsd.customer_master_id
            LEFT JOIN (SELECT SUM(amount_adjusted) AS amount_adjusted,customer_master_id, date_created FROM `adjust_security_deposit_with_invoice` GROUP BY customer_master_id) asdi ON cm.id=asdi.customer_master_id
            LEFT JOIN (SELECT SUM(interest_amount) AS interest_amount ,customer_master_id, transaction_date FROM  `security_deposit_interest` GROUP BY customer_master_id) sdi ON cm.id=sdi.customer_master_id
            LEFT JOIN (SELECT SUM(IFNULL(journal_details.`debit_amount`,0)-IFNULL(journal_details.`credit_amount`,0)) AS receivableAmount, journal_details.`prefix_code`, journal.`date_created`, journal.`id`  FROM journal_details INNER JOIN journal ON journal.id=journal_details.journal_id WHERE journal.is_active=TRUE GROUP BY journal_details.`prefix_code`) jd ON cm.`code`=jd.`prefix_code`

            WHERE ctsa.territory_sub_area_id IN(
                                 SELECT territory_sub_area_id
                                 FROM customer_territory_sub_area
                                WHERE customer_master_id = ${customerId})

            AND ctsa.customer_master_id != ${customerId}
            AND cc.name = 'Sales Man'
            AND pc.short_name = 'TP'

            GROUP BY ctsa.customer_master_id
            ORDER BY cm.name ASC"""

        sql = new Sql(dataSource)
        List list = sql.rows(strSql.toString())
        return list
    }

    @Transactional
    public Integer updateSecurityDeposit(Object object) {
        try {
            Map map = (Map) object

            SecurityDeposit securityDeposit = map.get("securityDeposit")
            CustomerPayment customerPayment = map.get("customerPayment")
            //List<SubLedger> subLedgers = map.get("subLedgers")

            securityDeposit.save(validate: false, insert: true)

//        List<Journal> listExistJournal = (List<Journal>) map.get('listExistJournal')
//        listExistJournal.each { journal->
//            journal.save(validate: false, insert: true)
//        }


            Journal journal = (Journal) map.get('journalHead')
            journal.save(validate: false, insert: true)

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails ->
                journalDetails.save(validate: false, insert: true)
            }
//        if(securityDeposit){
//            securityDeposit.save(false)
//
//            if (customerPayment) {
//                customerPayment.save(false)
//            }
//
//            if(subLedgers && subLedgers.size()>0){
//                subLedgers.each{
//                    it.save(false)
//                }
//            }
//
            return new Integer(1)
//        }
        }catch(Exception ex){
            log.error(ex.message)
        }
    }

    @Transactional
    public Integer createSecurityDepositInterest(Object object){
        try {
            Map map = (Map) object
            List<SecurityDepositInterest> securityDepositInterestList = map.securityDepositInterestList

            if (securityDepositInterestList && securityDepositInterestList.size() > 0) {
                securityDepositInterestList.each {
                    it.save(false)
                }
                Journal journal = (Journal) map.get('journal')
                journal.save(validate: false, insert: true)

                List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
                journalDetailsList.each { journalDetails ->
                    journalDetails.save(validate: false, insert: true)
                }
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getCalculatedInterestByQuarter(Object object){
        sql = new Sql(dataSource)
        Map map = (Map) object
        String quarter = map.quarter
        String customerId = map.customerId
        String ids
        Double lastQuarterBalance = 0

        Date dateNow = new Date()
        SimpleDateFormat formatYear = new SimpleDateFormat("YYYY")
        String currentYear = formatYear.format(dateNow)

//        List customerList = getDpDefaultCustomersIc(Long.parseLong(customerId),'')
        List customerList = getCustomerLisByQuarter(Long.parseLong(customerId),quarter)
        List customerListCheck = getCustomerLisByQuarter(Long.parseLong(customerId),"${Integer.parseInt(quarter) -1}")

        if(customerList && customerList.size() > 0){
            customerList.each {
                if(ids){
                    ids += ',' + it.customer_master_id
                }else{
                    ids = it.customer_master_id
                }
            }
        }

        String query = """
            SELECT *
            FROM security_deposit_interest
            WHERE quarter_name = '${quarter}'
                AND customer_master_id IN (${ids})
                AND DATE_FORMAT(transaction_date,'%Y') = ${currentYear}
        """

        String queryCheck = """
            SELECT *
            FROM security_deposit_interest
            WHERE quarter_name = '${Integer.parseInt(quarter) -1}'
                AND customer_master_id IN (${ids})
                AND DATE_FORMAT(transaction_date,'%Y') = ${currentYear}
        """

        List list = (List)sql.rows(query.toString())

        List listCheck = (List)sql.rows(queryCheck.toString())

        Map mapReturn = [:]

        if(list && list.size() > 0){
            mapReturn.put("isCalculated",new Integer(1))
        }else{
            mapReturn.put("isCalculated",new Integer(0))
        }

        if(customerListCheck && customerListCheck.size() > 0) {
            if (listCheck && listCheck.size() > 0) {
                mapReturn.put("isLasQuarterCalculated", new Integer(1))
            } else {
                if (quarter == '1') {
                    mapReturn.put("isLasQuarterCalculated", new Integer(1))
                } else {
                    mapReturn.put("isLasQuarterCalculated", new Integer(0))
                }
            }
        }else{
            mapReturn.put("isLasQuarterCalculated", new Integer(1))
        }

//        lastQuarterBalance = getLastQuarterBalance(Long.parseLong(customerId), quarter);

//        mapReturn.put("lastQuarterBalance",lastQuarterBalance)
        mapReturn.put("customerList",customerList)

        return  mapReturn
    }

    public Double getLastQuarterBalance(Long cId, String quarter){
        Double totalAmount = 0

        if(quarter){
            List customerList = getDpDefaultCustomersIc(cId,quarter)

            if(customerList && customerList.size() >0 ){
                customerList.each {
                    totalAmount += it.lastQob
                }
            }
        }

        return totalAmount
    }

    @Transactional(readOnly = true)
    public double getCustomerBalance(Object params) {
        double balance = 0.00

        sql = new Sql(dataSource)
        String strSql = """
            SELECT `balance`
            FROM `customer_account`
            WHERE `customer_master_id` = ${params.customerId}
        """

        List list = sql.rows(strSql)
        if(list && list.size() > 0){
            balance = list.first().balance
        }
        return balance
    }

    @Transactional(readOnly = true)
    public List fetchDp(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT DISTINCT `distribution_point`.`id`,`distribution_point`.`name`,`customer_master`.`name` AS customer
                            FROM `distribution_point`
                            INNER JOIN `distribution_point_warehouse`
                                ON `distribution_point_warehouse`.`distribution_point_id` = `distribution_point`.`id`
                            INNER JOIN `customer_master`
                                ON `customer_master`.id = `distribution_point_warehouse`.`default_customer_id`
                            INNER JOIN `application_user_distribution_point` audp
                                 ON audp.`distribution_point_id` =  `distribution_point`.`id`

                            WHERE `distribution_point`.`is_factory` IS FALSE
                                AND `distribution_point`.`enterprise_configuration_id` = ${params.entId}
                                AND audp.`application_user_id`=(SELECT id FROM `application_user` WHERE id=${params.id})
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List fetchCashPool(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `cash_pool`.`name`,`cash_pool`.`id`,
                                (SELECT IFNULL(SUM(sub_ledger.debit) - SUM(sub_ledger.credit),0)
                                FROM sub_ledger
                                WHERE sub_ledger.acc_code = cash_pool.account_no) AS amount
                            FROM `cash_pool`
                            WHERE `cash_pool`.`distribution_point_id` = ${params.dpId}
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List totalReceivableAmount(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT  SUM(jd.`debit_amount`) AS totalReceivable, SUM(jd.`credit_amount`) AS totalReceived, SUM(jd.`debit_amount`) - SUM(jd.`credit_amount`) AS due_amount
            FROM `journal_details` AS jd
                INNER JOIN chart_of_accounts coa
                    ON coa.`id` = jd.`chart_of_accounts_id`
                INNER JOIN `chart_of_accounts_mapping` coam
                   ON coam.chart_of_accounts_id = coa.id
            WHERE coam.coa_type = '${COAType.ACCOUNTS_RECEIVABLE}'
                AND jd.`is_active` IS TRUE
                AND prefix_code = '${params.cusCode}'
        """
        List objList = sql.rows(strSql)
        return objList
    }
    @Transactional(readOnly = true)
    public List fetchDepositPool(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT `deposit_pool`.`name`,`deposit_pool`.`id`
            FROM `deposit_pool`
            WHERE `enterprise_configuration_id` = ${params.entId}
            ORDER BY `id`
        """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Integer createDepositCashToDepositPool(Object object) {
        try{
        Map map = (Map) object
        DepositCashToDepositPool depositCashToDepositPool = map.depositCashToDepositPool
        List<DepositCashCurrencyDenomination> depositCashCurrencyDenominationList = map.depositCashCurrencyDenominationList
        if (depositCashToDepositPool) {
            depositCashToDepositPool.save(false)
            if(map.depositCashCurrencyDenominationList){
                if (depositCashCurrencyDenominationList && depositCashCurrencyDenominationList.size() > 0) {
                    depositCashCurrencyDenominationList.each {
                        it.save(false)
                    }
                }
            }

            Journal journalHead = (Journal) map.get('journalHead')
            if(journalHead){
                journalHead.save(validate: false, insert: true)
            }

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.get('journalDetailsList')
            journalDetailsList.each { journalDetails->
                journalDetails.save(validate: false, insert: true)
            }
//            List<SubLedger> subLedgers = map.subLedgers
//            subLedgers.each {
//                it.save(false)
//            }
        }
        return new Integer(1)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List fetchCustomerListByDP(Object params, DistributionPoint distributionPoint) {
        String cond = ""
        sql = new Sql(dataSource)
        if(distributionPoint){
            if(distributionPoint.isFactory) {
                cond = """
                    WHERE `customer_master`.`customer_level` = '${CustomerLevel.PRIMARY}'
                        AND `customer_master`.`category_id` != '${ApplicationConstants.CUSTOMER_CATEGORY_BRANCH_ID}'
                """
            } else{
                cond = """
                    INNER JOIN `master_type`
                        ON `master_type`.`id` = `customer_master`.`master_type_id`
                    INNER JOIN `customer_territory_sub_area`
                        ON `customer_master`.id = `customer_territory_sub_area`.`customer_master_id`
                    INNER JOIN `distribution_point_territory_sub_area`
                        ON `customer_territory_sub_area`.`territory_sub_area_id` = `distribution_point_territory_sub_area`.`territory_sub_area_id`
                    WHERE `distribution_point_territory_sub_area`.`distribution_point_id` = ${distributionPoint.id}
                        AND `customer_master`.`customer_level` = '${CustomerLevel.SECONDARY}'
                """
            }

            if (params.searchKey) {
                cond += """
                    AND (`customer_master`.`name` LIKE '%${params.searchKey}%' OR `customer_master`.`code` LIKE '%${params.searchKey}%' OR `customer_master`.`legacy_id` LIKE '%${params.searchKey}%')
                """
            }
            String strSql = """
                SELECT `customer_master`.`name`,`customer_master`.id,`customer_master`.code, `customer_master`.`legacy_id`,
                    `customer_category`.`name` AS category, IFNULL(`customer_master`.`present_address`, '') AS `present_address`
                FROM `customer_master`
                INNER JOIN `customer_category`
                    ON `customer_category`.id = `customer_master`.`category_id`
                ${cond}
                GROUP BY `customer_master`.`id`
                ORDER BY `customer_master`.`id`
            """
            List objList = sql.rows(strSql)
            return objList
        }else{
            return new ArrayList()
        }
    }

    @Transactional(readOnly = true)
    public List getCustomerListByTerritory(Object params){
        String condition = ""

        if(params.key){
            condition = """
                AND (cm.name LIKE '%${params.key}%' OR cm.code LIKE '%${params.key}%' OR cm.legacy_id LIKE '%${params.key}%')
            """
        }
        String query = """
            SELECT cm.id, cm.name, cm.code, cm.legacy_id, cm.category_id, cc.name AS category,
                IFNULL(SUM(sd.deposited - IFNULL(sd.withdrawn,0)),0) securityDeposit,
                (SELECT DISTINCT tc.name FROM territory_sub_area tsa
                    INNER JOIN territory_configuration tc
                            ON tc.id = tsa.territory_configuration_id
                    WHERE tsa.id IN (
                        SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                    )
                ) AS territory_configuration,
                (SELECT GROUP_CONCAT(tsa.geo_location SEPARATOR ', ') AS geoLocation FROM territory_sub_area tsa
                    WHERE tsa.id IN (
                        SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                    )
                ) AS geo

                FROM customer_master cm
                INNER JOIN customer_category cc
                        ON cc.id = cm.category_id
                LEFT JOIN security_deposit sd
                        ON sd.customer_master_id = cm.id
                WHERE LOWER(cc.name) NOT IN ('branch','sales man')
                AND (SELECT DISTINCT tc.id FROM territory_sub_area tsa
                    INNER JOIN territory_configuration tc
                            ON tc.id = tsa.territory_configuration_id
                    WHERE tsa.id IN (
                        SELECT territory_sub_area_id FROM customer_territory_sub_area WHERE customer_master_id = cm.id
                    )
                ) = ${params.territoryId}

                ${condition}
                GROUP BY cm.id
                ORDER BY cm.name
        """
        sql = new Sql(dataSource)
        List objList = sql.rows(query)
        return objList
    }

    @Transactional(readOnly = true)
    public Double getCustomerBalance(){
        //

        return 0.00
    }

    @Transactional(readOnly = true)
    public Map fetchCashPoolBalance(Object params) {
        try{
            sql = new Sql(dataSource)
            Float amountBalance = 0
            Float securityDepositBalance = 0
            String strSql = """
                SELECT SUM(`amountReceived`) AS `amountReceived`, SUM(`securityDepositReceived`) AS `securityDepositReceived`
                    , SUM(`amountPaid`) AS `amountPaid`, SUM(`securityDepositPaid`) AS `securityDepositPaid`
                FROM(
                    SELECT
                        SUM(IFNULL(`amount`, 0)) AS `amountReceived`
                        , SUM(IFNULL(IF(is_security_deposit, amount, 0), 0)) AS `securityDepositReceived`
                        , 0 AS `amountPaid`, 0 AS `securityDepositPaid`
                        , `cash_pool_id`
                    FROM
                        `customer_payment`
                    WHERE `cash_pool_id` = ${params.cashPoolId}
                    GROUP BY `cash_pool_id`
                    UNION ALL
                    SELECT
                        0 AS `amountReceived`, 0 AS `securityDepositReceived`
                        , SUM(IFNULL(`total`, 0)) AS `amountPaid`
                        , SUM(IFNULL( `sd_amount`, 0)) AS `securityDepositPaid`
                        , `cash_pool_id`
                    FROM
                        `deposit_cash_to_deposit_pool`
                    WHERE `cash_pool_id` = ${params.cashPoolId}
                    GROUP BY `cash_pool_id`
                    UNION ALL
                    SELECT
                        SUM(`withdraw_amount`) AS `amountReceived`, 0 AS `securityDepositReceived`
                        , 0 AS `amountPaid`
                        , 0 AS `securityDepositPaid`
                        , `cash_pool_id`
                    FROM
                        `withdraw_cash_from_deposit_pool`
                    WHERE `cash_pool_id` =  ${params.cashPoolId}
                    GROUP BY `cash_pool_id`
                    UNION ALL
                    SELECT
                        0 AS `amountReceived`, 0 AS `securityDepositReceived`
                        , SUM(IFNULL(`expense_amount`, 0)) AS `amountPaid`
                        , 0 AS `securityDepositPaid`
                        , `cash_pool_id`
                    FROM
                        `expense_fromdpcash_pool`
                    WHERE `cash_pool_id` = ${params.cashPoolId}
                    GROUP BY `cash_pool_id`
                ) AS tbl
                GROUP BY `cash_pool_id`
            """
            List objList = sql.rows(strSql)
            if(objList && objList.size()){
                amountBalance = objList.first().amountReceived - objList.first().amountPaid
                securityDepositBalance = objList.first().securityDepositReceived - objList.first().securityDepositPaid
            }
            return [amountBalance: amountBalance, securityDepositBalance: securityDepositBalance]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException()
        }
    }

    @Transactional(readOnly = true)
    public List fetchCashPoolBalanceForCashInHandFromCOA(Object params) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT  SUM(jd.`debit_amount`) - SUM(jd.`credit_amount`) AS amountBalance
                FROM `journal_details` AS jd
                    INNER JOIN chart_of_accounts coa
                        ON coa.`id` = jd.`chart_of_accounts_id`
                    INNER JOIN `chart_of_accounts_mapping` coam
                        ON coam.chart_of_accounts_id = coa.id
                WHERE coam.coa_type = '${COAType.CASH}'
                    AND `journal_details`.`is_active` = TRUE
                    AND postfix_code = ${params.dpId}
            """
            List objList = sql.rows(strSql)
           return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException()
        }
    }

    @Transactional(readOnly = true)
    public List fetchCashPoolBalanceForCashInHandFromCOANonBankVault(Object params) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
             SELECT SUM(amountBalance + withdraw - deposit) AS amountBalance
             FROM(
                SELECT  SUM(jd.`debit_amount`) - SUM(jd.`credit_amount`) AS amountBalance, 0.00 AS deposit, 0.00 AS withdraw
                FROM `journal_details` AS jd
                    INNER JOIN chart_of_accounts coa
                        ON coa.`id` = jd.`chart_of_accounts_id`
                    INNER JOIN `chart_of_accounts_mapping` coam
                        ON coam.chart_of_accounts_id = coa.id
                WHERE coam.coa_type = '${COAType.CASH}'
                    AND `jd`.`is_active` = TRUE
                    AND postfix_code = ${params.dpId}
            UNION ALL
                SELECT 0.00 AS amountBalance, IFNULL(SUM(total), 0) AS deposit, 0.00 AS withdraw FROM deposit_cash_to_deposit_pool
                WHERE deposit_cash_to_deposit_pool.`distribution_point_id` = ${params.dpId}
                     AND `deposit_cash_to_deposit_pool`.`ho_deposit` IS FALSE
                UNION ALL
                SELECT 0.00 AS amountBalance, 0.00 AS deposit, IFNULL(SUM(withdraw_amount),0) AS withdraw FROM withdraw_cash_from_deposit_pool
                WHERE withdraw_cash_from_deposit_pool.`distribution_point_id` = ${params.dpId}
                ) AS tbl
            """
            List objList = sql.rows(strSql)
            return objList
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException()
        }
    }
    @Transactional(readOnly = true)
    public Object fetchCustomerDepositBalance(Long customerId, String date) {
        sql = new Sql(dataSource)

        String dateWithdrawDeposit=""
        String dateDeposit=""
        String dateAdjustDeposit=""
        String dateInterestDeposit=""
        if(date){
            dateWithdrawDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateDeposit=""" where DATE(date_transaction) <= STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateAdjustDeposit=""" WHERE DATE(date_created)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
            dateInterestDeposit=""" WHERE DATE(transaction_date)<=STR_TO_DATE('${date}','%d-%m-%Y')"""
        }
        String strSql = """  SELECT cm.id AS default_customer_id, cm.name, cm.code, cm.legacy_id
                            ,ROUND(SUM(IFNULL(sd.deposited,0)-IFNULL(wsd.wdAmount,0)-IFNULL(asdi.amount_adjusted,0)+IFNULL(sdi.interest_amount,0)),2) totalAvailableSd
                            FROM customer_master cm

                            LEFT JOIN (SELECT SUM(deposited) AS deposited, `customer_master_id`, `date_created` FROM `security_deposit` ${dateDeposit} GROUP BY `customer_master_id` ) AS sd ON cm.id= sd.customer_master_id
                            LEFT JOIN (SELECT SUM(IFNULL(withdrawal_amount,0)+IFNULL(`force_withdrawal_amount`,0)) AS wdAmount, customer_master_id, date_created FROM `withdraw_security_deposit` ${dateWithdrawDeposit} GROUP BY customer_master_id) wsd ON cm.id=wsd.customer_master_id
                            LEFT JOIN (SELECT SUM(amount_adjusted) AS amount_adjusted,customer_master_id, date_created FROM `adjust_security_deposit_with_invoice` ${dateAdjustDeposit} GROUP BY customer_master_id) asdi ON cm.id=asdi.customer_master_id
                            LEFT JOIN (SELECT SUM(interest_amount) AS interest_amount ,customer_master_id, transaction_date FROM  `security_deposit_interest`  ${dateInterestDeposit} GROUP BY customer_master_id) sdi ON cm.id=sdi.customer_master_id

                            WHERE cm.id = ${customerId}
                          """

        Object obj = sql.firstRow(strSql.toString())
        return obj
    }

    @Transactional(readOnly = true)
    public List fetchTransactionNo(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT `transaction_no`,`total`
            FROM `deposit_cash_to_deposit_pool`
            WHERE `cash_pool_id` = ${params.cashPoolId}
                AND `deposit_pool_id` = ${params.depositPoolId}
                AND `ho_deposit` IS FALSE
                AND `transaction_no` NOT IN
                    (SELECT `transaction_no`
                    FROM `withdraw_cash_from_deposit_pool`)
                AND DATE(date_created) = STR_TO_DATE('${params.date}','%d-%m-%Y')
            ORDER BY `transaction_no`
        """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Integer createWithdrawCashFromDepositPool(Object object) {
        try{
            Map map = (Map) object
            WithdrawCashFromDepositPool withdrawCashFromDepositPool = map.withdrawCashFromDepositPool
            if (withdrawCashFromDepositPool) {
                withdrawCashFromDepositPool.save(false)
            }
            return new Integer(1)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List fetchWithdrawDenomination(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `currency_demonstration`.`id`,`deposit_cash_currency_denomination`.`quantity`,
                                `currency_demonstration`.`note_name`
                            FROM `deposit_cash_currency_denomination`
                            INNER JOIN `currency_demonstration`
                                ON `currency_demonstration`.`id` = `deposit_cash_currency_denomination`.`currency_demonstration_id`
                            INNER JOIN `deposit_cash_to_deposit_pool`
                                ON `deposit_cash_to_deposit_pool`.`id` = `deposit_cash_currency_denomination`.`deposit_cash_to_deposit_pool_id`
                            WHERE `deposit_cash_to_deposit_pool`.`transaction_no` = '${params.tranNo}'
                            ORDER BY `currency_demonstration`.`id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional(readOnly = true)
    public List listSalesManSDBalance(Object params) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT customer_master_id, `code`, `customer_name`, legacy_id,
                    IFNULL((SELECT SUM(journal_details.`credit_amount`) - SUM(journal_details.`debit_amount`)
                    FROM journal_details
                        INNER JOIN chart_of_accounts_mapping ON (journal_details.`chart_of_accounts_id` = chart_of_accounts_mapping.`chart_of_accounts_id`)
                    WHERE chart_of_accounts_mapping.`coa_type` = '${COAType.SECURITY_DEPOSIT.code()}'
                    AND journal_details.prefix_code  = `code`
                    AND journal_details.is_active = TRUE
                    AND DATE(journal_details.date_created) <= STR_TO_DATE('${params.asOfDate}', '${ApplicationConstants.DATE_FORMAT_DB}')),0) AS amount,

                    IFNULL(ROUND((SELECT SUM(journal_details.`debit_amount`) - SUM(journal_details.`credit_amount`)
                    FROM journal_details
                        INNER JOIN chart_of_accounts_mapping ON (journal_details.`chart_of_accounts_id` = chart_of_accounts_mapping.`chart_of_accounts_id`)
                    WHERE chart_of_accounts_mapping.`coa_type` = '${COAType.ACCOUNTS_RECEIVABLE.code()}'
                    AND journal_details.prefix_code  = `code`
                    AND journal_details.is_active = TRUE
                    AND DATE(journal_details.date_created) <= STR_TO_DATE('${params.asOfDate}', '${ApplicationConstants.DATE_FORMAT_DB}')),2),0) AS receivableAmount



                FROM (
                    SELECT DISTINCT customer_master.id AS customer_master_id, customer_master.`code`, customer_master.`name` AS customer_name, customer_master.`legacy_id`
                    FROM distribution_point
                        INNER JOIN distribution_point_territory_sub_area ON (distribution_point_territory_sub_area.`distribution_point_id` = distribution_point.`id`)
                        INNER JOIN customer_territory_sub_area ON (customer_territory_sub_area.`territory_sub_area_id` = distribution_point_territory_sub_area.`territory_sub_area_id`)
                        INNER JOIN customer_master ON (customer_territory_sub_area.`customer_master_id` = customer_master.`id`)
                    WHERE distribution_point.`id` = ${params.dpId}
                        AND customer_master.`category_id` = ${ApplicationConstants.CUSTOMER_CATEGORY_SALES_MAN_ID}
                    ORDER BY customer_master.`name`
                ) AS tbl
            """

            List list = (List)sql.rows(strSql)
            return list
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer rollbackDepositToHo(Map map){
        try{
            DepositCashToDepositPool depositCashToDepositPool = (DepositCashToDepositPool) map.depositCashToDepositPool
            Journal journal = (Journal) map.journal
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList
            int count = 0

            if(journalDetailsList && journalDetailsList.size() > 0){
                journalDetailsList.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            if(journal && journal.save()){
                count++
            }

            if(depositCashToDepositPool.delete()){
                count++
            }

            return count

        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException("Exception-${ex.message}")
        }
    }

    @Transactional
    public Integer cancelPayment(Map map){
        try{
            CustomerPayment customerPayment = (CustomerPayment) map.customerPayment
            SecurityDeposit securityDeposit = (SecurityDeposit) map.securityDeposit
            List<CustomerPaymentInvoice> customerPaymentInvoiceList = (List<CustomerPaymentInvoice>) map.customerPaymentInvoiceList
            List<Invoice> invoiceList = (List<Invoice>) map.invoiceList
            int count = 0

            if(invoiceList && invoiceList.size() > 0){
                invoiceList.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            if(customerPaymentInvoiceList && customerPaymentInvoiceList.size() > 0){
                customerPaymentInvoiceList.each {
                    it.delete()
                    count++
                }
            }

            if(securityDeposit){
                securityDeposit.delete()
                count++
            }
            if(customerPayment){
                customerPayment.delete()
                count++
            }

            Journal journal = (Journal) map.journal
            if(journal){
                journal.save()
                count++
            }

            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList
            if(journalDetailsList && journalDetailsList.size()>0){
                journalDetailsList.each {
                    it.save()
                    count++
                }
            }

            List<CustomerPaymentCurrencyDenomination> customerPaymentCurrencyDenominationList = (List<CustomerPaymentCurrencyDenomination>) map.customerPaymentCurrencyDenominationList
            if(customerPaymentCurrencyDenominationList && customerPaymentCurrencyDenominationList.size()>0){
                customerPaymentCurrencyDenominationList.each {
                    it.delete()
                    count++
                }
            }

            return count

        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException("Exception-${ex.message}")
        }
    }

}
