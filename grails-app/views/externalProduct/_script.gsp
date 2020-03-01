<script type="text/javascript" language="Javascript">
    $(document).ready(function () {
        $('#ui-widget-header-text').html('External Product')
        textboxes = $("input:visible:not([disabled],[readonly]),select,button");
        if ($.browser.mozilla) {
            $(textboxes).keypress(checkForEnter);
        } else {
            $(textboxes).keydown(checkForEnter);
        }

        $("#gFormExternalProduct").validationEngine({   //    client side validation
            isOverflown: true,
            overflownDIV: ".ui-layout-center"
        });
        $("#gFormExternalProduct").validationEngine('attach');
        reset_externalProduct_form("#gFormExternalProduct");
        $("#jqgrid-grid").jqGrid({
//            url: '',
            url: '${resource(dir:'externalProduct', file:'list')}',
            datatype: "json",
            colNames: [
                'SL',
                'ID',
                'Enterprise',
                'External Product Name',
                'Description',
                'Code'
            ],
            colModel: [
                {name: 'sl', index: 'sl', width: 30, sortable: false, align: 'left'},
                {name: 'id', index: 'id', width: 0, hidden: true},
                {name: 'enterpriseConfiguration', index: 'enterpriseConfiguration', width: 100, align: 'left'},
                {name: 'name', index: 'name', width: 100, align: 'left'},
                {name: 'description', index: 'description', width: 100, align: 'left'},
                {name: 'code', index: 'code', width: 100, align: 'left'}
            ],
            rowNum: 50,
            rowList: [10, 20, 30, 40, 50, 60, 70, 80, 90, 100, -1],
            pager: '#jqgrid-pager',
            sortname: 'id',
            viewrecords: true,
            sortorder: "desc",
            caption: "External Product List",
            autowidth: true,
            height: true,
            scrollOffset: 0,
            loadComplete: function () {
            },
            onSelectRow: function (rowid, status) {
                executeEditExternalProduct();
            }
        });
        $("#jqgrid-grid").jqGrid('navGrid', '#jqgrid-pager', {edit: false, add: false, del: false, search: false});
//     $("#jqgrid-grid").gridResize({minWidth:350,maxWidth:800,minHeight:200});
//      $(".ui-pg-selbox").children().each(function() {
//          if ($(this).val() == -1) {
//              $(this).html('All')
//          }
//
//      });
    });
    function getSelectedExternalProductId() {
        var externalProductId = null;
        var rowid = $("#jqgrid-grid").jqGrid('getGridParam', 'selrow');
        if (rowid) {
            externalProductId = $("#jqgrid-grid").jqGrid('getCell', rowid, 'id');
        }
        if (externalProductId == null) {
            externalProductId = $('#gFormExternalProduct input[name = id]').val();
        }
        return externalProductId;
    }
    function executePreCondition() {
        // trim field vales before process.
        trim_form();
        if (!$("#gFormExternalProduct").validationEngine('validate')) {
            return false;
        }
        return true;
    }
    function executeAjaxExternalProduct() {
        if (!executePreCondition()) {
            return false;
        }
        var actionUrl = null;
        if ($('#gFormExternalProduct input[name = id]').val()) {
            actionUrl = "${request.contextPath}/${params.controller}/update";
        } else {
            actionUrl = "${request.contextPath}/${params.controller}/create";
        }
        jQuery.ajax({
            type: 'post',
            data: jQuery("#gFormExternalProduct").serialize(),
            url: actionUrl,
            success: function (data, textStatus) {
                executePostCondition(data);
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
    function executePostCondition(result) {
        if (result.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_externalProduct_form('#gFormExternalProduct');
        }
        MessageRenderer.render(result);
    }
    function deleteAjaxExternalProduct() {    // Delete record
        var Id = getSelectedId();
        if (executePreConditionForDelete(Id)) {
            $("#dialog").dialog("destroy");
            $("#dialog-confirm-").dialog({
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
                            data: jQuery("#gFormExternalProduct").serialize(),
                            url: "${resource(dir:'externalProduct', file:'delete')}",
                            success: function (message) {
                                executePostConditionForDelete(message);
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

    function executePreConditionForEditExternalProduct(Id) {
        if (Id == null) {
            alert("Please select to edit");
            return false;
        }
        return true;
    }
    function executeEditExternalProduct() {
        var externalProductId = getSelectedExternalProductId();
        if (executePreConditionForEditExternalProduct(externalProductId)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'externalProduct', file:'edit')}?id=" + externalProductId,
                success: function (entity) {
                    executePostConditionForEditExternalProduct(entity);
                },
                dataType: 'json'
            });
        }
    }
    function executePostConditionForEditExternalProduct(data) {
        if (data == null) {
            alert('Selected External Product might have been deleted by someone');  //Message renderer
        } else {
            showExternalProduct(data);
        }
    }
    function executePreConditionForDeleteExternalProduct(externalProductId) {
        if (externalProductId == null) {
            alert("Please select a ExternalProduct to delete");
            return false;
        }
        return true;
    }
    function executePostConditionForDeleteExternalProduct(data) {
        if (data.type == 1) {
            $("#jqgrid-grid").trigger("reloadGrid");
            reset_externalProduct_form('#gFormExternalProduct');
        }
        MessageRenderer.render(data)
    }
    function showExternalProduct(entity) {
        var externalProduct = entity.externalProduct
        var enterpriseConfiguration = entity.enterpriseConfiguration

        $('#gFormExternalProduct input[name = id]').val(externalProduct.id);
        $('#gFormExternalProduct input[name = version]').val(externalProduct.version);

        if (enterpriseConfiguration) {
            $('#enterpriseList').setValue(enterpriseConfiguration.name);
            $('#enterpriseConfiguration').val(enterpriseConfiguration.id)
        }

        $('#name').val(externalProduct.name);
        $('#description').val(externalProduct.description);
        $('#code').val(externalProduct.code);

        $('#create-button').attr('value', 'Update');
        $('#cancel-button').attr('value', 'Cancel');
        $('#delete-button').show();
    }
    function executeSearchExternalProduct(fieldName, fieldValue) {
        if (executePreConditionForSearchExternalProduct(fieldName, fieldValue)) {
            $.ajax({
                type: "POST",
                url: "${resource(dir:'externalProduct', file:'search')}?fieldName=" + fieldName + "&fieldValue=" + fieldValue,
                success: function (entity) {
                    executePostConditionForSearchExternalProduct(entity, fieldName, fieldValue);
                },
                dataType: 'json'
            });
        }
    }
    function executePreConditionForSearchExternalProduct(fieldName, fieldValue) {
        // trim field vales before process.
        $('#' + fieldName).val($.trim($('#' + fieldName).val()));
        if (fieldValue == '' || fieldValue == null) {
            reset_externalProduct_form("#gFormExternalProduct");
            return false;
        }
        return true;
    }
    function executePostConditionForSearchExternalProduct(data, fieldName, fieldValue) {
        if (data == null) {
            reset_externalProduct_form("#gFormExternalProduct"); // Clear Form
            $('#' + fieldName).val(fieldValue); // Set search field with fieldValue
        } else {
            showExternalProduct(data);
        }
    }
    function reset_externalProduct_form(formName) {
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