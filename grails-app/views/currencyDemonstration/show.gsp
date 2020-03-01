<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="currencyDemonstration.create.label" default="Create Currency Demonstration"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="currencyDemonstration.create.label" default="Create Currency Demonstration"/></h1>
        <h3><g:message code="currencyDemonstration.Info.label" default="Currency Demonstration"/></h3>
        <g:render template='create'/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <div class="jqgrid-container blue_grid">
            <table id="jqgrid-grid-currencyDemonstration"></table>
            <div id="jqgrid-pager-currencyDemonstration"></div>
        </div>
        <div id="dialog-confirm-currencyDemonstration" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
