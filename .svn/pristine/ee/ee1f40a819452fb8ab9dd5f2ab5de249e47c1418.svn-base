package com.bits.bdfp.inventory.setup

import com.bits.bdfp.geolocation.TerritoryConfiguration
import com.bits.bdfp.inventory.product.FinishProduct
import com.bits.bdfp.inventory.setup.incentive.QuantityBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.SalesAmountBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.TargetBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.VolumeBasedIncentive
import com.bits.bdfp.inventory.setup.incentive.CreateQuantityBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.CreateSalesAmountBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.CreateTargetBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.CreateVolumeBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.ListAllProductByMasterAndMainProductAction
import com.bits.bdfp.inventory.setup.incentive.ListCcAndCustomerByScAction
import com.bits.bdfp.inventory.setup.incentive.ListCustomerByCcAction
import com.bits.bdfp.inventory.setup.incentive.ListGeoAndCustomerByTerritoryAction
import com.bits.bdfp.inventory.setup.incentive.ListPtAndCustomerByGeoAction
import com.bits.bdfp.inventory.setup.incentive.ListQbUomByProductAction
import com.bits.bdfp.inventory.setup.incentive.ListQuantityBasedCurrentCustomerAction
import com.bits.bdfp.inventory.setup.incentive.ListQuantityBasedIncentiveCurrentSlabAction
import com.bits.bdfp.inventory.setup.incentive.ListSalesAmountBasedCurrentCustomerAction
import com.bits.bdfp.inventory.setup.incentive.ListSalesAmountBasedIncentiveCurrentSlabAction
import com.bits.bdfp.inventory.setup.incentive.ListScAndCustomerByPtAction
import com.bits.bdfp.inventory.setup.incentive.ListTargetBasedCurrentCustomerAction
import com.bits.bdfp.inventory.setup.incentive.ListTargetBasedIncentiveCurrentSlabAction
import com.bits.bdfp.inventory.setup.incentive.ListVbPrimaryUomByProductAction
import com.bits.bdfp.inventory.setup.incentive.ListVolumeBasedCurrentCustomerAction
import com.bits.bdfp.inventory.setup.incentive.ListVolumeBasedIncentiveCurrentSlabAction
import com.bits.bdfp.inventory.setup.incentive.UpdateQuantityBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.UpdateSalesAmountBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.UpdateTargetBasedIncentiveAction
import com.bits.bdfp.inventory.setup.incentive.UpdateVolumeBasedIncentiveAction
//import com.bits.bdfp.inventory.setup.incentive.UtilIncentiveSetupAction
import com.docu.common.Message
import com.docu.security.ApplicationUser
import grails.converters.JSON
import org.springframework.beans.factory.annotation.Autowired

class SetupIncentiveController {
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    static  defaultAction = "show"
    @Autowired
    CreateTargetBasedIncentiveAction createTargetBasedIncentiveAction
    @Autowired
    CreateSalesAmountBasedIncentiveAction createSalesAmountBasedIncentiveAction
    @Autowired
    CreateQuantityBasedIncentiveAction createQuantityBasedIncentiveAction
    @Autowired
    CreateVolumeBasedIncentiveAction createVolumeBasedIncentiveAction
    @Autowired
    UpdateVolumeBasedIncentiveAction updateVolumeBasedIncentiveAction
    @Autowired
    UpdateQuantityBasedIncentiveAction updateQuantityBasedIncentiveAction
    @Autowired
    UpdateSalesAmountBasedIncentiveAction updateSalesAmountBasedIncentiveAction
    @Autowired
    UpdateTargetBasedIncentiveAction updateTargetBasedIncentiveAction
//    @Autowired
//    UtilIncentiveSetupAction utilIncentiveSetupAction
    @Autowired
    ListTargetBasedIncentiveCurrentSlabAction listTargetBasedIncentiveCurrentSlabAction
    @Autowired
    ListTargetBasedCurrentCustomerAction listTargetBasedCurrentCustomerAction
    @Autowired
    ListSalesAmountBasedIncentiveCurrentSlabAction listSalesAmountBasedIncentiveCurrentSlabAction
    @Autowired
    ListSalesAmountBasedCurrentCustomerAction listSalesAmountBasedCurrentCustomerAction
    @Autowired
    ListQuantityBasedIncentiveCurrentSlabAction listQuantityBasedIncentiveCurrentSlabAction
    @Autowired
    ListQuantityBasedCurrentCustomerAction listQuantityBasedCurrentCustomerAction
    @Autowired
    ListVolumeBasedIncentiveCurrentSlabAction listVolumeBasedIncentiveCurrentSlabAction
    @Autowired
    ListVolumeBasedCurrentCustomerAction listVolumeBasedCurrentCustomerAction
    @Autowired
    ListGeoAndCustomerByTerritoryAction listGeoAndCustomerByTerritoryAction
    @Autowired
    ListPtAndCustomerByGeoAction listPtAndCustomerByGeoAction
    @Autowired
    ListScAndCustomerByPtAction listScAndCustomerByPtAction
    @Autowired
    ListCcAndCustomerByScAction listCcAndCustomerByScAction
    @Autowired
    ListCustomerByCcAction listCustomerByCcAction
    @Autowired
    ListQbUomByProductAction listQbUomByProductAction
    @Autowired
    ListAllProductByMasterAndMainProductAction listAllProductByMasterAndMainProductAction
    @Autowired
    ListVbPrimaryUomByProductAction listVbPrimaryUomByProductAction

