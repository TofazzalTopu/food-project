package com.bits.bdfp.accounts

import com.bits.bdfp.util.ApplicationConstants
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class JournalDetailsService {

    static transactional = true
    DataSource dataSource

    @Transactional(readOnly = true)
    public Float getDebitAccountsBalance(ChartOfAccounts chartOfAccounts, String prefixCode = "") {
        try {
            Float balance = 0.00
            String filter = ""
            String groupBy = " GROUP BY journal_details.`chart_of_accounts_id`"
            if(prefixCode != ""){
                filter += " AND journal_details.prefix_code = '${prefixCode}'"
                groupBy += ", journal_details.prefix_code"
            }
            String query = """
                SELECT ROUND(SUM(journal_details.`debit_amount`) - SUM(journal_details.`credit_amount`), 2) AS balance
                FROM journal_details
                WHERE  journal_details.is_active = true
                    AND journal_details.`chart_of_accounts_id` = ${chartOfAccounts.id}
                    ${filter}
                ${groupBy}
            """
            Sql db = new Sql(dataSource)
            List results = db.rows(query)
            if(results && results.size() > 0){
                balance = results.first().balance
            }
            return balance
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Float getCreditAccountsBalance(ChartOfAccounts chartOfAccounts, String prefixCode = "") {
        try {
            Float balance = 0.00
            String filter = ""
            String groupBy = " GROUP BY journal_details.`chart_of_accounts_id`"
            if(prefixCode != ""){
                filter += " AND journal_details.prefix_code = '${prefixCode}'"
                groupBy += ", journal_details.prefix_code"
            }
            String query = """
                SELECT ROUND(SUM(journal_details.`credit_amount`) - SUM(journal_details.`debit_amount`),2) AS balance FROM journal_details
                WHERE journal_details.is_active = true
                    AND journal_details.`chart_of_accounts_id` = ${chartOfAccounts.id}
                    ${filter}
                ${groupBy}
            """
            Sql db = new Sql(dataSource)
            List results = db.rows(query)
            if(results && results.size() > 0){
                balance = results.first().balance
            }
            return balance
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Float getCustomerBalance(ChartOfAccounts chartOfAccountsReceivable, String prefixCode = "") {
        try {
            Sql db = new Sql(dataSource)
//            Float advance = 0.00
//            Float receivable = 0.00
            Float balance = 0.00
            String filter = ""
            String groupBy = " GROUP BY journal_details.`chart_of_accounts_id`"
            if(prefixCode != ""){
                filter += " AND journal_details.prefix_code = '${prefixCode}'"
                groupBy += ", journal_details.prefix_code"
            }
//            String query = """
//                SELECT SUM(journal_details.`credit_amount`) - SUM(journal_details.`debit_amount`) AS balance FROM journal_details
//                WHERE journal_details.`chart_of_accounts_id` = ${chartOfAccountsAdvance.id}
//                    AND journal_details.is_active = true
//                    ${filter}
//                ${groupBy}
//            """

//            List resultsAdvance = db.rows(query)
//            if(resultsAdvance && resultsAdvance.size() > 0){
//                advance = resultsAdvance.first().balance
//            }

            String query = """
                SELECT SUM(journal_details.`debit_amount`) - SUM(journal_details.`credit_amount`) AS balance FROM journal_details
                WHERE journal_details.`chart_of_accounts_id` = ${chartOfAccountsReceivable.id}
                    AND journal_details.is_active = true
                    ${filter}
                ${groupBy}
            """
            List resultsReceivable = db.rows(query)
            if(resultsReceivable && resultsReceivable.size() > 0){
                balance = resultsReceivable.first().balance
            }
            return balance  // if +ve then Receivable else Advance
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public Float getCustomerSDBalance(ChartOfAccounts chartOfAccounts, String customerCode, String fromDate, String toDate) {
        try {
            Sql db = new Sql(dataSource)
            Float sdBalance = 0.00
            String filter = ""
            String groupBy = " GROUP BY journal_details.`chart_of_accounts_id`"

            if(fromDate){
                filter += " AND DATE(journal_details.date_created) >= STR_TO_DATE('${fromDate}', '${ApplicationConstants.DATE_FORMAT_DB}')"
            }

            if(toDate){
                filter += " AND DATE(journal_details.date_created) <= STR_TO_DATE('${toDate}', '${ApplicationConstants.DATE_FORMAT_DB}')"
            }

            String query = """
                SELECT SUM(journal_details.`debit_amount`) - SUM(journal_details.`credit_amount`) AS sdBalance FROM journal_details
                WHERE journal_details.`chart_of_accounts_id` = ${chartOfAccounts.id}
                    AND journal_details.prefix_code = '${customerCode}'
                    AND journal_details.is_active = true
                    ${filter}
                GROUP BY journal_details.`chart_of_accounts_id`
            """
            List resultsReceivable = db.rows(query)
            if(resultsReceivable && resultsReceivable.size() > 0){
                sdBalance = resultsReceivable.first().sdBalance
            }
            return sdBalance  // if +ve then Receivable else Advance
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Transactional(readOnly = true)
    public List listPrefixCode(Object params) {
        try{
            Sql sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " and  `prefix_code` LIKE '%${params.query}%'"
            }
            String strSql = """
                SELECT DISTINCT`prefix_code` as prefixCode
                FROM `journal_details`
                WHERE `prefix_code` IS NOT NULL
                    AND `journal_details`.`is_active` = TRUE
                    ${query}
                ORDER BY `prefix_code`
            """

            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }


    @Transactional(readOnly = true)
    public List listPostfixCode(Object params) {
        try{
            Sql sql = new Sql(dataSource)
            String query = ""
            if (params.query) {
                query = " and `postfix_code` LIKE '%${params.query}%'"
            }
            String strSql = """
                SELECT DISTINCT `postfix_code` as postfixCode
                FROM `journal_details`
                WHERE `postfix_code` IS NOT NULL
                    AND `journal_details`.`is_active` = TRUE
                    ${query}
                ORDER BY `prefix_code`
            """
            return sql.rows(strSql)
        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }
}

