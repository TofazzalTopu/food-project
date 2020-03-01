package com.bits.bdfp.inventory.sales.processmarketreturn

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.sales.MarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnDetails
import com.bits.bdfp.inventory.sales.ReceiveProductsFromMarketService
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.SubWarehouse
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.commons.ObjectUtil
import com.docu.security.ApplicationUser
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.inventory.sales.ProcessMarketReturn
import com.bits.bdfp.inventory.sales.ProcessMarketReturnService
import com.docu.commons.CommonConstants

import java.text.SimpleDateFormat


@Component("createProcessMarketReturnAction")
class CreateProcessMarketReturnAction extends Action {
    public static final Log log = LogFactory.getLog(CreateProcessMarketReturnAction.class)
    private final String MESSAGE_HEADER = 'Process Market Return'
    private final String MESSAGE_SUCCESS = 'Process Market Return Success'

    @Autowired
    ProcessMarketReturnService processMarketReturnService
    @Autowired
    ReceiveProductsFromMarketService receiveProductsFromMarketService

    @Autowired
    SessionFactory sessionFactory

    Message message

    @Override
    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            ProcessMarketReturn processMarketReturn = map.processMarketReturn
            if (!processMarketReturn.validate()) {
                message = this.getValidationErrorMessage(processMarketReturn)
            } else {
                message = this.getMessage(processMarketReturn, Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage("Market Return Process", Message.ERROR, "Exception-${ex.message}")
        }
    }

    @Override
    public Object postCondition(def Object object1, def Object object2) {
        return null
    }

