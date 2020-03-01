package com.bits.bdfp.inventory.warehouse.transferproducts

import com.bits.bdfp.accounts.COAType
import com.bits.bdfp.accounts.ChartOfAccountsMapping
import com.bits.bdfp.accounts.JournalDetailsService


import com.bits.bdfp.accounts.Journal
import com.bits.bdfp.accounts.JournalDetails
import com.bits.bdfp.accounts.JournalStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.sales.DistributionPointStock
import com.bits.bdfp.inventory.sales.DistributionPointStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodStock
import com.bits.bdfp.inventory.warehouse.FinishGoodStockTransaction
import com.bits.bdfp.inventory.warehouse.FinishGoodWarehouseDetails
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransfer
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferService
import com.bits.bdfp.inventory.warehouse.InventoryToInventoryTransferStatus
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.util.ApplicationConstants
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by liton.miah on 4/27/2016.
 */
@Component("createInventoryToInventoryTransferAction")
class CreateInventoryToInventoryTransferAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    private final String MESSAGE_HEADER = 'Message'
    @Autowired
    InventoryToInventoryTransferService inventoryToInventoryTransferService
    @Autowired
    SessionFactory sessionFactory
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            InventoryToInventoryTransfer inventoryToInventoryTransfer = (InventoryToInventoryTransfer) object

            if (!inventoryToInventoryTransfer.validate()) {
                message = this.getValidationErrorMessage(inventoryToInventoryTransfer)
            }else{
                message = this.getMessage("Inventory ToInventory Transfer", Message.SUCCESS, SUCCESS_SAVE)
            }
            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object params, Object user) {
        try {

            Date dateNow = new Date()

            SimpleDateFormat formatMonth = new SimpleDateFormat("MM")
            String currentMonth = formatMonth.format(dateNow)
            SimpleDateFormat formatYear = new SimpleDateFormat("YY")
            String currentYear = formatYear.format(dateNow)
            SimpleDateFormat formatDay = new SimpleDateFormat ("DD")
            String currentDay = formatDay.format(dateNow)

            ApplicationUser applicationUser = (ApplicationUser) user

            InventoryToInventoryTransfer inventoryToInventoryTransfer = new InventoryToInventoryTransfer(params)
            inventoryToInventoryTransfer.transferNo=CodeGenerationUtil.generate(8).toString()
            inventoryToInventoryTransfer.transferChallanStatus = InventoryToInventoryTransferStatus.TRANSFER_IN_TRANSIT
            inventoryToInventoryTransfer.userCreated = applicationUser
            inventoryToInventoryTransfer.dateCreated = new Date()

            DistributionPoint distributionPoint = DistributionPoint.get(Long.parseLong(params.senderDp.id))
            FinishGoodStock finishGoodStock = null
            FinishGoodStockTransaction finishGoodStockTransaction = null
            DistributionPointStock distributionPointStock = null
            DistributionPointStockTransaction distributionPointStockTransaction = null

            if(distributionPoint.isFactory){
                finishGoodStock = FinishGoodStock.get(Long.parseLong(params.dpsId))

                if(finishGoodStock.inQuantity - finishGoodStock.outQuantity >= Float.parseFloat(params.transferQty)){
                    finishGoodStock.outQuantity += Float.parseFloat(params.transferQty)
                    finishGoodStock.userUpdated = applicationUser
                    finishGoodStock.lastUpdated = new Date()

                    if(finishGoodStock){
                        finishGoodStockTransaction = new FinishGoodStockTransaction(
                                finishGoodStock: finishGoodStock,
                                transactionDate:new Date(),
                                inQuantity:0,
                                unitPrice:Float.parseFloat(params.unitPrice),
                                outQuantity:Float.parseFloat(params.transferQty),

                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )
                    }
                }else{
                    return this.getMessage("Inventory To Inventory Transfer", Message.ERROR, "Transfer quantity must less than or equal to available quantity.")
                }
            }else{
                distributionPointStock = DistributionPointStock.get(Long.parseLong(params.dpsId))

                if(distributionPointStock.inQuantity - distributionPointStock.outQuantity >= Float.parseFloat(params.transferQty)){
                    distributionPointStock.outQuantity += Float.parseFloat(params.transferQty)
                    distributionPointStock.userUpdated = applicationUser
                    distributionPointStock.lastUpdated = new Date()

                    if(distributionPointStock){
                        distributionPointStockTransaction = new DistributionPointStockTransaction(
                                distributionPointStock:distributionPointStock,
                                transactionDate:new Date(),
                                inQuantity:0,
                                unitPrice:Float.parseFloat(params.unitPrice),
                                outQuantity:Float.parseFloat(params.transferQty),

                                userCreated: applicationUser,
                                dateCreated: new Date()
                        )
                    }
                }else{
                    return this.getMessage("Inventory To Inventory Transfer", Message.ERROR, "Transfer quantity must less than or equal to available quantity.")
                }
            }

//            FinishGoodWarehouseDetails finishGoodWarehouseDetails = FinishGoodWarehouseDetails.get(Long.parseLong(params.fgwdId))
//            finishGoodWarehouseDetails.quantity = finishGoodWarehouseDetails.quantity - Float.parseFloat(params.transferQty)

            Map mapCreate = [:]
            mapCreate.put("inventoryToInventoryTransfer",inventoryToInventoryTransfer)
            mapCreate.put("finishGoodStock",finishGoodStock)
            mapCreate.put("finishGoodStockTransaction",finishGoodStockTransaction)
            mapCreate.put("distributionPointStock",distributionPointStock)
            mapCreate.put("distributionPointStockTransaction",distributionPointStockTransaction)


            /******************COA start**********************/
            DistributionPoint dPSender=DistributionPoint.read(Long.parseLong(params.senderDp.id))
            DistributionPoint dPReceiver=DistributionPoint.read(Long.parseLong(params.receiverDp.id))
            EnterpriseConfiguration enterpriseConfiguration=DistributionPoint.read(Long.parseLong(params.senderDp.id)).enterpriseConfiguration//.enterpriseConfiguration
            Journal journalHead = new Journal(enterprise: enterpriseConfiguration,userCreated: applicationUser, isActive: true)
            journalHead.tableName = sessionFactory.getClassMetadata(InventoryToInventoryTransfer).tableName
            journalHead.transactionDate = new Date()

            journalHead.journalStatus = JournalStatus.CHECKED
            journalHead.transactionNo = inventoryToInventoryTransfer.transferNo
            journalHead.journalNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "JOURNAL", "", "", "", "", "", currentMonth, currentYear, "", currentDay)
            journalHead.moduleName = ApplicationConstants.MODULE_INVENTORY_TO_INVENTORY_TRANSFER
            if(!journalHead.validate()){
                throw new RuntimeException(this.getValidationErrorMessage(journalHead).messageBody[0].toString())
            }
            ChartOfAccountsMapping chartOfAccountsCurrentAccountWithHo =  ChartOfAccountsMapping.findByCoaType(COAType.CURRENT_ACCOUNT_WITH_HO)
            if(!chartOfAccountsCurrentAccountWithHo){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Current Account With HO head is not mapped with Chart of Accounts")
            }

            ChartOfAccountsMapping chartOfAccountsPurchase =  ChartOfAccountsMapping.findByCoaType(COAType.PURCHASE)
            if(!chartOfAccountsPurchase){
                return this.getMessage(MESSAGE_HEADER, Message.ERROR, "Accounts Receivable head is not mapped with Chart of Accounts")
            }

            Float transferAmount= Float.parseFloat(params.unitPrice)*Float.parseFloat(params.transferQty)
            List<JournalDetails> journalDetailsList = new ArrayList<JournalDetails>()
            journalDetailsList = inventoryToInventoryTransferService.getJournalDetailsList(transferAmount,journalHead, applicationUser,
                    chartOfAccountsCurrentAccountWithHo.chartOfAccounts, chartOfAccountsPurchase.chartOfAccounts,dPSender,dPReceiver)


            mapCreate.put("journalHead", journalHead)
            mapCreate.put("journalDetailsList", journalDetailsList)

            message = preCondition(params, inventoryToInventoryTransfer)
            //inventoryToInventoryTransferService.processInventoryToInventoryTransfer(mapInstance)

            /******************COA end**********************/



            if(message.type == 1){
                InventoryToInventoryTransfer inventoryToInventoryTransferRow = inventoryToInventoryTransferService.createInventoryToInventoryTransfer(mapCreate)
                if (inventoryToInventoryTransferRow) {
                    message = this.getMessage("Inventory ToInventory Transfer", Message.SUCCESS, "Inventory ToInventory Transfer Created Successfully.")
                } else {
                    message = this.getMessage("Inventory ToInventory Transfer", Message.ERROR, FAIL_SAVE)
                }
            }

            return message
        }catch (Exception ex){
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }

    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}
