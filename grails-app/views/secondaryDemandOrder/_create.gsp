<%@ page import="com.docu.commons.DateUtil; com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.SecondaryDemandOrder" %>
<form name='gFormSecondaryDemandOrder' id='gFormSecondaryDemandOrder'>
    <g:hiddenField name="id" value="${secondaryDemandOrder?.id}" />
    <g:hiddenField name="version" value="${secondaryDemandOrder?.version}" />
    <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
    <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
    <g:hiddenField name="customerMaster.id" id="customerId" value=""/>
    <g:hiddenField name="userTentativeDelivery.id" id="deliveryCustomerId"/>
    <g:hiddenField name="enterpriseConfiguration"  id="enterpriseConfiguration"/>
    <div id="remote-content-territoryConfiguration"></div>
    <br>

    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 105px;">
                        <g:message code="secondaryDemandOrder.enterpriseConfiguration.label"
                                   default="Enterprise "/>
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>

                <g:if test="${result}">
                    <g:if test="${list.size()==1}">
                        <td>
                            <g:textField name="enterPriseName" id="enterPriseName" readonly="readonly" value="${list[0].name}" style="width: 300px;"/>
                            <script type="text/javascript">
                                $(document).ready(function(){
                                    $("#enterpriseConfiguration").val("${list[0].id}");
                                });
                            </script>
                        </td>
                    </g:if>
                    <g:else>
                        <td><div id="enterpriselist" style="width: 300px;"></div></td>
                        <script type="text/javascript">
                            %{--alert("${result}");--}%
                            jQuery(document).ready(function () {

                                $("#enterpriselist").empty();

                                $("#enterpriselist").flexbox(${result}, {
                                    watermark: "Select Enterprise",
                                    width: 260,
                                    onSelect: function () {
                                        $("#enterpriseConfiguration").val($('#enterpriselist_hidden').val());
                                    }
                                });
                                $('#enterpriselist_input').addClass("validate[required]");
                                $('#enterpriselist_input').blur(function () {
                                    if ($('#enterpriselist_input').val() == '') {
                                        $("#enterpriseId").val("");
                                        $("#enterpriseConfiguration").val("");
                                    }
                                });
                            });
                        </script>
                    </g:else>
                </g:if>
                <g:else>
                    <td>
                        <g:textField name="enterPriseName" readonly="readonly" value=""/>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                MessageRenderer.showHeaderMessage("You have no assigned enterprise, please assign enterprise first.", 0)
                            });
                        </script>
                    </td>
                </g:else>
                <td>
                    <label class="txtright bold hight1x width1x" style="width: 100px;">
                        Customer<span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td> <div class='element-input-td inputContainer'>
                    <input type="text" id="customer" name="customer" class="width205 validate[required] " readonly="readonly"/>
                    <g:if test="${userType == ApplicationConstants.USER_TYPE_CUSTOMER}">
                        <script type="text/javascript">
                            $(document).ready(function(){
                                $("#customer").val("[${customerMaster.code}] ${customerMaster.name}");
                                $("#customerId").val("${customerMaster.id}");
                                generateGeoSelectList("${customerMaster.id}");
                                loadProduct("${customerMaster.id}");
                            })
                        </script>
                    </g:if>
                    </div>
                </td>
            </tr>
            <g:textField name="name" id="name" readonly="readonly" hidden="hidden"/>

            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width105">
                        Order Placed By<span class="mendatory_field"> * </span>
                    </label>

                </td>
                <td><g:textField  name="orderBy" value="${applicationUser?.username}" readonly="readonly" class="width150"/></td>
                <g:if test="${userType == ApplicationConstants.USER_TYPE_OTHER}">
                    <td>
                        <label class="txtright bold hight1x width150">
                            Tentative Delivery Man<span class="mendatory_field"> * </span>
                        </label>

                    </td>
                    <td>
                        <div class='element-input-td inputContainer'>
                            <g:if test="${tentativeDeliveryManList}">
                                <g:if test="${deliveryManList?.size()==1}">
                                    <g:textField name="deliveryMan" id="deliveryMan" readonly="readonly" value="${deliveryManList[0].name}" style="width: 300px;"/>
                                    <script type="text/javascript">
                                        $(document).ready(function () {
                                            $("#deliveryCustomerId").val("${deliveryManList[0].id}");
                                            $("#customerId").val("${deliveryManList[0].id}");
                                            $("#customer").val("${deliveryManList[0].name}");
                                            generateGeoSelectList("${deliveryManList[0].id}");
                                            loadProduct("${deliveryManList[0].id}");
                                        });
                                    </script>
                                </g:if>
                                <g:else>
                                    <div id="deliveryManList" style="width: 300px;"></div>
                                    <script type="text/javascript">
                                        $(document).ready(function () {
                                            $("#deliveryManList").empty();
                                            $("#deliveryManList").flexbox(${tentativeDeliveryManList}, {
                                                watermark: "Select Delivery Man",
                                                width: 280,
                                                onSelect: function () {
                                                    $("#deliveryCustomerId").val($('#deliveryManList_hidden').val());
                                                    $("#customerId").val($('#deliveryManList_hidden').val());
                                                    $("#customer").val($('#deliveryManList_input').val());
                                                    generateGeoSelectList($('#deliveryManList_hidden').val());
                                                    loadProduct($('#deliveryManList_hidden').val());
                                                }
                                            });
                                            $('#deliveryManlList_input').addClass("validate[required]");
                                            $('#deliveryManlList_input').blur(function () {
                                                if ($('#deliveryManlList_input').val() == '') {
                                                    $("#deliveryCustomerId").val("");
                                                    $("#customerId").val("");
                                                    $("#customer").val("");
                                                }
                                            });
                                        });
                                    </script>
                                </g:else>
                            </g:if>
                            <g:else>
                                <g:textField name="deliveryMan" readonly="readonly" value=""/>
                                <script type="text/javascript">
                                    $(document).ready(function () {
                                        MessageRenderer.showHeaderMessage("You have no tentative delivery man in your GEO Location.", 0)
                                    });
                                </script>
                            </g:else>
                        </div>

                    </td>
                </g:if>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width105">
                        Order Date
                        <span class="mendatory_field"> * </span>
                    </label>
                    %{--<div class='element-input inputContainer'><g:textField name="dateOrder" id="dateOrder" class="validate[required]" style="width: 100px"--}%
                                                                           %{--value="${DateUtil.getCurrentDateFormatAsString()}" readonly="readonly"/></div>--}%
                    <div class='element-input inputContainer'><g:textField name="dateOrder" id="dateOrder" class="validate[required] width100"
                                                                           value="${DateUtil.getCurrentDateFormatAsString()}"/></div>
                </td>
                <td style="padding-left: 2%">
                    <label class="txtright bold hight1x width105">
                        Delivery Date
                        <span class="mendatory_field"> * </span>
                    </label>

                </td>
                <td>
                    <div class='element-input inputContainer'><g:textField name="dateDeliver" id="dateDeliver" class="validate[required] width100"
                                 value="${DateUtil.getNextDateFormatAsString()}"/></div>


                </td>
            </tr>

        </table>

        <table style="width: 100%;">

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label for="territorySubArea" class="txtright bold hight1x width160">
                        Geo Location
                        <span class="mendatory_field">*</span>
                    </label>
                </td>
                <td>
                    <g:select name="territorySubArea.id"
                              class="validate[required] width165"
                              id="territorySubArea"
                              style="height: 20px;"
                    />
                </td>
            </tr>

            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width105">
                        Product
                    </label>

                </td>
                <td><input type="text" id="searchProductKey" name="searchProductKey" class="width500"/>
                    <input type="hidden" id="productId" name="productId"/>
                    <input type="hidden" id="productCode" />
                    <input type="hidden" id="productName" />
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span></td>
                </tr>

                <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width105">
                        Rate
                    </label>

                </td>
                <td><g:textField class="width150 alin_right" name="rate" id="rate" value="" readonly="readonly"/></td>
                <td>
                    <label class="txtright bold hight1x width105">
                        Quantity
                    </label>
                </td>
                <td><g:textField class="width150" name="quantity" value="" /></td>
                <td>
                    <div class="buttons">
                        <span class="button"><input type="button" name="add-button" id="add-button"
                                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                                    value="Add"
                                                    onclick="addNewItemToCollectionGrid();"/></span>
                    </div>
                </td>
            </tr>

        </table>

        <div class="jqgrid-container">
            <table id="jqgrid-grid"></table>

            <div id="jqgrid-pager"></div>
        </div>
    </div>
    <br/>

    <div class="buttons" style="padding-top: 460px;">
        <span class="button"><input type="button" name="create-button" id="create-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    value="${message(code: 'default.button.create.label', default: 'Create')}"
                                    onclick="executeAjaxSecondaryDemandOrder();"/></span>
        %{--<span class="button"><input type='button' name="delete-button" id="delete-button"--}%
                                    %{--class="ui-button ui-widget ui-state-default ui-corner-all" value='Delete'--}%
                                    %{--onclick="deleteAjaxTerritoryConfiguration();"/></span>--}%
        <span class="button"><input type="button" name="cancel-button" id="cancel-button"
                                    class="ui-button ui-widget ui-state-default ui-corner-all"
                                    onclick="resetForm();" value="Cancel"/></span>
    </div>
</form>

