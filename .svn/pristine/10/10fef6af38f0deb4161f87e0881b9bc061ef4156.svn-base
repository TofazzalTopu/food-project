package com.bits.bdfp.bill.createbill

import com.bits.bdfp.bill.CreateBill
import com.bits.bdfp.bill.CreateBillService
import com.bits.bdfp.bill.CreateBillTemp
import com.bits.bdfp.bill.CreateBillTempService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.plugins.springsecurity.SpringSecurityService
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.SimpleDateFormat

/**
 * Created by mdtofazzal.hossain on 5/17/2016.
 */

@Component("CreateBillTempAction")
class CreateBillTempAction extends Action{
    public static final Log log = LogFactory.getLog(CreateBillTempAction.class)
    private final String MESSAGE_HEADER = 'New Bill Printed'
    private final String MESSAGE_SUCCESS = 'Bill Printed Successfully'
    private final String MESSAGE_FAILED = 'Create Bill not saved'


    @Autowired
    SpringSecurityService springSecurityService
    @Autowired
    CreateBillTempService createBillTempService

    public Object preCondition(Object user, def object) {

        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            CreateBillTemp createBill  = (CreateBillTemp) object
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
            SimpleDateFormat nsdf= new SimpleDateFormat("dd-MM-yyyy");
            //String billNumbr= "Bill" + params.id;
            int length =Integer.parseInt(params.quantity);
           // List<CreateBill> createBillList=[]
            List<CreateBillTemp> createBillTempList=[]

            params.items.each { key, val ->
                /*for (int i = 0; i < length; i++) {*/
                if (val instanceof Map) {

                    CreateBillTemp createBillTemp=new CreateBillTemp()
                    createBillTemp.properties = params
                    //createBill.customerId =5
                    createBillTemp.customerMasterId =Integer.parseInt(params.custId)
                    createBillTemp.billNumber =params.billNumber

                    createBillTemp.billGenerationDate= new Date()

                    createBillTemp.invoiceNumber = val.invoiceNo
                    createBillTemp.purchaseOrderNumber = val.po

                    String dt=val.pod;
                    if(dt) {
                        Date pd = nsdf.parse(dt);
                        createBillTemp.purchaseOrderDate = pd //val.pod
                    }

                    createBillTemp.vatChallanNumber = val.vatChallanNo

                    String vat=val.vatChallanDate;
                    if(vat){
                        Date vatDt = nsdf.parse(vat);
                        createBillTemp.vatChallanDate = vatDt
                    }


                    String dat=val.deliveryDate;
                    if(dat) {
                        Date result = sdf.parse(dat);
                        createBillTemp.deliveryDate = result
                    }
                    //Date date= (Date) sdf.parse(dt)
                    //createBill.deliveryDate = sdf.parse(dt)

                    createBillTemp.receivableAmount = Double.parseDouble(val.receivableAmount)
                    //createBill.customerCategory= customerCategory.id

                    //createBillService.create(createBillMap[i])\


                    createBillTempList.add(createBillTemp)

                }
                //}

            }
            Map map = new LinkedHashMap()
            map.put("createBillTempList",createBillTempList)
            /* if (!createBill.validate()) {
                 this.getValidationErrorMessage(createBill)
             }*/



            Integer roww= createBillTempService.create(map)
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)



        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, ex.message)
        }
    }


    public Object executeReport(Object object, Object data) {
        try {
            return createBillTempService.getBillInfo(object)
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
            params.put("_file", "createBill/newBill.jasper")
            Map map = [params: params]
            return map;

        } catch (Exception ex) {
            log.error(ex.message)
            return null
        }
    }

  /*  public Object executeDelete() {
        try {
            //CreateBill createBill = createBillService.read(Long.parseLong(params.id))
            createBillTempService.delete()
            return this.getMessage(MESSAGE_HEADER, Message.SUCCESS, MESSAGE_SUCCESS)
        } catch (Exception ex) {
            log.error(ex.message)
            return this.getMessage(MESSAGE_HEADER, Message.ERROR, MESSAGE_FAILURE)
        }
    }*/

}
