package com.bits.bdfp.security

import com.bits.bdfp.settings.ApplicationUserDistributionPoint
import com.bits.bdfp.settings.ApplicationUserEnterprise
import com.bits.bdfp.util.ApplicationConstants
import com.docu.app.UserPortletPreference
import com.docu.app.UserPreference
import com.docu.common.Action
import com.docu.commons.CommonConstants
import com.docu.commons.InternationalizationService
import com.docu.commons.JsonUtil
import com.docu.commons.SessionManagementService
import com.docu.plugin.cxfws.CommonHelperWebService
import com.docu.security.ApplicationUser
import com.docu.security.ApplicationUserAuthority
import com.docu.security.ApplicationUserType
import com.docu.security.EmailNotification
import com.docu.security.SecurityAnswer
import com.docu.security.UserFirstLoginPasswordVerification
import com.docu.util.EmailService
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy
import org.springframework.transaction.annotation.Transactional

import java.util.regex.Pattern

class UserAccountService extends InternationalizationService {

    static transactional = false
    TransactionAwareDataSourceProxy dataSource
    SessionManagementService sessionManagementService
    def springSecurityService
    EmailService emailService
    Sql sql

    @Autowired
    JsonUtil jsonUtil
    CommonHelperWebService commonHelperWebService

    @Transactional(readOnly = true)
    public ApplicationUser read(Map params) {
        return ApplicationUser.read(params.id)
    }

    @Transactional(readOnly = true)
    public List<ApplicationUserAuthority> getUserAuthorityByApplicationUser(ApplicationUser applicationUser) {
        ApplicationUserAuthority.withCriteria { eq("applicationUser", applicationUser) }
    }

    @Transactional(readOnly = true)
    public List<ApplicationUserEnterprise> getUserEnterpriseByApplicationUser(ApplicationUser applicationUser) {
        ApplicationUserEnterprise.withCriteria { eq("applicationUser", applicationUser) }
    }

    @Transactional(readOnly = true)
    public List<ApplicationUserDistributionPoint> getUserDistributionPointByApplicationUser(ApplicationUser applicationUser) {
        ApplicationUserDistributionPoint.withCriteria { eq("applicationUser", applicationUser) }
    }

