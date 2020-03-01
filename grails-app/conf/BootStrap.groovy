import com.bits.bdfp.accounts.ChartOfAccountLayer
import com.bits.bdfp.accounts.ChartOfAccounts
import com.bits.bdfp.bonus.BonusPercent
import com.bits.bdfp.common.BankPaymentMethod
import com.bits.bdfp.common.CodeGenerationFormat
import com.bits.bdfp.common.CountryInfo
import com.bits.bdfp.common.Designation
import com.bits.bdfp.common.District
import com.bits.bdfp.common.Division
import com.bits.bdfp.currency.CurrencyDemonstration
import com.bits.bdfp.currency.LocalCurrency
import com.bits.bdfp.customer.CustomerCategory
import com.bits.bdfp.customer.CustomerContactType
import com.bits.bdfp.customer.CustomerPaymentType
import com.bits.bdfp.customer.CustomerPriority
import com.bits.bdfp.customer.CustomerSalesChannel
import com.bits.bdfp.customer.CustomerTitle
import com.bits.bdfp.customer.MasterType
import com.bits.bdfp.inventory.product.PackageType
import com.bits.bdfp.inventory.product.PricingCategory
import com.bits.bdfp.inventory.product.ProductPricingType
import com.bits.bdfp.inventory.warehouse.SubWarehouseType
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.settings.BusinessUnitConfiguration
import com.bits.bdfp.settings.Department
import com.bits.bdfp.settings.EnterpriseConfiguration
import com.bits.bdfp.settings.MeasureUnitConfiguration
import com.bits.bdfp.util.ApplicationCacheManager
import com.bits.bdfp.util.LocationUtil
import com.docu.accesscontrol.DefaultDataService
import com.docu.commons.CommonService
import com.docu.commons.GrailsClassUtil
import com.docu.commons.annotation.ControllerAnnotationHelper
import com.docu.security.ApplicationUser
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.plugins.PluginManagerHolder
import org.codehaus.groovy.grails.plugins.springsecurity.SecurityFilterPosition
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.beans.factory.annotation.Autowired

class BootStrap {

    GrailsClassUtil grailsClassUtil
    ControllerAnnotationHelper controllerAnnotationHelper

    @Autowired
    LocationUtil locationUtil
    @Autowired
    ApplicationCacheManager applicationCacheManager
    DefaultDataService      defaultDataService
    CommonService           commonService


