


<%@ page import="com.bits.bdfp.customer.CustomerEligibilityMaster" %>


<div id="spinnerCustomerEligibilityMaster" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormCustomerEligibilityMaster' id='gFormCustomerEligibilityMaster'>
  <g:hiddenField name="id" value="${customerEligibilityMaster?.id}" />
  <g:hiddenField name="version" value="${customerEligibilityMaster?.version}" />
    <div id="remote-content-customerEligibilityMaster"></div>
    <div>
      

        
          <div class="element_container_big">
            <div class="block-title">
                <div class='element-title'>
 <g:message code='customerEligibilityMaster.eligibilityTemplate.label' default='Customer' />
<span class="mendatory_field"> * </span> 

</div>
                <div class="clear"></div>
            </div>
            <div class="block-input">
                <div class='element-input inputContainer '><g:select name="customerMaster.id" id="customerMaster" from="${com.bits.bdfp.customer.CustomerMaster.list()}" optionKey="id" value="${customerMaster?.id}"  onchange="getValuesForCustomer(this.value)"/></div>
                <div class="clear"></div>
            </div>
          </div>
        
  </div>
  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-customerEligibilityMaster" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxCustomerEligibilityMaster();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button-customerEligibilityMaster" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxCustomerEligibilityMaster();"/></span>
    <span class="button"><input name="clearFormButtonCustomerEligibilityMaster" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormCustomerEligibilityMaster');" value="Cancel"/></span>
  </div>
</form>
