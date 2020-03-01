<script>
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#openingStockUploadForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#openingStockUploadForm").validationEngine('attach');

        $("#transactionDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });
        $('#transactionDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

        $("#jqgrid-grid-uploadDataInfo").jqGrid({
            %{--url: '${resource(dir:'chargeType', file:'list')}',--}%
            data: ${dataList},
            datatype: "local",
            colNames: [
                'SL',
                'Product',
                'Product Id',
                'Quantity',
                'Per Unit Cost',
                'Batch Number',
                'Batch Controlled',
                'Batch Date',
                'Batch Time',
                'Destination Inventory',
                'Inventory Id',
                'Sub-Inventory',
                'Sub-Inventory Id'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'productName', index: 'productName', width: 100, align: 'left'},
                {name: 'productId', index: 'productId', width: 70, align: 'center'},
                {name: 'quantity', index: 'quantity', width: 80, align: 'right'},
                {name: 'perUnitCostValue', index: 'perUnitCostValue', width: 90, align: 'right',
                    formatter:'currency',
                    formatoptions:{defaulValue:0,decimalPlaces:2}
                },
                {name: 'batchNumber', index: 'batchNumber', width: 90, align: 'center'
//                    formatter:'currency',
//                    formatoptions:{defaulValue:0,thousandsSeparator:',',decimalPlaces:2}
                },
                {name: 'batchControlled', index: 'batchControlled', width: 70, align: 'center'},
                {name: 'batchDate', index: 'batchDate', width: 80, align: 'center',
                    formatter: 'date',
                    formatoptions:{newformat:'d-m-Y'}
                },
                {name: 'batchTime', index: 'batchTime', width: 60, align: 'center'},
                {name: 'destinationInventory', index: 'destinationInventory', width: 100, align: 'left'},
                {name: 'inventoryId', index: 'inventoryId', width: 70, align: 'center'},
                {name: 'subInventory', index: 'subInventory', width: 100, align: 'left'},
                {name: 'subInventoryId', index: 'subInventoryId', width: 70, align: 'center'}
            ],
            rowNum: ${rowNum},
//            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
//            pager: '#jqgrid-pager-uploadDataInfo',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Data Info",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var grid =  $("#jqgrid-grid-uploadDataInfo");
                var rowIds = grid.jqGrid('getDataIDs');
                for(key in rowIds){
                    grid.jqGrid('setRowData', rowIds[key],{
                        sl:rowIds[key]
                    })
                }
            },
            onSelectRow: function (rowid, status) {
//                executeEditChargeType();
            }
        });
