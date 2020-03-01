<%--
  Created by IntelliJ IDEA.
  User: debashish.pramanik
  Date: 11/13/14
  Time: 12:08 PM
--%>

<%@ page import="org.codehaus.groovy.grails.commons.ApplicationHolder; com.docu.commons.AddressType" %>
<meta name="layout" content="${ApplicationHolder.application.config.defaultLayout}"/>
<title><g:message code="Language.create.label" default="Address Type Setup"/></title>

<div style="width:800px;">
    <div class="content_container">
        <h1> <g:message code="addressType.addressType.label" default="Address Type"/> </h1>
        <h3> <g:message code="addressType.addressType.label" default="Address Type Information"/> </h3>
        <g:render plugin="sbicloud-commons" template="/commons/addressType/create"/>
        <g:render plugin="sbicloud-commons" template="/commons/addressType/script"/>

        <div class="jqgrid-container blue_grid">
            <table id="jqgrid-grid-addressType"></table>
            <div id="jqgrid-pager-addressType"></div>
        </div>


        <div id="dialog-confirm-addressType" title="Delete Alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>Are sure that you want delete the record completely?</p>
        </div>

    </div>
</div>

