package com.bits.bdfp.accounts

import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class ChartOfAccountUploadService {
    static transactional = false
    DataSource dataSource
    Sql sql

    @Transactional
    public Integer migrateAllData(Map map){
        try{
            Integer count = 0
            Journal journal = (Journal) map.journal
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList

            if(journal && journal.save()){
                if(journalDetailsList && journalDetailsList.size()>0){
                    journalDetailsList.each {
                        if(it.save()){
                            count++
                        }
                    }
                }
            }
            return count
        }catch (Exception ex){
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }

    @Transactional
    public Integer migrateAllOpeningStock(Map map){
        try{
            Integer count = 0
            List<DistributionPointStock> distributionPointStockList = (List<DistributionPointStock>) map.distributionPointStockList
            List<DistributionPointStockTransaction> distributionPointStockTransactionList = (List<DistributionPointStockTransaction>) map.distributionPointStockTransactionList

            if(distributionPointStockList && distributionPointStockList.size()>0){
                distributionPointStockList.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            if(count>0 && distributionPointStockTransactionList && distributionPointStockTransactionList.size()>0){
                distributionPointStockTransactionList.each {
                    if(it.save()){
                        count++
                    }
                }
            }

            return count
        }catch (Exception ex){
            log.error(ex.message)
            throw  new RuntimeException(ex.message)
        }
    }
}
