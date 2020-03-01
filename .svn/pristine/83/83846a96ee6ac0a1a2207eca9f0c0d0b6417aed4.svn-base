package com.bits.bdfp.bill

import com.bits.bdfp.bill.createbill.CreateBillAction
import com.bits.bdfp.bill.createbill.CreateBillTempAction
import com.docu.common.Message
import com.docu.commons.CommonConstants
import grails.converters.JSON
import org.omg.CORBA.Object
import org.springframework.beans.factory.annotation.Autowired

import java.text.SimpleDateFormat

class CreateBillTempController {

    @Autowired
    private CreateBillTempAction createBillTempAction

    @Autowired
    private CreateBillTempService createBillTempService

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index = {}


    def create = {
        CreateBill createBill = CreateBill.find("from CreateBill order by id desc")
        //Object bill= CreateBill.executeQuery(""" from CreateBill where id = ( select max(id) from CreateBill) """)
        //String maxBill=bill.billNumber
        //Long billNo = createBill.id + 1
        //params.billNumber = "Bill" + billNo



        //This delete process execute slower
        //CreateBillTemp.findAll().each { it.delete() }

        //This delete process execute faster
        CreateBillTemp.executeUpdate("delete CreateBillTemp");

        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        Date dt= new Date()
        params.billGenerationDate=sdf.format(dt)

        Message message = createBillTempAction.execute(params, null)
        render message as JSON
    }

   /* def delete = {
        Message message = createBillTempAction.executeDelete()
        render message as JSON
    }*/

    def viewReportBeforeCreateBill = {
        Message message = new Message();
        String js;

        List list = createBillTempAction.executeReport(params, null)
        if (list.size() > 0) {
            Map map = createBillTempAction.postCondition(params, null)
            chain(controller: 'docuJasper', action: 'index', model: [data: list], params: map.params)
        } else {
            render ("No Data Found")
        }
        js = "<script>parent.MessageRenderer.render(${message as JSON})</script>";
        render js
    }
}
