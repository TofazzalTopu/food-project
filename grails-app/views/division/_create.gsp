<%@ page import="com.bits.bdfp.common.Division" %>
<form name='gFormDivision' id='gFormDivision'>
    <g:hiddenField name="id" value="${division?.id}"/>
    <g:hiddenField name="version" value="${division?.version}"/>
    <div id="remote-content-division"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="division.countryInfo.label" default="Country Name"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="countryInfo.id" from="${com.bits.bdfp.common.CountryInfo.list()}" optionKey="id"
                      noSelection="['':'Select Country...']"
                      id="countryInfo"
                      value="${division?.countryInfo?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="division.geoCode.label" default="Geo Code"/>
            %{--<span class="mendatory_field"> * </span>--}%
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="geoCode" maxlength="30" value="${division?.geoCode}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="division.name.label" default="Division Name"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="name" maxlength="30" class="validate[required]" value="${division?.name}"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxDivision();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxDivision();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick=" reset_form('#gFormDivision');" value="Cancel"/></span>
    </div>
</form>
