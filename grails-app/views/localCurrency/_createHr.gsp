<div id="spinnerLocalCurrency" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormLocalCurrency' id='gFormLocalCurrency'>
  <g:hiddenField name="id" value="${localCurrency?.id}" />
  <g:hiddenField name="version" value="${localCurrency?.version}" />
  <div id="remote-content-localCurrency"></div>

  
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.userCreated.label' default='User Created' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:select name="userCreated.id" id="userCreated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${localCurrency?.userCreated?.id}"  />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.userUpdated.label' default='User Updated' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:select name="userUpdated.id" id="userUpdated" from="${com.docu.security.ApplicationUser.list()}" optionKey="id" value="${localCurrency?.userUpdated?.id}" noSelection="['null': '']" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.dateCreated.label' default='Date Created' />
    </label>
  
    <script type='text/javascript'>
        $(document).ready(function(){
            $('#dateCreated').mask("${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}",{});
        });
    </script>

    <div class='element-input inputContainer'>
         <g:textField name="dateCreated" id="dateCreated" value="${fieldValue(bean: localCurrency, field: 'dateCreated')}" />
    </div>
    
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.dateUpdated.label' default='Date Updated' />
    </label>
  
    <script type='text/javascript'>
        $(document).ready(function(){
            $('#dateUpdated').mask("${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}",{});
        });
    </script>

    <div class='element-input inputContainer'>
         <g:textField name="dateUpdated" id="dateUpdated" value="${fieldValue(bean: localCurrency, field: 'dateUpdated')}" />
    </div>
    
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.isActive.label' default='Is Active' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:checkBox name="isActive" value="${localCurrency?.isActive}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.name.label' default='Name' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="name" value="${localCurrency?.name}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.note.label' default='Note' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="note" value="${localCurrency?.note}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='localCurrency.symbol.label' default='Symbol' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="symbol" value="${localCurrency?.symbol}" />
    </div>
  
  </div>
    

  <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button">
            <input type="button" name="create-button" id="create-button-localCurrency"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="${message(code: 'default.button.create.label', default: 'Create')}"
                   onclick="executeAjaxLocalCurrency();"/>
        </span>
        <span class="button">
            <input type='button' name="delete-button" id="delete-button-localCurrency"
                   class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                   onclick="deleteAjaxLocalCurrency();"/>
        </span>
        <span class="button">
            <input name="clearFormButtonLocalCurrency" class="ui-button ui-widget ui-state-default ui-corner-all"
                   type="button" onclick=" reset_form('#gFormLocalCurrency');" value="Cancel"/>
        </span>
    </div>
</form>
