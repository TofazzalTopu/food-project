

<%@ page import="com.bits.bdfp.inventory.demandorder.SecondaryDemandOrderDetails" %>
<form name='gFormSecondaryDemandOrderDetails' id='gFormSecondaryDemandOrderDetails'>
  <g:hiddenField name="id" value="${secondaryDemandOrderDetails?.id}" />
  <g:hiddenField name="version" value="${secondaryDemandOrderDetails?.version}" />
  <div id="remote-content-secondaryDemandOrderDetails"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create SecondaryDemandOrderDetails</legend>
     <table>
      

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.secondaryDemandOrder.label" default="Secondary Demand Order" /></td>
                <td><g:select name="secondaryDemandOrder.id" from="${com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.secondaryDemandOrder?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.finishProduct.label" default="Finish Product" /></td>
                <td><g:select name="finishProduct.id" from="${com.bits.bdfp.inventory.product.FinishProduct.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.finishProduct?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.rate.label" default="Rate" /></td>
                <td><g:textField name="rate" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'rate')}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.quantity.label" default="Quantity" /></td>
                <td><g:textField name="quantity" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'quantity')}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.amount.label" default="Amount" /></td>
                <td><g:textField name="amount" value="${fieldValue(bean: secondaryDemandOrderDetails, field: 'amount')}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${secondaryDemandOrderDetails?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${secondaryDemandOrderDetails?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="secondaryDemandOrderDetails.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${secondaryDemandOrderDetails?.lastUpdated}" default="none" noSelection="['': '']" /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxSecondaryDemandOrderDetails();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxSecondaryDemandOrderDetails();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormSecondaryDemandOrderDetails');" value="Cancel"/></span>
  </div>
</form>
