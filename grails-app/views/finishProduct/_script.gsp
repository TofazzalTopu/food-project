<script type="text/javascript" language="Javascript">
    var coaGroupId = "";
    var coaId = "";
    $(document).ready(function () {
        $('#ui-widget-header-text').html('FinishProduct');
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }


        //For Amount Varification
        $("#packSize").keypress(function(event){
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }
        });

        //For Amount Varification
        $("#qtyInLtr").keypress(function(event){
            if ((event.which != 46 || $(this).val().indexOf('.') != -1) &&
                    ((event.which < 48 || event.which > 57) &&
                    (event.which != 0 && event.which != 8))) {
                event.preventDefault();
            }
        });



//        $("#gFormFinishProduct").validationEngine({   //    client side validation
//            isOverflown: true,
//            overflownDIV: ".ui-layout-center"
//        });
//        $("#gFormFinishProduct").validationEngine('attach');
//        reset_form("#gFormFinishProduct");
        $("#delete-button").hide();
        $('#enterpriselist_input').val('');
        $("#businessUnitList").flexbox('Select Business Unit', {
            watermark: "Select Business Unit",
            width: 140,
            onSelect: function () {
                $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
//                        loadBusinessUnit($('#enterpriselist_hidden').val());
            }
        });
        $('#businessUnitList_input').val('');
        $('#businessUnitList_input').addClass("validate[required]");
        $('#businessUnitList_input').blur(function () {
            if ($('#businessUnitList_input').val() == '') {
                $("#businessUnitConfiguration").val("");
            }
        });

        $("#productCategoryList").flexbox("Select Category", {
            watermark: "Select Category",
            width: 140,
            onSelect: function () {
                $("#productCategory").val($('#productCategoryList_hidden').val());
            }
        });
        $('#productCategoryList_input').val('');
        $('#productCategoryList_input').addClass("validate[required]");
        $('#productCategoryList_input').blur(function () {
            if ($('#productCategoryList_input').val() == '') {
                $("#productCategory").val("");
            }
        });


        $("#masterProductList").flexbox("Select Master Product", {
            watermark: "Select Master Product",
            width: 140,
            onSelect: function () {
                $("#masterProduct").val($('#masterProductList_hidden').val());
            }
        });
        $('#masterProductList_input').val('');
        $('#masterProductList_input').addClass("validate[required]");
        $('#masterProductList_input').blur(function () {
            if ($('#masterProductList_input').val() == '') {
                $("#masterProduct").val("");
            }
        });


        $("#mainProductList").flexbox("Select Main Product", {
            watermark: "Select Main Product",
            width: 140,
            onSelect: function () {
                $("#mainProduct").val($('#mainProductList_hidden').val());

            }
        });
        $('#mainProductList_input').val('');
        $('#mainProductList_input').addClass("validate[required]");
        $('#mainProductList_input').blur(function () {
            if ($('#mainProductList_input').val() == '') {
                $("#mainProduct").val("");
            }
        });
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'finishProduct', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Business Unit',
                'Product Category',
                'Master Product',
                'Main Product',
                'Product Type',
                'ERP Item Code',
                'Old Item Code',
                'Item Name',
                'Description',
                'Pack Size',
                'UOM',
                'Length',
                'Width',
                'Color',
                'Density',
                'Texture',
                'Other',
                'Sequence Number',
                'Is Active'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'center'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'en_name', index: 'en_name', width: 90, align: 'left', hidden: true},
                {name: 'bu_name', index: 'bu_name', width: 100, align: 'left', hidden: true},
                {name: 'pc_name', index: 'pc_name', width: 110, align: 'left', sortable: false},
                {name: 'master_name', index: 'master_name', width: 110, align: 'left', sortable: false},
                {name: 'main_name', index: 'main_name', width: 140, align: 'left', sortable: false},
                {name: 'product_type_name', index: 'product_type_name', width: 70, align: 'left', sortable: false},
                {name: 'code', index: 'code', width: 120, align: 'left', sortable: false},
                {name: 'old_code', index: 'old_code', width: 150, align: 'left', hidden: true},
                {name: 'name', index: 'name', width: 170, align: 'left', sortable: false},
                {name: 'description', index: 'description', width: 150, align: 'left', hidden: true},
                {name: 'pack_size', index: 'pack_size', width: 60, align: 'left', sortable: false},
                {name: 'measure_name', index: 'measure_name', width: 40, align: 'left', sortable: false},
                {name: 'length', index: 'length', width: 120, align: 'left', hidden: true},
                {name: 'width', index: 'width', width: 120, align: 'left', hidden: true},
                {name: 'color', index: 'color', width: 100, align: 'left', hidden: true},
                {name: 'density', index: 'density', width: 130, align: 'left', hidden: true},
                {name: 'texture', index: 'texture', width: 130, align: 'left', hidden: true},
                {name: 'other', index: 'other', width: 100, align: 'left', hidden: true},
                {name: 'sequence_number', index: 'sequence_number', width: 70, align: 'left', sortable: false},
                {name: 'is_active', index: 'is_active', width: 60, align: 'center', sortable: false}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
