<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript">
    $(document).ready(function(){
        $("#gFormRegisterFG").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormRegisterFG").validationEngine('attach');
        setDateRangeNoLimit('fromDate','toDate');
        $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

        if(${wareHouseList.size = 1}){
            $("#warehouse").val(${wareHouseList[0].id}).attr("selected", "selected");
            $("#warehouse").change();
        }

        $("#jqgrid-grid-register-fg").jqGrid({
            url: "${resource(dir:'registerFinishGood', file:'listReceivableOrder')}",
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Invoice/Challan No',
                'Primary Order No',
                'Customer',
                'Order Date',
                'Invoice Amount',
                ''
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 35, sortable: false, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'invoiceNo', index: 'invoiceNo', width: 170, align: 'center'},
                {name: 'orderNo', index: 'orderNo', width: 150, align: 'center'},
                {name: 'customer', index: 'name', width: 200, align: 'left'},
                {name: 'orderDate', index: 'orderDate', width: 150, align: 'left',  formatter: 'date', formatoptions: {newformat: 'd-m-Y'}},
                {name: 'invoiceAmount', index: 'invoiceAmount', width: 200,formatter: 'number', align: 'right'},
                {name: 'receive', index: 'receive', width: 160, align: 'center'}
            ],
            rowNum: -1,
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Receivable Orders",
            width: 620,
            height: true,
            multiselect: false,
            scrollOffset: 0,
            subGrid : true,
            subGridRowExpanded: function (subgridId, rowid) {
                var subgridTableId = subgridId + "_t";
                $("#" + subgridId).html("<table id='" + subgridTableId + "'></table>");
                $("#" + subgridTableId).jqGrid({
                    datatype: "json",
//                    data: {id: rowid},
                    url: "${resource(dir:'registerFinishGood', file:'listReceivableOrderDetails')}?id=" + rowid,
                    colNames: ["SL", "Item", "Quantity", "Rate","Amount"],
                    colModel: [
                        {name: "sl", index:"sl", width: 40, align: 'center'},
                        {name: "item", index:"item", width: 230, align: 'left'},
                        {name: "quantity",index:"quantity",template: "number", width: 100, align: 'center'},
                        {name: "amount",index:"amount",template: "number", width: 100, align: 'right'},
                        { name: "amountcalculate", index:"amountcalculate", width: 60,align: 'right',
                            formatter: function (cellvalue, options, rowObject)
                            {
                                var rq =rowObject[2];
                                var up = rowObject[3];
                                return (parseFloat(rq) * parseFloat(up)).toFixed(2);
                            }
                        }
                    ],
                    height: "100%",
                    rowNum: -1,
                    sortname: "name",
                    footerrow : true,
                    idPrefix: "s_" + rowid + "_"
                });
                setTimeout(function(){
                    var $grid = $("#" + subgridTableId);
                    var colSum = $grid.jqGrid('getCol', 'amountcalculate', false, 'sum');
                    $grid.jqGrid('footerData', 'set', { 'amountcalculate': colSum.toFixed(2),amount:"Total"}, false);
                },1000);

            },
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-register-fg").jqGrid('getRowData', data.rows[i].id);
                    rowData.receive = '<input type="button" name="receive-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Receive" onclick="executeAjaxRegisterFG(' + data.rows[i].id + ');"/>';
                    $('#jqgrid-grid-register-fg').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            onSelectRow: function (rowid, status) {
            }
//            ,
//            gridComplete: function() {
//                $("#jqgrid-grid-register-fg_cb").css("width","30px");
//                $("#jqgrid-grid-register-fg tbody tr").children().first("td").css("width","30px");
//            }
        });
        $("#jqgrid-grid-register-fg").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
    });

    function loadSubWarehouse(warehouseId){
        var options = '<option value="">Select Sub Inventory</option>';
        if(warehouseId){
            $.ajax({
                type:'POST',
                data:'id=' + warehouseId,
                url:'${request.contextPath}/${params.controller}/listSubWarehouse',
                success:function (data) {
                    if (data.length > 0) {
                        for (var i = 0; i < data.length; i++) {
                            options += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                        }
                    }
                    $("#subWarehouse").html(options);
                    if (data.length > 0) {
                        $("#subWarehouse").val(data[0].id).attr("selected", "selected");
                    }
                },
                error:function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }  else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete:function (data) {
                    //alert('in complete');
                },
                dataType:'json'
            });
        }else{
            $("#subWarehouse").html(options);
        }
    }

    function executePreConditionRegisterFG() {
        if (!$("#gFormRegisterFG").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxRegisterFG(invoiceId) {
        if (!executePreConditionRegisterFG()) {
            return false;
        }
        var warehouseId = $("#warehouse").val();
        var subWarehouseId = $("#subWarehouse").val();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: 'invoiceId=' + invoiceId + "&warehouseId=" + warehouseId + "&subWarehouseId=" + subWarehouseId,
            url: "${request.contextPath}/${params.controller}/receive",
            success: function (data, textStatus) {
                executePostConditionRegisterFG(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-register-fg").trigger("reloadGrid");
                    reset_form('#gFormRegisterFG');
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
    function executePostConditionRegisterFG(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-register-fg").trigger("reloadGrid");
            reset_form('#gFormRegisterFG');
        }
        MessageRenderer.render(result);
    }
    function searchReceivableOrder() {
        jQuery("#jqgrid-grid-register-fg").jqGrid().setGridParam({url:"${resource(dir:'registerFinishGood', file:'listReceivableOrder')}?"
                + "invoiceNo=" + $("#invoiceNo").val() + "&fromDate=" + $("#fromDate").val() + "&toDate=" + $("#toDate").val(),
            mtype: "POST",
            cache: false,
            datatype:"json"
        }).trigger("reloadGrid",[{page:1}])
    }
</script>