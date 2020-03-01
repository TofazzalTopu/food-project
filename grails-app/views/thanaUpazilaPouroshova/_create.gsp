<%@ page import="com.bits.bdfp.common.ThanaUpazilaPouroshova" %>
<form name='gFormThanaUpazilaPouroshova' id='gFormThanaUpazilaPouroshova'>
    <g:hiddenField name="id" value="${thanaUpazilaPouroshova?.id}"/>
    <g:hiddenField name="version" value="${thanaUpazilaPouroshova?.version}"/>
    <div id="remote-content-thanaUpazilaPouroshova"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code='district.division.label' default='Country'/>
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
                      onchange="selectDistrict(this.value, 0)"
                      value="${district?.division?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="thanaUpazilaPouroshova.district.label" default="District"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="district.id" optionKey="id"
                      id="district"
                      noSelection="['': 'Select District']"
                      value="${thanaUpazilaPouroshova?.district?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="thanaUpazilaPouroshova.geoCode.label" default="Geo Code"/>
            %{--<span class="mendatory_field"> * </span>--}%
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="geoCode" maxlength="30" value="${thanaUpazilaPouroshova?.geoCode}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            <g:message code="thanaUpazilaPouroshova.name.label" default="Thana/Upazila Name"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" maxlength="50" value="${thanaUpazilaPouroshova?.name}"/>
        </div>

    </div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxThanaUpazilaPouroshova();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxThanaUpazilaPouroshova();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="resetAll();" value="Cancel"/></span>
    </div>
</form>
