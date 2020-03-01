package com.bits.bdfp

import com.bits.bdfp.customer.CustomerMaster
import com.docu.app.UserPreference
import com.docu.app.action.AjaxMenuAction
import com.docu.commons.SessionManagementService
import com.docu.security.ApplicationUser
import com.docu.security.SecurityQuestion
import com.docu.security.UserFirstLoginPasswordVerification
import com.docu.security.UserLoginFailStatus
import grails.plugins.springsecurity.SpringSecurityService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import grails.converters.JSON

import com.bits.bdfp.dashboard.SearchDashboardInfoAction

class HomeController {
    SpringSecurityService springSecurityService
    SessionManagementService sessionManagementService
    @Autowired
    AjaxMenuAction ajaxMenuAction

    @Autowired
    SearchDashboardInfoAction searchDashboardInfoAction

    def index = {
        if (!springSecurityService.isLoggedIn()){
            redirect(controller: "login", action: "auth", params: params)
        }

//        def auth = springSecurityService.principal
        def authorities = springSecurityService.getAuthentication().getAuthorities()
//        Set authorities = auth.authorities
        ApplicationUser applicationUser = (ApplicationUser) springSecurityService.getCurrentUser()
        if(applicationUser){
            UserLoginFailStatus userLoginFailStatus = UserLoginFailStatus.findByApplicationUser(applicationUser)
            if(userLoginFailStatus){
                if(userLoginFailStatus.loginFailCount > 0){
                    userLoginFailStatus.loginFailCount = 0
                    userLoginFailStatus.save()
                }
            }
        }

        session.applicationUser = applicationUser
        String add1 = request.getRemoteAddr()
        String add2 = request.getHeader("X-Forwarded-For")
        String add3 = request.getHeader("Client-IP")
        String add4 = InetAddress.getLocalHost().getHostAddress()
        String ipAddr = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
        String ipDetails = "RemoteAdd :" + add1 + " Forwarded-For :" + add2 + " Client :" + add3 + " Host :" + add4 + " Request Attr From :" + ipAddr
        session?.ipDetails = ipDetails
        sessionManagementService.setUser(session.applicationUser)
        sessionManagementService.setUserRole(authorities.collectAll { it.role.substring(5) }.join(","))
        sessionManagementService.setUserMenuList(ajaxMenuAction.getCurrentUserMenuList())
        sessionManagementService.setUserFavouriteLink(ajaxMenuAction.getUserFavouriteLink())
        List<Long> moduleIdList = ajaxMenuAction.getUserModuleIdList()
        sessionManagementService.setAllowedModuleList(moduleIdList)
        // Set Customer
        if(applicationUser.customerMasterId){
            session.customerMaster = CustomerMaster.get(applicationUser.customerMasterId)
        }

        // Set Color Theme
        UserPreference userPreference = UserPreference.findByUserId(applicationUser.id)
        if(userPreference){
            sessionManagementService.setPreferredTheme(userPreference.colorTheme)
        }
        UserFirstLoginPasswordVerification userFirstLoginPasswordVerification=UserFirstLoginPasswordVerification.findByApplicationUser(applicationUser)
        if (userFirstLoginPasswordVerification){
            if (userFirstLoginPasswordVerification.isVerified) {
                render(view: 'index', model: [applicationUser: applicationUser])
            }
            else{
                List<SecurityQuestion> securityQuestionList=SecurityQuestion.list()
                render(view: 'changePass',model: [securityQuestionList:securityQuestionList,
                                                  applicationUser: applicationUser,
                                                  userFirstLoginPasswordVerification:userFirstLoginPasswordVerification])
            }
        }

        else{
            render(view: 'index', model: [applicationUser: applicationUser])
        }

    }

    private boolean isValidApplicationUserWithAuthorization(def auth) {
        Set authorities = auth.authorities // a Collection of GrantedAuthority
        Object noAuthority = authorities.find { it.role == "ROLE_NO_ROLES"}
        if (noAuthority == null) {
            ApplicationUser user = ApplicationUser.findByUsername(auth.username)
            if (user) {
                sessionManagementService.setUser(user)
                sessionManagementService.setUserRole(authorities.collectAll { it.role.substring(5) }.join(","))
            }
            return true
        }
        return false
    }

    //R and D highchart

    def highchartdata = {
        List list1 = searchDashboardInfoAction.salesVsCollection()
        List list2 = searchDashboardInfoAction.salesVsCollectionClaim()
        List list3 = searchDashboardInfoAction.increaseDecreaseSales()
        List list4 = searchDashboardInfoAction.targetvsAchievement()
        List list5 = searchDashboardInfoAction.projectedSales()
        Map result = new HashMap()
        result.put("list1",list1)
        result.put("list2",list2)
        result.put("list3",list3)
        result.put("list4",list4)
        result.put("list5",list5)
        render result as JSON

    }

}
