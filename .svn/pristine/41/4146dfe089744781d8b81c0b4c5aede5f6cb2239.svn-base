<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('ProductType')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormProductType").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormProductType").validationEngine('attach');
        reset_productType_form("#gFormProductType");
        $("#jqgrid-grid").jqGrid({
            url: '${resource(dir:'productType', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',

                'Enterprise',
                'Product Type Code',
                'Product Type Name',
                'Note'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},

                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'note', index: 'note', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "Product Type List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditProductType();
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
    function getSelectedProductTypeId() {
        var productTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            productTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (productTypeId == null) {
            productTypeId = $('#gFormProductType input[name = id]').val();
        }
        return productTypeId;
    }
    function executePreConditionProductType() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormProductType").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxProductType() {
        if (!executePreConditionProductType()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormProductType input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormProductType").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionProductType(data);
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
    function executePostConditionProductType(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productType_form('#gFormProductType');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxProductType() {    // Delete record
        var productTypeId = getSelectedProductTypeId();
        if (executePreConditionForDeleteProductType(productTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-productType").dialog({
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
                            data: jQuery("#gFormProductType").serialize(),
                            url: "${resource(dir:'productType', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteProductType(message);
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

    function executePreConditionForEditProductType(productTypeId) {
        if (productTypeId == null) {
            alert("Please select a productType to edit");
            return false;
        }
        return true;
    }
    function executeEditProductType() {
        var productTypeId = getSelectedProductTypeId();
        if (executePreConditionForEditProductType(productTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productType', file:'edit')}?id=" + productTypeId,
                success: function (entity) {
                    executePostConditionForEditProductType(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditProductType(data) {
        if (data == null) {
            alert('Selected productType might have been deleted by someone');  //Message renderer
        } else {
            showProductType(data);
        }
    }
    function executePreConditionForDeleteProductType(productTypeId) {
        if (productTypeId == null) {
            alert("Please select a productType to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteProductType(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_productType_form('#gFormProductType');
        }
        MessageRenderer.render(data)
    }
    function showProductType(entity) {
        var productType = entity.productType
        var enterpriseConfiguration = entity.enterpriseConfiguration
        $('#gFormProductType input[name = id]').val(productType.id);
        $('#gFormProductType input[name = version]').val(productType.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }
        $('#name').val(productType.name);
        $('#code').val(productType.code);
        $('#code').attr('readonly', 'readonly');
        $('#note').val(productType.note);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchProductType(fieldName, fieldValue) {
        if (executePreConditionForSearchProductType(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'productType', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchProductType(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchProductType(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_productType_form("#gFormProductType");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchProductType(data, fieldName, fieldValue) {
        if (data == null) {
            reset_productType_form("#gFormProductType"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showProductType(data);
        }
    }
    function reset_productType_form(formName) {
        var enterprise = $("#enterpriseConfiguration").val();
        $(formName).find(":input").not(":button, :submit, :reset, :checkbox, :radio").each(function () {
            if (this.type == 'hidden') {
                this.value = "";
            } else {
                this.value = this.defaultValue;
            }
        });
        $(formName+' select').val('');
        $(formName+' input').attr('readonly',false);
//    $('input[type=checkbox]').attr('checked', false);
        $(formName + ' input[name = create-button]').attr('value', 'Create');
        $(formName + ' input[name = delete-button]').hide();
        $("#enterpriseConfiguration").val(enterprise);
    }
</script>