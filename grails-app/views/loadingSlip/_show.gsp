<%@ page import="com.bits.bdfp.inventory.sales.LoadingSlip" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="primaryDemandOrder.create.label" default="Search Loading Slip"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="primaryDemandOrder.create.label" default="Search Loading Slip"/></h1>

        <h3><g:message code="primaryDemandOrder.info.label" default="Loading Slip Info"/></h3>

        <div style="width:100%;">
            <g:render template='create' model="[enterpriseList: enterpriseList, salesChannelList: salesChannelList]"/>
            <g:render template="script"/>
        </div>

        <div id="dialog-confirm-loadingslip" title="Cancellation alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>Selected order(s) and all associated data will be permanently cancelled/deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>


