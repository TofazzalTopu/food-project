
<script type="text/javascript">
    function reset_business_dayform(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if(this.name!="financialYear.id"){
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked',false);
        $(formName +' input[name = create-button]').attr('value', 'Open');
        $(formName +' input[name = delete-button]').hide();

        $('#month').attr("disabled", false);

    }
</script>

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

<form name='gFormBusinessMonth' id='gFormBusinessMonth'>
    <g:hiddenField name="id" value="${businessDay?.id}"/>
    <g:hiddenField name="version" value="${businessDay?.version}"/>
    <div id="remote-content-division"></div>

    <div>
        <div class="element_container_big">
            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Year  :  '/></label>
                <label>${financialYearList[0]?.name}</label>
                <g:hiddenField name="financialYear.id" id="financialYearId" value="${financialYearList[0]?.id}" />
            </div>

            <div class="element-content-form-elements">
                <label style="padding-right: 5px;" for='countryInfo' class='bold txtalgnrght'><g:message
                        code='division.countryInfo.label' default='Financial Month : '/></label>
                <g:select name="month" id="month"
                          from="" optionKey="id"
                          value=""
                          noSelection="['': 'Select One']"/>

            </div>

            %{--<div class="element-content-form-elements">--}%
                %{--<label style="padding-right: 5px;" for='geoCode' class='bold txtalgnrght'><g:message--}%
                        %{--code='division.geoCode.label' default='Is Open : '/></label>--}%
                %{--<div class='element-input inputContainer '><g:checkBox name="isOpen"  /></div>--}%
            %{--</div>--}%
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
                                    value="Open"
                                    onclick="executeAjaxBusinessMonth();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button-division"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxDivision();"/></span>--}%
        %{--<span class="button"><input name="clearFormButtonDivision"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" type="button"--}%
                                    %{--onclick="reset_business_dayform('#gFormBusinessMonth');" value="Cancel"/></span>--}%
    </div>
</form>
