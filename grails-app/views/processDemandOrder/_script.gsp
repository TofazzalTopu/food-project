<script type="text/javascript" language="Javascript">
    var subAreaId = 1;
    $(document).ready(function () {
        loadCustomer();

        $('#orderNo').blur(function () {
            if ($('#orderNo').val() == '') {
                $("#id").val('');
            }
        });

        jQuery('#orderNo').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/listOrderForProcessAutoComplete?query=" + $('#orderNo').val();
                DocuAutoComplete.setSpinnerSelector('orderNo').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                $("#id").val(ui.item.id);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var orderDetails = "";
            orderDetails = '<div style="font-size: 9px; color:#326E93;">' + " Order: " + item.order_no + '</div>';
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + orderDetails).appendTo(ul);
        };

        $("#orderSelect").val("no");
        $('#delete-button').hide();
        $("#deliveryDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#timeTransaction').timepicker({showPeriod: true, showLeadingZero: true});
        $('#ui-widget-header-text').html('Finish Goods');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $("#gFormFinishGood").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

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
                'Ship To Address',
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
                {name: 'address', index: 'address', width: 80, align: 'center'},
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
        $("#jqgrid-grid-primaryOrder").jqGrid('navGrid', '#jqgrid-pager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
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
            rowNum: -1,
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
    })
    ;

    function loadCustomer() {
        $('#customerId').val('')
        $('#customerList').val('')
        jQuery('#customerList').autocomplete({
            minLength: '2',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                $('#popEmpDetails').html("");
                $('#customerId').val('')
                var data = {searchKey: request.term};
                var url = '${resource(dir:'customerMaster', file:'listCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['legacy_id'] + " [" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                EmployeeRegister.setEmployeeInformation(ui.item.id, ui.item.value);
//                EmployeeRegister.showEmployeeDetail();
                $('#name').val(ui.item.name);
//        CustomerRegister.showCustomerDetail(ui.item.account_no);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item.id) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Legacy ID: " + item.legacy_id + ", Code: " + item.code + ", Name: " + item.name + ",Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);

        }
    }

    var EmployeeRegister = {
        employeeCoreInfoId: null,
        /* popupEmployeeListPanel: function(){
         var url = "${request.contextPath}/${params.controller}/popupCustomerListPanel";
         var params = {unqId:"employeeRegisterId"};
         DocuAjax.html(url, params, function(html){
         $.fancybox(html);
         });
         },*/

        /* showEmployeeDetailsById: function(employeeCoreInfoId){
         EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId
         //            EmployeeRegister.showEmployeeDetail();
         EmployeeRegister.employeeCoreInfoId = null
         }
         ,*/
        showEmployeeDetail: function () {
            if (!EmployeeRegister.employeeCoreInfoId) {
                MessageRenderer.renderErrorText("Select an employee.", "Employee Detail Information");
                return false;
            }
            var url = "${request.contextPath}/${params.controller}/edit";
            var params = {id: EmployeeRegister.employeeCoreInfoId};
            AjaxLoader.showTo('popEmpDetails');
            DocuAjax.html(url, params, function (html) {
                $('#popEmpDetails').html(html);
            });
        },
        setEmployeeInformation: function (employeeCoreInfoId, employeeCoreInfo) {
            $("#customerId").val(employeeCoreInfoId)
            $("#employeeCoreInfo").val(employeeCoreInfo);
//            EmployeeRegister.employeeCoreInfoId = employeeCoreInfoId;
        }
    };


    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });
        var gridId = $("#jqgrid-grid-primaryOrder");
        for (var k = (subAreaId - 1); k >= 1; k--) {
            jQuery('#jqgrid-grid-primaryOrder').delRowData(k);
        }

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' input[name = select-button').attr('value', 'Select All')
    }

    function showHideInput() {
        if ($('#orderSelect').val() == 'date') {
            $('#deliveryDate').val('');
            $('#orderNo').val('');
            $('#dummy').hide();
            $('#deliveryDate').show();
            $('#orderNo').hide();
        }

        else if ($('#orderSelect').val() == 'number') {
            $('#deliveryDate').val('');
            $('#orderNo').val('');
            $('#dummy').hide();
            $('#deliveryDate').hide();
            $('#orderNo').show();
        }
        else {
            $('#dummy').show();
        }
    }
    function orderDetailsInfo() {
        if ($('#orderSelect').val() != 'no' && ($('#deliveryDate').val() || $('#orderNo').val())) {
            var customerId = $('#customerId').val() ? $('#customerId').val() : ''
            var legacyId = $('#legacyId').val() ? $('#legacyId').val() : ''
            var salesChannel = $('#salesChannel').val() ? $('#salesChannel').val() : ''
            var customerList = $('#customerList').val() ? $('#customerList').val() : ''
            var checked = false;
            if ($('#pendingOrder').is(':checked')) {
                checked = true
            }
            $("#jqgrid-grid-primaryOrder").setGridParam({
                url: '${resource(dir:'processDemandOrder', file:'showOrderDetails')}?deliveryDate=' + $('#deliveryDate').val() + '&salesChannel=' + salesChannel + '&orderNo=' + $('#orderNo').val() + '&checked=' + checked + '&customerId=' + customerId + '&legacyId=' + legacyId,
                datatype: "json"
            });
            $("#jqgrid-grid-primaryOrder").trigger("reloadGrid", [
                {page: 1}
            ]);
            $("#jqgrid-grid-item-available").jqGrid('clearGridData');
        }
        else {
            MessageRenderer.renderErrorText("Please select search type and input value");
        }
    }

    function checkItemQuantity() {
        var allIds = $("#jqgrid-grid-primaryOrder").jqGrid('getGridParam', 'selarrrow');

        if (allIds == "") {
            $("#jqgrid-grid-item-available").jqGrid('clearGridData');
            return false
        }
        var warehouseId = $("#warehouseId").val();
        if (!warehouseId) {
            MessageRenderer.renderErrorText("Factory Inventory is not available to process");
            return false
        }

        $("#jqgrid-grid-item-available").setGridParam({
            url: '${resource(dir:'processDemandOrder', file:'showItemAvailabilityDetails')}?ids=' + allIds.toString() + "&warehouseId=" + warehouseId,
            datatype: "json"
        });
        $("#jqgrid-grid-item-available").trigger("reloadGrid", [
            {page: 1}
        ]);
        /* //------ Calculate Total Amount
         var rowId =$("#jqgrid-grid-primaryOrder").jqGrid('getGridParam','selrow');
         var rowData = jQuery("#jqgrid-grid-primaryOrder").getRowData(rowId);
         var colData = rowData['totalAmout'];


         alert('$grid '+ rowId);
         // alert('$grid '+ rowData);
         //alert('$grid '+ colData);

         var allIdss = $("#jqgrid-grid-primaryOrder").jqGrid('getGridParam','selarrrow');
         // var allIdss = $("#jqgrid-grid-primaryOrder").jqGrid('getRowData');
         var lngth =allIdss.length;
         //   alert('lngth '+lngth)
         alert('allIdss '+allIdss)
         var colAmount;
         for (var i=0; i < lngth; ++i) {
         alert('allIdss '+allIdss[i])
         var rowData = jQuery("#jqgrid-grid-primaryOrder").getRowData(allIdss[i]);
         var dataFromCellByColumnName = jQuery('#jqgrid-grid-primaryOrder').jqGrid ('getCell', rowId, 'totalAmout');
         var colData = rowData['totalAmout'];
         colAmount = colAmount + colData
         alert('colAmount '+colAmount)
         alert('dataFromCellByColumnName  '+dataFromCellByColumnName)
         }

         var $grid = $('#customer-order-grid');
         var colSum = allIds.jqGrid('getCol', 'totalAmout', false, 'sum');

         //-------------
         */
        return true;
    }
    function UpdateDeliveryDate() {
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
            }); //end of dialog;
        } else {
            window.open('${createLink(uri: '/')}primaryDemandOrder/showUpdateDeliveryDate?ids=' + allIds);
        }
    }
    function processOrder() {
        var allIds = $("#jqgrid-grid-primaryOrder").jqGrid('getGridParam', 'selarrrow');
        if (allIds == "") {
            var msg = {
                "class": "com.docu.common.Message",
                "messageBody": ["Select Primary Oreder"],
                "messageTitle": "Message",
                "type": 0
            };
            MessageRenderer.render(msg);
            return false
        }
        var warehouseId = $("#warehouseId").val();
        if (!warehouseId) {
            MessageRenderer.renderErrorText("Factory Inventory is not available to process");
            return false
        }
//        var i = 0;
        var data = [];
        data.push({'name': 'orderIds', 'value': allIds.toString()});
        data.push({'name': 'warehouseId', 'value': warehouseId});
        var gd = $("#jqgrid-grid-item-available").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            if (gd[i].batch_qty != "") {
                var batchData = gd[i].hidden_qty;
                var batchDataList = batchData.split(",");
                var batchDataLength = batchDataList.length;
                for (var j = 0; j < batchDataLength; j++) {
                    data.push({'name': 'items.batchItems[' + i + '][' + j + '].productId', 'value': gd[i].id});
                    var batchId = "";
                    var batchQty = "";
                    var batchPart = batchDataList[j];
                    var batchPartList = batchPart.split("_");
                    var batchPartListLength = batchPartList.length;
                    if (batchPartListLength > 2) {
                        batchQty = batchPartList[batchPartListLength - 1];
                        batchId = batchPartList[batchPartListLength - 2];
                    }
                    data.push({'name': 'items.batchItems[' + i + '][' + j + '].batchId', 'value': batchId});
                    data.push({'name': 'items.batchItems[' + i + '][' + j + '].batchQty', 'value': batchQty});
                }
            }
        }
        data.push({'name': 'calculateBonus', 'value': $("#calculateBonus").val()});

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/processSelectedOrder",
            success: function (data, textStatus) {
                executePostConditionProcessOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    var checked = false;
                    if ($('#pendingOrder').is(':checked')) {
                        checked = true
                    }
                    $("#jqgrid-grid-primaryOrder").setGridParam({
                        url: '${resource(dir:'processDemandOrder', file:'showOrderDetails')}?deliveryDate=' + $('#deliveryDate').val() + '&orderNo=' + $('#orderNo').val() + '&checked=' + checked,
                        datatype: "json"
                    });
                    $("#jqgrid-grid-primaryOrder").trigger("reloadGrid");

                    $("#jqgrid-grid-item-available").jqGrid("clearGridData");

                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else {
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

    function executePostConditionProcessOrder(result) {
        var checked = false;
        if ($('#pendingOrder').is(':checked')) {
            checked = true
        }
        $("#jqgrid-grid-primaryOrder").setGridParam({
            url: '${resource(dir:'processDemandOrder', file:'showOrderDetails')}?deliveryDate=' + $('#deliveryDate').val() + '&orderNo=' + $('#orderNo').val() + '&checked=' + checked,
            datatype: "json"
        });
        $("#jqgrid-grid-primaryOrder").trigger("reloadGrid");

        $("#jqgrid-grid-item-available").jqGrid("clearGridData");
        MessageRenderer.render(result);
    }


    function updateDemandOrderPopup(orderId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'processDemandOrder', file:'updateDemandOrderPopup')}?id=" + orderId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (html) {
                $.fancybox(html);
//                $("#fancy-update-order-display-block").html(html);
//                $("#fancy-update-order-div").fancybox({
//                    'transitionIn': 'elastic',
//                    'transitionOut': 'elastic',
//                    'speedIn': 600,
//                    'speedOut': 200,
//                    'overlayShow': false
//                }).trigger('click')

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                $("#loader_icon").hide();
            },
            dataType: 'html'
        });
    }

    function batchWiseOrderAllocation(productId, orderIds) {
        var warehouseId = $("#warehouseId").val();
        if (!warehouseId) {
            MessageRenderer.renderErrorText("Factory Inventory is not available to process");
            return false
        }
        $.ajax({
            type: "POST",
            url: "${resource(dir:'processDemandOrder', file:'productBatchPopup')}?id=" + productId + "&orderIds=" + orderIds + "&warehouseId=" + warehouseId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (html) {
                $.fancybox(html);
//                $("#fancy-product-batch-display-block").html(html)
//                $("#fancy-product-batch-div").fancybox({
//                    'transitionIn': 'elastic',
//                    'transitionOut': 'elastic',
//                    'speedIn': 600,
//                    'speedOut': 200,
//                    'overlayShow': false
//                }).trigger('click')
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                $("#loader_icon").hide();
            },
            dataType: 'html'
        });
    }

    function setId(id) {
        $('#enterpriseConfiguration').val(id);
    }

    function calculateChange() {
        if (document.getElementById('calculateBonus').checked) {
            $("#calculateBonus").val('true');
        } else {
            $("#calculateBonus").val('false');
        }
    }
</script>