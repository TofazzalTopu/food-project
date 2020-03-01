%{--
  - ****************************************************************
  - Copyright © 2010 Documentatm (TM) Limited. All rights reserved.
  - DOCUMENTA PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
  - This software is the confidential and proprietary information of
  - Documentatm (TM) Limited ("Confidential Information").
  - You shall not disclose such Confidential Information and shall use
  - it only in accordance with the terms of the license agreement
  - you entered into with Documentatm (TM) Limited.
  - *****************************************************************
  --}%


<%@ page import="com.docu.accesscontrol.ModuleInfo; org.codehaus.groovy.grails.commons.ApplicationHolder as AH" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<g:set var="pluginStyle" value="${AH.application.config.pluginStyle}" />
<meta name="layout" content='${pluginStyle}'/>
 %{--<meta name="layout" content="pluginStyle"/>--}%
<g:set var="entityName" value="Module Info"/>
<title><g:message code="default.create.label" args="[entityName]"/></title>

<g:javascript>

    function onSubmitModuleInfo() {

   //   if ($("#moduleInfoForm").validate().form({onfocusout: false}) == false) {
   //     return false;
   //   }
      $.ajax({type:'post',
        data:jQuery("#moduleInfoForm").serialize(),
        url: "${request.contextPath}/moduleInfo/saveModuleInfoRemotely",
        success:function(data, textStatus) { onSaveModuleInfo(data);},
        error:function(XMLHttpRequest, textStatus, errorThrown) { },
        complete:function(XMLHttpRequest, textStatus) { showHideSpinner(false) }});
      return false;
    }
    function showMessage(divID, message, messageType) {

        // Clearing the existing style rules from message panel
        $('#' + divID).removeAttr("style");

        $('#' + divID).css({ "display": "inline" });
        ////
        $('#' + divID).fadeIn('slow');
        $('#' + divID).animate({ opacity: 1.0 }, 1000);
        $('#' + divID).show();
        $('#' + divID).html(message);


        if (messageType == "Success") {
            $('#' + divID).removeClass('appError').addClass('appMessage');
            setTimeout(function() {
                $('#' + divID).fadeOut(2000);
            }, 4000);
        }
        else if (messageType == "Error") {
            $('#' + divID).removeClass('appMessage').addClass('appError');
        }
        else {
            setTimeout(function() {
                $('#' + divID).fadeOut(3000);
            }, 5000);
        }
    }
</g:javascript>


<g:render plugin="docuaccesscontrol" template='/moduleInfo/create'/>


<g:render plugin="docuaccesscontrol" template='/moduleInfo/list'/>