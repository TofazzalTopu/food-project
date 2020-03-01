package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.history.PrimaryOrderHistory
import com.bits.bdfp.settings.ApplicationUserDistributionPoint
import com.bits.bdfp.util.ApplicationConstants
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import groovy.sql.GroovyRowResult
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import javax.sql.DataSource
import groovy.sql.Sql
import com.docu.common.Action

class PrimaryDemandOrderService extends Service {

    static transactional = false
    DataSource dataSource
    SpringSecurityService springSecurityService
    Sql sql

    @Transactional
    public PrimaryDemandOrder create(Object object) {
        PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
        if (primaryDemandOrder.save(false)) {
            return primaryDemandOrder
        }
        return null
    }

    @Transactional
    public PrimaryDemandOrder createNew(Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object.get(PrimaryDemandOrder.class.simpleName)
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = (List<PrimaryDemandOrderDetails>) object.get(PrimaryDemandOrderDetails.class.simpleName)
            if (primaryDemandOrder.save(false)) {
                primaryDemandOrderDetailsList.each { primaryDemandOrderDetails ->
                    primaryDemandOrderDetails.save(false)
                }
                return primaryDemandOrder
            }
            return null
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public PrimaryDemandOrder updateNew(Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object.get(PrimaryDemandOrder.class.simpleName)
            List<PrimaryDemandOrderDetails> primaryDemandOrderDetailsList = (List<PrimaryDemandOrderDetails>) object.get(PrimaryDemandOrderDetails.class.simpleName)
            if (primaryDemandOrder.save(false)) {
                primaryDemandOrderDetailsList.each { primaryDemandOrderDetails ->
                    primaryDemandOrderDetails.save(false)
                }
                return primaryDemandOrder
            }
            return null
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional
    public Integer update(Object object) {
        PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
        if (primaryDemandOrder.save()) {
            return new Integer(1)
        } else {
            return new Integer(0)
        }
    }

    @Transactional
    public boolean updatePrimaryDemandOrderDetails(PrimaryDemandOrderDetails primaryDemandOrderDetails) {
        try {
            primaryDemandOrderDetails.save(vaidate: false, insert: false)
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public boolean createPrimaryOrderHistory(PrimaryOrderHistory primaryOrderHistory) {
        try {
            primaryOrderHistory.save(vaidate: false, insert: false)
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    @Transactional
    public Integer createORUpdatePrimaryOrderHistory(List orderHistoryList) {
        List<PrimaryOrderHistory> primaryOrderHistoryList = orderHistoryList
        primaryOrderHistoryList.each {
            if (it.validate()) {
                it.save(false)
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer delete(Object object) {
        PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object
        primaryDemandOrder.delete()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action) {
        List<PrimaryDemandOrder> objList = PrimaryDemandOrder.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = PrimaryDemandOrder.count()
        return [objList: objList, count: total]
    }

    @Transactional(readOnly = true)
    public List<PrimaryDemandOrder> list() {
        return PrimaryDemandOrder.list()
    }

    @Transactional(readOnly = true)
    public List<PrimaryDemandOrderDetails> findByPrimaryDemandOrder(PrimaryDemandOrder primaryDemandOrder) {
        return PrimaryDemandOrderDetails.findAllByPrimaryDemandOrder(primaryDemandOrder)
    }

    @Transactional(readOnly = true)
    public PrimaryDemandOrder read(Long id) {
        return PrimaryDemandOrder.read(id)
    }

    @Transactional(readOnly = true)
    public PrimaryDemandOrderDetails readDetails(Long id) {
        return PrimaryDemandOrderDetails.read(id)
    }

    @Transactional(readOnly = true)
    public PrimaryDemandOrder search(String fieldName, String fieldValue) {
        String query = "from PrimaryDemandOrder as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return PrimaryDemandOrder.find(query)
    }

    @Transactional(readOnly = true)
    public Map getListOrderStatusGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String filter = "";
        String offSet = ""
        if (params.orderNo) {
            filter += """ AND `primary_demand_order`.`order_no`='${params.orderNo}' """
        }
        if (params.orderDateFrom && params.orderDateTo) {
            filter += """ AND DATE(primary_demand_order.`order_date`)
                BETWEEN STR_TO_DATE('${params.orderDateFrom}','${
                ApplicationConstants.DATE_FORMAT_DB
            }') AND STR_TO_DATE('${params.orderDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        if (params.deliveryDateFrom && params.deliveryDateTo) {
            filter += """ AND DATE(primary_demand_order.`date_expected_deliver`)
                BETWEEN STR_TO_DATE('${params.deliveryDateFrom}','${
                ApplicationConstants.DATE_FORMAT_DB
            }') AND STR_TO_DATE('${params.deliveryDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        // Check for New Primary Demand Order
        if (params.isNew) {
            filter += """ AND `primary_demand_order`.`is_new` = ${params.isNew} """
        }
        // Check for status
        if (params.status) {
            filter += """ AND `primary_demand_order`.`demand_order_status` = '${params.status}' """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage} """
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT `primary_demand_order`.`id`,`primary_demand_order`.`order_no`,`csa`.`address`,
                `primary_demand_order`.`demand_order_status`, `customer_master`.`name` AS `customer`,
                DATE_FORMAT(`primary_demand_order`.`order_date`,'${ApplicationConstants.DATE_FORMAT_DB}') AS order_date,
                DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'${ApplicationConstants.DATE_FORMAT_DB}') AS expected_date,
                DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`,'${ApplicationConstants.DATE_FORMAT_DB}') AS proposed_date,
                `primary_demand_order`.is_new,
                (SELECT CONCAT('[',`application_user`.`username`,'] ',`application_user`.`full_name`)
                    FROM `primary_demand_order_approval_status`
                        INNER JOIN `application_user` ON (`application_user`.`id` = `primary_demand_order_approval_status`.`user_approved_id`)
                    WHERE `primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`
                    ORDER BY `primary_demand_order_approval_status`.id DESC
                    LIMIT 1) AS approved_by
            FROM `primary_demand_order`
                INNER JOIN `customer_master` ON (`primary_demand_order`.`customer_order_for_id` = customer_master.`id`)
                LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
                WHERE 1 = 1
                ${filter}
            ORDER BY `primary_demand_order`.`order_no` DESC
               ${strLIMIT}
               ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS `recordCount`
            FROM `primary_demand_order`
            WHERE 1 = 1
                ${filter}
        """
        List countData = sql.rows(strSql)
        if (countData && countData.size() > 0) {
            resultCount = countData.first().recordCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getListNewPrimaryOrderStatusGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        String strLIMIT = "";
        String filter = "";
        String offSet = ""
        if (params.orderNo) {
            filter += """ AND `primary_demand_order`.`order_no`='${params.orderNo}' """
        }
        if (params.orderDateFrom && params.orderDateTo) {
            filter += """ AND DATE(primary_demand_order.`order_date`)
                BETWEEN STR_TO_DATE('${params.orderDateFrom}','${ApplicationConstants.DATE_FORMAT_DB}')
                    AND STR_TO_DATE('${params.orderDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        if (params.deliveryDateFrom && params.deliveryDateTo) {
            filter += """ AND DATE(primary_demand_order.`date_expected_deliver`)
                BETWEEN STR_TO_DATE('${params.deliveryDateFrom}','${ApplicationConstants.DATE_FORMAT_DB}')
                    AND STR_TO_DATE('${params.deliveryDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        // Check for New Primary Demand Order
        if (params.isNew) {
            filter += """ AND `primary_demand_order`.`is_new` = ${params.isNew} """
        }
        // Check for status
        if (params.status) {
            filter += """ AND `primary_demand_order`.`demand_order_status` = '${params.status}' """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage} """
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT `primary_demand_order`.`id`,`primary_demand_order`.`order_no`, `csa`.`address`,
                `primary_demand_order`.`demand_order_status`, `customer_master`.`name` AS `customer`,
                DATE_FORMAT(`primary_demand_order`.`order_date`,'${ApplicationConstants.DATE_FORMAT_DB}') AS order_date,
                DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'${ApplicationConstants.DATE_FORMAT_DB}') AS expected_date,
                DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`,'${ApplicationConstants.DATE_FORMAT_DB}') AS proposed_date,
                `primary_demand_order`.is_new,
                (SELECT CONCAT('[',`application_user`.`username`,'] ',`application_user`.`full_name`)
                    FROM `primary_demand_order_approval_status`
                        INNER JOIN `application_user` ON (`application_user`.`id` = `primary_demand_order_approval_status`.`user_approved_id`)
                    WHERE `primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`
                    ORDER BY `primary_demand_order_approval_status`.id DESC
                    LIMIT 1) AS approved_by
            FROM `primary_demand_order`
                INNER JOIN `customer_master` ON (`primary_demand_order`.`customer_order_for_id` = customer_master.`id`)
                LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
            WHERE `primary_demand_order`.is_new = TRUE
                AND  `primary_demand_order`.user_created_id = ${applicationUser.id}
                ${filter}
            ORDER BY `primary_demand_order`.`order_no` DESC
               ${strLIMIT}
               ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS `recordCount`
            FROM `primary_demand_order`
            WHERE `primary_demand_order`.is_new = TRUE
                AND  `primary_demand_order`.user_created_id = ${applicationUser.id}
                ${filter}
        """
        List countData = sql.rows(strSql)
        if (countData && countData.size() > 0) {
            resultCount = countData.first().recordCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getListForUpdateDeliveryDate(Action action, Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String inIds = ""
        String deliveryDate = ""

        if (params.ids) {
            inIds = """
            AND tbl.id IN (${params.ids})
        """
        }
        if (params.orderDateFrom) {
            deliveryDate = """
            AND DATE(primary_demand_order.`date_expected_deliver`) <= STR_TO_DATE('${params.orderDateFrom}','${
                ApplicationConstants.DATE_FORMAT_DB
            }')

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
                            SELECT DISTINCT tbl.id,tbl.`order_no`,
                            tbl.order_date,
                            tbl.deliver_date,
                            tbl.updated_date,
                            tbl.name,tbl.default_customer_name AS cus_name,
                            tbl.address,tbl.code,
                            tbl.demand_order_status,
                            tbl.amount
                            FROM
                            (SELECT DISTINCT primary_demand_order.id,primary_demand_order.`order_no`,
                            DATE_FORMAT(primary_demand_order.`order_date`,'${ApplicationConstants.DATE_FORMAT_DB}') AS order_date,
                            DATE_FORMAT(primary_demand_order.`date_expected_deliver`,'${
            ApplicationConstants.DATE_FORMAT_DB
        }') AS deliver_date,
                            DATE_FORMAT(primary_demand_order.`updated_delivery_date`,'${
            ApplicationConstants.DATE_FORMAT_DB
        }') AS updated_date,
                            customer_master.name,application_user.username,customer_shipping_address.`address`,
                            customer_master.code,
                            (SELECT `customer_master`.`name` FROM `customer_master` WHERE
                            `primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`
                            )
                            AS default_customer_name,
                            SUM(primary_demand_order_details.`amount`) AS `amount` ,primary_demand_order.`demand_order_status`
                            FROM `primary_demand_order`
                            INNER JOIN `primary_demand_order_details`
                            ON primary_demand_order.id=primary_demand_order_details.`primary_demand_order_id`

                            INNER JOIN `customer_master`
                            ON customer_master.id = primary_demand_order.`customer_order_for_id`
                            INNER JOIN `customer_shipping_address`
                            ON `customer_master`.id = customer_shipping_address.`customer_master_id`
                            INNER JOIN `application_user`
                            ON   application_user.id= primary_demand_order.`user_order_placed_id`
                            GROUP BY primary_demand_order.`id`
                            ) AS tbl
                            WHERE (tbl.demand_order_status = '${
            DemandOrderStatus.APPROVED
        }' OR tbl.demand_order_status = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                            ${deliveryDate}
                            ${inIds}
                            ORDER BY tbl.id ASC
                            ${strLIMIT}
                            ${offSet}
                          """
        //LEFT OUTER JOIN workflow ON tbl.workflow_id=workflow.`priority_sequence`
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List listOrderNo(String orderNO, ApplicationUser applicationUser, boolean isNew = false) {
        try {
            String filter = ""
            ApplicationUserDistributionPoint applicationUserDistributionPoint = ApplicationUserDistributionPoint.findByApplicationUser(applicationUser)
            if (applicationUserDistributionPoint) {
                if (applicationUserDistributionPoint.distributionPoint.isFactory == false) {
                    filter = " AND `primary_demand_order`.`user_order_placed_id` = ${applicationUser.id}"
                }
            } else {
                filter = " AND `primary_demand_order`.`user_order_placed_id` = ${applicationUser.id}"
            }
            sql = new Sql(dataSource)
            String strSql = """
                SELECT primary_demand_order.id,primary_demand_order.`order_no`,customer_master.name
                FROM `primary_demand_order`
                    INNER JOIN `customer_master` ON (customer_master.id = primary_demand_order.`customer_order_for_id`)
                WHERE primary_demand_order.`order_no` LIKE '%${orderNO}%'
                    AND primary_demand_order.`is_new` = ${isNew}
                    ${filter}
            """
            return sql.rows(strSql)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public List listInvoiceNo(String orderNO, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT invoice.id, invoice.code AS `invoice_no`,customer_master.name
            FROM `invoice`
                INNER JOIN `customer_master` ON (customer_master.id = invoice.`default_customer_id`)
            WHERE invoice.is_active = true
                AND invoice.`primary_demand_order_id` IS NOT NULL
                -- AND `invoice`.`user_created_id` = ${applicationUser.id}
                AND `invoice`.`code` LIKE '%${orderNO}%'
        """
        return sql.rows(strSql)
    }

    @Transactional(readOnly = true)
    public List listApprovalHistory(Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """SELECT DISTINCT primary_demand_order.id,primary_demand_order.`order_no`,
                            (CASE WHEN primary_demand_order_approval_status.`is_approved` IS TRUE THEN 'Approved'
                            WHEN primary_demand_order_approval_status.`is_reject`IS TRUE
                            THEN CONCAT('Rejected[','Cause: ' ,primary_demand_order_approval_status.`rejection_cause`,']')
                            ELSE '' END) AS approve

                            FROM `primary_demand_order`

                            INNER JOIN `primary_demand_order_details`
                            ON primary_demand_order.id=primary_demand_order_details.`primary_demand_order_id`

                            INNER JOIN `primary_demand_order_approval_status`
                            ON primary_demand_order.id=primary_demand_order_approval_status.`primary_demand_order_id`

                            WHERE primary_demand_order.`id`=${params.id}
                            AND `primary_demand_order`.`user_order_placed_id`=${applicationUser.id}


                          """

        List list = sql.rows(strSql.toString())
        return list
    }


    @Transactional(readOnly = true)
    public Map getPrimaryDemandOrderListForApprove(Action action, Object params) {
        int total = 0;
        sql = new Sql(dataSource)

        String strLIMIT = "";
        String offSet = ""

        String orderNo = ""
        String orderDate = ""
        String deliveryDate = ""
        String customerName = ""
        String customerId = ""
        String legacyId = ""
        String customerSalesChannelId = ""

        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        ChartOfAccountsMapping chartOfAccountsMapping = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)

        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        if (params.defaultCustomer) {
            customerName = """  AND customer_master.name  LIKE '%${params.defaultCustomer}%'
        """
        }
        if (params.customerId) {
            customerId = """  AND `customer_master`.`code` LIKE '%${params.customerId}%'
        """
        }
        if (params.orderNo) {
            orderNo = """  AND `primary_demand_order`.`order_no`='${params.orderNo}'
        """
        }
        if (params.legacyId) {
            legacyId = """  AND `customer_master`.`legacy_id`='${params.legacyId}'
        """
        }
        if (params.customerSalesChannel) {
            customerSalesChannelId = """  AND `customer_master`.`customer_sales_channel_id`=${
                params.customerSalesChannel
            }
        """
        }

        if (params.orderDateFrom && params.orderDateTo) {
            orderDate = """ AND DATE(primary_demand_order.`order_date`)
             BETWEEN STR_TO_DATE('${params.orderDateFrom}','%d-%m-%Y')  AND STR_TO_DATE('${params.orderDateTo}','%d-%m-%Y')
        """
        }
        if (params.deliveryDateFrom && params.deliveryDateTo) {
            deliveryDate = """
            AND DATE(primary_demand_order.`date_expected_deliver`)
                BETWEEN STR_TO_DATE('${params.deliveryDateFrom}','%d-%m-%Y') AND STR_TO_DATE('${params.deliveryDateTo}','%d-%m-%Y')
        """
        }
        String strSql = """
            SELECT DISTINCT primary_demand_order.id, customer_master.name AS customer_name,  `csa`.`address`,
                `customer_master`.code , primary_demand_order.order_no, `customer_master`.`legacy_id`,
                DATE_FORMAT(primary_demand_order.`order_date`, '%d-%m-%Y') AS order_date,
                DATE_FORMAT(primary_demand_order.`date_expected_deliver`, '%d-%m-%Y') AS date_proposed_delivery,
                SUM( primary_demand_order_details.quantity*primary_demand_order_details.rate) AS delivery_order_value,
                (SELECT ROUND(SUM(journal_details.`debit_amount`) - SUM(journal_details.`credit_amount`), 2) AS balance FROM journal_details
                WHERE  journal_details.is_active = true
                    AND journal_details.`chart_of_accounts_id` = ${chartOfAccountsMapping.chartOfAccounts.id}
                    AND journal_details.prefix_code = `customer_master`.code) AS receivable, '' AS inventory
            FROM primary_demand_order
                INNER JOIN primary_demand_order_details
                    ON (primary_demand_order_details.`primary_demand_order_id` = primary_demand_order.id)
                INNER JOIN `customer_master` ON (customer_master.id = primary_demand_order.`customer_order_for_id`)
                LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
            WHERE  primary_demand_order.`customer_order_for_id` IN (
                    SELECT `workflow_customer_mapping`.`customer_master_id`
                    FROM `workflow_customer_mapping`
                        INNER JOIN `workflow` ON (`workflow_customer_mapping`.`workflow_id` = `workflow`.`id`)
                        INNER JOIN `workflow_user_mapping` ON (`workflow_user_mapping`.`workflow_id` = `workflow`.`id`)
                    WHERE `workflow_user_mapping`.`application_user_id` = ${applicationUser.id})
            AND primary_demand_order.`demand_order_status` = '${DemandOrderStatus.WAITING_FOR_APPROVAL}'

            ${customerName}
            ${customerId}
            ${orderNo}
            ${legacyId}
            ${customerSalesChannelId}
            ${orderDate}
            ${deliveryDate}
            GROUP BY primary_demand_order.order_no
            ORDER BY primary_demand_order.date_expected_deliver DESC
            ${strLIMIT}
            ${offSet}
        """
        List<GroovyRowResult> approvalResultList = new ArrayList<GroovyRowResult>();
        List<GroovyRowResult> list = sql.rows(strSql);
        if (list && list.size() > 0) {
            list.each {
                if (isAuthenticateForApproval(it.id, applicationUser.id)) {
                    approvalResultList.add(it)
                }
            }
        }

        return [objList: approvalResultList, count: approvalResultList.size()]
    }

    @Transactional(readOnly = true)
    private boolean isAuthenticateForApproval(long demandOrderId, long applicationUserId) {
        try {
            long workflowId = 0
            int approvalCount = 0  // Already Approved
            int approvalRequiredCount = 0 // Approval Required
            sql = new Sql(dataSource)
            String strSql = """
                SELECT COUNT(*) AS approvalCount
                FROM
                    `primary_demand_order_approval_status`
                WHERE `primary_demand_order_id` = ${demandOrderId}
            """
            List listApproval = sql.rows(strSql)
            if (listApproval && listApproval.size() > 0) {
                approvalCount = listApproval.first().approvalCount
            }

            strSql = """
                SELECT `workflow_id` FROM `workflow_customer_mapping`
                WHERE `customer_master_id` = (SELECT `customer_order_for_id`
                                              FROM `primary_demand_order` WHERE `id` = ${demandOrderId})
            """
            List listWorkflow = sql.rows(strSql)
            if (listWorkflow && listWorkflow.size() > 0) {
                workflowId = listWorkflow.first().workflow_id
            }
            strSql = """
                SELECT COUNT(*) AS approvalRequired
                FROM
                    `workflow_user_mapping`
                    INNER JOIN `workflow`
                        ON (`workflow_user_mapping`.`workflow_id` = `workflow`.`id`)
                WHERE `workflow`.`menu_name` = '${ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM}'
                    AND `workflow`.`id` = ${workflowId}
                    AND `workflow_user_mapping`.`priority_sequence` < (
                        SELECT `workflow_user_mapping`.`priority_sequence`
                        FROM
                            `workflow_user_mapping`
                            INNER JOIN `workflow`
                                ON (`workflow_user_mapping`.`workflow_id` = `workflow`.`id`)
                        WHERE `workflow_user_mapping`.`application_user_id` = ${applicationUserId}
                            AND `workflow`.`menu_name` = '${ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM}'
                            AND `workflow`.`id` = ${workflowId}
                    )
            """
            List listApprovalRequired = sql.rows(strSql)
            if (listApprovalRequired && listApprovalRequired.size() > 0) {
                approvalRequiredCount = listApprovalRequired.first().approvalRequired
            }
            if (approvalCount == approvalRequiredCount) {
                return true
            } else {
                return false
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    private boolean isNextApprovalRequired(long applicationUserId, long primaryDemandOrderId) {
        try {
            int approvalRequiredCount = 0 // Next Approval Required
            long workflowId = 0
            String strSql = """
                SELECT `workflow_id` FROM `workflow_customer_mapping`
                WHERE `customer_master_id` = (SELECT `customer_order_for_id` FROM `primary_demand_order` WHERE `id` = ${
                primaryDemandOrderId
            })
            """
            List listWorkflow = sql.rows(strSql)
            if (listWorkflow && listWorkflow.size() > 0) {
                workflowId = listWorkflow.first().workflow_id
            }

            strSql = """
                SELECT COUNT(*) AS approvalRequired
                FROM
                    `workflow_user_mapping`
                    INNER JOIN `workflow`
                        ON (`workflow_user_mapping`.`workflow_id` = `workflow`.`id`)
                WHERE `workflow`.`menu_name` = '${ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM}'
                    AND `workflow`.`id` = ${workflowId}
                    AND `workflow_user_mapping`.`priority_sequence` > (
                        SELECT `workflow_user_mapping`.`priority_sequence`
                        FROM
                            `workflow_user_mapping`
                            INNER JOIN `workflow`
                                ON (`workflow_user_mapping`.`workflow_id` = `workflow`.`id`)
                        WHERE `workflow_user_mapping`.`application_user_id` = ${applicationUserId}
                            AND `workflow`.`menu_name` = '${ApplicationConstants.PRIMARY_DEMAND_ORDER_APPROVAL_FORM}'
                            AND `workflow`.`id` = ${workflowId}
                    )
            """
            List listApprovalRequired = sql.rows(strSql)
            if (listApprovalRequired && listApprovalRequired.size() > 0) {
                approvalRequiredCount = listApprovalRequired.first().approvalRequired
            }
            if (approvalRequiredCount > 0) {
                return true
            } else {
                return false
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getFinishedProductListForSelectedPrimaryDemandOrderExecuteForEdit(Object params) {
        int total = 0;
        sql = new Sql(dataSource)

        String strSql = """
            SELECT finish_product.id,  primary_demand_order_details.id AS primaryDemandOrderDetailsId,finish_product.name as product,SUM(primary_demand_order_details.`quantity`) AS totalQuantity,SUM(primary_demand_order_details.`quantity`) AS newQuantity,ROUND(SUM(primary_demand_order_details.rate),2) AS rate,SUM(primary_demand_order_details.`amount`) AS totalAmount,
                ROUND((`finish_product`.`qty_in_ltr` * `primary_demand_order_details`.`quantity`),2) AS qtyInLtr, `finish_product`.`qty_in_ltr` AS qtyInLtrConvert
            FROM primary_demand_order
                LEFT JOIN primary_demand_order_details ON primary_demand_order_details.`primary_demand_order_id`=primary_demand_order.`id`
                LEFT JOIN finish_product ON finish_product.id=primary_demand_order_details.`finish_product_id`
                INNER JOIN main_product AS map ON map.id = finish_product.main_product_id
                INNER  JOIN master_product AS mp ON mp.id = finish_product.master_product_id
            WHERE primary_demand_order.id = ${params.orderId}
            GROUP BY mp.id ,map.id, finish_product.id
            ORDER BY mp.sequence_number ASC, map.sequence_number ASC, finish_product.sequence_number ASC
        """
        List list = sql.rows(strSql);
        if (list)
            total = list.size();
        return [objList: list, count: total]
    }

    @Transactional(readOnly = true)
    public List primaryDemandOrderDefaultApplicationUserList(Object params) {
        sql = new Sql(dataSource)
        String query = ""
        if (params.query) {
            query = """ WHERE (application_user.full_name LIKE '%${params.query}%')
                """
        }
        String strSql = """
            SELECT application_user.id,'' as code,application_user.full_name as name,'' AS status
            FROM application_user
            ${query}
        """

        List resultList = sql.rows(strSql.toString())
        return resultList
    }

    @Transactional
    public Integer approvePrimaryDemandOrderStatus(List primaryDemandOrderApprovalStatusList) {
        try {
            List<PrimaryDemandOrderApprovalStatus> listPrimaryDemandOrderApprovalStatus = primaryDemandOrderApprovalStatusList;
            listPrimaryDemandOrderApprovalStatus.each {
                it.save()
                if (!isNextApprovalRequired(it.userApproved.id, it.primaryDemandOrder.id)) {
                    PrimaryDemandOrder primaryDemandOrder = it.primaryDemandOrder
                    primaryDemandOrder.demandOrderStatus = DemandOrderStatus.APPROVED
                    primaryDemandOrder.isApproved = true
                    primaryDemandOrder.isApprovalRequired = false
                    primaryDemandOrder.save()
                }
            }
            return 1;
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer rejectPrimaryDemandOrderStatus(List primaryDemandOrderApprovalStatusList) {
        try {
            List<PrimaryDemandOrderApprovalStatus> listPrimaryDemandOrderApprovalStatus = primaryDemandOrderApprovalStatusList;
            listPrimaryDemandOrderApprovalStatus.each {
                it.save()
                PrimaryDemandOrder primaryDemandOrder = it.primaryDemandOrder
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.REJECTED
                primaryDemandOrder.isApproved = false
                primaryDemandOrder.isApprovalRequired = false
                primaryDemandOrder.save()
            }
            return 1
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public PrimaryDemandOrder createPrimaryOrder(Map map) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) map.get('primaryDemandOrder')
            primaryDemandOrder = primaryDemandOrder.save(false)
            PrimaryDemandOrderDetails[] primaryDemandOrderDetails = map.get('primaryDemandOrderDetails')
            for (int i = 0; i < primaryDemandOrderDetails.length; i++)
                primaryDemandOrderDetails[i].save(false)

            String[] ids = map.get('ids')
            String condition = ''
            for (int i = 1; i < ids.length; i++) {
                if (ids[i])
                    condition = condition + """ OR `secondary_demand_order`.`id` = ${ids[i]}"""
            }
            sql = new Sql(dataSource)
            String query = """
                UPDATE `secondary_demand_order`
                    SET `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.PROCESSED}',
                    `secondary_demand_order`.`primary_demand_order_id` = ${primaryDemandOrder.id}
                WHERE `secondary_demand_order`.`id` = ${ids[0]}
                    ${condition}
                    """
            sql.execute(query)
            return primaryDemandOrder
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public boolean cancelNewPrimaryOrder(Object params) {
        try {
            sql = new Sql(dataSource)
            String[] primaryOrderIds = params.primaryOrderIds.split(',')
            int length = primaryOrderIds.length
            for (int i = 0; i < length; i++) {
                String query = """
                    UPDATE `primary_demand_order`
                        SET `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.CANCELED}',
                            `primary_demand_order`.`last_updated` = NOW(), `primary_demand_order`.`user_updated_id` =:applicationUserId
                    WHERE `primary_demand_order`.`id` = ${primaryOrderIds[i]}
                        AND (`primary_demand_order_approval_status`.`demand_order_status` = '${
                    DemandOrderStatus.WAITING_FOR_APPROVAL
                }'
                            OR `primary_demand_order_approval_status`.`demand_order_status` = '${
                    DemandOrderStatus.SENT_FOR_PROCESSING
                }')
                """
                sql.execute(query, params)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
        return true
    }

    @Transactional(readOnly = true)
    public Map listFinishProductForPrimaryDemandOrder(Object params) {
        sql = new Sql(dataSource)
        String query = """
            SELECT DISTINCT `finish_product`.`id`, CONCAT('[', `finish_product`.`code`, '] ', `finish_product`.`name`) AS `name`
            FROM `product_price`
                INNER JOIN `finish_product` ON (`product_price`.`finish_product_id` = `finish_product`.`id`)
                INNER JOIN `enterprise_configuration` ON (`finish_product`.`enterprise_configuration_id` = `enterprise_configuration`.`id`
                    AND `enterprise_configuration`.`id` IN (SELECT `application_user_enterprise`.`enterprise_configuration_id` FROM `application_user_enterprise`
                    WHERE `is_active` = TRUE AND `application_user_id` =:applicationUserId))
            WHERE `product_price`.`is_active` = TRUE
        """
        List result = sql.rows(query, params)
        return [results: result, total: result.size()]
    }

    @Transactional(readOnly = true)
    public Map readNewPrimaryDemandOrder(Object params) {
        sql = new Sql(dataSource)
        String query = """
            SELECT DISTINCT
                `primary_demand_order`.`id`
                , `primary_demand_order`.`version`
                , `primary_demand_order`.`order_no` AS `orderNo`
                , DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `dateProposedDelivery`
                , CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`
                , `customer_master`.`id` AS `customerId`
                , `customer_master`.`code` AS `customerNumber`
                , `customer_master`.`name` AS `customerName`
                , IFNULL(`customer_master`.`present_address`, '') AS `customerAddress`
                , IFNULL(`primary_demand_order`.`distribution_point_id`, '') AS `distributionPointId`
                , IFNULL(`primary_demand_order`.`shipping_address_id`, '') AS `shippingAddressId`
            FROM
                `primary_demand_order`
                INNER JOIN `customer_master`
                    ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
            WHERE `primary_demand_order`.`id` =:demandOrderId
                AND `primary_demand_order`.`user_order_placed_id` =:applicationUserId
            LIMIT 1
        """
        List result = sql.rows(query, params)
        if (result.size() > 0) {
            return result.first()
        } else {
            return [:]
        }
    }

    @Transactional(readOnly = true)
    public Map ListSearchNewPrimaryOrderStatusGrid(Action action, Object params) {
        try {
            sql = new Sql(dataSource)
            String strLIMIT = "";
            String offSet = ""
            String condition = ""
            if (params.orderNo) {
                condition += " AND `primary_demand_order`.`order_no` = '${params.orderNo}'"
            }
            if (params.orderDateFrom && params.orderDateTo) {
                condition += """ AND DATE(`primary_demand_order`.`order_date`)
                                 BETWEEN STR_TO_DATE('${params.orderDateFrom}','${
                    ApplicationConstants.DATE_FORMAT_DB
                }') AND STR_TO_DATE('${params.orderDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
                            """
            }
            if (params.deliveryDateFrom && params.deliveryDateTo) {
                condition += """ AND DATE(`primary_demand_order`.`date_expected_deliver`)
                                  BETWEEN STR_TO_DATE('${params.deliveryDateFrom}','${
                    ApplicationConstants.DATE_FORMAT_DB
                }') AND STR_TO_DATE('${params.deliveryDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
                            """
            }
            if (params.status) {
                condition += " AND `primary_demand_order`.`demand_order_status` = '${params.status}'"
            }

            // Search Filter by Customer Legacy ID
            if (params.legacyId) {
                condition += " AND `customer_master`.`legacy_id` = '${params.legacyId}'"
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
                    DISTINCT `primary_demand_order`.`id`
                    , `primary_demand_order`.`order_no`
                    , `csa`.`address`
                    , DATE_FORMAT(`primary_demand_order`.`order_date`, '${ApplicationConstants.DATE_FORMAT_DB}') AS `order_date`
                    , DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`, '${
                ApplicationConstants.DATE_FORMAT_DB
            }') AS `delivery_date`
                    , `primary_demand_order`.`demand_order_status`
                    , IF(`primary_demand_order`.`distribution_point_id` IS NULL, '',  CONCAT('[', `distribution_point`.`code`, '] ', `distribution_point`.`name`)) AS `distribution_point`
                    , CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer_master`
                FROM
                    `primary_demand_order`
                    LEFT JOIN `distribution_point`
                        ON (`primary_demand_order`.`distribution_point_id` = `distribution_point`.`id`)
                    INNER JOIN `customer_master`
                        ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
                    LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
                WHERE `primary_demand_order`.`user_order_placed_id` = ${params.applicationUserId}
                    AND `primary_demand_order`.`is_new` = TRUE
                    AND (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.WAITING_FOR_APPROVAL}'
                        OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.REJECTED}'
                        OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                    ${condition}
                ORDER BY `primary_demand_order`.`order_date` DESC
                ${strLIMIT}
                ${offSet}
            """
            List objList = sql.rows(strSql)
            strSql = """
                SELECT COUNT(DISTINCT `primary_demand_order`.`id`) AS orderCount
                FROM
                    `primary_demand_order`
                    LEFT JOIN `distribution_point`
                        ON (`primary_demand_order`.`distribution_point_id` = `distribution_point`.`id`)
                    INNER JOIN `customer_master`
                        ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
                WHERE `primary_demand_order`.`user_order_placed_id` = ${params.applicationUserId}
                    AND `primary_demand_order`.`is_new` = TRUE
                    AND (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.WAITING_FOR_APPROVAL}'
                        OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.REJECTED}'
                        OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                    ${condition}
            """
            int resultCount = 0
            List objListCount = sql.rows(strSql)
            if (objListCount) {
                resultCount = objListCount.first().orderCount
            }

            return [objList: objList, count: resultCount]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map ListPrimaryOrderDetailsGrid(Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT
                `finish_product`.`id`
                , `finish_product`.`code` AS `productCode`
                , `finish_product`.`name` AS `productName`
                , `primary_demand_order_details`.`id` AS `primaryDemandOrderDetailsId`
                , `primary_demand_order_details`.`rate`
                , `primary_demand_order_details`.`quantity`
                , `primary_demand_order_details`.`amount`
            FROM
                `primary_demand_order_details`
                INNER JOIN `finish_product`
                    ON (`primary_demand_order_details`.`finish_product_id` = `finish_product`.`id`)
            WHERE `primary_demand_order_details`.`primary_demand_order_id` =:demandOrderId
            ORDER BY `primary_demand_order_details`.`id`
        """
        List objList = sql.rows(strSql, params)
        int resultCount = objList.size()
        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getListPrintInvoiceStatusGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String orderNo = ""
        String orderDate = ""
        String salesChannel = ""
        String legacyId = ""

        if (params.orderNo) {
            orderNo = """  AND `invoice`.`code`='${params.orderNo}'
        """
        }
        if (params.salesChannel) {
            salesChannel = """  AND  customer_master.customer_sales_channel_id = ${params.salesChannel}
        """
        }

        if (params.customerId) {
            salesChannel = """  AND  customer_master.id = ${params.customerId}
        """
        }
        if (params.legacyId) {
            legacyId = """  AND  customer_master.legacy_id = '${params.legacyId}'
        """
        }

        if (params.orderDateFrom && params.orderDateTo) {
            orderDate = """ AND DATE(`invoice`.`date_created`)
                            BETWEEN STR_TO_DATE('${params.orderDateFrom}', '${
                ApplicationConstants.DATE_FORMAT_DB
            }')  AND STR_TO_DATE('${params.orderDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')
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
                `invoice`.`id`
                , `invoice`.`code` AS `invoice_no`
                , `primary_demand_order`.`order_no`
                ,  `csa`.`address`
                , CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `createdBy`
                ,`customer_master`.`legacy_id` AS legacy
                , CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`
                , `invoice`.`invoice_amount` AS `invoiceAmount`
                , `primary_demand_order`.`demand_order_status`
                , `invoice`.`date_created`
            FROM
                `invoice`
                INNER JOIN `primary_demand_order` ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `application_user`
                    ON (`invoice`.`user_created_id` = `application_user`.`id`)
                INNER JOIN `customer_master`
                    ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
                LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
            WHERE `invoice`.`is_active` = true
                ${salesChannel}
                ${legacyId}
                ${orderNo}
                ${orderDate}
            ORDER BY `invoice`.`id` DESC
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)

        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS resultCount
            FROM
                `invoice`
                INNER JOIN `primary_demand_order` ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `application_user`
                    ON (`invoice`.`user_created_id` = `application_user`.`id`)
                INNER JOIN `customer_master`
                    ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
            WHERE `invoice`.`is_active` = true
                ${salesChannel}
                ${legacyId}
                ${orderNo}
                ${orderDate}
        """

        List objCount = sql.rows(strSql)
        if (objCount && objCount.size() > 0) {
            resultCount = objCount.first().resultCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map primaryInvoiceListForRollback(Object params) {
        try {
            sql = new Sql(dataSource)

            String strSql = """
                SELECT
                    `invoice`.`id`
                    , `invoice`.`code` AS `invoice_no`
                    , `primary_demand_order`.`order_no`
                    , CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `createdBy`
                    , CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`
                    , `invoice`.`invoice_amount` AS `invoiceAmount`
                    , `primary_demand_order`.`demand_order_status`
                    , `invoice`.`date_created`
                FROM
                    `invoice`
                    INNER JOIN `primary_demand_order` ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                    INNER JOIN `application_user`
                        ON (`invoice`.`user_created_id` = `application_user`.`id`)
                    INNER JOIN `customer_master`
                        ON (`primary_demand_order`.`customer_order_for_id` = `customer_master`.`id`)
                WHERE `invoice`.`is_active` = TRUE
                    AND `invoice`.`paid_amount` = 0.0
                    AND primary_demand_order.`demand_order_status` = '${DemandOrderStatus.DELIVERED}'
                    AND `invoice`.`code`='${params.invoiceCode}'
                ORDER BY `invoice`.`id` DESC
            """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }

            return [objList: objList, count: resultCount]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListForCancelSecondaryInvoiceStatusGrid(Action action, Object params) {
        try {
            sql = new Sql(dataSource)
            String strLIMIT = "";
            String offSet = ""
            String orderNo = ""
            String orderDate = ""

            ApplicationUser applicationUser = (ApplicationUser) springSecurityService.currentUser

            if (params.orderNo) {
                orderNo = """  AND `invoice`.`code` = '${params.orderNo}'
                """
            }
            if (params.orderDateFrom && params.orderDateTo) {
                orderDate = """ AND DATE(`secondary_demand_order`.`date_order`)
                                BETWEEN STR_TO_DATE('${params.orderDateFrom}', '${
                    ApplicationConstants.DATE_FORMAT_DB
                }')  AND STR_TO_DATE('${params.orderDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')
                """
            }

            if (action.resultPerPage != -1) {
                strLIMIT = """ LIMIT ${action.resultPerPage} """
                offSet = """ OFFSET ${action.start} """
            } else {
                action.resultPerPage = -1;
            }

            String strSql = """
               SELECT * FROM (
                SELECT `invoice`.`id`,
                    `invoice`.`code` AS `invoice_no`,
                    `secondary_demand_order`.`order_no`,
                    CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `createdBy`,
                    CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`,
                    ROUND(`invoice`.`invoice_amount`) AS `invoiceAmount`,
                    `secondary_demand_order`.`demand_order_status`, `invoice`.`date_created`,
                    (SELECT COUNT(*) FROM retail_order
                    WHERE retail_order.`secondary_demand_order_id` = `secondary_demand_order`.`id`
                    AND retail_order.`invoice_id` IS NOT NULL) AS retailCount
                FROM `invoice`
                    INNER JOIN `secondary_demand_order`
                        ON (`secondary_demand_order`.`id` = invoice.`secondary_demand_order_id`)
                    INNER JOIN `application_user`
                        ON (`application_user`.`id` = `invoice`.`user_created_id`)
                    INNER JOIN `customer_master`
                        ON (`invoice`.`default_customer_id` = `customer_master`.`id`)
                    INNER JOIN application_user_distribution_point
                        ON (application_user_distribution_point.`distribution_point_id` = invoice.`distribution_point_id`)
                WHERE invoice.is_active = true
                    AND secondary_demand_order_id IS NOT NULL
                    AND invoice.`paid_amount` = 0.00
                     AND application_user_distribution_point.application_user_id = ${applicationUser.id}
                    ${orderNo}
                    ${orderDate}
                ORDER BY `invoice`.`date_created` DESC
                ) AS tbl
                WHERE retailCount = 0
                ${strLIMIT}
                ${offSet}
            """
            List objList = sql.rows(strSql)
            int resultCount = 0
            if (objList && objList.size() > 0) {
                resultCount = objList.size()
            }

            return [objList: objList, count: resultCount]
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListPrintSecondaryInvoiceStatusGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String orderNo = ""
        String orderDate = ""

        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.currentUser

        if (params.orderNo) {
            orderNo = """  AND `invoice`.`code` = '${params.orderNo}'
            """
        }
        if (params.orderDateFrom && params.orderDateTo) {
            orderDate = """
                AND DATE(`invoice`.`date_created`)
                    BETWEEN STR_TO_DATE('${params.orderDateFrom}', '${
                ApplicationConstants.DATE_FORMAT_DB
            }')  AND STR_TO_DATE('${params.orderDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }

        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage} """
            offSet = """ OFFSET ${action.start} """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT `secondary_demand_order`.`id`,
                `invoice`.`code` AS `invoice_no`,
                `secondary_demand_order`.`order_no`,
                CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `createdBy`,
                CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`,
                ROUND(`invoice`.`invoice_amount`) AS `invoiceAmount`,
                `secondary_demand_order`.`demand_order_status`, `invoice`.`date_created`
            FROM `invoice`
                INNER JOIN `secondary_demand_order`
                    ON (`secondary_demand_order`.`id` = invoice.`secondary_demand_order_id`)
                INNER JOIN `application_user`
                    ON (`application_user`.`id` = `invoice`.`user_created_id`)
                INNER JOIN `customer_master`
                    ON (`invoice`.`default_customer_id` = `customer_master`.`id`)
            WHERE invoice.is_active = true
                AND secondary_demand_order_id IS NOT NULL
                AND `invoice`.`user_created_id` = ${applicationUser.id}
                ${orderNo}
                ${orderDate}
            ORDER BY `invoice`.`date_created` DESC
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)

        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS resultCount
            FROM `invoice`
                INNER JOIN `secondary_demand_order`
                    ON (`secondary_demand_order`.`id` = invoice.`secondary_demand_order_id`)
                INNER JOIN `application_user`
                    ON (`application_user`.`id` = `invoice`.`user_created_id`)
                INNER JOIN `customer_master`
                    ON (`invoice`.`default_customer_id` = `customer_master`.`id`)
            WHERE invoice.is_active = true
                AND secondary_demand_order_id IS NOT NULL
                AND `invoice`.`user_created_id` = ${applicationUser.id}
                ${orderNo}
                ${orderDate}
        """
        List objCount = sql.rows(strSql)
        if (objCount && objCount.size() > 0) {
            resultCount = objCount.first().resultCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getListCancelInvoiceStatusGrid(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String orderNo = ""
        String orderDate = ""

        if (params.orderNo) {
            orderNo = """  AND `invoice`.`code` = '${params.orderNo}'
            """
        }
        if (params.orderDateFrom && params.orderDateTo) {
            orderDate = """
                AND DATE(`invoice`.`date_created`)
                BETWEEN STR_TO_DATE('${params.orderDateFrom}', '${
                ApplicationConstants.DATE_FORMAT_DB
            }')  AND STR_TO_DATE('${params.orderDateTo}', '${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }

        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage} """
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT
                `primary_demand_order`.`id`,`primary_demand_order`.`order_no`
                , `invoice`.`code` AS `invoice_no`
                , `primary_demand_order`.`order_no`
                , CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `createdBy`
                , CONCAT('[', `customer_master`.`code`, '] ', `customer_master`.`name`) AS `customer`
                , `csa`.`address`
                , invoice.`invoice_amount` AS `invoiceAmount`
                , `primary_demand_order`.`demand_order_status`
                , invoice.date_created
            FROM
                `invoice`
                INNER JOIN `primary_demand_order`
                    ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `application_user`
                    ON (`invoice`.`user_created_id` = `application_user`.`id`)
                INNER JOIN `customer_master`
                    ON (`invoice`.`default_customer_id` = `customer_master`.`id`)
                 LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id
            WHERE (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.ORDER_BOOKED}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.READY_FOR_SHIPMENT}')
                AND `invoice`.`is_active` = TRUE AND `invoice`.`is_direct_invoice` != TRUE AND `invoice`.`paid_amount` = 0
                ${orderNo}
                ${orderDate}
            ORDER BY `invoice`.`id` DESC
            ${strLIMIT} ${offSet}
        """

        List objList = sql.rows(strSql)
        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS resultCount
            FROM
                `invoice`
                INNER JOIN `primary_demand_order`
                    ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `application_user`
                    ON (`invoice`.`user_created_id` = `application_user`.`id`)
                INNER JOIN `customer_master`
                    ON (`invoice`.`default_customer_id` = `customer_master`.`id`)
            WHERE (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.ORDER_BOOKED}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.READY_FOR_SHIPMENT}')
                AND `invoice`.`is_active` = TRUE AND `invoice`.`is_direct_invoice` != TRUE AND `invoice`.`paid_amount` = 0
                ${orderNo}
                ${orderDate}
        """

        List objCount = sql.rows(strSql)
        if (objCount && objCount.size() > 0) {
            resultCount = objCount.first().resultCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List listPrintInvoiceForReport(Action action, Object params) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT tbl.id,tbl.`invoice_number`,
             tbl.`name`, tbl.`username`,
             SUM(tbl.`amount`) AS amount
              FROM
             ( SELECT DISTINCT primary_demand_order.id,print_invoice_status.`invoice_number`,
             `customer_master`.`name`, application_user.`username`,
              (finished_product_booked_details.`amount`) AS amount
                FROM `primary_demand_order`
                    INNER JOIN `finished_product_booked`
                        ON (finished_product_booked.`primary_demand_order_id` = `primary_demand_order`.id)
                    INNER JOIN `primary_demand_order_details`
                        ON (primary_demand_order.id = primary_demand_order_details.`primary_demand_order_id`)
                    INNER JOIN `finished_product_booked_details`
                        ON (finished_product_booked.id = finished_product_booked_details.`finished_product_booked_id`)
                    INNER JOIN `customer_master`
                        ON (`customer_master`.id = primary_demand_order_details.`customer_order_for_id`)
                    INNER JOIN `application_user`
                        ON (`application_user`.id = primary_demand_order.`user_order_placed_id`)
                    INNER JOIN `print_invoice_status`
                        ON (`primary_demand_order`.id= `print_invoice_status`.`primary_demand_order_id`)
                WHERE print_invoice_status.`print_status` > 0
              ) AS tbl
              INNER JOIN `primary_demand_order`
                ON (tbl.id= primary_demand_order.id)
              GROUP BY tbl.id,tbl.`invoice_number`,
                    `tbl`.`name`, tbl.`username`
              ORDER BY tbl.id ASC
        """
        List objList = sql.rows(strSql.toString())
        return objList
    }

    @Transactional
    // Primary Invoice
    public Integer createPrintInvoice(Object object, ApplicationUser applicationUser) {
        Map map = (Map) object
        List<PrintInvoiceStatus> printInvoiceStatusList = map.printInvoiceStatusList
        PrimaryDemandOrder primaryDemandOrder = null
        printInvoiceStatusList.each {
            it.save()
            primaryDemandOrder = it.primaryDemandOrder
            if (primaryDemandOrder.demandOrderStatus == DemandOrderStatus.ORDER_BOOKED) {
                primaryDemandOrder.demandOrderStatus = DemandOrderStatus.READY_FOR_SHIPMENT
                primaryDemandOrder.lastUpdated = new Date()
                primaryDemandOrder.userUpdated = applicationUser
                primaryDemandOrder.save()
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer createSecondaryPrintInvoice(Object object) {
        Map map = (Map) object
        List<PrintInvoiceStatus> printInvoiceStatusList = map.printInvoiceStatusList
        SecondaryDemandOrder secondaryDemandOrder = null
        printInvoiceStatusList.each {
            it.save(false)
            secondaryDemandOrder = it.secondaryDemandOrder
            secondaryDemandOrder.demandOrderStatus = DemandOrderStatus.DELIVERED
            secondaryDemandOrder.save()
        }
        return new Integer(1)
    }

    @Transactional
    public Integer updateDeliveryDate(Object object) {
        Map map = (Map) object
        List<PrimaryDemandOrder> primaryDemandOrderList = map.primaryDemandOrderList
        primaryDemandOrderList.each {
            it.save(false)
        }
        return new Integer(1)
    }

    @Transactional
    public Object listForEdit(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String searchCondition = ""
        String orderNo = ""
        String date = ""
        if (params.orderNo) {
            orderNo = """ AND `primary_demand_order`.`order_no` = '${params.orderNo}'
        """
        }
        if (params.dateFrom && params.dateTo) {
            date = """ AND DATE_FORMAT(`primary_demand_order`.`order_date`,'${
                ApplicationConstants.DATE_FORMAT_DB
            }') BETWEEN '${params.dateFrom}' AND '${params.dateTo}'
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
            SELECT `primary_demand_order`.`id`,`primary_demand_order`.`order_no`,`application_user`.`full_name`,
                DATE_FORMAT(`primary_demand_order`.`order_date`,'${ApplicationConstants.DATE_FORMAT_DB}') AS order_date,
                DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`,'${ApplicationConstants.DATE_FORMAT_DB}') AS date_proposed_delivery,
                DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'${ApplicationConstants.DATE_FORMAT_DB}') AS date_expected_deliver,
                COUNT(`primary_demand_order_approval_status`.`id`) as `count`,
                `primary_demand_order`.`demand_order_status`
            FROM `primary_demand_order`
                INNER JOIN `application_user` ON (`application_user`.`id` = `primary_demand_order`.`user_order_placed_id`)
                LEFT JOIN `primary_demand_order_approval_status` ON (`primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`)
            WHERE `primary_demand_order`.`user_order_placed_id` = ${params.applicationUser.id}
                AND  `primary_demand_order`.`is_new` = false
                AND (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.WAITING_FOR_APPROVAL}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.REJECTED}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                ${orderNo}
                ${searchCondition}
                ${date}
            GROUP BY `primary_demand_order`.`id`
            ${strLIMIT}
            ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0

        strSql = """
            SELECT COUNT(*) AS resultCount
            FROM `primary_demand_order`
                INNER JOIN `application_user` ON (`application_user`.`id` = `primary_demand_order`.`user_order_placed_id`)
                LEFT JOIN `primary_demand_order_approval_status` ON (`primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`)
            WHERE `primary_demand_order`.`user_order_placed_id` = ${params.applicationUser.id}
                AND  `primary_demand_order`.`is_new` = false
                AND (`primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.WAITING_FOR_APPROVAL}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.REJECTED}'
                OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
                ${orderNo}
                ${searchCondition}
                ${date}
        """
        List objCount = sql.rows(strSql)
        if (objCount && objCount.size() > 0) {
            resultCount = objCount.first().resultCount
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public Map getDetailsListForGrid(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT `primary_demand_order_details`.`id`,`primary_demand_order_details`.`quantity`,
                `primary_demand_order_details`.`rate`,`primary_demand_order_details`.`amount`,
                `primary_demand_order_details`.`finish_product_id`, `finish_product`.`code` AS `productCode`, `finish_product`.`name` AS `productName`
            FROM `primary_demand_order_details`
                INNER JOIN `finish_product` ON (`primary_demand_order_details`.`finish_product_id` = `finish_product`.`id`)
            WHERE `primary_demand_order_details`.`primary_demand_order_id` = ${id}
        """

        List resultList = sql.rows(strSql)
        return [objList: resultList, count: resultList.size()]
    }

    @Transactional
    public Integer updatePrimaryOrder(Map map) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) map.get('primaryDemandOrder')
            if (primaryDemandOrder.save()) {
                String[] list = map.get('deletedIds')
                for (int i = 0; i < list.length; i++) {
                    if (list[i] != '') {
                        PrimaryDemandOrderDetails primaryDemandOrderDetails = PrimaryDemandOrderDetails.read(Long.parseLong(list[i]))
                        primaryDemandOrderDetails.delete()
                    }
                }
                PrimaryDemandOrderDetails[] primaryDemandOrderDetails = map.get('primaryDemandOrderDetails')
                for (int i = 0; i < primaryDemandOrderDetails.length; i++) {
                    if (primaryDemandOrderDetails[i].id) {
                        primaryDemandOrderDetails[i].save()
                    } else {
                        primaryDemandOrderDetails[i].save(false)
                    }
                }
                return new Integer(1)
            } else {
                return new Integer(0)
            }
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer deletePrimaryOrder(Object object) {
        try {
            PrimaryDemandOrder primaryDemandOrder = (PrimaryDemandOrder) object.get('primaryDemandOrder')
            sql = new Sql(dataSource)
            String strSql = """SELECT `id`
                            FROM `primary_demand_order_approval_status`
                            WHERE `primary_demand_order_id` = ${primaryDemandOrder.id}
                          """
            List list = sql.rows(strSql.toString())
            PrimaryDemandOrderApprovalStatus.executeUpdate("delete from PrimaryDemandOrderApprovalStatus pdos where pdos.primaryDemandOrder=?", [primaryDemandOrder])
//            PrimaryDemandOrderApprovalStatus primaryDemandOrderApprovalStatus = PrimaryDemandOrderApprovalStatus.read(list[0].id)
            PrimaryDemandOrderDetails[] primaryDemandOrderDetails = object.get('primaryDemandOrderDetails')

//            primaryDemandOrderApprovalStatus.delete()
            for (int i = 0; i < primaryDemandOrderDetails.length; i++) {
                primaryDemandOrderDetails[i].delete()
            }
            primaryDemandOrder.delete()

            return new Integer(1)
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListForCashThroughBank(Action action, Object params) {
        sql = new Sql(dataSource)

        String strSql = """
            SELECT `invoice`.id, `invoice`.`code` AS `invoice_no`,
                 DATE_FORMAT(`invoice`.`date_created`,'${ApplicationConstants.DATE_FORMAT_DB}') AS invoice_date,
                 DATE_FORMAT(DATE_ADD(`invoice`.`date_created`, INTERVAL customer_master.`credit_period_in_days` DAY),'${
            ApplicationConstants.DATE_FORMAT_DB
        }') AS payment_due_date,
                 (ROUND(`invoice`.`invoice_amount` - `invoice`.`paid_amount`, 2)) AS due_amount,
                 (ROUND(`invoice`.`paid_amount`, 2)) AS paid_amount,
                 ROUND(`invoice`.`invoice_amount`, 2) AS amount
            FROM invoice
                INNER JOIN customer_master ON (invoice.`default_customer_id` = customer_master.`id`)
            WHERE invoice.is_active = true
                AND invoice.`default_customer_id` = ${params.customerMasterId}
                AND (`invoice`.`invoice_amount` - `invoice`.`paid_amount`) > 0
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List listOrderNoWithInvoice(String orderNO) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT DISTINCT `primary_demand_order`.`id`, `primary_demand_order`.`order_no`, `invoice`.`code` AS `invoice_no`
            FROM `primary_demand_order`
                INNER JOIN `invoice` ON (`invoice`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `secondary_demand_order` ON (`secondary_demand_order`.`primary_demand_order_id` = `primary_demand_order`.`id`)
            WHERE `primary_demand_order`.`order_no` LIKE '%${orderNO}%'
                AND `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.DELIVERED}'
                AND secondary_demand_order.demand_order_status != '${DemandOrderStatus.DELIVERED}'
        """

        List resultList = sql.rows(strSql)
        return resultList
    }


    @Transactional(readOnly = true)
    public List listOrderNoWithInvoiceSecondary(String orderNO, Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strSql = """
        SELECT DISTINCT `secondary_demand_order`.`id`, `secondary_demand_order`.`order_no`-- , `invoice`.`code` AS `invoice_no`
            FROM `secondary_demand_order`
         WHERE `secondary_demand_order`.`order_no` LIKE '%${orderNO}%'
                AND secondary_demand_order.demand_order_status = '${DemandOrderStatus.UNDER_PROCESS}'
                 AND(`secondary_demand_order`.`territory_sub_area_id` IN (
                                     SELECT `territory_sub_area_id`
                                     FROM `distribution_point_territory_sub_area`
                                     INNER JOIN `distribution_point_warehouse` ON `distribution_point_warehouse`.`distribution_point_id` = `distribution_point_territory_sub_area`.`distribution_point_id`
                                     WHERE `distribution_point_warehouse`.`warehouse_id` = ${params.warehouse})
                                     OR `secondary_demand_order`.`user_order_placed_id` = ${applicationUser.id})
  """

        List resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public List listOrderNoForProcess(String orderNO) {
        sql = new Sql(dataSource)
        String strSql = """
            SELECT `primary_demand_order`.`id`, `primary_demand_order`.`order_no`
            FROM `primary_demand_order`
            WHERE `primary_demand_order`.`order_no` LIKE '%${orderNO}%'
                AND (`primary_demand_order`.`demand_order_status` = '${
            DemandOrderStatus.APPROVED
        }' OR `primary_demand_order`.`demand_order_status` = '${DemandOrderStatus.SENT_FOR_PROCESSING}')
        """
        List resultList = sql.rows(strSql)
        return resultList
    }

    @Transactional(readOnly = true)
    public Map listInfoByEnterprise(Object params, Object list) {
        String id = ''
        if (params)
            id = params.id
        else
            id = list[0].id

        sql = new Sql(dataSource)
        String strSql = """SELECT `id`,`name`
                        FROM `customer_master`
                        WHERE `enterprise_configuration_id` = '${id}'
                          """
        List customerList = sql.rows(strSql.toString())
        strSql = """SELECT `id`,`name`
                FROM `finish_product`
                WHERE `enterprise_configuration_id` = '${id}'
                          """
        List productList = sql.rows(strSql.toString())
        Map result = [:]
        result.put('customerList', customerList)
        result.put('productList', productList)
        return result
    }

    @Transactional
    public void changeStatus(Object params) {
        sql = new Sql(dataSource)
        String strSql = """UPDATE `secondary_demand_order`
                    INNER JOIN `customer_master` ON `customer_master`.`id` = `secondary_demand_order`.`customer_master_id`
                    INNER JOIN `enterprise_configuration` ON `enterprise_configuration`.`id` = `customer_master`.`enterprise_configuration_id`
                    SET `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_PROCESS}'
                    WHERE `enterprise_configuration`.`id` = ${params.idEnterprise}
                    AND `secondary_demand_order`.`demand_order_status` = '${DemandOrderStatus.UNDER_REVIEW}'
                          """
        sql.execute(strSql)
    }

    @Transactional(readOnly = true)
    public Map primaryOrderHistory(Object params) {

        sql = new Sql(dataSource)
        String strSql = """
            SELECT CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `initiator` ,
                DATE_FORMAT(`primary_demand_order`.`date_created`, '${ApplicationConstants.DATE_TIME_FORMAT_DB}') AS `initiationDate`,
                IFNULL(`designation`.`name`, '') AS `designation`
            FROM `primary_demand_order`
                INNER JOIN `application_user` ON (`primary_demand_order`.`user_created_id` = `application_user`.`id`)
                LEFT JOIN `customer_master` ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                LEFT JOIN `designation` ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `primary_demand_order`.`id`=:primaryOrderId
            LIMIT 1
        """
        List initiatorList = sql.rows(strSql, params)

        strSql = """
            SELECT CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `approvedBy` ,
                DATE_FORMAT(`primary_demand_order_approval_status`.`date_created`, '${
            ApplicationConstants.DATE_TIME_FORMAT_DB
        }') AS `approvalDate`,
                `primary_demand_order_approval_status`.`demand_order_status` AS `orderStatus`, IFNULL(`designation`.`name`, '') AS `designation`,
                IFNULL(`primary_demand_order_approval_status`.`remarks`,'') AS `remarks`

            FROM `primary_demand_order`
                INNER JOIN `primary_demand_order_approval_status` ON ( `primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`)
                INNER JOIN `application_user` ON (`primary_demand_order_approval_status`.`user_approved_id` = `application_user`.`id`)
                LEFT JOIN `customer_master` ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                LEFT JOIN `designation` ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `primary_demand_order`.`id`=:primaryOrderId
                ORDER BY `primary_demand_order_approval_status`.`id` DESC
        """
        List approvalUserList = sql.rows(strSql, params)

        strSql = """
            SELECT  CONCAT('[', `application_user`.`username`, '] ', `application_user`.`full_name`) AS `pendingUser`,
                IFNULL(`designation`.`name`, '') AS `designation`, wum.`priority_sequence`
            FROM primary_demand_order pdo
                INNER JOIN workflow_customer_mapping wcm ON (pdo.`customer_order_for_id` = wcm.customer_master_id)
                INNER JOIN `workflow_user_mapping` wum ON (wcm.workflow_id = wum.workflow_id)
                INNER JOIN application_user ON wum.`application_user_id` = application_user.`id`
                LEFT JOIN `customer_master` ON (`application_user`.`customer_master_id` = `customer_master`.`id`)
                LEFT JOIN `designation` ON (`customer_master`.`designation_id` = `designation`.`id`)
            WHERE `pdo`.`id` = :primaryOrderId
                AND wum.`application_user_id` NOT IN (SELECT pdoas.`user_approved_id` FROM  primary_demand_order_approval_status pdoas WHERE pdoas.`primary_demand_order_id` = :primaryOrderId)
            ORDER BY wum.`priority_sequence`
        """
        List pendingUserList = sql.rows(strSql, params)
        return [initiatorList: initiatorList, approvalUserList: approvalUserList, pendingUserList: pendingUserList]
    }

    @Transactional(readOnly = true)
    public List fetchDefaultCustomer(Object params) {

        sql = new Sql(dataSource)
        String strSql = """
            SELECT `customer_master`.`name`,`customer_master`.`code`,`customer_master`.`id`
            FROM `customer_master`
                INNER JOIN `distribution_point_warehouse` ON
                    `distribution_point_warehouse`.`default_customer_id` = `customer_master`.`id`
                INNER JOIN `distribution_point` ON
                    `distribution_point`.`id` = `distribution_point_warehouse`.`distribution_point_id`
            WHERE `distribution_point`.`id` = ${params.id}
        """
        List initiatorList = sql.rows(strSql)

        return initiatorList
    }


    @Transactional(readOnly = true)
    public List coaDetailList(Long id) {
        try {
            CustomerMaster customerMaster = CustomerMaster.read(id)
            String customerCode = customerMaster.code
            sql = new Sql(dataSource)
            String strSql = """
                SELECT SUM(receivable) AS receivable, SUM(deposit) AS deposit,
                    (SELECT ROUND(IFNULL(customer_master.`customer_credit_limit`, 0.00), 2)
                    FROM customer_master
                    WHERE `customer_master`.`id` = ${id} LIMIT 1) AS customer_credit_limit,
                    (SELECT `customer_priority`.`name`
                    FROM customer_master
                        INNER JOIN `customer_priority` ON (`customer_priority`.`id` = `customer_master`.`customer_priority_id`)
                    WHERE `customer_master`.`id` = ${id} LIMIT 1) AS  `priority`
                FROM(
                    SELECT ROUND(IFNULL(SUM(`debit_amount`), 0) - IFNULL(SUM(`credit_amount`), 0), 2) AS receivable, 0.00 AS deposit
                    FROM `journal_details`
                        INNER JOIN `chart_of_accounts_mapping` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `journal_details`.`chart_of_accounts_id`)
                    WHERE `journal_details`.`is_active` = TRUE
                        AND `chart_of_accounts_mapping`.`coa_type` = '${COAType.ACCOUNTS_RECEIVABLE}'
                        AND `journal_details`.`prefix_code` = '${customerCode}'
                    GROUP BY `chart_of_accounts_mapping`.`coa_type`
                    UNION ALL
                    SELECT 0.00 AS receivable,  ROUND(IFNULL(SUM(`credit_amount`), 0) - IFNULL(SUM(`debit_amount`), 0), 2) AS deposit
                    FROM `journal_details`
                        INNER JOIN `chart_of_accounts_mapping` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `journal_details`.`chart_of_accounts_id`)
                    WHERE `journal_details`.`is_active` = TRUE
                        AND `chart_of_accounts_mapping`.`coa_type` = '${COAType.SECURITY_DEPOSIT}'
                        AND `journal_details`.`prefix_code` = '${customerCode}'
                    GROUP BY `chart_of_accounts_mapping`.`coa_type`
                ) AS tbl
            """
            List resultList = sql.rows(strSql)
            return resultList
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map getListOrderStatusGridIsFactoryFalse(Action action, Object params, ApplicationUser applicationUser) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String filter = "";
        String offSet = ""
        if (params.orderNo) {
            filter += """ AND `primary_demand_order`.`order_no`='${params.orderNo}' """
        }
        if (params.orderDateFrom && params.orderDateTo) {
            filter += """ AND DATE(primary_demand_order.`order_date`)
                BETWEEN STR_TO_DATE('${params.orderDateFrom}','${
                ApplicationConstants.DATE_FORMAT_DB
            }') AND STR_TO_DATE('${params.orderDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        if (params.deliveryDateFrom && params.deliveryDateTo) {
            filter += """ AND DATE(primary_demand_order.`date_expected_deliver`)
                BETWEEN STR_TO_DATE('${params.deliveryDateFrom}','${
                ApplicationConstants.DATE_FORMAT_DB
            }') AND STR_TO_DATE('${params.deliveryDateTo}','${ApplicationConstants.DATE_FORMAT_DB}')
            """
        }
        // Check for New Primary Demand Order
        if (params.isNew) {
            filter += """ AND `primary_demand_order`.`is_new` = ${params.isNew} """
        }
        // Check for status
        if (params.status) {
            filter += """ AND `primary_demand_order`.`demand_order_status` = '${params.status}' """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """ LIMIT ${action.resultPerPage} """
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
            SELECT `primary_demand_order`.`id`,`primary_demand_order`.`order_no`, `csa`.`address`,
                `primary_demand_order`.`demand_order_status`, `customer_master`.`name` AS `customer`,
                DATE_FORMAT(`primary_demand_order`.`order_date`,'${ApplicationConstants.DATE_FORMAT_DB}') AS order_date,
                DATE_FORMAT(`primary_demand_order`.`date_expected_deliver`,'${ApplicationConstants.DATE_FORMAT_DB}') AS expected_date,
                DATE_FORMAT(`primary_demand_order`.`date_proposed_delivery`,'${ApplicationConstants.DATE_FORMAT_DB}') AS proposed_date,
                `primary_demand_order`.is_new,
                (SELECT CONCAT('[',`application_user`.`username`,'] ',`application_user`.`full_name`)
                    FROM `primary_demand_order_approval_status`
                        INNER JOIN `application_user` ON (`application_user`.`id` = `primary_demand_order_approval_status`.`user_approved_id`)
                    WHERE `primary_demand_order_approval_status`.`primary_demand_order_id` = `primary_demand_order`.`id`
                    ORDER BY `primary_demand_order_approval_status`.id DESC
                    LIMIT 1) AS approved_by
            FROM `primary_demand_order`
                INNER JOIN `customer_master` ON (`primary_demand_order`.`customer_order_for_id` = customer_master.`id`)
            WHERE 1 = 1 AND `customer_master`.id IN(
             SELECT `customer_master`.`id` FROM `customer_master`
                    INNER JOIN `customer_territory_sub_area`
                        ON  `customer_territory_sub_area`.`customer_master_id` = `customer_master`.`id`
                    INNER JOIN `territory_sub_area`
                        ON `territory_sub_area`.`id` = `customer_territory_sub_area`.`territory_sub_area_id`
                    INNER JOIN `distribution_point_territory_sub_area`
                        ON `distribution_point_territory_sub_area`.`territory_sub_area_id` = `territory_sub_area`.`id`
                    LEFT JOIN customer_shipping_address AS csa ON csa.id = primary_demand_order.shipping_address_id

             WHERE `distribution_point_territory_sub_area`.`distribution_point_id` =
                   (SELECT `distribution_point`.`id` FROM `application_user_distribution_point`
                         INNER JOIN `distribution_point` ON (`application_user_distribution_point`.`distribution_point_id` = `distribution_point`.`id`)
                            WHERE `application_user_distribution_point`.`application_user_id` = ${applicationUser.id}
                                AND `distribution_point`.`enterprise_configuration_id` IN (
                                        SELECT `enterprise_configuration_id` FROM `application_user_enterprise`
                                        WHERE `application_user_id` = ${applicationUser.id}))
                                        )
                ${filter}
            ORDER BY `primary_demand_order`.`order_no` DESC
               ${strLIMIT}
               ${offSet}
        """
        List objList = sql.rows(strSql)
        int resultCount = 0
        strSql = """
            SELECT COUNT(*) AS `recordCount`
            FROM `primary_demand_order`
            WHERE 1 = 1
                ${filter}
        """
        List countData = sql.rows(strSql)
        if (countData && countData.size() > 0) {
            resultCount = countData.first().recordCount
        }
        return [objList: objList, count: resultCount]
    }

    @Transactional(readOnly = true)
    public List getAuthorizedEmployeeInfo() {
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        sql = new Sql(dataSource)
        String strSql = """
                    SELECT  cm.name, concat(ifnull(concat(cm.first_name,' '),''), ifnull(concat(cm.middle_name,' '),'') , ifnull(cm.last_name,'')) AS userName,
                      cm.first_name, cm.middle_name, cm.last_name,
                      cm.code AS pin, dpt.name AS departmentName, dsg.name AS designationName
                    FROM application_user AS au
                    INNER JOIN customer_master AS cm ON cm.id = au.customer_master_id
                    INNER JOIN department AS dpt ON (dpt.id = cm.department_id)
                    INNER JOIN designation AS dsg ON (dsg.id = cm.designation_id)
                    WHERE au.id = ${applicationUser.id}
                          """
        List resultList = sql.rows(strSql.toString())
        return resultList
    }
}
