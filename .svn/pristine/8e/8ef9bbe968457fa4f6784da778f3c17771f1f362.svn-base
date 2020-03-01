<%--
  Created by IntelliJ IDEA.
  User: mdalinaser.khan
  Date: 9/15/15
  Time: 12:38 PM
--%>

<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants; com.bits.bdfp.inventory.product.ProductPricingType; com.bits.bdfp.inventory.product.ProductPrice" %>
%{--<style>--}%
%{--#jqgh_cb .cbox {--}%
    %{--margin-left: 0 !important;--}%
    %{--margin-right: 3px;--}%
    %{--display: inherit;--}%
%{--}--}%

%{--#jqgrid-grid-geoLocation tr td .cbox {--}%
    %{--margin-left: 0 !important;--}%
    %{--margin-top: 3px;--}%
%{--}--}%

%{--#jqgrid-grid-customer tr td .cbox {--}%
    %{--margin-left: 0 !important;--}%
    %{--margin-top: 3px;--}%
%{--}--}%
%{--</style>--}%
<script type="text/javascript">
    var jqGridCustomerList = null;
    $(document).ready(function(){
        var from = $("#dateEffectiveFrom").val().split("-");
        $("#dateEffectiveTo").datepicker( {
            dateFormat:'${ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            changeMonth:true,
            changeYear:true,
            minDate: new Date(from[2], from[1] - 1, from[0]) ,
            inline: true
        });
        $("#div_customer_selection").hide();
        $("#div_product_territory").hide();
        $("#dateEffectiveTo").mask("${CommonConstants.DATE_MASK_FORMAT}");
        if(${commonData.pricingTypeId} == ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}) {
            $("#div_product_territory").show();
            $("#jqgrid-grid-geoLocation").jqGrid({
                url:'',
                datatype: "local",
                //            mtype: "POST",
                colNames:[
                    'ID',
                    'Geographic Location',
                    'Country',
                    'Division',
                    'District',
                    'Thana',
                    'Union',
                    'Para/ Locality',
                    'Road'
                ],
                colModel:[
                    {name:'id', index:'id', width:0, hidden:true},
                    {name:'geographicLocation', index:'saleItemId', width:100, align:'left'},
                    {name:'country', index:'country', width:100, align:'left'},
                    {name:'division', index:'division', width:120, align:'left'},
                    {name:'district', index:'district', width:120, align:'left'},
                    {name:'thana', index:'thana', width:150, align:'left'},
                    {name:'union', index:'union', width:100, align:'left'},
                    {name:'paraOrLocality', index:'paraOrLocality',width:100, align:'left'},
                    {name:'road', index:'road', width:100, align:'left'}
                ],
                rowNum: -1,
                //            pager: '#jqgrid-pager-geoLocation',
                viewrecords:true,
                caption:"Assign Geographic Location",
                autowidth:true,
                height:true,
                scrollOffset:0,
                gridview:true,
                multiselect: true,
                rownumbers: true,
                //            footerrow : true,
                loadComplete: function() {
                    var i, count, $grid = $("#jqgrid-grid-geoLocation");
                    var rowArrayData = "${commonData.geoLocationIds}";
                    var rowArray = rowArrayData.split(',');
                    for (i = 0, count = rowArray.length; i < count; i++) {
                        $grid.jqGrid('setSelection', rowArray[i], false);
                    }
                },
                onSelectRow: function(rowid, status) {
                    //                executeEditSaleItem();
                },
                loadError: function (jqXHR, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(jqXHR.responseText,'');
                }
            });
            $("#cb_jqgrid-grid-geoLocation").hide();
            var territoryList = ${territoryList};
            var options = '<option value="">Select Territory</option>';
            for (var i = 0; i < territoryList.length; i++) {
                options += '<option value="' + territoryList[i].id + '">' + territoryList[i].name + '</option>';
            }
            $("#territoryConfiguration").html(options);
            $("#territoryConfiguration").val(${commonData.territoryId}).attr('selected', 'selected');
            $("#territoryConfiguration").change();
        }
        else if(${commonData.pricingTypeId} == ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}) {
            $("#div_customer_selection").show();
            jqGridCustomerList = $("#jqgrid-grid-customer").jqGrid({
                url:'',
                datatype: "local",
                colNames:[
                    'ID',
                    'Customer Number',
                    'Customer Name',
                    'Customer Address'
                ],
                colModel:[
                    {name:'id',index:'id', width:0, hidden:true},
                    {name:'customerNumber', index:'saleItemId',width:200, align:'left'},
                    {name:'customerName', index:'customerName',width:250, align:'left'},
                    {name:'customerAddress', index:'customerAddress',width:300, align:'left'}
                ],
                rowNum: -1,
                //            pager: '#jqgrid-pager-customer',
                viewrecords:true,
                caption:"Select Customer List",
                autowidth:true,
                height:true,
                scrollOffset:0,
                gridview:true,
                rownumbers: true,
                multiselect: true,
                gridComplete: function() {
                },
                loadComplete: function() {
                    var i, count, $grid = $("#jqgrid-grid-customer");
                    var rowArrayData = "${commonData.customerIds}";
                    var rowArray = rowArrayData.split(',');
                    for (i = 0, count = rowArray.length; i < count; i++) {
                        $grid.jqGrid('setSelection', rowArray[i], false);
                    }
                },
                onSelectRow: function(rowid, status) {
                },
                loadError: function (jqXHR, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(jqXHR.responseText,'');
                }
            });
            loadCustomerList();
            loadCustomer(${commonData.enterpriseId});
            $("#cb_jqgrid-grid-customer").hide();
        }
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
    });
    function loadGeographicLocationList(territoryId){
        if(territoryId) {
            $("#jqgrid-grid-geoLocation").jqGrid().setGridParam({url:"${resource(dir:'territorySubArea', file:'listTerritorySubAreaByTerritory')}?"
                    + "territoryId=" + territoryId,
                mtype: "POST",
                page:1,
                datatype:"json"
            }).trigger("reloadGrid")
        }else{
            $("#jqgrid-grid-geoLocation").jqGrid('clearGridData');
        }
    }
    function loadCustomerList(){
        $("#jqgrid-grid-customer").jqGrid().setGridParam({url:"${resource(dir:'productPrice', file:'listCustomer')}?"
                + "priceName=" + '${commonData.priceName}' + "&priceTypeId=" + ${commonData.pricingTypeId},
            mtype: "POST",
            page:1,
            datatype:"json"
        }).trigger("reloadGrid")
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function(customerCoreInfoId){
            var url = '${resource(dir:'productPrice', file:'popupCustomerListPanel')}' ;
            var params = {id:customerCoreInfoId};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function loadCustomer(enterpriseId) {
        jQuery('#searchKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {enterpriseId: enterpriseId, query: request.term};
                var url = '${resource(dir:'productPrice', file:'customerAutoCompleteByEnterPrise')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                $("#customerId").val(ui.item.id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.present_address);
//                $('#name').val(ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " + item.code + "; Legacy ID: " + item.legacy_id + "; Name: " + item.name + "; Location: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };

        $('#search-btn-customer-register-id').click(function(){
            CustomerInfo.popupCustomerListPanel(enterpriseId);
        });
    }

    function addCustomerToGrid(){
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }

        var customerName = $("#customerName").val();
        var customerNumber = $("#customerNumber").val();
        var customerAddress = $("#customerAddress").val();

        var rowTo = jqGridCustomerList.getRowData(customerId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':customerId.toString(), 'customerNumber':customerNumber.toString(), 'customerName':customerName.toString(), 'customerAddress':customerAddress.toString()}
            ];
            jqGridCustomerList.addRowData(customerId.toString(), newRowData);
            $("#jqgrid-grid-customer [id*='undefined']").attr('id', customerId.toString());
            $("#searchKey").val("");
            $("#customerId").val("");
            $("#customerName").val("");
            $("#customerNumber").val("");
            $("#customerAddress").val("");
        }else{
            MessageRenderer.renderErrorText("Customer Already Selected");
        }
    }
    function executePreConditionProductPrice() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormProductPrice").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxProductPrice() {
        if(!executePreConditionProductPrice()) {
            return false;
        }
        var data =  $("#gFormProductPrice").serializeArray();

        var tempId = "";
        var isProductPriceSelected = false;
        $(".baseData").each(function () {
            if(this.value){
                data.push({'name': this.id, 'value': this.value});
                var idValues = this.id.split("_");
                if(idValues.length > 2){
                    // Set Vat Percent
                    tempId = 'vatPercent_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});
                    // Set Vat Amount
                    tempId = 'vatAmount_' + idValues[1] + "_" + idValues[2];
                    data.push({'name':tempId, 'value': Number($("#" + tempId).val())});
                    isProductPriceSelected = true
                }
            }
        });
        if(!isProductPriceSelected){
            MessageRenderer.renderErrorText("Product Price value is not given");
            return false
        }

        var productPricingTypeId = $("#productPricingType").val();
        if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_STANDARD_ID}) {
            var selRowIds = $("#jqgrid-grid-geoLocation").getGridParam("selarrrow");
//            var selRowIds = $('#jqgrid-grid-geoLocation').jqGrid('getGridParam', 'selarrrow');
            if(selRowIds.length <=  0){
                MessageRenderer.renderErrorText("Please Select Geo Location");
                return false
            }
            data.push({'name': "geoLocationIds", 'value': selRowIds.toString()});
        } else if(productPricingTypeId == ${ApplicationConstants.PRODUCT_PRICING_TYPE_NEGOTIATED_ID}) {
            var customerIds = $('#jqgrid-grid-customer').jqGrid('getGridParam', 'selarrrow');
            if(customerIds.length <=  0){
                MessageRenderer.renderErrorText("Please Select Customer");
                return false
            }
            data.push({'name': "customerIds", 'value': customerIds.toString()});
        }
        $.fancybox.close();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/update",
            success:function(data, textStatus) {
                executePostConditionProductPrice(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-priceList").trigger("reloadGrid");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
                SubmissionLoader.hideFrom()
            },
            dataType:'json'
        });
        return false;
    }
    function executePostConditionProductPrice(result) {
        if (result.type == 1) {
//            $.fancybox.close();
            $("#jqgrid-grid-priceList").trigger("reloadGrid");
        }
        MessageRenderer.render(result);
    }
    function selectAllGeoLocation(){
        var i, count, $grid = $("#jqgrid-grid-geoLocation");
        $grid.jqGrid('resetSelection');
        var ids = $grid.getDataIDs();
        for (i =0 , il = ids.length; i < il; i++) {
            $grid.jqGrid('setSelection',ids[i], false);
        }
    }
    function clearAllGeoLocation(){
        $("#jqgrid-grid-geoLocation").jqGrid('resetSelection');
    }
    function selectAllCustomer(){
        var i, count, $grid = $("#jqgrid-grid-customer");
        $grid.jqGrid('resetSelection');
        var ids = $grid.getDataIDs();
        for (i =0 , il = ids.length; i < il; i++) {
            $grid.jqGrid('setSelection',ids[i], false);
        }
    }
    function clearAllCustomer(){
        $("#jqgrid-grid-customer").jqGrid('resetSelection');
    }
