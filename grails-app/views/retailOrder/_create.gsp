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
    <g:hiddenField name="id" value=""/>
    <g:hiddenField name="version" value="0"/>
    <g:hiddenField name="enterprise.id"  id="enterprise"/>
    <g:hiddenField name="orderPlacedFor.id"  id="customerId"/>
    <g:hiddenField name="deliveryMan.id"  id="deliveryMan"/>
    <div id="remote-content-retailOrder"></div>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Order Date:
                        <span class="mendatory_field"> * </span>
                    </label>

                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="orderDate" id="orderDate" class="validate[required] width150"
                                     value="${DateUtil.getCurrentDateFormatAsString()}" readonly="readonly"/>
                        %{--<g:textField name="orderDate" id="orderDate" class="validate[required] width150"--}%
                                     %{--value="${DateUtil.getCurrentDateFormatAsString()}"/>--}%
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
                        <g:textField name="deliveryDate" id="deliveryDate" class="validate[required] width150"
                                                                           value="${DateUtil.getNextDateFormatAsString()}"/>
                        %{--<g:textField name="deliveryDate" id="deliveryDate" class="validate[required] width150"--}%
                                     %{--value="${DateUtil.getNextDateFormatAsString()}"/>--}%
                    </div>


                </td>
                <td>
                    <label class='txtright bold hight1x width100'>
                        Retail Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:textField name="orderNo" maxlength="20" value="AUTO" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Enterprise:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                    <g:if test="${result}">
                        <g:if test="${list.size()==1}">
                            <g:textField class="width150" name="enterPriseName" id="enterPriseName" readonly="readonly" value="${list[0].name}"/>
                            <script type="text/javascript">
                                $(document).ready(function(){
                                    $("#enterprise").val("${list[0].id}");
                                });
                            </script>
                        </g:if>
                        <g:else>
                            <div id="enterpriselist"></div>
                            <script type="text/javascript">
                                jQuery(document).ready(function () {

                                    $("#enterpriselist").empty();

                                    $("#enterpriselist").flexbox(${result}, {
                                        watermark: "Select Enterprise",
                                        width: 160,
                                        onSelect: function () {
                                            $("#enterprise").val($('#enterpriselist_hidden').val());
                                        }
                                    });
                                    $('#enterpriselist_input').addClass("validate[required]");
                                    $('#enterpriselist_input').blur(function () {
                                        if ($('#enterpriselist_input').val() == '') {
                                            $("#enterprise").val("");
                                        }
                                    });
                                });
                            </script>
                        </g:else>
                    </g:if>
                    <g:else>
                        <g:textField name="enterPriseName" readonly="readonly" value=""/>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                            });
                        </script>
                    </g:else>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Geo Location:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:select class="minWidth200 validate[required]" name="territorySubArea.id"
                                  id="territorySubArea"
                                  optionKey="id" optionValue="geo_location"
                                  from="" onchange="changeGeolocation(this.value)"
                                  value="" noSelection="['':'Select Geo Location']"/>
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
                        <g:textField class="minWidth200" name="road" id="road" value="" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Customer:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    %{--<div class='element-input-td inputContainer width300'>--}%
                        <input type="text" id="searchCustomerKey" name="searchCustomerKey" class="width400 validate[required]"/>
                        <span id="search-btn-customer-register-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    %{--</div>--}%
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

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class='txtright bold hight1x width120'>
                        Delivery Man:<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width320'>
                        <g:if test="${userTypeId == ApplicationConstants.USER_TYPE_CUSTOMER}">
                            <g:textField class="width300" name="deliveryManInfo" value="${deliveryMan}" readonly="readonly"/>
                            <script type="text/javascript">
                                $(document).ready(function(){
                                     $("#deliveryMan").val("${deliveryManId}");
                                })
                            </script>
                        </g:if>
                        <g:else>
                            <div id="deliveryManList"></div>
                        </g:else>

                    </div>
                </td>
                <td>
                    <label class='txtright bold hight1x width100'>
                        Order Placed By:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <sec:loggedInUserInfo field="username"/>
                    </div>
                </td>
            </tr>
      %{--      <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width120">
                        Select Product:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    --}%%{--<div class='element-input-td inputContainer width400'>--}%%{--
                        <input type="text" id="searchProductKey" name="searchProductKey" class="width400"/>
                        <input type="hidden" id="productId" name="productId"/>
                        <input type="hidden" id="productCode" />
                        <input type="hidden" id="product" />
                        <span id="search-btn-customer-product-id" title="" role="button"
                              class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                            <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                            <span class="ui-button-text"></span>
                        </span>
                    --}%%{--</div>--}%%{--
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
                    <div class='element_row_container inputContainer width100'>
                        <g:hiddenField class="width80" name="qtyInLtr" id="qtyInLtr" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Quantity:
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width120'>
                        <g:textField class="width100" name="quantity" id="quantity" value=""/>
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
--}%
        </table>
        <div class="jqgrid-container">
            <table id="jqgrid-grid-finishProduct"></table>
            <div id="jqgrid-product-pager"></div>
        </div>


       %{-- <div class="element_row_content_container lightColorbg pad_bot0">
            <div class="jqgrid-container" style="margin-left: 5px;">
                <h4></h4>
                <table id="jqgrid-product-grid"></table>

                <div id="jqgrid-product-pager"></div>
            </div>
        </div>--}%
    </div>

    <div class="clear"></div>

    <div class="buttons" style="margin-left:10px;">
        <span class="button"><input type="button" name="submit-button" id="submit-button-retailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Submit"
                                    onclick="executeAjaxRetailOrder(true);"/></span>
        <span class="button"><input type="button" name="save-button" id="save-button-retailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="Save"
                                    onclick="executeAjaxRetailOrder(false);"/></span>
        <span class="button"><input name="clearFormButtonRetailOrder"
                                    class="ui-button ui-widget ui-state-default ui-corner-all" type="button"
                                    onclick=" reset_formRetailOrder('#gFormRetailOrder');" value="Cancel"/>
        </span>
    </div>
</form>
