<form name="gFormAddressType" id="gFormAddressType">

    <g:hiddenField name="id" value="${addressType?.id}"/>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='addressType.addressType.label' default='Address Type'/>  <span class="red">*</span>
        </label>

        <div class='element-input inputContainer '>
            <g:textField name="addressTypeName" id="addressTypeName" class="w-p25" value="${addressType?.addressTypeName}"/>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight3x width1x">
            <g:message code='addressType.description.label' default='Description'/>
        </label>

        <div class='element-input inputContainer '>
            <g:textArea name="description" cols="50" rows="2" id="description" class="w-p66" value="${addressType?.description}"/>
        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-addressType"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxAddressType();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-addressType"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxAddressType();"/></span>
        <span class="button"><input name="clearFormButtonAddressType"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormAddressType');" value="Cancel"/></span>
    </div>

</form>


