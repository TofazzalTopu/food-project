package com.bits.bdfp.common

import com.bits.bdfp.common.countryinfo.CreateCountryInfoAction
import com.bits.bdfp.common.countryinfo.DeleteCountryInfoAction
import com.bits.bdfp.common.countryinfo.ListCountryInfoAction
import com.bits.bdfp.common.countryinfo.UpdateCountryInfoAction
import com.bits.bdfp.common.countryinfo.ReadCountryInfoAction
import com.bits.bdfp.common.countryinfo.SearchCountryInfoAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class CountryInfoController {

    @Autowired
    private CreateCountryInfoAction createCountryInfoAction
    @Autowired
    private UpdateCountryInfoAction updateCountryInfoAction
    @Autowired
    private ListCountryInfoAction listCountryInfoAction
    @Autowired
    private DeleteCountryInfoAction deleteCountryInfoAction
    @Autowired
    private ReadCountryInfoAction readCountryInfoAction
    @Autowired
    private SearchCountryInfoAction searchCountryInfoAction

    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        List list = listCountryInfoAction.execute(params, null)
        render listCountryInfoAction.postCondition(null, list) as JSON
    }

    def show = {
        CountryInfo countryInfo = new CountryInfo()
        render(template: "show", model: [countryInfo: countryInfo])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        CountryInfo countryInfo = new CountryInfo(params)
        CountryInfo countryInfoInstance = createCountryInfoAction.preCondition(applicationUser, countryInfo)
        Message message = null
        if (countryInfoInstance == null) {
            message = createCountryInfoAction.getValidationErrorMessage(countryInfo)
        } else {
            countryInfoInstance = createCountryInfoAction.execute(null, countryInfoInstance)
            if (countryInfoInstance) {
                message = createCountryInfoAction.getMessage(countryInfoInstance, Message.SUCCESS, "Country Created Successfully")
            } else {
                message = createCountryInfoAction.getMessage(countryInfo, Message.ERROR, createCountryInfoAction.FAIL_SAVE)
            }
        }
        render message as JSON
    }

    def edit = {
        render readCountryInfoAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        CountryInfo countryInfo = new CountryInfo(params)
        Object object = updateCountryInfoAction.preCondition(params, applicationUser)
        Message message = null
        if (object == false) {
            message = updateCountryInfoAction.getValidationErrorMessage(countryInfo)
        } else {
            int noOfRows = (int) updateCountryInfoAction.execute(null, object)
            if (noOfRows > 0) {
                message = updateCountryInfoAction.getMessage(countryInfo, Message.SUCCESS, "Country Updated Successfully")
            } else {
                message = updateCountryInfoAction.getMessage(countryInfo, Message.ERROR, updateCountryInfoAction.FAIL_UPDATE)
            }
        }
        render message as JSON
    }

    def delete = {
        CountryInfo countryInfo = deleteCountryInfoAction.preCondition(params, null);
        Message message = null
        if (countryInfo) {
            int rowCount = (int) deleteCountryInfoAction.execute(null, countryInfo);
            if (rowCount > 0) {
                message = deleteCountryInfoAction.getMessage(countryInfo, Message.SUCCESS, deleteCountryInfoAction.SUCCESS_DELETE);
            } else {
                message = deleteCountryInfoAction.getMessage(countryInfo, Message.ERROR, deleteCountryInfoAction.FAIL_DELETE);
            }
        } else {
            message = deleteCountryInfoAction.getMessage(countryInfo, Message.ERROR, deleteCountryInfoAction.ALREADY_DELETED);
        }
        render message as JSON;
    }

    def search = {
        CountryInfo countryInfo = searchCountryInfoAction.execute(params.fieldName, params.fieldValue)
        if (countryInfo) {
            render countryInfo as JSON
        } else {
            render ''
        }

    }
}
