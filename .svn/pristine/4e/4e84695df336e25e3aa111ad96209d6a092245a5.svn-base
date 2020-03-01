<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var subAreaId = 1
    var allElements = new Array();
    var elementIndex = 0;
    var lastSel = -1;
    var appliedAmountText = '';
    var qtyText = '';
    function checkForValue(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }
    var updateOrderEditor = {
        onEnterKeyPressToGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;

                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-grid').restoreRow(lastSel);
                            updateOrderEditor.editGridCell(lastSel)
                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-grid').restoreRow(lastSel);
                        updateOrderEditor.editGridCell(lastSel)
                    }
                }
            };

            return callback;
        },

        editGridCell: function (rowid) {
            var myGrid = $("#jqgrid-grid");
            var appliedAmount = appliedAmountText.value;

            if (appliedAmount && rowid) {
                if (isNaN(appliedAmount)) {
                    alert("Please enter number as amount");
                }
                else {
                    selRowId = myGrid.jqGrid('getGridParam', 'selrow');
                    celValue = myGrid.jqGrid('setCell', selRowId, 'applied_amount', appliedAmount);
                    var sum = 0.0;
                    $('.print-checkbox').each(function () {
                        if (this.checked) {
                            var approvalInfo = $(this).val();
                            var appliedAmount = $("#jqgrid-grid").getCell(approvalInfo, 'applied_amount').toFixed(2);
                            sum += parseFloat(appliedAmount.toFixed(2));
                            var amount = Number($('#amount').val().toFixed(2));
//                            var amount = Number($('#amount').val()).toFixed(2);
                            if (sum > amount) {
                                MessageRenderer.render({
                                    messageTitle: 'Payment Register!',
                                    type: 2,
                                    messageBody: 'Total Applied amount is greater than Total received.'
                                });
//                                $('.print-checkbox').attr('checked', false);
//                                sum -= parseFloat(appliedAmount);
//                                $("#jqgrid-grid").setCell(approvalInfo, 'applied_amount', 0);
//                                sum = Number($("#totalAppliedAmount").val());
                            }
                        }
                    });
                    $("#totalAppliedAmount").val(sum);
                }
            }
            else {
                alert("Please enter amount")
            }

        },
        onEnterKeyPressToDataGridCell: function () {
            var callback = {
                type: 'keydown',
                fn: function (e) {
                    var key = e.charCode || e.keyCode;
                    if (key == 9) {
                        if (this.name == allElements[allElements.length - 1].name) {
                            e.preventDefault();
                            jQuery('#jqgrid-data-grid').restoreRow(lastSel);
                            updateOrderEditor.editDataGridCell(lastSel)
                        }
                    }
                    else if (key == 13) {
                        jQuery('#jqgrid-data-grid').restoreRow(lastSel);
                        updateOrderEditor.editDataGridCell(lastSel)
                    }
                }
            };

            return callback;
        },
        editDataGridCell: function (rowid) {
            var myGrid = $("#jqgrid-data-grid");
            var qty = qtyText.value
            if (qty && rowid) {
                if (isNaN(qty)) {
                    alert("Please enter number as amount");
                }
                else {
                    selRowId = myGrid.jqGrid('getGridParam', 'selrow');
                    var quantity = myGrid.jqGrid('getCell', selRowId, 'qty');
                    var oldTotal = Number($("#totalAmountQty").val());
                    celValue = myGrid.jqGrid('setCell', selRowId, 'qty', qty);
                    var ids = $("#jqgrid-data-grid").jqGrid('getDataIDs');
                    var sum = 0;
                    for (key in ids) {
                        var id = $("#jqgrid-data-grid").getCell(ids[key], 'id');
                        var qtyAmount = $("#jqgrid-data-grid").getCell(id, 'qty');
                        var amount = $("#jqgrid-data-grid").getCell(id, 'amount');
                        sum += parseFloat((qtyAmount * amount).toFixed(2));
                        var recAmount = Number($("#amount").val());
                        if (sum > recAmount) {
                            MessageRenderer.render({
                                messageTitle: 'Payment Register!',
                                type: 2,
                                messageBody: 'Total is greater than specified Received Amount.'
                            });
                            sum = oldTotal;
                            myGrid.jqGrid('setCell', selRowId, 'qty', quantity);
                            break;
                        }
                    }
                    $("#totalAmountQty").val(sum);
                }
            }
            else {
                alert("Please enter amount")
            }

        }
    };
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Register Cash Through Bank');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCashTroughBank").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashTroughBank").validationEngine('attach');

        clearAll();

        setPreviousDatePicker('dateTransaction');
        $('#dateTransaction').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                $("#customerMaster").val('');
            }
        });

