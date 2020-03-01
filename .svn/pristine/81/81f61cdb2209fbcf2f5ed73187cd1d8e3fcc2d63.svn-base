<script type="text/javascript" language="Javascript">

    var rowIndex = -1;

    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormQuotation").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormQuotation").validationEngine('attach');
//        reset_form("#gFormQuotation");
    });

    function renderPage(val) {
        var url = '';
        if (val == 0) {
            url = '${resource(dir:'quotation', file:'loadPage')}';
        } else {
            if ($('#quotationNo').val() == '') {
                return false;
            }
            url = '${resource(dir:'quotation', file:'loadPage')}?fieldName=quotationNo&fieldValue=' + $('#quotationNo').val();
        }
        jQuery.ajax({
            type: 'post',
            url: url,
            success: function (data, textStatus) {
                if (data) {
                    $('#createDiv').html(data);
                    loadGrid(val);
                    loadProduct();
                } else {
                    MessageRenderer.render({
                        messageTitle: 'Info Error',
                        type: 2,
                        messageBody: 'Quotation No: ' + $('#quotationNo').val() + ' Not Found.'
                    });
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function (data) {
                if (val != 0) {
                    $("#date").val($("#quotationDate").val());
                    $("#save-button-quotation").val('Update');
                }
            },
            dataType: 'html'
        });
    }

    function loadGrid(val) {
        setDateRangeNoLimit('validFrom', 'validTo');
        $('#refDate,#quotationDate').datepicker({
            dateFormat: 'dd-mm-yy',
            changeMonth: true,
            changeYear: true
        });
        $('#refDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#validFrom').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#validTo').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        $('#quotationDate').mask('${com.docu.commons.CommonConstants.DATE_MASK_FORMAT}', {});
        var url = '';
        if (val != 0) {
            $("#jqgrid-grid-quotation").jqGrid({
                url: '${resource(dir:'quotation', file:'list')}?id=' + $("#id").val(),
                datatype: "json",
                colNames: [
                    'ID',

                    'Product ID',
                    'Product Name',
                    'Code',
                    'Quantity',
                    'Unit Price',
                    'Total Price',
                    'Delete'
                ],
                colModel: [
                    {name: 'id', index: 'id', width: 0, hidden: true},
                    {name: 'productId', index: 'productId', width: 0, hidden: true},
                    {name: 'productName', index: 'productName', width: 100, align: 'left'},
                    {name: 'code', index: 'code', width: 100, align: 'left'},
                    {
                        name: 'quantity', index: 'quantity', width: 80, align: 'left', sorttype: 'number',
                        formatter: "number",
                        formatoptions: {decimalSeparator: ".", thousandsSeparator: ",", decimalPlaces: 0},
                        editable: true,
                        editrules: {number: true}
                    },
                    {name: 'rate', index: 'rate', width: 80, align: 'right'},
                    {name: 'price', index: 'price', width: 80, align: 'right'},
                    {name: 'delete', index: 'delete', width: 40, align: 'center'}
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#jqgrid-pager-quotation',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "Product List <span class='mendatory_field'>*</span>",
                width: 730,
                height: 100,
                scrollOffset: 0,
                altRows: true,
                cellEdit: true,
                cellsubmit: 'clientArray',
                cellurl: null,
                afterSaveCell: function (id, name, val, iRow, iCol) {
                    calculatePrice(id);
                },
                loadComplete: function () {
                },
                onSelectRow: function (rowid, status) {
//                executeEditQuotation();
                }
            });
        } else {
            $("#quotationDate").datepicker('setDate', new Date());
            $("#isActive").attr('checked', true);
            $("#jqgrid-grid-quotation").jqGrid({
                datatype: "json",
                colNames: [
                    'ID',

                    'Product ID',
                    'Product Name',
                    'Code',
                    'Quantity',
                    'Unit Price',
                    'Total Price',
                    'Delete'
                ],
                colModel: [
                    {name: 'id', index: 'id', width: 0, hidden: true},
                    {name: 'productId', index: 'productId', width: 0, hidden: true},
                    {name: 'productName', index: 'productName', width: 100, align: 'left'},
                    {name: 'code', index: 'code', width: 100, align: 'left'},
                    {name: 'quantity', index: 'quantity', width: 80, align: 'left'},
                    {name: 'rate', index: 'rate', width: 80, align: 'right'},
                    {name: 'price', index: 'price', width: 80, align: 'right'},
                    {name: 'delete', index: 'delete', width: 40, align: 'center'}
                ],
                rowNum: 10,
                rowList: [10, 20, 30],
                pager: '#jqgrid-pager-quotation',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                caption: "Product List <span class='mendatory_field'>*</span>",
                width: 730,
                height: 100,
                scrollOffset: 0,
                altRows: true,
                loadComplete: function () {
                },
                onSelectRow: function (rowid, status) {
//                executeEditQuotation();
                }
            });
        }
    }

    function calculatePrice(id) {
        var rowData = $("#jqgrid-grid-quotation").jqGrid('getRowData', id);
        rowData.price = parseFloat(rowData.rate) * parseFloat(rowData.quantity);
        $('#jqgrid-grid-quotation').jqGrid('setRowData', id, rowData);
    }

    function loadProduct() {
        jQuery('#searchProductKey').autocomplete({
            minLength: '1',
            source: function (request, response) {
                var data = {searchKey: request.term};
                var url = '${resource(dir:'receiveProductsFromMarket', file:'listProduct')}?entId=' + $('#enterpriseConfiguration').val();
                DocuAutoComplete.setSpinnerSelector('searchProductKey').execute(response, url, data, function (item) {
                    item['label'] = item['name'];
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                CustomerInfo.setProductInformation(ui.item.id, ui.item.value, ui.item.code, ui.item.stock_id);
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

    var CustomerInfo = {
        customerCoreInfoId: null,
        popupProductListPanel: function (customerCoreInfoId) {
            var url = '${resource(dir:'receiveProductsFromMarket', file:'popupProductListPanel')}?entId=' + $('#enterpriseConfiguration').val();
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (productInfoId, productInfo, code) {
            $("#searchProductKey").val(productInfo);
            $("#finishProduct").val(productInfoId);
            $("#productCode").val(code);
        }
    };

    function addItem() {
        var myGrid = $("#jqgrid-grid-quotation");
        var total = parseFloat($("#quantity").val()) * parseFloat($("#rate").val());
        var dataItem = {
            id: '',
            productId: $("#finishProduct").val(),
            productName: $("#searchProductKey").val(),
            code: $("#productCode").val(),
            quantity: $("#quantity").val(),
            rate: $("#rate").val(),
            price: total,
            delete: '<a  href="javascript:deleteItemFromGrid(' + rowIndex + ')" class="ui-icon ui-icon-trash" title="Delete"></a>'
        };
        myGrid.addRowData(rowIndex, dataItem, "last");
        rowIndex--;
    }

    function deleteItemFromGrid(id) {
        var myGrid = $("#jqgrid-grid-quotation");
        var detail = myGrid.jqGrid('getCell', id, 'id');
        if (detail != '') {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-quotation").dialog({
                resizable: false,
                height: 150,
                modal: true,
                title: 'Delete alert',
                buttons: {
                    'Delete Item': function () {
                        $(this).dialog('close');
                        jQuery.ajax({
                            type: 'post',
                            url: '${resource(dir:'quotation', file:'delete')}?id=' + id,
                            success: function (data, textStatus) {
                                MessageRenderer.render(data);
                                $("#jqgrid-grid-quotation").trigger("reloadGrid");
                            },
                            error: function (XMLHttpRequest, textStatus, errorThrown) {
                                if(XMLHttpRequest.status = 0){
                                    MessageRenderer.renderErrorText("Network Problem: Time out");
                                }else{
                                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                                }
                            },
                            complete: function (data) {
                            },
                            dataType: 'json'
                        });
                    },
                    Cancel: function () {
                        $(this).dialog('close');
                    }
                }
            });
        } else {
            myGrid.jqGrid('delRowData', id);
        }
    }

    function executePreConditionQuotation() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormQuotation").validationEngine('validate')) {
            return false;
        }
        return true;
    }

    function executeAjaxQuotation() {
        if (!executePreConditionQuotation()) {
            return false;
        }
        var actionUrl = null;
        var data = $("#gFormQuotation").serializeArray();
        var gd = $("#jqgrid-grid-quotation").jqGrid('getRowData');
        var length = gd.length;
        for (var i = 0; i < length; i++) {
            if (gd[i].id != '') {
                data.push({'name': 'items.quotationDetails[' + i + '].id', 'value': gd[i].id});
            }
            data.push({'name': 'items.quotationDetails[' + i + '].finishProduct.id', 'value': gd[i].productId});
            data.push({'name': 'items.quotationDetails[' + i + '].rate', 'value': gd[i].rate});
            data.push({'name': 'items.quotationDetails[' + i + '].quantity', 'value': gd[i].quantity});
            data.push({'name': 'items.quotationDetails[' + i + '].total', 'value': gd[i].price});
        }
        data.push({'name': 'enterpriseConfiguration.id', 'value': $("#enterpriseConfiguration").val()});

        if ($("#save-button-quotation").val() == 'Create') {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        }
        jQuery.ajax({
            type: 'post',
            data: data,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionQuotation(data);
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
        return false;
    }

    function executePostConditionQuotation(result) {
        if (result.type == 1) {
            $('#createDiv').html('');
//            reset_form('#gFormQuotation');
        }
        MessageRenderer.render(result);
    }

    function executePreConditionForEditQuotation(quotationId) {
        if (quotationId == null) {
            alert("Please select a quotation to edit");
            return false;
        }
        return true;
    }
    function executeEditQuotation() {
        var quotationId = getSelectedQuotationId();
        if (executePreConditionForEditQuotation(quotationId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'quotation', file:'edit')}?id=" + quotationId,
                success: function (entity) {
                    executePostConditionForEditQuotation(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditQuotation(data) {
        if (data == null) {
            alert('Selected quotation might have been deleted by someone');  //Message renderer
        } else {
            showQuotation(data);
        }
    }

    function showQuotation(entity) {
        $('#gFormQuotation input[name = id]').val(entity.id);
        $('#gFormQuotation input[name = version]').val(entity.version);

        $('#quotationNo').val(entity.quotationNo);
        $('#refNo').val(entity.refNo);
        $('#refDate').val(entity.refDate);
        $('#validFrom').val(entity.validFrom);
        $('#validTo').val(entity.validTo);
        $('#customerName').val(entity.customerName);
        $('#customerId').val(entity.customerId);
        $('#contactNumber').val(entity.contactNumber);
        $('#address').val(entity.address);
        $('#shipmentCharge').val(entity.shipmentCharge);
        $('#vatCharge').val(entity.vatCharge);
        $('#handlingCharge').val(entity.handlingCharge);
        $('#otherCharge').val(entity.otherCharge);
        $('#paymentTerm').val(entity.paymentTerm);
        $('#remarks').val(entity.remarks);
        $('#salesTermsAndConditions').val(entity.salesTermsAndConditions);
        $('#isActive').attr('checked', entity.isActive);
        $('#create-button-quotation').attr('value', 'Update');
        $('#delete-button-quotation').show();
    }

    function trim_form() {
        $(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            this.value = $.trim(this.value);
        });
    }
</script>