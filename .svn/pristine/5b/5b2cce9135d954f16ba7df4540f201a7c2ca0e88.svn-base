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
    width: 160px;
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

.cutomInput {
    height: 18px;
    width: 160px;
}
</style>

<div class="main_container" style="overflow: hidden;">
    <h3>Product List</h3>
    <g:form name='gFormUpdateProductSalesCommission' id='gFormUpdateProductSalesCommission'>
        <g:render template="editSalesCommission/updateProductSalesCommissionScript"/>

        <div id="remote-content-distributionPoint"></div>
        <fieldset>
            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>
                    <tr>
                        <td>
                            <label class="txtright bold hight1x width150">
                                Effective Date From
                                <span class="mendatory_field">*</span>
                            </label>
                        </td>
                        <td>
                            <div class='element-input inputContainer'>
                                <g:textField name="customerEffectiveDateFrom" id="customerEffectiveDateFrom" class="width140"/>
                            </div>
                        </td>
                        <td>
                            <div style="padding-left: 20px">
                                <label class="txtright bold hight1x width175">
                                    Effective Date To
                                    <span class="mendatory_field">*</span>
                                </label>

                            </div>
                        </td>
                        <td>
                            <div class='element-input inputContainer'>
                                <g:textField name="customerEffectiveDateTo" id="customerEffectiveDateTo" class="width140"/>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>

            <div style="float:left;">
                <g:hiddenField name="cid" id="cid" value="${params?.cscId}"/>
                <g:hiddenField name="productId" id="productId"/>

                <table>
                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td>

                                        <label style="padding-right: 5px;" class='customLabel'>Select Product:</label>

                                        <div class='element-input inputContainer'>
                                            <g:select name="pId" class="width300"
                                                      id="pId" optionKey="id" optionValue="productInfo"
                                                      from="${list}"
                                                      noSelection="['': 'Select Product']"/>
                                        </div>
                                    </td>
                                    <td class="width180"></td>
                                    <td>
                                        <div class="buttons">
                                            <span class="button"><input type="button" name="search" id="search-button"
                                                                        class="ui-button ui-widget ui-state-default ui-corner-all"
                                                                        value="Add"
                                                                        onclick="addProductDetails();"/></span>

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

                            </div>
                        </td>

                    </tr>
                </table>

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
                                    <g:textField name="branchCommission" id="branchCommission"
                                                 class="width140 validate[required,funcCall[checkBranchCommission]]"
                                                 onkeyup="insertSalesManCommission(this.value);"/>
                                </div>
                            </td>
                            <td>
                                <div style="padding-left: 20px">
                                    <label class="txtright bold hight1x width175">
                                        Sales Man Commission %
                                        <span class="mendatory_field">*</span>
                                    </label>

                                </div>
                            </td>
                            <td>
                                <div class='element-input inputContainer'>
                                    <g:textField name="salesManCommission" id="salesManCommission"
                                                 class="width140 validate[required]"
                                                 readonly="readonly"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="buttons">
                    <span class="button"><input type="button" name="search" id="save-button"
                                                class="ui-button ui-widget ui-state-default ui-corner-all"
                                                value="Submit" onclick="updateDateForCustomer();"/></span>
                </div>
            </div>

            <div style="clear:both;"></div>
            <br/>
        </fieldset>

    </g:form>
</div>


