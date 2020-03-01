<%@ page import="com.bits.bdfp.inventory.sales.DistributionPoint" %>
<style>
.customLabel {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 100px;
}

.customLabel4 {
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin: 0;
    padding: 5px 10px 0;
    width: 160px;
}

.customLabel1 {
    background-color: #d7dfe7;
    color: #000000;
    display: block;
    float: left;
    font-size: 11px;
    font-weight: bold;
    height: 17px;
    margin-left: 4px;
    padding: 5px 10px 0;
    width: 160px;
}

.customInput {
    height: 18px;
    width: 100px;
}
</style>

<g:form name='gFormUpdateDemandOrder' id='gFormUpdateDemandOrder'>
    <g:render template="updateDemandOrderScript"/>
    <g:hiddenField name="id" id="orderId" value="${params?.id}"/>

    <div id="remote-content-distributionPoint"></div>
    <fieldset>
        <div style="float:left;">
            <table>
                <tr>
                    <td>
                        <table>

                            <tr>
                                <td>

                                    <label for='productId' class='customLabel'>Select Product:</label>

                                    <div class='element-input inputContainer'>
                                        <g:select class="width400" name="productId"
                                                  id="pId"
                                                  from="${list}"
                                                  optionKey="id"
                                                  optionValue="name"
                                                  onchange="setPriceValue();"
                                                  noSelection="['': 'Select Product']"/>
                                    </div>

                                </td>
                            </tr>
                            <tr>
                                <td>

                                    <label for='qty' class='customLabel'> Enter Qty: </label>
                                    <input type="hidden" id="rate" value="">
                                    <input type="hidden" id="productId" value="">
                                    <div class='element-input inputContainer' style="padding-left: 15px;">
                                        <g:textField class="customInput"
                                                     id="qty" name="qty" value=""/>

                                    </div>

                                </td>
                                <td>
                                    <label for='customerName' class='customLabel'>Customer:</label>
                                </td>

                                <td>
                                    <div >
                                        <input type="text" id="customerName" name="customerName" readonly="readonly" style="width: 250px;">
                                    </div>
                                </td>
                                <td style="padding-left: 20px;"></td>
                                <td>
                                    <div class="buttons">
                                        <span class="button"><input type="button" name="search" id="search-button"
                                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                    value="Add"
                                                                    onclick="addPrimaryDemandOrderDetails();"/></span>

                                    </div>
                                </td>

                            </tr>

                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div class="jqgrid-container">
                            <table id="jqgrid-grid-update-order"></table>
                            <div id="jqgrid-update-order-pager"></div>
                        </div>
                    </td>

                </tr>
            </table>

        </div>

        <div style="clear:both;"></div>
        <br/>
    </fieldset>

%{--<div class="buttons">--}%
%{--<span class="button"><input type="button" name="create-button" id="create-button"--}%
%{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
%{--value="Add Line Item"--}%
%{--onclick="executeAjaxDistributionPoint();"/></span>--}%
%{--<span class="button"><input type="button" name="cancel-button" id="cancel-button"--}%
%{--class="ui-button ui-widget ui-state-default ui-corner-all"--}%
%{--onclick="checkItemQuantity();" value="Allocate Batch"/></span>--}%

%{--</div>--}%

</g:form>


