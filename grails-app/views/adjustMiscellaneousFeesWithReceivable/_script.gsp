<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#subInventoryToSubInventoryTransferForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#subInventoryToSubInventoryTransferForm").validationEngine('attach');

        $('#btn-withdraw').hide();
        $('#div-withdraw').hide();

        jQuery('#customerName').autocomplete({
            minLength:'2',
            source:function (request, response) {
                $('#popEmpDetails').html("");
                var data = {searchKey:request.term};
                var url = '${resource(dir:'adjustMiscellaneousFeesWithReceivable', file:'getAllCustomerList')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $('#customerId').val(ui.item.id);
                $('#customerCode').val(ui.item.code);
                $('#customerAddress').val(ui.item.present_address);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item.id) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Legacy ID: " + item.legacy_id + ", Code: " +item.code+", Name: "+item.name+",Address: "+item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        };

//        Customer List
        $("#jqgrid-customer-grid").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'ID',
                'Customer Name',
                'Customer ID',
                'Receivable Amount',
                'Applied Amount',
                'Due Amount',
            ],
            colModel: [
                {name: 'customerId', index: 'customerId', width: 30, align: 'left', hidden:false},
                {name: 'customerName', index: 'customerName', width: 120, align: 'left'},
                {name: 'customerCode', index: 'customerCode', width: 120, align: 'left'},
                {name: 'receivableAmount', index: 'receivableAmount', width: 125, align: 'left'},
                {name: 'appliedAmount', index: 'appliedAmount', width: 120, align: 'left'},
                {name: 'dueAmount', index: 'dueAmount', width: 110, align: 'left'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-customer-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Available Customer Info",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#tb-customer-grid").jqGrid('navGrid', '#jqgrid-customer-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });

        $('#amount').keypress(function(event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });
    });

    function searchCustomer(){
        $('#btn-withdraw').hide();
        $('#div-withdraw').hide();

        var customerId = $('#customerId').val();
        jQuery("#jqgrid-customer-grid").clearGridData();

        if(customerId){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {customerId:customerId},
                url:  "${request.contextPath}/${params.controller}/getCustomerInfoByCustomerId",
                success: function (data, textStatus) {
                    if(data[0]){
                        jQuery("#jqgrid-customer-grid")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
                    }else{
                        MessageRenderer.render({
                            "messageBody": "Customer receivable is not found.",
                            "messageTitle": "Adjust Miscellaneous Fees",
                            "type": "0"
                        });
                        return false;
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#jqgrid-customer-grid")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
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
        }

    }
    var withdrawDueAmount = 0;
    var previousReceivableAmount = 0;
    function adjustAgainstReceivable(){
        var allData = {};
        if(!preConditionAdjustMiscellaneousFeesWithReceivable()){
            return false;
        }
        var rowId = $("#jqgrid-customer-grid").jqGrid('getDataIDs');

        if(rowId == ''){
            MessageRenderer.render({
                "messageBody": "Customer receivable is not found.",
                "messageTitle": "Adjust Miscellaneous Fees",
                "type": "0"
            });
            return false;
        }

        if(previousReceivableAmount>0){
            $("#jqgrid-customer-grid").jqGrid("setCell", rowId, "receivableAmount", previousReceivableAmount);
        }
        var receivableAmount = parseFloat($("#jqgrid-customer-grid").jqGrid('getCell',rowId,'receivableAmount'));
        var amount = parseFloat($('#amount').val());
        var dueAmount = receivableAmount - amount;
            previousReceivableAmount = dueAmount;
        $("#jqgrid-customer-grid").jqGrid("setCell", rowId, "appliedAmount", amount);
        $("#jqgrid-customer-grid").jqGrid("setCell", rowId, "dueAmount", dueAmount);

        if(dueAmount<0){
            withdrawDueAmount = dueAmount * (-1);
            $('#btn-withdraw').fadeIn();
        }else{
            $('#btn-withdraw').hide();
            $('#div-withdraw').hide();
        }

        allData['customerMaster.id'] = $('#customerId').val();
        allData['expenseType'] = $('#expenseType').val();
        allData['receivableAmount'] = receivableAmount;
        allData['appliedAmount'] = amount;
        allData['dueAmount'] = dueAmount;

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url:  "${request.contextPath}/${params.controller}/adjust",
            success: function (data, textStatus) {
                MessageRenderer.render(data.message);
                $('#amfwrId').val(data.amfwrId);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==0){
                    $("#jqgrid-customer-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
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

    function submitWithdraw(){
        var paymentType = $('input[name=paymentType]').is(':checked');
        var withdrawalAmount = parseFloat($('#withdrawalAmount').val());
        if(!preConditionSubmitWithdraw()){
            return false;
        }

        if(withdrawalAmount > withdrawDueAmount){
            MessageRenderer.render({
                "messageBody": "Withdrawal Amount cannot greater that Due Amount.",
                "messageTitle": "Withdraw Amount",
                "type": "0"
            });
            return false;
        }

        if(!paymentType){
            MessageRenderer.render({
                "messageBody": "Please select a payment method.",
                "messageTitle": "Withdraw Amount",
                "type": "0"
            });
            return false;
        }

        var data = $('#adjustMiscellaneousFeesWithdrawForm').serialize();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url:  "${request.contextPath}/${params.controller}/withdraw",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                reset_form_data("#adjustMiscellaneousFeesWithReceivableForm");
                reset_form_data("#adjustMiscellaneousFeesWithdrawForm");
                jQuery("#jqgrid-customer-grid").clearGridData();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==0){
                    $("#jqgrid-customer-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
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

    function withdraw(){
        $('#div-withdraw').fadeIn();
    }

    function preConditionAdjustMiscellaneousFeesWithReceivable(){
        if(!$("#adjustMiscellaneousFeesWithReceivableForm").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function preConditionSubmitWithdraw(){
        if(!$("#adjustMiscellaneousFeesWithdrawForm").validationEngine('validate')){
            return false;
        }
        return true;
    }

//    extra
    function transferProduct(){
        if(!preConditionAdjustMiscellaneousFeesWithReceivable()){
            return false;
        }

        var allData = {};

        allData["inventoryId"] = $("#inventory").val();
        allData["transferDate"] = $("#transferDate").val();
        allData["subInventoryFromId"] = $("#subInventoryFrom").val();
        allData["subInventoryToId"] = $("#subInventoryTo").val();

        var selectedProductIds = $('#jqgrid-product-grid').jqGrid('getGridParam', 'selarrrow');
        var productIds = filterListOfIds(selectedProductIds);


        for(var i = 0; i < productIds.length; i++){
            var transferQty = $('#'+productIds[i]+'_transferQty').val();

            allData["products.product["+ i +"].productId"] = $("#jqgrid-product-grid").getRowData(productIds[i]).productId;
            allData["products.product["+ i +"].fgwdId"] = $("#jqgrid-product-grid").getRowData(productIds[i]).fgwdId;
            allData["products.product["+ i +"].batchNo"] = $("#jqgrid-product-grid").getRowData(productIds[i]).batch_no;
            allData["products.product["+ i +"].unitPrice"] = $("#jqgrid-product-grid").getRowData(productIds[i]).price;
            allData["products.product["+ i +"].transferQty"] = transferQty;
        }

        if(productIds == ''){
            MessageRenderer.render({
                "messageBody": "Please select at least one product from the list.",
                "messageTitle": "Sub Inventory to Sub Inventory Transfer",
                "type": "0"
            });
            return false;
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url:  "${request.contextPath}/${params.controller}/transferProduct",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                reset_form_data("#subInventoryToSubInventoryTransferForm");
                jQuery("#jqgrid-product-grid").clearGridData();
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status==0){
                    $("#jqgrid-customer-grid")
                            .jqGrid('setGridParam',
                            {
                                datatype: 'local',
                                data: data
                            })
                            .trigger("reloadGrid");
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom()
            },
            dataType: 'json'
        });
    }

    function filterListOfIds(idsArray){
        var ids = [];
        $.each(idsArray, function(i, e) {
            if ($.inArray(e, ids) == -1) ids.push(e);
        });
        ids = ids.filter(function(n){ return n != undefined });
        return ids;
    }

    function reset_form_data(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = "";
        });
        $(formName+' select').val('');
    }

    $('#search-btn-customer-id').click(function () {
        CustomerInfo.popupCustomerListPanel();
    });

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var url = '${resource(dir:'adjustMiscellaneousFeesWithReceivable', file:'getAllCustomerList')}';
            var params = {query: ''};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, address) {
            SubmissionLoader.showTo();
            $("#customerName").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            $('#customerCode').val(code);
            $('#customerAddress').val(address);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>