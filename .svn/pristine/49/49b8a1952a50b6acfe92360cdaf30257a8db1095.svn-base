<%@ page import="com.docu.commons.DateUtil" %>
<form name='subInventoryToSubInventoryTransferForm' id='subInventoryToSubInventoryTransferForm'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width130 alin_left" style="margin-left: 5px;">
                        Distribution Point
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"

                                  name="distributionPoint" value=""
                                  id="distributionPoint"
                                  class="validate[required] width230"
                                  onchange="setInventoryByDistributionPoint(this.value);"
                        />
                        %{--noSelection="['':'Select Distribution Point']"--}%
                    </div>
                </td>

            </tr>


            <tr>
                <td>
                    <label class="txtright bold hight1x width130 alin_left" style="margin-left: 5px;">
                        Inventory Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"
                                  name="inventory.id" value=""
                                  id="inventory"
                                  class="validate[required] width230"
                                  onchange="getSubInventoryList(this.value);"
                        />
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width150 alin_left" style="margin-left: 5px;">
                        Date
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="transferDate" readonly="true" class="validate[required]" value="${DateUtil.getCurrentDateFormatAsString()}"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width130 alin_left" style="margin-left: 5px;">
                        Sub-Inventory From
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select sub-inventory from...']"
                                  name="subInventoryFrom.id" value=""
                                  id="subInventoryFrom"
                                  class="validate[required] width230"
                                  onchange="setSubInventoryFromId(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x width150 alin_left" style="margin-left: 5px;">
                        Sub-Inventory From ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer '>
                        <g:textField name="subInventoryFromId" value="" class="validate[required]" readonly="true"/>
                    </div>
                </td>
            </tr>

            <tr>
                <td>
                    <label class="txtright bold hight1x width130 alin_left" style="margin-left: 5px; align-content: flex-start;">
                        Sub-Inventory To
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:select from=""
                                  optionKey="id"
                                  optionValue="name"
                                  noSelection="['':'Select sub-inventory to...']"
                                  name="subInventoryTo.id" value=""
                                  id="subInventoryTo"
                                  class="validate[required] width230"
                                  onchange="setSubInventoryToId(this.value);"
                        />
                    </div>
                </td>

                <td>
                    <label class="txtright bold hight1x width150 alin_left" style="margin-left: 5px;">
                        Sub-Inventory To ID
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='inputContainer'>
                        <g:textField name="subInventoryToId" value="" class="validate[required]" readonly="true"/>
                    </div>
                </td>
            </tr>
        </table>

    </div>

%{--    <g:textField name="subInventoryToIdText" value="" class="validate[required]" />--}%

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container" style="margin-left: 5px;">
            <h4></h4>
            <table id="jqgrid-product-grid"></table>

            <div id="jqgrid-product-pager"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="buttons">
            <span class="button"><input type="button" name="transfer-button" id="transfer-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Transfer"
                                        onclick="transferProduct();"/></span>
        </div>
    </div>
</form>


