<script type="text/javascript" language="Javascript">

    var isCustomer = 0;
    var rowIndex = 0;
    var invoiceLoaded = 0;
    var oldBatch = '';

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
            %{--url: '${resource(dir:'marketReturn', file:'list')}',--}%
            datatype: "local",
            colNames: [
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
                {name: 'stockId', index: 'stockId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'invoiceId', index: 'invoiceId', width: 0, hidden: true},
                {name: 'product', index: 'product', width: 100, align: 'left'},
                {name: 'productCode', index: 'productCode', width: 120, align: 'left'},
                {name: 'invoiceNo', index: 'invoiceNo', width: 120, align: 'left'},
                {name: 'batch', index: 'batch', width: 40, align: 'left'},
                {name: 'mrType', index: 'mrType', width: 80, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 55, align: 'right'},
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
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditMarketReturn();
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
        populateCustomer();
        loadProduct();
//        loadInvoice();
        $("#isNonDpCustomer").attr('checked', false);
        $("#invoiceAvailable").attr('checked', false);
        customerTypeChange();

        //Change Event Execute for SourceDistributionPoint
         var sourceDpId= document.getElementById('sourceDistributionPoint').value
         if(sourceDpId){
         setCustomer(sourceDpId);
         }
    });

    function showData() {
        var size;
        var options = '';
        if ('${factoryList}' != '') {
            size = ${factorySize};

            if(size==1){
                options = '<option value="' + ${factoryList}[0].id + '">' + ${factoryList}[0].name + '</option>';
            }else{
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${factoryList}[i].id + '">' + ${factoryList}[i].name + '</option>';
                }
            }

            $("#destinationDistributionPoint").html(options);
        }
        options = '<option value="">Please Select</option>';
        if ('${dpList}' != '') {
            size = ${dpSize};
            var options='';
            if(size==1){
                options = '<option value="' + ${dpList}[0].id + '">' + ${dpList}[0].name + '</option>';
            }else{
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
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
            $("#invoice").val('');
            $("#finishProduct").val('');
            $("#searchProductKey").val('');
            $("#productCode").val('');
            $('#searchInvoiceKey').val('');
            $('#mrType').val('');
            $('#batch').val('');
            $("#aQuantity").val('');
            $("#quantity").val('');
            $('#reference').val('');
            $('#remarks').val('');
            setCustomer('');
            $("#isDpCustomer").val('false');
        } else {
            $("#searchKey").attr('disabled', true);
            $("#sourceDistributionPoint").attr('disabled', false);
            $("#warehouse").attr('disabled', false);
            $("#subWarehouse").attr('disabled', false);
            $("#isDpCustomer").val('true');
            $("#invoice").val('');
            $("#finishProduct").val('');
            $("#searchProductKey").val('');
            $("#productCode").val('');
            $('#searchInvoiceKey').val('');
            $('#mrType').val('');
            $('#batch').val('');
            $("#aQuantity").val('');
            $("#quantity").val('');
            $('#reference').val('');
            $('#remarks').val('');
            setCustomer('');
        }
        $("#jqgrid-grid-marketReturn").jqGrid("clearGridData");
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
        }else {
            listWarehouse();
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
                    '&dpId=' + $("#sourceDistributionPoint").val() + '&subId=' + $("#subWarehouse").val() +
                    '&batched=' + $("#invoiceAvailable").val();
                } else {
                    if ($("#invoiceAvailable").val() == 'true') {
                        if ($("#invoice").val() == '') {
                            MessageRenderer.render({
                                messageTitle: 'Missing Info',
                                type: 2,
                                messageBody: 'Please select Invoice first.'
                            });
                            return false;
                        }
                        url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1 +
                        '&invoice=' + $("#invoice").val();
                    } else {
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
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value, ui.item.code, '', ui.item.stock_id,
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

    function populateGeoLocation(id) {
        var options = '<option value="">Please Select</option>';
        if (isCustomer != 1) {
            $("#searchKey").val('');
            $("#primaryCustomer").val('');
            $("#primaryCustomerCode").val('');
            $("#primaryCustomerCategory").val('');
            $("#searchKey").attr('disabled', 'true');
            $("#searchKeySec").val('');
            $("#secondaryCustomer").val('');
            $("#secondaryCustomerCode").val('');
            $("#searchKeySec").attr('disabled', 'true');
        }
        if (id == '') {
            $("#territorySubArea").html(options);
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'marketReturn', file:'fetchGeoLocation')}?id=' + id,
            success: function (data, textStatus) {
                options = '<option value="">Please Select</option>';
                if (data != null) {
                    for (var i = 0; i < data.length; i++) {
                        options += '<option value="' + data[i].id + '">' +
                        data[i].geo_location + ', ' + data[i].para_or_locality + ', ' + data[i].road + '</option>';
                    }
                }
                $("#territorySubArea").html(options);
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

    function showInvoiceRow() {
        if (document.getElementById('invoiceAvailable').checked) {
            $("#invoiceAvailable").val(true);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCode").val('');
            $("#aQuantity").val('');
            $("#invoiceRow").attr('hidden', false);
            if (invoiceLoaded == 0) {
                loadInvoice();
                invoiceLoaded = 1;
            }
        } else {
            $("#invoiceAvailable").val(false);
            $("#invoiceRow").attr('hidden', true);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCode").val('');
            $("#aQuantity").val('');
            $("#searchInvoiceKey").val('');
            $("#invoice").val('');
            $("#batch").val('');
        }
    }

    function loadInvoice() {

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
                } else {
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
                if ($("#invoiceAvailable").val() == 'true') {
                    if ($("#invoice").val() == '') {
                        MessageRenderer.render({
                            messageTitle: 'Missing Info',
                            type: 2,
                            messageBody: 'Please select Invoice first.'
                        });
                        return false;
                    }
                    url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1 +
                    '&invoice=' + $("#invoice").val();
                } else {
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
            $("#productCode").val(code);
            $("#stockId").val(stock);
            $("#aQuantity").val(quantity);
            $("#price").val(price);
            if (!document.getElementById('isNonDpCustomer').checked) {
                $("#searchInvoiceKey").val('');
                $("#invoice").val('');
//                $("#batch").val('');
                $("#batch").val(batch);
            }else {
                $("#batch").val(batch);
            }
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
            } else {
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
            if (!document.getElementById('isNonDpCustomer').checked) {
                $("#aQuantity").val(quantity);
                oldBatch = $("#batch").val();
            }else {
                $("#aQuantity").val('');
                $("#searchProductKey").val('');
                $("#finishProduct").val('');
                $("#productCode").val('');
                $("#price").val('');
            }
            $("#searchInvoiceKey").val(productInfo);
            $("#invoice").val(productInfoId);
            $("#batch").val(batch);
            $("#stockId").val(stock);
        }
    };

   /* function showCode() {
        var code = $("#destinationDp").find('option:selected').attr('id');
        $("#dpCode").val(code);
    }*/

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
        if ($("#aQuantity").val() == 'Select Invoice') {
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
            stockId: $("#stockId").val(),
            productId: productId,
            invoiceId: $("#invoice").val(),
            product: $("#searchProductKey").val(),
            productCode: $("#productCode").val(),
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
        rowIndex++;

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
        $('#searchInvoiceKey').val('');
        $("#finishProduct").val('');
        $("#searchProductKey").val('');
        $("#productCode").val('');
        $('#mrType').val('');
        $('#batch').val('');
        $("#aQuantity").val('');
        $("#quantity").val('');
        $('#reference').val('');
        $('#remarks').val('');
        $('#price').val('');
//            },
//            dataType: 'json'
//        });
    }

    function deleteItemFromGrid(id) {
        var myGrid = $("#jqgrid-grid-marketReturn");
        var qyt = myGrid.jqGrid("getCell", id, 'quantity');
        var stockId = myGrid.jqGrid("getCell", id, 'stockId');
        %{--jQuery.ajax({--}%
        %{--type: 'post',--}%
        %{--url: '${resource(dir:'marketReturn', file:'changeQuantity')}?id=' +--}%
        %{--stockId + '&quantity=' + qyt + '&del=' + 1,--}%
        %{--success: function (data, textStatus) {--}%
        %{--},--}%
        %{--error: function (XMLHttpRequest, textStatus, errorThrown) {--}%
        %{--},--}%
        %{--complete: function () {--}%
        $("#invoice").val('');
        $("#finishProduct").val('');
        $("#searchProductKey").val('');
        $("#productCode").val('');
        $('#searchInvoiceKey').val('');
        $('#mrType').val('');
        $('#batch').val('');
        $("#aQuantity").val('');
        $("#quantity").val('');
        $('#reference').val('');
        $('#remarks').val('');
//            },
//            dataType: 'json'
//        });
        myGrid.jqGrid('delRowData', id);
    }

    function getSelectedMarketReturnId() {
        var marketReturnId = null;
        var rowid = $("#jqgrid-grid-marketReturn").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            marketReturnId = $("#jqgrid-grid-marketReturn").jqGrid('getCell', rowid, 'id');
        }
        if (marketReturnId == null) {
            marketReturnId = $('#gFormMarketReturn input[name = id]').val();
        }
        return marketReturnId;
    }
    function executePreConditionMarketReturn() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormMarketReturn").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxMarketReturn() {
        if (!executePreConditionMarketReturn()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormMarketReturn input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/save";
        }

        var data = $("#gFormMarketReturn").serializeArray();
        var gd = $("#jqgrid-grid-marketReturn").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            data.push({'name': 'items.marketReturnDetails[' + i + '].finishProduct.id', 'value': gd[i].productId});
            data.push({'name': 'items.marketReturnDetails[' + i + '].mrType', 'value': gd[i].mrType});
            data.push({'name': 'items.marketReturnDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.marketReturnDetails[' + i + '].invoice.id', 'value': gd[i].invoiceId});
            data.push({'name': 'items.marketReturnDetails[' + i + '].batch', 'value': gd[i].batch});
            data.push({'name': 'items.marketReturnDetails[' + i + '].reference', 'value': gd[i].reference});
            data.push({'name': 'items.marketReturnDetails[' + i + '].remarks', 'value': gd[i].remarks});
            data.push({
                'name': 'items.marketReturnDetails[' + i + '].distributionPointStock.id',
                'value': gd[i].stockId
            });
            if (gd[i].price != '') {
//                data.push({'name': 'priceOf' + gd[i].productId, 'value': gd[i].price});
                data.push({'name': 'items.marketReturnDetails[' + i + '].price', 'value': gd[i].price});
            }
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMarketReturn(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-marketReturn").trigger("reloadGrid");
                    customerTypeChange();
                    $("#jqgrid-grid-marketReturn").jqGrid("clearGridData");

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
    function executePostConditionMarketReturn(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-marketReturn").trigger("reloadGrid");
            customerTypeChange();
            $("#jqgrid-grid-marketReturn").jqGrid("clearGridData");
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxMarketReturn() {    // Delete record
        var marketReturnId = getSelectedMarketReturnId();
        if (executePreConditionForDeleteMarketReturn(marketReturnId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-marketReturn").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete item(s)': function () {
                        $(this).dialog('close');
                        $.ajax({
                            type: "POST",
                            dataType: "json",
                            data: jQuery("#gFormMarketReturn").serialize(),
                            url: "${resource(dir:'marketReturn', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteMarketReturn(message);
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

    function executePreConditionForEditMarketReturn(marketReturnId) {
        if (marketReturnId == null) {
            alert("Please select a marketReturn to edit");
            return false;
        }
        return true;
    }
    function executeEditMarketReturn() {
        alert("executeEditMarketReturn")
        var marketReturnId = getSelectedMarketReturnId();
        if (executePreConditionForEditMarketReturn(marketReturnId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'marketReturn', file:'edit')}?id=" + marketReturnId,
                success: function (entity) {
                    executePostConditionForEditMarketReturn(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditMarketReturn(data) {
        if (data == null) {
            alert('Selected marketReturn might have been deleted by someone');  //Message renderer
        } else {
            showMarketReturn(data);
        }
    }
    function executePreConditionForDeleteMarketReturn(marketReturnId) {
        if (marketReturnId == null) {
            alert("Please select a marketReturn to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteMarketReturn(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-marketReturn").trigger("reloadGrid");
            reset_form('#gFormMarketReturn');
        }
        MessageRenderer.render(data)
    }
    function showMarketReturn(entity) {
        $('#gFormMarketReturn input[name = id]').val(entity.id);
        $('#gFormMarketReturn input[name = version]').val(entity.version);

        if (entity.territoryConfiguration) {
            $('#territoryConfiguration').val(entity.territoryConfiguration.id).attr("selected", "selected");
        }
        else {
            $('#territoryConfiguration').val(entity.territoryConfiguration);
        }
        if (entity.territorySubArea) {
            $('#territorySubArea').val(entity.territorySubArea.id).attr("selected", "selected");
        }
        else {
            $('#territorySubArea').val(entity.territorySubArea);
        }
        if (entity.primaryCustomer) {
            $('#primaryCustomer').val(entity.primaryCustomer.id).attr("selected", "selected");
        }
        else {
            $('#primaryCustomer').val(entity.primaryCustomer);
        }
        $('#mrNo').val(entity.mrNo);
        if (entity.secondaryCustomer) {
            $('#secondaryCustomer').val(entity.secondaryCustomer.id).attr("selected", "selected");
        }
        else {
            $('#secondaryCustomer').val(entity.secondaryCustomer);
        }
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
        $('#dateUpdated').val(entity.dateUpdated);
        $('#create-button-marketReturn').attr('value', 'Update');
        $('#delete-button-marketReturn').show();
    }
    function executeSearchMarketReturn(fieldName, fieldValue) {
        if (executePreConditionForSearchMarketReturn(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'marketReturn', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchMarketReturn(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchMarketReturn(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormMarketReturn");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchMarketReturn(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormMarketReturn"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showMarketReturn(data);
        }
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }

    function eraseData(){
        $("#invoice").val('');
        $("#batch").val(oldBatch);
    }

    function listWarehouse(){
        if($("#sourceDistributionPoint").val() == ''){
            return false;
        }
        var options = '<option value="">Please Select</option>';
        $("#receivingSubInventory").html(options);
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'primaryDemandOrder', file:'fetchDefaultCustomer')}?id=' + $("#sourceDistributionPoint").val(),
            success: function (data, textStatus) {
                $("#searchKey").val(data[0].name);
                $("#primaryCustomer").val(data[0].id);
                $("#primaryCustomerCode").val(data[0].code);
                $("#primaryCustomerCategory").val('1');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'receiveProductsFromMarket', file:'listWarehouse')}?dpId=' + $("#sourceDistributionPoint").val()
            + "&default=" + 1,
            success: function (data, textStatus) {
                var batch = data;
                for (var i = 0; i < batch.length; i++) {
                    options += '<option value="' + batch[i].id + '">' + batch[i].name + '</option>';
                }
                $("#warehouse").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

    function listSubWarehouse(){
        if($("#warehouse").val() == ''){
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'receiveProductsFromMarket', file:'listSubWarehouse')}?id=' + $("#warehouse").val(),
            success: function (data, textStatus) {
                var batch = data;
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < batch.length; i++) {
                    options += '<option value="' + batch[i].id + '">' + batch[i].name + '</option>';
                }
                $("#subWarehouse").html(options);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
            },
            dataType: 'json'
        });
    }

</script>