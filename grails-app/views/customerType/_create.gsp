<%@ page import="com.bits.bdfp.customer.CustomerType" %>
<form name='gFormCustomerType' id='gFormCustomerType'>
    <g:hiddenField name="id" value="${customerType?.id}"/>
    <g:hiddenField name="version" value="${customerType?.version}"/>
    <div id="remote-content-customerType"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerType.name.label" default="Customer Type"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" value="${customerType?.name}" class="validate[required]" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerType.name.label" default="Receivable A/C Code"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="receivableCode" value="${customerType?.receivableCode}" class="validate[required]" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerType.name.label" default="Advance A/C Code"/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="advanceCode" value="${customerType?.advanceCode}" class="validate[required]" maxlength="50"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="customerType.note.label" default="Note"/>
        </label>

        <div class='element-input inputContainer'>
            <g:textArea name="note" value="${customerType?.note}" maxlength="512"/>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxCustomerType();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxCustomerType();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormCustomerType');" value="Cancel"/></span>
    </div>
</form>