</script>
<div class="main_container width1000">
    <div class="content_container">
<form name='gFormProductPrice' id='gFormProductPrice'>
    <g:hiddenField name="enterpriseConfiguration" id="enterpriseConfiguration" value="${commonData.enterpriseId}"/>
    <g:hiddenField name="productPriceId" id="productPriceId" value="${commonData.id}"/>
    <g:hiddenField name="businessUnitConfiguration" id="businessUnitConfiguration" value="${commonData.businessUnitId}"/>
    <g:hiddenField name="productPricingType" id="productPricingType" value="${commonData.pricingTypeId}"/>
    <div id="remote-content-productPrice"></div>
    <div class="element_row_content_container lightColorbg pad_bot0">
    %{--<fieldset  class="ui-state-default ui-corner-all">--}%
        %{--<legend class="ui-jqgrid-titlebar ui-widget-header  ui-helper-clearfix">Product Basic Information</legend>--}%
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width110">
                        Price List Type:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width130" name="pricingName" id="pricingName" value="${commonData.pricingType}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Enterprise Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width130" name="enterprise" id="enterprise" value="${commonData.enterprise}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width110">
                        Business Unit:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width130" name="businessUnit" id="businessUnit" value="${commonData.businessUnit}" readonly="readonly"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width110">
                        Price List Name:
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width130" name="name" value="${commonData.priceName}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Effective Date: From
                    </label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:textField class="width130" name="dateEffectiveFrom" value="${commonData.dateEffectiveFrom}" readonly="readonly"/>
                    </div>
                </td>
                <td colspan="2">
                    <div class='element-input inputContainer'>
                        <div style="float: left">To: &nbsp;&nbsp;</div><div style="float: left"><g:textField class="width80" name="dateEffectiveTo" value="${commonData.dateEffectiveTo}" readonly="readonly"/></div>
                    </div>
                </td>
            </tr>
        </table>
    %{--</fieldset>--}%
    </div>
