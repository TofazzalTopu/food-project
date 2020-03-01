package com.bits.bdfp.settings

import com.bits.bdfp.settings.enterpriseconfiguration.CreateEnterpriseConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.DeleteEnterpriseConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.ListEnterpriseConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.UpdateEnterpriseConfigurationAction
import com.bits.bdfp.settings.enterpriseconfiguration.ReadEnterpriseConfigurationAction

import com.docu.common.Message
import com.docu.security.ApplicationUser
import org.hibernate.Session
import org.springframework.beans.factory.annotation.Autowired
import grails.converters.JSON

class EnterpriseConfigurationController {

    @Autowired
    private CreateEnterpriseConfigurationAction createEnterpriseConfigurationAction
    @Autowired
    private UpdateEnterpriseConfigurationAction updateEnterpriseConfigurationAction
    @Autowired
    private ListEnterpriseConfigurationAction listEnterpriseConfigurationAction
    @Autowired
    private DeleteEnterpriseConfigurationAction deleteEnterpriseConfigurationAction
    @Autowired
    private ReadEnterpriseConfigurationAction readEnterpriseConfigurationAction


    static allowedMethods = [create: "POST", update: "POST", delete: "POST"]

    def list = {
        render listEnterpriseConfigurationAction.execute(params, null) as JSON
    }

    def show = {
        EnterpriseConfiguration enterpriseConfiguration = new EnterpriseConfiguration()
        render(template: "show", model: [enterpriseConfiguration: enterpriseConfiguration])
    }

    def create = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createEnterpriseConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def edit = {
        render readEnterpriseConfigurationAction.execute(params, null) as JSON
    }

    def update = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateEnterpriseConfigurationAction.execute(params, applicationUser)
        render message as JSON
    }

    def delete = {
        Message message = deleteEnterpriseConfigurationAction.execute(params, null)
        render message as JSON
    }

}
