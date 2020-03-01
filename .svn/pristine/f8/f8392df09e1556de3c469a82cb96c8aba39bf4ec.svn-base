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
                    <g:hiddenField name="vbProgramId" value="${volumeBasedIncentive?.id}" class="validate[required]"/>
                    <g:hiddenField name="vbVersion" value="${volumeBasedIncentive?.version}" class="validate[required]"/>
                    <g:textField name="vbProgramName" value="${volumeBasedIncentive?.programName}" class="validate[required]"/>
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
                    <g:textField name="vbEffectiveDateFrom" value="${formatDate(format:'dd-MM-yyyy',date:volumeBasedIncentive?.effectiveDateFrom)}" class="validate[required]"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    To
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbEffectiveDateTo" value="${formatDate(format:'dd-MM-yyyy',date:volumeBasedIncentive?.effectiveDateTo)}" />
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
                    Select Master Product
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select from="${com.bits.bdfp.inventory.product.MasterProduct.findAllByIsActive(true)}" name="vbMasterProduct"
                              value=""
                              optionKey="id"
                              noSelection="['':'Select master product...']"
                              onchange="getProductList();"
                              style="width:200px;"
                    />
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Select Main Product
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:select from="${com.bits.bdfp.inventory.product.MainProduct.findAllByIsActive(true)}" name="vbMainProduct"
                              value=""
                              optionKey="id"
                              noSelection="['':'Select main product...']"
                              onchange="getProductList();"
                              style="width:200px;"
                    />
                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td style="vertical-align: top;">
                <label class="txtright bold hight1x">
                    Select Product Set
                    <span class="mendatory_field">*</span>
                </label>
            </td>
            <td style="vertical-align: top; width: 200px;">
                <div class="jqgrid-container">
                    <table id="vb-jqgrid-all-products-grid"></table>
                </div>
            </td>

            <td>
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x" style="margin-left: 5px;">
                                Product Set Name
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <div class='inputContainer'>
                                <g:textField name="vbProductSetName"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x" style="margin-left: 5px;">
                                Primary UOM
                            </label>
                        </td>
                        <td>
                            <div class='inputContainer'>
                                <g:textField name="vbPrimaryUom"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <label class="txtright bold hight1x" style="margin-left: 5px;">
                                Master UOM
                            </label>
                        </td>
                        <td>
                            <div class='inputContainer'>
                                <g:select from="${com.bits.bdfp.settings.MeasureUnitConfiguration.findAllByIsMasterUom(true)}" name="vbMasterUom"
                                          value=""
                                          optionKey="id"
                                          noSelection="['':'Select master uom...   ']"
                                          onchange=""
                                />
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <table>
        <tr>
            <td>
                <label class="txtright bold hight1x">
                    Eligible volume From (in Master UOM)
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbVolumeFrom" value=""/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Eligible Volume To (in Master UOM)
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbVolumeTo" value=""/>
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
                    <g:radio name="incentiveType" value="amt"/>
                    Total Incentive Amount in value
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbIncentiveAmount" value=""/>
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
                    <g:radio name="incentiveType" value="pct"/>
                    Incentive Volume in %
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbVolumeInPct" value="" style="width: 60px;"/>
                </div>
            </td>
            <td>
                <label class="txtright bold hight1x" style="margin-left: 5px;">
                    Per unit (Master UOM) cost in BDT
                </label>
            </td>
            <td>
                <div class='inputContainer'>
                    <g:textField name="vbMasterUomUnitCost" value=""/>
                </div>
            </td>
            <td>
                <div class="buttons" style="margin-left: 20px;">
                <span class="button"><input type="button" name="vb-add-button" id="vb-add-button"
                class="ui-button ui-widget ui-state-default ui-corner-all"
                value="Add"
                onclick="addVbIncentive();"/></span>
                </div>
            </td>
        </tr>
    </table>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container">
        <table id="vb-jqgrid-incentive-slab-grid"></table>

        <div id="vb-jqgrid-incentive-slab-pager"></div>
    </div>
</div>


<div class="element_row_content_container lightColorbg pad_bot0">
    <h4>Select Customer For This Incentive</h4>

    <div class="jqgrid-container floatL">
        <table id="vb-jqgrid-eligible-territory-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="vb-jqgrid-eligible-geo-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="vb-jqgrid-eligible-pt-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="vb-jqgrid-eligible-sc-grid"></table>
    </div>

    <div class="jqgrid-container floatL" style="margin-left: 10px;">
        <table id="vb-jqgrid-eligible-cc-grid"></table>
    </div>

    <div class="clearfix"></div>
</div>

<div class="element_row_content_container lightColorbg pad_bot0">
    <h4></h4>

    <div class="jqgrid-container" style="margin-left: 0px;">
        <table id="vb-jqgrid-eligible-customers-grid"></table>

        <div id="vb-jqgrid-eligible-customers-pager"></div>
    </div>

    <div id="vbicList" class="jqgrid-container" style="margin-top: 10px;">
        <table id="vb-update-jqgrid-eligible-customers-grid"></table>

        <div id="vb-update-jqgrid-eligible-customers-pager"></div>
    </div>

    <div class="clearfix"></div>
</div>

<div class="buttons floatR">
    <span class="button"><input type="button" name="vb-create-button" id="vb-create-button"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Save"
                                onclick="executeAjaxIncentiveVolumeBased();"/></span>
</div>

