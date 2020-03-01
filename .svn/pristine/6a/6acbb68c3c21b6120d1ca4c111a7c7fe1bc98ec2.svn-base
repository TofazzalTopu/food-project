package com.bits.bdfp.inventory.setup

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.InvoiceDetails
import com.bits.bdfp.inventory.setup.incentive.CalculateAndAdjustIncentive
import com.bits.bdfp.inventory.setup.incentive.UnadjustedIncentive
import com.bits.bdfp.util.ApplicationConstants
import groovy.sql.Sql
import org.springframework.transaction.annotation.Transactional

import javax.sql.DataSource

class CalculateAndAdjustIncentiveService {
    static transactional = true
    DataSource dataSource
    Sql sql

    @Transactional
    public Integer adjust(Map map){
        try {
            int count = 0
            List<CalculateAndAdjustIncentive> calculateAndAdjustIncentiveList = (List<CalculateAndAdjustIncentive>) map.calculateAndAdjustIncentiveList
            List<Invoice> invoiceUpdateList = (List<Invoice>) map.invoiceUpdateList
            List<Invoice> calculatedInvoiceList = (List<Invoice>) map.calculatedInvoiceList
            List<InvoiceDetails> calculatedInvoiceDetailsList = (List<InvoiceDetails>) map.calculatedInvoiceDetailsList
            List<UnadjustedIncentive> unadjustedIncentiveList = (List<UnadjustedIncentive>) map.calculatedInvoiceDetailsList
/*
            List<TargetBasedIncentiveCustomers> targetBasedIncentiveCustomersList = (List<TargetBasedIncentiveCustomers>) map.targetBasedIncentiveCustomersList
            List<SalesAmountBasedIncentiveCustomers> salesAmountBasedIncentiveCustomersList = (List<SalesAmountBasedIncentiveCustomers>) map.salesAmountBasedIncentiveCustomersList
            List<QuantityBasedIncentiveCustomers> quantityBasedIncentiveCustomersList = (List<QuantityBasedIncentiveCustomers>) map.quantityBasedIncentiveCustomersList
            List<VolumeBasedIncentiveCustomers> volumeBasedIncentiveCustomersList = (List<VolumeBasedIncentiveCustomers>) map.volumeBasedIncentiveCustomersList
*/
            List<Journal> journalList = (List<Journal>) map.journalList
            List<JournalDetails> journalDetailsList = (List<JournalDetails>) map.journalDetailsList

            if(calculateAndAdjustIncentiveList && calculateAndAdjustIncentiveList.size()>0){
                calculateAndAdjustIncentiveList.each{
                    it.save()
                    count++
                }
            }

            if(count>0){
                if(invoiceUpdateList && invoiceUpdateList.size()>0){
                    invoiceUpdateList.each{
                        it.save()
                    }
                }

                if(calculatedInvoiceList && calculatedInvoiceList.size()>0){
                    calculatedInvoiceList.each{
                        it.save()
                    }
                }

                if(calculatedInvoiceDetailsList && calculatedInvoiceDetailsList.size()>0){
                    calculatedInvoiceDetailsList.each{
                        it.save()
                    }
                }

                if(unadjustedIncentiveList && unadjustedIncentiveList.size()>0){
                    calculatedInvoiceDetailsList.each{
                        it.save()
                    }
                }
/*
                if(targetBasedIncentiveCustomersList && targetBasedIncentiveCustomersList.size()>0){
                    targetBasedIncentiveCustomersList.each{
                        it.save()
                    }
                }else if(salesAmountBasedIncentiveCustomersList && salesAmountBasedIncentiveCustomersList.size()>0){
                    salesAmountBasedIncentiveCustomersList.each{
                        it.save()
                    }
                }else if(quantityBasedIncentiveCustomersList && quantityBasedIncentiveCustomersList.size()>0){
                    quantityBasedIncentiveCustomersList.each{
                        it.save()
                    }
                }else if(volumeBasedIncentiveCustomersList && volumeBasedIncentiveCustomersList.size()>0){
                    volumeBasedIncentiveCustomersList.each{
                        it.save()
                    }
                }
*/
                if(journalList && journalList.size()>0){
                    journalList.each {
                        it.save()
                    }
                }

                if(journalDetailsList && journalDetailsList.size()>0){
                    journalDetailsList.each {
                        it.save()
                    }
                }
            }

            return count

        }catch(Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    @Transactional(readOnly = true)
    public List fetchBranch(Long id) {
        sql = new Sql(dataSource)
        String strSql = """
                            SELECT DISTINCT `distribution_point`.`code`
                            FROM `distribution_point`
                            INNER JOIN `distribution_point_territory_sub_area`
                                ON `distribution_point_territory_sub_area`.`distribution_point_id` = `distribution_point`.`id`
                            WHERE `distribution_point_territory_sub_area`.`territory_sub_area_id` IN
                                (SELECT `territory_sub_area_id`
                                FROM `customer_territory_sub_area`
                                WHERE `customer_master_id` = ${id})
                          """
        List objList = sql.rows(strSql)
        return objList
    }
}
