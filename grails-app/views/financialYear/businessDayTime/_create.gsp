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

<form name='gFormBusinessDayTime' id='gFormBusinessDayTime'>
    <g:hiddenField name="id" value="${division?.id}"/>
    <g:hiddenField name="version" value="${division?.version}"/>
    <div id="remote-content-division"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Year  : '/></label>
                <label>${financialYearList[0]?.name}</label>
                <g:hiddenField name="financialYearId" id="financialYearId" value="${financialYearList[0]?.id}" />
            </div>
            <script type="text/javascript">
                $(function () {
                    $('#startTime').timepicker({showPeriod: true, showLeadingZero: true});
                    $('#endTime').timepicker({showPeriod: true, showLeadingZero: true});
                });
            </script>
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='geoCode' class='bold txtalgnrght'><g:message
                        code='division.geoCode.label' default='Start Time : '/></label>
                <g:textField name="startTime" id="startTime" />
            </div>
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='geoCode' class='bold txtalgnrght'><g:message
                        code='division.geoCode.label' default='End Time : '/></label>
                <g:textField name="endTime" id="endTime" />
            </div>

            %{--<div class="element-content-form-elements">--}%
                %{--<label style="padding-right: 5px;" for='name' class='bold txtalgnrght'><g:message--}%
                        %{--code='division.name.label' default='Name'/></label>--}%
                %{--<g:textField name="name" id="name" value="${division?.name}"/>--}%
            %{--</div>--}%
        </div>
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="create-button" id="create-button-division"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Save"
                                    onclick="executeAjaxBusinessDayTime();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button-division"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxDivision();"/></span>--}%
        %{--<span class="button"><input name="clearFormButtonDivision"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" type="button"--}%
                                    %{--onclick=" reset_business_day_time_form('#gFormBusinessDayTime');" value="Cancel"/></span>--}%
    </div>
</form>
