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

<g:form name='gFormUpdateSecondaryDemandOrder' id='gFormUpdateSecondaryDemandOrder'>
    <g:render template="updateSecondaryDemandOrderScript"/>
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

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label'
                                            default='Select Product'/></label>
</td><td>
                                        <g:select name="productId" style="max-width: 140px;"
                                                  id="pId"
                                                  from="${list}"
                                                  optionKey="id"
                                                  optionValue="name"
                                                  onchange="setPriceValue();"
                                                  noSelection="['': 'Select Product']" />

                                </td>
                                <td>

                                    <label style="padding-right: 5px;" for='countryInfo' class='customLabel'><g:message
                                            code='division.countryInfo.label' default='Enter Qty'/></label>
                                    <input type="hidden" id="rate" value="">
                                    <input type="hidden" id="productId" value="">
    </td>
                                    <td>
                                        <g:textField class="cutomInput"
                                                     id="qty" name="qty" value=""/>


                                </td>
                                <td style="min-width: 20px;"></td>
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
                            <div id="#jqgrid-update-order-pager"></div>
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


