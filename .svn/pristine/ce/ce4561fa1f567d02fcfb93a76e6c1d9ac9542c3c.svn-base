

<%@ page import="com.bits.bdfp.bonus.CustomerBonusFinishGood" %>
<form name='gFormCustomerBonusFinishGood' id='gFormCustomerBonusFinishGood'>
  <g:hiddenField name="id" value="${customerBonusFinishGood?.id}" />
  <g:hiddenField name="version" value="${customerBonusFinishGood?.version}" />
  <div id="remote-content-customerBonusFinishGood"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create CustomerBonusFinishGood</legend>
     <table>
      

            <tr>
              <td><g:message code="customerBonusFinishGood.bonusCriteriaSetup.label" default="Bonus Criteria Setup" /></td>
                <td><g:select name="bonusCriteriaSetup.id" from="${com.bits.bdfp.bonus.BonusCriteriaSetup.list()}" optionKey="id" value="${customerBonusFinishGood?.bonusCriteriaSetup?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.bonusQuantity.label" default="Bonus Quantity" /></td>
                <td><g:textField name="bonusQuantity" value="${fieldValue(bean: customerBonusFinishGood, field: 'bonusQuantity')}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.dateCreated.label" default="Date Created" /></td>
                <td><g:datePicker name="dateCreated" precision="day" value="${customerBonusFinishGood?.dateCreated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.finishedProductBookedDetails.label" default="Finished Product Booked Details" /></td>
                <td><g:select name="finishedProductBookedDetails.id" from="${com.bits.bdfp.inventory.warehouse.FinishedProductBookedDetails.list()}" optionKey="id" value="${customerBonusFinishGood?.finishedProductBookedDetails?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.lastUpdated.label" default="Last Updated" /></td>
                <td><g:datePicker name="lastUpdated" precision="day" value="${customerBonusFinishGood?.lastUpdated}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.userCreated.label" default="User Created" /></td>
                <td><g:select name="userCreated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerBonusFinishGood?.userCreated?.id}"  /></td>
            </tr>
          

            <tr>
              <td><g:message code="customerBonusFinishGood.userUpdated.label" default="User Updated" /></td>
                <td><g:select name="userUpdated.id" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${customerBonusFinishGood?.userUpdated?.id}"  /></td>
            </tr>
          
         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxCustomerBonusFinishGood();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCustomerBonusFinishGood();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormCustomerBonusFinishGood');" value="Cancel"/></span>
  </div>
</form>