    def show = {
        List<TerritoryConfiguration> territoryConfigurationList = TerritoryConfiguration.list()
        List<FinishProduct> productList = FinishProduct.list()

        TargetBasedIncentive targetBasedIncentive = null
        List targetBasedIncentiveSlabList  = []
        List targetBasedIncentiveCustomersList  = []

        SalesAmountBasedIncentive salesAmountBasedIncentive = null
        List salesAmountBasedIncentiveSlabList  = []
        List salesAmountBasedIncentiveCustomersList  = []

        QuantityBasedIncentive quantityBasedIncentive = null
        List quantityBasedIncentiveSlabList  = []
        List quantityBasedIncentiveCustomersList  = []

        VolumeBasedIncentive volumeBasedIncentive = null
        List volumeBasedIncentiveSlabList  = []
        List volumeBasedIncentiveCustomersList  = []

        if(params.id){
            if(params.type == 'tbi'){
                targetBasedIncentive = TargetBasedIncentive.get(params.id)
                targetBasedIncentiveSlabList = (List) listTargetBasedIncentiveCurrentSlabAction.execute(targetBasedIncentive, null)
                targetBasedIncentiveCustomersList = (List) listTargetBasedCurrentCustomerAction.execute(targetBasedIncentive, null)
            }
            if(params.type == 'sabi'){
                salesAmountBasedIncentive = SalesAmountBasedIncentive.get(params.id)
                salesAmountBasedIncentiveSlabList = (List) listSalesAmountBasedIncentiveCurrentSlabAction.execute(salesAmountBasedIncentive, null)
                salesAmountBasedIncentiveCustomersList = (List) listSalesAmountBasedCurrentCustomerAction.execute(salesAmountBasedIncentive, null)
            }
            if(params.type == 'qbi'){
                quantityBasedIncentive = QuantityBasedIncentive.get(params.id)
                quantityBasedIncentiveSlabList = (List) listQuantityBasedIncentiveCurrentSlabAction.execute(quantityBasedIncentive, null)
                quantityBasedIncentiveCustomersList = (List) listQuantityBasedCurrentCustomerAction.execute(quantityBasedIncentive, null)
            }
            if(params.type == 'vbi'){
                volumeBasedIncentive = VolumeBasedIncentive.get(params.id)
                volumeBasedIncentiveSlabList = (List) listVolumeBasedIncentiveCurrentSlabAction.execute(volumeBasedIncentive, null)
                volumeBasedIncentiveCustomersList = (List) listVolumeBasedCurrentCustomerAction.execute(volumeBasedIncentive, null)
            }
        }

        render(view: "show", model: [productList:productList, incentiveType:params.type,
                                     territoryConfigurationList:territoryConfigurationList as JSON,
                                     territoryConfigurationSize:territoryConfigurationList.size(),

                                     targetBasedIncentive:targetBasedIncentive,
                                     targetBasedIncentiveSlabList:targetBasedIncentiveSlabList as JSON,
                                     targetBasedIncentiveCustomersList:targetBasedIncentiveCustomersList as JSON,

                                     salesAmountBasedIncentive:salesAmountBasedIncentive,
                                     salesAmountBasedIncentiveSlabList:salesAmountBasedIncentiveSlabList as JSON,
                                     salesAmountBasedIncentiveCustomersList:salesAmountBasedIncentiveCustomersList as JSON,

                                     quantityBasedIncentive:quantityBasedIncentive,
                                     quantityBasedIncentiveSlabList:quantityBasedIncentiveSlabList as JSON,
                                     quantityBasedIncentiveCustomersList:quantityBasedIncentiveCustomersList as JSON,

                                     volumeBasedIncentive:volumeBasedIncentive,
                                     volumeBasedIncentiveSlabList:volumeBasedIncentiveSlabList as JSON,
                                     volumeBasedIncentiveCustomersList:volumeBasedIncentiveCustomersList as JSON
                                    ])
    }

    def getTerritoryWiseGeoAndCustomers = {
        Map map = listGeoAndCustomerByTerritoryAction.execute(params, null)
        render map as JSON
    }

    def getGeoWiseTpAndCustomers = {
        Map map = listPtAndCustomerByGeoAction.execute(params, null)
        render map as JSON
    }

    def getPtWiseScAndCustomers = {
        Map map = listScAndCustomerByPtAction.execute(params, null)
        render map as JSON
    }

    def getScWiseCcAndCustomers = {
        Map map = listCcAndCustomerByScAction.execute(params, null)
        render map as JSON
    }

    def getCcWiseCustomers = {
        Map map = listCustomerByCcAction.execute(params, null)
        render map as JSON
    }

//    Target Based Incentive Setup
    def createTargetBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createTargetBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

    // Update
    def updateTargetBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateTargetBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

//    Sales Amount Based Incentive Setup
    def createSalesAmountBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createSalesAmountBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

    // Update
    def updateSalesAmountBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateSalesAmountBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

//    Quantity Based Incentive Setup
    def getQbUomByProduct = {
        render listQbUomByProductAction.execute(params, null) as JSON
    }

    def createQuantityBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createQuantityBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

    // Update
    def updateQuantityBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateQuantityBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

//    Volume Based Incentive Setup
    def getVbProductList = {
        List productList = listAllProductByMasterAndMainProductAction.execute(params, null)
        render productList as JSON
    }

    def getVbPrimaryUomByProduct = {
        render listVbPrimaryUomByProductAction.execute(params, null) as JSON
    }

    def createVolumeBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = createVolumeBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }

    // Update
    def updateVolumeBasedIncentive = {
        ApplicationUser applicationUser = session?.applicationUser
        Message message = updateVolumeBasedIncentiveAction.execute(params,applicationUser)
        render message as JSON
    }
}
