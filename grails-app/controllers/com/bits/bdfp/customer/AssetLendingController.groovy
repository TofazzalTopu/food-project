package com.bits.bdfp.customer

import com.bits.bdfp.settings.enterpriseconfiguration.EnterpriseAutocompleteListAction
import org.springframework.beans.factory.annotation.Autowired


class AssetLendingController {

    @Autowired
    EnterpriseAutocompleteListAction enterpriseAutocompleteListAction


    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static defaultAction = "show"

    def list = { }

    def show = {}

    def create = {}

    def edit = {}

    def update = {}

    def delete = {}

    def search = {}
}
