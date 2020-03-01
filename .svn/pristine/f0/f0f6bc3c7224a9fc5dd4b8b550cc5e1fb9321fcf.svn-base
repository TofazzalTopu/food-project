<%@ page import="com.bits.bdfp.common.Designation" %>
<form name='gFormDesignation' id='gFormDesignation'>
    <g:hiddenField name="id" value="${designation?.id}"/>
    <g:hiddenField name="version" value="${designation?.version}"/>
    <div id="remote-content-designation"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="designation.code.label" default="Code"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="code" value="${designation?.code}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="designation.name.label" default="Name"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" value="${designation?.name}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="designation.note.label" default="Note"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${designation?.note}"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxDesignation();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxDesignation();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormDesignation');" value="Cancel"/></span>
    </div>
</form>
