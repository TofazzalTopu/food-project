package com.docu.commons.annotation

import org.apache.commons.lang.WordUtils
import com.docu.commons.GrailsClassUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by IntelliJ IDEA.
 * User: rafiqul.islam
 * Date: 4/25/11
 * Time: 3:12 PM
 * To change this template use File | Settings | File Templates.
 */

@Component("controllerAnnotationHelper")
class ControllerAnnotationHelper {
  @Autowired
  GrailsClassUtil grailsClassUtil

  private static Map<String, Map<String, List<Class>>> _actionMap = [:]
  private static Map<String, Class> _controllerAnnotationMap = [:]

  /**
   * Find controller annotation information. Called by BootStrap.init().
   */
  public void resolve() {
    grailsClassUtil.controllerClassList.each { controllerClass ->
      def clazz = controllerClass.clazz
      String controllerName = WordUtils.uncapitalize(controllerClass.name)
      mapClassAnnotation(clazz, RequiresBusinessDay, controllerName)
      mapClassAnnotation(clazz, RequiresAuthentication, controllerName)

      Map<String, List<Class>> annotatedClosures = findAnnotatedClosures(clazz, RequiresBusinessDay, RequiresAuthentication)
      if (annotatedClosures) {
        _actionMap[controllerName] = annotatedClosures
      }
    }
  }

 // for testing
  public void reset() {
    _actionMap.clear()
    _controllerAnnotationMap.clear()
  }

  // for testing
  public Map<String, Map<String, List<Class>>> getActionMap() {
    return _actionMap
  }

  // for testing
  public Map<String, Class> getControllerAnnotationMap() {
    return _controllerAnnotationMap
  }

  private void mapClassAnnotation(clazz, annotationClass, controllerName) {
    if (clazz.isAnnotationPresent(annotationClass)) {
      def list = _controllerAnnotationMap[controllerName] ?: []
      list << annotationClass
      _controllerAnnotationMap[controllerName] = list
    }
  }

  /**
   * Check if the specified controller action requires Authentication.
   * @param controllerName  the controller name
   * @param actionName  the action name (closure name)
   */
  public boolean requiresAuthentication(String controllerName, String actionName) {
    return requiresAnnotation(RequiresAuthentication, controllerName, actionName)
  }

  /**
   * Check if the specified controller action requires Business Day.
   * @param controllerName  the controller name
   * @param actionName  the action name (closure name)
   */
  public boolean requiresBusinessDay(String controllerName, String actionName) {
    return requiresAnnotation(RequiresBusinessDay, controllerName, actionName)
  }

  private boolean requiresAnnotation(Class annotationClass, String controllerName, String actionName) {
    // see if the controller has the annotation
    def annotations = _controllerAnnotationMap[controllerName]
    if (annotations && annotations.contains(annotationClass)) {
      return true
    }

    // otherwise check the action
    Map<String, List<Class>> controllerClosureAnnotations = _actionMap[controllerName] ?: [:]
    List<Class> annotationClasses = controllerClosureAnnotations[actionName]
    return annotationClasses && annotationClasses.contains(annotationClass)
  }

  private Map<String, List<Class>> findAnnotatedClosures(Class clazz, Class... annotationClasses) {
    // since action closures are defined as "def foo = ..." they're
    // fields, but they end up private
    def map = [:]
    for (field in clazz.declaredFields) {
      def fieldAnnotations = []
      for (annotationClass in annotationClasses) {
        if (field.isAnnotationPresent(annotationClass)) {
          fieldAnnotations << annotationClass
        }
      }
      if (fieldAnnotations) {
        map[field.name] = fieldAnnotations
      }
    }

    return map
  }
}
