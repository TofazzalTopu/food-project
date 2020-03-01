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

        loadProduct(${primaryDemandOrder?.customerId});
        readCustomerBalanceAndShippingAddress(${primaryDemandOrder?.customerId});
        $("#quantity").format({precision: 2, allow_negative: false, autofix: true});

        $("#gFormNewPrimaryDemandOrder").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormNewPrimaryDemandOrder").validationEngine('attach');

        $("#dateProposedDelivery").datepicker({
            dateFormat: '${ApplicationConstants.JQUERY_UI_DATE_FORMAT}',
            minDate: new Date()
        }).attr("readonly", "readonly");

        if (${distributionPointList.size() == 0}) {
            $('#distributionPoint').removeClass("validate[required]");
        } else {
            $('#distributionPoint').addClass("validate[required]");
        }

        jqGridProductList = $("#jqgrid-grid-finishProduct").jqGrid({
            url: '',
            datatype: "local",
            colNames: [
                'ID',
                'primaryDemandOrderDetailsId',
                'Product Code',
                'Product Name',
                'Quantity',
                'Rate',
                'Amount',
                ''
            ],
            colModel: [
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'primaryDemandOrderDetailsId', index: 'primaryDemandOrderDetailsId', hidden: true},
                {name: 'productCode', index: 'productCode', width: 150, align: 'center'},
                {name: 'productName', index: 'productName', width: 250, align: 'left'},
                {
                    name: 'quantity',
                    index: 'quantity',
                    width: 100,
                    align: 'center',
                    sorttype: 'number',
                    formatter: "number",
                    editable: true,
                    editrules: {number: true}
                },
                {
                    name: 'rate',
                    index: 'rate',
                    width: 80,
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
                {name: 'delete', index: 'delete', width: 30, align: 'center', editable: false, sortable: false}
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
            cellEdit: true,
            cellsubmit: 'clientArray',
            cellurl: null,
            footerrow: true,
            gridComplete: function () {
                calculateSummaryData();
            },
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
//                executeEditSaleItem();
            },
            afterSaveCell: function (rowid, name, val, iRow, iCol) {
                var rowData = $("#jqgrid-grid-finishProduct").jqGrid('getRowData', rowid);
                rowData.amount = rowData.quantity * rowData.rate;
                $('#jqgrid-grid-finishProduct').jqGrid('setRowData', rowid, rowData);
                calculateSummaryData();
            },
            loadError: function (jqXHR, textStatus, errorThrown) {
                MessageRenderer.renderErrorText(jqXHR.responseText, '');
            }
        });
        loadPrimaryDemandOrderDetails($('#gFormNewPrimaryDemandOrder input[name = id]').val());
    });
    function getSelectedPrimaryDemandOrderId() {
        return $('#gFormNewPrimaryDemandOrder input[name = id]').val();
    }
    function executePreConditionPrimaryDemandOrder() {
        trim_form();
        if (!$("#gFormNewPrimaryDemandOrder").validationEngine('validate')) {
            return false;
        }
        $('#jqgrid-grid-finishProduct').jqGrid("editCell", 0, 0, false);
        return true;
    }
    function executeAjaxNewPrimaryDemandOrder() {
        if (!executePreConditionPrimaryDemandOrder()) {
            return false;
        }
        var data = $("#gFormNewPrimaryDemandOrder").serializeArray();
        var gd = $("#jqgrid-grid-finishProduct").jqGrid('getRowData');

        var shippingAddress = $("#shippingAddress").val();
        var shipToAddress = $("#shipToAddress").val();
        var isShip = $("#ship").is(':checked');
        if (isShip) {
            data.push({'name': 'shippingAddressId', 'value': $("#shippingAddress").val()});
            data.push({'name': 'ship', 'value': 'ship'});

            if (!shippingAddress) {
                MessageRenderer.renderErrorText("Please select ship to address!");
                return;
            }
        }
        if(shipToAddress && !shippingAddress){
            MessageRenderer.renderErrorText("You can not remove ship to address!");
            return;
        }
        var length = gd.length;
        for (var i = 0; i < length; ++i) {
            data.push({'name': 'items.primaryDemandOrderDetails[' + i + '].finishProduct.id', 'value': gd[i].id});
            data.push({
                'name': 'items.primaryDemandOrderDetails[' + i + '].id',
                'value': gd[i].primaryDemandOrderDetailsId
            });
            data.push({'name': 'items.primaryDemandOrderDetails[' + i + '].rate', 'value': gd[i].rate});
            data.push({
                'name': 'items.primaryDemandOrderDetails[' + i + '].primaryDemandOrder.id',
                'value': $('#gFormNewPrimaryDemandOrder input[name = id]').val()
            });
            data.push({'name': 'items.primaryDemandOrderDetails[' + i + '].quantity', 'value': gd[i].quantity});
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: data,
            url: "${request.contextPath}/${params.controller}/updateNew",
            success: function (data, textStatus) {
                executePostConditionPrimaryDemandOrder(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    clean_form('#gFormNewPrimaryDemandOrder');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else {
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

    function executePostConditionPrimaryDemandOrder(result) {
        if (result.type == 1) {
//            $("#jqgrid-grid").trigger("reloadGrid");
            clean_form('#gFormNewPrimaryDemandOrder');
        }
        MessageRenderer.render(result);
    }

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function () {
            var url = '${resource(dir:'primaryDemandOrder', file:'popupCustomerListPanel')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            $("#searchKey").val(customerCoreInfo);
            $("#customerId").val(customerCoreInfoId);
            CustomerInfo.customerCoreInfoId = customerCoreInfoId;
        },
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'primaryDemandOrder', file:'popupProductListPanel')}';
            var params = {id: customerCoreInfoId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (productInfoId, productInfo) {
            $("#searchProductKey").val(productInfo);
            $("#productId").val(productInfoId);
        }
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
                var data = {searchKey: request.term};
                var url = '${resource(dir:'secondaryDemandOrder', file:'listProduct')}?id=' + customerId + "&query=" + $('#searchProductKey').val();
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = "[" + item['code'] + "] " + item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value);
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

    function addFinishProductToGrid() {
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

        var totalQuantity = Number($("#quantity").val());
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
                    'primaryDemandOrderDetailsId': '',
                    'productCode': productCode.toString(),
                    'productName': productName.toString(),
                    'quantity': totalQuantity.toString(),
                    'rate': unitPrice.toString(),
                    'amount': amount.toString(),
                    'delete': '<a  href="javascript:deleteProduct(' + productId + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
                }
            ];
            jqGridProductList.addRowData(productId.toString(), newRowData);
            $("#jqgrid-grid-finishProduct [id*='undefined']").attr('id', productId.toString());
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
        $("#jqgrid-grid-finishProduct").jqGrid('clearGridData');
    }

    function loadPrimaryDemandOrderDetails(primaryOrderId) {
        jQuery("#jqgrid-grid-finishProduct").jqGrid().setGridParam({
            url: "${resource(dir:'primaryDemandOrder', file:'loadPrimaryDemandOrderDetails')}?"
            + "demandOrderId=" + primaryOrderId,
            mtype: "POST",
            page: 1,
            datatype: "json"
        }).trigger("reloadGrid")
    }

    function deleteProduct(productId) {
        $('#jqgrid-grid-finishProduct').jqGrid('delRowData', productId);
    }
    function readCustomerBalanceAndShippingAddress(customerId) {
        var options = '<option value=""></option>';
        $("#advanceAmount").val("");
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: "${request.contextPath}/${params.controller}/readCustomerBalanceAndAddress",
            success: function (data, textStatus) {
                $("#advanceAmount").val(data.balance);

               /* if(data.isOwn == "false"){
                    $('input:radio[name="shipTo"]').filter('[value="ShipAddress"]').attr('checked', true);
                }else{
                    $('input:radio[name="shipTo"]').filter('[value="Own"]').attr('checked', true);
                }
                */
                var length = data.addressList.length;
                if (length > 0) {
                    for (var i = 0; i < length; i++) {
                        options += '<option value="' + data.addressList[i].id + '">' + data.addressList[i].address + '</option>';
                    }
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (XMLHttpRequest.status = 0) {
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                } else {
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                $("#shippingAddress").html(options);
                $("#shippingAddress").val(${primaryDemandOrder.shippingAddressId});
                $("#shipToAddress").val(${primaryDemandOrder.shippingAddressId});
                setAddress(${primaryDemandOrder.shippingAddressId})
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function setAddress(shipToAddress){
        var address = $("#shipToAddress").val();
        if(address) {
         $('input:radio[id="ship"]').attr('checked', true);
         $('input:radio[id="own"]').attr('checked', false);
         } else {
         $('input:radio[id="own"]').attr('checked', true);
         $('input:radio[id="ship"]').attr('checked', false);
         }
    }
</script>