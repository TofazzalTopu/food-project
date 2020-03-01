<%@ page import="com.docu.commons.CommonConstants" %>
<style>
#gview_jqgrid-grid-retailOrder .ui-state-highlight { background: limegreen !important; }
</style>
<script type="text/javascript">
    var jqGridRetailOrderList = null;
    var jqGridProductList = null;
    var jqGridSecondaryProductList = null;
    var selectedRetailOrderIds = "";
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#deliveryDateSearch,#dateOrder").datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
//            minDate: +1
        });

        $("#gSecondaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gSecondaryDemandOrder").validationEngine('attach');

        $('#deliveryDateSearch,#dateOrder').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        showHideSecondaryActionButton(false);
        jqGridRetailOrderList = $("#jqgrid-grid-retailOrder").jqGrid({
            datatype: "local",
            colNames: [
                'SL',
                'ID',
                'Legacy ID',
                'Customer ID',
                'Customer Name',
                'Order No',
                'Order Date',
                'Expected Delivery Date',
                'Quantity',
                'Amount'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 20, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'legacyId', index: 'legacyId', width: 80, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 80, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 150, align: 'left'},
                {name: 'orderNo', index: 'orderNo', width: 70, align: 'center'},
                {name: 'orderDate', index: 'orderDate', width: 60, align: 'center'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 60, align: 'center'},
                {name:'quantity', index:'quantity', width:80, align:'center', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'amount', index:'amount', width:80, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Retail Order List",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
//            altRows: true,
//            rownumbers: true,
            multiselect: true,
            loadComplete: function () {
            },
            gridComplete: function() {
                $("#jqgrid-grid-retailOrder_cb").css("width","40px");
                $("#jqgrid-grid-retailOrder tbody tr").children().first("td").css("width","40px");
            },
            onSelectRow: function (rowid, status) {
                executeShowRetailOrderDetails();
            },
            onSelectAll: function(aRowids, status) {
                executeShowRetailOrderDetails();
            }
        });

        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Product Code',
                'Product Name',
                'Quantity',
                'Quantity In Liter For ML',
                'Rate',
                'Amount'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'productCode', index:'productCode', width:30, align:'center'},
                {name:'productName', index:'productName', width:50, align:'left'},
                {name:'quantity', index:'quantity', width:10, align:'right', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'qtyInLtr', index:'qtyInLtr', width:20, align:'right', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'rate', index:'rate', width:10, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 1}},
                {name:'amount', index:'amount', width:20, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Order Details List",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            rownumbers: true,
            footerrow : true,
//            altRows: true,
            gridComplete: function() {
                calculateSummaryData();
            },
            onSelectRow: function(rowid, status) {
//                executeEditSaleItem();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
        jqGridSecondaryProductList = $("#jqgrid-grid-secondaryOrderDetails").jqGrid({
            datatype: "local",
            colNames: [
                'ID',
                'Product Code',
                'Product Name',
                'Quantity',
                 'Quantity in Liter',
                'Rate',
                'Amount',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 100, align: 'left', hidden: true},
                {name: 'productCode', index: 'productCode', width: 150, align: 'center'},
                {name: 'productName', index: 'productName', width: 250, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 100, align: 'center',
                    sorttype:'number',
                    formatter:"number",
                    formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules:{number:true}
                },
                {name:'qtyInLtr', index:'qtyInLtr', width:60, align:'right', sorttype:'number', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'rate', index: 'rate', width: 60, align: 'right', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'amount', index: 'amount', width: 60, align: 'right',formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'delete', index:'delete', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Secondary Order Details",
            autowidth:true,
            height:true,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            rownumbers: true,
            altRows: true,
            footerrow : true,
            loadComplete: function (data) {
                calculateSecondaryDetailsSummaryData();
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-secondaryOrderDetails").jqGrid('getRowData', data.rows[i].id);
                    rowData.delete = '<a  href="javascript:deleteSecondaryProduct(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                    $('#jqgrid-grid-secondaryOrderDetails').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#jqgrid-grid-secondaryOrderDetails").jqGrid('getRowData', rowid);
                rowData.amount = rowData.quantity * rowData.rate;
                $('#jqgrid-grid-secondaryOrderDetails').jqGrid('setRowData', rowid, rowData);
                calculateSecondaryDetailsSummaryData();
            },
            onSelectRow: function (rowid, status) {
//                    editProductForPrimaryOrder(rowid);
            }
        });
        loadProduct(${customerMaster?.id});
    });
    function calculateSummaryData() {
        var $grid = $('#jqgrid-grid-finishProduct');
        var sumAmount = $grid.jqGrid('getCol', 'amount', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'amount': sumAmount });
        $grid.jqGrid('footerData', 'set', { 'productCode': 'Total'});

       // var $grid = $('#jqgrid-grid-finishProduct');
        var totalInLtr = $grid.jqGrid('getCol', 'qtyInLtr', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'qtyInLtr': totalInLtr.toFixed(2)});
       //$grid.jqGrid('footerData', 'set', { 'productCode': 'Total'});
    }
    function deleteSecondaryProduct(productId){
        $('#jqgrid-grid-secondaryOrderDetails').jqGrid('delRowData', productId);
        calculateSecondaryDetailsSummaryData();
    }
    function executeRetailOrderSearch() {
        // Get Searched data
        var deliveryDate = $('#deliveryDateSearch').val();
        if(!deliveryDate){
            MessageRenderer.renderErrorText("Select Delivery Date");
            return
        }
        var includePendingOrder = "true";
        if (!$('#includePendingOrder').is(":checked")) {
            includePendingOrder = "";
        }
        $("#jqgrid-grid-retailOrder").jqGrid().setGridParam({url: "${resource(dir:'consolidateRetailOrder', file:'listOrderForConsolidate')}?"
                + 'includePendingOrder=' + includePendingOrder + '&deliveryDate=' + deliveryDate,
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }
    function clearAllGridData(){
        $("#jqgrid-grid-retailOrder").jqGrid('clearGridData');
        $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
    }
    function executeShowRetailOrderDetails() {
        // Get Searched data
        var selectedRetailOrderIds = jQuery("#jqgrid-grid-retailOrder").jqGrid('getGridParam','selarrrow');
        if(selectedRetailOrderIds.toString() != ""){
            $("#jqgrid-grid-finishProduct").jqGrid().setGridParam({url: "${resource(dir:'consolidateRetailOrder', file:'listOrderDetailsForConsolidate')}?"
                    + 'retailOrderIds=' + selectedRetailOrderIds.toString(),
                datatype: "json"
            }).trigger("reloadGrid", [
                        {page: 1}
                    ]);
        }else{
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        }
        $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
    }
    function changeGeolocation(geoLocationId){
        $("#territorySubAreaId").val(geoLocationId);
        $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
    }
    function generateSecondaryDetailsFromRetailOrder() {
        if (!$("#gSecondaryDemandOrder").validationEngine('validate')) {
            return false;
        }

        if(!$("#customerId").val()) {
            MessageRenderer.renderErrorText('Customer is not available.','Message');
            return false;
        }
        if(!$('#territorySubAreaId').val()){
            MessageRenderer.renderErrorText('Please set customers geo location first to proceed the order.', 'Message');
            return false;
        }
        var deliveryDate = $("#deliveryDateSearch").val();
        if(!deliveryDate){
            MessageRenderer.renderErrorText('Please Select Delivery Date.', 'Message');
            return false;
        }
        selectedRetailOrderIds = jQuery("#jqgrid-grid-retailOrder").jqGrid('getGridParam','selarrrow');
        if(selectedRetailOrderIds.toString() != ""){
            $("#jqgrid-grid-secondaryOrderDetails").jqGrid().setGridParam({url: "${resource(dir:'consolidateRetailOrder', file:'listOrderDetailsForConsolidate')}?"
                    + 'retailOrderIds=' + selectedRetailOrderIds.toString() + '&customerId=' + $('#customerId').val() + '&territorySubAreaId=' + $("#territorySubAreaId").val(),
                datatype: "json"
            }).trigger("reloadGrid", [
                        {page: 1}
                    ]);
            $("#dateDeliver").val(deliveryDate);
            showHideSecondaryActionButton(true);
        }else{
            MessageRenderer.renderErrorText('Retail Order(s) are not selected','Message');
            return false;
        }
        return true
    }

    function calculateSecondaryDetailsSummaryData() {
        var $grid = $('#jqgrid-grid-secondaryOrderDetails');
        var sumAmount = $grid.jqGrid('getCol', 'amount', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'amount': sumAmount });
        $grid.jqGrid('footerData', 'set', { 'productCode': 'Total'});
    }
    function loadProduct(customerId) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
        var territorySubAreaId = $("#territorySubAreaId").val();
        if(!territorySubAreaId){
            territorySubAreaId = $("#territorySubArea").val();
        }
        jQuery('#searchProductKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                var data = {searchKey:request.term};
                var url = '${resource(dir:'retailOrder', file:'listProduct')}?customerId=' + customerId + "&territorySubAreaId=" + territorySubAreaId + "&query=" + $('#searchProductKey').val();
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['productCode'] + "] " + item['productName'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
              //  alert(ui.item.code)
                $("#searchProductKey").val(ui.item.value);
                $("#productId").val(ui.item.productId);
                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.productCode);
                $('#productName').val(ui.item.productName);
                $('#quantity').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            //alert(item.code)
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " + item.productCode + ", Name: " + item.productName + " Price Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    $('#search-btn-customer-product-id').click(function(){
        var url = '${resource(dir:'retailOrder', file:'popupProductListPanel')}' ;
        var params = {customerId: $("#customerId").val(), territorySubAreaId:$("#territorySubArea").val()};
        DocuAjax.html(url, params, function(html){
            $.fancybox(html);
        });
    });
    function addFinishProductToGrid(){
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }
        var productId = $("#productId").val();
        if(!productId){
            MessageRenderer.renderErrorText("Product Item is not selected");
            return
        }
        var totalQuantity = Number($("#quantity").val());
        if(totalQuantity <= 0){
            MessageRenderer.renderErrorText("Quantity is empty");
            return
        }
        var productName = $("#productName").val();
        var productCode = $("#productCode").val();
        var unitPrice = Number($("#rate").val());
        var qtyInLtr = Number($("#qtyInLtr").val());
        var totalQtyInLtr = qtyInLtr*totalQuantity
        var amount = unitPrice * totalQuantity;

        var rowTo = jqGridSecondaryProductList.getRowData(productId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':productId.toString(), 'productCode':productCode.toString(), 'productName':productName.toString(),
                    'quantity':totalQuantity.toString(), 'qtyInLtr':totalQtyInLtr.toString(), 'rate':unitPrice.toString(), 'amount':amount.toString(), 'delete': '<a  href="javascript:deleteSecondaryProduct(' + productId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'}
            ];
            jqGridSecondaryProductList.addRowData(productId.toString(), newRowData[0]);
        }else{
            rowTo.quantity = Number(rowTo.quantity) + totalQuantity;
            rowTo.amount = Number(rowTo.amount) + amount;
            $('#jqgrid-grid-secondaryOrderDetails').jqGrid('setRowData', productId, rowTo);
            MessageRenderer.renderErrorText("Product added on selected item");
        }
        calculateSecondaryDetailsSummaryData();
        clearProductItem();
    }

    function clearProductItem(){
        $("#productId").val('');
        $("#productName").val('');
        $("#productCode").val('');
        $("#rate").val('');
        $("#quantity").val('');
        $('#searchProductKey').val('');
    }

    function executePreConditionSecondaryDemandOrder() {
        // trim field vales before process.
        trim_form();
        if (!$("#gSecondaryDemandOrder").validationEngine('validate')) {
            return false;
        }
        $('#jqgrid-grid-secondaryOrderDetails').jqGrid("editCell", 0, 0, false);
        return true;
    }

    function executeAjaxSecondaryDemandOrder(isSubmitted) {
        if(!executePreConditionSecondaryDemandOrder()) {
            return false;
        }
        var data =  $("#gSecondaryDemandOrder").serializeArray();
        $('#jqgrid-grid-secondaryOrderDetails').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-secondaryOrderDetails").jqGrid('getRowData');
        var length = gd.length;
        if(length <= 0){
            MessageRenderer.renderErrorText("No Details Item available", "Message");
            return false
        }
        for (var i=0; i < length; ++i) {
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].finishProduct.id', 'value': gd[i].id});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].rate', 'value': gd[i].rate});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].quantity', 'value': gd[i].quantity});
        }

        if(isSubmitted){
            data.push({'name':'isSubmitted', 'value': 'true'});
        }
        data.push({'name':'retailOrderIds', 'value': selectedRetailOrderIds.toString()});
        SubmissionLoader.showTo();
        jQuery.ajax({
            type:'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/createSecondaryOrder",
            success:function(data, textStatus) {
                executePostConditionSecondaryDemandOrder(data);
            },
            error:function(XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
                    $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
                    showHideSecondaryActionButton(false);
                    selectedRetailOrderIds = "";

                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete:function(){
                SubmissionLoader.hideFrom();
            },
            dataType:'json'
        });
        return true;
    }
    function executePostConditionSecondaryDemandOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
            $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
            showHideSecondaryActionButton(false);
            selectedRetailOrderIds = "";
        }
        MessageRenderer.render(result);
    }
    function cancelSecondaryOrder(){
        $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
    }
    function showHideSecondaryActionButton(isShow){
        if(isShow){
            $("#save-button-secondaryOrder").show();
            $("#submit-button-secondaryOrder").show();
            $("#cancel-button-secondaryOrder").show();
        }else{
            $("#save-button-secondaryOrder").hide();
            $("#submit-button-secondaryOrder").hide();
            $("#cancel-button-secondaryOrder").hide();
        }
    }
    function deleteAjaxMultipleRetailOrder() {    // Delete record
        var selectedRetailOrderIds = jQuery("#jqgrid-grid-retailOrder").jqGrid('getGridParam','selarrrow');
        if(selectedRetailOrderIds.toString() != ""){
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-retailOrder").dialog({
                resizable: false,
                height:'auto',
                width: 450,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function() {
                        $(this).dialog('close');
                        SubmissionLoader.showTo();
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data:"retailOrderIds=" + selectedRetailOrderIds,
                            url: "${resource(dir:'consolidateRetailOrder', file:'deleteMultipleRetailOrder')}",
                            success: function(message) {
                                if(message.type == 1){
                                    $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
                                    $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
                                }
                                MessageRenderer.render(message);
                            },
                            error:function(XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
                                    $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');

                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                } else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function(){
                                SubmissionLoader.hideFrom();
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }
</script>