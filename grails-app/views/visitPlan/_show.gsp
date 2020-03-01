<%@ page import="com.bits.bdfp.setup.salestarget.VisitPlan" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Visit Plan</title>
<div class="main_container">
    <div class="content_container">
        <h1>Visit Plan</h1>
        <h3>Visit Plan Details</h3>
        <g:render template='create'  model="[applicationUser:applicationUser,recentDate:recentDate]"/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>

        <div class="buttons">
            <span class="button" id="merge-span"><input type="button" name="merge-button" id="merge-order-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Save"
                                                        onclick="saveGridItems();"/></span>
        </div>

        <div id="dialog-confirm-warehouse" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
