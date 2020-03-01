<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var subAreaId = 1;
    $(document).ready(function () {
        initDatePicker();

        /*$('#timeTransaction').timepicker({showPeriod: true, showLeadingZero: true});
        $('#ui-widget-header-text').html('Finish Goods');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }*/
        $("#gFormHoReceiveReport").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
/*

        $("#jqgrid-grid-primaryOrder").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                '',
                'SL',
                'Id',
//                '&#10004;',
                'Customer Name',
                'Legacy ID',
                'Customer ID',
                'Primary Order No',
                'Order Date',
                'Expected Delivery Date',
                'Customer S_Id',
                'Demand Value',
                'Advance ACC Code',
                'Receivable ACC Code'

            ],
            colModel: [
                {name: 'edit', index: 'edit', width: 20, align: 'center', sortable: false},
                {name: 'sl', index: 'sl', width: 30, align: 'center'},
                {name: 'id', index: 'id', width: 30, hidden: true},
//                {name: 'checkOrder', index: 'checkOrder', width: 30, align: 'center'},
                {name: 'customer_name', index: 'customer_name', width: 130, align: 'left'},
                {name: 'legacy_id', index: 'legacy_id', width: 80, align: 'right'},
                {name: 'customer_id', index: 'customer_id', width: 100, align: 'left', hidden: true},
                {name: 'order_no', index: 'order_no', width: 80, align: 'center'},
                {name: 'order_date', index: 'order_date', width: 80, align: 'center'},
                {name: 'date_expected_deliver', index: 'date_expected_deliver', width: 80, align: 'center'},
                {name: 'customer_code', index: 'customer_code', width: 80, align: 'left', hidden: true},
                {
                    name: 'totalAmout',
                    index: 'totalAmout',
                    width: 80,
                    align: 'right',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 2}
                },
                {name: 'adv_acc_code', index: 'adv_acc_code', width: 100, align: 'left', hidden: true},
                {name: 'rcv_acc_code', index: 'rcv_acc_code', width: 100, align: 'left', hidden: true}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-primaryOrder-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Orders Available for Processing",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            multiselect: true,
            gridComplete: function () {
                $("#jqgrid-grid-primaryOrder_cb").html("&#10004;");
            },
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-primaryOrder").jqGrid('getRowData', data.rows[i].id);
                    rowData.edit = '<a  href="javascript:updateDemandOrderPopup(' + data.rows[i].id + ')" class="ui-icon ui-icon-pencil" title="Change Item Quantity"></a>';
                    $('#jqgrid-grid-primaryOrder').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            onSelectRow: function (rowid, status) {
                checkItemQuantity();
            }
        });

        $("#jqgrid-grid-item-available").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'SL',
                'Id',
                '&#10004;',
                'Product Name',
                'Product Code',
                'Order Qty.',
                'Available Batches',
                'Available Qty.',
                'Batch Wise Qty.',
                'Hidden Qty.'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, align: 'center'},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'checkOrder', index: 'checkOrder', width: 30, hidden: true},
                {name: 'name', index: 'name', width: 180},
                {name: 'code', index: 'code', width: 180},
                {
                    name: 'order_qty',
                    index: 'order_qty',
                    width: 70,
                    align: 'center',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 0}
                },
                {name: 'batch_no', index: 'batch_no', width: 100, align: 'left'},
                {
                    name: 'qty',
                    index: 'qty',
                    width: 85,
                    align: 'center',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 0}
                },
                {name: 'batch_qty', index: 'batch_qty', width: 200, align: 'left'},
                {name: 'hidden_qty', index: 'hidden_qty', width: 150, align: 'left', hidden: true}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Item wise Availability",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                var order_qty = $("#jqgrid-grid-item-available").jqGrid('getCell', rowid, 'order_qty');
                var avail_qty = $("#jqgrid-grid-item-available").jqGrid('getCell', rowid, 'qty');
                if (order_qty == 0) {
                    var msg = {
                        "class": "com.docu.common.Message",
                        "messageBody": ["Order Quantity Is Zero"],
                        "messageTitle": "Message",
                        "type": 0
                    };
                    MessageRenderer.render(msg);
                }
                else {
                    var allIds = $("#jqgrid-grid-primaryOrder").jqGrid('getGridParam', 'selarrrow');
                    if (allIds == "") {
                        $("#dialog").dialog("destroy");
                        $("#dialog-primaryOrder-selection").dialog({
                            resizable: false,
                            height: 150,
                            modal: true,
                            title: 'Order Selection Missing',
                            buttons: {
                                Ok: function () {
                                    $(this).dialog('close');
                                }
                            }
                        }); //end of dialog
                        return false
                    }
                    var allIdList = allIds.toString().split(",");
                    if (allIdList.length > 1) {
                        var msg = {
                            "class": "com.docu.common.Message",
                            "messageBody": ["Select Single Order for Batch Wise Allocation"],
                            "messageTitle": "Message",
                            "type": 0
                        };
                        MessageRenderer.render(msg);
                        return false
                    }
                    else {
                        batchWiseOrderAllocation(rowid, allIds)
                    }
                }
            }
        });
*/

        function initDatePicker() {
            $("#toDate,#fromDate").datepicker(
                    {
                        dateFormat: 'dd-mm-yy',
                        changeMonth: true,
                        changeYear: true
                    });
            $('#fromDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
            $('#toDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        }
    });

    function setId(id) {
        $('#enterpriseConfiguration').val(id);
    }

</script>