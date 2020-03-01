

<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointTerritorySubArea" %>
<form name='gFormDistributionPointTerritorySubArea' id='gFormDistributionPointTerritorySubArea'>
  <g:hiddenField name="id" value="${distributionPointTerritorySubArea?.id}" />
  <g:hiddenField name="version" value="${distributionPointTerritorySubArea?.version}" />
  <div id="remote-content-distributionPointTerritorySubArea"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create DistributionPointTerritorySubArea</legend>
     <table>
      

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.distributionPoint.label" default="Distribution Point" /></td>
                <td><g:select name="distributionPoint.id" from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.distributionPoint?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.territorySubArea.label" default="Territory Sub Area" /></td>
                <td><g:select name="territorySubArea.id" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.territorySubArea?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${distributionPointTerritorySubArea?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${distributionPointTerritorySubArea?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointTerritorySubArea.dateUpdated.label" default="Date Updated" /></td>
                <td><g:datePicker name="dateUpdated" precision="day" value="${distributionPointTerritorySubArea?.dateUpdated}" default="none" noSelection="['': '']" /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDistributionPointTerritorySubArea();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDistributionPointTerritorySubArea();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormDistributionPointTerritorySubArea');" value="Cancel"/></span>
  </div>
</form>