    def init = { servletContext ->
        SpringSecurityUtils.clientRegisterFilter('requestLoggingFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10)
        if(ConfigurationHolder.config.application.bootstrap.initDefaultData){
            if (PluginManagerHolder.pluginManager.hasGrailsPlugin('docuaccesscontrol')) {
                defaultDataService.initializeDefaultData()
            }
            if (PluginManagerHolder.pluginManager.hasGrailsPlugin('sbicloud-commons')) {
                commonService.initializeDefaultData()
            }
            //
            ApplicationUser applicationUser = ApplicationUser.get(1)
            if(!applicationUser){
                applicationUser = ApplicationUser.list(max: 1, sort: "id").first()
            }
            // Department
            if(Department.count() == 0){
                new Department(code: '001', name: "Sales", userCreated: applicationUser).save(validate: false)
                new Department(code: '002', name: "Marketing", userCreated: applicationUser).save(validate: false)
                new Department(code: '003', name: "Administration", userCreated: applicationUser).save(validate: false)
                new Department(code: '004', name: "Accounts & Finance", userCreated: applicationUser).save(validate: false)
                new Department(code: '005', name: "QMS", userCreated: applicationUser).save(validate: false)
                new Department(code: '006', name: "Chilling", userCreated: applicationUser).save(validate: false)
                new Department(code: '007', name: "Procurement", userCreated: applicationUser).save(validate: false)
                new Department(code: '008', name: "QA", userCreated: applicationUser).save(validate: false)
                new Department(code: '009', name: "Production", userCreated: applicationUser).save(validate: false)
                new Department(code: '010', name: "Maintenance", userCreated: applicationUser).save(validate: false)
                new Department(code: '011', name: "Transport", userCreated: applicationUser).save(validate: false)
                new Department(code: '012', name: "Store", userCreated: applicationUser).save(validate: false)
                new Department(code: '013', name: "IT", userCreated: applicationUser).save(validate: false)
                new Department(code: '014', name: "R&D", userCreated: applicationUser).save(validate: false)
                new Department(code: '015', name: "General", userCreated: applicationUser).save(validate: false)
                new Department(code: '016', name: "Other", userCreated: applicationUser).save(validate: false)
            }
            // Designation Setup
            if(Designation.count() == 0){
                new Designation(code: '001', name: "Jr. Officer", userCreated: applicationUser).save(validate: false)
                new Designation(code: '002', name: "Officer", userCreated: applicationUser).save(validate: false)
                new Designation(code: '003', name: "Sr. Officer", userCreated: applicationUser).save(validate: false)
                new Designation(code: '004', name: "SPO", userCreated: applicationUser).save(validate: false)
                new Designation(code: '005', name: "SO", userCreated: applicationUser).save(validate: false)
                new Designation(code: '006', name: "Merchandiser", userCreated: applicationUser).save(validate: false)
                new Designation(code: '007', name: "Sales Executive", userCreated: applicationUser).save(validate: false)
                new Designation(code: '008', name: "Jr. Executive", userCreated: applicationUser).save(validate: false)
                new Designation(code: '009', name: "Executive", userCreated: applicationUser).save(validate: false)
                new Designation(code: '010', name: "Sr. Executive", userCreated: applicationUser).save(validate: false)
                new Designation(code: '011', name: "Asst. Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '012', name: "Dy. Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '013', name: "Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '014', name: "Sr. Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '015', name: "AGM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '016', name: "Sr. AGM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '017', name: "DGM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '018', name: "Sr. DGM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '019', name: "GM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '020', name: "Sr. GM", userCreated: applicationUser).save(validate: false)
                new Designation(code: '021', name: "Head Of the Dept.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '022', name: "Program Head", userCreated: applicationUser).save(validate: false)
                new Designation(code: '023', name: "Director", userCreated: applicationUser).save(validate: false)
                new Designation(code: '024', name: "Sr. Director", userCreated: applicationUser).save(validate: false)
                new Designation(code: '025', name: "MD", userCreated: applicationUser).save(validate: false)
                new Designation(code: '026', name: "DMD", userCreated: applicationUser).save(validate: false)
                new Designation(code: '027', name: "Chairman", userCreated: applicationUser).save(validate: false)

                new Designation(code: '028', name: "KAM-Key Account Mgr.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '029', name: "KAE-Key Account Exe.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '030', name: "DI-Distribution Incharge", userCreated: applicationUser).save(validate: false)
                new Designation(code: '031', name: "BM-Branch Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '032', name: "ISM- Ins. Sales Manager", userCreated: applicationUser).save(validate: false)
                new Designation(code: '033', name: "ISE- Ins. Sales Exe", userCreated: applicationUser).save(validate: false)
                new Designation(code: '034', name: "CSM-Channel Sales Mgr.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '035', name: "NSM- National Sales Mgr.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '036', name: "RSM-Regional Sales Mgr.", userCreated: applicationUser).save(validate: false)
                new Designation(code: '037', name: "ZSM-Zonal Sales Mgr.", userCreated: applicationUser).save(validate: false)
            }

            /************ Set Geographical Location  **************/
            if(CountryInfo.count() == 0){
                CountryInfo countryInfoBD = new CountryInfo(code: "880", name: "Bangladesh", userCreated: applicationUser).save(validate: false)
                if(Division.count() == 0){
                    Division divisionDhaka = new Division(countryInfo: countryInfoBD, geoCode: "30", name: "Dhaka", userCreated: applicationUser).save(validate: false)
                    if(District.count() == 0){
                        District districtDhaka = new District(division: divisionDhaka, geoCode: "26", name: "Dhaka", userCreated: applicationUser).save(validate: false)
                    }
                }
            }
            /*****************************************************/
            EnterpriseConfiguration bracDairyEnterprise = null
            if(EnterpriseConfiguration.count() == 0){
                 new EnterpriseConfiguration(code: '157', name: 'BRAC Dairy', noOfLayers: 3, codeLength: 11, layerUsed: true, coaUsed: 0, userCreated: applicationUser).save(validate: false)
            }
            if(!bracDairyEnterprise){
                bracDairyEnterprise = EnterpriseConfiguration.findByName("BRAC Dairy").save(validate: false)
            }
            if(ApplicationUserEnterprise.count() == 0){
                new ApplicationUserEnterprise(applicationUser: applicationUser, enterpriseConfiguration: bracDairyEnterprise, isActive: true, userCreated: applicationUser).save(validate: false)
            }

            if(BusinessUnitConfiguration.count() == 0){
                new BusinessUnitConfiguration(enterpriseConfiguration: bracDairyEnterprise, code: '157', name: 'BRAC Dairy', userCreated: applicationUser, isActive: true).save(validate: false)
            }
            /****************************** Accounting Setup ********************************/
            if(ChartOfAccountLayer.count() == 0){
                new ChartOfAccountLayer(layerNumber: 1, layerName: "Group", enterpriseConfiguration: bracDairyEnterprise, layerCodeLength: 2, userCreated: applicationUser, isActive: true).save(validate: false)
                new ChartOfAccountLayer(layerNumber: 2, layerName: "Sub-Group", enterpriseConfiguration: bracDairyEnterprise, layerCodeLength: 2, userCreated: applicationUser, isActive: true).save(validate: false)
                new ChartOfAccountLayer(layerNumber: 3, layerName: "Head", enterpriseConfiguration: bracDairyEnterprise, layerCodeLength: 7, userCreated: applicationUser, isActive: true).save(validate: false)
            }
            if(ChartOfAccounts.count() == 0){
                ChartOfAccountLayer universalTop = ChartOfAccountLayer.findByLayerName("Group")
                if(!universalTop){
                    universalTop = ChartOfAccountLayer.list().first()
                }
                new ChartOfAccounts(enterpriseConfiguration: bracDairyEnterprise, chartOfAccountLayer: universalTop, chartOfAccountCodeUser: "10", chartOfAccountCodeAuto: bracDairyEnterprise.code + "1000000", chartOfAccountName: "Asset", parentId: 0, parentCode: bracDairyEnterprise.code, isActive: true, userCreated: applicationUser).save(validate: false)
                new ChartOfAccounts(enterpriseConfiguration: bracDairyEnterprise, chartOfAccountLayer: universalTop, chartOfAccountCodeUser: "20", chartOfAccountCodeAuto: bracDairyEnterprise.code + "2000000", chartOfAccountName: "Liability", parentId: 0, parentCode: bracDairyEnterprise.code, isActive: true, userCreated: applicationUser).save(validate: false)
                new ChartOfAccounts(enterpriseConfiguration: bracDairyEnterprise, chartOfAccountLayer: universalTop, chartOfAccountCodeUser: "30", chartOfAccountCodeAuto: bracDairyEnterprise.code + "3000000", chartOfAccountName: "Income", parentId: 0, parentCode: bracDairyEnterprise.code, isActive: true, userCreated: applicationUser).save(validate: false)
                new ChartOfAccounts(enterpriseConfiguration: bracDairyEnterprise, chartOfAccountLayer: universalTop, chartOfAccountCodeUser: "40", chartOfAccountCodeAuto: bracDairyEnterprise.code + "4000000", chartOfAccountName: "Expense", parentId: 0, parentCode: bracDairyEnterprise.code, isActive: true, userCreated: applicationUser).save(validate: false)

            }
            /********************************************************************************/
            /****************************** Currency Setup ********************************/
            LocalCurrency localCurrency = null
            if(LocalCurrency.count() == 0){
                localCurrency = new LocalCurrency(name: "Taka", symbol: "Tk", isActive: true, userCreated: applicationUser).save(validate: false)
            }
            if(!localCurrency){
                localCurrency = LocalCurrency.findByName("Taka")
            }

            if(CurrencyDemonstration.count() == 0){
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "1000", value: 1000, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "500", value: 500, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "100", value: 100, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "50", value: 50, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "20", value: 20, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "10", value: 10, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "5", value: 5, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "2", value: 2, isActive: true, userCreated: applicationUser).save(validate: false)
                new CurrencyDemonstration(localCurrency: localCurrency, noteName: "1", value: 1, isActive: true, userCreated: applicationUser).save(validate: false)
            }
            /******************************************************************************/

            /******************************* Customer Setup ********************************/
            if(MasterType.count() == 0){
                new MasterType(name: 'Employee', userCreated: applicationUser).save(validate: false)
                new MasterType(name: 'Customer', userCreated: applicationUser).save(validate: false)
            }
            // Customer Title
            if(CustomerTitle.count() == 0){
                new CustomerTitle(name: "Mr.", userCreated: applicationUser).save(validate: false)
                new CustomerTitle(name: "Mrs.", userCreated: applicationUser).save(validate: false)
                new CustomerTitle(name: "Dr.", userCreated: applicationUser).save(validate: false)
                new CustomerTitle(name: "Eng.", userCreated: applicationUser).save(validate: false)
                new CustomerTitle(name: "Advocate", userCreated: applicationUser).save(validate: false)
                new CustomerTitle(name: "Ms.", userCreated: applicationUser).save(validate: false)
            }

            if(PricingCategory.count() == 0){
                new PricingCategory(enterpriseConfiguration: bracDairyEnterprise, code: "1001", name: "Distribution Partner", shortName: 'DP', userCreated: applicationUser).save(validate: false)
                new PricingCategory(enterpriseConfiguration: bracDairyEnterprise, code: "1002", name: "Trade Partner", shortName: 'TP', userCreated: applicationUser).save(validate: false)
                new PricingCategory(enterpriseConfiguration: bracDairyEnterprise, code: "1003", name: "Retail Partner", shortName: 'MRP', userCreated: applicationUser).save(validate: false)
                new PricingCategory(enterpriseConfiguration: bracDairyEnterprise, code: "1004", name: "Special Partner", shortName: 'Negotiated', userCreated: applicationUser).save(validate: false)
            }

            if(CustomerPriority.count() == 0){
                new CustomerPriority(enterpriseConfiguration: bracDairyEnterprise, name: "VIP", priority: 1, userCreated: applicationUser).save(validate: false)
                new CustomerPriority(enterpriseConfiguration: bracDairyEnterprise, name: "High Priority", priority: 2, userCreated: applicationUser).save(validate: false)
                new CustomerPriority(enterpriseConfiguration: bracDairyEnterprise, name: "Moderate Priority", priority: 3, userCreated: applicationUser).save(validate: false)
                new CustomerPriority(enterpriseConfiguration: bracDairyEnterprise, name: "Low Priority", priority: 4, userCreated: applicationUser).save(validate: false)
                new CustomerPriority(enterpriseConfiguration: bracDairyEnterprise, name: "Blacklisted", priority: 5, userCreated: applicationUser).save(validate: false)
            }

            if(CustomerSalesChannel.count() == 0){
                new CustomerSalesChannel(enterpriseConfiguration: bracDairyEnterprise, name: "Modern Trade", isActive: true, userCreated: applicationUser).save(validate: false)
                new CustomerSalesChannel(enterpriseConfiguration: bracDairyEnterprise, name: "Institutional Sales", isActive: true, userCreated: applicationUser).save(validate: false)
                new CustomerSalesChannel(enterpriseConfiguration: bracDairyEnterprise, name: "Retail Sales", isActive: true, userCreated: applicationUser).save(validate: false)
                new CustomerSalesChannel(enterpriseConfiguration: bracDairyEnterprise, name: "Others", isActive: true, userCreated: applicationUser).save(validate: false)
            }

            if(CustomerCategory.count() == 0){
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Branch", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Dealer", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Sales Man", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Hotel", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "BLC", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Restaurants", userCreated: applicationUser).save(validate: false)
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "POS", userCreated: applicationUser).save(validate: false)  // POS Customer
                new CustomerCategory(enterpriseConfiguration: bracDairyEnterprise, name: "Retail Outlet", userCreated: applicationUser).save(validate: false)
            }

            if(CustomerContactType.count() == 0){
                new CustomerContactType(enterpriseConfiguration: bracDairyEnterprise, name: "Proprietor", userCreated: applicationUser).save(validate: false)
                new CustomerContactType(enterpriseConfiguration: bracDairyEnterprise, name: "Sales Manager", userCreated: applicationUser).save(validate: false)
                new CustomerContactType(enterpriseConfiguration: bracDairyEnterprise, name: "Owners Representative", userCreated: applicationUser).save(validate: false)
                new CustomerContactType(enterpriseConfiguration: bracDairyEnterprise, name: "Others", userCreated: applicationUser).save(validate: false)
            }

            if(CustomerPaymentType.count() == 0){
                new CustomerPaymentType(enterpriseConfiguration: bracDairyEnterprise, name: "Cash", userCreated: applicationUser).save(validate: false)
                new CustomerPaymentType(enterpriseConfiguration: bracDairyEnterprise, name: "Credit", userCreated: applicationUser).save(validate: false)
            }
            /*******************************************************************************/

            //BONUS PERCENTAGE
            if(BonusPercent.count() == 0) {
                new BonusPercent(percentage: 1, userCreated: applicationUser, dateCreated: new Date()).save(validate: false)
                new BonusPercent(percentage: 1.5, userCreated: applicationUser, dateCreated: new Date()).save(validate: false)
                new BonusPercent(percentage: 2, userCreated: applicationUser, dateCreated: new Date()).save(validate: false)
            }
            //END

            if(ProductPricingType.count() == 0){
                new ProductPricingType(enterpriseConfiguration: bracDairyEnterprise, name: "Standard", isActive: true, priority: 1, userCreated: applicationUser).save(validate: false)
                new ProductPricingType(enterpriseConfiguration: bracDairyEnterprise, name: "Negotiating", isActive: true, priority: 1, userCreated: applicationUser).save(validate: false)
            }

            if(SubWarehouseType.count() == 0){
                new SubWarehouseType(enterpriseConfiguration: bracDairyEnterprise, name: 'Salable', userCreated: applicationUser).save(validate: false)
                new SubWarehouseType(enterpriseConfiguration: bracDairyEnterprise, name: 'Market Return', userCreated: applicationUser).save(validate: false)
                new SubWarehouseType(enterpriseConfiguration: bracDairyEnterprise, name: 'Damage', userCreated: applicationUser).save(validate: false)
                new SubWarehouseType(enterpriseConfiguration: bracDairyEnterprise, name: 'Bonus', userCreated: applicationUser).save(validate: false)
            }

            if(CodeGenerationFormat.count() == 0){
                Date currentDate = new Date()
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "CUSTOMER_KEY", format: "ENPC|########", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "DEBIT_TRANSACTION_KEY", format: "D|############", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "CREDIT_TRANSACTION_KEY", format: "C|############", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                // Order Related
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "RETAIL_ORDER", format: "ENPC|YY|MM|DD|#####", currentNo: "0", prefix: "R", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "SECONDARY_DEMAND_ORDER", format: "ENPC|YY|MM|####", currentNo: "0", prefix: "S", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "PRIMARY_DEMAND_ORDER", format: "ENPC|YY|MM|####", currentNo: "0", prefix: "P", dateUpdated: currentDate).save(validate: false)

                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "PRODUCT_CODE", format: "ENPC|BUC|PCC|MPC|MNPC|PTC|####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "FINISH_PRODUCT_TRANSACTION_REF", format: "ENPC|YY|########", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "FINISH_PRODUCT_PRODUCT_REF", format: "ENPC|PC|YY|########", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                // Invoice Related
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "RETAIL_INVOICE", format: "ENPC|YY|MM|#####", currentNo: "0", dateUpdated: currentDate, prefix: "RI").save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "SECONDARY_INVOICE", format: "ENPC|YY|MM|####", currentNo: "0", dateUpdated: currentDate, prefix: "SI").save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "PRIMARY_INVOICE", format: "ENPC|YY|MM|####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "DIRECT_INVOICE", format: "ENPC|YY|MM|####", currentNo: "0", dateUpdated: currentDate, prefix: "DI").save(validate: false)

                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "LOADING_SLIP", format: "VHCL|YY|MM|DD|######", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "VOUCHER", format: "ENPC|YY|MM|####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "JOURNAL", format: "ENPC|YY|MM|DD|#####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "QUOTATION", format: "ENPC|YY|MM|DD|#####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "TENTATIVE_CUSTOMER", format: "ENPC|YY|#####", currentNo: "0", dateUpdated: currentDate).save(validate: false)
                new CodeGenerationFormat(enterpriseConfiguration: bracDairyEnterprise, keyCode: "DEPOSIT_CASH_TO_DEPOSIT_POOL", format: "ENPC|DD|MM|YY|####", currentNo: "0", dateUpdated: currentDate, prefix: "RECON").save(validate: false)
            }

            if(BankPaymentMethod.count() == 0){
                new BankPaymentMethod(name: "Demand Draft", shortName: "DD", userCreated: applicationUser, isActive: true).save(validate: false)
                new BankPaymentMethod(name: "Telephonic Transfer", shortName: "TT", userCreated: applicationUser, isActive: true).save(validate: false)
                new BankPaymentMethod(name: "Money Transfer", shortName: "MT", userCreated: applicationUser, isActive: true).save(validate: false)
                new BankPaymentMethod(name: "Pay Order", shortName: "PO", userCreated: applicationUser, isActive: true).save(validate: false)
                new BankPaymentMethod(name: "Cheque", shortName: "Cheque", userCreated: applicationUser, isActive: true).save(validate: false)
            }

            if(MeasureUnitConfiguration.count() == 0){
                new MeasureUnitConfiguration(enterpriseConfiguration: bracDairyEnterprise, name: 'ml', isFree: true, userCreated: applicationUser).save(validate: false)
                new MeasureUnitConfiguration(enterpriseConfiguration: bracDairyEnterprise, name: 'gm', isFree: true, userCreated: applicationUser).save(validate: false)
                new MeasureUnitConfiguration(enterpriseConfiguration: bracDairyEnterprise, name: 'kg', isFree: true, userCreated: applicationUser).save(validate: false)
            }

            if(PackageType.count() == 0){
                new PackageType(enterpriseConfiguration: bracDairyEnterprise, name: 'Crate', userCreated: applicationUser).save(validate: false)
                new PackageType(enterpriseConfiguration: bracDairyEnterprise, name: 'Carton', userCreated: applicationUser).save(validate: false)
                new PackageType(enterpriseConfiguration: bracDairyEnterprise, name: 'Box', userCreated: applicationUser).save(validate: false)
            }
        }

        locationUtil.setContextPath(servletContext.getRealPath("/"))
        locationUtil.makeTempDirectory()
        grailsClassUtil.resolve()
        controllerAnnotationHelper.resolve()
        applicationCacheManager.resolveAll()
    }
    def destroy = {
        applicationCacheManager.destroyAll()
    }
}
