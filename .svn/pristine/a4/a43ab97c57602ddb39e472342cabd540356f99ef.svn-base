<%@ page import="com.bits.bdfp.common.CountryInfo" %>
<form name='gFormCountryInfo' id='gFormCountryInfo'>
    <g:hiddenField name="id" value="${countryInfo?.id}"/>
    <g:hiddenField name="version" value="${countryInfo?.version}"/>
    <div id="remote-content-countryInfo"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='countryInfo.code.label' default='Country Code'/>
            %{--<span class="mendatory_field">*</span>--}%
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="code" maxlength="20" value="${countryInfo?.code}"/>
        </div>

    </div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='countryInfo.name.label' default='Country Name'/>
            <span class="mendatory_field">*</span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" maxlength="30" class="validate[required]" value="${countryInfo?.name}"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxCountryInfo();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxCountryInfo();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormCountryInfo');" value="Cancel"/></span>
    </div>
</form>
