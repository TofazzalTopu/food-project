

<%@ page import="com.bits.bdfp.bonus.BonusCriteriaSetup" %>
<style>
    .requiredQuantityformError {
        left: 242px !important;
        margin-top: 109px !important;
    }
    .bonusQuantityformError {
        left: 242px !important;
        margin-top: 132px !important;
    }
</style>
<form name='gFormBonusCriteriaSetup' id='gFormBonusCriteriaSetup'>
  <g:hiddenField name="id" value="${bonusCriteriaSetup?.id}" />
  <g:hiddenField name="version" value="${bonusCriteriaSetup?.version}" />
  <div id="remote-content-bonusCriteriaSetup"></div>
  <fieldset  class="ui-state-default ui-corner-all">
    <legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Create BonusCriteriaSetup</legend>
     <table>
      

            <tr>
              <td><g:message code="bonusCriteriaSetup.finishProduct.label" default="Finish Product" /></td>
                <td><g:select name="finishProduct.id" from="${com.bits.bdfp.inventory.product.FinishProduct.list()}" optionKey="id" value="${bonusCriteriaSetup?.finishProduct?.id}" style="margin: 0 !important;"  /></td>
            </tr>
          

            <tr>
              <td>
                  <g:message code="bonusCriteriaSetup.requiredQuantity.label" default="Required Quantity" />
                  <span class="mendatory_field"> * </span>
              </td>
                <td><g:textField class="validate[required]" name="requiredQuantity" value="${fieldValue(bean: bonusCriteriaSetup, field: 'requiredQuantity')}" /></td>
            </tr>
          

            <tr>
              <td>
                  <g:message code="bonusCriteriaSetup.bonusQuantity.label" default="Bonus Quantity" />
                  <span class="mendatory_field"> * </span>
              </td>
                <td><g:textField class="validate[required]" name="bonusQuantity" value="${fieldValue(bean: bonusCriteriaSetup, field: 'bonusQuantity')}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="bonusCriteriaSetup.isMultiplexing.label" default="Is Multiplexing" /></td>
                <td><g:checkBox name="isMultiplexing" value="${bonusCriteriaSetup?.isMultiplexing}" /></td>
            </tr>
          

            <tr>
              <td><g:message code="bonusCriteriaSetup.isActive.label" default="Is Active" /></td>
                <td><g:checkBox name="isActive" value="${bonusCriteriaSetup?.isActive}" /></td>
            </tr>

         </table>
  </fieldset>
  <br/>
  <div class="buttons">
    <span class="button"><input type="button" name="create-button" id="create-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxBonusCriteriaSetup();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxBonusCriteriaSetup();"/></span>
    <span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" reset_form('#gFormBonusCriteriaSetup');" value="Cancel"/></span>
  </div>
</form>