//        $('#gridSecurityDeposit').hide();
//      $('#remove-button').hide();
        $("#jqgrid-grid").jqGrid({
            datatype: "local",
            colNames: [

                'Id',
                'Invoice Number',
                'Invoice date',
                'Payment Due Date',
                'Amount',
                'Paid Amount',
                'Due Amount',
                'Applied Amount',
                'Select'

            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'invoice_no', index: 'invoice_no', width: 100, align: 'center'},
                {name: 'invoice_date', index: 'invoice_date', width: 100, align: 'center'},
                {name: 'payment_due_date', index: 'payment_due_date', width: 100, align: 'center'},
                {
                    name: 'amount',
                    index: 'amount',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {
                    name: 'paid_amount',
                    index: 'paid_amount',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {
                    name: 'due_amount',
                    index: 'due_amount',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {
                    name: 'applied_amount',
                    index: 'applied_amount',
                    width: 100,
                    align: 'right',
                    sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: "", decimalPlaces: 2},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'edit', index: 'edit', width: 50, align: 'left', hidden: true}

            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Available Invoices",
            autowidth: true,
            height: 250,
            footerrow: true,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
//                var sum = 0;
//                $('.print-checkbox').each(function () {
//                    if (this.checked) {
//                        var approvalInfo = $(this).val();
//                        var appliedAmount = $("#jqgrid-grid").getCell(approvalInfo, 'applied_amount');
//                        sum += parseFloat(appliedAmount)
//                    }
//                });
                $("#totalAppliedAmount").val($("#jqgrid-grid").jqGrid('getCol', 'applied_amount', false, 'sum'));
                var ids = $("#jqgrid-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-grid").getCell(ids[key], 'id');
                    $("#jqgrid-grid").jqGrid('setRowData', ids[key], {
                        edit: '<input type="checkbox" id="print-checkbox' +
                        id + '" class="print-checkbox" value="' + id + '" checked="checked" onclick="javascript:loadTotalAmount(' + id + ')"/>'
                    });
                }
//                var sumAmount = $("#jqgrid-grid").jqGrid('getCol', 'due_amount', false, 'sum');
                //$("#totalReceivedAmount").val($("#jqgrid-grid").jqGrid('getCol', 'due_amount', false, 'sum'));
                $("#jqgrid-grid").jqGrid('footerData', 'set', {'payment_due_date': 'Total:'});
                $("#jqgrid-grid").jqGrid('footerData', 'set', {'amount': $("#jqgrid-grid").jqGrid('getCol', 'amount', false, 'sum')});
                $("#jqgrid-grid").jqGrid('footerData', 'set', {'paid_amount': $("#jqgrid-grid").jqGrid('getCol', 'paid_amount', false, 'sum')});
                $("#jqgrid-grid").jqGrid('footerData', 'set', {'due_amount': $("#jqgrid-grid").jqGrid('getCol', 'due_amount', false, 'sum')});
//                $("#jqgrid-grid").jqGrid('footerData', 'set', {'due_amount': $("#totalReceivedAmount").val()});
                $("#jqgrid-grid").jqGrid('footerData', 'set', {'applied_amount': 0});
            },
            onSelectRow: function (rowid, status) {
                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {
                    jQuery('#jqgrid-grid').restoreRow(lastSel);
                    $("#jqgrid-grid").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-grid").jqGrid('editRow', rowid, true);
                    lastSel = rowid;
                }
            },
            afterSaveCell: function (rowid) {
                var due = parseFloat($("#jqgrid-grid").jqGrid('getCell', rowid, 'due_amount'));
                var applied = parseFloat($("#jqgrid-grid").jqGrid('getCell', rowid, 'applied_amount'));
                if (applied > due) {
                    MessageRenderer.render({
                        messageTitle: 'Payment Register!',
                        type: 2,
                        messageBody: 'Applied Amount can not be greater than Due Amount.'
                    });
                    $("#jqgrid-grid").jqGrid('setCell', rowid, 'applied_amount', due);
                }
                loadTotalAmount();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
        $("#jqgrid-data-grid").jqGrid({
            url: '${request.contextPath}/${params.controller}/listCurrencyDenomination',
            datatype: "json",
            colNames: [
                'Id',
                'Denomination',
                'Qty'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'amount', index: 'amount', width: 100, align: 'left'},
                {
                    name: 'qty',
                    index: 'qty',
                    width: 100,
                    align: 'left',
                    editable: true,
                    edittype: 'text',
                    resizable: true,
                    editoptions: {
                        dataInit: function (elem) {
                            qtyText = elem;
                            $(elem).focus(function () {
                                this.select();
                            })
                        }, dataEvents: [updateOrderEditor.onEnterKeyPressToDataGridCell()]
                    },
                    editrules: {custom: true, custom_func: checkForValue}
                }
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-data-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Enter Amount Received",
            autowidth: true,
            height: 150,

//            footerrow:true,
            scrollOffset: 0,
            loadComplete: function () {
//                var sum = 0;
//                $('.print-checkbox').each(function () {
//                    if (this.checked) {
//                        var approvalInfo = $(this).val();
//                        var appliedAmount = $("#jqgrid-data-grid").getCell(approvalInfo, 'applied_amount');
//                        sum += parseFloat(appliedAmount)
//                    }
//                });
//                $("#totalAppliedAmount").val(sum.toFixed(2));
//                var ids = $("#jqgrid-data-grid").jqGrid('getDataIDs');
//                allEmail = '';
//                allMobile = '';
//                for (key in ids) {
//                    var id = $("#jqgrid-data-grid").getCell(ids[key], 'id');
//                    $("#jqgrid-data-grid").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="print-checkbox' + id + '" class="print-checkbox" value="' + id + '" onclick="javascript:loadTotalAmount(' + id + ')"/>'});
//                }
            },
            onSelectRow: function (rowid, status) {
                elementIndex = 0;
                allElements = new Array();
                if (rowid && rowid !== lastSel) {
                    jQuery('#jqgrid-data-grid').restoreRow(lastSel);
                    $("#jqgrid-data-grid").editRow(rowid, true, false, false, false, false);
                    lastSel = rowid;
                }
                if (rowid == lastSel) {
                    jQuery("#jqgrid-data-grid").jqGrid('editRow', rowid, true);
                    lastSel = rowid;
                }
            }
        });
        $("#jqgrid-data-grid").jqGrid('navGrid', '#jqgrid-data-pager', {
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

        $("#jqgrid-sd-grid").jqGrid({
            %{--url: '${request.contextPath}/${params.controller}/listSecurityDeposit?customerId='+4,--}%
            url: '',
            datatype: "json",
            colNames: [
                'Customer ID',
                'Customer Name',
                'Deposited',
                'Withdrawn',
                'Date'
            ],
            colModel: [
                {name: 'customerId', index: 'customerId', width: 0, hidden: true},
                {name: 'customerName', index: 'customerName', width: 0, hidden: false},
                {name: 'deposited', index: 'deposited', width: 100, align: 'right'},
                {name: 'withdrawn', index: 'withdrawn', width: 100, align: 'right'},
                {
                    name: 'dateTransaction', index: 'dateTransaction', width: 100, align: 'center',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }
            ],
            rowNum: 50,
            rownumbers: true,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-sd-pager',
            sortname: 'si',
            viewrecords: true,
            sortorder: "desc",
            caption: "Customer All Security Deposit",
            autowidth: true,
            height: 150,
//            footerrow:true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });
        $("#jqgrid-sd-grid").jqGrid('navGrid', '#jqgrid-sd-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        if ('${isFactory}' == 'true') {
            loadBankAccount();
        } else {
            $("#paymentMode").val('Cash');
            $("#bankOption").attr('disabled', 'disabled');
            $("#bank").hide();
            $("#cash").show();
        }
        $("#amount").format({precision: 2, allow_negative: false, autofix: true});
        $("#confirmAmount").format({precision: 2, allow_negative: false, autofix: true});
    });
    function getSelectedSecondaryDemandOrderId() {
        var secondaryDemandOrderId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            secondaryDemandOrderId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (secondaryDemandOrderId == null) {
            secondaryDemandOrderId = $('#gFormCashTroughBank input[name = id]').val();
        }
        return secondaryDemandOrderId;
    }
    function executePreConditionSecondaryDemandOrder() {
        // trim field vales before process.
//      trim_form();
        if (!$("#gFormCashTroughBank").validationEngine('validate')) {
            return false
        }

        /*
        if (!$('#isSecurityDeposit').is(':checked')){
            if (parseFloat($("#amount").val()) <= parseFloat($("#totalReceivedAmount").val())) {
                if (parseFloat($("#amount").val()) != parseFloat($("#totalAppliedAmount").val())) {
                    MessageRenderer.render({
                        messageTitle: 'Data Error!',
                        type: 2,
                        messageBody: 'Amount can not be greater or less than Total Applied Amount.'
                    });
                    return false;
                }
            } else {
                if (parseFloat($("#totalReceivedAmount").val()) != parseFloat($("#totalAppliedAmount").val())) {
                    MessageRenderer.render({
                        messageTitle: 'Data Error!',
                        type: 2,
                        messageBody: 'Total Applied Amount can cot be greater or less than Total Due Amount.'
                    });
                    return false;
                }
            }
        } */

        return true;
    }
    function executeAjax() {
        if (!executePreConditionSecondaryDemandOrder()) {
            return false;
        }
        var data = jQuery("#gFormCashTroughBank").serialize();
        var allIds = {};
        var i = 0;
        var gridCollection = jQuery("#jqgrid-grid").jqGrid('getRowData');
        $('.print-checkbox').each(function () {
            if (this.checked && parseFloat(gridCollection[i].applied_amount) > 0) {
                data = data + '&items.print[' + i + '].id=' + gridCollection[i].id;
                data = data + '&items.print[' + i + '].invoice_no=' + gridCollection[i].invoice_no;
                data = data + '&items.print[' + i + '].invoice_date=' + gridCollection[i].invoice_date;
                data = data + '&items.print[' + i + '].applied_amount=' + gridCollection[i].applied_amount;
            }
            i++;
        });

        var denominationData = jQuery("#jqgrid-data-grid").jqGrid('getRowData');
        var dataLength = denominationData.length;
        for (var k = 0; k < dataLength; k++) {
            data = data + '&denominations.secondaryDemandOrder[' + k + '].id=' + denominationData[k].id;
            data = data + '&denominations.secondaryDemandOrder[' + k + '].qty=' + denominationData[k].qty;
            data = data + '&denominations.secondaryDemandOrder[' + k + '].amount=' + denominationData[k].amount;
        }

        var amountReceived = Number($("#amount").val());
        var confirmAmount = Number($("#confirmAmount").val());
        var totalReceived = Number($("#totalReceivedAmount").val());
        var totalApplied = Number($("#totalAppliedAmount").val());

        var actionUrl = '';
        if (amountReceived != confirmAmount) {
            MessageRenderer.renderErrorText("Amount Received and Confirm Amount should be same.", 0);
            return false;
        }
        if (totalApplied > amountReceived) {
            MessageRenderer.renderErrorText("Total Applied Amount can't be greater than Amount Received.", 0);
            return false;
        }

        if ($('#denomination').is(":checked")) {
            var totalAmountQty = Number($("#totalAmountQty").val());
            if (amountReceived > totalAmountQty) {
                MessageRenderer.renderErrorText("Amount Received and Denomination Amount mismatch", 0);
                return false;
            }
        }
        %{--if ((totalApplied)) {--}%
        %{--if (((totalApplied) <= (amountReceived))) {--}%

        %{--if (((amountReceived) == (confirmAmount)) && ((amountReceived) == (totalReceived))) {--}%
        %{--actionUrl = "${request.contextPath}/${params.controller}/create";--}%
        %{--}--}%
        %{--else {--}%
        %{--MessageRenderer.renderErrorText("Amount Received, Confirm Amount and Total Received Amount should be same.", 0)--}%
        %{--}--}%
        %{--}--}%
        %{--else {--}%
        %{--MessageRenderer.renderErrorText("Amount Received Can not be less than Total Applied.", 0)--}%
        %{--}--}%

        %{--}--}%
        %{--else {--}%
        %{--MessageRenderer.renderErrorText("Please check total applied amount", 0)--}%
        %{--}--}%

        actionUrl = "${request.contextPath}/${params.controller}/create";
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSecondaryDemandOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").clearGridData();
                    $("#jqgrid-sd-grid").clearGridData();
                    $('#gridSecurityDeposit').hide();
                    clear_form('#gFormCashTroughBank');
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
    function executePostConditionSecondaryDemandOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").clearGridData();
            $("#jqgrid-sd-grid").clearGridData();
            $('#gridSecurityDeposit').hide();
//            clear_form('#gFormCashTroughBank');

            $("#searchKey").val("");
            $("#customerMaster").val("");
            $("#code").val("");
            $("#name").val("");

            $("#amount").val("");
            $("#confirmAmount").val("");
            $("#totalReceivedAmount").val("");
            $("#totalAppliedAmount").val("");

        }
        MessageRenderer.render(result);
    }
    function deleteAjaxSecondaryDemandOrder() {    // Delete record
        var secondaryDemandOrderId = getSelectedSecondaryDemandOrderId();
        if (executePreConditionForDeleteSecondaryDemandOrder(secondaryDemandOrderId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-secondaryDemandOrder").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        SubmissionLoader.showTo();
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: jQuery("#gFormCashTroughBank").serialize(),
                            url: "${resource(dir:'secondaryDemandOrder', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteSecondaryDemandOrder(message);
                            }
                        });
                        SubmissionLoader.hideFrom();
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForEditSecondaryDemandOrder(secondaryDemandOrderId) {
        if (secondaryDemandOrderId == null) {
            alert("Please select a secondaryDemandOrder to edit");
            return false;
        }
        return true;
    }
    function executeEditSecondaryDemandOrder() {
        var secondaryDemandOrderId = getSelectedSecondaryDemandOrderId();
        if (executePreConditionForEditSecondaryDemandOrder(secondaryDemandOrderId)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'secondaryDemandOrder', file:'edit')}?id=" + secondaryDemandOrderId,
                success: function (entity) {
                    executePostConditionForEditSecondaryDemandOrder(entity);
                },
                dataType: 'json'
            });
            SubmissionLoader.hideFrom();
        }
    }
    function executePostConditionForEditSecondaryDemandOrder(data) {
        if (data == null) {
            alert('Selected secondaryDemandOrder might have been deleted by someone');  //Message renderer
        } else {
            showSecondaryDemandOrder(data);
        }
    }
    function executePreConditionForDeleteSecondaryDemandOrder(secondaryDemandOrderId) {
        if (secondaryDemandOrderId == null) {
            alert("Please select a secondaryDemandOrder to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteSecondaryDemandOrder(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            clearAll();
        }
        MessageRenderer.render(data)
    }
    function showSecondaryDemandOrder(entity) {
        $('#gFormCashTroughBank input[name = id]').val(entity.id);
        $('#gFormCashTroughBank input[name = version]').val(entity.version);

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }
        if (entity.userOrderPlaced) {
            $('#userOrderPlaced').val(entity.userOrderPlaced.id).attr("selected", "selected");
        }
        else {
            $('#userOrderPlaced').val(entity.userOrderPlaced);
        }
        if (entity.userTentativeDelivery) {
            $('#userTentativeDelivery').val(entity.userTentativeDelivery.id).attr("selected", "selected");
        }
        else {
            $('#userTentativeDelivery').val(entity.userTentativeDelivery);
        }
        $('#orderNo').val(entity.orderNo);
        $('#dateOrder').val(entity.dateOrder);
        $('#dateDeliver').val(entity.dateDeliver);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#lastUpdated').val(entity.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchSecondaryDemandOrder(fieldName, fieldValue) {
        if (executePreConditionForSearchSecondaryDemandOrder(fieldName, fieldValue)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'secondaryDemandOrder', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchSecondaryDemandOrder(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
            SubmissionLoader.hideFrom();
        }
    }
    function executePreConditionForSearchSecondaryDemandOrder(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            clearAll();
            return false;
        }
        return true;
    }
    function executePostConditionForSearchSecondaryDemandOrder(data, fieldName, fieldValue) {
        if (data == null) {
            clearAll();
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showSecondaryDemandOrder(data);
        }
    }

    function loadDistributionPoint(enterpriseId) {
        var option = "<option value=''>Select DP/Factory</option>";
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'distributionPoint', file:'listDPByEnterpriseAndAppUser')}?enterpriseId=' + enterpriseId,
            dataType: 'json',
            success: function (data, textStatus) {
                $("#distributionPointList").empty();
                $("#distributionPointList").flexbox(data, {
                    watermark: "Select Distribution Point",
                    width: 260,
                    onSelect: function () {
                        $("#distributionPoint").val($('#distributionPointList_hidden').val());
                        // alert($('#distributionPointList_hidden').val())
                        loadCustomer();
                        loadCashPool();
                    }
                });
                $('#distributionPointList_input').blur(function () {
                    if ($('#distributionPointList_input').val() == '') {
                        $("#distributionPoint").val("");
                        clearCustomer();
                    }
                });

                var result = data.results;
                if (result.length == 1) {
//                    alert("dsds "+result[0].name);
                    //$("#distributionPointList").val(result[0].id);
                    //console.log(result[key]);
                    $("#distributionPointList").setValue(result[0].name);
                    $("#distributionPoint").val(result[0].id);
                    loadCustomer();
                    loadCashPool();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {

            }
        });
    }

    function clearCustomer() {
        $('#searchKey').val("");
        $("#customerMaster").val("");
        $('#name').val("");
        $('#code').val("");
        $('#totalReceivedAmount').val('0');
        $("#jqgrid-grid").clearGridData();
    }

    function loadCustomer() {
        $('#searchKey').html("");
        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                clearCustomer();
            }
        });

        $('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var distributionPointId = $("#distributionPoint").val();
                if (distributionPointId) {
                    var data = {searchKey: request.term, distributionPointId: distributionPointId};
                    var url = "${resource(dir:'customerPayment', file:'customerAutoComplete')}";
                    DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                        item['label'] = "[" + item['code'] + "] " + item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }

            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.name);
