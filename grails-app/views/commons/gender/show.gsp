<%@ page import="com.docu.commons.Gender" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title><g:message code="educationLevel.create.label" default="Create Gender"/></title>
<div class="main_container">
    <div class="content_container">
        <h1><g:message code="educationLevel.create.label" default="Create Gender"/></h1>
        <h3><g:message code="educationLevel.create.label" default="Gender"/></h3>
        <g:render template='../commons/gender/tmpviews/create'/>
        %{--<div class="clear height5"></div>--}%
        <g:render template='../commons/gender/tmpscripts/script'/>
        %{--<div class="jqgrid-container blue_grid">--}%
            <g:render template='../commons/gender/tmpviews/listgrid'/>
        %{--</div>--}%
        <div id="dialog-confirm-educationLevel" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>

