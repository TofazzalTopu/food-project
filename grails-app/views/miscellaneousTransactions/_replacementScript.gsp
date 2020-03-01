<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

        showReplacementDp();


        $("#gFormMiscellaneousTransactions").validationEngine('attach');
//        reset_form("#gFormMiscellaneousTransactions");

        $('#defaultCustomer').addClass('validate[required]');

        $('#defaultCustomer, #selectProduct').blur(function () {
            if ($('#defaultCustomer').val() == '') {
                $("#customer").val("");
            }
            if ($('#selectProduct').val() == '') {
                $('#product').val("");
            }
        });



        jQuery('#defaultCustomer').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($('#distributionPoint').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getCustomerListBySelectedDp?dpId=" + $('#distributionPoint').val() + "&query=" + $('#defaultCustomer').val();
                    DocuAutoComplete.setSpinnerSelector('defaultCustomer').execute(response, url, data, function (item) {
                        item['label'] = item['name'] + " [" + item['code'] + "]";
                        item['value'] = item['label'];
                        return item;
                    });
                } else {
                    MessageRenderer.render({
                        "messageBody": "Please select a distribution point.",
                        "messageTitle": "Miscellaneous Transactions",
                        "type": "0"
                    });
                    return false;
                }

            },
            select: function (event, ui) {
                ReplacementCustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            if (item.type) {
                customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };

        jQuery('#selectProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                //EmployeeRegister.employeeCoreInfoId = null;
                if ($('#subInventory').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getProductListBySubInventory?subInventoryId=" + $('#subInventory').val() + "&query=" + $('#selectProduct').val() + '&dpId=' + $('#distributionPoint').val();
                    DocuAutoComplete.setSpinnerSelector('selectProduct').execute(response, url, data, function (item) {
                        item['label'] = item['name'] + " [" + item['code'] + "]";
                        item['value'] = item['label'];
                       // alert(item)
                        return item;
                    });
                } else {
                    MessageRenderer.render({
                        "messageBody": "Please select a sub inventory.",
                        "messageTitle": "Miscellaneous Transactions",
                        "type": "0"
                    });
                    return false;
                }

            },
            select: function (event, ui) {
                ReplacementProductInfo.setProductInformation(ui.item.id, ui.item.value, ui.item.quantity);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" +item.quantity +  '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);

        };

        $("#jqgrid-grid-marketReturnSummary").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                '',
                'mrId',
                'mrdId',
                'ID',
                'Product Name',
                'Claimed Qty for Market Return',
                'Accepted Qty'

            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'mrId', index: 'mrId', width: 0, hidden: true},
                {name: 'mrdId', index: 'mrdId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 100},
                {name: 'claimedQty', index: 'claimedQty', width: 100},
                {name: 'acceptedQty', index: 'acceptedQty', width: 80}

            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-marketReturnSummary',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Market Return Summary",
            autowidth: true,
            autoheight: true,
            height: 80,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
                var ids = $("#jqgrid-grid-marketReturnSummary").jqGrid('getDataIDs');
                var i = 1;
                for (key in ids) {
                    $("#jqgrid-grid-marketReturnSummary").jqGrid('setRowData', ids[key], {
                        sl: i
                    });
                    i++;
                }
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $("#jqgrid-grid-marketReturnReplacement").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'dpId',
                'mrId',
                'mrdId',
                'inventoryId',
                'subInventoryId',
                'productId',
                'Product Name',
                'Replacement Qty'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'dpId', index: 'dpId', width: 0, hidden: true},
                {name: 'mrId', index: 'mrId', width: 0, hidden: true},
                {name: 'mrdId', index: 'mrdId', width: 0, hidden: true},
                {name: 'inventoryId', index: 'inventoryId', width: 0, hidden: true},
                {name: 'subInventoryId', index: 'subInventoryId', width: 0, hidden: true},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 100},
                {name: 'replacementQty', index: 'replacementQty', width: 80}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-marketReturnReplacement',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Market Return Replacement",
            autowidth: true,
            autoheight: true,
            height: 80,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#replacementQty').keypress(function (event) {
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }

            var text = $(this).val();

            if ((text.indexOf('.') != -1) &&
                    (text.substring(text.indexOf('.')).length > 2) &&
                    (event.which != 0 && event.which != 8) &&
                    ($(this)[0].selectionStart >= text.length - 2)) {
                event.preventDefault();
            }
        });

    });

    function executePreConditionMiscellaneousTransactions() {
        // trim field vales before process.
        trim_form();
        var products = '';
        products = $("#jqgrid-grid-marketReturnReplacement").jqGrid('getDataIDs');
        if (products == '') {
            MessageRenderer.render({
                "messageBody": "No Replacement Product and Quantity found.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
        return true;
    }
    function executeAjaxMiscellaneousTransactions() {
        if (!executePreConditionMiscellaneousTransactions()) {
            return false;
        }
        var dataList = $("#jqgrid-grid-marketReturnReplacement").jqGrid('getRowData');
        var allData = {};
        for (var i = 0; i < dataList.length; i++) {
            allData['products.item[' + i + '].distributionPoint.id'] = dataList[i].dpId;
            allData['products.item[' + i + '].mrdId'] = dataList[i].mrdId;
            allData['products.item[' + i + '].inventory.id'] = dataList[i].inventoryId;
            allData['products.item[' + i + '].subInventory.id'] = dataList[i].subInventoryId;
            allData['products.item[' + i + '].product.id'] = dataList[i].productId;
            allData['products.item[' + i + '].replacementQty'] = dataList[i].replacementQty;
        }

        var actionUrl = null;
        if ($('#gFormMiscellaneousTransactions input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/createReplacement";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionMiscellaneousTransactions(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-marketReturnSummary").clearGridData();
                    $("#jqgrid-grid-marketReturnReplacement").clearGridData();
                    reset_form('#gFormMiscellaneousTransactions');
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
    function executePostConditionMiscellaneousTransactions(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-marketReturnSummary").clearGridData();
            $("#jqgrid-grid-marketReturnReplacement").clearGridData();
            reset_form('#gFormMiscellaneousTransactions');
        }
        MessageRenderer.render(result);
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = '';
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

    function loadCustomerId(customerId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: '${request.contextPath}/${params.controller}/getCustomerCodeById',
            success: function (data, textStatus) {
                if (data) {
                    $("#customerId").val(data.code);
                } else {
                    $("#customerId").val('');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#customerId").val('');
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

    function loadMrNumber(customerId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: '${request.contextPath}/${params.controller}/getMrListByCustomerId',
            success: function (data, textStatus) {
                if (data) {
                    $('#marketReturnNumber option').remove();
                    $('#marketReturnNumber').append('<option value="">' + "Select Market Return Number..." + '</option>');
                    for (key in data) {
                        $('#marketReturnNumber').append('<option value="' + data[key].id + '">' + data[key].mrNo + '</option>');
                    }
                } else {
                    $('#marketReturnNumber option').remove();
                    $('#marketReturnNumber').append('<option value="">' + "Select Market Return Number..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#marketReturnNumber option').remove();
                    $('#marketReturnNumber').append('<option value="">' + "Select Market Return Number..." + '</option>');
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

    function viewMarketReturnSummary() {
        var mrId = $('#marketReturnNumber').val();
        if (mrId) {
            SubmissionLoader.showTo();
            jQuery.ajax({
                type: 'post',
                data: {mrId: mrId},
                url: '${request.contextPath}/${params.controller}/getMarketReturnSummaryByMrId',
                success: function (data, textStatus) {
                    jQuery("#jqgrid-grid-marketReturnSummary").clearGridData();
                    if (data[0]) {
                        jQuery("#jqgrid-grid-marketReturnSummary")
                                .jqGrid('setGridParam',
                                {
                                    datatype: 'local',
                                    data: data
                                })
                                .trigger("reloadGrid");
                    } else {
                        jQuery("#jqgrid-grid-marketReturnSummary").clearGridData();
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        Query("#jqgrid-grid-marketReturnSummary").clearGridData();
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
        } else {
            jQuery("#jqgrid-grid-marketReturnSummary").clearGridData();
        }
    }

   function showReplacementDp() {
        var size;

        var options = '';
       if ('${dpList}' != '') {
            size = ${dpSize};
            if(size==1){
               options = '<option value="' + ${dpList}[0].id + '">' + ${dpList}[0].name + '</option>';
            }else{
                options = '<option value="">Please Select</option>';
                for (var i = 0; i < size; i++) {
                options += '<option value="' + ${dpList}[i].id + '">' + ${dpList}[i].name + '</option>';
                }
            }
            $("#distributionPoint").html(options);
        }
    }


    function getInventoryLisByDp() {
        var dpId = $('#distributionPoint').val();
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: dpId},
            url: '${request.contextPath}/${params.controller}/getInventoryListByDp',
            success: function (data, textStatus) {
                if (data) {
                    $('#inventory option').remove();
                    $('#inventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    for (key in data) {
                        $('#inventory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#inventory option').remove();
                    $('#inventory').append('<option value="">' + "Select Inventory..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#inventory option').remove();
                    $('#inventory').append('<option value="">' + "Select Inventory..." + '</option>');
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

    function getSubInventoryLisByInventory(id) {
        if(!id){
            return false;
        }
        var salable = '[Salable]';
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {inventoryId: id},
            url: '${request.contextPath}/${params.controller}/getSubInventoryListByInventory',
            success: function (data, textStatus) {
                if (data) {
                    $('#subInventory option').remove();
                    $('#subInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    for (key in data) {
                        $('#subInventory').append('<option value="' + data[key].id + '">' + data[key].name + ' ['+data[key].type+']'  + '</option>');
                    }
                } else {
                    $('#subInventory option').remove();
                    $('#subInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                }
                $("#subInventory option:contains(" + salable + ")").attr('selected', 'selected');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#subInventory option').remove();
                    $('#subInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
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

    var rowId = 1;
    function addProductIntoGrid() {
        var myGrid = $("#jqgrid-grid-marketReturnReplacement");
        var dpId = $("#distributionPoint").val();
        var inventoryId = $("#inventory").val();
        var subInventoryId = $("#subInventory").val();
        var productId = $("#product").val();
        var productName = $("#selectProduct").val();
        var replacementQty = parseFloat($("#replacementQty").val());
        var avlQty = $("#availableQty").val();
        var acceptedQty = $("#jqgrid-grid-marketReturnSummary").jqGrid('getCell', productId, 'acceptedQty');
        var mrId = $("#jqgrid-grid-marketReturnSummary").jqGrid('getCell', productId, 'mrId');
        var mrdId = $("#jqgrid-grid-marketReturnSummary").jqGrid('getCell', productId, 'mrdId');
       if(avlQty > replacementQty) {
            if (acceptedQty && (acceptedQty >= replacementQty)) {
                var dataItem = {
                    sl: rowId,
                    dpId: dpId,
                    mrId: mrId,
                    mrdId: mrdId,
                    inventoryId: inventoryId,
                    subInventoryId: subInventoryId,
                    productId: productId,
                    productName: productName,
                    replacementQty: replacementQty
                };
                myGrid.addRowData(rowId, dataItem, "last");
                rowId++;
            } else {
                MessageRenderer.render({
                    "messageBody": "Replacement product and Qty must match with the Accepted product and Qty.",
                    "messageTitle": "Miscellaneous Transactions",
                    "type": "0"
                });
                return false;
            }
        }else{
           MessageRenderer.renderErrorText("Replacement product Qty can not be greater then available qty");
       }
    }

    $('#search-btn-customer-register-id').click(function () {
        if ($('#distributionPoint').val()) {
            tabIndex = 'replacement';
            ReplacementCustomerInfo.popupCustomerListPanel($('#distributionPoint').val());
        }
        else {
            MessageRenderer.render({
                "messageBody": "Please select a distribution point.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    });

    var ReplacementCustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (distributionPointId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'distributionPointCustomerList')}';
            var params = {dpId: distributionPointId};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            SubmissionLoader.showTo();
            $("#defaultCustomer").val(customerCoreInfo);
            $("#customer").val(customerCoreInfoId);
            ReplacementCustomerInfo.customerCoreInfoId = customerCoreInfoId;

            loadCustomerId(customerCoreInfoId);
            loadMrNumber(customerCoreInfoId);
            getInventoryLisByDp();
            SubmissionLoader.hideFrom();
        }
    }

    $('#search-btn-product-id').click(function () {
        if ($('#subInventory').val()) {
            tabIndex = 'replacement';
            ReplacementProductInfo.popupProductListPanel($('#subInventory').val());
        }
        else {
            MessageRenderer.render({
                "messageBody": "Please select a sub inventory.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    });

    var ReplacementProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function (subInventoryId) {
           // alert('ReplacementProductInfo')
            var url = '${resource(dir:'miscellaneousTransactions', file:'getDistributionProductListBySubInventory')}';
            var params = {subInventoryId: subInventoryId, dpId: $('#distributionPoint').val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo, qty) {
            //alert('productCoreInfoId  '+ qty)
            SubmissionLoader.showTo();
            $("#availableQty").val(qty);
            $("#selectProduct").val(productCoreInfo);
            $("#product").val(productCoreInfoId);
            ReplacementProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>