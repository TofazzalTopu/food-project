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

%{----}%

<%@ page import="com.docu.security.ApplicationUserAuthority" %>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<meta name="layout" content="setup"/>
<g:set var="entityName" value="${com.docu.common.Tools.formatCamelCase(message(code: 'applicationUserAuthority.label', default: 'ApplicationUserAuthority'))}"/>
<title><g:message code="default.create.label" args="[entityName]"/></title>
<g:javascript>

    function onSubmitApplicationUserAuthority() {

      if ($("#applicationUserAuthorityForm").validate().form({onfocusout: false}) == false) {
        return false;
      }

      $.ajax({type:'post',
        data:jQuery("#applicationUserAuthorityForm").serialize(),
        url: "${resource(dir: 'applicationUserAuthority', file: 'saveApplicationUserAuthorityRemotely')}",
        success:function(data, textStatus) { onSaveApplicationUserAuthority(data);},
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

<%-- Includes create form template.  see (grails-app/views/applicationUserAuthority/create.gsp --%>
<g:render plugin="sbicloud-commons" template='/applicationUserAuthority/create'/>

<%-- Includes list view (usually Flexigrid) template  see (grails-app/views/applicationUserAuthority/_list.gsp --%>
<g:render plugin="sbicloud-commons" template='/applicationUserAuthority/list'/>