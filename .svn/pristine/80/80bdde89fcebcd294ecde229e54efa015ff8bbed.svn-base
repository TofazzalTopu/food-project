


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

  
  <div class="element-content-form-elements">
    <label style="padding-right: 5px;" class="bold txtalgnrght">
        <g:message code='bloodGroup.groupName.label' default='Group Name' />
    </label>
        <g:textField name="groupName" value="${bloodGroup?.groupName}" />

  </div>
    
  <div class="element-content-form-elements">
    <label style="padding-right: 5px;" class="bold txtalgnrght hight2x">
        <g:message code='bloodGroup.description.label' default='Description' />
    </label>
        <g:textArea cols="50" rows="2" name="description" value="${bloodGroup?.description}" />

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