</form>
<br/>
<fieldset  class="ui-state-default ui-corner-all">
    <g:render template="/product/productPrice/updatePriceSetup"/>
</fieldset>
<br/>
<div id="div_product_territory">
    <fieldset  class="ui-state-default ui-corner-all">
        <table>
            <tr>
                <td colspan="2">
                    <label for='territoryConfiguration' class='customLabel'>Enter the Following Information:</label>
                </td>
            </tr>
            <tr>
                <td>
                    <label for='territoryConfiguration' class='customLabel'>Select Territory:</label>
                </td>
                <td>
                    <div class='element-input inputContainer'>
                        <g:select name="territoryConfiguration" id="territoryConfiguration"
                                  from="" value="" onchange="loadGeographicLocationList(this.value)"/>
                    </div>
                </td>
            </tr>
        </table>
        <div class="jqgrid-container">
            <table id="jqgrid-grid-geoLocation"></table>
            <div id="jqgrid-pager-geoLocation"></div>
        </div>
    </fieldset>
</div>
<div id="div_customer_selection">
    %{--<fieldset class="ui-state-default ui-corner-all">--}%
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        Customer
                        <span class="mendatory_field"> * </span>
                    </label>
                </td>
                <td colspan="3">
                    <input type="text" id="searchKey" name="searchKey" class="width400 validate[required] "/>
                    <input type="hidden" id="customerId" name="customerId"/>
                    %{--<span id="search-btn-customer-register-id" title="" role="button"--}%
                          %{--class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">--}%
                        %{--<span class="ui-button-icon-primary ui-icon ui-icon-search"></span>--}%
                        %{--<span class="ui-button-text"></span>--}%
                    %{--</span>--}%
                </td>
            </tr>
            <tr>
                <td>
                    <label class="txtright bold hight1x width120">
                        Customer Number:
                    </label>
                </td>
                <td>
                    %{--<div class='element-input inputContainer'>--}%
                        <g:textField class="validate[required]" name="customerNumber" value="" disabled="disabled"/>
                    %{--</div>--}%
                </td>
                <td>
                    <label class="txtright bold hight1x width100">
                        Customer Name:
                    </label>
                </td>
                <td>
                    %{--<div class='element-input inputContainer'>--}%
                        <g:textField class="validate[required] width200" name="customerName" value="" disabled="disabled"/>
                    %{--</div>--}%
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Customer Address:
                    </label>
                </td>
                <td>
                    %{--<div class='element-input inputContainer'>--}%
                        <g:textField class="validate[required] width350" name="customerAddress" value="" disabled="disabled"/>
                    %{--</div>--}%
                </td>
            </tr>
            <tr>
                <td colspan="6">
                    <span class="button"><input type="button" name="addCustomer-button" id="addCustomer-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Add Customer" onclick="addCustomerToGrid();"/></span>
                </td>
            </tr>
        </table>
        <div class="jqgrid-container">
            <table id="jqgrid-grid-customer"></table>
        </div>
    %{--</fieldset>--}%
    </div>
</div>
<br/>
<div class="buttons">
    <span class="button"><input type="button" name="selectAllGeo-button" id="selectAllGeo-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All" onclick="selectAllGeoLocation();"/></span>
    <span class="button"><input type="button" name="clearAllGeo-button" id="clearAllGeo-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Clear All" onclick="clearAllGeoLocation();"/></span>
    <span class="button"><input type="button" name="selectAllCustomer-button" id="selectAllCustomer-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Select All" onclick="selectAllCustomer();"/></span>
    <span class="button"><input type="button" name="clearAllCustomer-button" id="clearAllCustomer-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Clear All" onclick="clearAllCustomer();"/></span>
    <span class="button"><input type="button" name="update-button" id="update-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Update" onclick="executeAjaxProductPrice();"/></span>
</div>
</div>
</div>

