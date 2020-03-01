<%@ page import="com.docu.security.UserAuthority" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>User Authority</title>
<div class="width1000">
    <div class="content_container">
        <h1 class="width980">User Authority</h1>
        <h3>User Authority Details</h3>
        <g:render plugin="sbicloud-commons" template='create'/>
        <br/>
        <g:render plugin="sbicloud-commons" template="script"/>

        <div class="jqgrid-container blue_grid">
          <table id="jqgrid-grid-authority"></table>
          <div id="jqgrid-pager-authority"></div>
        </div>
        <div id="dialog-confirm-authority" title="Delete alert" style="display: none">
          <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
