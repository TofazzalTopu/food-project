<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.DateUtil; com.bits.bdfp.inventory.retailorder.RetailOrder" %>


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

<form name='gFormRetailOrder' id='gFormRetailOrder'>
<g:hiddenField name="id" value="${retailOrder?.id}"/>
<g:hiddenField name="version" value="${retailOrder?.version}"/>
<g:hiddenField name="enterprise.id" id="enterprise" value="${retailOrder?.enterprise?.id}"/>
<g:hiddenField name="orderPlacedFor.id" id="customerId" value="${retailOrder?.orderPlacedFor?.id}"/>
<g:hiddenField name="deliveryMan.id" id="deliveryMan" value="${deliveryMan?.id}"/>
<g:hiddenField name="territorySubArea.id" id="territorySubArea" value="${retailOrder?.territorySubArea?.id}"/>
<div id="remote-content-retailOrder"></div>

<div class="element_row_content_container lightColorbg pad_bot0">
<table>
<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width100">
            Order Date:"
            <span class="mendatory_field"> * </span>
        </label>

    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField name="orderDateRead" id="orderDate" class="validate[required] width100"
                         value="${orderDate}" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width100">
            Delivery Date:
            <span class="mendatory_field"> * </span>
        </label>

    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField name="deliveryDate" id="deliveryDate" class="validate[required]"
                         value="${deliveryDate}"/>
        </div>
    </td>
    <td>
        <label class='txtright bold hight1x width100'>
            Retail Order No:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer '>
            <g:textField name="orderNoRead" maxlength="20" value="${retailOrder?.orderNo}" readonly="readonly"/>
        </div>
    </td>
</tr>
<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width100">
            Enterprise:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer '>
            <g:textField class="width150" name="enterPriseName" id="enterPriseName" readonly="readonly" value="${retailOrder?.enterprise?.name}"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width100">
            Geo Location:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="minWidth200" name="territorySubAreaName"
                      value="${retailOrder?.territorySubArea?.geoLocation}" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width60">
            Road:
            <span class="mendatory_field"> * </span>
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="minWidth200" name="territorySubAreaRoad"
                         value="${retailOrder?.territorySubArea?.road}" readonly="readonly"/>
        </div>
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
            <g:textField class="width100" name="legacyId" value="${retailOrder?.orderPlacedFor?.legacyId}" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width120">
            Customer Number:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="width100" name="customerNumber" value="${retailOrder?.orderPlacedFor?.code}" readonly="readonly"/>
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
            <g:textField class="width300" name="customerName" value="${retailOrder?.orderPlacedFor?.name}" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width120">
            Customer Address:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer'>
            <g:textField class="width300" name="customerAddress" value="${retailOrder?.orderPlacedFor?.presentAddress}" readonly="readonly"/>
        </div>
    </td>
</tr>

<tr class="element_row_content_container lightColorbg pad_bot0">
    <td>
        <label class='txtright bold hight1x width120'>
            Delivery Man:<span class="mendatory_field"> * </span>
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer width320'>
            <g:textField class="width300" name="deliveryManInfo" value="[${deliveryMan?.code}] ${deliveryMan?.name}" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class='txtright bold hight1x width100'>
            Order Placed By:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer '>
            <g:textField class="width100" name="orderPlacedByUser" value="${retailOrder?.orderPlacedBy?.username}" readonly="readonly"/>
        </div>
    </td>
</tr>
<tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width120">
            Select Product:
        </label>
    </td>
    <td>
        %{--<div class='element-input-td inputContainer width400'>--}%
        <input type="text" id="searchProductKey" name="searchProductKey" class="width400"/>
        <input type="hidden" id="productId" name="productId"/>
        <input type="hidden" id="productCode" />
        <input type="hidden" id="product" />
        <span id="search-btn-customer-product-id" title="" role="button"
              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
            <span class="ui-button-text"></span>
        </span>
        %{--</div>--}%
    </td>
</tr>
<tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
    <td>
        <label class="txtright bold hight1x width120">
            Product Name:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer width260'>
            <g:hiddenField name="productCode" id="productCode" value=""/>
            <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width100">
            Rate:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer width100'>
            <g:textField class="width80" name="rate" id="rate" value="" readonly="readonly"/>
        </div>
    </td>
    <td>
        <label class="txtright bold hight1x width100">
            Quantity:
        </label>
    </td>
    <td>
        <div class='element_row_container inputContainer width120'>
            <g:textField class="width100" name="quantity" value=""/>
        </div>
    </td>
    <td>
        <div class="buttons">
            <span class="button"><input type="button" name="add-button" id="add-button"
                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                        value="Add"
                                        onclick="addFinishProductToGrid();"/></span>
        </div>
    </td>
</tr>
</table>
<div class="jqgrid-container">
    <table id="jqgrid-grid-finishProduct"></table>
</div>
</div>

<div class="clear"></div>

<div class="buttons" style="margin-left:10px;">
    <span class="button"><input type="button" name="update-button" id="update-button-retailOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                value="${message(code: 'default.button.update.label', default: 'Update')}"
                                onclick="executeAjaxRetailOrder(false);"/></span>
    <span class="button"><input type='button' name="delete-button" id="delete-button-retailOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'
                                onclick="deleteAjaxRetailOrder();"/></span>
    <span class="button"><input name="submitRetailOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                onclick="executeAjaxRetailOrder(true);" value="Submit"/>
    </span>
    <span class="button"><input name="clearFormButtonRetailOrder"
                                class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                onclick=" reset_formRetailOrder('#gFormRetailOrder');" value="Cancel"/>
    </span>
</div>
</form>