//        $("#jqgrid-grid-uploadDataInfo").jqGrid('navGrid', '#jqgrid-pager-uploadDataInfo', {edit: false, add: false, del: false, search: false});
//        $(".ui-pg-selbox").children().each(function () {
//            if ($(this).val() == -1) {
//                $(this).html('All')
//            }
//        });
    });

    function executePreCondition() {
        if(!$('#openingStockUploadForm').validationEngine('validate')){
            return false;
        }

        var rowIds = $("#jqgrid-grid-uploadDataInfo").jqGrid('getDataIDs');
        if (rowIds == '') {
            MessageRenderer.render({
                "messageBody": "No data found from excel sheet.",
                "messageTitle": "Opening Stock Upload",
                "type": "0"
            });
            return false;
        }

        if($('#enterpriseConfiguration').val() == ''){
            MessageRenderer.render({
                "messageBody": "Please select Enterprise first.",
                "messageTitle": "Opening Stock Upload",
                "type": "0"
            });
            return false;
        }

        return true;
    }

    function executeAjaxUpload() {
        if (!executePreCondition()) {
            return false;
        }

        var dataSet = {}

        var grid =  $("#jqgrid-grid-uploadDataInfo");
        var gridData = grid.jqGrid('getRowData');

        dataSet['confirm'] = false;
        dataSet['enterprise'] = $('#enterpriseConfiguration').val();
        dataSet['transactionDate'] = $('#transactionDate').val();

        for(var i=0; i<gridData.length; i++){
            dataSet["dataList.data["+i+"].rowNum"] = gridData[i].sl;
            dataSet["dataList.data["+i+"].productId"] = gridData[i].productId;
            dataSet["dataList.data["+i+"].quantity"] = gridData[i].quantity;
            dataSet["dataList.data["+i+"].perUnitCostValue"] = gridData[i].perUnitCostValue;
            dataSet["dataList.data["+i+"].batchNumber"] = gridData[i].batchNumber;
            dataSet["dataList.data["+i+"].batchControlled"] = gridData[i].batchControlled;
            dataSet["dataList.data["+i+"].batchDate"] = gridData[i].batchDate;
            dataSet["dataList.data["+i+"].batchTime"] = gridData[i].batchTime;
            dataSet["dataList.data["+i+"].inventoryId"] = gridData[i].inventoryId;
            dataSet["dataList.data["+i+"].subInventoryId"] = gridData[i].subInventoryId;
        }

        var actionUrl = "${request.contextPath}/${params.controller}/ajaxUpload";

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: dataSet,
            url: actionUrl,
            dataType: 'json',
            success: function (data, textStatus) {
                if(data.message.type == 1){
                    executePostConditionChargeType(dataSet);
                    MessageRenderer.render(data.message);
                }else{
//                    MessageRenderer.render(data.message);
                    var msg = '';

                    if(data.productLine){
                        if(msg) {
                            msg += "<br> System does not found 'Product-ID' in line (" + data.productLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Product-ID' in line (" + data.productLine.split(',').sort() + ")";
                        }
                    }

                    if(data.inventoryLine){
                        if(msg) {
                            msg += "<br> System does not found 'Inventory-ID' in line (" + data.inventoryLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Inventory-ID' in line (" + data.inventoryLine.split(',').sort() + ")";
                        }
                    }

                    if(data.subInventoryLine){
                        if(msg) {
                            msg += "<br> System does not found 'Sub-Inventory-ID' in line (" + data.subInventoryLine.split(',').sort() + ")";
                        }else{
                            msg = "System does not found 'Sub-Inventory-ID' in line (" + data.subInventoryLine.split(',').sort() + ")";
                        }
                    }

                    MessageRenderer.render({
                        "messageBody": msg,
                        "messageTitle": "Opening Stock Upload",
                        "type": "0"
                    });

                    for(var i=0; i<data.lines.split(',').length; i++){
                        grid.jqGrid('setRowData',data.lines.split(',')[i],false, {'font-weight':'bold', color:'white', background:'#FF6060'});
                    }

                    for(var i=0; i<data.productLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.productLine.split(',')[i],'productId', '', 'my-highlight');
                    }

                    for(var i=0; i<data.inventoryLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.inventoryLine.split(',')[i],'inventoryId', '', 'my-highlight');
                    }

                    for(var i=0; i<data.subInventoryLine.split(',').length; i++){
                        grid.jqGrid('setCell',data.subInventoryLine.split(',')[i],'subInventoryId', '', 'my-highlight');
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
//                    reset_chargeType_form('#chartOfAccountUploadForm');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            }
        });
        return false;
    }

    function executePostConditionChargeType(dataSet) {
        dataSet['confirm'] = true;
        var actionUrl = "${request.contextPath}/${params.controller}/ajaxUpload";

        $("#custom_message").html("System verified that all data is ok. Do you really want to proceed to upload all data?");

        $("#dialog-confirm-chargeType").dialog({
            resizable: false,
            height: 150,
            modal: true,
            title: 'Confirm Dialog',
            buttons: {
                Yes: function () {
                    $(this).dialog('close');
                    SubmissionLoader.showTo();
                    jQuery.ajax({
                        url: actionUrl,
                        type: "POST",
                        data: dataSet,
                        dataType: "json",
                        beforeSend: function () {
                            $("#upload-button").attr('disabled', 'disabled')
                        },
                        success: function (result) {
                            if (result.type == 1) {
                                $("#jqgrid-grid-uploadDataInfo").jqGrid('clearGridData');
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
                            $("#upload-button").removeAttr('disabled');
                            SubmissionLoader.hideFrom();
                        }
                    });
                },
                No: function () {
                    $(this).dialog('close');
                }
            }
        }); //end of dialog
    }

    function reset_chargeType_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });

        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName + ' select').val('');
    }
</script>

<style type="text/css">
.my-highlight {background-color: yellow; color: red; font-weight: bold !important; }
</style>