<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCashReceivedFromBranch").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashReceivedFromBranch").validationEngine('attach');
//        reset_form("#gFormCashReceivedFromBranch");

        $("#jqgrid-grid-unAdjusted").jqGrid({
            %{--url: '${resource(dir:'mushak', file:'list')}',--}%
            datatype: "local",
            colNames: [
                'ID',
                'Select',
                'Invoice No',
                'Invoice Date',
                'Invoice Amount',
                'Due Amount',
                'Adjust Amount',
                'Selected'
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {
                    name: 'select', width: 50, align: 'center',
                    formatter: cboxFormatter, formatoptions: {disabled: false},
                    edittype: "checkbox", editoptions: {value: "true:false", defaultValue: "false"},
                    stype: "select", searchoptions: {
                    sopt: ["eq", "ne"],
                    value: ":Any;true:true;false:false"
                }
                },
                {name: 'invoiceNo', index: 'invoiceNo', width: 0, align: 'left'},
                {name: 'invoiceDate', index: 'invoiceDate', width: 0, align: 'center'},
                {name: 'invoiceAmount', index: 'invoiceAmount', width: 0, align: 'right', sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'dueAmount', index: 'dueAmount', width: 0, align: 'right', sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}},
                {
                    name: 'adjustAmount', index: 'adjustAmount', width: 0, align: 'right',
                    sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'selected', index: 'selected', width: 0, align: 'left', hidden: true}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30],
//            pager: '#jqgrid-pager-unAdjusted',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Un Adjusted Invoices",
            autowidth: true,
            autoheight: true,
            scrollOffset: 50,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            afterSaveCell: function (rowid) {
                var due = parseFloat($("#jqgrid-grid-unAdjusted").jqGrid('getCell', rowid, 'dueAmount'));
                var applied = parseFloat($("#jqgrid-grid-unAdjusted").jqGrid('getCell', rowid, 'adjustAmount'));
                if(applied > due){
                    MessageRenderer.render({
                        messageTitle: 'Data Error',
                        type: 2,
                        messageBody: 'Adjusted amount can cot be greater than due amount.'
                    });
                    $("#jqgrid-grid-unAdjusted").jqGrid('setCell', rowid, 'adjustAmount', 0);
                }
                loadTotalAmount();
            },
            loadComplete: function () {
//                var totalAdjusted = $("#jqgrid-grid-unAdjusted").jqGrid('getCol', 'adjustAmount', false, 'sum')
//                $("#totalAdjusted").val(totalAdjusted.toFixed(2));
                $('#totalAdjusted').val('0.00');
            },
            onSelectRow: function (rowid, status) {
            }
        });

        setPreviousDatePicker('date');
        $('#date').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        loadDp();
        populateCustomer();
    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<input type="checkbox" ' + (cellvalue == 'true' ? ' checked="checked"' : '') +
                'value="false" onchange="if(this.checked){changeValue(' + options.rowId + ',true)}else{changeValue(' + options.rowId + ',false)};"/>';
    }

    function changeValue(id, val) {
        $("#jqgrid-grid-unAdjusted").jqGrid('setCell', id, 'selected', val);
        loadTotalAmount();
//        var amount = parseFloat($("#jqgrid-grid-unAdjusted").jqGrid('getCell', id, 'adjustAmount'));
//        var totalAdjusted = parseFloat($("#totalAdjusted").val()) + amount;
//        $("#totalAdjusted").val(totalAdjusted.toFixed(2));
    }

    function subValue(id, val) {
        $("#jqgrid-grid-unAdjusted").jqGrid('setCell', id, 'selected', val);
        var amount = parseFloat($("#jqgrid-grid-unAdjusted").jqGrid('getCell', id, 'adjustAmount'));
        var totalAdjusted = parseFloat($("#totalAdjusted").val()) - amount;
        $("#totalAdjusted").val(totalAdjusted.toFixed(2));
    }

    function loadDp() {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: "${resource(dir:'customerPayment', file:'loadDp')}?entId=" + $("#enterpriseConfiguration").val(),
            success: function (data, textStatus) {
                var option = '<option value="">Please Select</option>';
                for (var i = 0; i < data.length; i++) {
                    option += '<option value="' + data[i].id + '" name="' + data[i].customer + '">' + data[i].name + '</option>';
                }
                $("#distributionPoint").html(option);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function setDefaultCustomer() {
        $("#searchKey").val('');
        $("#customerMaster").val('');
        $("#code").val('');
        $("#commission").val('');
        $("#availableCommission").val('');
        $("#jqgrid-grid-unAdjusted").jqGrid('clearGridData');
        $("#defaultCustomer").val($("#distributionPoint").find(':selected').attr('name'));
    }

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($("#distributionPoint").val() == '') {
                    return false;
                }
                var data = {searchKey: request.term};
                var url = '${resource(dir:'processMarketReturn', file:'fetchCustomer')}?dpId=' + $("#distributionPoint").val();
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.category);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + ", Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };

        $('#search-btn-customer-id').click(function () {
            CustomerInfo.popupCustomerListPanel();
        });

    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            if ($("#distributionPoint").val() == '') {
                return false;
            }
            var url = '${resource(dir:'processMarketReturn', file:'popupCustomerListPanel')}?dpId=' + $("#distributionPoint").val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerMaster").val(customerCoreInfoId);
            $("#code").val(code);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                url: "${resource(dir:'salesCommission', file:'fetchCommission')}?id=" + customerCoreInfoId + "&date=" + $("#date").val(),
                success: function (data, textStatus) {
                    $("#commission").val(data);
                    $("#availableCommission").val(data.toFixed(2));
                    applyDue($('#availableCommission').val());
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
            $("#jqgrid-grid-unAdjusted").setGridParam({
                url: '${resource(dir:'writeOff', file:'listInvoice')}?customerId=' + $("#customerMaster").val(),
                datatype: 'json'
            });
            $("#jqgrid-grid-unAdjusted").trigger("reloadGrid");
        }
    };

    function executeAjaxAdjust() {
        if ($("#totalAdjusted").val() == '' || $("#totalAdjusted").val() == '0') {
            MessageRenderer.render({
                messageTitle: 'Adjustment',
                type: 2,
                messageBody: 'Please select Invoice(s) to adjust.'
            });
            return false;
        }
        if (parseFloat($("#totalAdjusted").val()) > parseFloat($("#availableCommission").val())) {
            MessageRenderer.render({
                messageTitle: 'Adjustment',
                type: 2,
                messageBody: 'Total Adjusted Amount can not be greater than Available Commission.'
            });
            return false;
        }
//        if(parseFloat($("#totalAdjusted").val()) > parseFloat($("#availableCommission").val())){
//            MessageRenderer.render({
//                messageTitle: 'Adjustment',
//                type: 2,
//                messageBody: 'Total Adjusted Amount can not be greater than Available Commission.'
//            });
//            return false;
//        }

        $("#commission").val(parseFloat($("#availableCommission").val()) - parseFloat($("#totalAdjusted").val()));
        var data = $("#gFormAdjustCommission").serializeArray();
        var gd = $("#jqgrid-grid-unAdjusted").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            if (gd[i].selected == 'true') {
                data.push({'name': 'items.invoice[' + i + '].id', 'value': gd[i].id});
                data.push({
                    'name': 'items.invoice[' + i + '].paidAmount',
                    'value': parseFloat(gd[i].invoiceAmount) - parseFloat(gd[i].dueAmount) + parseFloat(gd[i].adjustAmount)
                });

                data.push({'name': 'details.adjustSalesCommissionDetails[' + i + '].invoice.id', 'value': gd[i].id});
                data.push({'name': 'details.adjustSalesCommissionDetails[' + i + '].adjustedAmount', 'value': parseFloat(gd[i].adjustAmount)});
            }
        }

        var actionUrl = "${request.contextPath}/${params.controller}/adjustSalesCommission";

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostCondition(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-unAdjusted").jqGrid('clearGridData');
                    var ent = $("#enterpriseConfiguration").val();
                    reset_form('#gFormAdjustCommission');
                    $("#enterpriseConfiguration").val(ent);

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
        return false;
    }

    function executePostCondition(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-unAdjusted").jqGrid('clearGridData');
            var ent = $("#enterpriseConfiguration").val();
            reset_form('#gFormAdjustCommission');
            $("#enterpriseConfiguration").val(ent);
        }
        MessageRenderer.render(result);
    }

    function applyDue(amount) {
        if(!amount){
            amount = 0;
        }
//        $("#appliedAmount").val(amount);
        var gridCollection = jQuery("#jqgrid-grid-unAdjusted").jqGrid('getRowData');
        var remainder = parseFloat(amount);
        for (var i = 0; i < gridCollection.length; i++) {
            var due = parseFloat(gridCollection[i].dueAmount);
            if(remainder <= due){
                jQuery("#jqgrid-grid-unAdjusted").jqGrid('setCell', gridCollection[i].id, 'adjustAmount', remainder.toFixed(2));
            }else{
                jQuery("#jqgrid-grid-unAdjusted").jqGrid('setCell', gridCollection[i].id, 'adjustAmount', due.toFixed(2));
            }
            remainder = remainder - due;
            if(remainder <= 0){
                remainder = 0;
            }
        }
        loadTotalAmount();
    }

    function loadTotalAmount() {
        var sum = 0;
        var avail = parseFloat($('#availableCommission').val());
        var deselect = -1;
        var gridData = $("#jqgrid-grid-unAdjusted").jqGrid('getRowData');
        for(var i = 0; i < gridData.length; i++){
            if(gridData[i].selected == 'true'){
                sum = sum + parseFloat(gridData[i].adjustAmount);
            }
            if(sum > avail){
                MessageRenderer.render({
                    messageTitle: 'Can not add data',
                    type: 2,
                    messageBody: 'Total Adjusted amount becomes greater than Total received amount.'
                });
                deselect = i;
                break;
            }
        }
        if(deselect != -1){
            sum = sum - parseFloat(jQuery("#jqgrid-grid-unAdjusted").jqGrid('getCell', gridData[deselect].id, 'adjustAmount'));
            $('#totalAdjusted').val(sum);
            while(deselect < gridData.length){
//                document.getElementById("chkbox" + gridData[deselect].id).checked = false;
                jQuery("#jqgrid-grid-unAdjusted").jqGrid('setCell', gridData[deselect].id, 'select', 'false');
                jQuery("#jqgrid-grid-unAdjusted").jqGrid('setCell', gridData[deselect].id, 'selected', 'false');
                deselect ++;
            }
        }else{
            $('#totalAdjusted').val(sum);
        }
    }

</script>