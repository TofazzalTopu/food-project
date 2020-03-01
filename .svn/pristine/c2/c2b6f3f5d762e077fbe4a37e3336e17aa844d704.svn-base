package com.bits.bdfp.util

import com.docu.util.FileService
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by mdalinaser.khan on 6/21/15.
 */
@Component("locationUtil")
class LocationUtil {
    @Autowired
    FileService fileService

    public static final String FILE_SEPARATOR = "/"
    public static String CONTEXT_PATH = ""
    public static String WEB_CONTEXT_PATH = ""
    public static String TEMP_FILE_UPLOAD_DIRECTORY = "temp"
    public static String AGENT_FILE_UPLOAD_DIRECTORY = "agent"
    public static String CUSTOMER_FILE_UPLOAD_DIRECTORY = "customer"
    public static String SUB_AGENT_FILE_UPLOAD_DIRECTORY = "subagent"
    @Autowired
    GrailsApplication grailsApplication

    public void setContextPath(String path) {
        WEB_CONTEXT_PATH = path
        CONTEXT_PATH = grailsApplication.config.app.data.dir

        File dataFile = new File(CONTEXT_PATH)
        if(!dataFile.exists()){
            dataFile.mkdir()
        }

        //    TMP_FILE_UPLOAD_LOCATION = path + TMP_FILE_UPLOAD_DIRECTORY
        //    AGENT_FILE_UPLOAD_LOCATION = path + AGENT_FILE_UPLOAD_DIRECTORY
        //    SUB_AGENT_FILE_UPLOAD_LOCATION = path + SUB_AGENT_FILE_UPLOAD_DIRECTORY
    }

    public static String getBasePath(String path) {
        StringBuffer baseLocationPath = new StringBuffer()
        baseLocationPath.append(CONTEXT_PATH).append(path)
        return baseLocationPath.toString()
    }

    public static String getAgentBasePath(String agentId) {
        StringBuffer agentPath = new StringBuffer()
        agentPath.append(AGENT_FILE_UPLOAD_DIRECTORY)
                .append(FILE_SEPARATOR).append(agentId)
                .append(FILE_SEPARATOR)
        return getBasePath(agentPath.toString())
    }
    public static String getCustomerBasePath(String customerId) {
        StringBuffer customerPath = new StringBuffer()
        customerPath.append(CUSTOMER_FILE_UPLOAD_DIRECTORY)
                .append(FILE_SEPARATOR).append(customerId)
                .append(FILE_SEPARATOR)
        return getBasePath(customerPath.toString())
    }

    public static String getSubAgentBasePath(String subAgentId) {
        StringBuffer agentPath = new StringBuffer()
        agentPath.append(SUB_AGENT_FILE_UPLOAD_DIRECTORY)
                .append(FILE_SEPARATOR).append(subAgentId)
                .append(FILE_SEPARATOR)
        return getBasePath(agentPath.toString())
    }


    public static String getTempBasePath(String uploadedFile) {
        StringBuffer tempPath = new StringBuffer()
        tempPath.append(TEMP_FILE_UPLOAD_DIRECTORY)
                .append(FILE_SEPARATOR).append(uploadedFile)
        return getBasePath(tempPath.toString())
    }

    public static String getTempBasePath() {
        StringBuffer tempPath = new StringBuffer()
        tempPath.append(TEMP_FILE_UPLOAD_DIRECTORY)
                .append(FILE_SEPARATOR)
        return getBasePath(tempPath.toString())
    }

    public void makeTempDirectory() {
        String tempPath = getTempBasePath()
        fileService.makeDir(tempPath)
    }
}
