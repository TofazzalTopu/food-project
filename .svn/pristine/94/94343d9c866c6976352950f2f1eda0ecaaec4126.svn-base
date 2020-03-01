

<%@ page import="com.bits.bdfp.geolocation.TerritorySubArea" %>
<form name='gFormTerritorySubArea' id='gFormTerritorySubArea'>
  <g:hiddenField name="id" value="${territorySubArea?.id}" />
  <g:hiddenField name="version" value="${territorySubArea?.version}" />
  <div id="remote-content-territorySubArea"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create TerritorySubArea</legend>
     <table>
      

            <tr>
              <td><g:message code="territorySubArea.territoryConfiguration.label" default="Territory Configuration" /></td>
                <td><g:select name="territoryConfiguration.id" from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id" value="${territorySubArea?.territoryConfiguration?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.paraOrLocality.label" default="Para Or Locality" /></td>
                <td><g:textField name="paraOrLocality" value="${territorySubArea?.paraOrLocality}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.road.label" default="Road" /></td>
                <td><g:textField name="road" value="${territorySubArea?.road}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${territorySubArea?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${territorySubArea?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${territorySubArea?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="territorySubArea.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${territorySubArea?.lastUpdated}"  /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxTerritorySubArea();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxTerritorySubArea();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormTerritorySubArea');" value="Cancel"/></span>
  </div>
</form>
