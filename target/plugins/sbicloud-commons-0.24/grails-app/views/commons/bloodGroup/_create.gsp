


<%@ page import="com.docu.commons.BloodGroup" %>


<div id="spinnerBloodGroup" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormBloodGroup' id='gFormBloodGroup'>
  <g:hiddenField name="id" value="${bloodGroup?.id}" />
  <g:hiddenField name="version" value="${bloodGroup?.version}" />
  <div id="remote-content-bloodGroup"></div>

  
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='bloodGroup.groupName.label' default='Group Name' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="groupName" value="${bloodGroup?.groupName}" />
    </div>
  
  </div>
    
  <div class="element_row_content_container lightColorbg pad_bot0">
    <label class="txtright bold hight1x width1x">
        <g:message code='bloodGroup.description.label' default='Description' />
    </label>
  
    <div class='element-input inputContainer'>
        <g:textField name="description" value="${bloodGroup?.description}" />
    </div>
  
  </div>
    

  <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button">
            <input type="button" name="create-button" id="create-button-bloodGroup"
                   class="ui-button ui-widget ui-state-default ui-corner-all"
                   value="${message(code: 'default.button.create.label', default: 'Create')}"
                   onclick="executeAjaxBloodGroup();"/>
        </span>
        <span class="button">
            <input type='button' name="delete-button" id="delete-button-bloodGroup"
                   class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                   onclick="deleteAjaxBloodGroup();"/>
        </span>
        <span class="button">
            <input name="clearFormButtonBloodGroup" class="ui-button ui-widget ui-state-default ui-corner-all"
                   type="button" onclick=" reset_form('#gFormBloodGroup');" value="Cancel"/>
        </span>
    </div>
</form>
