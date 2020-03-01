

<%@ page import="com.bits.bdfp.customer.CustomerShippingAddress" %>
<form name='gFormCustomerShippingAddress' id='gFormCustomerShippingAddress'>
  <g:hiddenField name="id" value="${customerShippingAddress?.id}" />
  <g:hiddenField name="version" value="${customerShippingAddress?.version}" />
  <div id="remote-content-customerShippingAddress"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create CustomerShippingAddress</legend>
     <table>
      

            <tr>
              <td><g:message code="customerShippingAddress.customerMaster.label" default="Customer Master" /></td>
                <td><g:select name="customerMaster.id" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${customerShippingAddress?.customerMaster?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.address.label" default="Address" /></td>
                <td><g:textField name="address" value="${customerShippingAddress?.address}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.isActive.label" default="Is Active" /></td>
                <td><g:checkBox name="isActive" value="${customerShippingAddress?.isActive}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerShippingAddress?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerShippingAddress?.userUpdated?.id}" noSelection="['null': '']" /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${customerShippingAddress?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerShippingAddress.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${customerShippingAddress?.lastUpdated}" default="none" noSelection="['': '']" /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxCustomerShippingAddress();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCustomerShippingAddress();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormCustomerShippingAddress');" value="Cancel"/></span>
  </div>
</form>
