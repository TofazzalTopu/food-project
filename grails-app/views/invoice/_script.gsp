<%@ page import="com.bits.bdfp.util.ApplicationConstants; com.docu.commons.CommonConstants" %>
<script type="text/javascript" language="Javascript">
    var jqGridProductList = null;
    $(document).ready(function () {
        $('#ui-widget-header-text').html('Primary Demand Order');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $(".amount").format({precision: 2, allow_negative: false, autofix: true});

        $("#gFormInvoice").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormInvoice").validationEngine('attach');
        $("#printInvoice-button").hide();
        $("#printMR-button").hide();
        $("#dateProposedDelivery").datepicker({
            dateFormat: '${ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            minDate: new Date()
        }).attr("readonly", "readonly");
        loadCustomerForPrimaryDemandOrder();
        var customerId = $("#customerId").val();
        if (customerId) {
            loadProduct(customerId);
        }
        disableCustomerSelection();
        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'ID',
                'internalData',
                'Product Code',
                'Product Name',
                'Quantity',
                'Rate',
                'Amount',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'internalData', index: 'internalData', hidden: true},
                {name: 'productCode', index: 'productCode', width: 150, align: 'left'},
                {name: 'productName', index: 'productName', width: 200, align: 'left'},
                {name: 'quantity', index: 'quantity', width: 100, align: 'left', sorttype: 'number'},
                {
                    name: 'rate',
                    index: 'rate',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {
                    name: 'amount',
                    index: 'amount',
                    width: 100,
                    align: 'right',
                    formatter: 'currency',
                    formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 2}
                },
                {name: 'delete', index: 'delete', width: 30, align: 'center', sortable: false}
            ],
            rowNum: -1,
            viewrecords: true,
            caption: "Product List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            gridview: true,
//            multiselect: true,
            rownumbers: true,
