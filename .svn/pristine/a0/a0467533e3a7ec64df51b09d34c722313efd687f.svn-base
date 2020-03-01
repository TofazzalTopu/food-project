<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
.height18 {
    height: 30px;
}
</style>
<g:form name='gFormUpdateDemandOrder' id='gFormUpdateDemandOrder'>
    <g:render template="productBatchAllocationScript"/>
    <g:hiddenField name="id" id="product_id" value="${params?.id}"/>
    <g:hiddenField name="warehouseId" id="warehouseId" value="${params.warehouseId}"/>

    <h3>Batch Wise Item Allocation</h3>
    <div class="element_row_content_container lightColorbg pad_bot0">

                        <table>
                            <tr class="element_row_content_container lightColorbg pad_bot0">
                                <td>
                                    <label class="txtright bold hight1x width100">
                                        Product Name:
                                    </label>

                                    <div class='element_row_container inputContainer'>
                                        <g:textField class="height18 width200" readonly="readonly"
                                                     id="name" name="name" value="${list[0]?.name}"/>
                                    </div>

                                </td>
                                <td>
                                    <label class="txtright bold hight1x width100">
                                        Product Code:
                                    </label>

                                    <div class='element_row_container inputContainer'>
                                        <g:textField class="height18 width180" readonly="readonly"
                                                     id="code" name="code" value="${list[0]?.code}"/>
                                    </div>

                                </td>

                            </tr>
        <tr class="element_row_content_container lightColorbg pad_bot0">
            <td>
                <label class="txtright bold hight1x width100">
                    Order Quantity:
                </label>
                <div class='element_row_container inputContainer'>
                    <g:textField class="height18 width80" readonly="readonly"
                                 id="orderQty" name="orderQty" value="${list[0]?.quantity}"/>
                </div>

            </td>
        </tr>
                        </table>
                        <div class="jqgrid-container">
                            <table id="jqgrid-grid-product-batch"></table>

                        </div>

    <table>
                <tr class="element_row_content_container lightColorbg pad_bot0">
                    <td>
                        <label class="txtright bold hight1x width100">
                            Pick Quantity:
                        </label>

                        <div class='element_row_container inputContainer'>
                            <g:textField class="height18 width100" readonly="readonly"
                                         id="pickQty" name="pickQty" value=""/>
                        </div>

                            <span class="button"><input type="button" name="create-button" id="create-button"
                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                        value="Add"
                                                        onclick="getBatchWiseProductInfo(${params.id});"/></span>
                    </td>

                </tr>
            </table>

        </div>

        <div style="clear:both;"></div>
        <br/>
</g:form>