//                $('#name').val(ui.item.name);
//                $('#code').val(ui.item.code);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + "; Legacy Id: " + item.legacy_id + ";" + "Name: " + item.name + ";" + "Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }
    $('#search-btn-customer-register-id').click(function () {
        CustomerInfo.popupCustomerListPanel();
    });


    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var distributionPointId = $("#distributionPoint").val();
            if (distributionPointId) {
                var url = '${resource(dir:'customerPayment', file:'popupCustomerListPanel')}';
                var params = {distributionPointId: distributionPointId};
                DocuAjax.html(url, params, function (html) {
                    $.fancybox(html);
                });
            } else {
                MessageRenderer.renderErrorText("Select DP/Factory");
            }

        },
        setCustomerInformation: function (customerId, customerCoreInfo, code, name) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerId);
            $("#code").val(code);
            $("#name").val(name);
            loadReceivableAmount();
            loadGrid();
            loadSdGrid();
            CustomerInfo.customerCoreInfoId = customerId;
        }
    };

    function loadBankAccount() {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'customerPayment', file:'listAccount')}?entId=' + $("#enterpriseConfiguration").val(),
            dataType: 'json',
            success: function (data, textStatus) {
                var options = '<option value="">Select Bank Account</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.account_no + '</option>';

                });
                $("#bankAccount").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });
    }

    function loadReceivableAmount() {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'customerPayment', file:'totalReceivableAmount')}?cusCode=' + $("#code").val(),
            dataType: 'json',
            success: function (data) {
                $("#totalReceivedAmount").val(data[0].due_amount);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });

    }

    function loadCashPool() {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: "POST",
            url: '${resource(dir:'customerPayment', file:'listCashPool')}?id=' + $("#distributionPoint").val(),
            dataType: 'json',
            success: function (data, textStatus) {


                var options = '<option value="">Select CashPool</option>';
                $.each(data, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
                });
                $("#cashPool").html(options);
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
    }
    /*
     if (entity.userTentativeDelivery) {
     $('#userTentativeDelivery').val(entity.userTentativeDelivery.id).attr("selected", "selected");
     }
     else {
     $('#userTentativeDelivery').val(entity.userTentativeDelivery);
     }*/

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = this.defaultValue;
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();

    }
    function loadGrid() {
        $("#jqgrid-grid").setGridParam({
            url: '${resource(dir:'primaryDemandOrder', file:'listGridForCashThroughBank')}?customerMasterId=' + $('#customerMaster').val(),
            datatype: 'json'
        });
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function loadSdGrid() {
        $("#jqgrid-sd-grid").setGridParam({
            url: '${resource(dir:'customerPayment', file:'listSecurityDeposit')}?customerId=' + $('#customerMaster').val()
        });
        $("#jqgrid-sd-grid").trigger("reloadGrid");
    }

    //    function selectAllApplicant() {
    //        if ($('#select-button').val() == 'Select All') {
    //            var sum = 0;
    //            $('.print-checkbox').each(function () {
    //                this.checked = true;
    //                var approvalInfo = $(this).val();
    //                var appliedAmount = $("#jqgrid-grid").getCell(approvalInfo, 'applied_amount');
    //                sum += parseFloat(appliedAmount);
    //                var amount = Number($('#amount').val());
    //                $('#select-button').attr('value', 'Deselect All');
    //                if (sum > amount) {
    //                    MessageRenderer.render({
    //                        messageTitle: 'Can not add data',
    //                        type: 2,
    //                        messageBody: 'Total Applied amount is greater than Total received.'
    //                    });
    //                    $('.print-checkbox').attr('checked', false);
    //                    sum -= parseFloat(appliedAmount);
    //                    $('#select-button').attr('value', 'Select All');
    //
    ////                                $("#jqgrid-grid").setCell(approvalInfo, 'applied_amount', 0);
    ////                                sum = Number($("#totalAppliedAmount").val());
    //                }
    //            });
    //            $("#totalAppliedAmount").val(sum)
    //        } else {
    //            $('.print-checkbox').each(function () {
    //                this.checked = false;
    //                $('#select-button').attr('value', 'Select All');
    //                $("#totalAppliedAmount").val(sum)
    //            })
    //        }
    //    }

    function loadTotalAmount(id) {

        var sum = $("#jqgrid-grid").jqGrid('getCol', 'applied_amount', false, 'sum');
        sum = sum.toFixed(2);
        var amount = Number($('#amount').val()).toFixed(2);
//        alert("sum "+ sum +"  -amount-  "+ amount)
        if (sum > amount) {
            MessageRenderer.render({
                messageTitle: 'Payment Register!',
                type: 2,
                messageBody: 'Total Applied Amount is greater than Amount Received. Fix It.'
            });
//                                $("#jqgrid-grid").setCell(approvalInfo, 'applied_amount', 0);
//                                sum = Number($("#totalAppliedAmount").val());
        }
        $("#totalAppliedAmount").val(sum);
        $("#jqgrid-grid").jqGrid('footerData', 'set', {'applied_amount': $("#totalAppliedAmount").val()});
    }

    function loadBankDiv() {
        if ($("#paymentMode").val() == 'Cash') {
            $("#bank").hide();
            $("#cash").show();
        }
        else {
            $("#denomination").attr('checked', false);
            loadDataGrid();
            $("#bank").show();
            $("#cash").hide();
        }
    }
    function loadDataGrid() {
        $('.denomination').each(function () {
            if (this.checked) {
                $("#dataGrid").show()
            }
            else {
                $("#dataGrid").hide()
            }
        })
    }

    function clearAll() {
        $("#jqgrid-grid").clearGridData();
        $("#jqgrid-sd-grid").clearGridData();
        var entId = $('#idEnterprise').val();
        var factory = $('#isFactory').val();
        clear_form("#gFormCashTroughBank");
        $('#idEnterprise').val(entId);
        $('#enterpriseConfiguration').val(entId);
        $('#isFactory').val(factory);
        $('#paymentMode').val('Bank');
        $('#isSecurityDeposit').attr('checked', false);
//        $('#gridSecurityDeposit').hide();
        loadDataGrid();
        disableForm();
    }

    function disableForm() {
        if ($('#isSecurityDeposit').is(':checked')) {
            $('#securityDeposit').attr('hidden', true);
            $('#gridDiv').attr('hidden', true);
            $('#gridSecurityDeposit').attr('hidden', false);
        } else {
            $('#securityDeposit').attr('hidden', false);
            $('#gridDiv').attr('hidden', false);
            $('#gridSecurityDeposit').attr('hidden', true);
        }
    }

    function applyDue(amount) {
        if (!amount) {
            amount = 0;
        }
        var gridCollection = jQuery("#jqgrid-grid").jqGrid('getRowData');
        var remainder = parseFloat(amount);
//        remainder = remainder.toFixed(2);
        for (var i = 0; i < gridCollection.length; i++) {
            var due = parseFloat(gridCollection[i].due_amount);
//            due = due.toFixed(2);
            if (remainder <= due) {
                jQuery("#jqgrid-grid").jqGrid('setCell', gridCollection[i].id, 'applied_amount', remainder.toFixed(2));
            } else {
                jQuery("#jqgrid-grid").jqGrid('setCell', gridCollection[i].id, 'applied_amount', due.toFixed(2));
            }
            remainder = remainder - due;
//            remainder = remainder.toFixed(2);
            if (remainder <= 0) {
                remainder = 0;
            }
        }
        loadTotalAmount();
    }

</script>