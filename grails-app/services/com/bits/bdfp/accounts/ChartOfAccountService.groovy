package com.bits.bdfp.accounts

import com.bits.bdfp.common.BankAccount
import com.docu.common.Action
import com.docu.common.Service
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

/**
 * Created by abhijit.majumder on 1/24/2016.
 */
class ChartOfAccountService extends Service {
    static transactional = false

    Sql sql
    DataSource dataSource

    @Transactional(readOnly = true)
    public Map getListForLayerGrid(Action action) {
        List<ChartOfAccountLayer> objList = ChartOfAccountLayer.withCriteria {
            if (action.resultPerPage != -1) {
                maxResults(action.resultPerPage)
            }
            firstResult(action.start)
            order(action.sortCol, action.sortOrder)
        }
        long total = BankAccount.count()
        return [objList: objList, count: total]
    }

    @Transactional
    public Object create(Object object) {
        if (object && object.size() > 0) {
            object.each {
                it.save(false)
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer update(Object object) {
        if (object && object.size() > 0) {
            object.each {
                it.save()
            }
        }
        return new Integer(1)
    }

    @Transactional
    public Integer delete(Object object) {
        ChartOfAccounts chartOfAccounts = (ChartOfAccounts) object
        chartOfAccounts.delete()
        return new Integer(1)
    }

    @Transactional
    public Object read(Long id) {
        return new Integer(1)
    }

    @Transactional
    public List list() {
        return null
    }

    @Transactional(readOnly = true)
    public List listLayers(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `id` AS layer_id,`layer_name`,`layer_number`,`layer_code_length`
                            FROM `chart_of_account_layer`
                            WHERE `enterprise_configuration_id` = ${id}
                                AND `is_active` IS TRUE
                            ORDER BY `layer_number`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Object createChartOfAccounts(Object object) {
        ChartOfAccounts chartOfAccounts = (ChartOfAccounts) object
        chartOfAccounts.save(false)
        return new Integer(1)
    }

    @Transactional
    public Integer deleteLayersOfEnterprise(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                            DELETE FROM `chart_of_account_layer`
                            WHERE `enterprise_configuration_id` = ${id}
                          """
        sql.execute(strSql)
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public List listCoa(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `chart_of_accounts`.`id`,`chart_of_accounts`.`chart_of_account_name`,`chart_of_accounts`.`parent_id`,
                                `chart_of_account_layer`.`layer_code_length`,`chart_of_accounts`.`chart_of_account_layer_id`,
                                `chart_of_account_layer`.`layer_number`,`chart_of_accounts`.`chart_of_account_code_user`,
                                `chart_of_accounts`.`chart_of_account_code_auto`,`chart_of_account_layer`.`id` AS layer_id,
                                (SELECT id FROM `chart_of_account_layer` WHERE parent_layer_id = layer_id) AS child_layer,
                                `chart_of_accounts`.`account_type`
                            FROM `chart_of_accounts`
                            INNER JOIN `chart_of_account_layer`
                                ON `chart_of_accounts`.`chart_of_account_layer_id` = `chart_of_account_layer`.`id`
                            WHERE `chart_of_accounts`.`enterprise_configuration_id` = ${id}
                                AND `chart_of_accounts`.`is_active` IS TRUE
                            ORDER BY `id`
                          """
        List objList = sql.rows(strSql)
        return objList
    }

    @Transactional
    public Object deleteChartOfAccounts(Object object) {
        ChartOfAccounts chartOfAccounts = (ChartOfAccounts) object
        chartOfAccounts.delete()
        return new Integer(1)
    }

    @Transactional
    public Object updateChartOfAccounts(Object object) {
        ChartOfAccounts chartOfAccounts = (ChartOfAccounts) object
        chartOfAccounts.save()
        return new Integer(1)
    }

    @Transactional(readOnly = true)
    public String getHead(String name) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT `chart_of_accounts`.`chart_of_account_code_auto`
                            FROM `chart_of_accounts`
                            INNER JOIN `chart_of_accounts` coa
                                ON coa.`id` = `chart_of_accounts`.`parent_id`
                            WHERE coa.`chart_of_account_name` = '${name}'
                          """
        List objList = sql.rows(strSql)
        return objList[0].chart_of_account_code_auto
    }

    @Transactional(readOnly = true)
    public List listCOAGroups(long enterpriseId) {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT `chart_of_accounts`.`id`, `chart_of_accounts`.`chart_of_account_name` AS `name`
                FROM `chart_of_accounts`
                WHERE `chart_of_accounts`.`parent_id` = 0
                    AND `chart_of_accounts`.`enterprise_configuration_id` = ${enterpriseId}
            """
            return sql.rows(strSql)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List chartOfAccountsChild() {
        try{
            sql = new Sql(dataSource)
            String strSql = """
                SELECT id, chart_of_account_name AS charOfAccountName, chart_of_account_code_user AS charOfAccountCodeUser
                FROM `chart_of_accounts` coa
                WHERE coa.`chart_of_account_layer_id` = (SELECT id FROM `chart_of_account_layer`
                                                        WHERE layer_number = (SELECT MAX(layer_number)
                                                        FROM `chart_of_account_layer`))
                ORDER BY chart_of_account_name

            """
            // --  AND `chart_of_accounts`.`enterprise_configuration_id` = ${enterpriseId}
            return sql.rows(strSql)
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
