<script type="text/javascript" language="Javascript">

    var isCustomer = 0;
    var rowIndex = -1;
    var initiated = 0;
    var invoiceLoaded = 0;
    var oldBatch;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormMarketReturn").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormMarketReturn").validationEngine('attach');
//        reset_form("#gFormMarketReturn");
        $("#jqgrid-grid-marketReturn").jqGrid({
            url: '${resource(dir:'marketReturn', file:'listDetails')}?id=' + ${marketReturn?.id},
            datatype: "json",
            colNames: [
                'ID',
                'Stock ID',
                'Product ID',
                'Invoice ID',

                'Product',
                'Product Code',
                'Invoice Code',
                'Batch',
                'MR Type',
                'Quantity',
                'Reference',
                'Remarks',
                'Price',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'stockId', index: 'stockId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'invoiceId', index: 'invoiceId', width: 0, hidden: true},
                {name: 'product', index: 'product', width: 100, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 120, align: 'left'},
                {name: 'invoiceNo', index: 'invoiceNo', width: 120, align: 'left'},
                {name: 'batch', index: 'batch', width: 40, align: 'left'},
                {name: 'mrType', index: 'mrType', width: 80, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 55, align: 'right',
                    sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'reference', index: 'reference', width: 80, align: 'left'},
                {name: 'remarks', index: 'remarks', width: 80, align: 'left'},
                {name: 'price', index: 'price', width: 0, hidden: true},
                {name: 'delete', index: 'reference', width: 30, align: 'center', sortable: false}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-marketReturn',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All Added Product",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
//            cellEdit: true,
//            cellsubmit: 'clientArray',
//            cellurl: null,
//            afterSaveCell: function (id, name, val, iRow, iCol) {
//                alert(id);
//            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditMarketReturn();
            }
        });
        $("#jqgrid-grid-marketReturn").jqGrid('navGrid', '#jqgrid-pager-marketReturn', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
//                .navButtonAdd('#jqgrid-pager-marketReturn', {
//                    caption: "Edit",
//                    buttonicon: "ui-icon-edit",
//                    onClickButton: function () {
//                        executeEditMarketReturn();
//                    },
//                    position: "last"
//                })
//                .navButtonAdd('#jqgrid-pager-marketReturn', {
//                    caption: "Delete",
//                    buttonicon: "ui-icon-del",
//                    onClickButton: function () {
//                        deleteAjaxMarketReturn();
//                    },
//                    position: "last"
//                });

        showData();
        setData();
        populateCustomer();
        loadProduct();
    });

    function showData() {
        var size;
        var options = '<option value="">Please Select</option>';
        if ('${factoryList}' != '') {
            size = ${factorySize};
            for (var i = 0; i < size; i++) {
                options += '<option value="' + ${factoryList}[i].id + '">' + ${factoryList}[i].name + '</option>';
            }
            $("#destinationDistributionPoint").html(options);
        }
        options = '<option value="">Please Select</option>';
        if ('${dpList}' != '') {
            size = ${dpSize};
            for (var i = 0; i < size; i++) {
                options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
            }
            $("#sourceDistributionPoint").html(options);
        }
        options = '<option value="">Please Select</option>';
        if ('${wareHouseList}' != '') {
            size = ${wareHouseSize};
            for (var i = 0; i < size; i++) {
                options += '<option value="' + ${wareHouseList}[i].id + '">' + ${wareHouseList}[i].name + '</option>';
            }
            $("#warehouse").html(options);
        }
        options = '<option value="">Please Select</option>';
        if ('${subWareHouseList}' != '') {
            size = ${subWareHouseSize};
            for (var i = 0; i < size; i++) {
                options += '<option value="' + ${subWareHouseList}[i].id + '">' + ${subWareHouseList}[i].name + '</option>';
            }
            $("#subWarehouse").html(options);
        }
        if('${marketReturn.isDpCustomer}' == 'true'){
            $("#isNonDpCustomer").attr('checked', false);
            $("#isDpCustomer").val('true');
            customerTypeChange();
            checkSecondaryCustomer();
        }
    }

    function setData(){
        $("#destinationDistributionPoint").val(${marketReturn?.destinationDistributionPoint?.id});
        $("#sourceDistributionPoint").val(${marketReturn?.sourceDistributionPoint?.id});
        $("#warehouse").val(${marketReturn?.warehouse?.id});
        $("#subWarehouse").val(${marketReturn?.subWarehouse?.id});
        $("#searchKey").val('${marketReturn?.primaryCustomer?.name}');
        $("#primaryCustomer").val(${marketReturn?.primaryCustomer?.id});
        $("#primaryCustomerCode").val(${marketReturn?.primaryCustomer?.code});
        $("#searchKeySec").val('${marketReturn?.secondaryCustomer?.name}');
        $("#secondaryCustomer").val(${marketReturn?.secondaryCustomer?.id});
        $("#secondaryCustomerCode").val(${marketReturn?.secondaryCustomer?.code});
    }

    function customerTypeChange() {
        if (document.getElementById('isNonDpCustomer').checked) {
            $("#searchKey").attr('disabled', false);
            $("#sourceDistributionPoint").val('');
            $("#sourceDistributionPoint").attr('disabled', true);
            $("#warehouse").val('');
            $("#warehouse").attr('disabled', true);
            $("#subWarehouse").val('');
            $("#subWarehouse").attr('disabled', true);
            $("#searchKeySec").val('');
            $("#secondaryCustomer").val('');
            $("#secondaryCustomerCode").val('');
            $("#searchKeySec").attr('disabled', true);
            setCustomer('');
            $("#isDpCustomer").val('false');
        } else {
            $("#searchKey").attr('disabled', true);
            $("#sourceDistributionPoint").attr('disabled', false);
            $("#warehouse").attr('disabled', false);
            $("#subWarehouse").attr('disabled', false);
            $("#isDpCustomer").val('true');
            $("#searchKeySec").val('');
            $("#secondaryCustomer").val('');
            $("#secondaryCustomerCode").val('');
            $("#searchKeySec").attr('disabled', false);
            setCustomer('');
        }
//        $("#jqgrid-grid-marketReturn").jqGrid("clearGridData");
    }

    function showInvoiceRow() {
        if (document.getElementById('invoiceAvailable').checked) {
            $("#invoiceAvailable").val(true);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCodeEdit").val('');
            $("#aQuantity").val('');
            $("#invoiceRow").attr('hidden', false);
            if(invoiceLoaded == 0) {
                loadInvoice();
                invoiceLoaded = 1;
            }
        } else {
            $("#invoiceAvailable").val(false);
            $("#invoiceRow").attr('hidden', true);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCodeEdit").val('');
            $("#aQuantity").val('');
            $("#searchInvoiceKey").val('');
            $("#invoice").val('');
            $("#batch").val('');
        }
    }

    function setCustomer(id) {
        if (id == '') {
            $("#searchKey").val('');
            $("#primaryCustomer").val('');
            $("#primaryCustomerCode").val('');
            $("#searchKeySec").val('');
            $("#searchKeySec").attr('disabled', true);
            $("#secondaryCustomer").val('');
            $("#secondaryCustomerCode").val('');
            return false;
        }
        if ('${customer}' != '') {
            $("#searchKey").val('${customer?.name}');
            $("#primaryCustomer").val('${customer?.id}');
            $("#primaryCustomerCode").val('${customer?.code}');
            $("#primaryCustomerCategory").val('${customer?.category}');
            isCustomer = 1;
        }
        checkSecondaryCustomer();
    }

    function populateCustomer() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, cat: '2'};
                var url = '${resource(dir:'marketReturn', file:'fetchCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.category);
