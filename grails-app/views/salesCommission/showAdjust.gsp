<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission" %>

<meta name="layout" content="docuThemeRollerLayout"/>
<title>Adjust Commission For Sales Man</title>

<div class="main_container">
    <div class="content_container">
        <h1>Adjust Commission For Sales Man</h1>

        <h3>Adjust Commission For Sales Man Details</h3>

        <div style="width:100%;">
            <g:render template='createAdjust' model="[result: result, list: list, currentDate: currentDate]"/>
            <g:render template="scriptAdjust" model="[list: list]"/>
        </div>

        <div id="dialog-confirm-vatType" title="Delete alert" style="display: none">
            <p><span class="ui-icon ui-icon-alert"
                     style="float:left; margin:0 7px 20px 0;"></span>These item(s) will be permanently deleted and cannot be recovered. Are you sure?
            </p>
        </div>
    </div>
</div>

