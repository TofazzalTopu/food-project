package com.bits.bdfp.inventory.setup.salescommission

import com.bits.bdfp.customer.CustomerMaster
import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.product.FinishProduct
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

@Component("createProductSalesCommissionAction")
class CreateProductSalesCommissionAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    SalesCommissionService salesCommissionService
    Message message

    public Object preCondition(Object params, Object object) {
        try {
            ProductSalesCommission productSalesCommission = (ProductSalesCommission) object
            if (!productSalesCommission.validate()) {
                message = this.getValidationErrorMessage(productSalesCommission)
            } else {
                message = this.getMessage(productSalesCommission, Message.SUCCESS, this.SUCCESS_SAVE)
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            message = this.getMessage("ProductSalesCommission", Message.ERROR, "Exception-${ex.message}")
            return message
        }

    }

    public Object preConditionDeleteCustomerSalesCommission(Object params, Object object) {
        try {
            Map map = (Map) object
            CustomerSalesCommission customerSalesCommission = (CustomerSalesCommission) map.get("customerSalesCommission")
            List<ProductSalesCommission> productSalesCommissionList = map.get("productSalesCommissionList")
            if (!customerSalesCommission.validate()) {
                message = this.getValidationErrorMessage(customerSalesCommission)
            } else {
                message = this.getMessage("Customer Sales Commission", Message.SUCCESS, SUCCESS_DELETE)
            }


            productSalesCommissionList.each {
                ProductSalesCommission productSalesCommission = ProductSalesCommission.findByCustomerSalesCommission(customerSalesCommission)
                if (!productSalesCommission.validate()) {
                    message = this.getValidationErrorMessage(productSalesCommission)
                }
            }

            if (message.type == 1) {
                message = this.getMessage("Customer Sales Commission", Message.SUCCESS, SUCCESS_DELETE)
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
            ProductSalesCommission productSalesCommission = new ProductSalesCommission()
            productSalesCommission.customerSalesCommission = CustomerSalesCommission.read(Long.parseLong(params.cid))
            productSalesCommission.finishProduct = FinishProduct.read(Long.parseLong(params.productId))
            productSalesCommission.userCreated = applicationUser
            productSalesCommission.dateCreated = new Date()

            message = this.preCondition(null, productSalesCommission)
            if (message.type == 1) {
                productSalesCommission = salesCommissionService.createProduct(productSalesCommission)
                if (productSalesCommission) {
                    message = this.getMessage(productSalesCommission, Message.SUCCESS, this.SUCCESS_SAVE)
                } else {
                    message = this.getMessage(productSalesCommission, Message.ERROR, this.FAIL_SAVE)
                }
            }
            return message
        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object executeDeleteCustomerSalesCommission(Object params, Object object) {
        try {
            CustomerSalesCommission customerSalesCommission = CustomerSalesCommission.read(Long.parseLong(params.cscId))
            List<ProductSalesCommission> productSalesCommissionList = ProductSalesCommission.findAllByCustomerSalesCommission(customerSalesCommission)
            Map map = [:]
            map.put("customerSalesCommission", customerSalesCommission)
            map.put("productSalesCommissionList", productSalesCommissionList)
            message = this.preConditionDeleteCustomerSalesCommission(params, map)
            if (message.type == 1) {
                CustomerSalesCommission customerSalesCommissiondeleted = salesCommissionService.deleteCustomerSalesCommission(map)
                if (customerSalesCommissiondeleted) {
                    message = this.getMessage(customerSalesCommissiondeleted, Message.SUCCESS, this.SUCCESS_DELETE)
                }
            }

            return message

        } catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex.message)
        }
    }

    public Object executeDelete(Object params, Object object) {
        try {
            ProductSalesCommission productSalesCommission = ProductSalesCommission.read(Long.parseLong(params.pid))
            ProductSalesCommission productSalesCommissiondeleted = salesCommissionService.deleteProduct(productSalesCommission)
            if (productSalesCommissiondeleted) {
                message = this.getMessage(productSalesCommissiondeleted, Message.SUCCESS, this.SUCCESS_DELETE)
            } else {
                message = this.getMessage(productSalesCommissiondeleted, Message.ERROR, this.FAIL_DELETE)
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