//            cellEdit: true,
//            cellsubmit: 'clientArray',
//            cellurl: null,
            footerrow: true,
            gridComplete: function () {
                calculateSummaryData();
            },
            onSelectRow: function (rowid, status) {
//                executeEditSaleItem();
            },
            afterSaveCell: function (rowid, name, val, iRow, iCol) {
//                var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', rowid);
//                rowData.amount = rowData.quantity * rowData.rate;
//                $('#jqgrid-grid-finishProduct').jqGrid('setRowData', rowid, rowData);
//                calculateSummaryData();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText, '');
            }
        });

        $("#jqgrid-grid-product-batch").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'SL',
                'id',
                '&#10004;',
                'Available Batches',
                'Available Quantity',
                'Batch Date',
                'Pick Qty'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30},
                {name: 'id', index: 'id', width: 30, hidden: true},
                {name: 'checkBatch', index: 'checkBatch', width: 30, hidden: true},
                {name: 'batch_no', index: 'batch_no', width: 100},
                {name: 'quantity', index: 'quantity', width: 100, align: 'left'},
                {name: 'batch_date', index: 'batch_date', width: 100, align: 'left'},
                {
                    name: 'pickQty',
                    index: 'pickQty',
                    width: 100,
                    sorttype: 'number',
                    formatter: "number",
                    editable: true,
                    editrules: {number: true}
                }
            ],
            rowNum: -1,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Batch Information",
            autowidth: true,
            height: true,
            width: 500,
            scrollOffset: 0,
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow: true,
            loadComplete: function () {
                calculateBatchSummary();
            },
            onSelectRow: function (rowid, status) {
            },
            beforeSaveCell: function (rowid, celname, value, iRow, iCol) {
                var rowData = $("#jqgrid-grid-product-batch").jqGrid('getRowData', rowid);
                if (rowData.quantity < value) {
                    MessageRenderer.renderErrorText("Pick Quantity cannot be greater than Available Quantity");
                    rowData.pickQty = "0.00";
                    $('#jqgrid-grid-product-batch').jqGrid('setRowData', rowid, rowData);
                    return '0.00'
                }
            },
            afterSaveCell: function (rowid, name, val, iRow, iCol) {
                calculateBatchSummary();
            }
        });
        $("#transactionDate").datepicker(
                {
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
//                    minDate: 0
                });
        $('#transactionDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
    });

    function calculateBatchSummary() {
        var $grid = $('#jqgrid-grid-product-batch');
        var sumQuantity = $grid.jqGrid('getCol', 'quantity', false, 'sum');
        $grid.jqGrid('footerData', 'set', {'quantity': sumQuantity});
        var sumPickQuantity = $grid.jqGrid('getCol', 'pickQty', false, 'sum');
        $grid.jqGrid('footerData', 'set', {'pickQty': sumPickQuantity});
        $grid.jqGrid('footerData', 'set', {'batch_no': 'Total'});
    }

    function deleteProduct(productId) {
        $('#jqgrid-grid-finishProduct').jqGrid('delRowData', productId);
    }
    function getSelectedPrimaryDemandOrderId() {
        return $('#gFormInvoice input[name = id]').val();
    }
    function executePreConditionInvoice() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormInvoice").validationEngine('validate')) {
            return false;
        }
        $('#jqgrid-grid-finishProduct').jqGrid("editCell", 0, 0, false);
        return true;
    }
    function executeAjaxInvoice() {
        if (!executePreConditionInvoice()) {
            return false;
        }
        if(parseFloat($("#paymentReceived").val()) != parseFloat($("#netTotalValue").val())){
            MessageRenderer.render({
                messageTitle: 'Inconsistent Info',
                type: 2,
                messageBody: 'Please make sure that Net Total and Payment Received are same.'
            });
            return false;
        }
        var data = $("#gFormInvoice").serializeArray();
        var gd = $("#jqgrid-grid-finishProduct").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            data.push({'name': 'invoiceDetails_' + i + '_finishProductId', 'value': gd[i].id});
            data.push({'name': 'invoiceDetails_' + i + '_batchData', 'value': gd[i].internalData});
            data.push({'name': 'invoiceDetails_' + i + '_unitPrice', 'value': gd[i].rate});
            data.push({'name': 'invoiceDetails_' + i + '_quantity', 'value': gd[i].quantity});
        }
        data.push({'name': 'productCount', 'value': length});
        var actualOtherChargeValue = Number($("#actualOtherChargeValue").val());
        if (actualOtherChargeValue > 0.00) {
            data.push({'name': 'otherChargeName', 'value': $("#actualOtherChargeLabel").html()});
        }
        var actualDiscountValue = Number($("#actualDiscountValue").val());
        if (actualDiscountValue > 0.00) {
            data.push({'name': 'discountName', 'value': $("#actualDiscountLabel").html()});
        }
        var actualVatValue = Number($("#actualVatValue").val());
        if (actualVatValue > 0.00) {
            data.push({'name': 'vatTaxName', 'value': $("#actualVatLabel").html()});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/create",
            success: function (data, textStatus) {
                executePostConditionInvoice(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#printInvoice-button").show();
                    $("#printMR-button").show();
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

    function executePostConditionInvoice(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid").trigger("reloadGrid");
            var messageParts = result.messageBody[0].split(':');
            if (messageParts.length > 1) {
                $("#invoiceNo").val(messageParts[1])
            }
            $("#printInvoice-button").show();
            $("#printMR-button").show();
//            clean_form('#gFormInvoice');
        }
        MessageRenderer.render(result);
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var url = '${resource(dir:'invoice', file:'popupCustomerListPanel')}';
            var params = {isPosCustomer: $("#isPosCustomer").val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            $("#invoiceNo").val("");
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        },
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'invoice', file:'popupProductListPanel')}';
            var params = {customerId: customerCoreInfoId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (productInfoId, productInfo) {
            $("#searchProductKey").val(productInfo);
            $("#productId").val(productInfoId);
        }
    };

    function loadCustomerForPrimaryDemandOrder() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                var data = {searchKey: request.term, isPosCustomer: $("#isPosCustomer").val()};
                var url = '${resource(dir:'invoice', file:'customerAutoComplete')}';
                DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
                loadProduct(ui.item.id);
                $("#customerId").val(ui.item.id);
                $("#invoiceNo").val("");
                $("#customerNumber").val(ui.item.code);
                $("#customerName").val(ui.item.name);
                $("#customerAddress").val(ui.item.present_address);
                $('#searchProductKey').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var custometDetailts = "";
            if (item) {
                custometDetailts = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + "-" + ", Category: " + item.category + "-" + ", Address: " + item.present_address + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + custometDetailts).appendTo(ul);
        };
    }

    $('#search-btn-customer-register-id').click(function () {
        CustomerInfo.popupCustomerListPanel();
    });

    function loadProduct(customerId) {
        $('#searchProductKey').blur(function () {
            if ($('#searchProductKey').val() == '') {
                $("#productId").val('');
            }
        });
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'finishProduct', file:'listProductAutoComplete')}?customerId=' + customerId + "&query=" + $('#searchProductKey').val();
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);
                loadProductBatch(productId);
                $('#rate').val(ui.item.price);
                $('#productCode').val(ui.item.code);
                $('#productName').val(ui.item.name);
                $('#quantity').focus();
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + " Product Category: " + item.p_cat + ", Price: " + item.price + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    $('#search-btn-customer-product-id').click(function () {
        CustomerInfo.popupProductListPanel($("#customerId").val());
    });

    function addFinishProductToGrid(pickQuantity, internalData) {
        var customerId = $("#customerId").val();
        if (!customerId) {
            MessageRenderer.renderErrorText("Customer is not selected");
            return
        }
        var productId = $("#productId").val();
        if (!productId) {
            MessageRenderer.renderErrorText("ProductItem is not selected");
            return
        }

        var totalQuantity = 0.00

        if (pickQuantity > 0.00) {
            totalQuantity = pickQuantity
        } else {
            totalQuantity = Number($("#quantity").val());
        }
        if (totalQuantity <= 0) {
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
                {
                    'id': productId.toString(),
                    'internalData': internalData,
                    'productCode': productCode.toString(),
                    'productName': productName.toString(),
                    'quantity': totalQuantity.toString(),
                    'rate': unitPrice.toString(),
                    'amount': amount.toString(),
                    'delete': '<a  href="javascript:deleteProduct(' + productId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridProductList.addRowData(productId.toString(), newRowData[0]);
        } else {
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
        $grid.jqGrid('footerData', 'set', {'amount': sumAmount});
        $grid.jqGrid('footerData', 'set', {'productCode': 'Total'});
        calculateNetAmount();
    }

    function calculateNetAmount() {
        var $grid = $('#jqgrid-grid-finishProduct');
        var sumAmount = $grid.jqGrid('getCol', 'amount', false, 'sum');
        var otherCharges = Number($("#actualOtherChargeValue").val());
        if (otherCharges <= 0.00) {
            $("#actualOtherChargeLabel").html("");
        }
        var discountValue = Number($("#actualDiscountValue").val());
        if (discountValue <= 0.00) {
            $("#actualDiscountLabel").html("");
        }
        var vatValue = Number($("#actualVatValue").val());
        if (vatValue <= 0.00) {
            $("#actualVatLabel").html("");
        }
        $("#netTotalValue").val(sumAmount + otherCharges + vatValue - discountValue);
    }

    function clearProductItem() {
        $("#productId").val('');
        $("#productName").val('');
        $("#productCode").val('');
        $("#rate").val('');
        $("#quantity").val('');
        $('#searchProductKey').val('');
    }

    function clean_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = "";
            }
        });
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $("#printInvoice-button").hide();
        $("#printMR-button").hide();
        $("#jqgrid-grid-product-batch").jqGrid('clearGridData');
        $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
    }
    function addOtherCharges() {
        var chargeType = $("#chargeType").val();
        var otherChargeValue = 0.00;
        var otherChargePercentage = 0.00;
        var actualOtherChargeValue = Number($("#actualOtherChargeValue").val());
        var sumAmount = $('#jqgrid-grid-finishProduct').jqGrid('getCol', 'amount', false, 'sum');
        if (chargeType) {
            otherChargeValue = Number($("#otherChargeValue").val());
            if (otherChargeValue > 0.00) {
                $("#otherChargePercentage").val('');
            } else {
                otherChargePercentage = Number($("#otherChargePercentage").val());
                otherChargePercentage = (sumAmount * otherChargePercentage) / 100
            }
            if (otherChargeValue > 0.00 || otherChargePercentage > 0.00) {
                var totalOtherCharges = actualOtherChargeValue + otherChargeValue + otherChargePercentage
                $("#actualOtherChargeValue").val(totalOtherCharges.toFixed(2));
                var actualOtherChargeLabel = $("#actualOtherChargeLabel").html();
                var addOtherChargeLabel = $("#chargeType option:selected").text();
                if (actualOtherChargeLabel.length <= 0) {
                    $("#actualOtherChargeLabel").html(addOtherChargeLabel);
                } else if (!actualOtherChargeLabel.contains(addOtherChargeLabel)) {
                    $("#actualOtherChargeLabel").html(actualOtherChargeLabel + " + " + addOtherChargeLabel);
                }
            } else {
                MessageRenderer.renderErrorText("Enter Valid Charge")
            }
            calculateNetAmount();
        } else {
            MessageRenderer.renderErrorText("Select Charge Type")
        }
    }
    function addDiscount() {
        var discountType = $("#discountType").val();
        var discountValue = 0.00;
        var discountPercentage = 0.00;
        var actualDiscountValue = Number($("#actualDiscountValue").val());
        var sumAmount = $('#jqgrid-grid-finishProduct').jqGrid('getCol', 'amount', false, 'sum');
        if (discountType) {
            discountValue = Number($("#discountValue").val());
            if (discountValue > 0.00) {
                $("#discountPercentage").val('');
            } else {
                discountPercentage = Number($("#discountPercentage").val());
                discountPercentage = (sumAmount * discountPercentage) / 100
            }
            if (discountValue > 0.00 || discountPercentage > 0.00) {
                var totalDiscount = actualDiscountValue + discountValue + discountPercentage;
                $("#actualDiscountValue").val(totalDiscount.toFixed(2));
                var actualDiscountLabel = $("#actualDiscountLabel").html();
                var addDiscountLabel = $("#discountType option:selected").text();
                if (actualDiscountLabel.length <= 0) {
                    $("#actualDiscountLabel").html(addDiscountLabel);
                } else if (!actualDiscountLabel.contains(addDiscountLabel)) {
                    $("#actualDiscountLabel").html(actualDiscountLabel + " + " + addDiscountLabel);
                }
            } else {
                MessageRenderer.renderErrorText("Enter Valid Discount")
            }
            calculateNetAmount();
        } else {
            MessageRenderer.renderErrorText("Select Discount Type")
        }
    }

    function addVatTax() {
        var vatType = $("#vatType").val();
        var vatValue = 0.00;
        var vatPercentage = 0.00;
        var actualVatValue = Number($("#actualVatValue").val());
        var sumAmount = $('#jqgrid-grid-finishProduct').jqGrid('getCol', 'amount', false, 'sum');
        if (vatType) {
            vatValue = Number($("#vatValue").val());
            if (vatValue > 0.00) {
                $("#vatPercentage").val('');
            } else {
                vatPercentage = Number($("#vatPercentage").val());
                vatPercentage = (sumAmount * vatPercentage) / 100
            }
            if (vatValue > 0.00 || vatPercentage > 0.00) {
                var totalVat = actualVatValue + vatValue + vatPercentage;
                $("#actualVatValue").val(totalVat.toFixed(2));
                var actualVatLabel = $("#actualVatLabel").html();
                var addVatLabel = $("#vatType option:selected").text();
                if (actualVatLabel.length <= 0) {
                    $("#actualVatLabel").html(addVatLabel);
                } else if (!actualVatLabel.contains(addVatLabel)) {
                    $("#actualVatLabel").html(actualVatLabel + " + " + addVatLabel);
                }
            } else {
                MessageRenderer.renderErrorText("Enter Valid Vat")
            }
            calculateNetAmount();
        } else {
            MessageRenderer.renderErrorText("Select Vat Type")
        }
    }
    function loadProductBatch(productId) {
        $("#jqgrid-grid-product-batch").jqGrid('clearGridData');
        jQuery("#jqgrid-grid-product-batch").jqGrid().setGridParam({
            url: "${resource(dir:'invoice', file:'productBatchDetails')}?"
            + "id=" + productId + "&customerId=" + $("#customerId").val(),
            mtype: "POST",
            page: 1,
            datatype: "json"
        }).trigger("reloadGrid")
    }
    function addPickQuantity() {
        var $grid = $('#jqgrid-grid-product-batch');
        var internalData = "";
        var gd = $grid.jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            if (gd[i].pickQty > 0) {
                if (internalData.length > 0) {
                    internalData += ";"
                }
                internalData += gd[i].id + "_" + gd[i].pickQty;
            }
        }

        var sumPickQuantity = $grid.jqGrid('getCol', 'pickQty', false, 'sum');
        if (sumPickQuantity > 0.00) {
            addFinishProductToGrid(sumPickQuantity, internalData)
        } else {
            MessageRenderer.renderErrorText("Pick Quantity can’t be greater than available Quantity, Please Change Pick Qty")
        }
    }

    function changeProductSelectionMethod(object) {
        if (object.checked) {
            object.value = 'true';
            $("#add-pick-quantity").attr('disabled', true);
            $("#add-button").attr('disabled', false);
            $("#quantity").val("").attr('disabled', false);
        }
        else {
            object.value = 'false';
            $("#add-pick-quantity").attr('disabled', false);
            $("#add-button").attr('disabled', true);
            $("#quantity").val("").attr('disabled', true);
        }
    }

    function printInvoice() {
        var invoiceNo = $("#invoiceNo").val();
        if (!invoiceNo) {
            MessageRenderer.renderErrorText("Please Save Invoice");
            return
        }
        var customer = $("#customerNumber").val() + " " + $("#customerName").val() + "," + $("#customerAddress").val();
        window.open("${request.contextPath}/primaryDemandOrder/rptPrintInvoice?"
                + "invoiceNo=" + invoiceNo + "&customer=" + customer
        );
    }

    function printMR() {
        var invoiceNo = $("#invoiceNo").val();
        if (!invoiceNo) {
            MessageRenderer.renderErrorText("Please Save Invoice");
            return
        }
    }
    function addRemoveCheckBoxForPos(object) {
        if (object.checked) {
            object.value = 'true';
            disableCustomerSelection();
        }
        else {
            object.value = 'false';
            enableCustomerSelection()
        }
    }
    function enableCustomerSelection() {
        $("#searchKey").val("").attr("disabled", false);
        $("#search-btn-customer-register-id").show();
        $("#customerId").val("");
        $("#customerNumber").val("");
        $("#customerName").val("");
        $("#customerAddress").val("");
        $(".externalInfo").attr('hidden', true);
    }
    function disableCustomerSelection() {
        $("#searchKey").val("").attr("disabled", true);
        $("#search-btn-customer-register-id").hide();
        $("#customerId").val("${customer.customerId}");
        $("#customerNumber").val("${customer.customerCode}");
        $("#customerName").val("${customer.customerName}");
        $("#customerAddress").val("${customer.customerAddress}");
        $(".externalInfo").attr('hidden', false);
    }
    function cleanForm(){
        $("#externalCustomerName").val("");
        $("#externalCustomerMobile").val("");
        $("#externalCustomerAddress").val("");
        $("#actualOtherChargeValue").val("");
        $("#actualDiscountValue").val("");
        $("#actualVatValue").val("");
        $("#actualOtherChargeLabel").val("");
        $("#actualDiscountLabel").val("");
        $("#actualVatLabel").val("");
        $("#paymentReceived").val("0.00");
        $("#transactionNo").val("");
        $("#reference").val("");
        $("#remarks").val("");


        $("#jqgrid-grid-product-batch").jqGrid('clearGridData');
        $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
        calculateBatchSummary();
    }
</script>