    @Transactional
    public boolean save(Map object) {
//        Office office = sessionManagementService.getOffice()
        boolean isUpdate = true
        try {
            List<ApplicationUserAuthority> applicationUserAuthorityList = (List<ApplicationUserAuthority>) object.get("applicationUserAuthority")
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
//            UserFirstLoginPasswordVerification userFirstLoginPasswordVerification = (UserFirstLoginPasswordVerification) object.get('userFirstLoginPasswordVerification')
            UserPreference userPreference = (UserPreference) object.get('userPreference')
            List<UserPortletPreference> userPortletPreferenceList = (List<UserPortletPreference>) object.get('userPortletPreference')
            List<ApplicationUserEnterprise> applicationUserEnterpriseList = (List<ApplicationUserEnterprise>) object.get("applicationUserEnterprise")
            List<ApplicationUserDistributionPoint> applicationUserDistributionPointList = (List<ApplicationUserDistributionPoint>) object.get("applicationUserDistributionPoint")
            if (applicationUser.id == null || applicationUser.id == "") {

                isUpdate = false
            }
            applicationUser.save()

            isUpdate = true
            applicationUserAuthorityList.each {
                isUpdate = false
                it.save()
            }

            applicationUserEnterpriseList.each {
                it.save(validate: false)
            }

            applicationUserDistributionPointList.each {
                it.save(validate: false)
            }

            isUpdate = true

            userPreference.userId = applicationUser.id
            userPreference.save()

            isUpdate = true
            userPortletPreferenceList.each {
                isUpdate = false
                it.save()
            }

//            userFirstLoginPasswordVerification.applicationUser=applicationUser
//
//            if (userFirstLoginPasswordVerification.validate()){
//                userFirstLoginPasswordVerification.save()
//            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean update(Map object) {
        boolean isUpdate = true
        try {
            List<ApplicationUserAuthority> applicationUserAuthorityList = (List<ApplicationUserAuthority>) object.get("applicationUserAuthority")
            List<ApplicationUserAuthority> deleteApplicationUserAuthorityList = (List<ApplicationUserAuthority>) object.get("deleteApplicationUserAuthority")
            List<ApplicationUserEnterprise> applicationUserEnterpriseList = (List<ApplicationUserEnterprise>) object.get("applicationUserEnterprise")
            List<ApplicationUserEnterprise> deleteApplicationUserEnterpriseList = (List<ApplicationUserEnterprise>) object.get("deleteApplicationUserEnterprise")
            List<ApplicationUserDistributionPoint> applicationUserDistributionPointList = (List<ApplicationUserDistributionPoint>) object.get("applicationUserDistributionPoint")
            List<ApplicationUserDistributionPoint> deleteApplicationUserDistributionPointList = (List<ApplicationUserDistributionPoint>) object.get("deleteApplicationUserDistributionPoint")
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
            UserPreference userPreference = (UserPreference) object.get('userPreference')
            List<UserPortletPreference> userPortletPreferenceList = (List<UserPortletPreference>) object.get('userPortletPreference')
            List<UserPortletPreference> deleteUserPortletPreferenceList = (List<UserPortletPreference>) object.get('deleteUserPortletPreferenceList')

            applicationUser.save()

            //Process to dete the third table data
            deleteApplicationUserAuthorityList.each {
                it.delete()
            }

            isUpdate = true
            applicationUserAuthorityList.each {
                it.save()
            }

            //Process to data the third table data
//            deleteApplicationUserEnterpriseList.each {
//                it.delete()
//            }

            ApplicationUserEnterprise.executeUpdate("delete from ApplicationUserEnterprise aue where aue.applicationUser = ?", [applicationUser])

            applicationUserEnterpriseList.each {
                it.save(validate: false)
            }

            ApplicationUserDistributionPoint.executeUpdate("delete from ApplicationUserDistributionPoint audp where audp.applicationUser = ?", [applicationUser])

//            deleteApplicationUserDistributionPointList.each {
//                it.delete()
//            }

            applicationUserDistributionPointList.each {
                it.save()
            }

            isUpdate = true
            if (userPreference.id == null) {
                isUpdate = false
            }
            userPreference.userId = applicationUser.id
            userPreference.save()

            deleteUserPortletPreferenceList.each {
                it.delete()
            }

            isUpdate = true
            userPortletPreferenceList.each {

                it.save()
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean simpleUpdate(ApplicationUser applicationUser) {
        try {
            applicationUser.save()
            return true
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
    }

    @Transactional
    public boolean updateAuthority(Map object) {
        boolean isUpdate = true
        try {
            List<ApplicationUserAuthority> applicationUserAuthorityList = (List<ApplicationUserAuthority>) object.get("applicationUserAuthority")
            List<ApplicationUserAuthority> deleteApplicationUserAuthorityList = (List<ApplicationUserAuthority>) object.get("deleteApplicationUserAuthority")
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')

            //Process to dete the third table data
            deleteApplicationUserAuthorityList.each {
                it.delete()
            }

            applicationUserAuthorityList.each {
                it.save()
            }

            applicationUser.save()
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            isUpdate false
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean resetPassword(Map object) {
        boolean isUpdate = true
        try {
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
            applicationUser.save()
            commonHelperWebService.callWebMethod(applicationUser, 'reset', isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean changePassword(Map object) {
        boolean isUpdate = true
        try {
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
            applicationUser.save()
            commonHelperWebService.callWebMethod(applicationUser, '', isUpdate)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean saveChangedPassword(Object object) {
        try {
            ApplicationUser applicationUser = (ApplicationUser) object.applicationUser
            UserFirstLoginPasswordVerification userFirstLoginPasswordVerification = (UserFirstLoginPasswordVerification) object.userFirstLoginPasswordVerification
            List<SecurityAnswer> securityAnswerList = object.securityAnswerList
            applicationUser.save()
            userFirstLoginPasswordVerification.save()

            securityAnswerList.each {
                it.save()
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean changeSettings(Map object) {
        boolean isUpdate = true
        try {
            ApplicationUser applicationUser = (ApplicationUser) object.get('applicationUser')
            applicationUser.save()

            UserPreference userPreference = (UserPreference) object.get('userPreference')
            userPreference.save()
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean lock(Map object) {
        boolean isUpdate = true
        try {
            List<ApplicationUser> applicationUserList = (List<ApplicationUser>) object.get(ApplicationUser.class.simpleName)

            applicationUserList.each {
                it.accountLocked = true
                it.save()
            }
            object.put('applicationUserList', applicationUserList)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional
    public boolean unlock(Map object) {
        boolean isUpdate = true
        try {
            List<ApplicationUser> applicationUserList = (List<ApplicationUser>) object.get(ApplicationUser.class.simpleName)

            applicationUserList.each {
                it.accountLocked = false
                it.save()
            }
            object.put('applicationUserList', applicationUserList)
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    @Transactional(readOnly = true)
    public List getDataForGrid() {
        List result = []
        try {
            String query = """
                            SELECT
                                au.id                    AS id,
                                au.username              AS username,
                                au.full_name             AS "fullName",
                                au.enabled AS enabled,
                                au.account_locked AS "accountLocked",
                                au.account_expired AS "accountExpired",
                                au.password_expired AS "passwordExpired",
                                au.date_created AS "dateCreated",
                                a.authority AS authority
                            FROM application_user AS au
                            LEFT JOIN application_user_authority AS aua ON ( au.id = aua.application_user_id)
                            LEFT JOIN user_authority AS a ON (a.id = aua.user_authority_id)
                           """

            Sql db = new Sql(dataSource)
            result = db.rows(query)

        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return result
    }

    @Transactional(readOnly = true)
    public Object showApplicationUserData(long userId) {
        List result = []
        try {
            String sql = """
                SELECT au.*, IFNULL(user_type.type_name, '') AS type_name, IFNULL(customer_master.`code`, '') AS `customerCode`, IFNULL(customer_master.`name`, '') AS `customerName`,
                    IFNULL(customer_master.`present_address`, '') AS `customerAddress`, IFNULL(enterprise_configuration.`name`, '') AS `customerEnterprise`
                FROM application_user AS au
                    LEFT JOIN user_type ON ( au.user_type_id = user_type.id)
                    LEFT JOIN customer_master ON (au.customer_master_id = customer_master.`id`)
                    LEFT JOIN enterprise_configuration ON (customer_master.`enterprise_configuration_id` = enterprise_configuration.`id`)
                WHERE au.id = ?
                LIMIT 1
            """
            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userId])
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return result.size() > 0 ? result.first() : result
    }

    @Transactional(readOnly = true)
    public Object showApplicationUserRoles(long userId) {
        List result = []
        try {
            String sql = """
                    SELECT GROUP_CONCAT(a.authority) AS authority
                    FROM application_user_authority AS aua
                    INNER JOIN user_authority AS a ON (a.id = aua.user_authority_id)
                    WHERE aua.application_user_id = ?
                    GROUP BY aua.application_user_id
                 """
                    Sql db = new Sql(dataSource)
                    result = db.rows(sql, [userId])
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return result.size() > 0 ? result.first() : result
    }

    @Transactional(readOnly = true)
    public Object showApplicationUserEnterprises(long userId) {
        List result = []
        try {
            String sql = """
                SELECT GROUP_CONCAT(ec.`name`) AS enterprise
                FROM application_user_enterprise AS aue
                    INNER JOIN enterprise_configuration AS ec ON (ec.id = aue.`enterprise_configuration_id`)
                WHERE aue.application_user_id = ?
                GROUP BY aue.application_user_id
            """
            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userId])
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return result.size() > 0 ? result.first() : result
    }

    @Transactional(readOnly = true)
    public Map listApplicationUserDistributionPoint(long userId) {
        List result = []
        try {
            String sql = """
                SELECT `dp`.`id`, `audp`.`id` AS `applicationUserDistributionPointId`, `ec`.`name` AS `enterprise`, `dp`.`name` AS `distributionPoint`, `dp`.`address`
                FROM `application_user_distribution_point` AS `audp`
                    INNER JOIN `distribution_point` AS `dp` ON (`audp`.`distribution_point_id` = `dp`.`id`)
                    INNER JOIN `enterprise_configuration` AS `ec` ON (`dp`.`enterprise_configuration_id` = `ec`.`id`)
                WHERE `audp`.`application_user_id` = ?
            """
            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userId])
            int count = result ? result.size() : 0
            return [objList: result, count: count]
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return [objList: result, count: 0]
    }


    @Transactional(readOnly = true)
    public List showSelectedRules(long userId) {
        List result = []
        try {
            String sql = "SELECT user_authority.* FROM user_authority,application_user_authority WHERE user_authority.id=application_user_authority.user_authority_id AND application_user_authority.application_user_id=?"
            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userId])
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return result
    }

    public boolean checkAvailability(String userName) {
        List result = []
        boolean isExist = false
        try {
            String sql = "SELECT count(*) as \"userCount\" FROM application_user WHERE username=?"

            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userName])
            if (result[0].userCount > 0) {
                isExist = true
            }
        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return isExist
    }

    public String checkUserName(String userName) {
        List result = []
        String userId = ""
        try {
            String sql = "SELECT id as \"ID\" FROM application_user WHERE username=?"

            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userName])
            if (result != null) {
                userId = result[0].ID
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return userId
    }

    public String isUserExist(String userName) {

        List result = []
        String userId = ""
        try {
            String sql = "SELECT id as \"ID\" FROM application_user WHERE username=?"

            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userName])
            if (result != null) {
                userId = result[0].ID
            }
        }
        catch (Exception ex) {

        }
        return userId
    }

    @Transactional
    public boolean updatePassword(ApplicationUser applicationUser) {
        try {
            applicationUser.save()
            sendMailForForgetPassword(applicationUser)
            return true
        }
        catch (Exception ex) {
            log.error(ex.message)
            throw new RuntimeException(ex)
        }
    }

    public String checkOldPassword(String userId) {
        List result = []
        String password = ""
        try {
            String sql = "SELECT password FROM application_user WHERE id=?"

            Sql db = new Sql(dataSource)
            result = db.rows(sql, [userId])
            if (result != null) {
                password = result[0].password
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
        return password
    }

    String getApplicationUserRoles(ApplicationUser user) {
        String roles = ""
        String sql = """
                SELECT a.role as role
                FROM application_user_authority AS aua
                LEFT JOIN application_user AS au ON (au.id = aua.application_user_id)
                LEFT JOIN user_authority AS a ON (aua.user_authority_id = a.id)
                WHERE au.id = '${user.id}'
        """
        Sql db = new Sql(dataSource)
        List resultList = db.rows(sql)
        resultList.each {
            roles += it.role + ","
        }
        if (roles.length() > 0) {
            roles = roles.substring(0, roles.length() - 1)
        }
        return roles
    }

    public void sendNotificationMail(ApplicationUser applicationUser) {
//        emailService.sendHtmlMail(["mrafiqulislam@gmail.com"], ["rafiqul.islam@documentabd.com"], "Your sbicloud user is created", "/mailNotification/usercreated", [fullName: applicationUser?.fullName, username: applicationUser?.username, password: applicationUser?.confirmPassword])
        emailService.sendHtmlMail([applicationUser?.emailAddress], ["support.sbicloud@documentabd.com", "rafiqul.islam@documentabd.com"], "Your sbicloud user is created", "/mailNotification/usercreated", [fullName: applicationUser?.fullName, username: applicationUser?.username, password: applicationUser?.confirmPassword])
    }

    public void sendMailForForgetPassword(ApplicationUser applicationUser) {
        EmailNotification emailAddress = EmailNotification.read(CommonConstants.FORGET_PASSWORD_EMAIL_ID)
        List<String> emailList = []
        List<String> ccList = []
        if (emailAddress.emailAddress) {
            emailList = emailAddress.emailAddress.split(",")
        }
        if (emailAddress.ccEmailAddress) {
            ccList = emailAddress.ccEmailAddress.split(",")
        }

        if (applicationUser.emailAddress) {
            emailService.sendHtmlMail([applicationUser.emailAddress], ccList, "[${applicationUser.username}]: Your sbiCloud user password is reset", "/mailNotification/forgotPassword", [fullName: applicationUser?.fullName, username: applicationUser?.username, password: applicationUser?.confirmPassword])
        } else {
//            emailService.sendHtmlMail(["support.sbicloud@documentabd.com"], ["rafiqul.islam@documentabd.com"], "Your sbicloud user password is updated", "/mailNotification/forgotpassword", [fullName: applicationUser?.fullName, username: applicationUser?.username, password: applicationUser?.confirmPassword])
            emailService.sendHtmlMail(emailList, ccList, "[${applicationUser.username}]: Your sbiCloud user password is reset", "/mailNotification/forgotPassword", [fullName: applicationUser?.fullName, username: applicationUser?.username, password: applicationUser?.confirmPassword])
        }
    }

    public Map checkSecurityQuestion(Map params) {
        boolean isValid = false
        List result = []
        Map response = [:]
        String message = ""
        try {
            String sql = "SELECT answer_one,answer_two FROM user_preference WHERE user_id='" + params.userid + "'"
//            AND answer_one='"+params.ques1+"' AND answer_two='"+params.ques2+"'"

            Sql db = new Sql(dataSource)
            result = db.rows(sql)
            if (result != null) {
                if (result.size() > 0) {
                    if (!(result[0].answer_one).equalsIgnoreCase(params.ques1)) {
                        message = "Wrong answer for question one"
                        isValid = false
                    } else if (!(result[0].answer_two).equalsIgnoreCase(params.ques2)) {
                        message = "Wrong answer for question two"
                        isValid = false
                    } else {
                        isValid = true
                    }
                }
            }
        }
        catch (Exception ex) {
            message = ex.message
            isValid = false
            log.error(ex.message)
        }

        response = [isValid: isValid, message: message]
        return response
    }

    public String generateRandomNumber() {
        String alphabet = (('A'..'Z') + ('0'..'9')).join();
        int n = 8;
        return new Random().with {
            (1..n).collect { alphabet[nextInt(alphabet.length())] }.join()
        }
    }

    public List<Map> applicationUserControlPanelData(Map params) {
        String query = """
                            SELECT
                                au.id                    AS id,
                                au.username              AS username,
                                au.full_name             AS name,
                                CASE (au.enabled) WHEN TRUE THEN 1 ELSE 0 END AS enabled,
                                CASE (au.account_expired) WHEN TRUE THEN 1 ELSE 0 END AS "accountExpired",
                                CASE (au.account_locked) WHEN TRUE THEN 1 ELSE 0 END AS "accountLocked",
                                CASE (au.password_expired ) WHEN TRUE THEN 1 ELSE 0 END AS "passwordExpired"
                            FROM application_user AS au
                       """
        Sql db = new Sql(dataSource)
        List<GroovyRowResult> rowResultList = db.rows(query)
        List<Map> dataList = getUserControlPanelDataMap(rowResultList)
        return dataList
    }

    public List<Map> getUserControlPanelDataMap(List<GroovyRowResult> rowResultList) {
        List<Map> dataList = []
        Map tempMap = [:]
        for (GroovyRowResult rowResult : rowResultList) {
            tempMap = [id: rowResult.id, username: rowResult.username, name: rowResult.name, enabled: rowResult.enabled, accountLocked: rowResult.accountLocked, accountExpired: rowResult.accountExpired, passwordExpired: rowResult.passwordExpired]
            dataList.add(tempMap)
        }
        return dataList
    }

    @Transactional
    public boolean updateUserStatus(List<ApplicationUser> applicationUserList) {
        try {
            applicationUserList.each { applicationUser ->
                applicationUser.save()
            }
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
        return true
    }

    public List<GroovyRowResult> getEmployeeInfoList(def params) {
        String query = """
                        SELECT
                          ei.id            AS "employeeId",
                          ei.pin_number    AS "employeePin",
                          ei.employee_name AS "employeeName",
                          COALESCE(ei.email_address, '') AS "emailAddress"
                        FROM employee_info AS ei
                        WHERE ei.domain_status_id = 1 AND ei.approval_status = 'APPROVED'
                            AND (ei.pin_number LIKE '${params.term}%' OR ei.employee_name LIKE '${params.term}%')
                       """
        return new Sql(dataSource).rows(query)
    }


    public boolean isOldPasswordExists(String oldPassword, ApplicationUser user) {
        boolean isValidOldPassword = false
        if (user.password == springSecurityService.encodePassword(oldPassword)) {
            isValidOldPassword = true
        }
        return isValidOldPassword
    }

    public boolean isValidPassword(String password) {
//        Pattern passwordPattern = ~/^(?=.*[0-9])(?=.*[a-z])(?=.*[\$@#%&]).{6,20}$/
        Pattern passwordPattern = ~/^(?=.*[0-9])(?=.*[a-z])(?=.*[\$@#%&*?^!]).{6,20}$/
        return passwordPattern.matcher(password).matches()
    }

    @Transactional(readOnly = true)
    public UserPreference getUserPreference(long userId) {
        List<UserPreference> userPreferenceList = UserPreference.withCriteria {
            eq("userId", userId)
        }
        return userPreferenceList[0]
    }


    public boolean isValidPassword(ApplicationUser user, String password) {
        if (user.password.equals(springSecurityService.encodePassword(password))) {
            return true
        }
        return false
    }

    @Transactional(readOnly = true)
    public ApplicationUser getApplicationUserByUserName(String userName) {
        return ApplicationUser.findByUsername(userName)
    }


    @Transactional(readOnly = true)
    public String getApplicationUserListByOffice(Map params) {
        List result = []
        List applicationUserList = []

        String sql = """
             SELECT
              au.id,
              au.username,
              au.full_name,
              ed.name            AS designation,
              eci.pin_no,
              case when au.account_locked then 'Yes' Else 'No' End account_locked,
              au.date_created
            FROM application_user au
              INNER JOIN employee_info ei
                ON au.user_ref_id = ei.id
              INNER JOIN employee_core_info eci
                ON ei.employee_core_info_id = eci.id
              LEFT JOIN employee_designation ed
                ON eci.e_designation_id = ed.id
            WHERE eci.office_info_id = '${params.officeInfoId}'
                AND au.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
                AND ei.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
                AND eci.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
        """
        try {

            Sql db = new Sql(dataSource)
            result = db.rows(sql)
            result.each {
                applicationUserList.add([id   : it.id, userName: it.username, fullName: it.full_name, designation: it.designation,
                                         pinNo: it.pin_no, accountLocked: it.account_locked, dateCreated: it.date_created, applicationUserId: it.id])
            }
            return jsonUtil.getJqGridFeed(applicationUserList, params, '1', '1', applicationUserList.size().toString())
        }
        catch (Exception ex) {
            log.error(getExceptionMessage(ex))
            throw new RuntimeException(getUserMessage(CommonConstants.DEFAULT_HIBERNAT_EXCEPTION_GENERIC))
        }
    }

    @Transactional
    private boolean truncateApplicationUserBackup() {
        String sql = 'TRUNCATE TABLE application_user_backup '

        try {
            Sql db = new Sql(dataSource)
            db.execute(sql)
        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }

    }

    @Transactional
    public boolean backupApplicationUser() {

        String sql = 'INSERT INTO application_user_backup (SELECT * FROM application_user)'

        try {
            truncateApplicationUserBackup()
            Sql db = new Sql(dataSource)
            db.execute(sql)
        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }

    }

    @Transactional
    public boolean lockApplicationUser() {
        String sql = "UPDATE application_user SET account_locked = TRUE WHERE username != '${sessionManagementService.user.username}'"
        try {
            Sql db = new Sql(dataSource)
            db.execute(sql)
        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }
    }

    @Transactional
    public boolean restoreApplicationUser() {
        String sql = """
            UPDATE
            application_user AS appUser
            INNER JOIN application_user_backup appBackup
            ON  appUser.id = appBackup.id
            SET appUser.account_locked = appBackup.account_locked"""
        try {
            Sql db = new Sql(dataSource)
            db.execute(sql)
        } catch (Exception ex) {
            log.error(getExceptionMessage(ex))
        }

    }

//    @Transactional
//    public boolean unLockApplicationUser(){
//
//        String sql = 'UPDATE application_user SET account_locked = FALSE'
//
//        try {
//            truncateApplicationUserBackup()
//            Sql db = new Sql(dataSource)
//            db.execute(sql)
//        } catch (Exception ex) {
//            log.error(getExceptionMessage(ex))
//        }
//
//    }

    public List<GroovyRowResult> getInternalApplicationUserList() {
        String query = """
            SELECT
                au.id,
                au.username,
                au.full_name as "fullName"
            FROM
                application_user au
            WHERE
                au.domain_status_id = ${CommonConstants.DOMAIN_STATUS_ACTIVE}
                AND au.application_user_type = '${ApplicationUserType.INTERNAL}'
                AND au.enabled = true
                AND au.account_locked = false
        """
        List<GroovyRowResult> list = new Sql(dataSource).rows(query)
        list.each {
            it.label = "[${it.username}] ${it.fullName}"
        }
        return list
    }

    public String encodePassword(String strPassword) {
        return springSecurityService.encodePassword(strPassword)
    }

    @Transactional(readOnly = true)
    public UserPreference getPreferenceByUserId(long userId) {
        return UserPreference.findByUserId(userId)
    }

    @Transactional(readOnly = true)
    public List<UserPortletPreference> getAllUserPortletPreference(UserPreference userPreference) {
        return UserPortletPreference.withCriteria { eq('userPreference', userPreference) }
    }

    @Transactional(readOnly = true)
    public Map listUsers(Action action, Object params) {
        sql = new Sql(dataSource)
        String strLIMIT = "";
        String offSet = ""
        String userName = ""
        String userType = ""
        if (params.userName) {
            userName = """ AND `application_user`.`username` = '${params.userName}'
        """
        }
        if (params.userType) {
            userType = """ AND `application_user`.`user_type_id` = ${params.userType}
                """
        }
        if (action.resultPerPage != -1) {
            strLIMIT = """LIMIT ${action.resultPerPage}"""
            offSet = """
                    OFFSET ${action.start}
                   """
        } else {
            action.resultPerPage = -1;
        }

        String strSql = """
                        SELECT `application_user`.`id`,`application_user`.`username`,`customer_master`.`code`,
                            `designation`.`name` AS designation,`department`.`name` AS department,
                            `customer_master`.`name` AS customer
                        FROM `application_user`
                        INNER JOIN `customer_master`
                            ON `application_user`.`customer_master_id` = `customer_master`.`id`
                        LEFT JOIN `designation`
                            ON `designation`.id = `customer_master`.`designation_id`
                        LEFT JOIN `department`
                            ON `department`.`id` = `customer_master`.`department_id`
                        INNER JOIN `enterprise_configuration`
                            ON `enterprise_configuration`.id = `customer_master`.`enterprise_configuration_id`
                        WHERE `enterprise_configuration`.id = ${params.entId}
                           ${userName}
                          ${userType}
                        ORDER BY `customer_master`.`code`
                           ${strLIMIT}
                           ${offSet}
                          """
        List objList = sql.rows(strSql)
        int resultCount = 0
        if (objList && objList.size() > 0) {
            resultCount = objList.size()
        }

        return [objList: objList, count: resultCount]
    }

}

