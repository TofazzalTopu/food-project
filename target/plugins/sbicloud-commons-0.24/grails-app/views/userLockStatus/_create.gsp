
<%@ page import="com.docu.security.UserLockStatus" %>

<form name='gFormUserLockStatus' id='gFormUserLockStatus'>
  <g:hiddenField name="id" value="${userLockStatus?.id}" />
  <g:hiddenField name="version" value="${userLockStatus?.version}" />
    <div id="remote-content-userLockStatus"></div>
    <div>


        
  </div>
  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    %{--<g:if test="${isUserLocked}">--}%
       <input type="button" name="create-button-unLockUser" id="create-button-unLockUser" class="ui-button ui-widget ui-state-default ui-corner-all" value= "UnLock User" onclick="unLockUserSave();"/>
    %{--</g:if>--}%
      %{--<g:else>--}%
         <input type="button" name="create-button-lockUser" id="lockUser" class="ui-button ui-widget ui-state-default ui-corner-all" value="Lock User" onclick="lockUserSave();"/>
      %{--</g:else>--}%
  </div>
</form>
