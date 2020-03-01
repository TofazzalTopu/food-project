

<%@ page import="com.bits.bdfp.inventory.sales.DistributionPointWarehouse" %>
<form name='gFormDistributionPointWarehouse' id='gFormDistributionPointWarehouse'>
  <g:hiddenField name="id" value="${distributionPointWarehouse?.id}" />
  <g:hiddenField name="version" value="${distributionPointWarehouse?.version}" />
  <div id="remote-content-distributionPointWarehouse"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create DistributionPointWarehouse</legend>
     <table>
      

            <tr>
              <td><g:message code="distributionPointWarehouse.distributionPoint.label" default="Distribution Point" /></td>
                <td><g:select name="distributionPoint.id" from="${com.bits.bdfp.inventory.sales.DistributionPoint.list()}" optionKey="id" value="${distributionPointWarehouse?.distributionPoint?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="distributionPointWarehouse.warehouse.label" default="Warehouse" /></td>
                <td><g:select name="warehouse.id" from="${com.bits.bdfp.inventory.warehouse.Warehouse.list()}" optionKey="id" value="${distributionPointWarehouse?.warehouse?.id}"  /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDistributionPointWarehouse();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDistributionPointWarehouse();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormDistributionPointWarehouse');" value="Cancel"/></span>
  </div>
</form>
