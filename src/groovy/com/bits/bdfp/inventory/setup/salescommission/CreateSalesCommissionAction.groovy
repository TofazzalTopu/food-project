package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.product.ProductPrice
import com.bits.bdfp.inventory.sales.DistributionPoint
import com.bits.bdfp.inventory.setup.CustomerSalesCommission
import com.bits.bdfp.inventory.setup.ProductSalesCommission
import com.bits.bdfp.inventory.setup.SalesCommission
import com.bits.bdfp.inventory.setup.SalesCommissionService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component("createSalesCommissionAction")
class CreateSalesCommissionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            Map map = (Map) object
            SalesCommission salesCommission = (SalesCommission) map.get("salesCommission")

            List<CustomerSalesCommission> customerSalesCommissionList = map.get("customerSalesCommissionList")
            List<ProductSalesCommission> productSalesCommissionList = map.get("productSalesCommissionList")

            if (!salesCommission.validate()) {
                message = this.getValidationErrorMessage(salesCommission)
            } else {
                message = this.getMessage("Sales Commission", Message.SUCCESS, SUCCESS_SAVE)
            }

//      customerSalesCommissionList.each {
//          CustomerSalesCommission customerSalesCommission =  CustomerSalesCommission.findByCustomerMaster(it.customerMaster)
//          if(customerSalesCommission){
//              message = this.getMessage("Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(customerSalesCommission.customerMaster.id).name}[${CustomerMaster.get(customerSalesCommission.customerMaster.id).code}]' already exist!")
//          }else if(!it.validate()){
//              message = this.getValidationErrorMessage(it)
//          }
//      }

            ////check date overlap
            customerSalesCommissionList.each {
                CustomerSalesCommission existingCustomerSalesCommission = CustomerSalesCommission.findByCustomerMaster(it.customerMaster)
                if (existingCustomerSalesCommission) {
                    if (it.effectiveDateFrom >= existingCustomerSalesCommission.effectiveDateFrom && it.effectiveDateFrom <= existingCustomerSalesCommission.effectiveDateTo) {
                        message = this.getMessage("Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
                    } else if (it.effectiveDateTo <= existingCustomerSalesCommission.effectiveDateTo && it.effectiveDateTo >= existingCustomerSalesCommission.effectiveDateTo) {
                        message = this.getMessage("Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
                    }
                } else if (!it.validate()) {
                    message = this.getValidationErrorMessage(it)
                }
            }
//            List<CustomerSalesCommission> existingCustomerSalesCommissionList = CustomerSalesCommission.findAll()
//            if (existingCustomerSalesCommissionList) {
//                for (int j = 0; j < existingCustomerSalesCommissionList.size(); j++) {
//                    customerSalesCommissionList.each {
//                        if (it.customerMaster.id == existingCustomerSalesCommissionList[j].customerMaster.id) {
//                            if (it.effectiveDateFrom >= existingCustomerSalesCommissionList[j].effectiveDateFrom && it.effectiveDateFrom <= existingCustomerSalesCommissionList[j].effectiveDateTo) {
//                                message = this.getMessage("Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
//                            } else if (it.effectiveDateTo >= existingCustomerSalesCommissionList[j].effectiveDateTo && it.effectiveDateTo <= existingCustomerSalesCommissionList[j].effectiveDateTo) {
//                                message = this.getMessage("Sales Commission", Message.ERROR, "Customer '${CustomerMaster.get(it.customerMaster.id).name}[${CustomerMaster.get(it.customerMaster.id).code}]' Date Conflicts!")
//                            }
//                        }
//                    }
//                }
//            } else {
//                customerSalesCommissionList.each {
//                    if (!it.validate()) {
//                        message = this.getValidationErrorMessage(it)
//
//                    }
//                }
//            }

//
            productSalesCommissionList.each {
                if (!it.validate()) {
                    message = this.getValidationErrorMessage(it)
                }
            }

            if (message.type == 1) {
                message = this.getMessage("Sales Commission", Message.SUCCESS, SUCCESS_SAVE)
            }

            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object execute(Object user, Object params) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) user

            SalesCommission salesCommission = new SalesCommission()
            salesCommission.properties = params

            String date1 = params.dateEffectiveFrom
            Date oldfromDate = Date.parse('dd-MM-yyyy', date1)
            String newfromDate = oldfromDate.format('yyyy-MM-dd')
            Date newEfromDate = Date.parse('yyyy-M-d', newfromDate)


            String date2 = params.dateEffectiveTo
            Date oldtoDate = Date.parse('dd-MM-yyyy', date2)
            String newtoDate = oldtoDate.format('yyyy-MM-dd')
            Date newEtoDate = Date.parse('yyyy-M-d', newtoDate)

            salesCommission.territory = TerritoryConfiguration.get(Long.parseLong(params.territoryId))
            salesCommission.distributionPoint = DistributionPoint.get(Long.parseLong(params.distributionPointId))
            salesCommission.salesCommissionName = params.salesCommissionName

            salesCommission.userCreated = applicationUser
            salesCommission.dateCreated = new Date()


            List<CustomerSalesCommission> customerSalesCommissionList = []
            List<ProductSalesCommission> productSalesCommissionList = []

            params.customerList.each { key, val ->
                if (val instanceof Map) {
                    CustomerSalesCommission customerSalesCommission = new CustomerSalesCommission()
                    customerSalesCommission.salesCommission = salesCommission
                    customerSalesCommission.customerMaster = CustomerMaster.read(val.customerMaster)
                    customerSalesCommission.userCreated = applicationUser
                    customerSalesCommission.effectiveDateFrom = newEfromDate
                    customerSalesCommission.effectiveDateTo = newEtoDate
                    customerSalesCommission.dateCreated = new Date()

                    params.productList.each { keyProd, valProd ->
                        if (valProd instanceof Map) {
                            ProductSalesCommission productSalesCommission = new ProductSalesCommission()
//                          productSalesCommission.salesCommission = salesCommission
//                          productSalesCommission.customerMaster = customerSalesCommissionList[i].customerMaster
                            productSalesCommission.customerSalesCommission = customerSalesCommission
                            productSalesCommission.finishProduct = FinishProduct.read(valProd.productId)
                            productSalesCommission.userCreated = applicationUser
                            productSalesCommission.dateCreated = new Date()

                            productSalesCommissionList.add(productSalesCommission)
                        }
                    }

                    customerSalesCommissionList.add(customerSalesCommission)
                }
            }


            Map map = [:]
            map.put("salesCommission", salesCommission)
            map.put("customerSalesCommissionList", customerSalesCommissionList)
            map.put("productSalesCommissionList", productSalesCommissionList)

            message = this.preCondition(params, map)

            if (message.type == 1) {
                SalesCommission rows = salesCommissionService.create(map)
                if (rows) {
                    message = this.getMessage("Sales Commission", Message.SUCCESS, "Sales Commission Created Successfully.")
                } else {
                    message = this.getMessage("Sales Commission", Message.ERROR, FAIL_SAVE)
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