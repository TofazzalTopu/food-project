

<%@ page import="com.bits.bdfp.customer.CustomerTerritorySubArea" %>
<form name='gFormCustomerTerritorySubArea' id='gFormCustomerTerritorySubArea'>
  <g:hiddenField name="id" value="${customerTerritorySubArea?.id}" />
  <g:hiddenField name="version" value="${customerTerritorySubArea?.version}" />
  <div id="remote-content-customerTerritorySubArea"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create CustomerTerritorySubArea</legend>
     <table>
      

            <tr>
              <td><g:message code="customerTerritorySubArea.customerMaster.label" default="Customer Master" /></td>
                <td><g:select name="customerMaster.id" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${customerTerritorySubArea?.customerMaster?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerTerritorySubArea.territorySubArea.label" default="Territory Sub Area" /></td>
                <td><g:select name="territorySubArea.id" from="${com.bits.bdfp.geolocation.TerritorySubArea.list()}" optionKey="id" value="${customerTerritorySubArea?.territorySubArea?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerTerritorySubArea.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerTerritorySubArea?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerTerritorySubArea.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerTerritorySubArea?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerTerritorySubArea.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${customerTerritorySubArea?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerTerritorySubArea.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${customerTerritorySubArea?.lastUpdated}" default="none" noSelection="['': '']" /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxCustomerTerritorySubArea();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCustomerTerritorySubArea();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormCustomerTerritorySubArea');" value="Cancel"/></span>
  </div>
</form>
