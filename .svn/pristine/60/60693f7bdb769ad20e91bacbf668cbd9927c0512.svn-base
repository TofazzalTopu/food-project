


<div id="spinnerDocumentType" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>
<form name='gFormDocumentType' id='gFormDocumentType'>
  <g:hiddenField name="id" value="${documentType?.id}" />
  <g:hiddenField name="version" value="${documentType?.version}" />
    <div id="remote-content-documentType"></div>
    <div>

        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='name' class='bold txtalgnrght'><g:message code='documentType.name.label' default='Name' /></label>
                <g:textField name="name" value="${documentType?.name}" />
            </div>
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='note' class='bold txtalgnrght hight2x'><g:message code='documentType.note.label' default='Note' /></label>
                <g:textArea name="note" value="${documentType?.note}" />
            </div>
        </div>
        
  </div>
  <div class="clear"></div>
  <div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="create-button" id="create-button-documentType" class="ui-button ui-widget ui-state-default ui-corner-all" value="${message(code: 'default.button.create.label', default: 'Create')}" onclick="executeAjaxDocumentType();"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button-documentType" class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete' onclick="deleteAjaxDocumentType();"/></span>
    <span class="button"><input name="clearFormButtonDocumentType" class="ui-button ui-widget ui-state-default ui-corner-all" type="button" onclick=" reset_form('#gFormDocumentType');" value="Cancel"/></span>
  </div>
</form>
