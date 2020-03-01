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
    <g:hiddenField name="id" value="${localCurrency?.id}"/>
    <g:hiddenField name="version" value="${localCurrency?.version}"/>
    <div id="remote-content-localCurrency"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='name' class='bold txtalgnrght'><g:message
                        code='localCurrency.name.label' default='Name'/></label>
                <g:textField name="name" id="name" value="${localCurrency?.name}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='symbol' class='bold txtalgnrght'><g:message
                        code='localCurrency.symbol.label' default='Symbol'/></label>
                <g:textField name="symbol" id="symbol" value="${localCurrency?.symbol}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='note' class='bold txtalgnrght hight2x'><g:message
                        code='localCurrency.note.label' default='Note'/></label>
                <g:textArea name="note" id="note" value="${localCurrency?.note}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='isActive' class='bold txtalgnrght'><g:message
                        code='localCurrency.isActive.label' default='Is Active'/></label>
                <g:checkBox name="isActive" id="isActive" value="${localCurrency?.isActive}"/>
            </div>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-localCurrency"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxLocalCurrency();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-localCurrency"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxLocalCurrency();"/></span>
        <span class="button"><input name="clearFormButtonLocalCurrency"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormLocalCurrency');" value="Cancel"/></span>
    </div>
</form>
