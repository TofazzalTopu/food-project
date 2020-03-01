<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>
<%--
  Created by IntelliJ IDEA.
  User: bipul.kumar
  Date: 5/2/11
  Time: 6:55 PM
  To change this template use File | Settings | File Templates.
--%>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}" />
<meta name="layout" content='${pluginStyle}'/>

<g:set var="entityName" value="${message(code: 'Show Feature Info', )}"/>
<title><g:message code="Show Feature Info" default="Show Feature Info"/></title>

<g:form name="showFeatureInfo"
        action="save"
        method="post">
    <div style="width:800px;">
        <div class="buttons">
            <div style="float:left;"><h1><g:message code="Show Feature Info" default="Show Feature Info"/></h1></div>
            <div style="float:right;">
                <input onClick="location.href = '${request.contextPath}/${params.controller}/edit/${featureInfoInstance?.id}'" type="button" name="edit" value="Edit" class="ui-button ui-widget ui-state-default ui-corner-all"/>
            </div>
            <div class="clear"></div>
        </div>

        <fieldset>
            <legend><g:message code="Module Info" default="Module Info"/></legend>
            <div>
                <div class="block-title">
                    <div class="element-title"><g:message code="Module" default="Module"/></div>
                    <div class="clear"></div>
                </div>
                <div class="block-input">
                    <div class="element-input value ${hasErrors(bean: featureInfoInstance, field: 'moduleInfo', 'errors')}">
                        ${fieldValue(bean: featureInfoInstance?.moduleInfo, field: 'moduleName')}
                    </div>
                    <div class="clear"></div>
                </div>
            </div>
        </fieldset>

        <fieldset>
            <legend><g:message code="Feature Info" default="Feature Info"/></legend>
            <div>
                <div class="block-title">
                    %{--<div class="element-title"><g:message code="groupInfo.groupCategory.label"/></div>--}%
                    <div class="element-title"><g:message code="Feature Name" default="Feature Name"/></div>
                    <div class="element-title"><g:message code="Description" default="Description"/></div>

                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    %{--<div class="element-input value ${hasErrors(bean: groupInfoInstance, field: 'groupCategory', 'errors')}">${fieldValue(bean: groupInfoInstance?.groupCategory, field: "categoryName")}</div>--}%
                    <div class="element-input value ${hasErrors(bean: featureInfoInstance, field: 'featureName', 'errors')}">${fieldValue(bean: featureInfoInstance, field: "featureName")}</div>
                    <div class="element-input value ${hasErrors(bean: featureInfoInstance, field: 'description', 'errors')}">${fieldValue(bean: featureInfoInstance, field: "description")}</div>

                    <div class="clear"></div>
                </div>

            </div>
        </fieldset>

        %{--feature Action portion--}%
        <fieldset>
            <legend><g:message code="Feature Action" default="Feature Action"/></legend>
            <div>
                <div class="block-title">
                    %{--<div class="element-title"><g:message code="groupInfo.groupCategory.label"/></div>--}%
                    <div class="element-title"><g:message code="Action Code" default="Action Code"/></div>
                    <div class="element-title"><g:message code="Action Name" default="Action Name"/></div>
                    <div class="element-title"><g:message code="Description" default="Description"/></div>
                    <div class="element-title"><g:message code="Menu Url" default="Menu Url"/></div>
                    <div class="clear"></div>
                </div>

                <div class="block-input">
                    <g:each var="featureAction" in="${featureInfoInstance.nFeatureActions}">
                        <div class="element-input value ${hasErrors(bean: featureAction, field: 'actionCode', 'errors')}">${fieldValue(bean: featureAction, field: "actionCode")}</div>
                        <div class="element-input value ${hasErrors(bean: featureAction, field: 'actionName', 'errors')}">${fieldValue(bean: featureAction, field: "actionName")}</div>
                        <div class="element-input value ${hasErrors(bean: featureAction, field: 'description', 'errors')}">${fieldValue(bean: featureAction, field: "description")}</div>
                        <div class='element-input inputContainer'>
                            <g:if test="${featureAction?.menuUrl !='null'}">
                                ${featureAction?.menuUrl}
                            </g:if>
                            <g:else>

                            </g:else>
                        </div>
                    %{--<div class="element-input value ${hasErrors(bean: featureAction, field: 'menuUrl', 'errors')}">${fieldValue(bean: featureAction, field: "menuUrl")}</div>--}%
                        <div class="clear"></div>
                    </g:each>
                </div>

            </div>
        </fieldset>

        <br/>
        <div class="buttons">
            <input onClick="location.href = '${request.contextPath}/${params.controller}/edit/${featureInfoInstance?.id}'" type="button" name="edit" value="Edit" class="ui-button ui-widget ui-state-default ui-corner-all"/>
        </div>
    </div>

</g:form>