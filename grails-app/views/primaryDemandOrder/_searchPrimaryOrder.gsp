<%@ page import="com.docu.commons.CommonConstants; com.bits.bdfp.util.ApplicationConstants; com.bits.bdfp.inventory.demandorder.PrimaryDemandOrder" %>
<meta name="layout" content="docuThemeRollerLayout"/>
<title>Search Primary Demand Order</title>

<g:javascript src="jquery/watermark/jquery.watermark.js"/>
<script type="text/javascript" language="Javascript">

    var idBeingEdited = -1;
    var rowIndex = -1;
    var selectedProduct = -1;
    var deletedIds = [];
    var deletedIdsSize = 0;
    var baseUrl = '${request.contextPath}/${params.controller}/orderListFrGrid';

    $(document).ready(function () {
        loadOrderNo();
        $("#primary-order-grid").jqGrid({
            url: '${request.contextPath}/${params.controller}/orderListFrGrid',
            datatype: "json",
            colNames: [
                'Sl',
                'Id',
                'Primary Order No.',
                'Ordered By',
                'Order Date',
                'Proposed Delivery Date',
                'Expected Delivery Date',
                'Order Status'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 20, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'order_no', index: 'order_no', width: 80, align: 'center'},
                {name: 'orderedBy', index: 'orderedBy', width: 100, align: 'left'},
                {name: 'date_order', index: 'date_order', width: 70, align: 'center'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 80, align: 'center'},
                {name: 'expectedDeliveryDate', index: 'expectedDeliveryDate', width: 85, align: 'center'},
                {name: 'demand_order_status', index: 'demand_order_status', width: 120, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#primary-order-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Primary Demand Order List",
            autowidth: true,
            height: 150,

            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditProduct(rowid);
            }
        });
        $("#primary-order-grid").jqGrid('navGrid', '#primary-order-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
        resetAll();
        setDateRangeNoLimit('dateFrom','dateTo');
    });

    function initDatePicker() {
        $("#dateProposedDelivery, #orderDate, #dateExpectedDeliver").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#dateProposedDelivery').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#orderDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateExpectedDeliver').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
    }

    function resetAll() {
        $('#popEmpDetails').html('');
        $('#searchOrderKey').val('');
        $('#dateFrom').val('');
        $('#dateTo').val('');
        $("#primary-order-grid").setGridParam({url: baseUrl}).trigger("reloadGrid");
        idBeingEdited = -1;
        rowIndex = -1;
        selectedProduct = -1;
        deletedIds = [];
        deletedIdsSize = 0;
    }

    function executeEditProduct(id) {
        var data = jQuery('#primary-order-grid').jqGrid('getRowData', id);
        $('#id').val(id);
        $('#popEmpDetails').html('');
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'editPrimaryOrder')}?id=' + id,
            success: function (data, textStatus) {
                $('#popEmpDetails').html(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function (data) {
                initGrid();
                initDatePicker();
                $(".amount").format({precision: 2, allow_negative: false, autofix: true});
                %{--$('#dateProposedDelivery').datepicker('setDate', ${dateProposedDelivery});--}%
                SubmissionLoader.hideFrom();
            },
            dataType: 'html'
        });
    }

    function initGrid() {
        $("#primary-order-details-grid").jqGrid({
            url: '${resource(dir:'primaryDemandOrder', file:'listDetailsForEdit')}?id=' + $('#id').val(),
            datatype: "json",
            colNames: [
                'ID',
                'Product ID',
                'Product Code',
                'Product Name',
                'Quantity',
                'Rate',
                'Amount',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'productId', index: 'productId', width: 100, align: 'left', hidden: true},
                {name: 'productCode', index: 'productCode', width: 150, align: 'center'},
                {name: 'productName', index: 'productName', width: 250, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 80, align: 'center',
                    sorttype:'number',
                    formatter:"number",
                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules:{number:true}
                },
                {name: 'rate', index: 'rate', width: 80, align: 'right', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'amount', index: 'amount', width: 100, align: 'right', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#details-pager',
            sortname: 'finishProduct',
            viewrecords: true,
            sortorder: "desc",
            caption: "Primary Order Details",
            autowidth: true,
//            width: 450,
            height: 200,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
                fillValue();
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#primary-order-details-grid").jqGrid('getRowData', rowid);
                rowData.amount = rowData.quantity * rowData.rate;
                $('#primary-order-details-grid').jqGrid('setRowData', rowid, rowData);
                fillValue();
            },
            onSelectRow: function (rowid, status) {
//                editProductForPrimaryOrder(rowid);
            }
        });
        $("#primary-order-details-grid").jqGrid('navGrid', '#details-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    }

    function fillValue() {
        var summaryData = $('#primary-order-details-grid').jqGrid('getCol', 'amount', false, 'sum');
        $('#totalOrderValue').val(summaryData);
    }

    function editProductForPrimaryOrder(id) {
        idBeingEdited = id;
        var name = $('#primary-order-details-grid').jqGrid("getCell", id, 'customerId');
        var productId = $('#primary-order-details-grid').jqGrid("getCell", id, 'productId');
        var qty = $('#primary-order-details-grid').jqGrid("getCell", id, 'quantity');
        $('#finishProduct').val(productId);
        $('#customerMaster').val(name);
        $('#quantity').val(qty);
        $('#add-button').val('Update');
        $('#remove-span').attr('hidden', false);
    }

    function storeSelectedId() {
        selectedProduct = $("#finishProduct option:selected").val();
    }

    function addNewItemToCollectionGrid() {
        var qty = $('#quantity').val();
        if (qty <= 0) {
            alert('Quantity cannot be zero.');
        } else {
            if ($('#add-button').val() == 'Update') {
                var rate = $('#primary-order-details-grid').jqGrid("getCell", idBeingEdited, 'rate');
                var entityId = $('#primary-order-details-grid').jqGrid("getCell", idBeingEdited, 'id');
                var customerName = $("#customerMasterName").val();
                var customerId = $("#customerMaster").val();
                var productName = $("#finishProduct option:selected").text();
                var productId = $("#finishProduct option:selected").val();
                var amount = qty * rate;
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'id', entityId);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'productId', productId);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'customerId', customerId);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'productName', productName);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'customerName', customerName);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'quantity', qty);
                $('#primary-order-details-grid').jqGrid("setCell", idBeingEdited, 'amount', amount);
                fillValue();
            } else {
//                var data = '&customerId=' + $("#customerMaster").val();
//                data = data + '&productId=' + selectedProduct;
                var name = $("#product").val();
                var id = $("#productId").val();
                var productCode = $("#productCode").val();
                var colData = $('#primary-order-details-grid').jqGrid('getDataIDs');
                for(var i = 0; i < colData.length; i++){
                    var productTemp = $('#primary-order-details-grid').jqGrid("getCell", colData[i], 'productId');
                    if(productTemp == id){
                        var quantityTemp = $('#primary-order-details-grid').jqGrid("getCell", colData[i], 'quantity');
                        var rateTemp = $('#primary-order-details-grid').jqGrid("getCell", colData[i], 'rate');
                        quantityTemp = Number(quantityTemp) + Number(qty);
                        $('#primary-order-details-grid').jqGrid("setCell", colData[i], 'quantity', quantityTemp);
                        $('#primary-order-details-grid').jqGrid("setCell", colData[i], 'amount', Number(quantityTemp) * Number(rateTemp));
                        fillValue();
                        resetAdderDiv();
                        return false;
                    }
                }
                %{--jQuery.ajax({--}%
                    %{--type: 'post',--}%
                    %{--data: data,--}%
                    %{--url: '${resource(dir:'primaryDemandOrder', file:'fetchPrice')}',--}%
                    %{--success: function (data, textStatus) {--}%
                        %{----}%
                    %{--},--}%
                    %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                    %{--},--}%
                    %{--complete: function () {--}%
                        %{--fillValue();--}%
                    %{--},--}%
                    %{--dataType: 'json'--}%
                %{--});--}%
                if ($('#rate').val()) {
                    var myGrid = $("#primary-order-details-grid");
                    var dataItem = {
                        id: '',
                        productId: id,
                        productCode: productCode,
                        productName: name,
                        quantity: qty,
                        rate: $('#rate').val(),
                        amount: Number($('#rate').val()) * Number(qty),
                        delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    };
                    myGrid.addRowData(rowIndex, dataItem, "last");
                    rowIndex--;
                    fillValue();
                    resetAdderDiv();
                } else {
                    alert("Rate for this product has not been set yet.");
                }
            }
        }
    }

    function deleteItemFromGrid() {
        var myGrid = $("#primary-order-details-grid");
        var deleteId = myGrid.jqGrid("getCell", idBeingEdited, 'id');
        if (deleteId != '') {
            deletedIds[deletedIdsSize] = deleteId;
            deletedIdsSize++;
        }
        myGrid.jqGrid('delRowData', idBeingEdited);
        resetAdderDiv();
    }

    function resetAdderDiv() {
        if (idBeingEdited != -1) {
            $('#searchProductKey').val(null);
            $('#productId').val(null);
            $('#productCode').val(null);
            $('#product').val(null);
            $('#rate').val(null);
            $('#quantity').val('');
            $('#add-button').val('Add');
            $('#remove-span').attr('hidden', 'hidden');
            idBeingEdited = -1;
        } else {
            $('#searchProductKey').val(null);
            $('#productId').val(null);
            $('#productCode').val(null);
            $('#product').val(null);
            $('#rate').val(null);
            $('#quantity').val('');
            selectedProduct = -1;
        }
    }

    function updatePrimaryDemandOrder() {
        var data = jQuery("#gFormEditPrimaryDemandOrder").serialize();
        var gridCollection = jQuery("#primary-order-details-grid").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        var count = 0;
        for (var i = 0; i < dataLength; i++) {
            data = data + '&items.details[' + count + '].id=' + gridCollection[i].id;
            data = data + '&items.details[' + count + '].productId=' + gridCollection[i].productId;
            data = data + '&items.details[' + count + '].rate=' + gridCollection[i].rate;
            data = data + '&items.details[' + count + '].quantity=' + gridCollection[i].quantity;
            data = data + '&items.details[' + count + '].amount=' + gridCollection[i].amount;
            count++;
        }
        data = data + '&count=' + count;
        data = data + '&deletedIds=' + deletedIds;
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/updatePrimaryOrder",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                if (data.type == 1) {
                    resetAll();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    resetAll();
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
    }

    function deletePrimaryDemandOrder() {
        var colData = $('#primary-order-details-grid').jqGrid('getDataIDs');
        var data = '&id=' + $('#id').val();
        var count = 0;
        for (var i = 0; i < colData.length; i++) {
            data = data + '&items.details[' + count + '].id=' + colData[i];
            count++;
        }
        data = data + '&count=' + count;
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: '${resource(dir:'primaryDemandOrder', file:'deletePrimaryOrder')}',
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                if (data.type == 1) {
                    resetAll();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadOrderNo() {
        jQuery('#searchOrderKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = '${resource(dir:'primaryDemandOrder', file:'listOrderForAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {

                $("#searchOrderKey").val(ui.item.order_no);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Order No: " + item.order_no + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }
    function loadDataByOrder() {
        $("#primary-order-grid").setGridParam({
            url: '${resource(dir:'primaryDemandOrder', file:'orderListFrGrid')}?orderNo=' + $('#searchOrderKey').val()
            + '&dateFrom=' + $('#dateFrom').val() + '&dateTo=' + $('#dateTo').val()
        });
        $("#primary-order-grid").trigger("reloadGrid");

    }

    function editDemandOrder(id) {
        window.open('${createLink(uri: '/')}secondaryDemandOrder/editDemandOrder?id=' + id);
    }

    function fetchCustomer(id){
        if(id == 'null'){
            $("#customerMasterName").val('');
            $("#customerMaster").val('');
            $("#customerMasterCode").val('');
            return false;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'fetchDefaultCustomer')}?id=' + id,
            success: function (data, textStatus) {
                $("#customerMasterName").val(data[0].name);
                $("#customerMaster").val(data[0].id);
                $("#customerMasterCode").val(data[0].code);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                loadProduct($("#customerMaster").val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function loadProduct(customerId) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if (customerId) {
                    var data = {searchKey: request.term};
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?id=' + customerId + "&query=" + $('#searchProductKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                        item['label'] = item['code'] + "-" + item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }

            },
            select: function (event, ui) {
//                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);
                $("#searchProductKey").val(ui.item.value);
                $("#productId").val(ui.item.id);
                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.code);
                $('#product').val(ui.item.name)
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.came + " Price Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

</script>

<div class="main_container">
    <div class="content_container">
        <h1>Search Primary Demand order</h1>

        <h3>Primary Demand order Information</h3>

        <form name='gFormSecondaryDemandOrderSearch' id='gFormSecondaryDemandOrderSearch'>
            <g:hiddenField name="id" value=""/>
            <g:hiddenField name="version" value="0"/>
            <g:hiddenField name="idEnterprise" id="idEnterprise" value=""/>
            <g:hiddenField name="idBusiness" id="idBusiness" value=""/>
            <div id="remote-content-territoryConfiguration"></div>
            <br>

            <div class="element_row_content_container lightColorbg pad_bot0">
                <table>

                    <tr>
                        <script type="text/javascript">
                            jQuery(document).ready(function () {
                                $("#dateFrom, #dateTo").datepicker(
                                        {
                                            dateFormat: 'dd-mm-yy',
                                            changeMonth: true,
                                            changeYear: true
                                        });
                                $('#dateFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                                $('#dateTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
                            });
                        </script>
                        <td>
                            <label class="txtright bold hight1x width120">
                                Primary Order No
                            </label>

                        </td>
                        <td><input type="text" id="searchOrderKey" name="searchOrderKey" class="width120"/>
                            <input type="hidden" id="productId" name="productId" value=""/>
                            <input type="hidden" id="productCode" value=""/>
                            <input type="hidden" id="product" value=""/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                Date From
                            </label>
                        </td>
                        <td>
                            <g:textField name="dateFrom" id="dateFrom" value="" class="width120"
                                         onload="loadDataByOrder();"/>
                        </td>
                        <td>
                            <label class="txtright bold hight1x width60">
                                Date To
                            </label>

                        </td>
                        <td>
                            <g:textField name="dateTo" id="dateTo" value="" class="width120"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div class="buttons">
                                <span class="button"><input type="button" name="show-button" id="show-button"
                                                            class="ui-button ui-widget ui-state-default ui-corner-all"
                                                            value="Search"
                                                            onclick="loadDataByOrder();"/></span>

                            </div>
                        </td>
                    </tr>
                </table>

                <div class="jqgrid-container">
                    <table id="primary-order-grid"></table>

                    <div id="primary-order-grid-pager"></div>
                </div>
            </div>

            <div id="popEmpDetails">
            </div>
        </form>
    </div>
</div>

