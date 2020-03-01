<%@ page import="com.docu.commons.DateUtil" %>
<script type="text/javascript">

    var jqGridRetailOrderProductList = null;
    var customerStockIds = "";
    $(document).ready(function(){
        $("#print-button-invoice").hide();
        $('#search-btn-customer-product-id').click(function () {
            var url = "${resource(dir:'processRetailOrder', file:'popupAvailableProductListPanel')}";
            var params = {customerStockIds: customerStockIds.toString()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        });
        jqGridRetailOrderProductList = $("#jqgrid-grid-processRetailOrder").jqGrid({
            url:'${resource(dir:'processRetailOrder', file:'listManualOrderDetails')}?retailOrderId=' + ${retailOrder?.id},
            datatype: "json",
            colNames: [
                'ID',
                'Product ID',
                'Product Code',
                'Product Name',
                'Available Qty.',
                'Order Qty.',
                'Rate',
              //  'Batch No',
                'Shipment Qty.',
                'Amount',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'productId', index: 'productId', width: 100, align: 'left', hidden: true},
                {name: 'productCode', index: 'productCode', width: 100, align: 'center'},
                {name: 'productName', index: 'productName', width: 200, align: 'left'},
                {name: 'availableQty', index: 'availableQty', width: 100, align: 'center', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name: 'orderQty', index: 'orderQty', width: 60, align: 'center', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name: 'rate', index: 'rate', width: 40, align: 'right', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
               // {name: 'batchNo', index: 'batchNo', width: 60, align: 'left'},
                {name: 'processQty', index: 'processQty', width: 60, align: 'center', sorttype:'integer', formatter:"integer", editable: true, editrules:{integer:true}, formatoptions:{thousandsSeparator: ","}},
                {name: 'amount', index: 'orderQty', width: 60, align: 'center', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Retail Order Details",
            autowidth:true,
            height:true,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow:true,
            rownumbers: true,
            altRows: true,
            loadComplete: function (data) {
                customerStockIds = "";
                $.each(data.rows, function (i, item) {
                    if(customerStockIds != ""){
                        customerStockIds += ","
                    }
                    customerStockIds += data.rows[i].id;
                });
                loadProduct(customerStockIds);

                var $grid = $('#jqgrid-grid-processRetailOrder');
                var colSum = $grid.jqGrid('getCol', 'amount', false, 'sum');
                $grid.jqGrid('footerData', 'set', { amount: colSum, productCode:'Total'});

                var processQtycolSum = $grid.jqGrid('getCol', 'processQty', false, 'sum');
                $grid.jqGrid('footerData', 'set', { processQty: processQtycolSum });
            },
            onSelectRow: function (rowid, status) {
            },
            beforeSaveCell: function (rowid, celname, value, iRow, iCol) {

                var rowData = $("#jqgrid-grid-processRetailOrder").jqGrid('getRowData', rowid);
                var avlQty = parseInt(rowData.availableQty);
                var shipmentQty= parseInt(value);

               if (avlQty < shipmentQty ) {
                    MessageRenderer.renderErrorText("Ship Quantity cannot be greater than Available Quantity");
                    rowData.processQty = rowData.availableQty;
                    $('#jqgrid-grid-processRetailOrder').jqGrid('setRowData', rowid, rowData);
                    return rowData.availableQty;
                }
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#jqgrid-grid-processRetailOrder").jqGrid('getRowData', rowid);
                rowData.amount = rowData.processQty * rowData.rate;
                $('#jqgrid-grid-processRetailOrder').jqGrid('setRowData', rowid, rowData);
                calculateSummaryData();
            }
        });
    });
    function calculateSummaryData() {
        var $grid = $('#jqgrid-grid-processRetailOrder');
        var colSum = $grid.jqGrid('getCol', 'amount', false, 'sum');
        $grid.jqGrid('footerData', 'set', { amount: colSum, productCode:'Total'});

        var processQtycolSum = $grid.jqGrid('getCol', 'processQty', false, 'sum');
        $grid.jqGrid('footerData', 'set', { processQty: processQtycolSum});
    }

    function loadProduct(customerStockIdList) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                clearProductItem();

            }
        });
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {query: request.term, customerStockIds: customerStockIdList};
                var url = "${resource(dir:'processRetailOrder', file:'listProductAutoComplete')}";
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['productCode'] + "] " + item['productName'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                setProductData(ui.item.id, ui.item.value, ui.item.productId, ui.item.productName, ui.item.productCode, ui.item.batchNo, ui.item.availableQuantity);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.productCode + "; Name: " + item.productName + "; Batch No: " + item.batchNo + "; Available Qty: " + item.availableQuantity + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };
    }
    function executePreConditionManualProcess() {
        // trim field vales before process.
        var retailOrderId  = $("#retailOrderId").val();
        if(!retailOrderId){
            MessageRenderer.renderErrorText("Retail Order not found");
            return false
        }
        return true;
    }
    function executeAjaxManualProcess() {
        if (!executePreConditionManualProcess()) {
            return false;
        }

        $('#jqgrid-grid-processRetailOrder').jqGrid("editCell", 0, 0, false);

        var data =  $("#gFormProcessRetailOrderManually").serializeArray();
        var gd = $("#jqgrid-grid-processRetailOrder").jqGrid('getRowData');
        var length = gd.length;
        for (var i=0; i < length; ++i) {
            data.push({'name':'items.customerStockTransaction['+i+'].customerStock.id', 'value': gd[i].id});
            data.push({'name':'items.customerStockTransaction['+i+'].unitPrice', 'value': gd[i].rate});
            data.push({'name':'items.customerStockTransaction['+i+'].outQuantity', 'value': gd[i].processQty});
        }
        if(length <= 0){
            MessageRenderer.renderErrorText("No Items Available to Process");
            return false
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/processManually",
            success: function (data, textStatus) {
                executePostConditionManualProcess(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
                    var messageParts = result.messageBody[0].split('=');
                    if (messageParts.length > 1) {
                        $("#invoiceId").val(messageParts[1])
                    }
                    $("#print-button-invoice").show();
                    $("#ship-button-retailOrder").hide();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return true;
    }
    function executePostConditionManualProcess(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
            var messageParts = result.messageBody[0].split('=');
            if (messageParts.length > 1) {
                $("#invoiceId").val(messageParts[1])
            }
            $("#print-button-invoice").show();
            $("#ship-button-retailOrder").hide();
        }
        MessageRenderer.render(result);
    }
    function executeAjaxPrintInvoice() {
        var invoiceId  = $("#invoiceId").val();
        if(!invoiceId){
            MessageRenderer.renderErrorText("No Invoice Available for print");
            return
        }
        window.open("${resource(dir:'processRetailOrder', file:'rptPrintRetailInvoice')}?format=PDF&invoiceIds=" + invoiceId.toString() );
    }
    function addFinishProductToGrid(){
        var productId = $("#productId").val();
        if(!productId){
            MessageRenderer.renderErrorText("ProductItem is not selected");
            return
        }

        var customerStockId = $("#customerStockId").val();
        if(!customerStockId){
            MessageRenderer.renderErrorText("Customer Stock is not Available");
            return
        }

        var totalQuantity = Number($("#quantity").val());
        var availableQuantity = Number($("#availableQuantity").val());
        if(totalQuantity <= 0){
            MessageRenderer.renderErrorText("Shipment Quantity is empty");
            return
        }
        if(totalQuantity > availableQuantity){
            MessageRenderer.renderErrorText("Shipment Quantity is greater than available quantity");
            return
        }
        var productName = $("#productName").val();
        var productCode = $("#productCode").val();
        var processQty = Number($("#processQty").val());
        var unitPrice = Number($("#rate").val());
        var totalAmount = unitPrice*totalQuantity;

        var rowTo = jqGridRetailOrderProductList.getRowData(customerStockId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':customerStockId.toString(), 'productId': productId, 'productCode':productCode.toString(), 'productName':productName.toString(), 'batchNo': $("#batchNo").val(), 'orderQty':totalQuantity.toString(),
                    'availableQty':availableQuantity.toString(), 'processQty':totalQuantity.toString(), 'rate':unitPrice.toString(),
                    'amount':totalAmount.toString(), 'delete': '<a  href="javascript:deleteProduct(' + customerStockId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridRetailOrderProductList.addRowData(customerStockId.toString(), newRowData[0]);
            clearProductItem();
            customerStockIds = $("#jqgrid-grid-processRetailOrder").jqGrid('getDataIDs').toString();
            $("#searchProductKey").html("");
            loadProduct(customerStockIds.toString());
        } else{
            MessageRenderer.renderErrorText("Stock already exist");
        }
    }

    function setProductData(customerStockId, name, productId, productName, productCode, batchNo, availableQuantity){
        loadProductPrice(productId);
        $("#customerStockId").val(customerStockId);
        $("#searchProductKey").val(name);
        $("#productId").val(productId);
        $("#productName").val(productName);
        $("#productCode").val(productCode);
        $("#batchNo").val(batchNo);
        $("#availableQuantity").val(availableQuantity);
        $("#quantity").focus();
    }
    function clearProductItem(){
        $("#customerStockId").val("");
        $("#productId").val("");
        $("#productName").val("");
        $("#productCode").val("");
        $("#batchNo").val("");
        $("#availableQuantity").val("");
        $("#searchProductKey").val("");
        $("#quantity").val("");
        $("#rate").val("");
    }
    function deleteProduct(customerStockId){
        $("#jqgrid-grid-processRetailOrder").jqGrid('delRowData', customerStockId);
        customerStockIds = $("#jqgrid-grid-processRetailOrder").jqGrid('getDataIDs').toString();
        $("#searchProductKey").html("");
        loadProduct(customerStockIds.toString());
    }
    function loadProductPrice(productId){
        if(productId){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {customerId: $("#orderPlacedForId").val(), productId: productId},
                url: "${request.contextPath}/${params.controller}/productPriceByCustomerAndProduct",
                success: function (data, textStatus) {
                    $("#rate").val(data.productPrice);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }
</script>
<form name='gFormProcessRetailOrderManually' id='gFormProcessRetailOrderManually'>
    <g:hiddenField name="id" id="retailOrderId" value="${retailOrder?.id}"/>
    <g:hiddenField name="version" id="version" value="${retailOrder.version}"/>
    <g:hiddenField name="invoiceId" id="invoiceId" value=""/>
    <g:hiddenField name="orderPlacedForId" id="orderPlacedForId" value="${retailOrder?.orderPlacedFor?.id}"/>
    <div class="element_row_content_container lightColorbg pad_bot0">
        <table>
            <tr class="element_row_content_container lightColorbg pad_bot0">
                <td>
                    <label class='txtright bold hight1x width100'>
                        Retail Order No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer '>
                        <g:textField name="orderNoTemp" id="orderNoTemp" maxlength="20" value="${retailOrder?.orderNo}" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width130">
                        Actual Delivery Date:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer'>
                        <g:textField name="actualDeliveryDateTemp" id="actualDeliveryDateTemp" class="width100" readonly="readonly" value="${DateUtil.getCurrentDateFormatAsString()}"/>
                    </div>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Select Product:
                    </label>
                </td>
                <td>
                    <input type="text" id="searchProductKey" name="searchProductKey" class="width400"/>
                    <span id="search-btn-customer-product-id" title="" role="button"
                          class="fld-btn ui-button ui-widget ui-state-default ui-corner-all ui-button-icon-only floatR">
                        <span class="ui-button-icon-primary ui-icon ui-icon-search"></span>
                        <span class="ui-button-text"></span>
                    </span>
                </td>
            </tr>
            <tr class="element_row_content_container inputContainer lightColorbg pad_bot0">
                <td>
                    <label class="txtright bold hight1x width100">
                        Product Name:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width260'>
                        <g:hiddenField name="customerStockId" id="customerStockId" value=""/>
                        <g:hiddenField name="productId" id="productId" value=""/>
                        <g:hiddenField name="productCode" id="productCode" value=""/>
                        <g:textField class="width240" name="productName" id="productName" value="" readonly="readonly"/>
                        <g:hiddenField name="rate" id="rate" value=""/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width70">
                        Batch No:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width100'>
                        <g:textField class="width80" name="batchNo" id="batchNo" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width90">
                        Available Qty:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width80'>
                        <g:textField class="width60" name="availableQuantity" value="" readonly="readonly"/>
                    </div>
                </td>
                <td>
                    <label class="txtright bold hight1x width90">
                        Shipment Qty:
                    </label>
                </td>
                <td>
                    <div class='element_row_container inputContainer width80'>
                        <g:textField class="width60" name="quantity" value=""/>
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
            <table id="jqgrid-grid-processRetailOrder"></table>
        </div>

        <div class="clear"></div>
        <div class="buttons" style="margin-left:10px;">
            <span class="button"><input type='button' name="check-button" id="ship-button-retailOrder"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Ship Order'
                                        onclick="executeAjaxManualProcess();"/>
            </span>
            <span class="button"><input type='button' name="print-button" id="print-button-invoice"
                                        class="ui-button ui-widget ui-state-default ui-corner-all" value='Print Invoice'
                                        onclick="executeAjaxPrintInvoice()"/>
            </span>
        </div>
        <div class="clear"></div>
    </div>
</form>