<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="secondaryDemandOrder.create.label" default="Update Demand Order"/></title>

<div class="main_container">
    <div class="content_container">
        <h1><g:message code="secondaryDemandOrder.create.label" default="Update Demand Order"/></h1>

        <h3><g:message code="secondaryDemandOrder.info.label" default="Demand Order Info"/></h3>

        <div style="width:100%;">
            <g:render template='createDemandOrderForUpdate'
                      model="[result: result, list: list, applicationUser: applicationUser, id: id]"/>
            <br/>
            <g:render template="scriptDemandOrderForUpdate"
                      model="[id: id, secondaryDemandOrderList: secondaryDemandOrderList]"/>
        </div>


        <div id="dialog-confirm-secondaryDemandOrderDetails" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>