    public Object execute(Object params, Object object) {
        try {
            ProcessMarketReturn processMarketReturn = new ProcessMarketReturn(params)
            MarketReturn marketReturn = MarketReturn.read(Long.parseLong(params.mrId))
            marketReturn.mrStatus = "PROCESSED"
            marketReturn.dateUpdated = new Date()
            marketReturn.userUpdated = (ApplicationUser) object
            processMarketReturn.dateCreated = new Date()
            processMarketReturn.userCreated = (ApplicationUser) object
            processMarketReturn.mrNo = marketReturn.mrNo

            List<FinishGoodStock> finishGoodStocks = new ArrayList<FinishGoodStock>()
            List<FinishGoodStockTransaction> finishGoodStockTransactions = new ArrayList<FinishGoodStockTransaction>()
            List<ProcessMarketReturnDetails> processMarketReturnDetailsList = ObjectUtil.instantiateObjects(params.items, ProcessMarketReturnDetails.class)
            List<DistributionPointStock> distributionPointStocks = new ArrayList<DistributionPointStock>()
            List<DistributionPointStockTransaction> distributionPointStockTransactions = new ArrayList<DistributionPointStockTransaction>()

            if(marketReturn.isDpCustomer == true) {
                List list = receiveProductsFromMarketService.listWareHouse(params)
                params.put('invId', list[0].id)
                list = receiveProductsFromMarketService.listSubWareHouse(params)
                params.put('invId', list[0].id)
                list = receiveProductsFromMarketService.listSubWareHouse(marketReturn.sourceDistributionPoint.id)
                params.put('dpInvId', list[0].id)
            }


            Long prevId = 0
            Float prevRate = 0
            FinishGoodStock finishGoodStockTemp = null
            FinishGoodStockTransaction finishGoodStockTransactionTemp = null

            for (int i = 0; i < processMarketReturnDetailsList.size(); i++) {
                processMarketReturnDetailsList[i].processMarketReturn = processMarketReturn

                if(marketReturn.isDpCustomer == true) {
                    List distributionPointStockList = processMarketReturnService.dpMrStock(params, processMarketReturnDetailsList[i].finishProduct.id)
                    loop1: for(int x = 0; x < distributionPointStockList.size(); x++){
                        DistributionPointStock distributionPointStock = DistributionPointStock.read(distributionPointStockList[x].id)
                        DistributionPointStockTransaction distributionPointStockTransaction = new DistributionPointStockTransaction()

                        int availQty = distributionPointStock.inQuantity - distributionPointStock.outQuantity
                        if(availQty >= processMarketReturnDetailsList[i].qcQuantity){
                            distributionPointStock.outQuantity = distributionPointStock.outQuantity + processMarketReturnDetailsList[i].qcQuantity
                            distributionPointStockTransaction.outQuantity = processMarketReturnDetailsList[i].qcQuantity
                        }else{
                            distributionPointStock.outQuantity = distributionPointStock.outQuantity + availQty
                            distributionPointStockTransaction.outQuantity = availQty
                        }
                        distributionPointStockTransaction.inQuantity = 0
                        distributionPointStockTransaction.unitPrice = processMarketReturnDetailsList[i].rate
                        distributionPointStockTransaction.transactionDate = new Date()
                        distributionPointStockTransaction.dateCreated = new Date()
                        distributionPointStockTransaction.userCreated = (ApplicationUser) object
                        distributionPointStockTransaction.distributionPointStock = distributionPointStock
                        distributionPointStockTransactions.add(distributionPointStockTransaction)
                        distributionPointStocks.add(distributionPointStock)
                        availQty = availQty - processMarketReturnDetailsList[i].qcQuantity
                        if(availQty >= 0){
                            break loop1;
                        }
                    }
                }


                FinishGoodStock finishGoodStock = processMarketReturnService.listFinishGoodTransaction(params, processMarketReturnDetailsList[i].finishProduct.id)
                FinishGoodStockTransaction finishGoodStockTransaction = null

                if (prevId != processMarketReturnDetailsList[i].finishProduct.id) {
                    if (finishGoodStock) {
                        finishGoodStock.lastUpdated = new Date()
                        finishGoodStock.userUpdated = (ApplicationUser) object
                        finishGoodStock.inQuantity = finishGoodStock.inQuantity + processMarketReturnDetailsList[i].qcQuantity
                    } else {
                        finishGoodStock = new FinishGoodStock()
                        finishGoodStock.dateCreated = new Date()
                        finishGoodStock.userCreated = (ApplicationUser) object
                        finishGoodStock.subWarehouse = SubWarehouse.read(CommonConstants.FACTORY_MARKET_RETURN_SUB_WAREHOUSE_ID)
                        finishGoodStock.inQuantity = processMarketReturnDetailsList[i].qcQuantity
                        finishGoodStock.finishProduct = processMarketReturnDetailsList[i].finishProduct
                        finishGoodStock.outQuantity = 0
                        finishGoodStock.batchNo = null
                    }
                    finishGoodStocks.add(finishGoodStock)
                }else{
                    finishGoodStock = finishGoodStockTemp
                    finishGoodStock.inQuantity = finishGoodStock.inQuantity + processMarketReturnDetailsList[i].qcQuantity
                }
                processMarketReturnDetailsList[i].finishGoodStock = finishGoodStock

                if (prevId != processMarketReturnDetailsList[i].finishProduct.id || prevRate != processMarketReturnDetailsList[i].rate) {
                    finishGoodStockTransaction = new FinishGoodStockTransaction()
                    finishGoodStockTransaction.dateCreated = new Date()
                    finishGoodStockTransaction.userCreated = (ApplicationUser) object
                    finishGoodStockTransaction.inQuantity = processMarketReturnDetailsList[i].qcQuantity
                    finishGoodStockTransaction.outQuantity = 0
                    finishGoodStockTransaction.transactionDate = processMarketReturn.dateCreated
                    finishGoodStockTransaction.unitPrice = processMarketReturnDetailsList[i].rate
                    finishGoodStockTransaction.finishGoodStock = finishGoodStock
                    finishGoodStockTransactions.add(finishGoodStockTransaction)
                } else {
                    finishGoodStockTransaction = finishGoodStockTransactionTemp
                    finishGoodStockTransaction.inQuantity = finishGoodStockTransaction.inQuantity + processMarketReturnDetailsList[i].qcQuantity
                }

                prevId = processMarketReturnDetailsList[i].finishProduct.id
                prevRate = processMarketReturnDetailsList[i].rate
                finishGoodStockTemp = finishGoodStock
                finishGoodStockTransactionTemp = finishGoodStockTransaction
            }

            /***************************** COA Entry Start ***************************/

            Date dateNow = new Date()
            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)
            EnterpriseConfiguration enterpriseConfiguration=null
            if(marketReturn.sourceDistributionPoint!=null)
            {
                 enterpriseConfiguration = marketReturn.sourceDistributionPoint.enterpriseConfiguration
            }
            else if (marketReturn.destinationDistributionPoint!=null)
            {
                 enterpriseConfiguration = marketReturn.destinationDistributionPoint.enterpriseConfiguration
            }
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            JournalDetails journalDetails = null
            //ChartOfAccountsMapping chartOfAccountsMapping = null
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration, userCreated: (ApplicationUser) object, isActive: true)
            journalHead.tableName =  sessionFactory.getClassMetadata(ProcessMarketReturn).tableName
            journalHead.transactionDate = new Date()
            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo =marketReturn.mrNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_PROCESS_MARKET_RETURN
            if(!journalHead.validate()){
                return this.getValidationErrorMessage(journalHead)
            }

