<div id="spinnerRetailOrder" class="spinner" style="display:none;" align="left">
    <table class="spinnerTable">
        <tbody>
        <tr>
            <td><img src="${resource(dir: 'images', file: 'spinner.gif')}" alt="Spinner"/></td>
            <td>Communicating with the server... Please wait!</td>
        </tr>
        </tbody>
    </table>
</div>

<form name='gFormCashCollect' id='gFormCashCollect'>
<g:hiddenField name="customerId"  id="customerId"/>
<div id="remote-content-cashCollect"></div>

<div class="element_row_content_container lightColorbg pad_bot0">
<table>
    <tr class="element_row_content_container lightColorbg pad_bot0">
        <td>
            <label class="txtright bold hight1x width120">
                Delivery Date From:
            </label>
        </td>
        <td>
            <div class='element_row_container inputContainer'>
                <g:textField class="width100 validate[required]" name="dateFrom" value=""/>
            </div>
        </td>
        <td>
            <label class="txtright bold hight1x width60">
                To:
            </label>
        </td>
        <td>
            <div class='element_row_container inputContainer'>
                <g:textField class="width100 validate[required]" name="dateTo" value=""/>
            </div>
        </td>
    </tr>
<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width120">
            Customer:
            <span class="mendatory_field"> * </span>
        </label>
    </td>
    <td>
        <input type="text" id="searchCustomerKey" name="searchCustomerKey" class="width400 validate[required]"/>
        <span id="search-btn-customer-register-id" title="" role="button"
              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
            <span class="ui-button-text"></span>
        </span>
    </td>
</tr>

<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width120">
            Customer Legacy ID:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="width100" name="legacyId" value="" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width120">
            Customer Number:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="width100" name="customerNumber" value="" readonly="readonly"/>
        </div>
    </td>
</tr>
<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width120">
            Customer Name:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer width320'>
            <g:textField class="width300" name="customerName" value="" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width120">
            Customer Address:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="width300" name="customerAddress" value="" readonly="readonly"/>
        </div>
    </td>
</tr>
</table>
<div class="jqgrid-container">
    <table id="jqgrid-grid-retailInvoice"></table>
</div>
</div>

<div class="clear"></div>

<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="apply-button" id="apply-button-cashCollect"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="Cancel Invoice"
                                onclick="executeAjaxCancelInvoice();"/></span>
</div>
</form>
