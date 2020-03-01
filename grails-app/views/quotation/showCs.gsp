<%@ page import="com.bits.bdfp.inventory.setup.Quotation" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="quotation.create.label" default="Create Quotation"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="quotation.create.label" default="Create Quotation"/></h1>

        <h3><g:message code="quotation.Info.label" default="Quotation Details"/></h3>
        <g:render template='createCs' model="[result:result, list:list]"/>
        <div class="clear height5"></div>
        <g:render template="scriptCs" model="[entId:entId]"/>

        <div id="dialog-confirm-quotation" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>This item will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
