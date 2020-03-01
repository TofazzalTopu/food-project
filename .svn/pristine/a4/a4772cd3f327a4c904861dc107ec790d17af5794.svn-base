<%@ page import="com.bits.bdfp.customer.CustomerAssetLending" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Customer Asset</title>
<div class="main_container">
    <div class="content_container">
        <h1>Customer Asset</h1>
        <h3>Customer Asset Lending & Recovery</h3>
        %{--<g:render template='create'/>--}%
        <g:render template='create' model="[assetLending: assetLending, list: list, result: result]"/>
        <div class="clear height5"></div>
        <g:render template="script"/>

        <g:render template="scriptRecovery"/>
        <div id="dialog-confirm-customerCategory" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>

    </div>
</div>
