<%@ page import="com.bits.bdfp.inventory.setup.SalesCommission;com.bits.bdfp.geolocation.TerritoryConfiguration" %>
<form name="gFormSalesCommission" id="gFormSalesCommission">
    <g:hiddenField name="id" value="${salesCommission?.id}"/>
    <g:hiddenField name="version" value="${salesCommission?.version}"/>
    <div id="remote-content-salesCommission"></div>
    <style>
    #jqgh_cb .cbox {
        margin-left: 0 !important;
        margin-right: 3px;
        display: inherit;
    }

    #jqgrid-grid tr td .cbox {
        margin-left: 0 !important;
        margin-top: 3px;
    }

    #jqgrid-grid2 tr td .cbox {
        margin-left: 0 !important;
        margin-top: 3px;
    }
    </style>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Territory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select class="validate[required]" name="territory"
                                  from="${TerritoryConfiguration.list()}" optionKey="id"
                                  noSelection="['': '- Select Territory -']"
                                  id="territory" onchange="selectTerritory(this.value);"
                                  value=""/>
                    </div></td>
                <td>
                    <div style="padding-left: 100px">
                        <label class="txtright bold hight1x width1x">
                            Effective Date From
                            <span class="mendatory_field">*</span>
                        </label>
                    </div>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="validate[required] width120" name="dateEffectiveFrom" value=""/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select DP
                        <span class="mendatory_field">*</span>
                    </label></td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select class="validate[required]" name="distributionPoint" optionKey="id"
                                  noSelection="['': '- Select DP -']"
                                  id="dp" onchange="selectDP(this.value);"
                                  value=""/>

                    </div>
                </td>
                <td>
                    <div style="padding-left: 100px">
                        <label class="txtright bold hight1x width1x ">
                            Effective Date To
                            <span class="mendatory_field">*</span>
                        </label>
                    </div>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="validate[required] width120" name="dateEffectiveTo" value=""/>
                    </div>
                </td>
            </tr>
        </table>
    </div>


    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField name="salesCommissionName" value="" class="width140 validate[required]"/>
                    </div>
                </td>

            </tr>
        </table>
    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField name="customerName" value="" class="width140 validate[required]"
                                     readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <div style="padding-left: 100px">
                        <label class="txtright bold hight1x width1x">
                            Customer ID
                            <span class="mendatory_field">*</span>
                        </label>

                    </div>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField name="customerId" value="" readonly="readonly"
                                     class="width140 validate[required]"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div>
        <label class="txtright bold hight1x width400">
            Select Eligible Customer (Sales Man) for Commission Setup
        </label>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            %{--<div id="jqgrid-pager"></div>--}%
        </div>
    </div>

    <div>
        <label class="txtright bold hight1x width400">
            Select Eligible Product For Commission
        </label>
    </div>

    %{--<div class="element_row_content_container lightColorbg pad_bot0">--}%
    %{--<label class="txtright bold hight1x width220">--}%
    %{--Select Eligible Product For Commission--}%
    %{--</label>--}%

    %{--<div class='element-input inputContainer width400'>--}%
    %{--<g:select name="productInfoId"--}%
    %{--from="${com.bits.bdfp.inventory.product.FinishProduct.list()}" optionKey="id"--}%
    %{--noSelection="['': '- Select Product -']"--}%
    %{--id="productInfo" style="min-width: 300px;"/>--}%
    %{--<span class="button">--}%
    %{--<input type="button" name="add-button" id="add-button"--}%
    %{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
    %{--value="Add"--}%
    %{--onclick="addNewItemToProductCollectionGrid();"/>--}%
    %{--</span>--}%
    %{--</div>--}%

    %{--</div>--}%

    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container">
            <table id="jqgrid-grid2"></table>

            <div id="jqgrid-pager2"></div>
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width150">
                        Branch Commission %
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField name="branchCommission" value=""
                                     class="width140 validate[required,funcCall[checkBranchCommission]]"
                                     onkeyup="insertSalesManCommission(this.value);"/>
                    </div>
                </td>
                <td>
                    <div style="padding-left: 100px">
                        <label class="txtright bold hight1x width175">
                            Sales Man Commission %
                            <span class="mendatory_field">*</span>
                        </label>

                    </div>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField name="salesManCommission" value="" class="width140 validate[required]"
                                     readonly="readonly"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

    <div>
        <table>
            <tr>
                <td style="padding-left: 750px">
                    <span class="button">
                        <input type="button" name="add-button"
                               class="ui-button ui-widget ui-state-default ui-corner-all"
                               value="Save"
                               onclick="executeAjaxSalesCommission();"/>
                    </span>
                </td>
            </tr>
        </table>
    </div>
</form>