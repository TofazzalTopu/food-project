<%@ page import="com.bits.bdfp.common.District" %>
<form name='gFormDistrict' id='gFormDistrict'>
    <g:hiddenField name="id" value="${district?.id}"/>
    <g:hiddenField name="version" value="${district?.version}"/>
    <div id="remote-content-district"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='district.division.label' default='Country Name'/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="countryInfo.id" from="${com.bits.bdfp.common.CountryInfo.list()}" optionKey="id"
                      noSelection="['': 'Select Country']"
                      value="${district?.division?.countryInfo?.id}" onchange="selectDivision(this.value, 0)"
                      id="countryInfo"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='district.division.label' default='Division'/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="division.id" optionKey="id"
                      id="division"
                      noSelection="['': 'Select Division']"
                      value="${district?.division?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='district.geoCode.label' default='Geo Code'/>
            %{--<span class="mendatory_field"> * </span>--}%
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="geoCode" maxlength="30" value="${district?.geoCode}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='district.geoCode.label' default='District Name'/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" maxlength="30" value="${district?.name}"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxDistrict();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxDistrict();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="resetAll();" value="Cancel"/></span>
    </div>
</form>
