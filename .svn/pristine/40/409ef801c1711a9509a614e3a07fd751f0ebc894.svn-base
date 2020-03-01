package com.bits.bdfp.inventory.product

import com.bits.bdfp.inventory.product.packagetype.CreatePackageTypeAction
import com.bits.bdfp.inventory.product.packagetype.DeletePackageTypeAction
import com.bits.bdfp.inventory.product.packagetype.ListPackageTypeAction
import com.bits.bdfp.inventory.product.packagetype.UpdatePackageTypeAction
import com.bits.bdfp.inventory.product.packagetype.ReadPackageTypeAction
import com.bits.bdfp.inventory.product.packagetype.SearchPackageTypeAction
import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class PackageTypeController {

    @Autowired
    private CreatePackageTypeAction createPackageTypeAction
    @Autowired
    private UpdatePackageTypeAction updatePackageTypeAction
    @Autowired
    private ListPackageTypeAction listPackageTypeAction
    @Autowired
    private DeletePackageTypeAction deletePackageTypeAction
    @Autowired
    private ReadPackageTypeAction readPackageTypeAction
    @Autowired
    private SearchPackageTypeAction searchPackageTypeAction
    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction
    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listPackageTypeAction.execute(params, null)
        render listPackageTypeAction.postCondition(null, list) as JSON
    }

    def show = {
        PackageType packageType = new PackageType()
        ApplicationUser applicationUser = session?.applicationUser
        Map result =[:]
        List enterpriseList = enterpriseAutocompleteListAction.execute(applicationUser, null)
        if (enterpriseList && enterpriseList.size()>0){
            result = ["results": enterpriseList, "total": enterpriseList.size()]
        }
        render(template: "show", model: [packageType: packageType,list: enterpriseList,result:result as JSON])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        PackageType packageType = new PackageType(params)
        PackageType packageTypeInstance = createPackageTypeAction.preCondition(applicationUser, packageType)
        Message message = null
        if (packageTypeInstance == null) {
            message = createPackageTypeAction.getValidationErrorMessage(packageType)
        } else {
            packageTypeInstance = createPackageTypeAction.execute(null, packageTypeInstance)
            if (packageTypeInstance) {
                message = createPackageTypeAction.getMessage(packageTypeInstance, Message.SUCCESS, createPackageTypeAction.SUCCESS_SAVE)
            } else {
                message = createPackageTypeAction.getMessage(packageType,Message.ERROR, createPackageTypeAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        Map result = readPackageTypeAction.execute(params, null)
        render result as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        Message message = updatePackageTypeAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deletePackageTypeAction.execute(params, null)
        render message as JSON
    }


    def search = {
        PackageType packageType = searchPackageTypeAction.execute(params.fieldName, params.fieldValue)
        if (packageType) {
            render packageType as JSON
        } else {
            render ''
        }

    }
}
