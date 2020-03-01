<form name='gFormSearchIncentive' id='gFormSearchIncentive'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        <g:message code="updateIncentive.effectiveDateFrom.label" default="Effective Date From"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="effectiveDateFrom" value="" class="validate[required]"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        <g:message code="updateIncentive.effectiveDateTo.label" default="To"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="effectiveDateTo" value="" class="validate[required]"/>
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
                        <g:message code="updateIncentive.programType.label" default="Select Incentive Type"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from="[['id':'tbi','name':'Target Based'],['id':'sabi','name':'Sales Amount Based'],
                                         ['id':'qbi','name':'Quantity Based'],['id':'vbi','name':'Volume Based']]"
                                  optionKey="id" optionValue="name"
                                  noSelection="['':'Select program type...']"
                                  name="programType" value="" class="validate[required]"
                                  onchange="getIncentivePrograms(this.value);"
                        />
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
                        <g:message code="updateIncentive.programName.label" default="Select Incentive Program"/>
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select program name...']"
                                  name="programName" value=""
                                  class="validate[required]"
                                  onchange="executeAjaxSearchIncentiveCustomers(this.value)"
                        />
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <h4></h4>

        <div class="jqgrid-container">
            <table id="jqgrid-search-grid"></table>

            <div id="jqgrid-search-pager"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="buttons">
            <span class="button"><input type="button" name="calculate-button" id="calculate-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Calculate"
                                        onclick="calculateIncentive();" style="margin-left: 0 !important;"/></span>

            <span class="button"><input type="button" name="adjust-button" id="adjust-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Adjust Against A/R"
                                        onclick="adjustIncentive();"/></span>
        </div>
    </div>
</form>

