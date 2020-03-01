<%@ page import="com.docu.commons.Religion" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="educationLevel.create.label" default="Create Religion"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="educationLevel.create.label" default="Create Religion"/></h1>
        <h3><g:message code="educationLevel.Info.label" default="Religion"/></h3>
        <g:render template='../commons/religion/tmpviews/create'/>
        %{--<div class="clear height5"></div>--}%
        <g:render template='../commons/religion/tmpscripts/script'/>
        %{--<div class="jqgrid-container blue_grid">--}%
            <g:render template='../commons/religion/tmpviews/listgrid'/>
        %{--</div>--}%
        <div id="dialog-confirm-educationLevel" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>

