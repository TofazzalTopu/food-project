<script type="text/javascript" language="Javascript">

    var rowIndex = 0;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormReceiveProductsFromMarket").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormReceiveProductsFromMarket").validationEngine('attach');
//        reset_form("#gFormReceiveProductsFromMarket");
        $("#jqgrid-grid-receiveProductsFromMarket").jqGrid({
            %{--url: '${resource(dir:'receiveProductsFromMarket', file:'list')}',--}%
            datatype: "local",
            colNames: [
                'Product ID',
                'Invoice ID',
                'Product',
                'Product Code',
                'MR Type',
                'Batch Available',
                'Invoice No',
                'Batch',
                'Qty',
                'Reference',
                'Remarks',
                ''
            ],
            colModel: [
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'invoiceId', index: 'invoiceId', width: 0, hidden: true},
                {name: 'product', index: 'product', width: 200, align: 'left'},
                {name: 'code', index: 'code', width: 120, align: 'left'},
                {name: 'mrType', index: 'mrType', width: 150, align: 'left'},
                {name: 'batchAvailable', index: 'batchAvailable', width: 0, align: 'left', hidden: true},
                {name: 'invoiceNo', index: 'invoiceNo', width: 100, align: 'left'},
                {name: 'batch', index: 'batch', width: 80, align: 'left'},
                {
                    name: 'quantity', index: 'quantity', width: 55, align: 'right',
                    sorttype: 'number',
                    formatter: "number",
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                    editable: true,
                    editrules: {number: true}
                },
                {name: 'reference', index: 'reference', width: 80, align: 'left'},
                {name: 'remarks', index: 'reference', width: 80, align: 'left'},
                {name: 'delete', index: 'reference', width: 30, align: 'center', sortable: false}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-receiveProductsFromMarket',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Received Products From Market " + '<span class="mendatory_field">*</span>',
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditReceiveProductsFromMarket();
            }
        });
        $("#jqgrid-grid-receiveProductsFromMarket").jqGrid('navGrid', '#jqgrid-pager-receiveProductsFromMarket', {
            edit: false,
            add: false,
            del: false,
            search: false
        });
        initiate();
        checkSecondaryCustomer();
        loadProduct();
        $("#quantity").format({precision: 0, allow_negative: false, autofix: true});
    });

    function initiate() {
        var options = '<option value="">Please Select</option>';
        if ('${dpSize}' != '1') {
            if ('${dpSize}' != '0') {
                size = ${dpSize};
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
            }
        } else {
            options = '<option value="' + ${dpList}[0].id + '">' + ${dpList}[0].name + '</option>';
        }
        $("#receivingDp").html(options);
        options = '<option value="">Please Select</option>';
        if ('${wareHouseSize}' != '1') {
            if ('${wareHouseSize}' != '0') {
                size = ${wareHouseSize};
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${wareHouseList}[i].id + '">' + ${wareHouseList}[i].name + '</option>';
                }
            }
        } else {
            options = '<option value="' + ${wareHouseList}[0].id + '">' + ${wareHouseList}[0].name + '</option>';
        }
        $("#receivingInventory").html(options);
        options = '<option value="">Please Select</option>';
        if ('${subWareHouseSize}' != '1') {
            if ('${subWareHouseSize}' != '0') {
                size = ${subWareHouseSize};
                for (var i = 0; i < size; i++) {
                    options += '<option value="' + ${subWareHouseList}[i].id + '">' + ${subWareHouseList}[i].name + '</option>';
                }
            }
        } else {
            options = '<option value="' + ${subWareHouseList}[0].id + '">' + ${subWareHouseList}[0].name + '</option>';
        }
        $("#receivingSubInventory").html(options);
    }

    function checkSecondaryCustomer() {
        jQuery('#searchKeySec').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var geo = 0;
                if ($("#receivingDp").val() != '') {
                    geo = $("#receivingDp").val();
                }
                var data = {searchKey: request.term, id: geo, cat: '3'};
                var url = '${resource(dir:'marketReturn', file:'fetchCustomer')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
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
                var url = '';
                if(document.getElementById('batchAvailable').checked){
                    if($("#invoice").val() == ''){
                        return false;
                    }
                    url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1 + '&invoice=' + $("#invoice").val();
                }else{
                    url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + 1;
                }
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value, ui.item.code);
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

    function showInvoiceRow() {
        if (document.getElementById('batchAvailable').checked) {
            $("#batchAvailable").val(true);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCode").val('');
            $("#searchInvoiceKey").val('');
            $("#batch").val('');
            $("#invoice").val('');
            $("#invoiceRow").attr('hidden', false);
            loadInvoice();
            jQuery.ajax({
                type: 'post',
                url: '${resource(dir:'receiveProductsFromMarket', file:'listBatch')}?id=' +
                $("#secondaryCustomer").val() + '&entId=' + 1,
                success: function (data, textStatus) {
                    var batch = data;
                    var options = '<option value="">Please Select</option>';
                    for (var i = 0; i < batch.length; i++) {
                        options += '<option value="' + batch[i].batch_no + '">' + batch[i].batch_no + '</option>';
                    }
                    $("#batch").html(options);
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
        } else {
            $("#batchAvailable").val(false);
            $("#searchProductKey").val('');
            $("#finishProduct").val('');
            $("#productCode").val('');
            $("#searchInvoiceKey").val('');
            $("#batch").val('');
            $("#invoiceRow").attr('hidden', true);
            $("#invoice").val('');
        }
    }

    function loadInvoice() {
        jQuery('#searchInvoiceKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'receiveProductsFromMarket', file:'listInvoice')}?id=' + $("#secondaryCustomer").val();
                DocuAutoComplete.setSpinnerSelector('searchInvoiceKey').execute(response, url, data, function (item) {
                    item['label'] = item['code'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setInvoiceInformation(ui.item.id, ui.item.code);
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
        popupSecCustomerListPanel: function () {
            if ($("#receivingDp").val() == '') {
                return false;
            }
            var url = '${resource(dir:'marketReturn', file:'popupCustomerListPanel')}?id=' + $("#receivingDp").val()
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
            $("#customerMaster").val(customerCoreInfoId);
            $("#secondaryCustomerCode").val(code);
        },
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '';
            if(document.getElementById('batchAvailable').checked){
                if($("#invoice").val() == ''){
                    return false;
                }
                url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1 + '&invoice=' + $("#invoice").val();
            }else{
                url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + 1;
            }
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (productInfoId, productInfo, code, batch, stock, quantity) {
            $("#searchProductKey").val(productInfo);
            $("#finishProduct").val(productInfoId);
            $("#productCode").val(code);
            $("#batch").val(batch);
        },
        popupInvoiceListPanel: function () {
            var url = '${resource(dir:'receiveProductsFromMarket', file:'popupInvoiceListPanel')}?id=' + $("#secondaryCustomer").val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setInvoiceInformation: function (productInfoId, productInfo, quantity, batch, stock) {
            $("#searchInvoiceKey").val(productInfo);
            $("#invoice").val(productInfoId);
        }
    };

    function addItemToGrid() {
        var myGrid = $("#jqgrid-grid-receiveProductsFromMarket");
        var colData = myGrid.jqGrid('getDataIDs');
        var productId = $("#finishProduct").val();
        var quantity = $('#quantity').val();
        var hasBatch = $('#batchAvailable').val();
        var mrType = $('#mrType').val();
        var invoice = '';
        var batch = '';
        if (productId == '' || quantity == '' || mrType == '') {
            MessageRenderer.render({
                messageTitle: 'Missing Info',
                type: 2,
                messageBody: 'Please fill the required information before adding to grid.'
            });
            return false;
        }
        if (document.getElementById('batchAvailable').checked) {
            invoice = $('#invoice').val();
            batch = $('#batch').val();
            jQuery.ajax({
                type: 'post',
                url: '${resource(dir:'receiveProductsFromMarket', file:'checkIntegrity')}?invoice=' +
                invoice + '&batch=' + batch + '&quantity=' + quantity + '&productId=' + productId,
                success: function (data, textStatus) {
                    if (data.type != 1) {
                        MessageRenderer.render(data);
                    } else {
                        var dataItem = {
                            productId: productId,
                            invoiceId: $("#invoice").val(),
                            product: $("#searchProductKey").val(),
                            code: $("#productCode").val(),
                            mrType: mrType,
                            batchAvailable: hasBatch,
                            invoiceNo: $("#searchInvoiceKey").val(),
                            batch: batch,
                            quantity: quantity,
                            reference: $('#reference').val(),
                            remarks: $('#remarks').val(),
                            delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                        };
                        myGrid.addRowData(rowIndex, dataItem, "last");
                        rowIndex++;
                        $("#finishProduct").val("");
                        $("#searchProductKey").val("");
                        $("#productCode").val("");
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
                },
                dataType: 'json'
            });
        } else {
            var dataItem = {
                productId: productId,
                invoiceId: $("#invoice").val(),
                product: $("#searchProductKey").val(),
                code: $("#productCode").val(),
                mrType: mrType,
                batchAvailable: hasBatch,
                invoiceNo: invoice,
                batch: batch,
                quantity: quantity,
                reference: $('#reference').val(),
                remarks: $('#remarks').val(),
                delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
            };
            myGrid.addRowData(rowIndex, dataItem, "last");
            rowIndex++;
            $("#finishProduct").val("");
            $("#searchProductKey").val("");
            $("#productCode").val("");
        }


        return true;
    }

    function deleteItemFromGrid(id) {
        var myGrid = $("#jqgrid-grid-receiveProductsFromMarket");
        myGrid.jqGrid('delRowData', id);
    }

    function executePreConditionReceiveProductsFromMarket() {
        // trim field vales before process.
        trim_form();
        if ($("#gFormReceiveProductsFromMarket").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxReceiveProductsFromMarket() {
        if (!executePreConditionReceiveProductsFromMarket()) {
            return false;
        }
        var actionUrl = null;
        actionUrl = "${request.contextPath}/${params.controller}/save";
        var data = $("#gFormReceiveProductsFromMarket").serializeArray();
        $('#jqgrid-grid-receiveProductsFromMarket').jqGrid("editCell", 0, 0, false);
        var gd = $("#jqgrid-grid-receiveProductsFromMarket").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].finishProduct.id', 'value': gd[i].productId});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].mrType', 'value': gd[i].mrType});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].batchAvailable', 'value': gd[i].batchAvailable});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].invoice.id', 'value': gd[i].invoiceId});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].batch', 'value': gd[i].batch});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].reference', 'value': gd[i].reference});
            data.push({'name': 'items.receiveProductsFromMarketDetails[' + i + '].remarks', 'value': gd[i].remarks});
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionReceiveProductsFromMarket(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-receiveProductsFromMarket").trigger("reloadGrid");
                    clear_form('#gFormReceiveProductsFromMarket');
                    $("#batchAvailable").val(false);
                    $("#invoiceRow").attr('hidden', true);
                    $("#jqgrid-grid-receiveProductsFromMarket").jqGrid("clearGridData");

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
    function executePostConditionReceiveProductsFromMarket(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-receiveProductsFromMarket").trigger("reloadGrid");
            clear_form('#gFormReceiveProductsFromMarket');
            $("#batchAvailable").val(false);
            $("#invoiceRow").attr('hidden', true);
        }
        MessageRenderer.render(result);
        $("#jqgrid-grid-receiveProductsFromMarket").jqGrid("clearGridData");
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
        $("#finishProduct").val("");
        $("#searchProductKey").val("");
        $("#productCode").val("");
        $("#batch").val('');
    }

    function listWarehouse(){
        var options = '<option value="">Please Select</option>';
        $("#receivingSubInventory").html(options);
        if($("#receivingDp").val() == ''){
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'receiveProductsFromMarket', file:'listWarehouse')}?dpId=' + $("#receivingDp").val(),
            success: function (data, textStatus) {
                var batch = data;
                for (var i = 0; i < batch.length; i++) {
                    options += '<option value="' + batch[i].id + '">' + batch[i].name + '</option>';
                }
                $("#receivingInventory").html(options);
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
        if($("#receivingInventory").val() == ''){
            return false;
        }
        jQuery.ajax({
            type: 'post',
            url: '${resource(dir:'receiveProductsFromMarket', file:'listSubWarehouse')}?id=' + $("#receivingInventory").val(),
            success: function (data, textStatus) {
                var batch = data;
                var options = '<option value="">Please Select</option>';
                for (var i = 0; i < batch.length; i++) {
                    options += '<option value="' + batch[i].id + '">' + batch[i].name + '</option>';
                }
                $("#receivingSubInventory").html(options);
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