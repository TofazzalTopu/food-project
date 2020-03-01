<%@ page import="com.bits.bdfp.settings.bussinessday.LocalHoliday" %>
<div id="spinnerDivision" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormLocalHoliday' id='gFormLocalHoliday'>
    <g:hiddenField name="id" value="${businessDay?.id}"/>
    <g:hiddenField name="version" value="${businessDay?.version}"/>
    <div id="remote-content-division"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Year  :  '/></label>
                <label>${financialYearList[0]?.name}</label>
                <g:hiddenField name="financialYearId" id="financialYearId" value="${financialYearList[0]?.id}"/>
                <g:hiddenField name="financialYear.id" value="${financialYearList[0]?.id}"/>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Territory: '/></label>
                %{--<label id="monthName"></label>--}%
                <div class='element-input inputContainer'>
                    <g:select name="territoryConfiguration.id" id="territoryConfiguration" class="validate[required]"
                              from="${com.bits.bdfp.geolocation.TerritoryConfiguration.list()}" optionKey="id"
                              optionValue="name"
                              value=""
                              noSelection="['': 'Select One']"/>
                </div>

            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Month : '/></label>
                %{--<label id="monthName"></label>--}%
                <g:hiddenField name="month" id="month" value=""/>
                <g:hiddenField name="year" id="year" value=""/>
                <div class='element-input inputContainer'>
                    <g:select name="monthSelect" id="monthSelect" class="validate[required]"
                              from="" optionKey="id"
                              value=""
                              onchange="getBusinessDay()"
                              noSelection="['': 'Select One']"/>
                </div>

            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Day : '/></label>

                <div class='element-input inputContainer'>
                    <g:select name="day" id="day" class="validate[required]"
                              from="" optionKey="id"
                              value=""
                              noSelection="['': 'Select One']"/>
                </div>
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Description : '/></label>

                <div class='element-input inputContainer'>
                    <g:textArea name="note" value=""/>
                </div>
            </div>

        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons mar_left10">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value="Save"
                                    onclick="executeAjaxHoliday();"/></span>
        <span class="button"><input type='button' name="delete-button" id="delete-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                    onclick="deleteAjaxHoliday();"/></span>
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="reset_holiday_form();" value="Cancel"/></span>

    </div>
</form>
