<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SetupIncentive');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#receiveInventoryToInventoryTransferForm").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#receiveInventoryToInventoryTransferForm").validationEngine('attach');

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
            url: '${request.contextPath}/${params.controller}/list',
            datatype: "json",
            colNames: [
                'SI',
                'id',
                'sender_dp_id',
                'Sender DP Name',
                'sender_inventory_id',
                'Sender Inventory Name',
                'sender_sub_inventory_id',
                'Sender Sub-Inventory Name',
                'Batch No',
                'ransfer_product_id',
                'Transfer Product Name',
                'Transfer Qty',
                'Unit Price',
                'Transfer Challan Number',
                'Transfer Date',
                'receiver_dp_id',
                'Receiver DP Name',
                'receiver_inventory_id',
                'Receiver Inventory Name'
            ],
            colModel: [
                {name: 'si', index: 'si', width: 30, align: 'center'},
                {name: 'id', index: 'id', width: 0, align: 'center', hidden:true},
                {name: 'sender_dp_id', index: 'sender_dp_id', width: 0, align: 'left', hidden:true},
                {name: 'sender_dp_name', index: 'sender_dp_name', width: 100, align: 'left'},
                {name: 'sender_inventory_id', index: 'sender_inventory_id', width: 0, align: 'left', hidden:true},
                {name: 'sender_inventory_name', index: 'sender_inventory_name', width: 100, align: 'left'},
                {name: 'sender_sub_inventory_id', index: 'sender_sub_inventory_id', width: 0, align: 'left', hidden:true},
                {name: 'sender_sub_inventory_name', index: 'sender_sub_inventory_name', width: 100, align: 'left'},
                {name: 'batch', index: 'batch', width: 80, align: 'left'},
                {name: 'transfer_product_id', index: 'transfer_product_id', width: 0, align: 'left', hidden:true},
                {name: 'transfer_product_name', index: 'transfer_product_name', width: 100, align: 'left'},
                {name: 'transfer_qty', index: 'transfer_qty', width: 60, align: 'center'},
                {name: 'unit_price', index: 'unit_price', width: 60, align: 'center'},
                {name: 'transfer_challan_number', index: 'transfer_challan_number', width: 60, align: 'center'},
                {name: 'transfer_date', index: 'transfer_date', width: 70, align: 'center',
                    formatter: 'date', formatoptions: {newformat: 'd-m-Y'}
                },
                {name: 'receiver_dp_id', index: 'receiver_dp_id', width: 0, align: 'left', hidden:true},
                {name: 'receiver_dp_name', index: 'receiver_dp_name', width: 100, align: 'left'},
                {name: 'receiver_inventory_id', index: 'receiver_inventory_id', width: 0, align: 'left', hidden:true},
                {name: 'receiver_inventory_name', index: 'receiver_inventory_name', width: 100, align: 'left'}
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
                $('#transferId').val('');
                getSubInventoryList('');
            },
            onSelectRow: function (rowid, status) {
                $('#transferId').val(rowid);
                var recInvId = $("#jqgrid-product-grid").jqGrid('getCell',rowid,'receiver_inventory_id');
                getSubInventoryList(recInvId);
            }
        });
        $("#jqgrid-product-grid").jqGrid('navGrid', '#jqgrid-product-pager', {edit: false, add: false, del: false, search: false});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }
        });
    });

    function getSubInventoryList(id){
        if(id){
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {id:id},
                url:  "${request.contextPath}/inventoryToInventoryTransfer/getDataAndSubInventoryListByInventoryId",
                success: function (data, textStatus) {
                    if(data.subInventoryList[0]){
                        $('#receiverSubInventory option').remove();
                        $('#receiverSubInventory').append('<option value="">'+ "Select Sub-Inventory..." +'</option>');

                        $.each(data.subInventoryList, function(key, val){
                            $('#receiverSubInventory').append('<option value="'+val.id+'">'+ val.name +'</option>');
                        });
                    }else{
                        $('#receiverSubInventory option').remove();
                        $('#receiverSubInventory').append('<option value="">'+ "Select Sub-Inventory..." +'</option>');
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
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
        }else{
            $('#receiverSubInventory option').remove();
            $('#receiverSubInventory').append('<option value="">'+ "Select Sub-Inventory..." +'</option>');
        }

    }

    function transferReceivePrecondition(){
        if($('#transferId').val()==''){
            MessageRenderer.render({
                "messageBody": "Please select a row from product list.",
                "messageTitle": "Receive Inventory To Inventory Transfer",
                "type": "0"
            });
            return false;
        }
        if(!$("#receiveInventoryToInventoryTransferForm").validationEngine('validate')){
            return false;
        }
        return true;
    }

    function transferReceive(){
        if(!transferReceivePrecondition()){
            return false;
        }
        var data = $("#receiveInventoryToInventoryTransferForm").serialize();

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url:  "${request.contextPath}/${params.controller}/transferReceive",
            success: function (data, textStatus) {
                MessageRenderer.render(data);
                reset_form_data("#receiveInventoryToInventoryTransferForm");
                $("#jqgrid-product-grid").trigger("reloadGrid");
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    reset_form_data("#receiveInventoryToInventoryTransferForm");
                    $("#jqgrid-product-grid").trigger("reloadGrid");
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

</script>