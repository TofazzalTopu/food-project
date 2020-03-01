<%@ page import="com.docu.commons.DateUtil" %>
<form name='gSearchRetailOrder' id='gSearchRetailOrder'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class='txtright bold hight1x width130'>
                        Secondary Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:textField name="orderNoSearch" maxlength="20" value=""/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width130">
                        Delivery Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="deliveryDateSearch" id="deliveryDateSearch" class="width100" onchange="clearAllGridData()"/>
                        <span class="button"><input type="button" name="search-button" id="search-button-retailOrder"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Search"
                                                    onclick="executeRetailOrderSearch();"/></span>
                    </div>
                </td>
                <td>
                    <label class='txtright bold hight1x width170'>
                        Include Any Pending Orders:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:checkBox name="includePendingOrder" checked="checked" value="${true}" onclick="addRemoveCheckBox(this)"/>
                    </div>
                </td>
            </tr>
        </table>
        <table style="width: 100%">
            <tr>
                <td style="width: 55%">
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-retailOrder"></table>
                    </div>
                </td>
                <td style="width: 45%">
                    <div class="jqgrid-container">
                        <table id="jqgrid-grid-finishProduct"></table>
                    </div>
                </td>
            </tr>
        </table>


        <div class="clear"></div>
        <div class="buttons" style="margin-left:10px;">
            <span class="button"><input type='button' name="check-button" id="process-button-auto"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Auto Process'
                                        onclick="executeAjaxAutoProcess();"/>
            </span>
            <span class="button"><input type='button' name="check-button" id="process-button-manually"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Process Manually'
                                        onclick="showManualProcess();"/>
            </span>
            <span class="button"><input type='button' name="check-button" id="check-button-retailOrder"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Check All Quantity'
                                        onclick="showAllRetailOrderDetails();"/>
            </span>
        </div>
        <div class="clear"></div>
        <br/>

    </div>
</form>
<div id="div-process-manually">
</div>