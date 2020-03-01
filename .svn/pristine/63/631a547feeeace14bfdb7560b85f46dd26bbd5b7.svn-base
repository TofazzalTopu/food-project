<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var jqGridProductList = null;
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gSecondaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gSecondaryDemandOrder").validationEngine('attach');

        $("#dateDeliver").datepicker( {
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true,
            minDate: new Date()
        });

        $('#deliveryDate').mask('${CommonConstants.DATE_MASK_FORMAT}', {});

        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url:'',
            datatype: "local",
            colNames:[
                'ID',
                'secondaryDemandOrderDetailsId',
                'Product Code',
                'Product Name',
                'Quantity',
                'Rate',
                'Amount',
                ''
            ],
            colModel:[
                {name:'id', index:'id', width:0, hidden:true},
                {name:'secondaryDemandOrderDetailsId', index:'secondaryDemandOrderDetailsId', hidden: true},
                {name:'productCode', index:'productCode', width:120, align:'center'},
                {name:'productName', index:'productName', width:200, align:'left'},
                {name:'quantity', index:'quantity', width:80, align:'center', sorttype:'number', formatter:"number", editable: true, editrules:{number:true}, formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 0}},
                {name:'rate', index:'rate', width:80, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'amount', index:'amount', width:80, align:'right', formatter:'currency', formatoptions:{decimalSeparator:".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name:'delete', index:'delete', width:30, align:'center', sortable:false}
            ],
            rowNum: -1,
            viewrecords:true,
            caption:"Secondary Order Details List",
            autowidth:true,
            height:true,
            scrollOffset:0,
            gridview:true,
//            multiselect: true,
            rownumbers: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow : true,
            altRows: true,
            gridComplete: function() {
                calculateSummaryData();
            },
            onSelectRow: function(rowid, status) {
//                executeEditSaleItem();
            },
            loadComplete: function (data) {
                $.each(data.rows, function (i, item) {
                    var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', data.rows[i].id);
                    rowData.delete = '<a  href="javascript:deleteProduct(' + data.rows[i].id + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
                    $('#jqgrid-grid-finishProduct').jqGrid('setRowData', data.rows[i].id, rowData);
                });
            },
            afterSaveCell : function(rowid,name,val,iRow,iCol) {
                var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', rowid);
                rowData.amount = rowData.quantity * rowData.rate;
                $('#jqgrid-grid-finishProduct').jqGrid('setRowData', rowid, rowData);
                calculateSummaryData();
            },
            loadError: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            }
        });
        loadProductItemGrid(${secondaryDemandOrder?.id});
        loadProduct(${customerMaster?.id});
    });
    function getSelectedSecondaryOrderId() {
        var secondaryDemandOrderId = null;
        var rowid = $("#jqgrid-grid-secondaryDemandOrder").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            secondaryDemandOrderId = $("#jqgrid-grid-secondaryDemandOrder").jqGrid('getCell', rowid, 'id');
        }
        if (secondaryDemandOrderId == null) {
            secondaryDemandOrderId = $('#gSecondaryDemandOrder input[name = id]').val();
        }
        return secondaryDemandOrderId;
    }
    function executePreConditionSecondaryOrder() {
        // trim field vales before process.
        trim_form();
        if ($("#gSecondaryDemandOrder").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxSecondaryOrder(isSubmit) {
        if (!executePreConditionSecondaryOrder()) {
            return false;
        }
        var data =  $("#gSecondaryDemandOrder").serializeArray();
        $('#jqgrid-grid-finishProduct').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-finishProduct").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].finishProduct.id', 'value': gd[i].id});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].id', 'value': gd[i].secondaryDemandOrderDetailsId});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].rate', 'value': gd[i].rate});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].secondaryDemandOrder.id', 'value': $('#gSecondaryDemandOrder input[name = id]').val()});
            data.push({'name':'items.secondaryDemandOrderDetails['+i+'].quantity', 'value': gd[i].quantity});
        }
        if(isSubmit){
            data.push({'name':'isSubmit', 'value': 'true'});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/updateSecondaryOrder",
            success: function (data, textStatus) {
                executePostConditionSecondaryOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
                    reset_formSecondaryOrder();

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
    function executePostConditionSecondaryOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
            reset_formSecondaryOrder();
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxSecondaryOrder() {    // Delete record
        var secondaryDemandOrderId = getSelectedSecondaryOrderId();
        if (executePreConditionForDeleteSecondaryOrder(secondaryDemandOrderId)) {
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
                            data: {id: $('#gSecondaryDemandOrder input[name = id]').val()},
                            url: "${resource(dir:'consolidateRetailOrder', file:'deleteSecondaryOrder')}",
                            success: function (message) {
                                executePostConditionForDeleteSecondaryOrder(message);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
                                    reset_formSecondaryOrder();

                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                } else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function(){
                                SubmissionLoader.hideFrom();
                            }
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function executePreConditionForDeleteSecondaryOrder(secondaryDemandOrderId) {
        if (secondaryDemandOrderId == null) {
            alert("Please select a Secondary Demand Order to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteSecondaryOrder(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
            reset_formSecondaryOrder();
        }
        MessageRenderer.render(data)
    }

    function reset_formSecondaryOrder() {
        $("#updateSecondaryOrderDiv").html("");
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function loadProduct(customerId) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
        var territorySubAreaId = $("#territorySubAreaId").val();
        jQuery('#searchProductKey').autocomplete({
            minLength:'1',
            source:function (request, response) {
                var data = {searchKey:request.term};
                var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?customerId=' + customerId + "&territorySubAreaId=" + territorySubAreaId + "&query=" + $('#searchProductKey').val();
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select:function (event, ui) {
                $("#searchProductKey").val(ui.item.value);
                $("#productId").val(ui.item.id);
                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.code);
                $('#productName').val(ui.item.name);
                $('#quantity').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' +" Code: " + item.code + ", Name: " + item.name + " Product Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    $('#search-btn-customer-product-id').click(function(){
        var url = '${resource(dir:'secondaryDemandOrder', file:'popupProductListPanel')}' ;
        var params = {customerId: $("#customerId").val(), territorySubAreaId:$("#territorySubAreaId").val()};
        DocuAjax.html(url, params, function(html){
            $.fancybox(html);
        });
    });

    function addFinishProductToGrid(){
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }
        var productId = $("#productId").val();
        if(!productId){
            MessageRenderer.renderErrorText("ProductItem is not selected");
            return
        }

        var totalQuantity = Number($("#quantity").val());
        if(totalQuantity <= 0){
            MessageRenderer.renderErrorText("Quantity is empty");
            return
        }
        var productName = $("#productName").val();
        var productCode = $("#productCode").val();
        var unitPrice = Number($("#rate").val());
        var amount = unitPrice * totalQuantity;

        var rowTo = jqGridProductList.getRowData(productId.toString());
        if (!rowTo.id) {
            var newRowData = [
                { 'id':productId.toString(), 'secondaryDemandOrderDetailsId': '', 'productCode':productCode.toString(), 'productName':productName.toString(),
                    'quantity':totalQuantity.toString(), 'rate':unitPrice.toString(), 'amount':amount.toString(), 'delete': '<a  href="javascript:deleteProduct(' + productId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'}
            ];
            jqGridProductList.addRowData(productId.toString(), newRowData);
            $("#jqgrid-grid-finishProduct [id*='undefined']").attr('id', productId.toString());
        }else{
            rowTo.quantity = Number(rowTo.quantity) + totalQuantity;
            rowTo.amount = Number(rowTo.amount) + amount;
            $('#jqgrid-grid-finishProduct').jqGrid('setRowData', productId, rowTo);
            MessageRenderer.renderErrorText("Product added on selected item");
            calculateSummaryData();
        }
        clearProductItem();
    }

    function calculateSummaryData() {
        var $grid = $('#jqgrid-grid-finishProduct');
        var sumAmount = $grid.jqGrid('getCol', 'amount', false, 'sum');
        $grid.jqGrid('footerData', 'set', { 'amount': sumAmount });
        $grid.jqGrid('footerData', 'set', { 'productCode': 'Total'});
        var advanceAmount = Number($("#advanceAmount").val());
        $("#receivableAmount").val(sumAmount - advanceAmount);
    }

    function clearProductItem(){
        $("#productId").val('');
        $("#productName").val('');
        $("#productCode").val('');
        $("#rate").val('');
        $("#quantity").val('');
        $('#searchProductKey').val('');
    }

    function deleteProduct(productId){
        var productRow = jqGridProductList.getRowData(productId.toString());
        if(Number(productRow.secondaryDemandOrderDetailsId) <= 0){
            $('#jqgrid-grid-finishProduct').jqGrid('delRowData', productId);
        }else{
            deleteAjaxSecondaryOrderProductFromDB(productRow.secondaryDemandOrderDetailsId, productId);
        }
    }
    function deleteAjaxSecondaryOrderProductFromDB(secondaryDemandOrderDetailsId, productId) {    // Delete record
        if(secondaryDemandOrderDetailsId) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-secondaryDemandOrder").dialog({
                resizable: false,
                height:'auto',
                width: 450,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function() {
                        if(!version){
                            version = 0
                        }
                        $(this).dialog('close');
                        SubmissionLoader.showTo();
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data:"secondaryOrderDetailsId=" + secondaryDemandOrderDetailsId,
                            url: "${resource(dir:'consolidateRetailOrder', file:'deleteSecondaryOrderDetails')}",
                            success: function(message) {
                                if(message.type == 1){
                                    $('#jqgrid-grid-finishProduct').jqGrid('delRowData', productId);
                                }
                                MessageRenderer.render(message);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                } else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function(){
                                SubmissionLoader.hideFrom();
                            }
                        });
                    },
                    Cancel: function() {
                        $(this).dialog('close');
                    }
                }
            }); //end of dialog
        }
    }

    function loadProductItemGrid(secondaryDemandOrderId){
        jQuery("#jqgrid-grid-finishProduct").jqGrid().setGridParam({url:
                '${resource(dir:'consolidateRetailOrder', file:'listSecondaryOrderDetails')}?secondaryOrderId=' + secondaryDemandOrderId,
            datatype: "json",mtype: "POST"}).trigger("reloadGrid");
    }
    function submitAjaxSecondaryOrder() {    // Delete record
        var secondaryDemandOrderIds = $('#gSecondaryDemandOrder input[name = id]').val();
        if (secondaryDemandOrderIds) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                dataType: "json",
                data: {secondaryOrderIds: secondaryDemandOrderIds},
                url: "${resource(dir:'consolidateRetailOrder', file:'submitSecondaryOrders')}",
                success: function (message) {
                    executePostConditionForDeleteSecondaryOrder(message);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        $("#jqgrid-grid-secondaryDemandOrder").trigger("reloadGrid");
                        reset_formSecondaryOrder();
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    } else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                }
            });
        }
    }
</script>