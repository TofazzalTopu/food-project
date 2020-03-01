package com.docu.commons.xml.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.*;
import java.io.*;

/**
 * Created by IntelliJ IDEA.
 * User: Rashed
 * Date: 3/15/11
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtil {
    public static final Log log = LogFactory.getLog(XmlUtil.class);

    /**
     * @param object
     * @param out
     * @return boolean
     */
    public static boolean exportXML(Object object, File out) {
        //log.info("XmlUtil1::exportXML::Enter");
        try {
            FileOutputStream fos = new FileOutputStream(out);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            XMLEncoder encoder = new XMLEncoder(bos);
            DefaultPersistenceDelegate defaultDelegate = new DatePersistenceDelegate(new String[]{"date"});
            // encoder.setPersistenceDelegate(java.sql.Date.class, defaultDelegate);
            encoder.setPersistenceDelegate(java.sql.Timestamp.class, defaultDelegate);
            encoder.writeObject(object);
            encoder.close();
            bos.close();
            fos.close();
            //log.info("XmlUtil1::exportXML::Exit");
            return true;
        } catch (Exception ex) {
            log.error("Error exporting xml:" + ex.getMessage());
            //log.info("XmlUtil1::exportXML::Exit");
        }
        //log.info("XmlUtil1::exportXML::Exit");
        return false;
    }

    /**
     * @param file
     * @return object
     */
    public static Object importXML(File file) {
        //log.info("XmlUtil1::importXML::Enter");
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            XMLDecoder decoder = new XMLDecoder(bis);
            Object result = decoder.readObject();
            decoder.close();
            bis.close();
            fis.close();
            //log.info("XmlUtil1::importXML::Exit");
            return result;
        } catch (Exception ex) {
            //log.info("Error importing xml:" + ex.getMessage());
            //log.info("XmlUtil1::importXML::Exit");

        }
        //log.info("XmlUtil1::importXML::Exit");
        return null;
    }

}

class DatePersistenceDelegate extends DefaultPersistenceDelegate {

    public DatePersistenceDelegate(String[] constructorPropertyNames) {
        super(constructorPropertyNames);
    }

    protected Expression instantiate(Object oldInstance, java.beans.Encoder out) {
        // java.sql.Date d = (java.sql.Date) oldInstance;
        java.sql.Timestamp d = (java.sql.Timestamp) oldInstance;
        return new Expression(oldInstance, d.getClass(), "new", new Object[]{new Long(d.getTime())});

    }

}
