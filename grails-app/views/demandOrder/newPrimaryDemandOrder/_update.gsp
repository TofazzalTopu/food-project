<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<form name='gFormNewPrimaryDemandOrder' id='gFormNewPrimaryDemandOrder'>
    <g:hiddenField name="id" value="${primaryDemandOrder?.id}" />
    <g:hiddenField name="version" value="${primaryDemandOrder?.version}" />
    <g:hiddenField id="customerId" name="customerId" value="${primaryDemandOrder?.customerId}"/>
    <div id="remote-content-productPrice"></div>
    %{--<fieldset  class="ui-state-default ui-corner-all">--}%
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Primary Order No:
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField id="primaryOrderNo" name="primaryOrderNo" class="width173 validate[required]" value="${primaryDemandOrder?.orderNo}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Delivery Date:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <g:textField id="dateProposedDelivery" name="dateProposedDelivery" class="width80 validate[required]" value="${primaryDemandOrder?.dateProposedDelivery}"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Number:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width100" name="customerNumber" value="${primaryDemandOrder?.customerNumber}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width300" name="customerName" value="${primaryDemandOrder?.customerName}" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Customer Address:
                    </label>
                </td>
                <td>
                    %{--<div class='element-input inputContainer width500'>--}%
                        <g:textField class="width600" name="customerAddress" value="${primaryDemandOrder?.customerAddress}" readonly="readonly"/>
                    %{--</div>--}%
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select Product:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    %{--<div class='element-input-td inputContainer'>--}%
                        <input type="text" id="searchProductKey" name="searchProductKey" class="width500"/>
                        <input type="hidden" id="productId" name="productId" value=""/>
                        <input type="hidden" id="product" value=""/>
                        <input type="hidden" id="shipToAddress" value=""/>
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
                    <label class="txtright bold hight1x width1x">
                        Product Code:
                    </label>
                </td>
                <td>
                    <g:textField class="width240" name="productCode" id="productCode" value="" readonly="readonly"/>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Product Name:
                    </label>
                </td>
                <td>
                    <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Rate:
                    </label>
                </td>
                <td><g:textField class="width80 alin_right" name="rate" id="rate" value="" readonly="readonly"/></td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Quantity:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td><g:textField name="quantity" value=""/></td>
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
        <table>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x">
                        Select DP:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="distributionPoint.id" id="distributionPoint" from="${distributionPointList}" optionKey="id" optionValue="name" value="${primaryDemandOrder?.distributionPointId}" noSelection="['':'Select DP']"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width50" style="padding-left: 10px;">
                        Ship To:
                    </label>
                </td>
                <td>
                    <div class='element-input-td inputContainer'>
                        <input type="radio" name="shipTo" id="own" value="Own" checked> Own
                    &nbsp;&nbsp;&nbsp;<input type="radio" name="shipTo" id="ship" value="ShipAddress"> Ship Address
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width1x">
                        Ship to Address:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="shippingAddress.id" id="shippingAddress" from="" optionKey="id" value="${primaryDemandOrder.shippingAddressId}" noSelection="['':'']"/>
                    </div>
                </td>
            </tr>
        </table>
    </div>

</form>
<br/>
<div class="buttons">
    <span class="button"><input type="button" name="update-button" id="update-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Update" onclick="executeAjaxNewPrimaryDemandOrder();"/></span>
    %{--<span class="button"><input type="button" name="cancel-button" id="cancel-button" class="ui-button ui-widget ui-state-default ui-corner-all" onclick=" clean_form('#gFormProductPrice');" value="Cancel"/></span>--}%
</div>

