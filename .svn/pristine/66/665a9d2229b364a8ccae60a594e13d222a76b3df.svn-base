package com.docu.commons

import net.sf.jasperreports.engine.JRExporter
import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperPrint
import net.sf.jasperreports.engine.export.JRPdfExporterParameter
import org.codehaus.groovy.grails.plugins.jasper.JasperReportDef
import org.codehaus.groovy.grails.plugins.jasper.JasperService

class DocuJasperService extends JasperService {

    public ByteArrayOutputStream generateReport(JasperReportDef reportDef) {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream()
        JRExporter exporter = generateExporter(reportDef)

        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray)
        exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF-8")
        exporter.setParameter(JRPdfExporterParameter.PDF_JAVASCRIPT, "this.print();");

        JasperPrint jasperPrint = generatePrinter(reportDef)
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint)
        exporter.exportReport()

        return byteArray
    }
}
