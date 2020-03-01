<script type="text/javascript" language="Javascript">

    var invoiceIds = [];
    var invoiceSize = 0;
    var truckId = -1;

    $(document).ready(function () {
        loadOrderNo();
        var entId = $("#idEnterprise").val();
        clear_form('#gFormSalesReceipt');
        $("#idEnterprise").val(entId);
        $('#enterpriseConfiguration').val(entId);
        $("#invoice-grid").clearGridData();

        $("#invoice-grid").jqGrid({
            url: '${resource(dir:'customerPayment', file:'listMRNo')}?entId=' + $('#enterpriseConfiguration').val(),
            datatype: "json",
            colNames: [
                'Select',
                'Id',
                'MR No',
                'Customer Name',
                'Transaction Number',
                'Invoice No',
                'Transaction Date',
                'Paid Amount',
                'Cancel Payment'
            ],
            colModel: [
                {
                    name: 'select', width: 30, align: 'center',
                    formatter: cboxFormatter, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'id', index: 'id', width: 100, hidden: true},
                {name: 'mrNo', index: 'mrNo', width: 60, align: 'center'},
                {name: 'customerName', index: 'customerName', width: 120, align: 'left'},
                {name: 'trans_no', index: 'trans_no', width: 70, align: 'center'},
                {name: 'invoices', index: 'invoices', width: 150, align: 'left'},
                {name: 'dateTransaction', index: 'dateTransaction', width: 60, align: 'center'},
                {name: 'amount', index: 'amount', width: 70, align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name: 'cancel', index: 'cancel', width: 70, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#invoice-grid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Invoice Information",
            autowidth: false,
            footerrow:true,
            width: 1000,
            height: 300,
            scrollOffset: 0,
            loadComplete: function (data) {
                var $grid = $("#invoice-grid");
                $.each(data.rows, function (i, item) {
                    var rowData = $grid.jqGrid('getRowData', data.rows[i].id);
                    rowData.cancel = '<input type="button" name="receive-button" class="ui-button ui-widget ui-state-default ui-corner-all" value="Cancel" onclick="cancelPayment(' + data.rows[i].id + ',\''+ rowData.trans_no + '\');" />';
                    $grid.jqGrid('setRowData', data.rows[i].id, rowData);
                });

                var colSum = $grid.jqGrid('getCol', 'amount', false, 'sum');
                $grid.jqGrid('footerData','set',{amount : colSum.toFixed(2)});
                $grid.jqGrid('footerData','set',{dateTransaction : 'Total Paid Amount'});

                var $footRow = $grid.closest(".ui-jqgrid-bdiv")
                        .next(".ui-jqgrid-sdiv")
                        .find(".footrow");

                var $select = $footRow.find('>td[aria-describedby="invoice-grid_select"]'),
                    $mrNo = $footRow.find('>td[aria-describedby="invoice-grid_mrNo"]'),
                    $customerName = $footRow.find('>td[aria-describedby="invoice-grid_customerName"]'),
                    $trans_no = $footRow.find('>td[aria-describedby="invoice-grid_trans_no"]'),
                    $invoices = $footRow.find('>td[aria-describedby="invoice-grid_invoices"]'),
                    $invoiceNo = $footRow.find('>td[aria-describedby="invoice-grid_invoiceNo"]'),
                    $dateTransaction = $footRow.find('>td[aria-describedby="invoice-grid_dateTransaction"]'),
                    width7 = $select.width() + $mrNo.outerWidth() + $customerName.outerWidth() + $trans_no.outerWidth()
                            + $invoices.outerWidth() + $invoiceNo.outerWidth() + $dateTransaction.outerWidth();
                $select.css("display", "none");
                $mrNo.css("display", "none");
                $customerName.css("display", "none");
                $trans_no.css("display", "none");
                $invoices.css("display", "none");
                $invoiceNo.css("display", "none");

                $dateTransaction.attr("colspan", "7").width(width7);
                $dateTransaction.css("text-align","right");
            },
            onSelectRow: function (rowid, status) {
            }
        });
        $("#invoice-grid").jqGrid('navGrid', '#invoice-grid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){addToInvoice(' + options.rowId + ')}else{removeFromInvoice(' + options.rowId + ')};"/>';
    }

    function setId(id){
        $('#idEnterprise').val(id);
        jQuery("#invoice-grid").jqGrid().setGridParam(
                {url: '${resource(dir:'customerPayment', file:'listMRNo')}?entId=' + $('#idEnterprise').val()}).trigger("reloadGrid");
//                        resetAll();
    }

    function loadOrderNo(){
        jQuery('#searchOrderKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                var data = {
                    searchKey:request.term,
                    enterpriseConfiguration:$('#enterpriseConfiguration').val()
                };
                var url = '${resource(dir:'customerPayment', file:'listMRNoForAutoComplete')}' ;
                DocuAutoComplete.setSpinnerSelector('searchOrderKey').execute(response, url, data, function (item) {
                    item['label'] = item['mr_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#searchOrderKey").val(ui.item.mr_no);
                $("#name").val(ui.item.name);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " MR No: " + item.mr_no + " Customer: " +  item.name + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }
    function loadDataByOrder() {

        $("#invoice-grid").setGridParam({
            url: '${resource(dir:'customerPayment', file:'listMRNo')}?entId=' + $('#idEnterprise').val() + '&slipNo='
            + $('#searchOrderKey').val() + '&dateFrom=' + $('#dateFrom').val() + '&dateTo=' + $('#dateTo').val()
        });
        $("#invoice-grid").trigger("reloadGrid");
    }

    function addToInvoice(id) {
        var mr  = $("#invoice-grid").getCell(id, 'mrNo');
        invoiceIds[invoiceSize] = mr;
        invoiceSize++;
    }

    function removeFromInvoice(id) {
        var mr  = $("#invoice-grid").getCell(id, 'mrNo');
        if (invoiceSize == 2) {
            var x = -1;
            for (var i = 0; i < invoiceSize; i++) {
                if (invoiceIds[i] != null && invoiceIds[i] != mr) {
                    x = invoiceIds[i];
                }
            }
            invoiceSize--;
            invoiceIds = [];
            invoiceIds[0] = x;
        } else {
            for (var i = 0; i < invoiceSize; i++) {
                if (invoiceIds[i] == mr) {
                    invoiceIds[i] = null;
                    invoiceSize--;
                }
            }
        }
    }

    function printMR(){
        if(invoiceSize <= 0){
            MessageRenderer.renderErrorText("Please Select MR to print");
            return
        }
        window.open("${request.contextPath}/customerPayment/generateInvoiceReport?"
                + "mr=" + invoiceIds + "&entId=" + $("#idEnterprise").val()
        );
    }

    function cancelPayment(id, trans_no){
        $("#cancelPaymentMsg").html("System will permanently delete all record set about the transaction no <b>"+trans_no+"</b>. Do you really want to proceed to cancel payment?");
        $("#dialog-confirm-cancelPayment").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm to Cancel Payment',
            buttons: {
                Yes: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    $.ajax({
                        url: '${application.contextPath}/${params.controller}/cancelPayment',
                        type: "POST",
                        data: {id:id, transNo:trans_no},
                        dataType: "json",
                        beforeSend: function () {
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                $("#invoice-grid").trigger("reloadGrid");
                            }
                            MessageRenderer.render(result);
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
                        }
                    });
                },
                No: function () {
                    $(this).dialog('close');
                }
            }
        });
    }

</script>