package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.setup.CustomerSalesCommission
import com.bits.bdfp.inventory.setup.ProductSalesCommission
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.text.DateFormat

@Component("updateCustomerSalesCommissionEffectiveDate")
class UpdateCustomerSalesCommissionEffectiveDate extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    Message message

    public Object preCondition(Object params, Object object) {
        try {

            List<CustomerSalesCommission> customerSalesCommissionList = object
            customerSalesCommissionList.each {
                    if (params.newEfromDate >= it.effectiveDateFrom  &&  params.newEfromDate <= it.effectiveDateTo ) {
//                        message = this.getMessage("Customer Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
//                        return message
                        throw new RuntimeException(CustomerMaster.get(it.customerMaster.id).name+ " has Date Range from " + it.effectiveDateFrom.format('yyyy-MM-dd') +"  To  "+it.effectiveDateTo.format('yyyy-MM-dd'))
                    } else if (params.newEtoDate >= it.effectiveDateFrom  && params.newEtoDate <= it.effectiveDateTo ) {
//                        message = this.getMessage("Customer Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
//                        return message
                        throw new RuntimeException(CustomerMaster.get(it.customerMaster.id).name+ "  has Date Range from " + it.effectiveDateFrom.format('yyyy-MM-dd') + "   To  " +it.effectiveDateTo.format('yyyy-MM-dd'))
                    }
                 else  {
                        message = this.getMessage("Customer Sales Commission", Message.SUCCESS, "Customer Date does not Conflict!")
                    }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("ProductSalesCommission", Message.ERROR, "${ex.message}")
            return message
        }

    }


    public Object execute(Object user, Object params) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user
            String date1 = params.customerEffectiveDateFrom
            Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
            String newfromDate = oldfromDate.format('yyyy-MM-dd')
            Date newEfromDate = Date.parse('yyyy-M-d', newfromDate)


            String date2 = params.customerEffectiveDateTo
            Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
            String newtoDate = oldtoDate.format('yyyy-MM-dd')
            Date newEtoDate = Date.parse('yyyy-M-d', newtoDate)
            params.put('newEfromDate',newEfromDate)
            params.put('newEtoDate',newEtoDate)

            CustomerSalesCommission customerSalesCommission = CustomerSalesCommission.read(Long.parseLong(params.cid))
//            CustomerMaster customerMaster = CustomerMaster.read(customerSalesCommission.customerMaster)
            List<CustomerSalesCommission> customerSalesCommissionList = CustomerSalesCommission.findAllByCustomerMaster(customerSalesCommission.customerMaster)
            if (customerSalesCommissionList) {
                message = this.preCondition(params, customerSalesCommissionList)
            }
            if(message.type == 1){
                customerSalesCommission.effectiveDateFrom = newEfromDate
                customerSalesCommission.effectiveDateTo = newEtoDate
                customerSalesCommission.userUpdated = applicationUser
                customerSalesCommission.lastUpdated = new Date()
                customerSalesCommission = salesCommissionService.update(customerSalesCommission)
                if (customerSalesCommission) {
                    message = this.getMessage(customerSalesCommission, Message.SUCCESS, this.SUCCESS_UPDATE)
                }
            }

//            if (message.type == 1) {
//                productSalesCommission = salesCommissionService.createProduct(productSalesCommission)
//                if (productSalesCommission) {
//                    message = this.getMessage(productSalesCommission, Message.SUCCESS, this.SUCCESS_SAVE)
//                } else {
//                    message = this.getMessage(productSalesCommission, Message.ERROR, this.FAIL_SAVE)
//                }
//            }
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