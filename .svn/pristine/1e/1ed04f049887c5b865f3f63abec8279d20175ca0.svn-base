package com.bits.common

import com.docu.commons.CommonConstants
import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry
import org.springframework.beans.propertyeditors.CustomDateEditor
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: Rana
 * Date: 4/5/11
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */
class BitsDateEditorRegistrarUtil implements PropertyEditorRegistrar {
    public void registerCustomEditors(PropertyEditorRegistry registry) {
        String dateFormat = CommonConstants.DATE_FORMAT //'yyyy/MM/dd'
        registry.registerCustomEditor(Date, new CustomDateEditor(new SimpleDateFormat(dateFormat), true))
    }
}

