<form name='gFormRegisterFG' id='gFormRegisterFG'>
    <div id="remote-content-registerFG"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x">
            Source Type
        </label>

        <div class='element-input inputContainer width300'>
            <input type="radio" name="sourceType" value="invoice" checked>Invoice
            <input type="radio" name="sourceType" value="challan">Transfer Challan
        </div>
    </div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <label class="txtright bold hight1x width1x" id="sourceNo">
            Invoice No/Challan No
        </label>

        <div class='element-input inputContainer'>
            <g:textField class="width120" name="invoiceNo" value="" />
        </div>

        <label class="txtright bold hight1x width230">
            Invoice Date/Challan Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From
        </label>

        <div class='element-input inputContainer width100'>
            <g:textField class="width80" name="fromDate" value="" />
        </div>

        <label class="txtright bold hight1x width20">
            To
        </label>

        <div class='element-input inputContainer width180'>
            <g:textField class="width80" name="toDate" value="" />
            <span class="button"><input type="button" name="search-button" id="search-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Search" onclick="searchReceivableOrder()"/></span>
        </div>

    </div>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <div class="jqgrid-container" style="float: left">
            <table id="jqgrid-grid-register-fg"></table>
        </div>
        <div style="float: right;">
            <table>
                <tr>
                    <td>
                        <label class="txtright bold hight1x width100">
                            Inventory<span class="mendatory_field"> * </span>
                        </label>
                    </td>
                    <td>
                        <div class='element-input inputContainer width230'>
                            <g:select class="validate[required] width220" name="warehouse.id" id="warehouse" from="${wareHouseList}" optionKey="id" optionValue="name" onchange="loadSubWarehouse(this.value)" noSelection="['':'Select Inventory']"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="txtright bold hight1x width100">
                            Sub Inventory<span class="mendatory_field"> * </span>
                        </label>
                    </td>
                    <td>
                        <div class='element-input inputContainer width230'>
                            <g:select class="validate[required] width220" name="subWarehouse.id" id="subWarehouse" from="" optionKey="id" noSelection="['':'Select Sub Inventory']"/>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </div>

    <div class="clear"></div>
</form>