            for (int i = 0; i < processMarketReturnDetailsList.size(); i++) {

// Factory
                /***************************** DR journal Factory ***************************/
                ChartOfAccountsMapping chartOfAccountsMappingSalesReturn = ChartOfAccountsMapping.findByCoaType(COAType.SALES_RETURN)
                if (!chartOfAccountsMappingSalesReturn) {
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Sales Return Head is not mapped with Chart of Accounts")
                }
                journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingSalesReturn.chartOfAccounts
                journalDetails.prefixCode = marketReturn.sourceDistributionPoint?.code
                journalDetails.postfixCode = marketReturn.destinationDistributionPoint.code
                journalDetails.debitAmount = processMarketReturnDetailsList[i].qcQuantity * processMarketReturnDetailsList[i].rate
                journalDetails.creditAmount = 0.00
                journalDetails.particular = "Sales Return to Distribution Point: ["+marketReturn.destinationDistributionPoint.code+"] "+marketReturn.destinationDistributionPoint.name
                journalDetails.journal = journalHead
                journalDetails.dateCreated = dateNow
                journalDetails.userCreated = (ApplicationUser) object
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

                /***************************** CR journal Factory ***************************/
                ChartOfAccountsMapping chartOfAccountsMappingAccountsReceivable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_RECEIVABLE)
                if (!chartOfAccountsMappingAccountsReceivable) {
                    return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable Head is not mapped with Chart of Accounts")
                }
                journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsReceivable.chartOfAccounts
                journalDetails.prefixCode = marketReturn.primaryCustomer.code //(marketReturn.destinationDistributionPoint != null) ? marketReturn.destinationDistributionPoint.code : null
                journalDetails.postfixCode = marketReturn.destinationDistributionPoint.code
                journalDetails.debitAmount = 0.00
                journalDetails.creditAmount = processMarketReturnDetailsList[i].qcQuantity * processMarketReturnDetailsList[i].rate
                journalDetails.particular = "Accounts Receivable from Distribution point: ["+marketReturn.destinationDistributionPoint.code+"] "+marketReturn.destinationDistributionPoint.name
                journalDetails.journal = journalHead
                journalDetails.dateCreated = dateNow
                journalDetails.userCreated = (ApplicationUser) object
                if (!journalDetails.validate()) {
                    return this.getValidationErrorMessage(journalDetails)
                }
                journalDetailsList.add(journalDetails)

// Branch
                if(marketReturn.isDpCustomer == true) {
//                    if(processMarketReturnDetailsList[i].qcQuantity>0)   // Accepted Quantity
//                    {

                        /***************************** DR journal Branch ***************************/
                        ChartOfAccountsMapping chartOfAccountsMappingAccountsPayable = ChartOfAccountsMapping.findByCoaType(COAType.ACCOUNTS_PAYABLE)
                        if (!chartOfAccountsMappingAccountsPayable) {
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Payable Head is not mapped with Chart of Accounts")
                        }
                        journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsPayable.chartOfAccounts
                        journalDetails.prefixCode = marketReturn.destinationDistributionPoint?.code//(marketReturn.sourceDistributionPoint!=null)?marketReturn.sourceDistributionPoint.code:null
                        journalDetails.postfixCode = marketReturn.sourceDistributionPoint?.code
                        journalDetails.debitAmount = processMarketReturnDetailsList[i].qcQuantity *processMarketReturnDetailsList[i].rate
                        journalDetails.creditAmount = 0.00
                        journalDetails.particular = "Accounts Payable to Distribution point: ["+marketReturn.destinationDistributionPoint.code+"] "+marketReturn.destinationDistributionPoint.name
                        journalDetails.journal = journalHead
                        journalDetails.dateCreated = dateNow
                        journalDetails.userCreated = (ApplicationUser) object
                        if (!journalDetails.validate()) {
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)


                        /***************************** CR journal Branch ***************************/
                        ChartOfAccountsMapping chartOfAccountsMappingPurchase = ChartOfAccountsMapping.findByCoaType(COAType.PURCHASE)
                        if (!chartOfAccountsMappingPurchase) {
                            return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Purchase Head is not mapped with Chart of Accounts")
                        }
                        journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                        journalDetails.chartOfAccounts = chartOfAccountsMappingPurchase.chartOfAccounts
                        journalDetails.prefixCode = ""
                        journalDetails.postfixCode = marketReturn.sourceDistributionPoint?.code
                        journalDetails.debitAmount = 0.00
                        journalDetails.creditAmount = processMarketReturnDetailsList[i].qcQuantity*processMarketReturnDetailsList[i].rate
                        journalDetails.particular = "Purchase from Customer: ["+marketReturn.primaryCustomer.code+"] "+marketReturn.primaryCustomer.name
                        journalDetails.journal = journalHead
                        journalDetails.dateCreated = dateNow
                        journalDetails.userCreated = (ApplicationUser) object
                        if (!journalDetails.validate()) {
                            return this.getValidationErrorMessage(journalDetails)
                        }
                        journalDetailsList.add(journalDetails)

                   // }

                }

//QC Failed
                if(processMarketReturnDetailsList[i].qcFailedQuantity>0) { // Rejected Quantity
                    /***************************** DR journal Branch ***************************/
                    ChartOfAccountsMapping chartOfAccountsMappingAccountsActualLeakOrShort = ChartOfAccountsMapping.findByCoaType(COAType.ACTUAL_LEAK_OR_SHORT)
                    if (!chartOfAccountsMappingAccountsActualLeakOrShort) {
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Actual Leak/Short Head is not mapped with Chart of Accounts")
                    }
                    journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingAccountsActualLeakOrShort.chartOfAccounts
                    journalDetails.prefixCode = ""
                    journalDetails.postfixCode = marketReturn.sourceDistributionPoint?.code
                    journalDetails.debitAmount = processMarketReturnDetailsList[i].qcFailedQuantity*processMarketReturnDetailsList[i].rate
                    journalDetails.creditAmount = 0.00
                    journalDetails.particular = "Actual Leak/Short for the Customer: ["+marketReturn.primaryCustomer.code+"] "+marketReturn.primaryCustomer.name
                    journalDetails.journal = journalHead
                    journalDetails.dateCreated = dateNow
                    journalDetails.userCreated = (ApplicationUser) object
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)


