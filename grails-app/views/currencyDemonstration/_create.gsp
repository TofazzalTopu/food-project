
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
    <g:hiddenField name="id" value="${currencyDemonstration?.id}"/>
    <g:hiddenField name="version" value="${currencyDemonstration?.version}"/>
    <div id="remote-content-currencyDemonstration"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='localCurrency' class='bold txtalgnrght'><g:message
                        code='currencyDemonstration.localCurrency.label' default='Local Currency'/></label>
                <g:select name="localCurrency.id" id="localCurrency"
                          from=""
                          optionKey="id" style="width: 145px;height: 21px;"
                          value=""/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='noteName' class='bold txtalgnrght'><g:message
                        code='currencyDemonstration.noteName.label' default='Note Name'/></label>
                <g:textField name="noteName" id="noteName"
                             value="${currencyDemonstration?.noteName}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='value' class='bold txtalgnrght'><g:message
                        code='currencyDemonstration.value.label' default='Value'/></label>
                <g:textField name="value" id="value"
                             value="${fieldValue(bean: currencyDemonstration, field: 'value')}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='isActive' class='bold txtalgnrght'><g:message
                        code='currencyDemonstration.isActive.label' default='Is Active'/></label>
                <g:checkBox name="isActive" id="isActive" value="${currencyDemonstration?.isActive}"/>
            </div>
        </div>
    </div>


    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-currencyDemonstration"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxCurrencyDemonstration();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button-currencyDemonstration"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxCurrencyDemonstration();"/></span>
        <span class="button"><input name="clearFormButtonCurrencyDemonstration"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_form('#gFormCurrencyDemonstration');" value="Cancel"/></span>
    </div>
</form>
