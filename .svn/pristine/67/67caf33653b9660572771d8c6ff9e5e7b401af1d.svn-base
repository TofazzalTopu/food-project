<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormVatRate").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormVatRate").validationEngine('attach');
        reset_form("#gFormVatRate");

//        Auto complete code
        jQuery('#selectProduct').autocomplete({
            minLength: '1',
            source: function (request, response) {
                $('#popEmpDetails').html("");
                var data = {searchKey: request.term};
                var url = "${request.contextPath}/${params.controller}/getProductListByKey?key=" + $('#selectProduct').val();
                DocuAutoComplete.setSpinnerSelector('selectProduct').execute(response, url, data, function (item) {
                    item['label'] = item['name'] + " [" + item['code'] + "]";
                    item['value'] = item['label'];
                    return item;
                });
            },
            select: function (event, ui) {
                ProductInfo.setProductInformation(ui.item.id, ui.item.value);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var productDetails = "";
            if (item.type) {
                productDetails = '<div style="font-size: 9px; color:#326E93;">' + " Name: " + item.name + "[" + item.code + "]" + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + productDetails).appendTo(ul);

        };
        
        $("#jqgrid-grid-vatRate").jqGrid({
            url: '${resource(dir:'vatRate', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Product Name',
                'Rate',
                'SD %',
                'Vat %',
                'From Date',
                'To Date',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: true, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},


                {name: 'finishProduct', index: 'finishProduct', width: 100, align: 'left'}

                ,

                {
                    name: 'rate',
                    index: 'rate',
                    width: 100,
                    align: 'right',
                    formatter: 'number',
                    formatoptions: {thousandsSeparator: ","}
                }

                ,

                {
                    name: 'supplementaryDuty',
                    index: 'supplementaryDuty',
                    width: 100,
                    align: 'right',
                    formatter: 'number',
                    formatoptions: {thousandsSeparator: ","}
                }

                ,

                {
                    name: 'vat',
                    index: 'vat',
                    width: 100,
                    align: 'right',
                    formatter: 'number',
                    formatoptions: {thousandsSeparator: ","}
                }

                ,

                {
                    name: 'effectiveFromDate',
                    index: 'effectiveFromDate',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }

                ,

                {
                    name: 'effectiveToDate',
                    index: 'effectiveToDate',
                    width: 100,
                    align: 'left',
                    formatter: 'date',
                    formatoptions: {newformat: 'd-m-Y'}
                }

                ,

                {name: 'isActive', index: 'isActive', width: 100, align: 'left'}


            ],
            rowNum: 10,
            rowList: [10, 20, 30],
            pager: '#jqgrid-pager-vatRate',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Vat Rate Information",
            autowidth: true,
            autoheight: true,
            scrollOffset: 0,
            altRows: true,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditVatRate();
            }
        });

    });

    $('#search-btn-vat-rate-id').click(function () {
        VatRateInfo.popupVatRateListPanel($("#id").val());
    });

    var VatRateInfo = {
        customerCoreInfoId: null,
        popupVatRateListPanel: function (vatRateInfoId) {
            var url = '${resource(dir:'vatRate', file:'popupVatRateListPanel')}';
            var params = {vatRateId: 1, territorySubAreaId: 1};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        }
    };

    function getSelectedVatRateId() {
        var vatRateId = null;
        var rowid = $("#jqgrid-grid-vatRate").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            vatRateId = $("#jqgrid-grid-vatRate").jqGrid('getCell', rowid, 'id');
        }
        if (vatRateId == null) {
            vatRateId = $('#gFormVatRate input[name = id]').val();
        }
        return vatRateId;
    }
    function executePreConditionVatRate() {

        var fromDate = $('#effectiveFromDate').val();
        var toDate = $('#effectiveToDate').val();

        if (!isPastDate(toDate, fromDate)) {
            var message = {
                'messageTitle': "Vat Rate",
                'type': 2,
                'messageBody': "From Date can't be greater than To Date"
            }
            MessageRenderer.render(message);
        }
        else {
            trim_form();

            if (!$("#gFormVatRate").validationEngine('validate')) {
                return false;
            }
            return true;

        }
        // return true;
        // trim field vales before process.

    }
    function executeAjaxVatRate() {
        if (!executePreConditionVatRate()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormVatRate input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        }
        else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormVatRate").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionVatRate(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid-vatRate").trigger("reloadGrid");
                    reset_form('#gFormVatRate');
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
    function executePostConditionVatRate(result) {
        if (result.type == 1) {
            $("#jqgrid-grid-vatRate").trigger("reloadGrid");
            reset_form('#gFormVatRate');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxVatRate() {    // Delete record
        var vatRateId = getSelectedVatRateId();
        if (executePreConditionForDeleteVatRate(vatRateId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-vatRate").dialog({
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
                            data: jQuery("#gFormVatRate").serialize(),
                            url: "${resource(dir:'vatRate', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteVatRate(message);
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

    function executePreConditionForEditVatRate(vatRateId) {
        if (vatRateId == null) {
            alert("Please select a vatRate to edit");
            return false;
        }
        return true;
    }
    function executeEditVatRate() {
        var vatRateId = getSelectedVatRateId();
        if (executePreConditionForEditVatRate(vatRateId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'vatRate', file:'edit')}?id=" + vatRateId,
                success: function (entity) {
                    executePostConditionForEditVatRate(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditVatRate(data) {
        if (data == null) {
            alert('Selected vatRate might have been deleted by someone');  //Message renderer
        } else {
            showVatRate(data);
        }
    }
    function executePreConditionForDeleteVatRate(vatRateId) {
        if (vatRateId == null) {
            alert("Please select a vatRate to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteVatRate(data) {
        if (data.type == 1) {
            $("#jqgrid-grid-vatRate").trigger("reloadGrid");
            reset_form('#gFormVatRate');
        }
        MessageRenderer.render(data)
    }
    function showVatRate(entity) {
        var effectiveFromDate = $.datepicker.formatDate('dd-mm-yy', new Date(entity.effectiveFromDate));
        var effectiveToDate = $.datepicker.formatDate('dd-mm-yy', new Date(entity.effectiveToDate));

        $('#gFormVatRate input[name = id]').val(entity.id);
        $('#gFormVatRate input[name = version]').val(entity.version);

        if(entity.finishProduct){
            $('#finishProduct').val(entity.finishProduct.id);

            $.ajax({
                type: "POST",
                url: "${resource(dir:'vatRate', file:'getProduct')}?id=" + entity.finishProduct.id,
                success: function (product) {
                    $('#selectProduct').val(product.name+'['+product.code+']');
                },
                dataType: 'json'
            });
        }

        $('#rate').val(entity.rate);
        $('#supplementaryDuty').val(entity.supplementaryDuty);
        $('#vat').val(entity.vat);
        $('#effectiveFromDate').val(effectiveFromDate);
        $('#effectiveToDate').val(effectiveToDate);
        $('#isActive').attr('checked', entity.isActive);
        $('#create-button-vatRate').attr('value', 'Update');
        $('#delete-button-vatRate').show();
    }
    function executeSearchVatRate(fieldName, fieldValue) {
        if (executePreConditionForSearchVatRate(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'vatRate', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchVatRate(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchVatRate(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormVatRate");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchVatRate(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormVatRate"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showVatRate(data);
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

    $('#search-btn-product-id').click(function () {
        ProductInfo.popupProductListPanel();
    });

    var ProductInfo = {
        productCoreInfoId: null,
        popupProductListPanel: function () {
            var url = '${resource(dir:'vatRate', file:'getProductList')}';
            var params = {};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },

        setProductInformation: function (productCoreInfoId, productCoreInfo) {
            SubmissionLoader.showTo();
            $("#selectProduct").val(productCoreInfo);
            $("#finishProduct").val(productCoreInfoId);
            ProductInfo.productCoreInfoId = productCoreInfoId;
            SubmissionLoader.hideFrom();
        }
    }

</script>