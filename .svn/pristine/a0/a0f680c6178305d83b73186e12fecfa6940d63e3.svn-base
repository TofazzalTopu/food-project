package com.bits.bdfp.accounts

import com.bits.bdfp.settings.EnterpriseConfiguration
import org.springframework.transaction.annotation.Transactional
import com.docu.common.Service
import groovy.sql.Sql
import javax.sql.DataSource
import com.docu.common.Action

class ChartOfAccountsMappingService extends Service {
    static transactional = false
    DataSource dataSource

    @Transactional(readOnly = true)
    public ChartOfAccountsMapping read(Long id) {
        return ChartOfAccountsMapping.read(id)
    }

    @Transactional(readOnly = true)
    public Map getListForGrid(Action action, Map params) {
        try {
            List<ChartOfAccountsMapping> chartOfAccountsMappingList = ChartOfAccountsMapping.withCriteria {
                maxResults(Integer.parseInt(action.resultPerPage.toString()))
                firstResult(Integer.parseInt(action.start.toString()))
                order(action.sortCol.toString(), action.sortOrder.toString())
            }
            long chartOfAccountsMappingCount = ChartOfAccountsMapping.count()
            return [objList: chartOfAccountsMappingList, count: chartOfAccountsMappingCount]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List<ChartOfAccountsMapping> list() {
        return ChartOfAccountsMapping.list()
    }

    @Transactional
    public ChartOfAccountsMapping create(Object object) {
        try {
            ChartOfAccountsMapping chartOfAccountsMapping = (ChartOfAccountsMapping) object
            return chartOfAccountsMapping.save(vaidate: false, insert: true)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Boolean submit(EnterpriseConfiguration enterprise, COAType coaType, List<ChartOfAccountsMapping> chartOfAccountsMappingList) {
        try {
            ChartOfAccountsMapping.executeUpdate("delete from ChartOfAccountsMapping coaMapping where coaMapping.enterprise = ? and coaMapping.coaType = ?", [enterprise, coaType])
            chartOfAccountsMappingList.each { chartOfAccountsMapping->
                chartOfAccountsMapping.save()
            }
            return Boolean.TRUE
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer update(Object object) {
        try {
            ChartOfAccountsMapping chartOfAccountsMapping = (ChartOfAccountsMapping) object
            if (chartOfAccountsMapping.save(vaidate: false)) {
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
            ChartOfAccountsMapping chartOfAccountsMapping = (ChartOfAccountsMapping) object
            chartOfAccountsMapping.delete()
            return new Integer(1)
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public ChartOfAccountsMapping search(String fieldName, String fieldValue) {
        String query = "from ChartOfAccountsMapping as docu where docu." + fieldName + " = '" + fieldValue + "'"
        return ChartOfAccountsMapping.find(query)
    }

    @Transactional(readOnly = true)
    public Map listChartOfAccountsMapping(Object params) {
        try {
            String query = """
                SELECT `chart_of_accounts`.`id`, CONCAT(`chart_of_accounts`.`chart_of_account_name`,' [',`chart_of_accounts`.`chart_of_account_code_user`,']') AS name FROM `chart_of_accounts`
                    INNER JOIN `enterprise_configuration` ON (`chart_of_accounts`.`enterprise_configuration_id` = `enterprise_configuration`.`id`)
                    INNER JOIN `chart_of_account_layer` ON (`chart_of_accounts`.`chart_of_account_layer_id` = `chart_of_account_layer`.`id`)
                WHERE `chart_of_accounts`.`enterprise_configuration_id` = ${params.enterpriseId}
                    AND `enterprise_configuration`.`no_of_layers` = `chart_of_account_layer`.`layer_number`
            """
            Sql db = new Sql(dataSource)
            List chartOfAccountsList = db.rows(query)

            query = """
                SELECT `chart_of_accounts`.`id`, CONCAT(`chart_of_accounts`.`chart_of_account_name`,' [',`chart_of_accounts`.`chart_of_account_code_user`,']') AS name FROM `chart_of_accounts_mapping`
                    INNER JOIN `chart_of_accounts` ON (`chart_of_accounts`.`id` = `chart_of_accounts_mapping`.`chart_of_accounts_id`)
                WHERE `chart_of_accounts`.`enterprise_configuration_id` = ${params.enterpriseId}
                    AND `chart_of_accounts_mapping`.`coa_type` = '${params.coaType}'
            """
            List selectedChartOfAccounts = db.rows(query)
            return [chartOfAccountsList: chartOfAccountsList, selectedChartOfAccounts: selectedChartOfAccounts]
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map checkForDeletedChartOfAccounts(String coaList, long enterpriseId){
        String message = ""
        int transactionFound = 0
        try{
             String query = """
                SELECT `chart_of_accounts_mapping`.`chart_of_accounts_id`, `chart_of_accounts`.`chart_of_account_name` FROM `chart_of_accounts_mapping`
                    INNER JOIN `journal_details` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `journal_details`.`chart_of_accounts_id`)
                    INNER JOIN `chart_of_accounts` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `chart_of_accounts`.`id`)
                WHERE `chart_of_accounts_mapping`.`chart_of_accounts_id` NOT IN (${coaList})
                    AND `chart_of_accounts_mapping`.`enterprise_id` = ${enterpriseId}
             """
            Sql sql = new Sql(dataSource)
            List results =  sql.rows(query)
            if(results && results.size() > 0){
                for (int i=0; i < results.size(); i++){
                    if(message != ""){
                        message += "; "
                    }
                    message += results[i].chart_of_account_name
                }

                message += " already used. You can not exclude these Accounts"
                transactionFound = 1
            }
            return [message: message, transactionFound: transactionFound]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Map checkForDeletedChartOfAccountsByCOAType(String coaType, long enterpriseId){
        String message = ""
        int transactionFound = 0
        try{
            String query = """
                SELECT `chart_of_accounts_mapping`.`chart_of_accounts_id`, `chart_of_accounts`.`chart_of_account_name` FROM `chart_of_accounts_mapping`
                    INNER JOIN `journal_details` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `journal_details`.`chart_of_accounts_id`)
                    INNER JOIN `chart_of_accounts` ON (`chart_of_accounts_mapping`.`chart_of_accounts_id` = `chart_of_accounts`.`id`)
                WHERE `chart_of_accounts_mapping`.`coa_type` = '${coaType}'
                    AND `chart_of_accounts_mapping`.`enterprise_id` = ${enterpriseId}
             """
            Sql sql = new Sql(dataSource)
            List results =  sql.rows(query)
            if(results && results.size() > 0){
                for (int i=0; i < results.size(); i++){
                    if(message != ""){
                        message += "; "
                    }
                    message += results[i].chart_of_account_name
                }

                message += " already used. You can not exclude these Accounts"
                transactionFound = 1
            }
            return [message: message, transactionFound: transactionFound]
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}
