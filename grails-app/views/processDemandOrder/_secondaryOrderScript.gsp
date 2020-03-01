<script type="text/javascript" language="Javascript">
    var subAreaId = 1;
    var cross_string = '<font style="font-weight: bold;color: red;">X</font>';
    var tick_string = '<font style="font-weight: bold;color: green;">&radic;</font>';
    $(document).ready(function () {
        $('#demandOrder').blur(function () {
            if ($('#demandOrder').val() == '') {
                $("#demandOrderId").val('');
            }
        });

        jQuery('#demandOrder').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
//                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/listOrderForAutoComplete?query=" + $('#demandOrder').val();
                DocuAutoComplete.setSpinnerSelector('demandOrder').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                $("#demandOrderId").val(ui.item.id);
                $('#invoiceNo').val(ui.item.invoice_no);
                $('#retriveOrderNumber').val(ui.item.order_no)
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Order: " + item.order_no+ '</div>';
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };

        jQuery('#demandOrderSecondary').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
//                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/listOrderForAutoCompleteSecondary?query=" + $('#demandOrderSecondary').val()+ "&warehouse=" + $("#warehouseId").val();
                DocuAutoComplete.setSpinnerSelector('demandOrderSecondary').execute(response, url, data, function (item) {
                    item['label'] = item['order_no'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                $("#demandOrderSecondaryId").val(ui.item.id);
//                $('#invoiceNo').val(ui.item.invoice_no)
//                $('#retriveOrderNumber').val(ui.item.order_no)
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Order: " + item.order_no+ '</div>';
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };

        $('#delete-button').hide();
        $("#deliveryDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
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

        $("#jqgrid-grid-secondaryOrder").jqGrid({
            datatype: "local",
            colNames: [
                '',
                'SL',
                'Id',
                'Secondary Order No',
                'Order Date',
                'Expected Delivery Date',
                'Customer ID',
                'Customer Name',
                'Order Amount'
//                ''
            ],
            colModel: [
                {name: 'edit', index:'edit', width:20, align:'center', sortable:false},
                {name: 'sl', index: 'sl', width: 20},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'order_no', index: 'order_no', width: 80, align: 'center'},
                {name: 'order_date', index: 'order_date', width: 80, align: 'center'},
                {name: 'date_expected_deliver', index: 'date_expected_deliver', width: 80, align: 'center'},
                {name: 'customer_id', index: 'customer_id', width: 100, align: 'left'},
                {name: 'customer_name', index: 'customer_name', width: 150, align: 'left'},
                {name: 'order_amount', index: 'order_amount', width: 100, align: 'left', hidden: false}
//                {name: 'edit', index:'edit', width:20, align:'center', sortable:false}
            ],
            rowNum: -1,
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Orders Available for Processing",
            autowidth: true,
            height: 200,
            scrollOffset: 0,
            multiselect: true,
            gridComplete: function() {
                $("#jqgrid-grid-secondaryOrder_cb").html("&#10004;");
            },
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-secondaryOrder").jqGrid('getRowData', data.rows[i].id);
                    rowData.edit = '<a  href="javascript:updateDemandOrderPopup(' + rowData.id + ')" class="ui-icon ui-icon-pencil" title="Change Item Quantity"></a>';
                    $('#jqgrid-grid-secondaryOrder').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            onSelectRow: function (rowid, status) {
//                var orderVal = $("#jqgrid-grid-secondaryOrder").jqGrid('getCell', rowid, 'order_no');
//                updateDemandOrderPopup(rowid,orderVal)
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
                'Product Id',
                'Order Quantity',
                'Processed Quantity',
                'Available Quantity'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 50},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'checkOrder', index: 'checkOrder', width: 30, hidden: true},
                {name: 'name', index: 'name', width: 150},
                {name: 'code', index: 'code', width: 150},
                {name: 'order_qty', index: 'order_qty', width: 150, align: 'center'},
                {name: 'qty', index: 'qty', width: 150, align: 'center'},
                {name: 'available_qty', index: 'available_qty', width: 150, align: 'center'}
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
            }
        });
    });


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
        var gridId = $("#jqgrid-grid-secondaryOrder");
        for (var k = (subAreaId - 1); k >= 1; k--) {
            jQuery('#jqgrid-grid-secondaryOrder').delRowData(k);
        }

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' input[name = select-button').attr('value', 'Select All')
    }


    function checkUnCheck() {
        //alert("alert notify")
        if ($('#select-button').val() == 'Select All') {
            $('.disChck').each(function () {
                this.checked = true;
                $('#select-button').attr('value', 'Deselect All')
            })
        }
        else {
            $('.disChck').each(function () {
                this.checked = false;
                $('#select-button').attr('value', 'Select All')
            })
        }
    }

    function buttonStatusChange() {
        var isSelected = true;
        $('.disChck').each(function () {
            if (this.checked) {
                $('#select-button').attr('value', 'Deselect All');
                isSelected = false
            }
        })

        if (isSelected) {
            $('#select-button').attr('value', 'Select All')
        }
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
        var primaryOrderNo = $('#demandOrder').val();
        var secondaryOrderNo = $('#demandOrderSecondary').val();
        var deliveryDateFrom = $("#deliveryDateFrom").val();
        var deliveryDateTo = $("#deliveryDateTo").val();
        if(primaryOrderNo || secondaryOrderNo){
            deliveryDateFrom = "";
            deliveryDateTo = ""
        }else{
           if(!deliveryDateFrom && !deliveryDateTo){
               MessageRenderer.renderErrorText("Please input Primary Order No or Delivery Date range");
               return false
           }
        }
        $("#jqgrid-grid-secondaryOrder").setGridParam({
            url: '${resource(dir:'processDemandOrder', file:'showProcessSecondaryOrderDetails')}?orderNo=' + primaryOrderNo
            + "&deliveryDateFrom=" + deliveryDateFrom + "&deliveryDateTo=" + deliveryDateTo + "&warehouse=" + $("#warehouseId").val()+ "&secondaryOrderNo=" + secondaryOrderNo,
            datatype: 'json'
        });
        $("#jqgrid-grid-secondaryOrder").trigger("reloadGrid");
        $("#jqgrid-grid-item-available").jqGrid("clearGridData");
    }

    function checkItemQuantity() {
        var allIds = $("#jqgrid-grid-secondaryOrder").jqGrid('getGridParam','selarrrow');
        if (allIds == "") {
            $("#jqgrid-grid-item-available").jqGrid('clearGridData');
            return false
        }
        var warehouseId = $("#warehouseId").val();
        if(!warehouseId){
            MessageRenderer.renderErrorText("Inventory is not available to process");
            return false
        }

        $("#jqgrid-grid-item-available").setGridParam({
            url: '${resource(dir:'processDemandOrder', file:'showSecondaryItemAvailability')}?ids=' + allIds.toString() + "&warehouseId=" + warehouseId,
            datatype: "json"
        });
        $("#jqgrid-grid-item-available").trigger("reloadGrid",[
            {page: 1}
        ]);
        return true;
    }

    function processOrder() {
        var allIds = $("#jqgrid-grid-secondaryOrder").jqGrid('getGridParam','selarrrow');
        if(allIds == ""){
            var msg = {
                "class": "com.docu.common.Message",
                "messageBody": ["Select Secondary Oreder"],
                "messageTitle": "Message",
                "type": 0
            };
            MessageRenderer.render(msg);
            return false
        }

        var warehouseId = $("#warehouseId").val();
        if(!warehouseId){
            MessageRenderer.renderErrorText("Inventory is not available to process");
            return false
        }

        var data = [];
        data.push({'name':'orderIds', 'value': allIds.toString()});
        data.push({'name':'warehouseId', 'value': warehouseId});
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/processSecondaryOrder",
            success: function (data, textStatus) {
                executePostConditionProcessOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-secondaryOrder").setGridParam({
                        url: '${resource(dir:'processDemandOrder', file:'showProcessSecondaryOrderDetails')}?orderNo=' + $('#demandOrder').val()
                        + "&deliveryDateFrom=" + $("#deliveryDateFrom").val() + "&deliveryDateTo=" + $("#deliveryDateTo").val() + "&warehouse=" + $("#warehouseId").val()+ "&secondaryOrderNo=" + $('#demandOrderSecondary').val(),
                        datatype: 'json'
                    });
                    $("#jqgrid-grid-secondaryOrder").trigger("reloadGrid");

                    $("#jqgrid-grid-item-available").jqGrid("clearGridData");

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
        return true;
        /*
        var allIds = {};
        var i = 0;
        var data ={};
        var allRowData = {};

        $('.orderChk').each(function () {
            if (this.checked) {
                allIds['items.processOrder[' + i + '].orderIds'] = $(this).val();
                allIds['items.processOrder[' + i + '].orderNo'] = $("#demandOrder").val();
                data['items.processOrder[' + i + '].orderIds'] = $(this).val();
                var dataFromTheRow = $('#jqgrid-grid-secondaryOrder').jqGrid('getRowData', $(this).val());
                allIds['invItems.invItems[' + i + '].orderIds'] = $(this).val();
                allIds['invItems.invItems[' + i + '].defaultCustomerId'] = dataFromTheRow['customer_id'];
                allIds['invItems.invItems[' + i + '].advAccCode'] = dataFromTheRow['adv_acc_code'];
                allIds['invItems.invItems[' + i + '].rcvAccCode'] = dataFromTheRow['rcv_acc_code'];
                allIds['invItems.invItems[' + i + '].orderAmount'] = dataFromTheRow['order_amount'];

                data['invItems.invItems[' + i + '].defaultCustomerId'] = dataFromTheRow['customer_id'];
                data['invItems.invItems[' + i + '].advAccCode'] = dataFromTheRow['adv_acc_code'];
                data['invItems.invItems[' + i + '].rcvAccCode'] = dataFromTheRow['rcv_acc_code'];
                data['invItems.invItems[' + i + '].orderAmount'] = dataFromTheRow['order_amount'];
                i++;
            }
        });

        if (i < 1) {
            $("#dialog").dialog("destroy");
            $("#dialog-secondaryOrder-selection").dialog({
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
        else {
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data:allIds,
                url: "${request.contextPath}/${params.controller}/processSecondaryOrder",
                success: function (data, textStatus) {
                    executePostConditionProcessOrder(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                },
                complete: function () {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
            return false;
        }*/
    }

    function executePostConditionProcessOrder(result) {
        var secondaryOrderNo = $('#demandOrderSecondary').val();
        var deliveryDateFrom = $("#deliveryDateFrom").val();
        var deliveryDateTo = $("#deliveryDateTo").val();
        $("#jqgrid-grid-secondaryOrder").setGridParam({
            url: '${resource(dir:'processDemandOrder', file:'showProcessSecondaryOrderDetails')}?orderNo=' + $('#demandOrder').val()
                + "&deliveryDateFrom=" + deliveryDateFrom + "&deliveryDateTo=" + deliveryDateTo + "&warehouse=" + $("#warehouseId").val()+ "&secondaryOrderNo=" + secondaryOrderNo,
            datatype: 'json'
        });
        $("#jqgrid-grid-secondaryOrder").trigger("reloadGrid");

        $("#jqgrid-grid-item-available").jqGrid("clearGridData");
        MessageRenderer.render(result);
    }

    function updateDemandOrderPopup(orderId) {
//        debugger

        var warehouseId = $("#warehouseId").val();
        var orderVal = $("#jqgrid-grid-secondaryOrder").jqGrid('getCell', orderId, 'order_no');
        $.ajax({
            type: "POST",
            url: "${resource(dir:'processDemandOrder', file:'updateSecondaryDemandOrderPopup')}?id=" + orderId+"&orderVal="+orderVal +"&wId="+warehouseId,
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
                MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
            },
            complete: function () {
//                $("#jqgrid-grid-item-available").jqGrid("clearGridData");
//                $("#jqgrid-grid-secondaryOrder").jqGrid("reloadGrid");
                $("#loader_icon").hide();
            },
            dataType: 'html'
        });
    }

    function batchWiseOrderAllocation(productId, orderId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'processDemandOrder', file:'productBatchPopup')}?id=" + productId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (html) {
                $("#fancy-product-batch-display-block").html(html);
                $("#fancy-product-batch-div").fancybox({
                    'transitionIn': 'elastic',
                    'transitionOut': 'elastic',
                    'speedIn': 600,
                    'speedOut': 200,
                    'overlayShow': false
                }).trigger('click')

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
            },
            complete: function () {
                $("#loader_icon").hide();
            },
            dataType: 'html'
        });
    }
</script>