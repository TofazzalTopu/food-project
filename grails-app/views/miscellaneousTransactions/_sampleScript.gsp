<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $("#gFormMiscellaneousTransactions").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });

        showSampleDp();

        var smDpId=document.getElementById('sampleDistributionPoint').value
        if(smDpId){
            getSampleInventoryListByDp(smDpId)
        }

        $("#gFormMiscellaneousTransactions").validationEngine('attach');
       // reset_form("#gFormMiscellaneousTransactions");
        $('#sampleCustomerName').hide();
        $('input[name=customerType]').click(function () {
            if ($(this).val() == 'enlisted') {
                $('#sampleCustomerName').val('');
                $('#sampleCustomerName').hide();
                $('#sampleCustomer').show();
                $('#sampleCustomerId').removeAttr('disabled');
            } else {
                $('#sampleCustomerName').show();
                $('#sampleDefaultCustomer').val('');
                $("#sampleCustomerIdHidden").val('');
                $('#sampleCustomer').hide();
                $('#sampleCustomerId').val('');
                $('#sampleCustomerId').attr('disabled', true);
            }
        });

        $('#sampleDefaultCustomer').addClass('validate[required]');

        $('#sampleDefaultCustomer, #selectSampleProduct').blur(function () {
            if ($('#sampleDefaultCustomer').val() == '') {
                $("#sampleCustomerIdHidden").val("");
            }

            if ($('#selectSampleProduct').val() == '') {
                $("#sampleProduct").val("");
            }
        });

        jQuery('#sampleDefaultCustomer').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($('#sampleDistributionPoint').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getAllCustomerListByKey?query=" + $('#sampleDefaultCustomer').val();
                    DocuAutoComplete.setSpinnerSelector('sampleDefaultCustomer').execute(response, url, data, function (item) {
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
                SampleCustomerInfo.setCustomerInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var customerDetails = "";
            if (item.type) {
                customerDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + customerDetails).appendTo(ul);

        };

        jQuery('#selectSampleProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                if ($('#sampleSubInventory').val()) {
                    $('#popEmpDetails').html("");
                    var data = {searchKey: request.term};
                    var url = "${request.contextPath}/${params.controller}/getProductListBySubInventory?subInventoryId=" + $('#sampleSubInventory').val() + "&query=" + $('#selectSampleProduct').val() + "&dpId=" + $('#sampleDistributionPoint').val();
                    DocuAutoComplete.setSpinnerSelector('selectSampleProduct').execute(response, url, data, function (item) {
                        item['label'] = item['name'] + " [" + item['code'] + "]";
                        item['value'] = item['label'];
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
                SampleProductInfo.setProductInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);
        };

        $("#jqgrid-grid-sampleProducts").jqGrid({
            datatype: "local",
            colNames: [
                'S/L No.',
                'productId',
                'Product Name',
                'Quantity'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'center'},
                {name: 'productId', index: 'productId', width: 0, hidden: true},
                {name: 'productName', index: 'productName', width: 100},
                {name: 'sampleQty', index: 'sampleQty', width: 80}
            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-sampleProducts',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Products",
            autowidth: true,
            autoheight: true,
            height: 80,
            scrollOffset: 17,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
            }
        });

        $('#sampleQty').keypress(function (event) {
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

    function executePreConditionSampleMiscellaneousTransactions() {
        // trim field vales before process.
        trim_form();
        var products = '';
        products = $("#jqgrid-grid-sampleProducts").jqGrid('getDataIDs');
        if (products == '') {
            MessageRenderer.render({
                "messageBody": "No Sample Product and Quantity found.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
        return true;
    }
    function executeAjaxSampleMiscellaneousTransactions() {
        if (!executePreConditionSampleMiscellaneousTransactions()) {
            return false;
        }

        var dpId = $("#sampleDistributionPoint").val();
        var customerType = $('input[name=customerType]:checked').val();
        var customerId = '';
        var customerName = '';
        if (customerType == 'enlisted') {
            customerId = $('#sampleCustomerIdHidden').val();
        } else if (customerType == 'others') {
            customerName = $('#sampleCustomerName').val();
        }
        var customerAddress = $('#sampleCustomerAddress').val();
        var inventoryId = $("#sampleInventory").val();
        var subInventoryId = $("#sampleSubInventory").val();
        var remarks = $("#sampleRemarks").val();

        var dataList = $("#jqgrid-grid-sampleProducts").jqGrid('getRowData');
        var allData = {};

        if (dpId && customerType && (customerId || customerName) && customerAddress && inventoryId && subInventoryId) {
            allData['distributionPoint.id'] = dpId;
            allData['customerType'] = customerType;
            allData['customer.id'] = customerId;
            allData['customerName'] = customerName;
            allData['customerAddress'] = customerAddress;
            allData['inventory.id'] = inventoryId;
            allData['subInventory.id'] = subInventoryId;
            allData['remarks'] = remarks;

            for (var i = 0; i < dataList.length; i++) {
                allData['products.item[' + i + '].product.id'] = dataList[i].productId;
                allData['products.item[' + i + '].sampleQty'] = dataList[i].sampleQty;
            }
        } else {
            MessageRenderer.render({
                "messageBody": "Please enter data for all required fields.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }

        var actionUrl = null;
        if ($('#gFormMiscellaneousTransactions input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/createSample";
        }

        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: allData,
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionSampleMiscellaneousTransactions(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-sampleProducts").jqGrid("clearGridData");
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

    function executePostConditionSampleMiscellaneousTransactions(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-sampleProducts").jqGrid("clearGridData");
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

    function showSampleDp() {
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
           $("#sampleDistributionPoint").html(options);
        }
    }

    function getSampleInventoryListByDp(dpId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {dpId: dpId},
            url: '${request.contextPath}/${params.controller}/getInventoryListByDp',
            success: function (data, textStatus) {
                if (data) {
                    $('#sampleInventory option').remove();
                    $('#sampleInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                    for (key in data) {
                        $('#sampleInventory').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#sampleInventory option').remove();
                    $('#sampleInventory').append('<option value="">' + "Select Inventory..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#sampleInventory option').remove();
                    $('#sampleInventory').append('<option value="">' + "Select Inventory..." + '</option>');

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
    }

    function getSampleSubInventoryListByInventory(id) {
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
                    $('#sampleSubInventory option').remove();
                    $('#sampleSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    for (key in data) {
                        $('#sampleSubInventory').append('<option value="' + data[key].id + '">' + data[key].name + ' ['+data[key].type+']' + '</option>');
                    }
                } else {
                    $('#sampleSubInventory option').remove();
                    $('#sampleSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                }

                $("#sampleSubInventory option:contains(" + salable + ")").attr('selected', 'selected');
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#sampleSubInventory option').remove();
                    $('#sampleSubInventory').append('<option value="">' + "Select Sub Inventory..." + '</option>');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }
                else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                getSampleProductListBySubInventory($('#sampleSubInventory').val());
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    function getSampleProductListBySubInventory(id) {
        if(!id){
            return false;
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {subInventoryId: id, dpId:  $('#sampleDistributionPoint').val()},
            url: '${request.contextPath}/${params.controller}/getProductListBySubInventory',
            success: function (data, textStatus) {
                if (data) {
                    $('#sampleProduct option').remove();
                    $('#sampleProduct').append('<option value="">' + "Select Product..." + '</option>');
                    for (key in data) {
                        $('#sampleProduct').append('<option value="' + data[key].id + '">' + data[key].name + '</option>');
                    }
                } else {
                    $('#sampleProduct option').remove();
                    $('#sampleProduct').append('<option value="">' + "Select Sub Product..." + '</option>');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $('#sampleProduct option').remove();
                    $('#sampleProduct').append('<option value="">' + "Select Sub Product..." + '</option>');

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
    }

    var sampleProductRowId = 1;
    function addSampleProductIntoGrid() {
        var myGrid = $("#jqgrid-grid-sampleProducts");

        var dpId = $("#sampleDistributionPoint").val();
        var customerType = $('input[name=customerType]:checked').val();
        var customerName = '';
        if (customerType == 'enlisted') {
            customerName = $('#sampleCustomerIdHidden').val();
        } else if (customerType == 'others') {
            customerName = $('#sampleCustomerName').val();
        } else {
            customerName = '';
        }
        var customerAddress = $('#sampleCustomerAddress').val();
        var inventoryId = $("#sampleInventory").val();
        var subInventoryId = $("#sampleSubInventory").val();
        var remarks = $("#sampleRemarks").val();

        var productId = $("#sampleProduct").val();
        var productName = $("#selectSampleProduct").val();
        var sampleQty = parseFloat($("#sampleQty").val());

        if (productId && productName && sampleQty && dpId && customerType && customerName && customerAddress && inventoryId && subInventoryId) {
            var dataItem = {
                sl: sampleProductRowId,
                productId: productId,
                productName: productName,
                sampleQty: sampleQty
            };
            myGrid.addRowData(sampleProductRowId, dataItem, "last");
            sampleProductRowId++;
        } else {
            MessageRenderer.render({
                "messageBody": "Please enter data for all required fields.",
                "messageTitle": "Miscellaneous Transactions",
                "type": "0"
            });
            return false;
        }
    }

    function loadSampleCustomerIdAndAddress(customerId) {
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: {customerId: customerId},
            url: '${request.contextPath}/${params.controller}/getCustomerCodeById',
            success: function (data, textStatus) {
                if (data) {
                    $("#sampleCustomerId").val(data.code);
                    $("#sampleCustomerAddress").val(data.presentAddress);
                } else {
                    $("#sampleCustomerId").val('');
                    $("#sampleCustomerAddress").val('');
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#sampleCustomerId").val('');
                    $("#sampleCustomerAddress").val('');
                    MessageRenderer.renderErrorText("Network Problem: Time out");
                }  else{
                    MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                }
            },
            complete: function () {
                SubmissionLoader.hideFrom();
            },
            dataType: 'json'
        });
    }

    $('#search-btn-sample-register-id').click(function () {
        if ($('#sampleDistributionPoint').val()) {
            tabIndex = 'sample';
            SampleCustomerInfo.popupCustomerListPanel($('#sampleDistributionPoint').val());
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

    var SampleCustomerInfo = {
        customerCoreInfoId: null,
        popupCustomerListPanel: function (distributionPointId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'getAllCustomerListByKey')}';

            DocuAjax.html(url, '', function (html) {
                $.fancybox(html);
            });
        },

        setCustomerInformation: function (customerCoreInfoId, customerCoreInfo) {
            SubmissionLoader.showTo();
            $("#sampleDefaultCustomer").val(customerCoreInfo);
            $("#sampleCustomerIdHidden").val(customerCoreInfoId);
            SampleCustomerInfo.customerCoreInfoId = customerCoreInfoId;

            loadSampleCustomerIdAndAddress(customerCoreInfoId);
            SubmissionLoader.hideFrom();
        }
    }

    $('#search-btn-sample-product-id').click(function () {
        if ($('#sampleSubInventory').val()) {
            tabIndex = 'sample';
            SampleProductInfo.popupProductListPanel($('#sampleSubInventory').val());
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

    var SampleProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function (subInventoryId) {
            var url = '${resource(dir:'miscellaneousTransactions', file:'getDistributionProductListBySubInventory')}';
            var params = {subInventoryId: subInventoryId, dpId:  $('#sampleDistributionPoint').val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo) {
            SubmissionLoader.showTo();
            $("#selectSampleProduct").val(productCoreInfo);
            $("#sampleProduct").val(productCoreInfoId);
            SampleProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>