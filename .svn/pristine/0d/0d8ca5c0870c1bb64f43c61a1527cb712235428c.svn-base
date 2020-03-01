package com.bits.bdfp.inventory.product.packagetype

import com.bits.bdfp.ValidationCheckService
import com.bits.bdfp.inventory.product.PackageType
import com.bits.bdfp.inventory.product.PackageTypeService
import com.docu.common.Action
import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory

@Component("updatePackageTypeAction")
class UpdatePackageTypeAction extends Action {
    private static final Log log = LogFactory.getLog(this)
    @Autowired
    PackageTypeService packageTypeService
    @Autowired
    ValidationCheckService validationCheckService
    Message message

    public Object preCondition(Object object, Object params) {
        Boolean isError = false
        try{

        ApplicationUser applicationUser=(ApplicationUser)object
        PackageType packageType = PackageType.read(Long.parseLong(params?.id?.toString()))
        packageType.properties = params
        packageType.userUpdated=applicationUser
        packageType.dateUpdated=new Date()


            String domain = 'package_type'
            String id =  packageType.id

            isError = validationCheckService.validationCheck(domain,id)

            if (!packageType.validate()) {
                message = this.getValidationErrorMessage(packageType)
                return message
            }
            else if (isError){
                message = this.getMessage('PackageType', Message.ERROR, 'This Package Type has already been used')
                return message
            }
            else{
                return packageType
            }


        }catch (Exception ex) {
            log.error(ex.message)
            throw ex
        }
    }

    public Object postCondition(Object params, Object object) {
        //not implement
        return null
    }

    public Object execute(Object params, Object object) {


        try {
            ApplicationUser applicationUser = (ApplicationUser) object

            Object result = this.preCondition(applicationUser, params)
            if (result instanceof PackageType) {
                int noOfRows = (int) packageTypeService.update(result)
                if (noOfRows > 0) {
                    message = this.getMessage(result, Message.SUCCESS, this.SUCCESS_UPDATE)
                } else {
                    message = this.getMessage(result, Message.ERROR, this.FAIL_UPDATE)
                }
            }

            return message;
        } catch (Exception ex) {
            log.error(ex.message)
            message= this.getMessage("PackageType", Message.ERROR, "Exception-${ex.message}")
            return message;
        }

    }
}
