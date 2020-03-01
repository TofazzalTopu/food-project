/**
 * Gant script that generates a CRUD controller, matching views, service and actions a for a given domain class
 *
 * @author Graeme Rocher
 *
 * @ Service and Actions generations are developed by
 * Md. Ali Naser Khan. Documenta TM Ltd.
 * @since 0.4
 */

includeTargets << grailsScript("_GrailsCreateArtifacts")
includeTargets << grailsScript("_GrailsGenerate")

import grails.util.GrailsNameUtils
import org.springframework.core.io.FileSystemResource

generateViews = true
generateController = true

target('default': "Generates a CRUD interface (controller + views) for a domain class") {
    depends(checkVersion, parseArguments, packageApp)
    promptForName(type: "Domain Class")

    try {
        def name = argsMap["params"][0]
        println "************************************************************************************"
        println "Generating......."
        def orginalParam = name
        if (!name || name == "*") {
            uberGenerate()
        }
        else {
            generateForName = name
            def fullDomainPath = name
            def actionPackage = getActionPackage(name)
            def servicePackage = getServicePackage(name)
            generateForOne()
            //copyCommonFiles() // Copy common Service, Action and Message file to proper path
            generateService(orginalParam, servicePackage) // Generate Service according to domain
            generateAllActions(orginalParam, actionPackage) // Generate Actions according to domain

            println "Generate Complete"
            println "************************************************************************************"
        }
    }
    catch (Exception e) {
        logError("Error running generate-all", e)
        exit(1)
    }
}

def generateAllActions(def orginalParam, def actionPackage) {
    try {
        def actionDir = "${basedir}/src/groovy/" + actionPackage
        if (!new File(actionDir).exists()) {
            ant.mkdir(dir: actionDir) // Create a action directory
            println("dir: ${actionDir} created")
        }
        /** ********* Move generated action files from views to action path  ******************/
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/CreateAction.gsp", "${actionDir}/Create${className}Action.groovy")
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/DeleteAction.gsp", "${actionDir}/Delete${className}Action.groovy")
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/ListAction.gsp", "${actionDir}/List${className}Action.groovy")
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/UpdateAction.gsp", "${actionDir}/Update${className}Action.groovy")
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/ReadAction.gsp", "${actionDir}/Read${className}Action.groovy")
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/SearchAction.gsp", "${actionDir}/Search${className}Action.groovy")

        /** **********************************************************************************/
        println("Process for Action file generation finished")
    }
    catch (Exception e) {
        println("error: " + e)
        exit(1)
    }

}

def getActionPackage(def orginalParam) {
    def nameOp = orginalParam
    def rawName = nameOp
    nameOp = nameOp.replaceAll('.entity', '.action')
    className = GrailsNameUtils.getClassNameRepresentation(nameOp)
    propertyName = GrailsNameUtils.getPropertyNameRepresentation(nameOp)

    String strDomain = "${propertyName}"
    String strDomainInput = strDomain
    if (strDomain.length() > 1) {
        String temp = strDomain.substring(0, 1).toUpperCase()
        strDomainInput = temp + strDomain.substring(1, strDomain.length())
    }

    nameOp = nameOp.replaceAll(strDomainInput, strDomain.toLowerCase())
    nameOp = nameOp.replace('.', '/')
    return nameOp
}

def generateService(def orginalParam, def servicePackage) {

    try {
        def serviceDir = "${basedir}/grails-app/services/" + servicePackage
        if (!new File(serviceDir).exists()) {
            ant.mkdir(dir: serviceDir) // Create a servive directory
            println("dir: ${serviceDir} created")
        }

        /** ********* Move generated service files from views to service path  ******************/
        moveGeneratedFile("${basedir}/grails-app/views/${propertyName}/Service.gsp", serviceDir + "/${className}Service.groovy")
        /** **********************************************************************************/
        println("Process for Service file generation finished")

    }
    catch (Exception e) {
        println("error:" + e)
        exit(1)
    }

}

def getServicePackage(def orginalParam) {
    def nameOp = orginalParam
    def rawName = nameOp
    nameOp = nameOp.indexOf('.') > -1 ? nameOp : GrailsNameUtils.getClassNameRepresentation(nameOp)
    nameOp = nameOp.replaceAll('.entity', '')
    className = GrailsNameUtils.getClassNameRepresentation(nameOp)
    propertyName = GrailsNameUtils.getPropertyNameRepresentation(nameOp)

    String strDomain = "${propertyName}"
    String strDomainInput = strDomain
    if (strDomain.length() > 1) {
        String temp = strDomain.substring(0, 1).toUpperCase()
        strDomainInput = temp + strDomain.substring(1, strDomain.length())
    }

    nameOp = nameOp.replaceAll('.' + strDomainInput, '')
    nameOp = nameOp.replace('.', '/')
    return nameOp
}

def moveGeneratedFile(String source, String destination) {
    try {
        // first check for presence of Action file in application
        def actionFileMove = new FileSystemResource(source)
        if (actionFileMove.exists()) {

            if (new File(destination).exists()) {
                if (!confirmInput("${destination}  already exists. Overwrite? [y/n]", "${destination}.overwrite")) {
                    return
                }
            }

            ant.move(file: "${source}", tofile: "${destination}", overwrite: "true")
        }
        else {
            println("${source} is not available")
        }
    }
    catch (Exception e) {
        println(e)
    }

}

def copyFile(String source, String destination) {
    try {
        // first check for presence of Action file in application
        def actionFileMove = new FileSystemResource(source)
        if (actionFileMove.exists()) {

            if (new File(destination).exists()) {
                if (!confirmInput("${destination}  already exists. Overwrite? [y/n]", "${destination}.overwrite")) {
                    return
                }
            }

            ant.copy(file: "${source}", tofile: "${destination}", overwrite: "true")
        }
        else {
            println("${source} is not available")
        }
    }
    catch (Exception e) {
        println(e)
    }

}


def copyCommonFiles() {
    /** ************ Copy Service file  ****************/
    def commonServiceDir = "${basedir}/grails-app/services/com/docu/common"
    if (!new File(commonServiceDir).exists()) {
        ant.mkdir(dir: commonServiceDir) // Create a Common Service directory
        println("dir: ${commonServiceDir} created")
    }

    // Copy Common Service file
    if (!new File(commonServiceDir + "/Service.groovy").exists()) {
        copyFile("${basedir}/src/templates/scaffolding/Service.groovy", "${commonServiceDir}/Service.groovy")
    }
    /** ************************************************/
    /** ************ Copy Action file  ****************/
    def commonActionDir = "${basedir}/src/groovy/com/docu/common/"
    if (!new File(commonActionDir).exists()) {
        ant.mkdir(dir: commonActionDir) // Create a Common Service directory
        println("dir: ${commonActionDir} created")
    }
    // Copy Common Action file
    if (!new File(commonActionDir + "/Action.groovy").exists()) {
        copyFile("${basedir}/src/templates/scaffolding/Action.groovy", "${commonActionDir}/Action.groovy")
    }
    /** **********************************************/

    /** ************ Copy Message file  ***************/
    def commonMessageDir = "${basedir}/src/groovy/com/docu/common/"
    if (!new File(commonMessageDir).exists()) {
        ant.mkdir(dir: commonMessageDir) // Create a Common Service directory
        println("dir: ${commonMessageDir} created")
    }
    // Copy Common Message file
    if (!new File(commonMessageDir + "/Message.groovy").exists()) {
        copyFile("${basedir}/src/templates/scaffolding/Message.groovy", "${commonMessageDir}/Message.groovy")
    }
    /** **********************************************/
}