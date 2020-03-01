<div id="spinnerCurrencyDemonstration" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormCurrencyDemonstration' id='gFormCurrencyDemonstration'>
  <g:hiddenField name="id" value="${currencyDemonstration?.id}" />
  <g:hiddenField name="version" value="${currencyDemonstration?.version}" />
  <div id="remote-content-currencyDemonstration"></div>

  
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.userCreated.label' default='User Created' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:select name="userCreated.id" id="userCreated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${currencyDemonstration?.userCreated?.id}"  />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.userUpdated.label' default='User Updated' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:select name="userUpdated.id" id="userUpdated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${currencyDemonstration?.userUpdated?.id}" noSelection="['null': '']" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.dateCreated.label' default='Date Created' />
    </label>
  
    <script type='text/javascript'>
        $(document).ready(function(){
            $('#dateCreated').mask("${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}",{});
        });
    </script>

    <div class='element-input inputContainer'>
         <g:textField name="dateCreated" id="dateCreated" value="${fieldValue(bean: currencyDemonstration, field: 'dateCreated')}" />
    </div>
    
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.dateUpdated.label' default='Date Updated' />
    </label>
  
    <script type='text/javascript'>
        $(document).ready(function(){
            $('#dateUpdated').mask("${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}",{});
        });
    </script>

    <div class='element-input inputContainer'>
         <g:textField name="dateUpdated" id="dateUpdated" value="${fieldValue(bean: currencyDemonstration, field: 'dateUpdated')}" />
    </div>
    
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.isActive.label' default='Is Active' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:checkBox name="isActive" value="${currencyDemonstration?.isActive}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.localCurrency.label' default='Local Currency' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:select name="localCurrency.id" id="localCurrency" from="" optionKey="id" value="${currencyDemonstration?.localCurrency?.id}"  />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.noteName.label' default='Note Name' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="noteName" value="${currencyDemonstration?.noteName}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='currencyDemonstration.value.label' default='Value' />
    </label>
  
    <div class='element-input inputContainer setup-css-numeric-currency'>
        <g:textField name="value" value="${fieldValue(bean: currencyDemonstration, field: 'value')}" />
    </div>
  
  </div>
    

  <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button">
            <input type="button" name="create-button" id="create-button-currencyDemonstration"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="${message(code: 'default.button.create.label', default: 'Create')}"
                   onclick="executeAjaxCurrencyDemonstration();"/>
        </span>
        <span class="button">
            <input type='button' name="delete-button" id="delete-button-currencyDemonstration"
                   class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                   onclick="deleteAjaxCurrencyDemonstration();"/>
        </span>
        <span class="button">
            <input name="clearFormButtonCurrencyDemonstration" class="ui-button ui-widget ui-state-default ui-corner-all"
                   type="button" onclick=" reset_form('#gFormCurrencyDemonstration');" value="Cancel"/>
        </span>
    </div>
</form>
