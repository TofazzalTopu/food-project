<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var jqGridRetailInvoiceList = null;
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormCashCollect").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormCashCollect").validationEngine('attach');
        reset_formCashCollect("#gFormCashCollect");
        loadCustomerForCashCollect();
        jqGridRetailInvoiceList = $("#jqgrid-grid-retailInvoice").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'Invoice No',
                'Delivery Date',
                'Receivable Amount',
                'Amount To Be Received',
                'Remaining Receivable'
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'invoiceNo', index:'invoiceNo', width:120, align:'center'},
                {name:'invoiceDate', index:'invoiceDate', width:100, align:'center'},
                {name:'invoiceAmount', index:'invoiceAmount', width:130, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'amountToBeReceived', index:'amountToBeReceived', width:150, align:'center', editable:true,
                    edittype:"text", editoptions:{
                    value:'', readonly:true, class:'gridInputMargin',
                    size: 20, maxlength: 20,
                    dataInit: function(element) {
                        $(element).keypress(function(e){
                            if ((e.which != 46 || $(this).val().indexOf('.') != -1) &&
                                    ((e.which < 48 || e.which > 57) &&
                                    (e.which != 0 && e.which != 8))) {
                                e.preventDefault();
                            }

                            var text = $(this).val();

                            if ((text.indexOf('.') != -1) &&
                                    (text.substring(text.indexOf('.')).length > 2) &&
                                    (e.which != 0 && e.which != 8) &&
                                    ($(this)[0].selectionStart >= text.length - 2)) {
                                e.preventDefault();
                            }
                        });
                    },
                    dataEvents: [{
                        type: 'change, keyup',
                        fn: function (e) {
                            var array = e.target.id.split('_');
                            var rowId = array[0];
                            var rowData = $("#jqgrid-grid-retailInvoice").getRowData(rowId);
                            var totalReceived = Number($('#totalAmount').val());
                            var totalToBeReceived = 0.00;

                            $('.gridInputMargin ').each(function(){
                                totalToBeReceived += Number($(this).val());
                            });

                            if(totalToBeReceived > totalReceived){
                                $('#'+rowId+'_amountToBeReceived').val('');
                                $("#jqgrid-grid-retailInvoice").jqGrid('setCell', rowId, 'remainingReceivableAmount', 0.00);
                                MessageRenderer.renderErrorText("Insufficient Cash Received.");
                                return false;
                            }

                            if(Number(e.target.value) > Number(rowData.invoiceAmount)){
                                $('#'+rowId+'_amountToBeReceived').val(rowData.invoiceAmount);
                                $("#jqgrid-grid-retailInvoice").jqGrid('setCell', rowId, 'remainingReceivableAmount', 0.00);
                                MessageRenderer.renderErrorText("Entered amount cannot greater than Receivable Amount.");
                                return false;
                            }else{
                                $("#jqgrid-grid-retailInvoice").jqGrid('setCell', rowId, 'remainingReceivableAmount', Number(rowData.invoiceAmount) - Number(e.target.value));
                            }
                        }
                    }]
                }
                },
                {name:'remainingReceivableAmount', index:'remainingReceivableAmount', width:140, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Available Unadjusted Invoices",
            autowidth:false,
            height:true,
            scrollOffset:0,
            gridview:true,
            multiselect: true,
//            rownumbers: true,

            gridComplete: function() {
                $("#jqgrid-grid-retailInvoice_cb").html("&#10004;");
            },
            loadComplete: function (data) {
                var $this = $(this), ids = $this.jqGrid('getDataIDs'), i, l = ids.length;
                for (i = 0; i < l; i++) {
                    $this.jqGrid('editRow', ids[i], true);
                }
                $("#cashReceived").focus();
            },
            onSelectRow: function(rowid, status) {
                changeInvoiceSelection(rowid);
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText,'');
            }
        });
        $("#cashReceived").format({precision: 2, allow_negative: false, autofix: true});
        $("#confirmAmount").format({precision: 2, allow_negative: false, autofix: true});
    });

    function executePreConditionCashCollect() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormCashCollect").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }

    function executePostConditionCashCollect(result) {
        if (result.type == 1) {
            reset_formCashCollect('#gFormCashCollect');
        }
        MessageRenderer.render(result);
    }

    function reset_formCashCollect(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $("#jqgrid-grid-retailInvoice").jqGrid('clearGridData');
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function(){
            var url = '${resource(dir:'retailCashCollect', file:'popupCustomerListPanel')}' ;
            var params = {};
            DocuAjax.html(url, params, function(html){
                $.fancybox(html);
            });
        },
        setCustomerInformation: function(customerCoreInfoId, customerCoreInfo){
            $("#searchCustomerKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };

    function loadCustomerForCashCollect() {
        jQuery('#searchCustomerKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {query: request.term};
                var url = '${resource(dir:'retailCashCollect', file:'customerAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                loadCustomerNonPaidInvoice(ui.item.id);
                getCustomerBalance(ui.item.id);
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                $("#customerId").val(ui.item.id);
                $("#legacyId").val(ui.item.legacy_id);
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.present_address);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + "Legacy ID: " + item.legacy_id +", Code: " +item.code + ", Name: " + item.name + ", Status: " + item.status + ", Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };
    }

    $('#search-btn-customer-register-id').click(function(){
        CustomerInfo.popupCustomerListPanel();
    });

    function loadCustomerNonPaidInvoice(customerId){
        $("#jqgrid-grid-retailInvoice").jqGrid().setGridParam({url: "${resource(dir:'retailCashCollect', file:'listNonPaidRetailInvoice')}?"
                + 'customerId=' + customerId,
            datatype: "json"
        }).trigger("reloadGrid", [
                    {page: 1}
                ]);
    }
    function getCustomerBalance(customerId){
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: "${request.contextPath}/${params.controller}/getCustomerBalance",
            success: function (data, textStatus) {
                var cashReceived = Number($("#cashReceived").val());
                var cashAdvance = data.balance;
                $("#advance").val(cashAdvance.toFixed(2));
                var totalCash = cashReceived + cashAdvance;
                $("#totalAmount").val(totalCash.toFixed(2));
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form("#gFormSalesCommission");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            dataType: 'json'
        });
    }

    var tempCash = 0.00;
    function onBlurCashReceived(){
//        $('#jqgrid-grid-retailInvoice').jqGrid('resetSelection');
        $('#manualSelect').attr('checked',false);
        $('input[name="amountToBeReceived"]').val('');
        $('input[name="amountToBeReceived"]').attr('readonly',true);
//        $("#jqgrid-grid-retailInvoice tr td").attr('aria-describedby','jqgrid-grid-retailInvoice_remainingReceivableAmount').text(0.00);
        var cashReceived = Number($("#cashReceived").val());
        var cashAdvance = Number($("#advance").val());
        var totalCash = cashAdvance + cashReceived;
        $("#totalAmount").val(totalCash.toFixed(2));
        if(totalCash > 0.00){
            var gd = $("#jqgrid-grid-retailInvoice").jqGrid('getRowData');
            var length = gd.length;
            tempCash = totalCash;
            for (var i=0; i < length; ++i) {
                if(gd[i].invoiceAmount <= tempCash){
//                    $("#jqg_jqgrid-grid-retailInvoice").jqGrid('setSelection', gd[i].id, false);
                    $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr('checked', true);
                    $("#"+gd[i].id+"_amountToBeReceived").val(gd[i].invoiceAmount);
                    $("#jqgrid-grid-retailInvoice").jqGrid('setCell', gd[i].id, 'remainingReceivableAmount', 0.00);
//                    $("#"+gd[i].id+"_amountToBeReceived").removeAttr('disabled');
//                    $("#"+gd[i].id+"_amountToBeReceived").attr('readonly',true);
//                    $("#jqgrid-grid-retailInvoice").setSelection(gd[i].id, false);
//                    $("#jqgrid-grid-retailInvoice").jqGrid('setSelection', gd[i].id, false);
//                    $("#"+gd[i].id+" #jqg_"+gd[i].id,'#jqgrid-grid-retailInvoice').attr("readonly",true);
//                    $("#id"+" #jqg_"+id,'#Subscriber_Grid').attr("readonly",true);
//                    $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr("disabled", true);
                    tempCash -= gd[i].invoiceAmount;
                }else{
                    if(tempCash > 0.00){
//                        $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).click().attr('checked', true);
                        $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr('checked', true);
                        $("#"+gd[i].id+"_amountToBeReceived").val(tempCash.toFixed(2));
//                        $("#"+gd[i].id+"_amountToBeReceived").removeAttr('disabled');
//                        $("#"+gd[i].id+"_amountToBeReceived").attr('readonly',true);
//                        $("#jqgrid-grid-retailInvoice").jqGrid('setRowData', remainingReceivableAmount)
                        $("#jqgrid-grid-retailInvoice").jqGrid('setCell', gd[i].id, 'remainingReceivableAmount', gd[i].invoiceAmount - tempCash);
//                        $("#jqgrid-grid-retailInvoice").setSelection(gd[i].id, false);
//                        $("#jqgrid-grid-retailInvoice").jqGrid('setSelection', gd[i].id, false);
//                        $("#"+gd[i].id+" #jqg_"+gd[i].id,'#jqgrid-grid-retailInvoice').attr("readonly",true);
//                        $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr("disabled", true);
                        tempCash = 0.00;
                    }else{
                        $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr('checked', false);
                        $("#"+gd[i].id+"_amountToBeReceived").attr('readonly',true);
//                        $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr("disabled", true);
                        $("#jqgrid-grid-retailInvoice").jqGrid('setCell', gd[i].id, 'remainingReceivableAmount', 0.00);
                    }
//                    break;
                }
                $("#jqg_jqgrid-grid-retailInvoice_" + gd[i].id).attr("disabled", true);
            }
        }
    }

    function manualInvoiceSelect(object){
        if($('#'+object.id).is(':checked')){
            $('#jqgrid-grid-retailInvoice').jqGrid('resetSelection');
            $('.jqgrow input[type="checkbox"]').attr('checked', false);
            $('.jqgrow input[type="checkbox"]').removeAttr('disabled');
            $('input[name="amountToBeReceived"]').val('');
            $('input[name="amountToBeReceived"]').attr('readonly',true);

            var ids = $("#jqgrid-grid-retailInvoice").jqGrid('getDataIDs');
            for(key in ids){
                $("#jqgrid-grid-retailInvoice").jqGrid('setCell', ids[key], 'remainingReceivableAmount', 0.00);
            }
        }else{
            onBlurCashReceived();
        }
    }

    function changeInvoiceSelection(invoiceRowId){
        var totalCash = Number($("#totalAmount").val());
        var amountToBeReceived = Number($("#"+invoiceRowId+"_amountToBeReceived").val());
        if(totalCash == 0.00){
            MessageRenderer.renderErrorText("Insufficient Cash Received");
            $("#jqg_jqgrid-grid-retailInvoice_" + invoiceRowId).attr('checked', false);
            return false;
        }
        if($('#manualSelect').is(':checked') == false){
            if(amountToBeReceived > 0.00){
                $("#jqg_jqgrid-grid-retailInvoice_" + invoiceRowId).attr('checked', true);
            }else{
                $("#jqg_jqgrid-grid-retailInvoice_" + invoiceRowId).attr('checked', false);
            }
            return false;
        }else{
            $("#jqg_jqgrid-grid-retailInvoice_" + invoiceRowId).removeAttr("disabled");
        }

        if($("#jqg_jqgrid-grid-retailInvoice_" + invoiceRowId).is(':checked')){
            $("#"+invoiceRowId+"_amountToBeReceived").attr('readonly',false);
        }else{
            $("#"+invoiceRowId+"_amountToBeReceived").val('');
            $("#"+invoiceRowId+"_amountToBeReceived").attr('readonly',true);
            $("#jqgrid-grid-retailInvoice").jqGrid('setCell', invoiceRowId, 'remainingReceivableAmount', 0.00);
        }
    }


    function executePreConditionApplyCashCollection() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormCashCollect").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxApplyCashCollection() {
        if (!executePreConditionApplyCashCollection()) {
            return false;
        }

        var cashReceived = Number($("#cashReceived").val());
        var confirmAmount = Number($("#confirmAmount").val());
        var totalAmount = Number($("#totalAmount").val());

        if(cashReceived != confirmAmount){
            MessageRenderer.renderErrorText("Received Amount & Confirm Amount must be same");
            return false
        }

//        var data =  $("#gFormCashCollect").serializeArray();
        var cashReceived =  $("#cashReceived").val();
        var amountToBeReceived =  0.00;
        var data =  {}

        data['cashReceived'] =  cashReceived;
        data['confirmAmount'] =  $("#confirmAmount").val();
        data['customerId'] =  $("#customerId").val();

        var ids = $("#jqgrid-grid-retailInvoice").jqGrid('getDataIDs');
        var selectedInvoices = {};
        var i = 0;
        for(key in ids){
            if($("#jqg_jqgrid-grid-retailInvoice_" + ids[key]).is(':checked')){
                i++;

                if(Number($('#'+ids[key]+'_amountToBeReceived').val())<=0){
                    MessageRenderer.renderErrorText("Please enter 'Amount To Be Received' for the selected invoice.");
                    return false;
                    break;
                }

//                var rowData = $("#jqgrid-grid-retailInvoice").getRowData(ids[key]);
                data['invoiceList.invoice['+i+'].invoiceId'] = ids[key];
                data['invoiceList.invoice['+i+'].amountToBeReceived'] = $('#'+ids[key]+'_amountToBeReceived').val();
//                selectedInvoices['invoice['+i+'].amountToBeReceived = '] = rowData.amountToBeReceived;
                amountToBeReceived += Number($('#'+ids[key]+'_amountToBeReceived').val());
            }
        }

        if(i==0){
            MessageRenderer.renderErrorText("Please select at least one invoice.");
            return false;
        }

        if(amountToBeReceived > totalAmount){
            MessageRenderer.renderErrorText("'Total Amount To Be Received' cannot greater than 'Cash Received'.");
            return false;
        }

//        if(selectedInvoices.toString() != ""){
//            data.push({'name':'selectedInvoices', 'value': selectedInvoices});
//        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'POST',
            data: data,
            url: "${request.contextPath}/${params.controller}/applyCashCollection",
            success: function (data, textStatus) {
                executePostConditionApplyCashCollection(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-retailInvoice").jqGrid('clearGridData');
                    reset_formCashCollect('#gFormCashCollect');

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
    function executePostConditionApplyCashCollection(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-retailInvoice").jqGrid('clearGridData');
            reset_formCashCollect('#gFormCashCollect');
        }
        MessageRenderer.render(result);
    }
</script>