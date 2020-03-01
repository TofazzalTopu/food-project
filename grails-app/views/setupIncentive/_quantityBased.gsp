<div id="qbReload">
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
                    <g:hiddenField name="qbIncentiveId" value="${quantityBasedIncentive?.id}" class="validate[required]"/>
                    <g:hiddenField name="qbIncentiveVersion" value="${quantityBasedIncentive?.version}" class="validate[required]"/>
                    <g:textField name="qbProgramName" value="${quantityBasedIncentive?.programName}" class="validate[required]"/>
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
                    <g:textField name="qbEffectiveDateFrom" value="${formatDate(format:'dd-MM-yyyy',date:quantityBasedIncentive?.effectiveDateFrom)}" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    To
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="qbEffectiveDateTo" value="${formatDate(format:'dd-MM-yyyy',date:quantityBasedIncentive?.effectiveDateTo)}" />
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
                    Select Product
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select from="${productList}" name="qbProduct"
                              value=""
                              optionKey="id"
                              noSelection="['':'Select product...']"
                              onchange="getQbUom(this.value);"
                    />
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Primary UOM
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="qbUom" value="" readonly="true" style="width: 60px;"/>
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
                    Eligible Qty From(In Pcs)
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="qbQuantityFrom" value="" style="width: 60px;"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Eligible Qty To(In Pcs)
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="qbQuantityTo" value="" style="width: 60px;"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Incentive Amount in BDT
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="qbIncentiveAmount" value="" style="width: 100px;"/>
                </div>
            </td>
            <td>
                <div class="buttons" style="margin-left: 20px;">
                    <span class="button"><input type="button" name="qb-add-button" id="qb-add-button"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Add"
                                                onclick="addQbIncentive();"/></span>
                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container">
        <table id="qb-jqgrid-incentive-slab-grid"></table>

        <div id="qb-jqgrid-incentive-slab-pager"></div>
    </div>
</div>


<div class="element_row_content_container lightColorbg pad_bot0">
    <h4>Select Customer For This Incentive</h4>

    <div class="jqgrid-container floatL">
        <table id="qb-jqgrid-eligible-territory-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="qb-jqgrid-eligible-geo-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="qb-jqgrid-eligible-pt-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="qb-jqgrid-eligible-sc-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="qb-jqgrid-eligible-cc-grid"></table>
    </div>

    <div class="clearfix"></div>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container" style="margin-left: 0px;">
        <table id="qb-jqgrid-eligible-customers-grid"></table>

        <div id="qb-jqgrid-eligible-customers-pager"></div>
    </div>

    <div id="qbicList" class="jqgrid-container" style="margin-top: 10px;">
        <table id="qb-update-jqgrid-eligible-customers-grid"></table>

        <div id="qb-update-jqgrid-eligible-customers-pager"></div>
    </div>

    <div class="clearfix"></div>
</div>

<div class="buttons floatR">
    <span class="button"><input type="button" name="qb-create-button" id="qb-create-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Save"
                                onclick="executeAjaxIncentiveQuantityBased();"/></span>
</div>

</div>