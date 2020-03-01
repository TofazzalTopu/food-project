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
                    <g:hiddenField name="abIncentiveId" value="${salesAmountBasedIncentive?.id}" class="validate[required]"/>
                    <g:hiddenField name="abIncentiveVersion" value="${salesAmountBasedIncentive?.version}" class="validate[required]"/>
                    <g:textField name="aBprogramName" value="${salesAmountBasedIncentive?.programName}" class="validate[required]"/>
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
                    <g:textField name="aBeffectiveDateFrom" value="${formatDate(format:'dd-MM-yyyy',date:salesAmountBasedIncentive?.effectiveDateFrom)}" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    To
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="aBeffectiveDateTo" value="${formatDate(format:'dd-MM-yyyy',date:salesAmountBasedIncentive?.effectiveDateTo)}" />
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
                    Sales Value from (in BDT)
                    %{--<span class="mendatory_field">*</span>--}%
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="aBachievementFrom" class="validate[funcCall[checkAchievementFrom]]" value=""/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Sales Value To (in BDT)
                    %{--<span class="mendatory_field">*</span>--}%
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="aBachievementTo" class="validate[funcCall[checkAchievementTo]]" value=""/>
                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    <g:radio name="incentiveType" value="amt"/>
                    Incentive Amount
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="aBincentiveAmount" value=""/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    <g:radio name="incentiveType" value="pct"/>
                    Incentive % of sales
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="aBincentivePct" value=""/>
                </div>
            </td>
            <td>
                <div class="buttons" style="margin-left: 20px;">
                    <span class="button"><input type="button" name="ab-add-button" id="ab-add-button"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addAbIncentive();"/></span>
                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container">
        <table id="ab-jqgrid-incentive-slab-grid"></table>

        <div id="ab-jqgrid-incentive-slab-pager"></div>
    </div>
</div>


<div class="element_row_content_container lightColorbg pad_bot0">
    <h4>Select Customer For This Incentive</h4>

    <div class="jqgrid-container floatL">
        <table id="ab-jqgrid-eligible-territory-grid"></table>

        %{--<div id="jqgrid-eligible-territory-pager"></div>--}%
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="ab-jqgrid-eligible-geo-grid"></table>

        %{--<div id="jqgrid-eligible-geo-pager"></div>--}%
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="ab-jqgrid-eligible-pt-grid"></table>

        %{--<div id="jqgrid-eligible-pt-pager"></div>--}%
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="ab-jqgrid-eligible-sc-grid"></table>

        %{--<div id="jqgrid-eligible-sc-pager"></div>--}%
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="ab-jqgrid-eligible-cc-grid"></table>

        %{--<div id="jqgrid-eligible-cc-pager"></div>--}%
    </div>

    <div class="clearfix"></div>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container" style="margin-left: 0px;">
        <table id="ab-jqgrid-eligible-customers-grid"></table>

        <div id="ab-jqgrid-eligible-customers-pager"></div>
    </div>

    <div id="sabicList" class="jqgrid-container" style="margin-top: 10px;">
        <table id="ab-update-jqgrid-eligible-customers-grid"></table>

        <div id="ab-update-jqgrid-eligible-customers-pager"></div>
    </div>

    <div class="clearfix"></div>
</div>

<div class="buttons floatR">
    <span class="button"><input type="button" name="ab-create-button" id="ab-create-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Save"
                                onclick="executeAjaxIncentiveAmountBased();"/></span>
</div>