//                    loadProduct(ui.item.id);
//                    $('#searchProductKey').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + ", Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };

        $('#search-btn-primary-customer-id').click(function () {
            CustomerInfo.popupCustomerListPanel();
        });

    }

    function checkSecondaryCustomer() {
        $("#searchKeySec").val('');
        $("#secondaryCustomer").val('');
        $("#secondaryCustomerCode").val('');
        $("#searchKeySec").attr('disabled', false);
        if(initiated == 1){
            return false;
        }
        initiated = 1;
        jQuery('#searchKeySec').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, id: $("#sourceDistributionPoint").val(), cat: '3'};
                var url = '${resource(dir:'marketReturn', file:'fetchCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKeySec').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setSecCustomerInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.category);
//                    loadProduct(ui.item.id);
//                    $('#searchProductKey').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + "-" + ", Category: " + item.category + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };

        $('#search-btn-secondary-customer-id').click(function () {
            CustomerInfo.popupSecCustomerListPanel();
        });
    }

    function deleteItemFromGrid(id) {
        var url = '';
        var myGrid = $("#jqgrid-grid-marketReturn");
        var qyt = myGrid.jqGrid("getCell", id, 'quantity');
        var stockId = myGrid.jqGrid("getCell", id, 'stockId');
        if(id > 0){
            url = '${resource(dir:'marketReturn', file:'changeQuantity')}?id=' +
            stockId + '&quantity=' + qyt + '&del=' + 2 + '&retId=' + id;
            jQuery.ajax({
                type: 'post',
                url: url,
                success: function (data, textStatus) {
//                    MessageRenderer.render({
//                        messageTitle: 'Delete Success',
//                        type: 1,
//                        messageBody: 'Product return record deleted successfully.'
//                    });
                    MessageRenderer.render(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }  else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                    $("#invoice").val('');
                    $("#finishProduct").val('');
                    $("#searchProductKey").val('');
                    $("#productCodeEdit").val('');
                    $('#searchInvoiceKey').val('');
                    $('#mrType').val('');
                    $('#batch').val('');
                    $("#aQuantity").val('');
                    $("#quantity").val('');
                    $('#reference').val('');
                    $('#remarks').val('');
                },
                dataType: 'json'
            });
        }
        myGrid.jqGrid('delRowData', id);
    }

    function loadProduct() {
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                %{--var url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1;--}%
                var url = '';
                if (!document.getElementById('isNonDpCustomer').checked) {
                    if ($("#sourceDistributionPoint").val() == '' || $("#subWarehouse").val() == '') {
                        MessageRenderer.render({
                            messageTitle: 'Missing Info',
                            type: 2,
                            messageBody: 'Please enter missing information above.'
                        });
                        return false;
                    }
                    url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1 +
                    '&dpId=' + $("#sourceDistributionPoint").val() + '&subId=' + $("#subWarehouse").val()+
                    '&batched=' + $("#invoiceAvailable").val();
                } else {
                    if($("#invoiceAvailable").val() == 'true'){
                        if($("#invoice").val() == ''){
                            MessageRenderer.render({
                                messageTitle: 'Missing Info',
                                type: 2,
                                messageBody: 'Please select Invoice first.'
                            });
                            return false;
                        }
                        url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1 +
                        '&invoice=' + $("#invoice").val();
                    }else {
                        url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1;
                    }
                }
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.stock_id,
                        ui.item.received_quantity, ui.item.unit_price ? ui.item.unit_price : '');
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };

        $('#search-btn-customer-product-id').click(function () {
            CustomerInfo.popupProductListPanel($("#customerId").val());
        });
    }

    function loadInvoice() {
        %{--jQuery.ajax({--}%
            %{--type: 'post',--}%
            %{--url: '${resource(dir:'receiveProductsFromMarket', file:'listBatch')}?id=' +--}%
            %{--$("#secondaryCustomer").val() + '&entId=' + 1,--}%
            %{--success: function (data, textStatus) {--}%
                %{--var batch = data;--}%
                %{--var options = '<option value="">Please Select</option>';--}%
                %{--for (var i = 0; i < batch.length; i++) {--}%
                    %{--options += '<option value="' + batch[i].batch_no + '">' + batch[i].batch_no + '</option>';--}%
                %{--}--}%
                %{--$("#batch").html(options);--}%
            %{--},--}%
            %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
            %{--},--}%
            %{--complete: function () {--}%
            %{--},--}%
            %{--dataType: 'json'--}%
        %{--});--}%

        jQuery('#searchInvoiceKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '';
                if (!document.getElementById('isNonDpCustomer').checked) {
                    if ($("#finishProduct").val() == '') {
                        MessageRenderer.render({
                            messageTitle: 'Missing Info',
                            type: 2,
                            messageBody: 'Please select Product first.'
                        });
                        return false;
                    }
                    url = '${resource(dir:'marketReturn', file:'listInvoice')}?proId=' + $("#finishProduct").val() +
                    '&dpId=' + $("#sourceDistributionPoint").val() + '&subId=' + $("#subWarehouse").val() + '&batchNo=' + $("#batch").val();
                }else{
                    if ($("#primaryCustomer").val() == '') {
                        MessageRenderer.render({
                            messageTitle: 'Missing Info',
                            type: 2,
                            messageBody: 'Please select Primary Customer first.'
                        });
                        return false;
                    }
                    url = '${resource(dir:'marketReturn', file:'listInvoice')}?id=' + $("#primaryCustomer").val();
                }
                DocuAutoComplete.setSpinnerSelector('searchInvoiceKey').execute(response, url, data, function (item) {
                    item['label'] = item['code'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setInvoiceInformation(ui.item.id, ui.item.code, ui.item.received_quantity, ui.item.batch_no, ui.item.stock_id);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Amount: " +
                item.invoice_amount + ", Dated: " + item.date_created + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        };

        $('#search-btn-customer-invoice-id').click(function () {
            CustomerInfo.popupInvoiceListPanel();
        });
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            if (!document.getElementById('isNonDpCustomer').checked) {
                return false;
            }
            var url = '${resource(dir:'marketReturn', file:'popupCustomerListPanel')}?cat=2';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKey").val(customerCoreInfo);
            %{--$("#searchKey").val('${customer.name}');--}%
            $("#primaryCustomerCategory").val(category);
            $("#primaryCustomer").val(customerCoreInfoId);
            $("#primaryCustomerCode").val(code);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        },
        popupSecCustomerListPanel: function () {
            if ($("#sourceDistributionPoint").val() == '') {
                return false;
            }
            var url = '${resource(dir:'marketReturn', file:'popupCustomerListPanel')}?id=' + $("#sourceDistributionPoint").val()
                    + '&cat=3';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setSecCustomerInformation: function (customerCoreInfoId, customerCoreInfo, code, category) {
            $("#searchKeySec").val(customerCoreInfo);
            %{--$("#searchKey").val('${customer.name}');--}%
            $("#secondaryCustomer").val(customerCoreInfoId);
            $("#secondaryCustomerCode").val(code);
        },
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '';
            if (!document.getElementById('isNonDpCustomer').checked) {
                if ($("#sourceDistributionPoint").val() == '' || $("#subWarehouse").val() == '') {
                    MessageRenderer.render({
                        messageTitle: 'Missing Info',
                        type: 2,
                        messageBody: 'Please enter missing information above.'
                    });
                    return false;
                }
                url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1 +
                '&dpId=' + $("#sourceDistributionPoint").val() + '&subId=' + $("#subWarehouse").val() +
                '&batched=' + $("#invoiceAvailable").val();
            } else {
                if($("#invoiceAvailable").val() == 'true'){
                    if($("#invoice").val() == ''){
                        MessageRenderer.render({
                            messageTitle: 'Missing Info',
                            type: 2,
                            messageBody: 'Please select Invoice first.'
                        });
                        return false;
                    }
                    url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1 +
                    '&invoice=' + $("#invoice").val();
                }else {
                    url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1;
                }
            }
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (productInfoId, productInfo, code, batch, stock, quantity, price) {
            $("#searchProductKey").val(productInfo);
            $("#finishProduct").val(productInfoId);
            $("#productCodeEdit").val(code);
            $("#stockId").val(stock);
            $("#aQuantity").val(quantity);
            $("#batch").val(batch);
            $("#price").val(price);
        },
        popupInvoiceListPanel: function () {
            var url = '';
            if (!document.getElementById('isNonDpCustomer').checked) {
                if ($("#finishProduct").val() == '') {
                    MessageRenderer.render({
                        messageTitle: 'Missing Info',
                        type: 2,
                        messageBody: 'Please select Product first.'
                    });
                    return false;
                }
                url = '${resource(dir:'marketReturn', file:'popupInvoiceListPanel')}?proId=' + $("#finishProduct").val() +
                '&dpId=' + $("#sourceDistributionPoint").val() + '&subId=' + $("#subWarehouse").val() + '&batchNo=' + $("#batch").val();
            }else{
                if ($("#primaryCustomer").val() == '') {
                    MessageRenderer.render({
                        messageTitle: 'Missing Info',
                        type: 2,
                        messageBody: 'Please select Primary Customer first.'
                    });
                    return false;
                }
                url = '${resource(dir:'marketReturn', file:'popupInvoiceListPanel')}?id=' + $("#primaryCustomer").val();
            }
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setInvoiceInformation: function (productInfoId, productInfo, quantity, batch, stock) {
            oldBatch = $("#batch").val();
            $("#searchInvoiceKey").val(productInfo);
            $("#invoice").val(productInfoId);
            $("#batch").val(batch);
            $("#stockId").val(stock);
            $("#aQuantity").val(quantity);
            $("#price").val('');
        }
    };

    function addItemToGrid() {
        var myGrid = $("#jqgrid-grid-marketReturn");
        var colData = myGrid.jqGrid('getDataIDs');
        var productId = $("#finishProduct").val();
        var quantity = $('#quantity').val();
        var mrType = $('#mrType').val();
        var invoice = $('#searchInvoiceKey').val();
        var batch = $('#batch').val();
        if (productId == '' || quantity == '' || mrType == '') {
            MessageRenderer.render({
                messageTitle: 'Missing Info',
                type: 2,
                messageBody: 'Please fill the required information before adding to grid.'
            });
            return false;
        }
        if($("#aQuantity").val() == 'Select Invoice'){
            MessageRenderer.render({
                messageTitle: 'Data Error',
                type: 2,
                messageBody: 'Select invoice first.'
            });
            return false;
        }
        if (!document.getElementById('isNonDpCustomer').checked) {
            if (parseFloat(quantity) > parseFloat($("#aQuantity").val())) {
                MessageRenderer.render({
                    messageTitle: 'Data Error',
                    type: 2,
                    messageBody: 'Return quantity can not be greater than available quantity.'
                });
                return false;
            }
        }
        var dataItem = {
            id: rowIndex,
            stockId: $("#stockId").val(),
            productId: productId,
            invoiceId: $("#invoice").val(),
            product: $("#searchProductKey").val(),
            productCode: $("#productCodeEdit").val(),
            invoiceNo: invoice,
            batch: batch,
            mrType: mrType,
            quantity: quantity,
            reference: $('#reference').val(),
            remarks: $('#remarks').val(),
            price: $('#price').val(),
            delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        myGrid.addRowData(rowIndex, dataItem, "last");
        rowIndex--;

        %{--jQuery.ajax({--}%
            %{--type: 'post',--}%
            %{--url: '${resource(dir:'marketReturn', file:'changeQuantity')}?id=' +--}%
            %{--$("#stockId").val() + '&quantity=' + quantity,--}%
            %{--success: function (data, textStatus) {--}%
            %{--},--}%
            %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
            %{--},--}%
            %{--complete: function () {--}%
                $("#invoice").val('');
                $("#finishProduct").val('');
                $("#searchProductKey").val('');
                $("#productCodeEdit").val('');
                $('#searchInvoiceKey').val('');
                $('#mrType').val('');
                $('#batch').val('');
                $("#aQuantity").val('');
                $("#quantity").val('');
                $('#reference').val('');
                $('#remarks').val('');
        $('#price').val('');
            %{--},--}%
            %{--dataType: 'json'--}%
        %{--});--}%
    }

    function eraseData(){
        $("#invoice").val('');
        $("#batch").val(oldBatch);
    }

</script>