                    /***************************** CR journal Branch ***************************/
                    ChartOfAccountsMapping chartOfAccountsMappingPurchase = ChartOfAccountsMapping.findByCoaType(COAType.PURCHASE)
                    if (!chartOfAccountsMappingPurchase) {
                        return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Purchase Head is not mapped with Chart of Accounts")
                    }
                    journalDetails = new JournalDetails(userCreated: (ApplicationUser) object, journal: journalHead, isActive: true)
                    journalDetails.chartOfAccounts = chartOfAccountsMappingPurchase.chartOfAccounts
                    journalDetails.prefixCode = ""
                    journalDetails.postfixCode = marketReturn.sourceDistributionPoint?.code
                    journalDetails.debitAmount = 0.00
                    journalDetails.creditAmount = processMarketReturnDetailsList[i].qcFailedQuantity*processMarketReturnDetailsList[i].rate
                    journalDetails.particular = "Purchase Distribution Point: ["+marketReturn.destinationDistributionPoint.code+"] "+ marketReturn.destinationDistributionPoint.name
                    journalDetails.journal = journalHead
                    journalDetails.dateCreated = dateNow
                    journalDetails.userCreated = (ApplicationUser) object
                    if (!journalDetails.validate()) {
                        return this.getValidationErrorMessage(journalDetails)
                    }
                    journalDetailsList.add(journalDetails)

                }

            }


            /***************************** COA Entry End ***************************/

            Map map = [:]
            map.put('processMarketReturn', processMarketReturn)
            map.put('processMarketReturnDetailsList', processMarketReturnDetailsList)
            map.put('marketReturn', marketReturn)
            map.put('finishGoodStocks', finishGoodStocks)
            map.put('finishGoodStockTransactions', finishGoodStockTransactions)
            map.put('distributionPointStocks', distributionPointStocks)
            map.put('distributionPointStockTransactions', distributionPointStockTransactions)

            map.put('journalHead', journalHead)
            map.put('journalDetailsList', journalDetailsList)

            message = this.preCondition(params, map)
            if (message.type == 1) {
                int noOfRows = processMarketReturnService.create(map)
                if (noOfRows > 0) {
                    message = this.getMessage(processMarketReturn, Message.SUCCESS, 'Market Return Processed Successfully')
                } else {
                    message = this.getMessage(processMarketReturn, Message.ERROR, 'Market Return Could Not Be Processed.')
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

}