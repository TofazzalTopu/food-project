<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        getTcNo();

        $("#inventoryToInventoryTransferForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#inventoryToInventoryTransferForm").validationEngine('attach');

//        Date Range
        $("#transferDate").datepicker(
            {
                dateFormat: 'dd-mm-yy',
                changeMonth: true,
                changeYear: true
            });

        $('#transferDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});

//        Product List
        $("#jqgrid-product-grid").jqGrid({
            url: '',
            datatype: "json",
            colNames: [
                '',
                'Product Name',
                'Product ID',
                'dpsId',
                'ID',
                'Batch Number',
                'Price',
                'Available Qty',
                'Total Price'
            ],
            colModel: [
                {name: 'select', index: 'select', width: 20, align: 'center'},
                {name: 'productName', index: 'productName', width: 120, align: 'left'},
                {name: 'productId', index: 'productId', width: 120, align: 'left', hidden:true},
                {name: 'dpsId', index: 'dpsId', width: 120, align: 'left', hidden:true},
                {name: 'productCode', index: 'productCode', width: 130, align: 'left'},
                {name: 'batch_no', index: 'batch_no', width: 100, align: 'left'},
                {name: 'price', index: 'price', width: 100, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 90, align: 'left'},
                {name: 'totalPrice', index: 'totalPrice', width: 100, align: 'left'}
            ],
            rowNum: 10,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-product-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "asc",
            caption: "Product List",
            autowidth: false,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
                var rowIds = $("#jqgrid-product-grid").jqGrid('getDataIDs')
                for(key in rowIds){
                    $("#jqgrid-product-grid").jqGrid('setRowData', rowIds[key],{
                        select: '<input name="p-select" type="radio" id="pool-radio-' + rowIds[key] + '" class="pool-radio" val="' + rowIds[key] + '" onclick="getProductLine('+rowIds[key]+');" />'
                    })
                }
            },
            onSelectRow: function (rowid, status) {

            }
        });
        $("#jqgrid-product-grid").jqGrid('navGrid', '#jqgrid-product-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
    });

    function getDataAndSubInventoryList(id){
        $('#senderInventoryId').val('');
        $('#inventoryAddress').val('');
        $('#subInventoryId').val('');
        $('#senderSubInventory').html("");
        $('#senderSubInventory').append('<option value="">Select sub-inventory...</option>');
        jQuery("#jqgrid-product-grid").clearGridData();

        var refId = ''

        if(id){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {id:id},
                url:  "${request.contextPath}/${params.controller}/getDataAndSubInventoryListByInventoryId",
                success: function (data, textStatus) {
                    if(data.inventoryInfo){
                        $('#senderInventoryId').val(data.inventoryInfo.code);
                        $('#inventoryAddress').val(data.inventoryInfo.address);
                    }

                    if(data.subInventoryList[0]){
                        $.each(data.subInventoryList, function(key, val){
                            $('#senderSubInventory').append('<option value="'+val.id+'">'+ val.name +'</option>');
                            if(val.typeName == 'Salable'){
                                refId = val.id;
                            }
                        });

                        $('#senderSubInventory').val(refId);
                        $('#subInventoryId').val(refId);
                        getAllProductListBySubInventoryId(refId);
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }  else{
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

    function getAllProductListBySubInventoryId(id){
        jQuery("#jqgrid-product-grid").clearGridData();
        $('#subInventoryId').val(id);

        if(id){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {id:id, dpId: $('#senderDp').val()},
                url:  "${request.contextPath}/${params.controller}/getAllProductListBySubInventoryId",
                success: function (data, textStatus) {
                    if(data[0]){
                        jQuery("#jqgrid-product-grid")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
                    }else{
                        jQuery("#jqgrid-product-grid").clearGridData();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        jQuery("#jqgrid-product-grid")
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

    var transferQty = '';
    function getProductLine(rowId){
        transferQty = $('#jqgrid-product-grid').jqGrid('getCell',rowId,'quantity');
        var batch = $('#jqgrid-product-grid').jqGrid('getCell',rowId,'batch_no');
        var unitPrice = $('#jqgrid-product-grid').jqGrid('getCell',rowId,'price');
        var transferProduct = $('#jqgrid-product-grid').jqGrid('getCell',rowId,'productId');
        var dpsId  = $('#jqgrid-product-grid').jqGrid('getCell',rowId,'dpsId');

        $('#transferProduct').val(transferProduct);
        $('#batch').val(batch);
        $('#unitPrice').val(unitPrice);
        $('#dpsId').val(dpsId);
    }

    function checkQuantity(){
        if(parseFloat($('#transferQty').val()) > parseFloat(transferQty)){
            return '* Transfer quantity cannot greater than available product quantity.'
        }
    }

    function getReceiverDpIdAndInventory(id){
        $('#receiverDpId').val('');

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {id:id},
            url:  "${request.contextPath}/${params.controller}/getReceiverDpInfo",
            success: function (data, textStatus) {
                if(data.dp){
                    $('#receiverDpId').val(data.dp.code);
                }else{
                    $('#receiverDpId').val('');
                }

                if(data.inventoryList[0]){
                    $('#receiverInventory option').remove();
                    $('#receiverInventory').append('<option value="">'+ "Select Inventory..." +'</option>');
                    for(key in data.inventoryList){
                        $('#receiverInventory').append('<option value="'+ data.inventoryList[key].id +'">'+ data.inventoryList[key].name +'</option>');
                    }
                }else{
                    $('#receiverInventory option').remove();
                    $('#receiverInventory').append('<option value="">'+ "Select Inventory..." +'</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#receiverInventory option').remove();
                    $('#receiverInventory').append('<option value="">'+ "Select Inventory..." +'</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getReceiverInventoryInfo(id){
        $('#receiverInventoryId').val('');
        $('#receiverInventoryAddress').val('');

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {id:id},
            url:  "${request.contextPath}/${params.controller}/getReceiverInventoryInfo",
            success: function (data, textStatus) {
                if(data){
                    $('#receiverInventoryId').val(data.code);
                    $('#receiverInventoryAddress').val(data.address);
                }else{
                    $('#receiverInventoryId').val('');
                    $('#receiverInventoryAddress').val('');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#receiverInventoryId').val('');
                    $('#receiverInventoryAddress').val('');

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
    }

    function transferChallanPrecondition(){
        if(!$("#inventoryToInventoryTransferForm").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function transferChallan(){
        if(!transferChallanPrecondition()){
            return false;
        }
        jQuery("#jqgrid-product-grid").clearGridData();
        var data = $("#inventoryToInventoryTransferForm").serialize();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url:  "${request.contextPath}/${params.controller}/transferChallan",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                reset_form_data("#inventoryToInventoryTransferForm");
                $("#jqgrid-product-grid").clearGridData();
                if(data.type == 1){
                    getTcNo();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function reset_form_data(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                //this.value = "";
                this.value = this.defaultValue;
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
    }

    function getTcNo(){
        jQuery.ajax({
            type: 'post',
            data: '',
            url:  "${request.contextPath}/${params.controller}/getTcNo",
            success: function (data, textStatus) {
                $('#transferChallanNumber').val('TC-'+data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

</script>