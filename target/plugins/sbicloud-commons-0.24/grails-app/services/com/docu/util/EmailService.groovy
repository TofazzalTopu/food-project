package com.docu.util

import com.docu.commons.CommonConstants
import com.docu.commons.SessionManagementService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib
import org.grails.mail.MailService
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware

class EmailService implements ApplicationContextAware {
    static transactional = false
    MailService mailService
    GrailsApplication grailsApplication
    SessionManagementService sessionManagementService
    static String mailSubject = "sbicloud - log"
    static String fileName = "error.log"
    ApplicationContext applicationContext
//    GrailsAttributes grailsAttributes
    private ApplicationTagLib g

    void setApplicationContext(ApplicationContext applicationContext) {
        g = applicationContext.getBean(ApplicationTagLib)

        // now you have a reference to g that you can call render() on
    }

    public void reportLogFiles() {
        try {
            String errorLogPath = grailsApplication.config.logPath + CommonConstants.FILE_SEPARATOR + "error.log"

            mailService.sendMail {
                multipart true
                to "mrafiqulislam@gmail.com"
                subject  mailSubject
                body 'Log file check'
                attachBytes fileName, 'text/plain', new File(errorLogPath).readBytes()
            }
        } catch (Exception ex) {
            log.error(ex.message)
        }
    }

    public void sendHtmlMail(String toEmail, String ccEmail, String emailSubject, String templateName, Map model, String fileName = ""){
        try {
            mailService.sendMail {
                to toEmail
                if (ccEmail?.length() > 0){
                    cc ccEmail
                }

                subject emailSubject
                html g.render(template: templateName, model: model)
                if (fileName?.length() > 0){
                    attachBytes fileName, 'text/plain', new File(fileName).readBytes()
                }
            }
        } catch (Exception ex) {
            log.error(ex.message)
        }
    }

    public void sendHtmlMail(List<String> toEmailList, List<String> ccEmailList, String emailSubject, String templateName, Map model, String fileName = ""){
        try {
            mailService.sendMail {
                to toEmailList?.toArray()
                if (ccEmailList?.size() > 0){
                    cc ccEmailList?.toArray()
                }
                subject emailSubject
                html g.render(template: templateName, model: model)
                if (fileName?.length() > 0){
                    attachBytes fileName, 'text/plain', new File(fileName).readBytes()
                }
            }
        } catch (Exception ex) {
            log.error(ex.message)
        }
    }
    public void sendHtmlMailWithAttchment(List<String> toEmail, List<String> ccEmailList, String emailSubject, String templateName, Map model, String filePath = "" ,String fileName = "" ){
        try {
            mailService.sendMail {
                multipart true
                to toEmail
                if (ccEmailList?.size() > 0){
                    cc ccEmailList?.toArray()
                }
                subject emailSubject
                html g.render(template: templateName, model: model)
                if (filePath?.length() > 0){
                    attachBytes fileName, 'text/plain', new File(filePath).readBytes()
                }
            }
        } catch (Exception ex) {
            log.error(ex.message)
        }
    }
}
