<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">

    var hoDeposit = true;

    $(document).ready(function () {
        $('#ui-widget-header-text').html('Deposit Cash To Deposit Pool');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        loadDp();
       /* var dpID=document.getElementById('distributionPoint').value
        alert('dpID '+dpID)*/
       // setDefaultCustomer();

        $("#frmDepositCashToDepositPool").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#frmDepositCashToDepositPool").validationEngine('attach');
//        reset_depositPool_form("#frmDepositCashToDepositPool");
        $("#depositToBankAccount").format({precision: 2, allow_negative: false, autofix: true});
        $("#depositToHoCash").format({precision: 2, allow_negative: false, autofix: true});
        $("#salesAmount").format({precision: 2, allow_negative: false, autofix: true});
        $("#sdAmount").format({precision: 2, allow_negative: false, autofix: true});
        $("#total").format({precision: 2, allow_negative: false, autofix: true});

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
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
                var sum = 0;
                $('.print-checkbox').each(function () {
                    if (this.checked) {
                        var approvalInfo = $(this).val();
                        var appliedAmount = $("#jqgrid-data-grid").getCell(approvalInfo, 'applied_amount');
                        sum += parseFloat(appliedAmount);
                    }
                });
                $("#totalAppliedAmount").val(sum);
                var ids = $("#jqgrid-data-grid").jqGrid('getDataIDs');
                allEmail = '';
                allMobile = '';
                for (key in ids) {
                    var id = $("#jqgrid-data-grid").getCell(ids[key], 'id');
                    $("#jqgrid-data-grid").jqGrid('setRowData', ids[key], {edit: '<input type="checkbox" id="print-checkbox' + id + '" class="print-checkbox" value="' + id + '" onclick="javascript:loadTotalAmount(' + id + ')"/>'});
                }
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

        setPreviousDatePicker('date');
        $('#date').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        if ('${list.size()}' == '1') {
            loadDp();
            loadDepositPool();
        }
    });

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
                    var sum = 0;
                    $('.print-checkbox').each(function () {
                        if (this.checked) {
                            var approvalInfo = $(this).val();
                            var appliedAmount = $("#jqgrid-grid").getCell(approvalInfo, 'applied_amount');
                            sum += parseFloat(appliedAmount);
                            var amount = Number($('#amount').val());
                            if (sum > amount) {
                                MessageRenderer.render({
                                    messageTitle: 'Can not add data',
                                    type: 2,
                                    messageBody: 'Total Applied amount is greater than Total received.'
                                });
                                $('.print-checkbox').attr('checked', false);
                                sum -= parseFloat(appliedAmount);
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
            var qty = qtyText.value;
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
                        sum += parseFloat(qtyAmount * amount);
                        var recAmount = Number($("#amount").val());
                        if (sum > recAmount) {
                            MessageRenderer.render({
                                messageTitle: 'Can not add data',
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

    function checkForValue(elem) {
        if (!elem) {
            return [false, "Please enter marks."];
        }
        else {
            return [true, ""];
        }
    }

    function loadDp() {
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadDp')}?entId=" + $("#enterpriseConfiguration").val(),
            success: function (data, textStatus) {
                //alert('fd')
                var option ='';
                if(data.length ==1){
                    //alert('one')
                    option= '<option value="' + data[0].id + '" name="' + data[0].customer + '">' + data[0].name + '</option>';
                }
                else{
                    //alert('more than one')
                    var  option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '" name="' + data[i].customer + '">' + data[i].name + '</option>';
                    }
//                    $("#distributionPoint").html(option);
                }

                $("#distributionPoint").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                var dpID=document.getElementById('distributionPoint').value
                if(dpID){
                    setDefaultCustomer();
                }

            },
            dataType: 'json'
        });
    }

    function loadDepositPool() {

        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadDepositPool')}?entId=" + $("#enterpriseConfiguration").val(),
            success: function (data, textStatus) {
                var option ='';
                if(data.length ==1){
                    //alert('one')
                    option = '<option value="' + data[0].id + '">' + data[0].name + '</option>';
                }
                else {
                     option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }
                }
                $("#depositPool").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function setDefaultCustomer() {
//        alert(hoDeposit);
        $("#defaultCustomer").val($("#distributionPoint").find(':selected').attr('name'));
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadCashPool')}?dpId=" + $("#distributionPoint").val(),
            success: function (data, textStatus) {
                var option ='';
                if(data.length == 1){
                    option = '<option value="' + data[0].id + '" name="' + data[0].amount + '">' + data[0].name + '</option>';
                }else{
                    option = '<option value="">Please Select</option>';
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '" name="' + data[i].amount + '">' + data[i].name + '</option>';
                    }
                }

                $("#cashPool").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                var cashPoolId = $("#distributionPoint").val();
                if(cashPoolId) {
                    loadCashPoolBalance(cashPoolId);
                }
            },
            dataType: 'json'
        });
    }

    function loadCashPoolBalance(cashPoolId){
//        alert($('input[name=pm]:checked').val());
//        var depositType = $('input[name=pm]:checked').val();
        $("#availableCash").val("0");
        if(cashPoolId){
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'customerPayment', file:'fetchCashPoolBalanceForCashInHandFromCOA')}?dpId=" + cashPoolId,
                success: function (data, textStatus) {
                    $("#availableCash").val(data[0].amountBalance);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                },
                dataType: 'json'
            });
        }
    }

    function showHo(key) {
        if (key == 1) {
            $("#hoDiv").show();
            $("#vaultDiv").hide();
            $("#denomination").attr('checked',false);
            $("#depositPool").val('');
            hoDeposit = true;
        } else {
            $("#hoDiv").hide();
            $("#vaultDiv").show();
            hoDeposit = false;
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

    function executePreCondition() {
        trim_form();
        if (!$("#frmDepositCashToDepositPool").validationEngine('validate')) {
            return false;
        }
       return true;
    }

    function executeAjax() {
        if (!executePreCondition()) {
            return false;
        }
        var totalAmount = Number($("#total").val());
        var availableCash = Number($("#availableCash").val());

        var msg = {
            "class": "com.docu.common.Message",
            "messageBody": ["Mandatory Field"],
            "messageTitle": "Message",
            "type": 2
        };

        if(totalAmount <= 0){
            msg.messageBody = "Deposit Amount cant be less or equal to zero";
            MessageRenderer.render(msg);
            return false
        }

        if(availableCash == 0){
            msg.messageBody = "No Available Cash";
            MessageRenderer.render(msg);
            return false
        }

        if(totalAmount > availableCash){
            msg.messageBody = "Deposit Amount cannot greater than Available Cash";
            MessageRenderer.render(msg);
            return false
        }
        if(hoDeposit){
            var depositToBankAccount = Number($("#depositToBankAccount").val());
            var depositToHoCash = Number($("#depositToHoCash").val());

            if((depositToBankAccount + depositToHoCash) > availableCash){
                msg.messageBody = "Sum of Deposit to Bank and Deposit to HO Amount cannot greater than Available Cash";
                MessageRenderer.render(msg);
                return false
            }

            var salesAmount = Number($("#salesAmount").val());
            var sdAmount = Number($("#sdAmount").val());

            if((salesAmount + sdAmount) > availableCash){
                msg.messageBody = "Sum of Sales Amount and SD Amount cannot greater than Available Cash";
                MessageRenderer.render(msg);
                return false
            }

            if((depositToBankAccount + depositToHoCash) != (salesAmount + sdAmount)){
                msg.messageBody = "Sum of Deposit to Bank and Deposit to HO Amount is not equal to Sum of Sales Amount and SD Amount";
                MessageRenderer.render(msg);
                return false
            }

            if((depositToBankAccount + depositToHoCash) != totalAmount){
                msg.messageBody = "Sum of Deposit to Bank and Deposit to HO Amount is not equal to Deposit Amount";
                MessageRenderer.render(msg);
                return false
            }

            if((salesAmount + sdAmount) != totalAmount){
                msg.messageBody = "Sum of Sales Amount and SD Amount is not equal to Deposit Amount";
                MessageRenderer.render(msg);
                return false
            }

        }

        var data = $("#frmDepositCashToDepositPool").serializeArray();
        if (document.getElementById('denomination').checked) {
            var denominationData = jQuery("#jqgrid-data-grid").jqGrid('getRowData');
            var dataLength = denominationData.length;
            for (var k = 0; k < dataLength; k++) {
                if(denominationData[k].qty != '0') {
                    data.push({
                        'name': 'items.depositCashCurrencyDenomination[' + k + '].quantity',
                        'value': denominationData[k].qty
                    });
                    data.push({
                        'name': 'items.depositCashCurrencyDenomination[' + k + '].currencyDemonstration.id',
                        'value': denominationData[k].id
                    });
                }
            }
        }
        data.push({'name': 'hoDeposit', 'value': hoDeposit});

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${resource(dir:'customerPayment', file:'createDepositCashToDepositPool')}",
            success: function (data, textStatus) {
                executePostCondition(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
        return false;
    }

    function executePostCondition(result) {
        if (result.type == 1) {
            $("#cashPool").html('');
            var ent = $("#enterpriseConfiguration").val();
            clear_form('#frmDepositCashToDepositPool');
            $("#enterpriseConfiguration").val(ent);
            loadDp();
//            $("#ho").attr('checked', 'checked');
//            showHo(1);
            $("#depositToBankAccount").val("");
            $("#depositToHoCash").val("");
            $("#salesAmount").val("");
            $("#sdAmount").val("");
        }
        MessageRenderer.render(result);
    }

    function isNumberKey(event) {
        var charCode = (event.which) ? event.which : event.keyCode;
        if (charCode != 46 && charCode > 31
                && (charCode < 48 || charCode > 57))
            return false;

        return true;
    }
</script>