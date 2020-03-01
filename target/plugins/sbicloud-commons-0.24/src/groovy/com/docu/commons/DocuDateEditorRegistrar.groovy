package com.docu.commons

import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry
import org.springframework.beans.propertyeditors.CustomDateEditor
import java.text.SimpleDateFormat

/**
 * Created by IntelliJ IDEA.
 * User: rafiqul.islam
 * Date: Nov 24, 2010
 * Time: 11:43:37 AM
 * To change this template use File | Settings | File Templates.
 */
class DocuDateEditorRegistrar implements PropertyEditorRegistrar {

  public void registerCustomEditors(PropertyEditorRegistry registry) {
    String dateFormat = CommonConstants.DATE_FORMAT //'yyyy/MM/dd'
    registry.registerCustomEditor(Date, new CustomDateEditor(new SimpleDateFormat(dateFormat), true))
  }

}
