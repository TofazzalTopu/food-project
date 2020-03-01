package com.bits.bdfp.inventory.demandorder

import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.util.ApplicationConstants
import com.docu.common.Service
import com.docu.security.ApplicationUser
import org.springframework.transaction.annotation.Transactional

class RollbackReceivedPrimaryInvoiceService extends Service{

    static transactional = true

    @Transactional
    public Integer execute(Invoice primaryInvoice, ApplicationUser applicationUser) {
        try{
            Invoice invoice = primaryInvoice

            List<DistributionPointStockTransaction> distributionPointStockTransactionList = DistributionPointStockTransaction.findAllByInInvoice(invoice)
            distributionPointStockTransactionList.each { distributionPointStockTransaction ->
                DistributionPointStock distributionPointStock = distributionPointStockTransaction.distributionPointStock
                distributionPointStock.inQuantity -= distributionPointStockTransaction.inQuantity
                distributionPointStock.userUpdated = applicationUser
                distributionPointStock.lastUpdated = new Date()
                distributionPointStock.save()

                distributionPointStockTransaction.inQuantity = 0
                distributionPointStockTransaction.userUpdated = applicationUser
                distributionPointStockTransaction.lastUpdated = new Date()
                distributionPointStockTransaction.save()
            }

            Journal journal = Journal.findByModuleNameAndTransactionNo(ApplicationConstants.MODULE_RECEIVE_FINISH_GOODS, invoice.code)
            if (journal) {
                List<JournalDetails> journalDetailsList = JournalDetails.findAllByJournal(journal)
                journalDetailsList.each { journalDetails ->
                    journalDetails.isActive = false
                    journalDetails.save()
                }
                journal.isActive = false
                journal.save()
            }
            PrimaryDemandOrder primaryDemandOrder = invoice.primaryDemandOrder
            primaryDemandOrder.demandOrderStatus = DemandOrderStatus.IN_TRANSIT
            primaryDemandOrder.userUpdated = applicationUser
            primaryDemandOrder.save()

            return new Integer(1)

        } catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    @Override
    Object create(Object object) {
        return null
    }

    @Override
    Integer update(Object object) {
        return null
    }

    @Override
    Integer delete(Object object) {
        return null
    }

    @Override
    Object read(Long id) {
        return null
    }

    @Override
    List list() {
        return null
    }
}
