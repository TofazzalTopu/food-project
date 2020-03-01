package com.bits.bdfp.common

import com.bits.bdfp.common.district.CreateDistrictAction
import com.bits.bdfp.common.district.DeleteDistrictAction
import com.bits.bdfp.common.district.ListDistrictAction
import com.bits.bdfp.common.district.UpdateDistrictAction
import com.bits.bdfp.common.district.ReadDistrictAction
import com.bits.bdfp.common.district.SearchDistrictAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class DistrictController {

    @Autowired
    private CreateDistrictAction createDistrictAction
    @Autowired
    private UpdateDistrictAction updateDistrictAction
    @Autowired
    private ListDistrictAction listDistrictAction
    @Autowired
    private DeleteDistrictAction deleteDistrictAction
    @Autowired
    private ReadDistrictAction readDistrictAction
    @Autowired
    private SearchDistrictAction searchDistrictAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listDistrictAction.execute(params, null)
        render listDistrictAction.postCondition(null, list) as JSON
    }

    def show = {
        District district = new District()
        render(template: "show", model: [district: district])
    }

    def create = {
        ApplicationUser applicationUser=session?.applicationUser
        District district = new District(params)
        District districtInstance = createDistrictAction.preCondition(applicationUser, district)
        Message message = null
        if (districtInstance == null) {
            message = createDistrictAction.getValidationErrorMessage(district)
        } else {
            districtInstance = createDistrictAction.execute(null, districtInstance)
            if (districtInstance) {
                message = createDistrictAction.getMessage(districtInstance,Message.SUCCESS, "District Created Successfully")
            } else {
                message = createDistrictAction.getMessage(district,Message.ERROR, createDistrictAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readDistrictAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser=session?.applicationUser
        District district = new District(params)
        Object object = updateDistrictAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateDistrictAction.getValidationErrorMessage(district)
        } else {
            int noOfRows = (int) updateDistrictAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateDistrictAction.getMessage(district,Message.SUCCESS, "District Updated Successfully")
            } else {
                message = updateDistrictAction.getMessage(district,Message.ERROR, updateDistrictAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        District district = deleteDistrictAction.preCondition(params, null);
        Message message = null
        if (district) {
            int rowCount = (int) deleteDistrictAction.execute(null, district);
            if (rowCount > 0) {
                message = deleteDistrictAction.getMessage(district,Message.SUCCESS, deleteDistrictAction.SUCCESS_DELETE);
            } else {
                message = deleteDistrictAction.getMessage(district,Message.ERROR, deleteDistrictAction.FAIL_DELETE);
            }
        } else {
            message = deleteDistrictAction.getMessage(district,Message.ERROR, deleteDistrictAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        District district = searchDistrictAction.execute(params.fieldName, params.fieldValue)
        if (district) {
            render district as JSON
        } else {
            render ''
        }

    }
}
