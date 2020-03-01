<%@ page import="com.bits.bdfp.common.UnionInfo" %>

<form name='gFormUnionInfo' id='gFormUnionInfo'>
    <g:hiddenField name="id" value="${unionInfo?.id}"/>
    <g:hiddenField name="version" value="${unionInfo?.version}"/>
    <div id="remote-content-unionInfo"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width2x">
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
        <label class="txtright bold hight1x width2x">
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
        <label class="txtright bold hight1x width2x">
            <g:message code="thanaUpazilaPouroshova.district.label" default="District"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="district.id" optionKey="id"
                      id="district"
                      onchange="selectThana(this.value, 0)"
                      noSelection="['': 'Select District']"
                      value="${thanaUpazilaPouroshova?.district?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width2x">
            <g:message code="unionInfo.thanaUpazilaPouroshova.label" default="Thana"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:select class="validate[required]" name="thanaUpazilaPouroshova.id"
                      id="thanaUpazilaPouroshova"
                      noSelection="['': 'Select District']"
                      optionKey="id" value="${unionInfo?.thanaUpazilaPouroshova?.id}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width2x">
            <g:message code="unionInfo.geoCode.label" default="Geo Code"/>
            %{--<span class="mendatory_field"> * </span>--}%
        </label>

        <div class='element-input inputContainer'>
            <g:textField name="geoCode" maxlength="30" value="${unionInfo?.geoCode}"/>
        </div>

    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width2x">
            <g:message code="unionInfo.name.label" default="Union/Pourashava Name"/>
            <span class="mendatory_field"> * </span>
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="validate[required]" name="name" maxlength="50" value="${unionInfo?.name}"/>
        </div>

    </div>

    <div class="clear"></div>

    <div class="buttons">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxUnionInfo();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxUnionInfo();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="resetAll();" value="Cancel"/></span>
    </div>
</form>
