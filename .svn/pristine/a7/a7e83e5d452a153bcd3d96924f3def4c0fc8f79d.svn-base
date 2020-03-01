package com.docu.commons
import org.codehaus.groovy.grails.plugins.jasper.JasperController
import com.docu.commons.annotation.RequiresAuthentication
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef

@RequiresAuthentication
class DocuJasperController extends JasperController{
    DocuJasperService docuJasperService

    def index = {
        println(params)
        def testModel = this.getProperties().containsKey('chainModel') ? chainModel : null
        JasperReportDef report = null
        if (params.containsKey('auto_print_dialog')){
            report = docuJasperService.buildReportDefinition(params, request.getLocale(), testModel)
        }
        else if(params.containsKey('save_to_server_hdd')){
            report = jasperService.buildReportDefinition(params, request.getLocale(), testModel)
            savePdfToServerHdd(report)
            return
        }
        else{
            report = jasperService.buildReportDefinition(params, request.getLocale(), testModel)
        }
        generateResponse(report)
    }

    def generateResponse = {reportDef ->
        if (!reportDef.fileFormat.inline && !reportDef.parameters._inline) {
            response.characterEncoding = "UTF-8"
            response.setHeader("Content-disposition", "inline; filename=${params._name}");
            response.setHeader("Content-disposition", "inline; filename="+(reportDef.parameters._name ?: reportDef.name) + "." + reportDef.fileFormat.extension);
            response.contentType = "application/pdf"
            response.outputStream << reportDef.contentStream.toByteArray()
        } else {
            render(text: reportDef.contentStream, contentType: reportDef.fileFormat.mimeTyp, encoding: reportDef.parameters.encoding ? reportDef.parameters.encoding : 'UTF-8');
        }
    }

    def openPdfReportTab = {

    }

    def savePdfToServerHdd = {reportDef ->
        String fileLoc = "",fileNameWithLoc = ""
        if(reportDef.parameters.containsKey('file_save_location')){
            fileLoc = reportDef.parameters.file_save_location.toString()
        }
        else{
            fileLoc = servletContext.getRealPath("/")
        }

        new File(fileLoc).mkdirs()
        fileNameWithLoc = fileLoc + "${reportDef.parameters._name}.pdf"

        FileOutputStream output = new FileOutputStream(fileNameWithLoc)
        output.write((byte[])reportDef.contentStream.toByteArray())
        output.close()
    }
}


