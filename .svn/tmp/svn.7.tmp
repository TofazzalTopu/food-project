<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.warehouse.SubWarehouse; com.bits.bdfp.inventory.product.FinishProduct; com.bits.bdfp.promotion.Promotion" %>
<style>
table tr td .cbox, table tr th .cbox {
    margin-left: 0 !important;
    margin-top: 3px;
}
</style>

<form name='gFormSetupBonusPromotion' id='gFormSetupBonusPromotion'>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table style="width: 100%;">
            <tr><td><h4>Promotion Information</h4></td></tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Select Promotion
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:select name="promotion.id" from="${Promotion.findAllByIsActive(true)}"
                              optionKey="id" optionValue="name"
                              noSelection="['': 'Select Promotion...']"
                              id="promotionId" value="" class="validate[required]" style="width: 160px;"/>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        As of Date
                    </label>
                </td>
                <td><g:textField name="asOfDate" id="asOfDate" value="${new Date().format('dd-MM-yyyy')}"
                                 class="validate[required]" readonly=""/></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Package Name
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="packageName" id="packageName" value="" class="validate[required]"/></td>
            </tr>
            <tr><td><h4>Select Eligible Customers</h4></td></tr>
            <tr><td>
                <div class="element_row_content_container lightColorbg pad_bot0">
                    <div class="jqgrid-container floatL" style="margin-left: 0px;">
                        <table id="tb-jqgrid-eligible-cc-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-territory-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-geo-grid"></table>
                    </div>

                    <div class="jqgrid-container floatL" style="margin-left: 5px;">
                        <table id="tb-jqgrid-eligible-customers-grid"></table>

                        <div id="tb-jqgrid-eligible-customers-pager"></div>
                    </div>

                    <div class="clearfix"></div>
                </div>
            </td></tr>

            <tr><td><h4>Select Product</h4></td></tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Select Product
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <select name="product.id" id="productId" class="" onchange="setProductRate('productRate',this.options[this.selectedIndex].getAttribute('data-product-rate'));" style="width: 165px">
                        <option value="" data-stock-id="" data-product-rate="">Select Product...</option>
                        <g:each in="${productList}" var="p">
                            <option value="${p.id}" data-stock-id="${p.stockId}" data-product-rate="${p.rate}">${p.name}</option>
                        </g:each>
                    </select>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Rate
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textField name="productRate" id="productRate" value="" class="" readonly=""/></td>
                <td></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Purchase Quantity
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="purchaseQuantity" id="purchaseQuantity" value="" class="check-numerical" style="width: 165px;"/></td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Quantity Limit
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:textField name="quantityLimit" id="quantityLimit" value=""  class="check-numerical"/>
                </td>
                <td>
                    <div class="buttons floatR">
                        <span class="button">
                            <input type="button" name="tb-add-button" id="tb-p-add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addProduct();"/>
                        </span>
                    </div>
                </td>
            </tr>
            <tr><td>
                <div class="element_row_content_container lightColorbg pad_bot0">
                    <div class="jqgrid-container" style="margin-left: 0px;">
                        <table id="jqgrid-eligible-products-grid"></table>
                        %{--<div id="jqgrid-eligible-products-pager"></div>--}%
                    </div>
                    <div class="clearfix"></div>
                </div>
            </td></tr>
            <tr><td><h4>Select Promotional Product/Discount</h4></td></tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Select Sub Inventory
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="subInventory.id" from="${SubWarehouse.findAllById(ApplicationConstants.BONUS_TYPE_INVENTORY_ID)}" style="width: 165px"
                              optionKey="id" optionValue="name"
                              noSelection="['': 'Select Sub Inventory...']"
                              id="subInventoryId" value="" class="" onchange="getPromotionalProductList(this.value)"/>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 150px;">
                        Rate
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textField name="promotionalProductRate" id="promotionalProductRate" value="" class="" readonly=""/></td>
                <td></td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Promotional Product
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <select name="promotionalProduct.id" id="promotionalProductId" class="" onchange="setProductRate('promotionalProductRate',this.options[this.selectedIndex].getAttribute('data-product-rate'));" style="width: 165px;">
                        <option value="" data-stock-id="" data-product-rate="">Select Product...</option>
                    </select>
                </td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 150px;">
                        Bonus Quantity
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td><g:textField name="bonusQuantity" id="bonusQuantity" value="" class="check-numerical"/></td>
                <td>
                    <div class="buttons floatR">
                        <span class="button">
                            <input type="button" name="tb-add-button" id="tb-pp-add-button"
                                   class="ui-button ui-widget ui-state-default ui-corner-all"
                                   value="Add"
                                   onclick="addPromotionalProduct();"/>
                        </span>
                    </div>
                </td>
            </tr>
            <tr><td>
                <div class="element_row_content_container lightColorbg pad_bot0">
                    <div class="jqgrid-container" style="margin-left: 0px;">
                        <table id="jqgrid-eligible-promotional-products-grid"></table>

                        %{--<div id="jqgrid-eligible-promotional-products-pager"></div>--}%
                    </div>

                    <div class="clearfix"></div>
                </div>
            </td></tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 165px;">
                        Discount Amount
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textField name="discountAmount" id="discountAmount" value="" class="check-fractional" style="width: 160px"/></td>

                <td>
                    <label class="txtright bold hight1x width1x" style="width: 150px;">
                        Remarks
                        %{--<span class="mendatory_field">*</span>--}%
                    </label>
                </td>
                <td><g:textArea name="remarks" id="remarks" value="" class="" style="width:200px;" /></td>
            </tr>
        </table>
    </div>

    <br/>

    <div class="buttons floatR">
        <span class="button"><input type="button" name="tb-create-button" id="tb-create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Save"
                                    onclick="executeAjaxPromotionSetup();"/></span>
    </div>
</form>
