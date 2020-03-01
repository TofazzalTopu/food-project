<form name='gFormUpdateIncentive' id='gFormUpdateIncentive'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x" style="margin-left: 5px;">
                        Effective Date From
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
                        To
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
                        Incentive Program Type
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
                        Incentive Program Name
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select program name...']"
                                  name="programName" value=""/>
                    </div>
                </td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="search-button" id="search-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="executeAjaxSearchIncentiveProgram();"/></span>
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
</form>

