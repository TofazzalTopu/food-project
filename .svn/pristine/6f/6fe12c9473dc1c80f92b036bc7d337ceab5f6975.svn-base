<%@ page import="com.bits.bdfp.common.Nationality" %>
<form name='gFormNationality' id='gFormNationality'>
    <g:hiddenField name="id" value="${nationality?.id}"/>
    <g:hiddenField name="version" value="${nationality?.version}"/>
    <div id="remote-content-nationality"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='nationality.name.label' default='Name' />
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" value="${nationality?.name}" />
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='nationality.note.label' default='Note' />
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" cols="40" rows="5" value="${nationality?.note}"/>
        </div>

    </div>
<div class="clear"></div>
    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxNationality();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxNationality();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormNationality');" value="Cancel"/></span>
    </div>
</form>
