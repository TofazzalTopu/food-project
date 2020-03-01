<%@ page import="com.docu.commons.CommonConstants" %>
<style>
#gview_customer-order-grid .ui-state-highlight {
    background: limegreen !important;
}
</style>
<script type="text/javascript" language="Javascript">
    var totalAmountInTaka= 0;
    var addProductEnabled = 0;
    var gridInitialized = 0;
    var idBeingEdited = -1;
    var selectedProduct = -1;
    var rowIndex = -1;
    var orderId = -1;
    var detailsTable;
    var mergeIds = [];
    var mergeIdSize = 0;
    var secondaryDemandIds = [];
    var secondaryDemandIdsSize = 0;

    $(document).ready(function () {
        $('#ui-widget-header-text').html('primaryDemandOrder');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormPrimaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormPrimaryDemandOrder").validationEngine('attach');

        var dpId = document.getElementById("distributionPoint").value
        if(dpId){
            fetchCustomer(dpId);
        }

        //resetAll();
        initDatePicker();
        initDateChecker();
        initializeGrid();
        InitOverviewDataTable();

        $(".amount").format({precision: 2, allow_negative: false, autofix: true});

    });

    function initDateChecker() {
        $('#dateExpectedDeliver').change(function () {
            var fromDate = DocuDateUtil.createDateFromString($('#dateProposedDelivery').val());
            var toDate = DocuDateUtil.createDateFromString($(this).val());

            if ($(this).val()) {
                if (fromDate > toDate) {
                    $(this).css('border-color', '#D42525');
                    MessageRenderer.render({
                        "messageBody": "Proposed Delivery  date cannot greater than Expected Deliver date.",
                        "messageTitle": "Create Demand Order",
                        "type": "0"
                    });
                } else {
                    $(this).css('border', '');
                }
            }
        });
    }

    function resetAll() {
        var entid = $('#idEnterprise').val();
        //reset_form("#gFormPrimaryDemandOrder");
        //$('#distributionPoint').val('');
        $("#totalOrderValue").val('');
        $('#finishProduct').val('');
        $('#customerMaster').val('');
        $('#totaleAmount').val('');
        $('#merge-span').attr('hidden', false);
        $('#order-cancel-span').attr('hidden', false);
        $('#cancel-span').attr('hidden', true);

        mergeIds = [];
        mergeIdSize = 0;
        secondaryDemandIds = [];
        secondaryDemandIdsSize = 0;
        orderId = -1;
        idBeingEdited = -1;
        selectedProduct = -1;
        rowIndex = 1;
        if (detailsTable) {
//            $("#customer-order-grid").clearGridData(true).trigger("reloadGrid");
//            $("#primary-order-grid").clearGridData(true).trigger("reloadGrid");
            jQuery("#primary-order-grid").jqGrid("clearGridData");
            %{--jQuery("#customer-order-grid").jqGrid().setGridParam({url: '${resource(dir:'primaryDemandOrder', file:'listCustomerOrders')}?date=' + ''}).trigger("reloadGrid");--}%
            jQuery("#customer-order-grid").jqGrid("clearGridData");
            detailsTable.fnDestroy();
            InitOverviewDataTable();
        }
        if (gridInitialized == 1) {
            $('#primary-demand-div').attr('hidden', true);
            $('#orderCreateDiv').attr('hidden', true);
            addProductEnabled = 0;
        }
        $('#idEnterprise').val(entid);
        $('#enterpriseConfiguration').val(entid);
    }

    function initDatePicker() {
        $("#dateProposedDelivery,#dateExpectedDeliver").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
//                    minDate: 0
                });
        $('#dateExpectedDeliver').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#dateProposedDelivery').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

        %{--$('#orderDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});--}%
//        $('#dateProposedDelivery').datepicker('show');
    }

    function initializeGrid() {
        $("#customer-order-grid").jqGrid({
            %{--url: '${resource(dir:'territorySubArea', file:'list')}',--}%
            datatype: "local",
            colNames: [
                'ID',
                'Customer ID',
                '&#10004;',
                'Customer Name',
                'Order No',
                'Order Date',
                'Delivery Date',
                'Total Value'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'customerId', index: 'id', width: 100, align: 'left', hidden: true},
                {
                    name: 'select', width: 30, align: 'left',
                    formatter: cboxFormatter, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'customerName', index: 'customerName', width: 100, align: 'left'},
                {name: 'orderNo', index: 'orderNo', width: 100, align: 'center'},
                {name: 'orderDate', index: 'orderDate', width: 100, align: 'center'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 100, align: 'center'},
                {
                    name: 'totalValue',
                    index: 'totalValue',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                }

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#customer-order-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Secondary Order List",
            autowidth: false,
            width: 450,
            height: 300,
            scrollOffset: 0,
            footerrow:true,
            loadComplete: function () {
                var colData = $('#customer-order-grid').jqGrid('getDataIDs');
                if (colData != '') {
                    if (secondaryDemandIdsSize > 1) {
                        for (var i = 0; i < colData.length; i++) {
                            for (var j = 0; j < secondaryDemandIdsSize; j++) {
                                if (colData[i] == secondaryDemandIds[j]) {
                                    $('#customer-order-grid').jqGrid("setCell", colData[i], 'select', 'true');
                                }
                            }
                        }
                    } else {
                        for (var i = 0; i < colData.length; i++) {
                            if (colData[i] == secondaryDemandIds) {
                                $('#customer-order-grid').jqGrid("setCell", colData[i], 'select', 'true');
                            }
                        }
                    }
                }

                var $grid = $('#customer-order-grid');
                var colSum = $grid.jqGrid('getCol', 'totalValue', false, 'sum');
                $grid.jqGrid('footerData', 'set', { totalValue: colSum, deliveryDate:'Total  ' });
            },
            onSelectRow: function (rowid, status) {
                loadDataTable(rowid);
                $("#orderRowid").val(rowid);
//                loadDataEligibilitycheck();

            }

        });
        $("#customer-order-grid").jqGrid('navGrid', '#customer-order-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        var $footRow = $('#customer-order-grid').closest(".ui-jqgrid-bdiv").next(".ui-jqgrid-sdiv").find(".footrow");

        var $select = $footRow.find('>td[aria-describedby="customer-order-grid_select"]'),
                $customer_name = $footRow.find('>td[aria-describedby="customer-order-grid_customerName"]'),
                $orderNo = $footRow.find('>td[aria-describedby="customer-order-grid_orderNo"]'),
                $orderDate = $footRow.find('>td[aria-describedby="customer-order-grid_orderDate"]'),
                $deliveryDate = $footRow.find('>td[aria-describedby="customer-order-grid_deliveryDate"]'),
                width4 = $select.width() + $customer_name.outerWidth() + $orderNo.outerWidth() + + $orderDate.outerWidth();
        $select.css("display", "none");
        $customer_name.css("display", "none");
        $orderNo.css("display", "none");
        $orderDate.css("display", "none");
        $deliveryDate.attr("colspan", "4").width(width4);
        $deliveryDate.css("text-align","right");
    }

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){addToMerge(' + options.rowId + ')}else{removeFromMerge(' + options.rowId + ')};"/>';
    }

    function InitOverviewDataTable() {
        detailsTable = $('#detailsTable').dataTable({
            "sPaginationType": "full_numbers",
            "bPaginate": false,
            "bJQueryUI": true, // ThemeRoller-stöd
            "bLengthChange": false,
            "bFilter": false,
            "bSort": true,
            "bInfo": false,
            "bAutoWidth": true,
            "bProcessing": false,
            sScrollY: "320px",
            "bScrollAutoCss": true,
//            "sDom": '<"header">t<"Footer">',
//            "oLanguage": {
//                "sInfo": "asd"
//            },
//            "iDisplayLength":10,
            "sAjaxSource": '${resource(dir:'primaryDemandOrder', file:'listOrderDetails')}?orderId=' + orderId,
            %{--"ajax":'${resource(dir:'primaryDemandOrder', file:'listOrderDetails')}?orderId=' + orderId,--}%
            "aoColumns": [
//                { "mDataProp":"id", "bVisible":false },
                {"mDataProp": "productName"},
                {"mDataProp": "quantity"},
                {"mDataProp": "rate"},
                {"mDataProp": "amount"},
                {"mDataProp": "qtyInLtr"}
            ]

            ,
            "fnRowCallback": function (nRow, aData, iDisplayIndex) {

             //  totalAmountInTaka += aData.amount;
              // alert("j "+JSON.stringify(aData));
              // alert("j "+adata);

            //   $("#totaleAmount").val(totalAmountInTaka);

                // ESCAPE CHARACTER: ' -> &#39;
//                var name = "[" + aData.code + "] " + aData.name.toString();
//                $('td:eq(0)', nRow).html('<a href="javascript:void(0)" onclick="loadDataInField(' + '\'' + aData.id + '\', \'' + name + '\')">' + aData.code + '</a>');
//                return nRow;
            }

        });

      //  $("#totaleAmount").val(totalAmountInTaka);
    }

    function populateCustomerOrderGrid() {
        var date = $('#dateProposedDelivery').val();
        if (!$('#idEnterprise').val()) {
            MessageRenderer.render({
                messageTitle: 'Grid can not be populated',
                type: 2,
                messageBody: 'Select Enterprise.'
            });
            return false;
        }
        if (!date) {
            MessageRenderer.render({
                messageTitle: 'Grid can not be populated',
                type: 2,
                messageBody: 'Select Proposed Delivery Date.'
            });
            return false;
        }
        jQuery("#customer-order-grid").jqGrid().setGridParam({
            url: '${resource(dir:'primaryDemandOrder',
         file:'listCustomerOrders')}?date=' + date + '&entId=' + $('#idEnterprise').val() + '&ids=' + mergeIds,
            datatype: 'json'
        }).trigger("reloadGrid");
        $('#customerName').val('');
        $('#customerOrderNo').val('');
        if (orderId > 0) {
            orderId = -1;
            detailsTable.fnDestroy();
            InitOverviewDataTable();
        }
    }

    function loadDataTable(id) {
        orderId = id;
        var data = jQuery('#customer-order-grid').jqGrid('getRowData', id);
        $('#customerName').val(data.customerName);
        $('#customerOrderNo').val(data.orderNo);
        $('#totalOrderValue').val(data.totalValue);
        $('#totaleAmount').val(data.totalValue);
        detailsTable.fnDestroy();
        InitOverviewDataTable();
        readCustomerBalanceAndShippingAddress(data.customerId);
    }
    function readCustomerBalanceAndShippingAddress(customerId) {
        var options = '<option value=""></option>';
        $("#advanceAmount").val("");
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: "${request.contextPath}/${params.controller}/readCustomerBalanceAndAddress",
            success: function (data, textStatus) {
                $("#advanceAmount").val(data.balance.toFixed(2));
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
    function addToMerge(id) {
        mergeIds[mergeIdSize] = id;
        mergeIdSize++;
        secondaryDemandIds[secondaryDemandIdsSize] = id;
        secondaryDemandIdsSize++;
    }

    function removeFromMerge(id) {
        if (secondaryDemandIdsSize == 2) {
            var x = -1;
            var y = -1;
            for (var i = 0; i < secondaryDemandIdsSize; i++) {
                if (secondaryDemandIds[i] != null && secondaryDemandIds[i] != id) {
                    x = secondaryDemandIds[i];
                    y = mergeIds[i];
                }
            }
            secondaryDemandIdsSize--;
            secondaryDemandIds = [];
            secondaryDemandIds[0] = x;
            mergeIdSize--;
            mergeIds = [];
            mergeIds[0] = y;
        } else {
            for (var i = 0; i < mergeIdSize; i++) {
                if (mergeIds[i] == id) {
                    mergeIds[i] = null;
                    mergeIdSize--;
                    secondaryDemandIds[i] = null;
                    secondaryDemandIdsSize--;
                }
            }
        }
    }

    function mergeSelectedOrders() {
        var fromDate = DocuDateUtil.createDateFromString($('#dateProposedDelivery').val());
        var toDate = DocuDateUtil.createDateFromString($('#dateExpectedDeliver').val());

        if ($('#dateExpectedDeliver').val() == '') {
            MessageRenderer.render({
                messageTitle: 'Can not merge data',
                type: 0,
                messageBody: 'Please enter Expected Deliver date.'
            });
            return false;
        }
        if (fromDate > toDate) {
            MessageRenderer.render({
                messageTitle: 'Can not merge data',
                type: 0,
                messageBody: 'Proposed Delivery  date cannot greater than Expected Deliver date.'
            });
            return false
        }

        if (mergeIdSize <= 1) {
            MessageRenderer.render({
                messageTitle: 'Can not merge data',
                type: 2,
                messageBody: 'Select 2 or more entries.'
            });
//            alert('Merge action requires 2 or more selected entries.');
        } else {
            var name = $('#customer-order-grid').jqGrid("getCell", mergeIds[0], 'customerId');
            for (var i = 1; i < mergeIdSize; i++) {
                var match = $('#customer-order-grid').jqGrid("getCell", mergeIds[i], 'customerId');
                if (name != match) {
                    MessageRenderer.render({
                        messageTitle: 'Can not merge data',
                        type: 2,
                        messageBody: 'Orders need to be made by the same customer.'
                    });
//                    alert("Merge requires orders to be made by the same customer.");
                    return false;
                }
            }
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                url: '${resource(dir:'primaryDemandOrder', file:'mergeOrders')}?ids=' + mergeIds,
                success: function (data, textStatus) {
                    MessageRenderer.render(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    $("#customer-order-grid").trigger("reloadGrid");
                    mergeIds = [];
                    mergeIdSize = 0;
                    secondaryDemandIds = [];
                    secondaryDemandIdsSize = 0;
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }

    function cancelSelectedOrder() {
        var fromDate = DocuDateUtil.createDateFromString($('#dateProposedDelivery').val());
        var toDate = DocuDateUtil.createDateFromString($('#dateExpectedDeliver').val());

        if ($('#dateExpectedDeliver').val() == '') {
            MessageRenderer.render({
                messageTitle: 'Can not cancel order',
                type: 0,
                messageBody: 'Please enter Expected Deliver date.'
            });
            return false;
        }
        if (fromDate > toDate) {
            MessageRenderer.render({
                messageTitle: 'Can not cancel order',
                type: 0,
                messageBody: 'Proposed Delivery  date cannot greater than Expected Deliver date.'
            });
            return false
        }

        if (mergeIdSize <= 0) {
            MessageRenderer.render({
                messageTitle: 'Can not cancel order',
                type: 2,
                messageBody: 'Select a Secondary Order.'
            });
//            alert('Select an order to cancel.');
        } else {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-territoryConfiguration").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Cancel order(s)': function () {
                        $(this).dialog('close');
                        SubmissionLoader.showTo();
                        jQuery.ajax({
                            type: 'post',
                            url: '${resource(dir:'primaryDemandOrder', file:'cancelOrders')}?ids=' + mergeIds,
                            success: function (data, textStatus) {
                                MessageRenderer.render(data);
                                $("#customer-order-grid").trigger("reloadGrid");
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    $("#customer-order-grid").trigger("reloadGrid");
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                }
                                else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function () {
                                mergeIds = [];
                                mergeIdSize = 0;
                                secondaryDemandIds = [];
                                secondaryDemandIdsSize = 0;
                                SubmissionLoader.hideFrom();
                            },
                            dataType: 'json'
                        });
                    },
                    No: function () {
                        $(this).dialog('close');
                    }
                }
            });
        }
    }

    function generateDemandOrder() {
        //condition
        if (!$('#customerMaster').val()) {
            MessageRenderer.render({
                messageTitle: 'Select Distribution Point',
                type: 0,
                messageBody: 'Please select distribution point.'
            });
            return false;
        }
        if (!$('#territorySubArea').val()) {
            MessageRenderer.render({
                messageTitle: 'Critical',
                type: 0,
                messageBody: 'Please set customers geo location first to proceed the order.'
            });
            return false;
        }
        var fromDate = DocuDateUtil.createDateFromString($('#dateProposedDelivery').val());
        var toDate = DocuDateUtil.createDateFromString($('#dateExpectedDeliver').val());

        if ($('#dateExpectedDeliver').val() == '') {
            MessageRenderer.render({
                messageTitle: 'Can not create order list',
                type: 0,
                messageBody: 'Please enter Expected Deliver date.'
            });
            return false;
        }
        if (fromDate > toDate) {
            MessageRenderer.render({
                messageTitle: 'Can not create order list',
                type: 0,
                messageBody: 'Proposed Delivery  date cannot greater than Expected Deliver date.'
            });
            return false
        }

        if (secondaryDemandIdsSize != 0) {
            $('#merge-span').attr('hidden', true);
            $('#order-cancel-span').attr('hidden', true);
            $('#cancel-span').attr('hidden', false);
            $('#primary-demand-div').attr('hidden', false);
            $('#orderCreateDiv').attr('hidden', false);

            if (gridInitialized == 0) {
                $("#primary-order-grid").jqGrid({
                    url: '${resource(dir:'primaryDemandOrder', file:'generatePrimaryOrder')}?ids=' + secondaryDemandIds + '&customerId=' + $('#customerMaster').val() + '&effectiveDeliveryDate=' + $('#dateExpectedDeliver').val() + '&territorySubAreaId=' + $("#territorySubArea").val(),
                    datatype: "json",
                    colNames: [
                        'ID',
                        'finiProductId',
                        'Product Code',
                        'Product Name',
                        'Quantity',
                        'Rate',
                        'Amount',
                        ''
                    ],
                    colModel: [
                        {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                        {name: 'finishProductId', index: 'finishProductId', width: 100, align: 'left', hidden: true},
                        {name: 'productCode', index: 'productCode', width: 150, align: 'center'},
                        {name: 'productName', index: 'productName', width: 250, align: 'left'},
                        {
                            name: 'quantity', index: 'quantity', width: 100, align: 'center',
                            sorttype: 'number',
                            formatter: "number",
                            formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                            editable: true,
                            editrules: {number: true}
                        },
                        {
                            name: 'rate',
                            index: 'rate',
                            width: 100,
                            align: 'right',
                            formatter: "number",
                            formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                        },
                        {
                            name: 'amount',
                            index: 'amount',
                            width: 100,
                            align: 'right',
                            formatter: "number",
                            formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                        },
                        {name: 'delete', index: 'delete', width: 20, align: 'center', sortable: false}
                    ],
                    rowNum: 50,
                    rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
                    pager: '#primary-order-grid-pager',
                    sortname: 'finishProduct',
                    viewrecords: true,
                    sortorder: "desc",
                    caption: "Primary Order List",
                    autowidth: true,
//            width: 450,
                    height: 200,
                    scrollOffset: 0,
                    cellEdit: true,
                    cellsubmit: 'clientArray',
                    cellurl: null,
                    loadComplete: function () {
                        fillValue();
//                        internalEligibilitycheck()
                    },
                    afterSaveCell: function (rowid, name, val, iRow, iCol) {
                        var rowData = $("#primary-order-grid").jqGrid('getRowData', rowid);
                        rowData.amount = rowData.quantity * rowData.rate;
                        $('#primary-order-grid').jqGrid('setRowData', rowid, rowData);
                        fillValue();
                    },
                    onSelectRow: function (rowid, status) {
                        editProductForPrimaryOrder(rowid);

                    },
                    loadError: function (XMLHttpRequest, textStatus, errorThrown) {
                        if(XMLHttpRequest.status = 0){
                            MessageRenderer.renderErrorText("Network Problem: Time out");
                        }else{
                            MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                        }
                    }
                });
                $("#primary-order-grid").jqGrid('navGrid', '#primary-order-grid-pager', {
                    edit: false,
                    add: false,
                    del: false,
                    search: false
                });
                gridInitialized = 1;
            } else {
                jQuery("#primary-order-grid").jqGrid().setGridParam(
                        {url: '${resource(dir:'primaryDemandOrder', file:'generatePrimaryOrder')}?ids=' + secondaryDemandIds + '&customerId=' + $('#customerMaster').val() + '&effectiveDeliveryDate=' + $('#dateExpectedDeliver').val() + '&territorySubAreaId=' + $("#territorySubArea").val(),
                        datatype: 'json'}).trigger("reloadGrid");
            }
        } else {
            MessageRenderer.render({
                messageTitle: 'Can not create order list',
                type: 2,
                messageBody: 'Select at least one Secondary Order.'
            });
//            resetAll();
        }

    }

    function fillValue() {
        var summaryData = $('#primary-order-grid').jqGrid('getCol', 'amount', false, 'sum');
        $('#totalOrderValue').val(summaryData);
        if (summaryData != 0) {
            checkForAdditionalGridItem();
        }

    }

    function editProductForPrimaryOrder(id) {
        idBeingEdited = id;
        var orderNo = $('#primary-order-grid').jqGrid("getCell", idBeingEdited, 'orderNo');
        if (orderNo == '') {
            if (addProductEnabled == 0) {
                showAddProductDiv();
            }
            var name = $('#primary-order-grid').jqGrid("getCell", idBeingEdited, 'customerId');
            var productId = $('#primary-order-grid').jqGrid("getCell", idBeingEdited, 'id');
            var qty = $('#primary-order-grid').jqGrid("getCell", idBeingEdited, 'quantity');
            $('#finishProduct').val(productId);
            $('#customerMaster').val(name);
            $('#quantity').val(qty);
            $('#add-button').val('Update');
            $('#remove-span').attr('hidden', false);
        } else {
            MessageRenderer.render({
                messageTitle: 'Can not alter data',
                type: 2,
                messageBody: 'Secondary Demand Order data can not be changed.'
            });
//            alert('Can not alter secondary demand order data.');
        }
    }

    function showAddProductDiv() {
        $('#enablerButton').attr('hidden', true);
        $('#productAdder').attr('hidden', false);
        document.getElementById('orderCreateDiv').style.paddingTop = "830px";
        addProductEnabled = 1;
    }

    function storeSelectedId() {
        selectedProduct = $("#finishProduct option:selected").val();
    }

    function addNewItemToCollectionGrid() {
        var qty = $('#quantity').val();
        if ($('#customerMaster').val() == 'null') {
            MessageRenderer.render({messageTitle: 'Can not add data', type: 2, messageBody: 'Select Customer.'});
//            alert('Select Customer');
            return false;
        }
        if (qty <= 0) {
            MessageRenderer.render({
                messageTitle: 'Can not add data',
                type: 2,
                messageBody: 'Quantity cannot be zero.'
            });
//            alert('Quantity cannot be zero.');
        } else {
            if ($('#add-button').val() == 'Update') {

                var rate = $('#primary-order-grid').jqGrid("getCell", idBeingEdited, 'rate');
//                var customerName = $("#customerMasterName").val();
//                var customerId = $("#customerMaster").val();
                var productName = $("#product").val();
                var productId = $("#productId").val();
                var amount = qty * rate;
                $('#primary-order-grid').jqGrid("setCell", idBeingEdited, 'id', productId);
                $('#primary-order-grid').jqGrid("setCell", idBeingEdited, 'finishProductId', productId);
                $('#primary-order-grid').jqGrid("setCell", idBeingEdited, 'productName', productName);
                $('#primary-order-grid').jqGrid("setCell", idBeingEdited, 'quantity', qty);
                $('#primary-order-grid').jqGrid("setCell", idBeingEdited, 'amount', amount);
                fillValue();
            } else {
                var data = '&fieldName=finishProduct';
                data = data + '&fieldValue=' + selectedProduct;
                var name = $("#product").val();
                var id = $("#productId").val();
                var proCo = $("#productCode").val();
//                var customerName = $("#customerMasterName").val();
//                var customerId = $("#customerMaster").val();
                var qty = $('#quantity').val();
                var colData = $('#primary-order-grid').jqGrid('getDataIDs');
                for (var i = 0; i < colData.length; i++) {
                    var productTemp = $('#primary-order-grid').jqGrid("getCell", colData[i], 'id');
//                    var customerTemp = $('#primary-order-grid').jqGrid("getCell", colData[i], 'customerId');
                    if (productTemp == id) {
                        var quantityTemp = $('#primary-order-grid').jqGrid("getCell", colData[i], 'quantity');
                        var rateTemp = $('#primary-order-grid').jqGrid("getCell", colData[i], 'rate');
                        quantityTemp = Number(quantityTemp) + Number(qty);
                        $('#primary-order-grid').jqGrid("setCell", colData[i], 'quantity', quantityTemp);
                        $('#primary-order-grid').jqGrid("setCell", colData[i], 'amount', Number(quantityTemp) * Number(rateTemp));
                        fillValue();
                        resetAdderDiv();
                        return false;
                    }
                }
                if ($('#rate').val()) {
                    var myGrid = $("#primary-order-grid");
                    var dataItem = {
                        id: id,
//                        customerId: customerId,
//                        orderNo: null,
                        productCode: proCo,
                        productName: name,
//                        customerName: customerName,
                        quantity: qty,
                        rate: $('#rate').val(),
                        amount: Number($('#rate').val()) * Number(qty),
                        delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                    };
                    myGrid.addRowData(rowIndex, dataItem, "last");
                    rowIndex--;
                } else {
                    MessageRenderer.render({
                        messageTitle: 'Can not add data',
                        type: 2,
                        messageBody: 'Rate for this product has not been set.'
                    });
//                            alert("Rate for this product has not been set yet.");
                }
                fillValue();
                %{--jQuery.ajax({--}%
                %{--type: 'post',--}%
                %{--data: data,--}%
                %{--url: '${resource(dir:'productPrice', file:'search')}',--}%
                %{--success: function (data, textStatus) {--}%
                %{----}%
                %{--},--}%
                %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
                %{--},--}%
                %{--complete: function () {--}%
                %{--},--}%
                %{--dataType: 'json'--}%
                %{--});--}%
            }
            resetAdderDiv();

        }
    }

    function deleteItemFromGrid(id) {
        var myGrid = $("#primary-order-grid");
        myGrid.jqGrid('delRowData', id);
//        resetAdderDiv();
    }

    function checkForAdditionalGridItem() {
        var orderValue = Number($("#totalOrderValue").val());
        var customerRecivable = Number($("#totalReceivableAmount").val());
        var customerSecurityDeposit = Number($("#customerDeposit").val());
        var limit = Number($("#customerLimit").val());
        if (orderValue != 0) {
            if ((orderValue + customerRecivable) - customerSecurityDeposit <= limit) {
                $("#button").text("Y");
            }
            else {
                $("#button").text("N");
            }
        }

    }

    function resetAdderDiv() {
        if (idBeingEdited != -1) {
            ('#searchProductKey').val(null);
            $('#productId').val(null);
            $('#productCode').val(null);
            $('#product').val(null);
            $('#rate').val(null);
            ;
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

    function createPrimaryDemandOrder() {
        var limit = $("#customerLimit").val();
//        if (limit == 0) {
//            MessageRenderer.render({
//                messageTitle: 'Warning',
//                type: 2,
//                messageBody: 'Credit Limit is not assigned.'
//            });
//            return false;
//        }
        if (!$("#gFormPrimaryDemandOrder").validationEngine('validate')) {
            return false;
        }
        if ($("#button").text() == 'N') {
            MessageRenderer.render({
                messageTitle: 'Warning',
                type: 2,
                messageBody: 'Customer is not eligible to proceed.'
            });
            return false;
        }
        var data = jQuery("#gFormPrimaryDemandOrder").serialize();
        var gridCollection = jQuery("#primary-order-grid").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        if (dataLength < 1) {
            MessageRenderer.render({
                messageTitle: 'Can not create order list',
                type: 2,
                messageBody: 'Price may not has been set to desired geo location of this customer.'
            });
            return false;
        }
        var count = 0;
        for (var i = 0; i < dataLength; i++) {
            data = data + '&items.details[' + count + '].id=' + gridCollection[i].id;
            data = data + '&items.details[' + count + '].rate=' + gridCollection[i].rate;
            data = data + '&items.details[' + count + '].quantity=' + gridCollection[i].quantity;
            data = data + '&items.details[' + count + '].amount=' + gridCollection[i].amount;
            count++;
        }
        data = data + '&count=' + count;
        data = data + '&ids=' + secondaryDemandIds;
//        data = data + '&secondaryIdsSize=' + secondaryDemandIdsSize;
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                if (data.type == 1) {
                    resetAll();
                    setTimeout(function(){  $("#button").text(""); }, 50);

                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    resetAll();
                    setTimeout(function(){  $("#button").text(""); }, 50);
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

//
//        return false;

    }

    function setId(id) {
        $('#idEnterprise').val(id);
        jQuery("#customer-order-grid").jqGrid("clearGridData");
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'listEnterpriseInfo')}?id=' + id,
            success: function (data, textStatus) {
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < data.customerList.length; i++) {
                    options += '<option value="' + data.customerList[i].id + '">' + data.customerList[i].name + '</option>';
                }
                $("#customerMaster").html(options);
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < data.productList.length; i++) {
                    options += '<option value="' + data.productList[i].id + '">' + data.productList[i].name + '</option>';
                }
                $("#finishProduct").html(options);
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

    function fetchCustomer(id) {
        if (id == '') {
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
                if (data.length > 0) {
                    $("#customerMasterName").val(data[0].name);
                    $("#dpCusName").text(data[0].name);

                    $("#customerMaster").val(data[0].id);
                    $("#customerMasterCode").val(data[0].code);
                    generateGeoSelectList(data[0].id);
                    loadDataEligibilitycheck();
                } else {
                    MessageRenderer.renderErrorText("No Default Customer Available")
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
                loadProduct($("#customerMaster").val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }
    ;
    function generateGeoSelectList(customerMasterId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'getGeoLocationForCustomer')}?customerMasterId=' + customerMasterId,
            success: function (data, textStatus) {
                $("#territorySubArea").empty();
                $.each(data, function (key, value) {
                    $("#territorySubArea").append('<option value=' + value.id + '>' + value.geoLocation + '</option>');
                });
                if (data.length > 1) {
                    MessageRenderer.render({
                        messageTitle: 'Select Geo Location',
                        type: 2,
                        messageBody: 'Customer assigned to more than one geo location.'
                    });
                    $("#geoTr").show();
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
    ;
    $('#search-btn-customer-product-id').click(function () {
//        CustomerInfo.popupProductListPanel($("#customerId").val());
        var url = '${resource(dir:'secondaryDemandOrder', file:'popupProductListPanel')}';
        var params = {id: $("#customerMaster").val(), territorySubAreaId: $("#territorySubArea").val()};
        DocuAjax.html(url, params, function (html) {
            $.fancybox(html);
        });
    });

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
                    var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?id=' + customerId + "&query=" + $('#searchProductKey').val() + "&territorySubAreaId=" + $("#territorySubArea").val();
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
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + " Product Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    function checkEligibility() {
        var id = $("#orderRowid").val();
        var data = jQuery('#customer-order-grid').jqGrid('getRowData', id);
        var orderValue = $("#totalOrderValue").val();
//        alert(orderValue);
        var customerRecivable = $("#receivableAmount").val();
        var customerSecurityDeposit = $("#customerDeposit").val();
        var limit = $("#customerLimit").val();
        var priority = $("#customerPriority").val();

        var url = '${resource(dir:'primaryDemandOrder', file:'popUpEligibility')}';
        var params = {
            orderValue: orderValue,
            customerRecivable: customerRecivable,
            customerSecurityDeposit: customerSecurityDeposit,
            limit: limit,
            priority: priority,
            text: $("#button").text()
        };
        DocuAjax.html(url, params, function (html) {
            $.fancybox(html);
        });

    }

    function loadDataEligibilitycheck() {
//        var id = $("#orderRowid").val();
        var id = $("#customerMaster").val();
//        var griddata = jQuery('#customer-order-grid').jqGrid('getRowData', id);
//        var datalength = JSON.stringify(griddata);

        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'loadDataEligibilitycheck')}?customerId=' + id,
            success: function (data, textStatus) {
                $("#totalReceivableAmount").val(data[0].receivable);
                $("#customerLimit").val(data[0].customer_credit_limit);
                $("#customerPriority").val(data[0].priority);
                $("#customerDeposit").val(data[0].deposit);
                $("#receivableAmount").val(data[0].receivable);
//                checkForAdditionalGridItem();

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });


    }

</script>