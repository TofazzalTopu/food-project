<%@ page import="com.bits.bdfp.accounts.Mushak" %>
<g:render template='create' model="[mushak: mushak]"/>
<div class="clear height5"></div>
<g:render template="editScript" model="[mushak: mushak]"/>
<div id="dialog-confirm-mushak" title="Delete alert" style="display: none;">
    <p><span class="ui-icon ui-icon-alert"
             style="float:left; margin:0 7px 20px 0;"></span>This item will be permanently deleted and cannot be recovered. Are you sure?
    </p>
</div>

