package com.bits.bdfp.bill.createbill

import com.bits.bdfp.bill.CreateBillTemp
import com.bits.bdfp.bill.CreateBillTempService
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.inventory.demandorder.Invoice
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import com.docu.common.Message
import com.docu.common.Action
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService

import java.sql.Timestamp
import java.text.SimpleDateFormat
//import java.util.*;

@Component("CreateBillAction")
class CreateBillAction extends Action{
    public static final Log log = LogFactory.getLog(CreateBillAction.class)
    private final String MESSAGE_HEADER = 'New Create Bill'
    private final String MESSAGE_SUCCESS = 'Bill Created Successfully'
   // private final String MESSAGE_FAILED = 'Create Bill not saved'

    @Autowired
    CreateBillService createBillService
    @Autowired
    SpringSecurityService springSecurityService



    public Object preCondition(Object user, def object) {

        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            CreateBill createBill  = (CreateBill) object
            createBill.userCreated = applicationUser
            createBill.dateCreated = new Date()
            if (!createBill.validate()) {
                return null
            }
            return createBill
        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    public Object execute(def params, def object) {
        try {

            def user=(ApplicationUser) springSecurityService?.getCurrentUser()

            ApplicationUser applicationUser = (ApplicationUser) user

            SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
           // SimpleDateFormat nsdf= new SimpleDateFormat("dd-MM-yyyy");
            //String billNumbr= "Bill" + params.id;
            int length =Integer.parseInt(params.quantity);
            List<CreateBill> createBillList=[]

            params.items.each { key, val ->
                /*for (int i = 0; i < length; i++) {*/
                    if (val instanceof Map) {

                        CreateBill createBill = new CreateBill()
                        createBill.properties = params


                        //createBill.customerId =5
                        createBill.customerMasterId =Integer.parseInt(params.custId)
                        createBill.billNumber =params.billNumber

                       // createBill.billGenerationDate= new Date()
                        createBill.isActive=1
                        createBill.invoiceNumber = val.invoiceNo
                        createBill.purchaseOrderNumber = val.po

                        String dt=val.pod;
                        if(dt) {
                            Date pd = sdf.parse(dt);
                            createBill.purchaseOrderDate = pd //val.pod
                        }
                        createBill.vatChallanNumber = val.vatChallanNo

                        String vat=val.vatChallanDate;
                        if(vat) {
                            Date vatDt = sdf.parse(vat);
                            createBill.vatChallanDate = vatDt
                        }
                        String dat=val.deliveryDate;
                        if(dat) {
                            Date result = sdf.parse(dat);
                            createBill.deliveryDate = result
                        }
                        createBill.receivableAmount = Double.parseDouble(val.receivableAmount)

                        createBill.userCreated=applicationUser
                        createBill.dateCreated=new Date()
                        createBillList.add(createBill);
                    }
                            //}
                        }
                        Map map = new LinkedHashMap()
                        map.put("createBillList",createBillList )
                       /* if (!createBill.validate()) {
                            this.getValidationErrorMessage(createBill)
                        }*/


            Integer row= createBillService.create(map)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)

        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }

    public Object executeReport(Object object, Object data) {
        try {
            return createBillService.getBillInfo(object)
        }catch (Exception ex){
            log.error(ex.message)
            return null;
        }
    }

    @Override
    public Map postCondition(Object params, Object user) {
        try {

            /* String companyName = "City Bank Limited"
             String companyAddress = "136, Gulshan Avenue,1212 Dhaka,Bangladesh."
             String headerTitle = "List of Asset transfer"
             String pagefooter = "Powered By: BRAC IT Services Ltd. (biTS)"
             params.put("companyName", companyName)
             params.put("headerTitle", headerTitle)
             params.put("companyAddress", companyAddress)
             params.put("pagefooter", pagefooter)*/
            params.put("_format", "PDF")
            params.put("_file", "createBill/printBill.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

    /*public Object postCondition(def params, def object) {
        return null
    }*/


}