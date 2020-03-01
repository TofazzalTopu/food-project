<%@ page import="com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var subAreaId = 1
    $(document).ready(function () {
        $('#ui-widget-header-text').html('SecondaryDemandOrder');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormSecondaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormSecondaryDemandOrder").validationEngine('attach');

        var id = $("#enterpriseConfiguration").val();
//        clear_form("#gFormSecondaryDemandOrder");
        $("#enterpriseConfiguration").val(id);

        %{--$('#dateOrder').mask('${CommonConstants.DATE_MASK_FORMAT}', {});--}%
        %{--$('#dateDeliver').mask('${CommonConstants.DATE_MASK_FORMAT}', {});--}%
//        setDateRangeNoLimit('dateOrder','dateDeliver');
//        setAdvanceDatePicker('dateDeliver');

        $("#dateDeliver,#dateOrder").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
//                    minDate: +1
                });

        $('#dateDeliver,#dateOrder').mask('${CommonConstants.DATE_MASK_FORMAT}', {});
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
//      $('#remove-button').hide();
        $("#jqgrid-grid").jqGrid({
            url: '',
            datatype: "json",
            colNames: [

                'Id',
                'Product Code',
                'Product',
                'Rate',
                'Quantity',
                'Amount',
                ''

            ],
            colModel: [

                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'productCode', index: 'productCode', width: 200, align: 'center'},
                {name: 'product', index: 'product', width: 200, align: 'left'},
                {name: 'rate', index: 'rate', width: 80, align: 'right'},
                {name: 'qty', index: 'qty', width: 80, align: 'center',sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'amount', index: 'amount', width: 100, align: 'right', sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}},
                {name: 'delete', index: 'delete', width: 40, align: 'center', formatter: cboxFormatter}

            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Product Information",
            autowidth: true,
            autoheight: true,
            footerrow: true,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            afterSaveCell: function (id, name, val, iRow, iCol) {
                var rowData = $("#jqgrid-grid").jqGrid('getRowData', id);
                rowData.amount = rowData.qty * rowData.rate;
                $('#jqgrid-grid').jqGrid('setRowData', id, rowData);
                calculateSummaryData();
            },
            loadComplete: function () {
//        var myGrid = $("#jqgrid-grid");

            },
            onSelectRow: function (rowid, status) {
                executeEditProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
//        setDateRange('dateOrder','dateDeliver');

    });

    function cboxFormatter(cellvalue, options, rowObject) {
        return '<a  href="javascript:removeProduct(' + options.rowId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>';
    }

    function removeProduct(id) {
        $('#jqgrid-grid').jqGrid('delRowData', id);
        calculateSummaryData();
    }

    function calculateSummaryData() {
        var $grid = $('#jqgrid-grid');
        var sumAmount = $grid.jqGrid('getCol', 'amount', false, 'sum');
        $grid.jqGrid('footerData', 'set', {'amount': sumAmount});
        $grid.jqGrid('footerData', 'set', {'productCode': 'Total'});
    }

    function getSelectedSecondaryDemandOrderId() {
        var secondaryDemandOrderId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            secondaryDemandOrderId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (secondaryDemandOrderId == null) {
            secondaryDemandOrderId = $('#gFormSecondaryDemandOrder input[name = id]').val();
        }
        return secondaryDemandOrderId;
    }
    function executePreConditionSecondaryDemandOrder() {
        var fromDate = DocuDateUtil.createDateFromString($('#dateOrder').val());
        var toDate = DocuDateUtil.createDateFromString($('#dateDeliver').val());
        // trim field vales before process.
//      trim_form();
        if ($("#gFormSecondaryDemandOrder").validationEngine('validate')) {
            if(!((fromDate > toDate) && (toDate < fromDate))){
                return true;
            }else{
                MessageRenderer.render({
                    "messageBody": "Order date cannot greater than Delivery date.",
                    "messageTitle": "Create Demand Order",
                    "type": "0"
                });
                return false;
            }
        }
        else {
            return false;
        }

    }
    function executeAjaxSecondaryDemandOrder() {
        if (!executePreConditionSecondaryDemandOrder()) {
            return false;
        }

        var data = jQuery("#gFormSecondaryDemandOrder").serialize();
        var gridCollection = jQuery("#jqgrid-grid").jqGrid('getRowData');
        var dataLength = gridCollection.length;
        for (var i = 0; i < dataLength; i++) {
            data = data + '&items.secondaryDemandOrder[' + i + '].finishProduct.id=' + gridCollection[i].id;
            data = data + '&items.secondaryDemandOrder[' + i + '].rate=' + gridCollection[i].rate;
            data = data + '&items.secondaryDemandOrder[' + i + '].quantity=' + gridCollection[i].qty;
            data = data + '&items.secondaryDemandOrder[' + i + '].amount=' + gridCollection[i].amount;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                executePostConditionSecondaryDemandOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").clearGridData();
                    var id = $("#enterpriseConfiguration").val();
                    var customerId = $("#deliveryCustomerId").val();
                    var customerId2 = $("#customerId").val();
                    var customerName = $("#customer").val();
                    clear_form("#gFormSecondaryDemandOrder");
                    $("#enterpriseConfiguration").val(id);
                    $("#deliveryCustomerId").val(customerId);
                    $("#customerId").val(customerId2);
                    $("#customer").val(customerName);
                    $("#deliveryManList_input").val(customerName);
                    $("#deliveryManList_hidden").val(customerId);

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
    function executePostConditionSecondaryDemandOrder(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").clearGridData();
            var id = $("#enterpriseConfiguration").val();
            var customerId = $("#deliveryCustomerId").val();
            var customerId2 = $("#customerId").val();
            var customerName = $("#customer").val();
            clear_form("#gFormSecondaryDemandOrder");
            $("#enterpriseConfiguration").val(id);
            $("#deliveryCustomerId").val(customerId);
            $("#customerId").val(customerId2);
            $("#customer").val(customerName);
            $("#deliveryManList_input").val(customerName);
            $("#deliveryManList_hidden").val(customerId);
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxSecondaryDemandOrder() {    // Delete record
        var secondaryDemandOrderId = getSelectedSecondaryDemandOrderId();
        if (executePreConditionForDeleteSecondaryDemandOrder(secondaryDemandOrderId)) {
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
                            data: jQuery("#gFormSecondaryDemandOrder").serialize(),
                            url: "${resource(dir:'secondaryDemandOrder', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteSecondaryDemandOrder(message);
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                } else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function () {
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

    function executePreConditionForEditSecondaryDemandOrder(secondaryDemandOrderId) {
        if (secondaryDemandOrderId == null) {
            alert("Please select a secondaryDemandOrder to edit");
            return false;
        }
        return true;
    }
    function executeEditSecondaryDemandOrder() {
        var secondaryDemandOrderId = getSelectedSecondaryDemandOrderId();
        if (executePreConditionForEditSecondaryDemandOrder(secondaryDemandOrderId)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'secondaryDemandOrder', file:'edit')}?id=" + secondaryDemandOrderId,
                success: function (entity) {
                    executePostConditionForEditSecondaryDemandOrder(entity);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
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
    }
    function executePostConditionForEditSecondaryDemandOrder(data) {
        if (data == null) {
            alert('Selected secondaryDemandOrder might have been deleted by someone');  //Message renderer
        } else {
            showSecondaryDemandOrder(data);
        }
    }
    function executePreConditionForDeleteSecondaryDemandOrder(secondaryDemandOrderId) {
        if (secondaryDemandOrderId == null) {
            alert("Please select a secondaryDemandOrder to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteSecondaryDemandOrder(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            var id = $("#enterpriseConfiguration").val();
            clear_form("#gFormSecondaryDemandOrder");
            $("#enterpriseConfiguration").val(id);
        }
        MessageRenderer.render(data)
    }
    function showSecondaryDemandOrder(entity) {
        $('#gFormSecondaryDemandOrder input[name = id]').val(entity.id);
        $('#gFormSecondaryDemandOrder input[name = version]').val(entity.version);

        if (entity.customerMaster) {
            $('#customerMaster').val(entity.customerMaster.id).attr("selected", "selected");
        }
        else {
            $('#customerMaster').val(entity.customerMaster);
        }
        if (entity.userOrderPlaced) {
            $('#userOrderPlaced').val(entity.userOrderPlaced.id).attr("selected", "selected");
        }
        else {
            $('#userOrderPlaced').val(entity.userOrderPlaced);
        }
        if (entity.userTentativeDelivery) {
            $('#userTentativeDelivery').val(entity.userTentativeDelivery.id).attr("selected", "selected");
        }
        else {
            $('#userTentativeDelivery').val(entity.userTentativeDelivery);
        }
        $('#orderNo').val(entity.orderNo);
        $('#dateOrder').val(entity.dateOrder);
        $('#dateDeliver').val(entity.dateDeliver);
        if (entity.userCreated) {
            $('#userCreated').val(entity.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(entity.userCreated);
        }
        if (entity.userUpdated) {
            $('#userUpdated').val(entity.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(entity.userUpdated);
        }
        $('#dateCreated').val(entity.dateCreated);
        $('#lastUpdated').val(entity.lastUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchSecondaryDemandOrder(fieldName, fieldValue) {
        if (executePreConditionForSearchSecondaryDemandOrder(fieldName, fieldValue)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'secondaryDemandOrder', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchSecondaryDemandOrder(entity, fieldName, fieldValue);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        var id = $("#enterpriseConfiguration").val();
                        clear_form("#gFormSecondaryDemandOrder");
                        $("#enterpriseConfiguration").val(id);
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
    }
    function executePreConditionForSearchSecondaryDemandOrder(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            var id = $("#enterpriseConfiguration").val();
            clear_form("#gFormSecondaryDemandOrder");
            $("#enterpriseConfiguration").val(id);
            return false;
        }
        return true;
    }
    function executePostConditionForSearchSecondaryDemandOrder(data, fieldName, fieldValue) {
        if (data == null) {
            var id = $("#enterpriseConfiguration").val();
            clear_form("#gFormSecondaryDemandOrder");
            $("#enterpriseConfiguration").val(id);
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showSecondaryDemandOrder(data);
        }
    }
    function generateGeoSelectList(customerId){
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            url: '${request.contextPath}/${params.controller}/getGeoLocationForCustomer?customerMasterId=' + customerId,
            success: function (data, textStatus) {
                $("#territorySubArea").empty();
                $.each(data,function(key, value)
                {
                    $("#territorySubArea").append('<option value=' + value.id + '>' + value.geoLocation + '</option>');
                });
                if(data.length > 1){
                    MessageRenderer.render({messageTitle:'Select Geo Location', type: 2, messageBody: 'Customer assigned to more than one geo location.'});
                    $("#geoTr").show();
                }else{
                    $("#geoTr").hide();
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
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
    };
    function loadProduct(customerId) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($("#customerId").val()) {
                    var data = {searchKey: request.term};
                    var url = '${resource(dir:'finishProduct', file:'listProductAutoComplete')}?customerId=' + customerId + "&query=" + $('#searchProductKey').val() + "&territorySubAreaId=" + $("#territorySubArea").val();
                    DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                        item['label'] = item['code'] + "-" + item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }

            },
            select: function (event, ui) {

                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);

                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.code);
                $('#productName').val(ui.item.name)

            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + " Product Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    var CustomerInfo = {
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'finishProduct', file:'popupProductListPanel')}';
            var params = {customerId: customerCoreInfoId , territorySubAreaId:$("#territorySubArea").val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#customerId").val(customerCoreInfoId);
            loadProduct($("#customerId").val());
        },
        setProductInformation: function (customerCoreInfoId, customerCoreInfo) {

            $("#searchProductKey").val(customerCoreInfo);
            $("#productId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        }
    };
    $('#search-btn-customer-product-id').click(function () {
        var customerId = $("#customerId").val();
        if(!customerId){
            MessageRenderer.renderErrorText("Please Select Tentative Delivery Man");
            return;
        }
        CustomerInfo.popupProductListPanel(customerId);
    });
    function addNewItemToCollectionGrid() {

        var fromDate = DocuDateUtil.createDateFromString($('#dateOrder').val());
        var toDate = DocuDateUtil.createDateFromString($('#dateDeliver').val());

        if($('#dateDeliver').val() == ''){
            MessageRenderer.render({
                "messageBody": "Please enter Delivery date.",
                "messageTitle": "Create Demand Order",
                "type": "0"
            });
            return;
        }

        if(((fromDate > toDate) && (toDate < fromDate))){
            MessageRenderer.render({
                "messageBody": "Order date cannot greater than Delivery date.",
                "messageTitle": "Create Demand Order",
                "type": "0"
            });
            return;
        }

        var myGrid = $("#jqgrid-grid");
        var productId = $("#productId").val();
        if(!productId){
            MessageRenderer.renderErrorText("Please Select Product");
            return;
        }
        var quantity = Number($("#quantity").val());
        if(quantity <= 0){
            MessageRenderer.renderErrorText("Please input quantity");
            return;
        }
        var amount = ($("#rate").val()) * quantity;
        if ($('#add-button').val() == 'Update') {

            selRowId = myGrid.jqGrid('getGridParam', 'selrow');
            celValue = myGrid.jqGrid('setCell', selRowId, 'id', productId);
            celValue2 = myGrid.jqGrid('setCell', selRowId, 'productCode', $("#productCode").val());
            celValue3 = myGrid.jqGrid('setCell', selRowId, 'product', $("#productName").val());
            celValue4 = myGrid.jqGrid('setCell', selRowId, 'rate', $("#rate").val());
            celValue5 = myGrid.jqGrid('setCell', selRowId, 'qty', quantity);
            celValue6 = myGrid.jqGrid('setCell', selRowId, 'amount', amount);
            var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');

            myGrid.jqGrid('footerData', 'set', {productCode: 'Total:', amount: sum});

            $('#add-button').val('Add');
            $('#remove-button').hide();
        } else {
            var rowTo = myGrid.getRowData(productId.toString());
            if (!rowTo.id) {
                var dataItem = {

                    id: productId,
                    productCode: $("#productCode").val(),
                    product: $("#productName").val(),
                    rate: $("#rate").val(),
                    qty: quantity,
                    amount: amount
                }
                myGrid.addRowData(productId, dataItem);
//            subAreaId++;
                var sum = myGrid.jqGrid('getCol', 'amount', false, 'sum');
                myGrid.jqGrid('footerData', 'set', {productCode: 'Total:', amount: sum});
            } else {
                rowTo.qty = Number(rowTo.qty) + quantity;
                rowTo.amount = Number(rowTo.amount) + amount;
                myGrid.jqGrid('setRowData', productId, rowTo);
                MessageRenderer.renderErrorText("Product added on selected item");
                calculateSummaryData();
            }
        }
        $("#searchProductKey").val('');
        $("#productCode").val('');
        $("#productName").val('');
        $("#rate").val('');
        $("#quantity").val('');
        $("#amount").val('');

    }
    function executeEditProduct() {
        var myGrid = $("#jqgrid-grid");

        var amount = ($("#rate").val()) * ($("#quantity").val());
        selRowId = myGrid.jqGrid('getGridParam', 'selrow');
        celValue = myGrid.jqGrid('getCell', selRowId, 'id', $("#productId").val());
        celValue2 = myGrid.jqGrid('getCell', selRowId, 'productCode', $("#productCode").val());
        celValue3 = myGrid.jqGrid('getCell', selRowId, 'product', $("#productName").val());
        celValue4 = myGrid.jqGrid('getCell', selRowId, 'rate', $("#rate").val());
        celValue5 = myGrid.jqGrid('getCell', selRowId, 'qty', $("#quantity").val());

        $("#searchProductKey").val(celValue2 + '-' + celValue3);
        $("#productId").val(celValue);
        $("#productCode").val(celValue2);
        $("#productName").val(celValue2);
        $("#rate").val(celValue4);
        $("#quantity").val(celValue5);


        $('#add-button').attr('value', 'Update');
        $('#remove-button').show();
    }

    function deleteProduct() {
        var myGrid = $("#jqgrid-grid");
        selRowId = myGrid.jqGrid('getGridParam', 'selrow');
        myGrid.jqGrid('delRowData', selRowId);
        $("#productCode").val('');
        $("#productName").val('');
        $("#rate").val('');
        $("#quantity").val('');
        $("#amount").val('');
        $("#searchProductKey").val('');
        myGrid.jqGrid('footerData', 'set', {productCode: '', amount: ''});
        $('#add-button').val('Add');
        $('#remove-button').hide();
    }
    function resetForm() {
        var id = $("#enterpriseConfiguration").val();
        clear_form("#gFormSecondaryDemandOrder");
        $("#enterpriseConfiguration").val(id);
        $("#jqgrid-grid").jqGrid("clearGridData");
//        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
//
//            this.value = this.defaultValue;
//
//        });
//        $(formName).find('select').each(function () {
//            this.value = "";
//        });
//
//        $(formName + ' input[name = create-button]').attr('value', 'Create');
//        $(formName + ' input[name = delete-button]').hide();
    }
</script>