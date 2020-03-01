package com.bits.bdfp.inventory.sales.loadingslip

import com.bits.bdfp.inventory.demandorder.DemandOrderStatus
import com.bits.bdfp.inventory.demandorder.Invoice
import com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder
import com.bits.bdfp.inventory.sales.LoadingSlip
import com.bits.bdfp.inventory.sales.LoadingSlipDetails
import com.bits.bdfp.inventory.sales.LoadingSlipService
import com.bits.bdfp.inventory.setup.DeliveryTruck
import com.bits.bdfp.inventory.warehouse.FinishedProductBooked
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.common.CodeGenerationUtil
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

import java.text.SimpleDateFormat

@Component("createLoadingSlipAction")
class CreateLoadingSlipAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    LoadingSlipService loadingSlipService

    Message message

    public Object preCondition(Object params, Object object) {
        try {
            LoadingSlip loadingSlip = (LoadingSlip) object.get('loadingSlip')
            if (!loadingSlip.validate()) {
                message = this.getValidationErrorMessage(loadingSlip)
            } else {
                message = this.getMessage(loadingSlip, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("LoadingSlip", Message.ERROR, "Exception-${ex.message}")
            return message
        }
    }

    public Object execute(Object params, Object object) {
        try {
            Map map = [:]
            ApplicationUser applicationUser = (ApplicationUser) object
            String [] invoiceIds = params.invoiceIds.split(',')

            List<LoadingSlipDetails> loadingSlipDetailsList = new ArrayList<LoadingSlipDetails>()
            EnterpriseConfiguration enterpriseConfiguration = EnterpriseConfiguration.read(Long.parseLong(params.entId))

            Date dateNow = new Date()
            SimpleDateFormat format = new SimpleDateFormat ("MM")
            String currentMonth = format.format(dateNow)
            format = new SimpleDateFormat ("YY")
            String currentYear = format.format(dateNow)
            format = new SimpleDateFormat ("dd")
            String currentDate = format.format(dateNow)
            DeliveryTruck deliveryTruck = DeliveryTruck.read(Long.parseLong(params.truckId))
            LoadingSlip loadingSlip = new LoadingSlip()
            loadingSlip.loadingSlipNo = CodeGenerationUtil.instance.generateCode(enterpriseConfiguration, "LOADING_SLIP", "", "", "", "", "", currentMonth, currentYear, "", currentDate)
            loadingSlip.dateSlipDate = dateNow
            loadingSlip.printCount = 0
            loadingSlip.dateCreated = dateNow
            loadingSlip.userCreated = applicationUser
            loadingSlip.deliveryTruck = deliveryTruck

            for(int i = 0; i < invoiceIds.length; i++){
                LoadingSlipDetails loadingSlipDetails = new LoadingSlipDetails()
                loadingSlipDetails.loadingSlip = loadingSlip
                loadingSlipDetails.invoice = Invoice.read(Long.parseLong(invoiceIds[i]))
                loadingSlipDetails.dateCreated = dateNow
                loadingSlipDetails.userCreated = applicationUser
                loadingSlipDetailsList.add(loadingSlipDetails)
            }

            map.put('loadingSlip', loadingSlip)
            map.put('loadingSlipDetailsList', loadingSlipDetailsList)
//            map.put('finishedProductBookeds', finishedProductBookeds)
//            map.put('primaryDemandOrders', primaryDemandOrders)
            message = this.preCondition(null, map)
            if (message.type == 1) {
                loadingSlip = loadingSlipService.create(map)
                if (loadingSlip) {
//                    message = this.getMessage(loadingSlip, Message.SUCCESS, this.SUCCESS_SAVE)
                    message = this.getMessage(loadingSlip, Message.SUCCESS, "Loading Slip Created Successfully for Loading Slip No: " + loadingSlip.loadingSlipNo)
                } else {
                    message = this.getMessage(loadingSlip, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object postCondition(Object params, Object object) {
        return null
    }
}