//            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "All FinishProduct Information",
            width: 1015,
//            autoWidth: true,
            height: 350,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditFinishProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
        $(".ui-pg-selbox").children().each(function () {
            if ($(this).val() == -1) {
                $(this).html('All')
            }

        });
    });
    function addRemoveCheckBoxForActive(object) {
        if (object.checked) {
            object.value = 'true';
        }
        else {
            object.value = 'false';
        }
    }
    function getSelectedFinishProductId() {
        var finishProductId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            finishProductId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (finishProductId == null) {
            finishProductId = $('#gFormFinishProduct input[name = id]').val();
        }
        return finishProductId;
    }
    function executePreConditionFinishProduct() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormFinishProduct").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxFinishProduct() {
        if (!executePreConditionFinishProduct()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormFinishProduct input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        SubmissionLoader.showTo();
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormFinishProduct").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionFinishProduct(data);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if(XMLHttpRequest.status = 0){
                    $("#jqgrid-grid").trigger("reloadGrid");
                    reset_form('#gFormFinishProduct');
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
    function executePostConditionFinishProduct(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormFinishProduct');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxFinishProduct() {    // Delete record
        var finishProductId = getSelectedFinishProductId();
        if (executePreConditionForDeleteFinishProduct(finishProductId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-finishProduct").dialog({
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
                            data: jQuery("#gFormFinishProduct").serialize(),
                            url: "${resource(dir:'finishProduct', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteFinishProduct(message);
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

    function executePreConditionForEditFinishProduct(finishProductId) {
        if (finishProductId == null) {
            alert("Please select a finishProduct to edit");
            return false;
        }
        return true;
    }
    function executeEditFinishProduct() {
        var finishProductId = getSelectedFinishProductId();
        if (executePreConditionForEditFinishProduct(finishProductId)) {
            SubmissionLoader.showTo();
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'edit')}?id=" + finishProductId,
                success: function (entity) {
                    executePostConditionForEditFinishProduct(entity);
                },
                complete: function(){
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditFinishProduct(data) {
        if (data == null) {
            alert('Selected finishProduct might have been deleted by someone');  //Message renderer
        } else {
            showFinishProduct(data);
        }
    }
    function executePreConditionForDeleteFinishProduct(finishProductId) {
        if (finishProductId == null) {
            alert("Please select a finishProduct to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteFinishProduct(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_form('#gFormFinishProduct');
        }
        MessageRenderer.render(data)
    }
    function showFinishProduct(entity) {
        var finishProduct = entity.finishProduct;
        var enterpriseConfiguration = entity.enterpriseConfiguration;
        var businessUnitConfiguration = entity.businessUnitConfiguration;
        var productCategory = entity.productCategory;
        var masterProduct = entity.masterProduct;
        var mainProduct = entity.mainProduct;
        var productType = entity.productType;
        var measureUnitConfiguration = entity.measureUnitConfiguration;
        var chartOfAccountGroup = entity.chartOfAccountGroup;
        var chartOfAccountHead = entity.chartOfAccountHead;

        $('#gFormFinishProduct input[name = id]').val(finishProduct.id);
        $('#gFormFinishProduct input[name = version]').val(finishProduct.version);

        $('#bonusCrCode').val(finishProduct.bonusCrCode);
        $('#bonusDrCode').val(finishProduct.bonusDrCode);

        $('#byProdSalesCode').val(finishProduct.byProdSalesCode);
        $('#code').val(finishProduct.code);
        $('#code').attr("disabled","disabled");
        $('#color').val(finishProduct.color);
        $('#density').val(finishProduct.density);
        $('#description').val(finishProduct.description);
        $('#exportSalesCode').val(finishProduct.exportSalesCode);
        $('#insideSalesCode').val(finishProduct.insideSalesCode);
        $('#institutionalSalesCode').val(finishProduct.institutionalSalesCode);
        $('#length').val(finishProduct.length);
        $('#qtyInLtr').val(finishProduct.qtyInLtr);
        $('#sequenceNumber').val(finishProduct.sequenceNumber);

        if (finishProduct.isActive) {
            $('#isActive').attr('checked', true);
        } else {
            $('#isActive').attr('checked', false);
        }

        if(chartOfAccountGroup) {
            coaGroupId = chartOfAccountGroup.id
        }else{
            coaGroupId = ""
        }

        if(chartOfAccountHead) {
            coaId = chartOfAccountHead.id
        }else{
            coaId = ""
        }


        if (enterpriseConfiguration) {
                loadCoaGroups(enterpriseConfiguration.id);
                $('#enterpriselist').setValue(enterpriseConfiguration.name+'['+enterpriseConfiguration.code+']');
                  businessUnitList(enterpriseConfiguration.id);
                $("#enterpriseConfiguration").val(enterpriseConfiguration.id);
                setTimeout(function(){
                $('#businessUnitList').setValue(businessUnitConfiguration.name+'['+businessUnitConfiguration.code+']');
                $('#businessUnitConfiguration').val(businessUnitConfiguration.id);
                $('#productCategoryList').setValue(productCategory.name+'['+productCategory.code+']');
                $('#productCategory').val(productCategory.id);
                $('#masterProductList').setValue(masterProduct.name+'['+masterProduct.code+']');
                $('#masterProduct').val(masterProduct.id);
                $('#mainProductList').setValue(mainProduct.name+'['+mainProduct.code+']');
                $('#mainProduct').val(mainProduct.id);
                if (productType) {
                    $('#productType').val(productType.id).attr("selected", "selected");
//                    showHideProductCodeInput()
                }
                else {
                    $('#productType').val(productType);
                }
                if (measureUnitConfiguration) {
                    $('#measureUnitConfiguration').val(measureUnitConfiguration.id).attr("selected", "selected");
                }
                else {
                    $('#measureUnitConfiguration').val(measureUnitConfiguration);
                }
            }, 1000);

        }
        else {
            $('#enterpriseConfiguration').val(enterpriseConfiguration);
        }

        $('#name').val(finishProduct.name);
        //alert("oldCode "+finishProduct.id)
        $('#oldCode').val(finishProduct.oldCode);
        $('#other').val(finishProduct.other);
        $('#outsideSalesCode').val(finishProduct.outsideSalesCode);
        $('#packSize').val(finishProduct.packSize);

        $('#promotionalCrCode').val(finishProduct.promotionalCrCode);
        $('#promotionalDrCode').val(finishProduct.promotionalDrCode);
        $('#sampleCrCode').val(finishProduct.sampleCrCode);
        $('#sampleDrCode').val(finishProduct.sampleDrCode);
        $('#scrapSalesCode').val(finishProduct.scrapSalesCode);
        $('#texture').val(finishProduct.texture);
        $('#wastageSalesCode').val(finishProduct.wastageSalesCode);
        $('#width').val(finishProduct.width);
        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }

    function executeSearchFinishProduct(fieldName, fieldValue) {
        if (executePreConditionForSearchFinishProduct(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchFinishProduct(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchFinishProduct(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_form("#gFormFinishProduct");
            return false;
        }
        return true;
    }

    function reset_form(formName) {
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName).find('select').each(function () {
            this.value = "";
        });
        $('.productTypeDiv').hide();

//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
    }
    function executePostConditionForSearchFinishProduct(data, fieldName, fieldValue) {
        if (data == null) {
            reset_form("#gFormFinishProduct"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showFinishProduct(data);
        }
    }

    function businessUnitList(enId) {
        $.ajax({
            type: "POST",
            url: "${resource(dir:'finishProduct', file:'businessUnitForEnterprise')}?id=" + enId,
            beforeSend: function (jqXHR, settings) {
                $("#loader_icon").show();
            },
            success: function (data) {
                var businessUnitData = data.businessUnitList;
                var productCategoryData = data.categoryList;
                var masterProductData = data.masterProductList;
                var mainProductData = data.mainProductList;
                var productTypeData = data.productTypeList;
                var measuerUnitData = data.measureUnitList;

                var businessUnitMap = new Object(); // or var map = {};
                businessUnitMap["results"] = businessUnitData;
                businessUnitMap["total"] = businessUnitData.length;
                $("#businessUnitList").empty();
                $("#businessUnitList").flexbox(businessUnitMap, {
                    watermark: "Select Business Unit",
                    width: 140,
                    onSelect: function () {
                        $("#businessUnitConfiguration").val($('#businessUnitList_hidden').val());
                    }

                });
                $('#businessUnitList_input').val('');
                $('#businessUnitList_input').addClass("validate[required]");
                $('#businessUnitList_input').blur(function () {
                    if ($('#businessUnitList_input').val() == '') {
                        $("#businessUnitConfiguration").val("");
                    }
                });
                var productCategoryMap = new Object(); // or var map = {};
                productCategoryMap["results"] = productCategoryData;
                productCategoryMap["total"] = productCategoryData.length;
                $("#productCategoryList").empty();
                $("#productCategoryList").flexbox(productCategoryMap, {
                    watermark: "Select Category",
                    width: 140,
                    onSelect: function () {
                        $("#productCategory").val($('#productCategoryList_hidden').val());
                    }
                });
                $('#productCategoryList_input').val('');
                $('#productCategoryList_input').addClass("validate[required]");
                $('#productCategoryList_input').blur(function () {
                    if ($('#productCategoryList_input').val() == '') {
                        $("#productCategory").val("");
                    }
                });

                var masterProductMap = new Object(); // or var map = {};
                masterProductMap["results"] = masterProductData;
                masterProductMap["total"] = masterProductData.length;
                $("#masterProductList").empty();
                $("#masterProductList").flexbox(masterProductMap, {
                    watermark: "Select Master Product",
                    width: 140,
                    onSelect: function () {
                        $("#masterProduct").val($('#masterProductList_hidden').val());
                    }
                });
                $('#masterProductList_input').val('');
                $('#masterProductList_input').addClass("validate[required]");
                $('#masterProductList_input').blur(function () {
                    if ($('#masterProductList_input').val() == '') {
                        $("#masterProduct").val("");
                    }
                });

                var masterProductMap = new Object(); // or var map = {};
                masterProductMap["results"] = mainProductData;
                masterProductMap["total"] = mainProductData.length;
                $("#mainProductList").empty();
                $("#mainProductList").flexbox(masterProductMap, {
                    watermark: "Select Main Product",
                    width: 140,
                    onSelect: function () {
                        $("#mainProduct").val($('#mainProductList_hidden').val());
                    }
                });
                $('#mainProductList_input').val('');
                $('#mainProductList_input').addClass("validate[required]");
                $('#mainProductList_input').blur(function () {
                    if ($('#mainProductList_input').val() == '') {
                        $("#mainProduct").val("");
                    }
                });

                options = '<option value="">Select Unit</option>';
                $.each(productTypeData, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                });
                $("#productType").html(options);

                options = '<option value="">Select Unit</option>';
                $.each(measuerUnitData, function (key, val) {
                    options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                });
                $("#measureUnitConfiguration").html(options);


            },
            complete: function (data) {
                $("#loader_icon").hide();
            },
            dataType: 'json'
        });
    }

    function populateDependenyForEdit(entity) {
        $('#enterpriseId').val($('#enterpriseConfiguration').val());
        if ($('#enterpriseId').val()) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'businessUnitForEnterprise')}?id=" + $('#enterpriseId').val(),
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    var businessUnitData = data.businessUnitList;
                    var productCategoryData = data.categoryList;
                    var masterProductData = data.masterProductList;
                    var mainProductData = data.mainProductList;
                    var productTypeData = data.productTypeList;
                    var measuerUnitData = data.measureUnitList;

                    var options = '<option value="">Select Unit</option>';
                    $.each(businessUnitData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#businessUnitConfiguration").html(options);

                    options = '<option value="">Select Unit</option>';
                    $.each(productCategoryData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#productCategory").html(options);

                    options = '<option value="">Select Unit</option>';
                    $.each(masterProductData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#masterProduct").html(options);

                    options = '<option value="">Select Unit</option>';
                    $.each(mainProductData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#mainProduct").html(options);

                    options = '<option value="">Select Unit</option>';
                    $.each(productTypeData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#productType").html(options);

                    options = '<option value="">Select Unit</option>';
                    $.each(measuerUnitData, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#measureUnitConfiguration").html(options);
                },
                complete: function (data) {
                    $("#loader_icon").hide();
                    populateDependencySelect(entity);
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("No Enterprise Selected")
        }
    }
    function populateDependencySelect(entity) {
        if (entity.mainProduct) {
            $('#mainProduct').val(entity.mainProduct.id).attr("selected", "selected");
        }
        else {
            $('#mainProduct').val(entity.mainProduct);
        }

        if (entity.masterProduct) {
            $('#masterProduct').val(entity.masterProduct.id).attr("selected", "selected");
        }
        else {
            $('#masterProduct').val(entity.masterProduct);
        }
        if (entity.measureUnitConfiguration) {
            $('#measureUnitConfiguration').val(entity.measureUnitConfiguration.id).attr("selected", "selected");
        }
        else {
            $('#measureUnitConfiguration').val(entity.measureUnitConfiguration);
        }
        if (entity.productCategory) {
            $('#productCategory').val(entity.productCategory.id).attr("selected", "selected");
        }
        else {
            $('#productCategory').val(entity.productCategory);
        }
        if (entity.productType) {
            $('#productType').val(entity.productType.id).attr("selected", "selected");
            showHideProductCodeInput();
        }
        else {
            $('#productType').val(entity.productType);
        }

        if (entity.businessUnitConfiguration) {
            $('#businessUnitConfiguration').val(entity.businessUnitConfiguration.id).attr("selected", "selected");
        }
        else {
            $('#businessUnitConfiguration').val(entity.businessUnitConfiguration);
        }
    }

    function productCategoryList() {
        if ($('#enterpriseId').val()) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'productCategoryForEnterprise')}?id=" + $('#enterpriseId').val(),
                beforeSend: function (jqXHR, settings) {
                    $("#loader_icon").show();
                },
                success: function (data) {
                    var options = '<option value="">Select Unit</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
//                    console.log(val)
                    });
                    $("#productCategory").html(options);
//                  if(day!=0){
//                      $('#day').val(day).attr("selected","selected");
//                      $('#day').attr("disabled", true);
//                  }
                },
                complete: function (data) {
                    $("#loader_icon").hide();
                },
                dataType: 'json'
            });
        }
        else {
            MessageRenderer.showHeaderMessage("No Enterprise Selected")
        }
    }

    function showHideProductCodeInput() {
        if ($('#productType').val()) {
            var productTypeVal = $('#productType option:selected').text();
            if (productTypeVal == 'By Product') {
                productTypeVal = 'By_Product'
            }
//            $('.productTypeDiv').hide();

//            showDiv(productTypeVal)
        }
        function showDiv(divId) {
            $('#' + divId).show();
        }
    }

    function searchProduct() {
        $("#jqgrid-grid").setGridParam({
            url: "${request.contextPath}/finishProduct/list?query=" + $('#query').val()
        });
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function resetSearch() {
        $('#query').val('');
        $("#jqgrid-grid").setGridParam({
            url: "${request.contextPath}/finishProduct/list?query=" + $('#query').val()
        });
        $("#jqgrid-grid").trigger("reloadGrid");
    }

    function loadCoaGroups(enterpriseId){
        if(enterpriseId){
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'listCoaGroup')}?enterpriseId=" + enterpriseId,
                beforeSend: function (jqXHR, settings) {
                    SubmissionLoader.showTo()
                },
                success: function (data) {
                    var options = '<option value="">Select Accounts Group</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    });
                    $("#coaGroup").html(options);
                    if(coaGroupId){
                        $("#coaGroup").val(coaGroupId);
                        $("#coaGroup").change();
                    }
                },
                complete: function (data) {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        } else{
            $("#coaGroup").html("");
        }
        $("#chartOfAccounts").html("");
    }
    function loadCoaHeadByGroup(coaGroupId){
        if(coaGroupId){
            $.ajax({
                type: "POST",
                url: "${resource(dir:'finishProduct', file:'listCoaHeadByGroup')}?coaGroupId=" + coaGroupId,
                beforeSend: function (jqXHR, settings) {
                    SubmissionLoader.showTo();
                },
                success: function (data) {
                    var options = '<option value="">Select Accounts</option>';
                    $.each(data, function (key, val) {
                        options += '<option value="' + val.id + '">' + val.name + '</option>';
                    });
                    $("#chartOfAccounts").html(options);
                    if(coaId){
                        $("#chartOfAccounts").val(coaId);
                    }
                },
                complete: function (data) {
                    SubmissionLoader.hideFrom();
                },
                dataType: 'json'
            });
        } else{
            $("#chartOfAccounts").html("");
        }
    }
</script>