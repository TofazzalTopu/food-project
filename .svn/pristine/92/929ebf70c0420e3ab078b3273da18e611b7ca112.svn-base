<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('gFormExternalProductStock')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormExternalProductStock").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormExternalProductStock").validationEngine('attach');
        reset_externalProductStock_form("#gFormExternalProductStock");
        $("#jqgrid-grid").jqGrid({
//            url: '',
            url: '${resource(dir:'externalProductStock', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'External Product Name',
                'Stock'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'stock', index: 'stock', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "External Product Stock List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditExternalProductStock();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
    });
    function getSelectedExternalProductStockId() {
        var packageTypeId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            packageTypeId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (packageTypeId == null) {
            packageTypeId = $('#gFormExternalProductStock input[name = id]').val();
        }
        return packageTypeId;
    }
    function executePreConditionExternalProductStock() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormExternalProductStock").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxExternalProductStock() {
        if (!executePreConditionExternalProductStock()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormExternalProductStock input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormExternalProductStock").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostConditionExternalProductStock(data);
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
    function executePostConditionExternalProductStock(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_externalProductStock_form('#gFormExternalProductStock');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxExternalProductStock() {    // Delete record
        var packageTypeId = getSelectedExternalProductStockId();
        if (executePreConditionForDeleteExternalProductStock(packageTypeId)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-externalProductStock").dialog({
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
                            data: jQuery("#gFormExternalProductStock").serialize(),
                            url: "${resource(dir:'externalProductStock', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDeleteExternalProductStock(message);
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

    function executePreConditionForEditExternalProductStock(packageTypeId) {
        if (packageTypeId == null) {
            alert("Please select a product to edit stock.");
            return false;
        }
        return true;
    }
    function executeEditExternalProductStock() {
        var packageTypeId = getSelectedExternalProductStockId();
        if (executePreConditionForEditExternalProductStock(packageTypeId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'externalProductStock', file:'edit')}?id=" + packageTypeId,
                success: function (entity) {
                    executePostConditionForEditExternalProductStock(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditExternalProductStock(data) {
        if (data == null) {
            alert('Selected external product might have been deleted by someone');  //Message renderer
        } else {
            showExternalProductStock(data);
        }
    }
    function executePreConditionForDeleteExternalProductStock(packageTypeId) {
        if (packageTypeId == null) {
            alert("Please select a product to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteExternalProductStock(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_externalProductStock_form('#gFormExternalProductStock');
        }
        MessageRenderer.render(data)
    }
    function showExternalProductStock(entity) {
        var externalProductStock = entity.externalProductStock
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormExternalProductStock input[name = id]').val(externalProductStock.id);
        $('#gFormExternalProductStock input[name = version]').val(externalProductStock.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#inQuantity').val(externalProductStock.inQuantity);
        $('#externalProduct').val(externalProductStock.externalProduct.id);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchExternalProductStock(fieldName, fieldValue) {
        if (executePreConditionForSearchExternalProductStock(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'externalProductStock', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchExternalProductStock(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchExternalProductStock(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_externalProductStock_form("#gFormExternalProductStock");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchExternalProductStock(data, fieldName, fieldValue) {
        if (data == null) {
            reset_externalProductStock_form("#gFormExternalProductStock"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showExternalProductStock(data);
        }
    }
    function reset_externalProductStock_form(formName) {
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