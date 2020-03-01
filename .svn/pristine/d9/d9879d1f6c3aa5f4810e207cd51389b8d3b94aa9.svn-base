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

        $("#deliveryDateSearch").datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
//            minDate: new Date()
        });

        $("#gSecondaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gSecondaryDemandOrder").validationEngine('attach');

        $('#deliveryDateSearch').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        jqGridRetailOrderList = $("#jqgrid-grid-retailOrder").jqGrid({
            datatype: "local",
            colNames: [
                'SL',
                'ID',
                'Order No',
                'Order Amount',
                'Order Date',
                'Legacy ID',
                'Customer ID',
                'Customer Name',
                'Expected Delivery Date'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 20, sortable: true, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'orderNo', index: 'orderNo', width: 85, align: 'center'},
                {name: 'orderAmount', index: 'orderAmount', width: 50, align: 'right'},
                {name: 'orderDate', index: 'orderDate', width: 50, align: 'center'},
                {name: 'legacyId', index: 'legacyId', width: 40, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 60, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 100, align: 'left'},
                {name: 'deliveryDate', index: 'deliveryDate', width: 55, align: 'center'}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Retail Orders Available for Processing",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
//            rownumbers: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeShowRetailOrderDetails(rowid);
            }
        });

        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Product Code',
                'Product Name',
                'Order Qty',
                'Available Qty'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'productCode', index:'productCode', width:100, align:'center'},
                {name:'productName', index:'productName', width:200, align:'left'},
                {name:'orderQuantity', index:'orderQuantity', width:50, align:'center', sorttype:'number', formatter:"number"},
                {name:'availableQuantity', index:'availableQuantity', width:50, align:'center', sorttype:'number', formatter:'number'}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Item wise Availability",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
            rownumbers: true,
            altRows: true,
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
                {name: 'rate', index: 'rate', width: 100, align: 'right', formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'amount', index: 'amount', width: 100, align: 'right',formatter:"number", formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
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
//                calculateSecondaryDetailsSummaryData();
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
//                calculateSecondaryDetailsSummaryData();
            },
            onSelectRow: function (rowid, status) {
//                    editProductForPrimaryOrder(rowid);
            }
        });
        %{--loadProduct(${customerMaster?.id});--}%
        loadSecondaryOrderAutoComplete();
    });
    function executeRetailOrderSearch() {
        // Get Searched data
        var secondaryOrderNo = $("#orderNoSearch").val();
        var deliveryDate = $('#deliveryDateSearch').val();
        if(!deliveryDate && ! secondaryOrderNo){
            MessageRenderer.renderErrorText("Select Delivery Date OR Secondary Order No");
            return
        }
        var includePendingOrder = "true";
        if (!$('#includePendingOrder').is(":checked")) {
            includePendingOrder = "";
        }
        $("#jqgrid-grid-retailOrder").jqGrid().setGridParam({url: "${resource(dir:'processRetailOrder', file:'listOrderForProcess')}?"
                + 'includePendingOrder=' + includePendingOrder + '&deliveryDate=' + deliveryDate + '&secondaryOrderNo=' + secondaryOrderNo,
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }
    function executeShowRetailOrderDetails(retailOrderId) {
        if(retailOrderId.toString() != ""){
            $("#jqgrid-grid-finishProduct").jqGrid().setGridParam({url: "${resource(dir:'processRetailOrder', file:'listOrderDetailsForProcess')}?"
                    + 'retailOrderIds=' + retailOrderId.toString(),
                datatype: "json"
            }).trigger("reloadGrid", [
                        {page: 1}
                    ]);
        }else{
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        }
        $("#div-process-manually").html("");
    }
    function showAllRetailOrderDetails() {
        var retailOrderIds = $('#jqgrid-grid-retailOrder').jqGrid('getDataIDs');
        if(retailOrderIds.length > 0){
            $("#jqgrid-grid-finishProduct").jqGrid().setGridParam({url: "${resource(dir:'processRetailOrder', file:'listOrderDetailsForProcess')}?"
                    + 'retailOrderIds=' + retailOrderIds.toString(),
                datatype: "json"
            }).trigger("reloadGrid", [
                        {page: 1}
                    ]);
        }else{
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        }
//        $("#jqgrid-grid-secondaryOrderDetails").jqGrid('clearGridData');
    }
    function loadSecondaryOrderAutoComplete() {
        jQuery('#orderNoSearch').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term};
                var url = '${resource(dir:'processRetailOrder', file:'secondaryOrderAutoCompleteForProcess')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] =  item['orderNo'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#orderNoSearch").val(ui.item.orderNo);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var orderDetails = "";
            if (item) {
                orderDetails = '<div style="font-size: 9px; color:#326E93;">' + "Customer Legacy ID: " + item.legacyId +", Customer Code: " +item.customerCode + ", Customer Name: " + item.customerName + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + orderDetails).appendTo(ul);
        };
    }
    function showManualProcess(){
        var retailOrderId = $("#jqgrid-grid-retailOrder").jqGrid('getGridParam', 'selrow');
        if (retailOrderId) {
            SubmissionLoader.showTo();
            $("#div-process-manually").html("");
            $.ajax({
                type:'POST',
                data:'id=' + retailOrderId,
                url:'${request.contextPath}/processRetailOrder/showManualProcess',
                success:function (result) {
                    $("#div-process-manually").html(result);
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                    SubmissionLoader.hideFrom();
                },
                dataType:'html'
            });
        }
    }
    function executePreConditionAutoProcess() {
        // trim field vales before process.
        var allRetailOrderIds = $('#jqgrid-grid-retailOrder').jqGrid('getDataIDs');
        if(!allRetailOrderIds){
            MessageRenderer.renderErrorText("No Retail Order Available");
            return false
        }
        return true;
    }
    function executeAjaxAutoProcess() {
        $("#div-process-manually").html("");
        if (!executePreConditionAutoProcess()) {
            return false;
        }
        var allRetailOrderIds = $('#jqgrid-grid-retailOrder').jqGrid('getDataIDs');

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {retailOrderIds: allRetailOrderIds.toString()},
            url: "${request.contextPath}/${params.controller}/processAutomatically",
            success: function (data, textStatus) {
                executePostConditionAutoProcess(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
                $("#jqgrid-grid-retailOrder").trigger("reloadGrid");
                $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
            },
            dataType: 'json'
        });
        return false;
    }
    function executePostConditionAutoProcess(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        }
        MessageRenderer.render(result);
    }
</script>