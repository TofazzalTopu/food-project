/*
* ****************************************************************
* Copyright ? 2010 Documentatm (TM) Limited. All rights reserved.
* DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
* This software is the confidential and proprietary information of
* Documentatm (TM) Limited ("Confidential Information").
* You shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with Documentatm (TM) Limited.
* *****************************************************************
*/
//--------Application Specific Configuration-------------
//--------Application Name-------------------------------
application.name = 'bdfe'

logPath = "c:/logs/${application.name}"
app.base.dir = "/usr/${application.name}"
app.data.dir = "${app.base.dir}/data/"
xml.upload.path = "${app.base.dir}/fund-transfer-xml/"
xml.member.transfer.export.path = "${app.base.dir}/member_export_xml/"
xml.member.transfer.import.path = "${app.base.dir}/member_import_xml/"
xml.group.transfer.path = "${app.base.dir}/group_transfer_xml/"
xml.group.receive.path = "${app.base.dir}/group_receive_xml/"
zip.local.path = "${app.base.dir}/export/"
zip.server.path = "${app.base.dir}/repository/"
//---------End Application File Repository---------------------
//--------End Application Specific Configuration---------------

grails.project.groupId = appName // change this to alter the default package name and Maven publishing destination
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [html: ['text/html', 'application/xhtml+xml'],
                     xml: ['text/xml', 'application/xml'],
                     text: 'text/plain',
                     js: 'text/javascript',
                     rss: 'application/rss+xml',
                     atom: 'application/atom+xml',
                     css: 'text/css',
                     csv: 'text/csv',
                     all: '*/*',
                     json: ['application/json', 'text/json'],
                     form: 'application/x-www-form-urlencoded',
                     multipartForm: 'multipart/form-data',
                     zip: 'application/zip'
]

// URL Mapping Cache Max Size, defaults to 5000
//grails.urlmapping.cache.maxsize = 1000

grails.views.javascript.library="jquery"

// The default codec used to encode data with ${}
grails.views.default.codec = "none" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
grails.gsp.enable.reload = true
grails.reload.enabled = true
// enable Sitemesh preprocessing of GSP pages
grails.views.gsp.sitemesh.preprocess = true
// scaffolding templates configuration
grails.scaffolding.templates.domainSuffix = ''
// Set to false to use the new Grails 1.2 JSONBuilder in the render method
grails.json.legacy.builder = false
// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true
// whether to install the java.util.logging bridge for sl4j. Disable for AppEngine!
grails.logging.jul.usebridge = true
// packages to include in Spring bean scanning
grails.spring.bean.packages = ['com']

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"

        logPath =  "c:/logs/bdfe"    // Windows
//        logPath = "/home/${application.name}/logs"    // Linux
    }
    development {
        grails.serverURL = "http://localhost:8090/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8090/${appName}"
    }
}
// log4j configuration
log4j = {
    appenders {
        appender new org.apache.log4j.DailyRollingFileAppender(
                name: "sqlScript",
                datePattern: "'.'yyyy-MM-dd",
                file: "${logPath}/sql.log",
                append: true,
                threshold: org.apache.log4j.Level.DEBUG,
                layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
        appender new org.apache.log4j.DailyRollingFileAppender(
                name: "warnLog",
                datePattern: "'.'yyyy-MM-dd",
                file: "${logPath}/warn.log",
                append: true,
                threshold: org.apache.log4j.Level.WARN,
                layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
        appender new org.apache.log4j.DailyRollingFileAppender(
                name: "errorLog",
                datePattern: "'.'yyyy-MM-dd",
                file: "${logPath}/error.log",
                append: true,
                threshold: org.apache.log4j.Level.ERROR,
                layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
        appender new org.apache.log4j.DailyRollingFileAppender(
                name: "debugLog",
                datePattern: "'.'yyyy-MM-dd",
                file: "${logPath}/debug.log",
                append: true,
                threshold: org.apache.log4j.Level.DEBUG,
                layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
        appender new org.apache.log4j.DailyRollingFileAppender(
                name: "infoLog",
                datePattern: "'.'yyyy-MM-dd",
                file: "${logPath}/info.log",
                append: true,
                threshold: org.apache.log4j.Level.INFO,
                layout: pattern(conversionPattern: '[%d{yyyy-MM-dd hh:mm:ss.SSS}] %p %c{5} %m%n'))
    }
    additivity.StackTrace = "false"
    logger.'org.springframework' = "off"
    error errorLog: ['grails.app', 'com.bits.bdfp', 'org.xhtmlrenderer.render']
    additivity.org.hibernate.SQL = "false"
    //Define log level
    root {
        error 'errorLog'
        additivity = "false"
    }
}

//grails.gorm.default.mapping = {
//    autoTimestamp true //or false based on your need
//}
//Default action after successful login
grails.plugins.springsecurity.successHandler.defaultTargetUrl = '/home'
grails.plugins.springsecurity.failureHandler.exceptionMappings = [
//        'org.springframework.security.authentication.LockedException':             '/user/accountLocked',
//        'org.springframework.security.authentication.DisabledException':           '/user/accountDisabled',
//        'org.springframework.security.authentication.AccountExpiredException':     '/user/accountExpired',
'org.springframework.security.authentication.CredentialsExpiredException': '/appSecurity/changeSecuritySetting'
]
//If there is no role defined  then reject/redirect to login page
grails.plugins.springsecurity.rejectIfNoRule = false
//Enable failOnError for all domain classes
grails.gorm.failOnError = true
grails.grom.autoFlush = false
grails.converters.json.domain.include.version = true

// Added by the Spring Security Core plugin:
grails.plugins.springsecurity.userLookup.userDomainClassName = 'com.docu.security.ApplicationUser'
grails.plugins.springsecurity.userLookup.authorityJoinClassName = 'com.docu.security.ApplicationUserAuthority'
grails.plugins.springsecurity.authority.className = 'com.docu.security.UserAuthority'
grails.plugins.springsecurity.requestMap.className = 'com.docu.security.Requestmap'
grails.plugins.springsecurity.securityConfigType = grails.plugins.springsecurity.SecurityConfigType.Requestmap
grails.plugins.springsecurity.successHandler.alwaysUseDefault = true
grails.plugins.springsecurity.successHandler.alwaysUseDefaultTargetUrl = true


// Added by docu accesscontrol plugin:
pluginStyle = 'docuThemeRollerLayout'
pluginCss = 'docuThemeRollerLayout.css'

defaultLayout = 'docuThemeRollerLayout'

grails.config.locations = ["classpath:${application.name}-config.groovy"]
//dataSource.passwordEncryptionCodec = com.docu.cypher.PasswordCypher


//grails.config.locations = ["classpath:application_config.properties"]
// Password decoder


grails {
    mail {
        host = "smtp.gmail.com"
        port = 465
        username = "bdfp@gmail.com"
        password = "aldfkjdls"
        props = ["mail.smtp.auth": "true",
                 "mail.smtp.socketFactory.port": "465",
                 "mail.smtp.socketFactory.class": "javax.net.ssl.SSLSocketFactory",
                 "mail.smtp.socketFactory.fallback": "false"]
    }
}

// Configuration for initialize default data for blank database
application.bootstrap.initDefaultData = false
application.codeGeneration.allowResetCode = false
