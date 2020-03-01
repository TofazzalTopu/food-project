<div id="targetBasedIncentive">
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x">
                        Incentive Program Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:hiddenField name="tbIncentiveId" value="${targetBasedIncentive?.id}"
                                       class="validate[required]"/>
                        <g:hiddenField name="tbIncentiveVersion" value="${targetBasedIncentive?.version}"
                                       class="validate[required]"/>
                        <g:textField name="tBprogramName" value="${targetBasedIncentive?.programName}"
                                     class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        Effective Date From
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="tBeffectiveDateFrom"
                                     value="${formatDate(format: 'dd-MM-yyyy', date: targetBasedIncentive?.effectiveDateFrom)}"
                                     class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        To
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="tBeffectiveDateTo"
                                     value="${formatDate(format: 'dd-MM-yyyy', date: targetBasedIncentive?.effectiveDateTo)}"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x">
                        Achievement % from
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="tBachievementFrom" class="validate[funcCall[checkTbAchievementFrom]]"
                                     value="" style="width: 60px;"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        Achievement % To
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="tBachievementTo" class="validate[funcCall[checkTbAchievementTo]]" value=""
                                     style="width: 60px;"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        Incentive Amount total (BDT)
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="tBincentiveAmount" value="" style="width: 100px;"/>
                    </div>
                </td>
                <td>
                    <div class="buttons" style="margin-left: 20px;">
                        <span class="button"><input type="button" name="tb-add-button" id="tb-add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addTbIncentive();"/></span>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4></h4>

        <div class="jqgrid-container">
            <table id="tb-jqgrid-incentive-slab-grid"></table>

            <div id="tb-jqgrid-incentive-slab-pager"></div>
        </div>
    </div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4>Select Customer For This Incentive</h4>

        <div class="jqgrid-container floatL">
            <table id="tb-jqgrid-eligible-territory-grid"></table>

            %{--<div id="jqgrid-eligible-territory-pager"></div>--}%
        </div>

        <div class="jqgrid-container floatL" style="margin-left: 10px;">
            <table id="tb-jqgrid-eligible-geo-grid"></table>

            %{--<div id="jqgrid-eligible-geo-pager"></div>--}%
        </div>

        <div class="jqgrid-container floatL" style="margin-left: 10px;">
            <table id="tb-jqgrid-eligible-pt-grid"></table>

            %{--<div id="jqgrid-eligible-pt-pager"></div>--}%
        </div>

        <div class="jqgrid-container floatL" style="margin-left: 10px;">
            <table id="tb-jqgrid-eligible-sc-grid"></table>

            %{--<div id="jqgrid-eligible-sc-pager"></div>--}%
        </div>

        <div class="jqgrid-container floatL" style="margin-left: 10px;">
            <table id="tb-jqgrid-eligible-cc-grid"></table>

            %{--<div id="jqgrid-eligible-cc-pager"></div>--}%
        </div>

        <div class="clearfix"></div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4></h4>

        <div class="jqgrid-container" style="margin-left: 0px;">
            <table id="tb-jqgrid-eligible-customers-grid"></table>

            <div id="tb-jqgrid-eligible-customers-pager"></div>
        </div>

        <div id="tbicList" class="jqgrid-container" style="margin-top: 10px;">
            <table id="tb-update-jqgrid-eligible-customers-grid"></table>

            <div id="tb-update-jqgrid-eligible-customers-pager"></div>
        </div>

        <div class="clearfix"></div>
    </div>

    <div class="buttons floatR">
        <span class="button"><input type="button" name="tb-create-button" id="tb-create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Save"
                                    onclick="executeAjaxIncentiveTargetBased();"/></span>
    </div>

</div>