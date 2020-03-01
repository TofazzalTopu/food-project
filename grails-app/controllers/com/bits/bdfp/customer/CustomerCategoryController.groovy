package com.bits.bdfp.customer

import com.bits.bdfp.customer.customercategory.CreateCustomerCategoryAction
import com.bits.bdfp.customer.customercategory.DeleteCustomerCategoryAction
import com.bits.bdfp.customer.customercategory.ListCustomerCategoryAction
import com.bits.bdfp.customer.customercategory.UpdateCustomerCategoryAction
import com.bits.bdfp.customer.customercategory.ReadCustomerCategoryAction
import com.bits.bdfp.customer.customercategory.SearchCustomerCategoryAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CustomerCategoryController {

    @Autowired
    private CreateCustomerCategoryAction createCustomerCategoryAction
    @Autowired
    private UpdateCustomerCategoryAction updateCustomerCategoryAction
    @Autowired
    private ListCustomerCategoryAction listCustomerCategoryAction
    @Autowired
    private DeleteCustomerCategoryAction deleteCustomerCategoryAction
    @Autowired
    private ReadCustomerCategoryAction readCustomerCategoryAction
    @Autowired
    private SearchCustomerCategoryAction searchCustomerCategoryAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCustomerCategoryAction.execute(params, null)
        render listCustomerCategoryAction.postCondition(null, list) as JSON
    }

    def show = {
        CustomerCategory customerCategory = new CustomerCategory()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [customerCategory: customerCategory, list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        CustomerCategory customerCategory = new CustomerCategory(params)
        CustomerCategory customerCategoryInstance = createCustomerCategoryAction.preCondition(applicationUser, customerCategory)
        Message message = null
        if (customerCategoryInstance == null) {
            message = createCustomerCategoryAction.getValidationErrorMessage(customerCategory)
        } else {
            customerCategoryInstance = createCustomerCategoryAction.execute(null, customerCategoryInstance)
            if (customerCategoryInstance) {
                message = createCustomerCategoryAction.getMessage("Customer Category",Message.SUCCESS, createCustomerCategoryAction.SUCCESS_SAVE)
            } else {
                message = createCustomerCategoryAction.getMessage("Customer Category",Message.ERROR, createCustomerCategoryAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result =readCustomerCategoryAction.execute(params, null)
        render result  as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updateCustomerCategoryAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteCustomerCategoryAction.execute(params, null)
        render message as JSON

    }

    def search = {
        CustomerCategory customerCategory = searchCustomerCategoryAction.execute(params.fieldName, params.fieldValue)
        if (customerCategory) {
            render customerCategory as JSON
        } else {
            render ''
        }

    }

    def customerCategoryDDopDownList = {
    /*
        SELECT DISTINCT
        `customer_category`.`id`
        , `customer_category`.`name`
        FROM
        `customer_category`
        INNER JOIN `customer_master`
        ON (`customer_category`.id = `customer_master`.`category_id`)
        WHERE `customer_master`.`customer_level`  != 'SECONDERY'
        */
    }

    def customerCategoryDropDownList = {
        List list = searchCustomerCategoryAction.executeCustomerCategory(params, null)
        render list as JSON
    }
}
