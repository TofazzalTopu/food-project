<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('ProductPackage')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }
        $('#searchKey').blur(function () {
            if ($('#searchKey').val() == '') {
                $("#finishProduct").val('');

            }
        });
        $("#gFormProductPackage").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormProductPackage").validationEngine('attach');
        reset_productPackage_form("#gFormProductPackage");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'productPackage', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Product',
                'Package Type',
                'Measure Unit',
                'Conversion Factor',
                'Pack Size'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'finishProduct', index: 'finishProduct', width: 100, align: 'left'},
                {name: 'packageType', index: 'packageType', width: 100, align: 'left'},
                {name: 'measureUnitConfiguration', index: 'measureUnitConfiguration', width: 100, align: 'left'},
                {name: 'conversionFactor', index: 'conversionFactor', width: 100, align: 'left'},
                {name: 'packSize', index: 'packSize', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All ProductPackage Information",
            autowidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditProductPackage();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
        $("#conversionFactor").format({precision: 0, allow_negative: false, autofix: true});
    });
    function getSelectedProductPackageId() {
        var productPackageId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            productPackageId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (productPackageId == null) {
            productPackageId = $('#gFormProductPackage input[name = id]').val();
        }
        return productPackageId;
    }
    function executePreConditionProductPackage() {
        // trim field vales before process.
//        trim_form();
        if ($("#gFormProductPackage").validate().form({onfocusout: false}) == false) {
            return false;
        }
        return true;
    }
    function executeAjaxProductPackage() {
        if (!executePreConditionProductPackage()) {
            return false;
        }

        var product=$('#finishProduct').val();
        if(!product) {
            MessageRenderer.renderErrorText("Please select product.");
            return false;
        }

        var pp=$('#packageType').val();
        if(!pp) {
            MessageRenderer.renderErrorText("Please select package type.");
            return false;
        }

        var cf=$('#conversionFactor').val();
        if(!cf) {
            MessageRenderer.renderErrorText("Please conversation factor.");
            return false;
        }

        var actionUrl = null;
        if ($('#gFormProductPackage input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormProductPackage").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionProductPackage(data);
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
    function executePostConditionProductPackage(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productPackage_form('#gFormProductPackage');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxProductPackage() {    // Delete record
        var productPackageId = getSelectedProductPackageId();
        if (executePreConditionForDeleteProductPackage(productPackageId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-productPackage").dialog({
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
                            data: jQuery("#gFormProductPackage").serialize(),
                            url: "${resource(dir:'productPackage', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteProductPackage(message);
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

    function executePreConditionForEditProductPackage(productPackageId) {
        if (productPackageId == null) {
            alert("Please select a productPackage to edit");
            return false;
        }

        return true;
    }
    function executeEditProductPackage() {
        var productPackageId = getSelectedProductPackageId();
        if (executePreConditionForEditProductPackage(productPackageId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productPackage', file:'edit')}?id=" + productPackageId,
                success: function (entity) {
                    executePostConditionForEditProductPackage(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditProductPackage(data) {
        if (data == null) {
            alert('Selected productPackage might have been deleted by someone');  //Message renderer
        } else {
            showProductPackage(data);
        }
    }
    function executePreConditionForDeleteProductPackage(productPackageId) {
        if (productPackageId == null) {
            alert("Please select a productPackage to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteProductPackage(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productPackage_form('#gFormProductPackage');
        }
        MessageRenderer.render(data)
    }
    function showProductPackage(entity) {

       // functionforConversionFactor()
        var productPackage = entity.productPackage
        var finishProduct = entity.finishProduct
        var packageType = entity.packageType
        var measureUnitConfiguration = entity.measureUnitConfiguration
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormProductPackage input[name = id]').val(productPackage.id);
        $('#gFormProductPackage input[name = version]').val(productPackage.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        if (finishProduct) {
            $('#searchKey').val(finishProduct.code + '-' + finishProduct.name);
            $("#finishProduct").val(finishProduct.id);
        }
        if (packageType) {
            var options = '<option value="' + packageType.id + '">' + packageType.name + '</option>';

            $("#packageType").html(options);
        }
        /*if (measureUnitConfiguration) {
            var options = '<option value="' + measureUnitConfiguration.id + '">' + measureUnitConfiguration.name + '</option>';

            $("#measureUnitConfiguration").html(options);
        }*/
        if (measureUnitConfiguration) {

            $('#measureUnitConfigurationName').val(entity.measureUnitConfiguration.name);
            $('#measureUnitConfigurationId').val(measureUnitConfiguration.id)
        }

        $('#conversionFactor').val(productPackage.conversionFactor);
        $('#packSize').val(productPackage.packSize);
        if (productPackage.userCreated) {
            $('#userCreated').val(productPackage.userCreated.id).attr("selected", "selected");
        }
        else {
            $('#userCreated').val(productPackage.userCreated);
        }
        if (productPackage.userUpdated) {
            $('#userUpdated').val(productPackage.userUpdated.id).attr("selected", "selected");
        }
        else {
            $('#userUpdated').val(productPackage.userUpdated);
        }
        $('#dateCreated').val(productPackage.dateCreated);
        $('#dateUpdated').val(productPackage.dateUpdated);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
        loadProduct();
    }
    function executeSearchProductPackage(fieldName, fieldValue) {
        if (executePreConditionForSearchProductPackage(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productPackage', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchProductPackage(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchProductPackage(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_productPackage_form("#gFormProductPackage");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchProductPackage(data, fieldName, fieldValue) {
        if (data == null) {
            reset_productPackage_form("#gFormProductPackage"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showProductPackage(data);
        }
    }

    $('#searchKey').blur(function () {

        if ($('#searchKey').val() == '') {
            //$("#packageType").val('');
            $("#finishProduct").val('');
            $("#packSize").val('');
            $("#measureUnitConfigurationId").val('');
            $("#measureUnitConfigurationName").val('');
        }
    });
    function loadProduct() {
        jQuery('#searchKey').autocomplete({
            minLength: '1',
            source: function (request, response) {

                if ($("#enterpriseConfiguration").val()) {
                    var data = {searchKey: request.term};
                    var url = '${resource(dir:'productPackage', file:'listProduct')}?id=' + $("#enterpriseConfiguration").val() + "&query=" + $('#searchKey').val();
                    DocuAutoComplete.setSpinnerSelector('searchKey').execute(response, url, data, function (item) {
                        item['label'] = item['code'] + "-" + item['name'];
                        item['value'] = item['label'];
                        return item;
                    });
                }
            },
            select: function (event, ui) {
               // alert(ui.item.pack_size)
                ProductInfo.setProductInformation(ui.item.id,  ui.item.name, ui.item.code, ui.item.pack_size, ui.item.mu,  ui.item.muId);
            }
        }).data("autocomplete")._renderItem = function (ul, item) {
            var accountstype = "";
            if (item) {
                accountstype = '<div style="font-size: 9px; color:#326E93;">' + " Code: " + item.code + ", Name: " + item.name + ", Product Category: " + item.p_cat + '</div>';
            }
            return $("<li></li>").data("item.autocomplete", item).append('<a>' + item['label'] + '</a>' + accountstype).appendTo(ul);
        }
    }

    $('#search-btn-product-register-id').click(function () {
        ProductInfo.popupProductListPanel();
    });

    var ProductInfo = {
        customerCoreInfoId: null,

        popupProductListPanel: function () {
            var url = '${resource(dir:'productPackage', file:'popupProductListPanel')}';
            var params = {id: $("#enterpriseConfiguration").val()};
            DocuAjax.html(url, params, function (html) {
                $.fancybox(html);
            });
        },
        setProductInformation: function (customerCoreInfoId, name, code, packSize,mu,muId) {
            //alert("sd  fd"+packSize+" - "+name)
            $("#searchKey").val(code+"-"+name);
            $("#finishProduct").val(customerCoreInfoId);
            $("#packSize").val(packSize);
            $("#measureUnitConfigurationId").val(muId);
            $("#measureUnitConfigurationName").val(mu);

           // $("#packSize").val(packSize);

          //  alert(muId )
            ProductInfo.customerCoreInfoId = customerCoreInfoId;
        }
    }
    function loadPackageType() {
        if ($("#enterpriseConfiguration").val()) {
            jQuery.ajax({
                type: "POST",
                url: '${resource(dir:'productPackage', file:'listPackageType')}?id=' + $("#enterpriseConfiguration").val(),
                dataType: 'json',
                success: function (data, textStatus) {
                    var options = '<option value="">Select Package Type</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    });
                    $("#packageType").html(options);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {
                }
            });
        }
    }
    function loadMeasurementUnit() {
        if ($("#enterpriseConfiguration").val()) {
            jQuery.ajax({
                type: "POST",
                url: '${resource(dir:'productPackage', file:'listMeasurementUnit')}?id=' + $("#enterpriseConfiguration").val(),
                dataType: 'json',
                success: function (data, textStatus) {
                    var options = '<option value="">Select Measurement Unit</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    });
                    $("#measureUnitConfiguration").html(options);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    if(XMLHttpRequest.status = 0){
                        MessageRenderer.renderErrorText("Network Problem: Time out");
                    }else{
                        MessageRenderer.renderErrorText(XMLHttpRequest.responseText);
                    }
                },
                complete: function () {

                }
            });
        }

    }
    function reset_productPackage_form(formName) {
        var enterpriseId = $('#enterpriseConfiguration').val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio, :selected").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $(formName+' select').val('');
//        $("#packageType").html('');
//        $("#measureUnitConfiguration").html('');
        $('#enterpriseConfiguration').val(enterpriseId);
    }
</script>