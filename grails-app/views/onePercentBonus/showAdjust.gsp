<%@ page import="com.bits.bdfp.bonus.OnePercentBonus" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Adjust 1% Bonus</title>
<div class="main_container">
    <div class="content_container">
        <h1>Adjust 1% Bonus</h1>
        <h3>1% Bonus Adjust Details</h3>
        <g:render template='createAdjust' model="[result:result, list:list]"/>
        <div class="clear height5"></div>
        <g:render template="scriptAdjust" model="[isFactory: isFactory]"/>

        <div id="dialog-confirm-onePercentBonus" title="Delete alert" style="display: none;">
            <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?</p>
        </div>

    </div>
</div>
