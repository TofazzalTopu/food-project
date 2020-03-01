
<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="division.create.label" default="Business Day Time"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="division.create.label" default="Office Time"/></h1>
        <h3><g:message code="division.Info.label" default="Office Time"/></h3>
        <g:render template='/financialYear/businessDayTime/create'/>
        <div class="clear height5"></div>
        <g:render template="/financialYear/businessDayTime/script"/>

        <div class="jqgrid-container">
            <table id="jqgrid-grid-division"></table>
            <div id="jqgrid-pager-division"></div>
        </div>
        <div id="dialog-confirm-division" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
